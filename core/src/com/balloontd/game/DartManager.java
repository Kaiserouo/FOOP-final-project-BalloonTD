package com.balloontd.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

import java.util.ArrayList;
import java.util.List;

public class DartManager extends Actor {
    private List<Dart> dart_list;
    private Group group;
    private List<Dart> dart_buffer;

    public DartManager() {
        dart_list = new ArrayList<>();
        dart_buffer = new ArrayList<>();
        group = new Group();
    }

    public Group getGroupActor() {
        return group;
    }

    public List<Dart> getDartList() {
        return dart_list;
    }

    public void addDartInBuffer(Dart dart) {
        dart_buffer.add(dart);
    }

    public void dumpBufferToList() {
        for(Dart dart: dart_buffer){
            dart_list.add(dart);
            group.addActor(dart);
        }
        dart_buffer.clear();
    }

    public void cleanDeadDarts() {
        List<Dart> dead_dart = new ArrayList<>();
        for(Dart dart: dart_list)
            if(!dart.getAliveState())
                dead_dart.add(dart);
        for(Dart dart: dead_dart){
            dart_list.remove(dart);
            group.removeActor(dart);
        }
    }
}
