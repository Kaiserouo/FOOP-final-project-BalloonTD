package com.balloontd.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Dart extends Actor {
    @Override
    abstract public void act(float delta);

    @Override
    abstract public void draw(Batch batch, float parentAlpha);
    abstract public Vector2 getCoords();
    abstract public int getPierceCnt();
    abstract public void hit(Bloon bloon);
    abstract public boolean touched(Bloon bloon);
    abstract public void setAliveState(boolean is_alive);
    abstract public boolean getAliveState();
}
