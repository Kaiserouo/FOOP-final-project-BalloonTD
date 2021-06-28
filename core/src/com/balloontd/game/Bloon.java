package com.balloontd.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Bloon extends Actor {
    @Override
    public void act(float delta) {
        // move bloon
    }

    @Override
    abstract public void draw(Batch batch, float parentAlpha);

    abstract public float getDistance();
    abstract public void setDistance(float dist);
    abstract public Vector2 getCoords();
    abstract public float getTouchRadius();
    abstract public float getSpeed();
    abstract public void pop(Dart dart);
    abstract public void setAliveState(boolean is_alive);
    abstract public boolean getAliveState();
}
