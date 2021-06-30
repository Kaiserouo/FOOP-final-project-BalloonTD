package com.balloontd.game;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

import java.util.ArrayList;
import java.util.List;

public class BloonManager extends Actor {
    private List<Bloon> bloon_list;
    private Group group;
    private List<Bloon> bloon_buffer;   // added to bloon_list at end of render()

    public BloonManager() {
        bloon_list = new ArrayList<>();
        bloon_buffer = new ArrayList<>();
        group = new Group();
    }

    public Group getGroupActor() {
        return group;
    }

    public List<Bloon> getBloonList() {
        return bloon_list;
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
        else    // odd number
            dist = middle_dist - (space * (spawn_list.size() / 2));

        for(Bloon bloon: spawn_list){
            bloon.setDistance(dist);
            addBloonInBuffer(bloon);
            dist += space;
        }
    }

    public int getBufferBloonCnt() {
        return bloon_buffer.size();
    }

    public void dumpBufferToList() {
        for(Bloon bloon: bloon_buffer){
            bloon_list.add(bloon);
            group.addActor(bloon);
        }
        bloon_buffer.clear();
    }

    public void cleanDeadBloons() {
        List<Bloon> dead_bloon = new ArrayList<>();
        for(Bloon bloon: bloon_list)
            if(!bloon.getAliveState())
                dead_bloon.add(bloon);
        for(Bloon bloon: dead_bloon){
            bloon_list.remove(bloon);
            group.removeActor(bloon);
        }
    }
}
