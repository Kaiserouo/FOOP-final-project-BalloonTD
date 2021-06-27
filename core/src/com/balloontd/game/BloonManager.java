package com.balloontd.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;
import java.util.List;

public class BloonManager extends Actor {
    private List<Bloon> bloon_list;
    private List<Bloon> bloon_buffer;   // added to bloon_list at end of render()

    public BloonManager() {
        bloon_list = new ArrayList<>();
        bloon_buffer = new ArrayList<>();
    }

    @Override
    public void act(float delta) {
        // note that all those popping logic or sorts will
        // be implemented in e.g. screen, not here.
        for(Bloon bloon: bloon_list)
            bloon.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        for(Bloon bloon: bloon_list)
            bloon.draw(batch, parentAlpha);
    }

    public List<Bloon> getBloonList() {
        return new ArrayList<>(bloon_list);
    }

    public void addBloonInBuffer(Bloon bloon) {
        bloon_buffer.add(bloon);
    }

    public void spawnBloons(Bloon poped_bloon, List<Bloon> spawn_list, float space) {
        // will spawn bloons near `poped_bloon`, with spacing `space`
        float middle_dist = poped_bloon.getDistance();
        float dist;
        if(spawn_list.size() % 2 == 0) // even number
            dist = middle_dist - (space * (spawn_list.size() / 2 - 0.5F));
        else
            dist = middle_dist - (space * (spawn_list.size() / 2));
        for(Bloon bloon: spawn_list){
            bloon.setDistance(dist);
            dist += space;
        }
    }

    public void dumpBufferToList() {
        bloon_list.addAll(bloon_buffer);
        bloon_buffer.clear();
    }

    public void cleanDeadBloons() {
        bloon_list.removeIf((bloon) -> bloon.getAliveState() == false);
    }
}
