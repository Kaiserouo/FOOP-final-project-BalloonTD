package com.balloontd.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Monkey extends Actor {
    private float cd_time;
    private Vector2 coord;
    protected float body_radius;
    protected float shoot_radius;
    protected GameScreen game_screen;
    private boolean alive_state;

    public Monkey(GameScreen game_screen, Vector2 coord, float body_radius,
                  float shoot_radius, boolean alive_state) {
        cd_time = 0;
        this.game_screen = game_screen;
        this.coord = coord.cpy();
        this.body_radius = body_radius;
        this.shoot_radius = shoot_radius;
        this.alive_state = alive_state;
    }


    // only tackles with the shooting part of logic.
    // Shooting animation should be tackled by subclass themselves.
    @Override
    public void act(float delta) {
        // update CD time
        cd_time += delta;

        // shoot dart
        if(cd_time > getCooldownTime()){
            // find in-range bloons
            float range_squ = (float) Math.pow(getShootRadius(), 2);
            List<Bloon> in_range_bloons =
                    game_screen.getBloonManager().getBloonList()
                               .stream()
                               .filter(bloon -> bloon.getCoords().dst2(getCoords()) < range_squ)
                               .collect(Collectors.toList());
            shoot(in_range_bloons);
            cd_time -= getCooldownTime();
        }
    }

    @Override
    abstract public void draw(Batch batch, float parentAlpha);

    abstract public String getName();
    public Vector2 getCoords() { return coord; }
    public Vector2 setCoords(Vector2 new_coord) { coord = new coord.cpy(); }
    public float getBodyRadius() { return body_radius; }
    public float getShootRadius() { return shoot_radius; }
    abstract public int getCurLevel();
    abstract public int getMaxLevel();
    abstract public float getLevelUpCost(int cur_level);
    abstract public void levelUp();
    abstract public String getLevelUpInfoDisplay(int cur_level);
    abstract public float getBuyPrice();
    abstract public float getSellPrice();
    abstract public void shoot(List<Bloon> in_range_bloons);
    abstract public float getCooldownTime();
    public void setAliveState(boolean is_alive) { alive_state = is_alive; }
    public boolean getAliveState() { return alive_state; }
}
