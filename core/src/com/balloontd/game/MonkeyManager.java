package com.balloontd.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

import java.util.ArrayList;
import java.util.List;

public class MonkeyManager {
    private List<Monkey> monkey_list;
    private Group group;
    private List<Monkey> monkey_buffer;

    public MonkeyManager() {
        group = new Group();
        monkey_list = new ArrayList<>();
        monkey_buffer = new ArrayList<>();
    }

    public Group getGroupActor() {
        return group;
    }

    public List<Monkey> getMonkeyList() {
        return monkey_list;
    }

    public void addMonkeyInBuffer(Monkey monkey) {
        monkey_buffer.add(monkey);
    }

    public void dumpBufferToList() {
        for(Monkey monkey: monkey_buffer){
            monkey_list.add(monkey);
            group.addActor(monkey);
        }
        monkey_buffer.clear();
    }

    public void cleanDeadMonkeys() {
        List<Monkey> dead_monkey = new ArrayList<>();
        for(Monkey monkey: monkey_list)
            if(!monkey.getAliveState())
                dead_monkey.add(monkey);
        for(Monkey monkey: dead_monkey){
            monkey_list.remove(monkey);
            group.removeActor(monkey);
        }
    }
}
