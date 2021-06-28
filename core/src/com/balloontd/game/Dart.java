package com.balloontd.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Dart extends Actor {
    @Override
    public void act(float delta) {
        // move forward
        // for all bloons, see if touched.
        //   if touched bloon, call bloon::pop, then decrease
        //   current dart pierce by 1 (and set alive state to dead if pierceCnt=0 or something)
    }

    @Override
    abstract public void draw(Batch batch, float parentAlpha);
    abstract public Vector2 getCoords();
    abstract public int getPierceCnt();
    abstract public void hit(Bloon bloon);
    abstract public boolean touched(Bloon bloon);
    abstract public void setAliveState(boolean is_alive);
    abstract public boolean getAliveState();
}
