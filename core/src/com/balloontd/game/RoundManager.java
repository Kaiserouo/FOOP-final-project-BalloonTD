package com.balloontd.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.StringReader;
import java.util.*;

public class RoundManager {
    private List<List<Map.Entry<Float, String>>> spawn_info;
    private List<Integer> reward;
    private GameScreen game_screen;
    private IBloonSpawner spawner;
    private int current_round;
    private int bloon_next_index;

    public RoundManager(GameScreen game_screen, String filename, IBloonSpawner spawner) {
        this.game_screen = game_screen;
        this.spawner = spawner;
        current_round = 0;
        bloon_next_index = 0;

        FileHandle file = Gdx.files.internal(filename);
        Scanner scanner = new Scanner(new StringReader(file.readString()));

        // read header info
        int round_count = Integer.parseInt(scanner.nextLine().split("[ ]+")[1]);
        scanner.nextLine();

        // read rest of info
        spawn_info = new ArrayList<>();
        reward = new ArrayList<>();
        for(int i = 1; i <= round_count; ++i){
            List<Map.Entry<Float, String>> cur_spawn_info = new ArrayList<>();
            scanner.nextLine();
            reward.add(Integer.parseInt(scanner.nextLine().split("[ ]+")[1]));
            while(true){
                String input_str = scanner.nextLine();
                if(input_str.contains("---")) break;
                String[] input_arr = input_str.split("[ ]+");
                cur_spawn_info.add(Map.entry(
                    Float.parseFloat(input_arr[0]),
                    input_arr[1]
                ));
            }
            spawn_info.add(cur_spawn_info);
        }

        Gdx.app.log("[GR_spawn_info]", spawn_info.toString());
        Gdx.app.log("[GR_reward]", reward.toString());
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
}
