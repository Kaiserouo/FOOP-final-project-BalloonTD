package com.balloontd.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Map extends Actor {
    @Override
    abstract public void draw(Batch batch, float parentAlpha);

    abstract public Trail getTrail();
}
