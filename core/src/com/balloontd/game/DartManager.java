package com.balloontd.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;
import java.util.List;

public class DartManager extends Actor {
    private List<Dart> dart_list;
    private List<Dart> dart_buffer;

    public DartManager() {
        dart_list = new ArrayList<>();
        dart_buffer = new ArrayList<>();
    }

    @Override
    public void act(float delta) {
        for(Dart dart: dart_list)
            dart.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        for(Dart dart: dart_list)
            dart.draw(batch, parentAlpha);
    }

    public List<Dart> getDartList() {
        return dart_list;
    }

    public void addDartInBuffer(Dart dart) {
        dart_buffer.add(dart);
    }

    public void dumpBufferToList() {
        dart_list.addAll(dart_buffer);
        dart_buffer.clear();
    }

    public void cleanDeadDarts() {
        dart_list.removeIf(dart -> !dart.getAliveState());
    }
}
