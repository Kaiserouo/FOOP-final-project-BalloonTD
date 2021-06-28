package com.balloontd.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;
import java.util.List;

public class MonkeyManager extends Actor {
    private List<Monkey> monkey_list;
    private List<Monkey> monkey_buffer;

    public MonkeyManager() {
        monkey_list = new ArrayList<>();
        monkey_buffer = new ArrayList<>();
    }

    @Override
    public void act(float delta) {
        for(Monkey monkey: monkey_list)
            if(monkey.getAliveState())
                monkey.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        for(Monkey monkey: monkey_list)
            if(monkey.getAliveState())
                monkey.draw(batch, parentAlpha);
    }

    public List<Monkey> getMonkeyList() {
        return monkey_list;
    }

    public void addMonkeyInBuffer(Monkey monkey) {
        monkey_buffer.add(monkey);
    }

    public void dumpBufferToList() {
        monkey_list.addAll(monkey_buffer);
        monkey_buffer.clear();
    }

    public void cleanDeadMonkeys() {
        monkey_list.removeIf(monkey -> !monkey.getAliveState());
    }
}
