package com.balloontd.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.StringReader;
import java.util.*;

/**
 *  round file format:
 *      <header>
 *      ---
 *      <round>
 *      ---
 *  where:
 *      <header> := "round_cnt: <round_cnt: int>"
 *      <round> := "<round>\n---\n<round>"
 *                 | "<round_header>\n<statement>"
 *      <round_header> := "round <round: int>\nreward: <reward: int>"
 *      <statement> := "single <bloon_type: String>"      // spawn 1 bloon, type defined in BloonSpawner
 *                   | "wait <wait_time: float>"
 *                   | "stream <statement> <times: int>"
 *                   | "<statement> <statement>"
 *
 *      Statement part is handled by RoundParser, can span multiple lines
 *
 *      e.g. Spawn 10 red bloons after 0.1 seconds from start, spacing 1 second
 *      => <statement> = "wait 0.1 stream single R wait 1 10"
 *                     => "(wait 0.1) (stream (single R) (wait 1) 10)"
 *      e.g. Spawn 10 red & blue bloons after 0.1 seconds from start,
 *           intertwined with each other (i.e. R->B->R->B->...), spacing 1 second
 *      => <statement> = "wait 0.1 stream single R wait 1 single B wait 1 10"
 *                     => "(wait 0.1) (stream (single R) (wait 1) (single B) (wait 1) 10)"
 */

public class RoundManager {
    private List<List<Map.Entry<Float, String>>> spawn_info;
    private List<Integer> reward;
    private GameScreen game_screen;
    private IBloonSpawner spawner;
    private int current_round;
    private int bloon_next_index;

    private boolean is_in_round;
    private float round_elapsed_time;

    private RoundParser round_parser;

    public RoundManager(GameScreen game_screen, String filename, IBloonSpawner spawner) {
        this.game_screen = game_screen;
        this.spawner = spawner;
        this.round_parser = new RoundParser(spawner);
        current_round = 0;
        bloon_next_index = 0;
        is_in_round = false;
        round_elapsed_time = 0;

        FileHandle file = Gdx.files.internal(filename);
        Scanner scanner = new Scanner(new StringReader(file.readString()));

        // read header info
        int round_count = Integer.parseInt(scanner.nextLine().split("[ ]+")[1]);
        scanner.nextLine();

        // read rest of info
        round_parser.parseFile(scanner, round_count);
        spawn_info = round_parser.getSpawnInfo();
        reward = round_parser.gerRewardList();
    }

    public int getCurrentRound() {
        return current_round + 1;
    }
    public void proceedToNextRound() {
        // need to give money
        game_screen.getPlayer().addMoney(reward.get(current_round));
        if(current_round + 1 < getTotalRound())
            current_round++;
        bloon_next_index = 0;
    }
    public boolean isThisRoundSpawnOver() {
        return spawn_info.get(current_round).size() == bloon_next_index;
    }
    public int getTotalRound() {
        return spawn_info.size();
    }
    public List<Bloon> getReleasedBloons(float round_elapsed_time) {
        // maybe should let round_elapsed_time be delta...?
        List<Bloon> released_bloons = new ArrayList<>();
        List<Map.Entry<Float, String>> cur_spawn_list = spawn_info.get(current_round);

        while(bloon_next_index < cur_spawn_list.size()){
            Map.Entry<Float, String> cur_bloon = cur_spawn_list.get(bloon_next_index);
            if(cur_bloon.getKey() > round_elapsed_time)
                break;

            Optional<Bloon> spawned_bloon = spawner.spawn(cur_bloon.getValue());
            if(spawned_bloon.isEmpty())
                throw new RuntimeException("RoundManager: Spawned null bloon!");
            released_bloons.add(spawned_bloon.get());
            bloon_next_index++;
        }
        return released_bloons;
    }

    public boolean isInRound() { return is_in_round; }

    public void startRound() {
        round_elapsed_time = 0;
        is_in_round = true;
    }

