package com.balloontd.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.List;

public abstract class Monkey extends Actor {
    @Override
    abstract public void act(float delta);

    @Override
    abstract public void draw(Batch batch, float parentAlpha);

    abstract public String getName();
    abstract public Vector2 getCoords();
    abstract public float getBodyRadius();
    abstract public float getShootRadius();
    abstract public int getCurLevel();
    abstract public int getMaxLevel();
    abstract public float getLevelUpCost(int cur_level);
    abstract public void levelUp();
    abstract public String getLevelUpInfoDisplay(int cur_level);
    abstract public float getBuyPrice();
    abstract public float getSellPrice();
    abstract public void shoot(List<Bloon> in_range_bloons);
    abstract public float getCooldownTime();
}