    public void act(float delta) {
        if(is_in_round) {
            round_elapsed_time += delta;
            for(Bloon bloon: getReleasedBloons(round_elapsed_time))
                game_screen.getBloonManager().addBloonInBuffer(bloon);

            if(isThisRoundSpawnOver()
               && game_screen.getBloonManager().getBufferBloonCnt() == 0
               && game_screen.getBloonManager().getBloonList().size() == 0){
                    if(getCurrentRound() < getTotalRound()){
                        proceedToNextRound();
                    }
                    else{
                        // game over
                        game_screen.gameOver(true);
                    }
                    is_in_round = false;
            }
        }
    }
}

// Only tackles with parsing round info, does not includes header and first '---'
class RoundParser {
    private List<List<Map.Entry<Float, String>>> spawn_info;
    private List<Integer> reward;
    private IBloonSpawner spawner;

    private String[] inputs;
    private int next;
    private float elapsed_time;

    public RoundParser(IBloonSpawner spawner) {
        spawn_info = new ArrayList<>();
        reward = new ArrayList<>();
        this.spawner = spawner;
    }

    public List<List<Map.Entry<Float, String>>> getSpawnInfo() { return spawn_info; }
    public List<Integer> gerRewardList() { return reward; }

    public void parseFile(Scanner scanner, int round_count) {
        spawn_info = new ArrayList<>();
        reward = new ArrayList<>();

        for(int i = 0; i != round_count; ++i){
            // read <round_header>
            scanner.nextLine();
            reward.add(Integer.parseInt(scanner.nextLine().split("[ ]+")[1]));

            // read <statement>
            StringBuilder input_str = new StringBuilder();
            String read_line;
            while(true){
                read_line = scanner.nextLine();
                if(read_line.contains("---")) break;
                input_str.append(read_line);
                input_str.append("\n");
            }
            inputs = input_str.toString().split("[ \t\n]+");
            next = 0;
            elapsed_time = 0;
            List<Map.Entry<Float, String>> cur_round_info = new ArrayList<>();
            while(next != inputs.length)
                cur_round_info.addAll(OneStatement());
            spawn_info.add(cur_round_info);
        }
    }

    private List<Map.Entry<Float, String>> OneStatement() {
        List<Map.Entry<Float, String>> ret = new ArrayList<>();
        if(next == inputs.length) return ret;
        switch (inputs[next]) {
            case "wait":
                ret.addAll(Wait()); break;
            case "single":
                ret.addAll(Single()); break;
            case "stream":
                ret.addAll(Stream()); break;
        }
        return ret;
    }
    private List<Map.Entry<Float, String>> Wait() {
        elapsed_time += Float.parseFloat(inputs[next + 1]);
        next += 2;
        return new ArrayList<>();
    }
    private List<Map.Entry<Float, String>> Single() {
        next += 2;
        return List.of(Map.entry(elapsed_time, inputs[next - 1]));
    }
    private List<Map.Entry<Float, String>> Stream() {
        next += 1;
        float cur_elapsed_time = elapsed_time;
        List<Map.Entry<Float, String>> one_pass = new ArrayList<>();
        int times = 0;
        while(true){
            // try until `next` is a number (representing times)
            try{
                times = Integer.parseInt(inputs[next]);
                next++;
                break;
            } catch(NumberFormatException e){
                one_pass.addAll(OneStatement());
            }
        }

        // tackles with array
        float delta_time = elapsed_time - cur_elapsed_time;
        List<Map.Entry<Float, String>> ret = new ArrayList<>();
        for(int i = 0; i != times; ++i) {
            float cur_delta = delta_time * i;
            for(Map.Entry<Float, String> ent: one_pass)
                ret.add(Map.entry(ent.getKey() + cur_delta, ent.getValue()));
        }
        elapsed_time = cur_elapsed_time + delta_time * times;
        return ret;
    }
}