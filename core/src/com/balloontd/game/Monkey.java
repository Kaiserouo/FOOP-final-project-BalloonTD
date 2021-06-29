package com.balloontd.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Monkey extends Actor {
    private float cd_time;
    protected float body_radius;
    protected float shoot_radius;
    protected GameScreen game_screen;
    private boolean alive_state;

    private float[] levelup_cost;
    private int cur_level, max_level;
    

    public Monkey(GameScreen game_screen, TextureRegion region,
                  Vector2 coord, float body_radius, float shoot_radius) {
        cd_time = 0;
        this.game_screen = game_screen;
        this.body_radius = body_radius;
        this.shoot_radius = shoot_radius;

        setSize(region.getRegionWidth(), region.getRegionHeight());
        setOrigin(region.getRegionWidth() * 0.5F, region.getRegionHeight() * 0.5F);
        setCoords(coord);

        setAliveState(true);
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
            if(in_range_bloons.size() != 0){
                cd_time -= getCooldownTime();
                shoot(in_range_bloons);
            }
            else{
                // no bloon in range, wait for 1/10 * cooldown time
                cd_time = getCooldownTime() * 0.9F;
            }
        }
    }

    @Override
    abstract public void draw(Batch batch, float parentAlpha);

    abstract public String getName();
    public Vector2 getCoords() {
        // defined as the middle of the texture
        // note that everytime you change someone's width,
        // its coords will change, too
        return new Vector2(
                getX() + getWidth() / 2,
                getY() + getHeight() / 2
        );
    }
    public void setCoords(Vector2 new_coord) {
        setX(new_coord.x - getWidth() / 2);
        setY(new_coord.y - getHeight() / 2);
    }

    // get basic information about monkey
    abstract public String getIntro();

    public float getBodyRadius() { return body_radius; }
    public float getShootRadius() { return shoot_radius; }

    public int getCurLevel() { return cur_level; }
    public int getMaxLevel() { return max_level; }

    // given this level, get level up cost
    // (e.g. monkey is level 0, getLevelUp(getCurLevel()) = level up cost of 0->1)
    public float getLevelUpCost(int cur_level) { return levelup_cost[cur_level+1]; }

    // level up, after this function getCurLevel should increase
    abstract public void levelUp();

    // given current level, get next level's information
    // (e.g. monkey level 1 have "longer range" = getLevelUpDisplay(0))
    abstract public String getLevelUpInfoDisplay(int cur_level);

    // get level 0 monkey price
    public float getBuyPrice() { return levelup_cost[0]; }

    // get price, basically it's a proportion of total spent money on this monkey
    abstract public float getSellPrice();

    // given in-range bloons, perform shoot action
    abstract public void shoot(List<Bloon> in_range_bloons);

    // get cooldown time. NOT the current remaining time until next shoot
    // e.g. monkey cooldown is 1 second, after 0.5 second, this should still return 1
    public float getCooldownTime() { return cd_time; }

    public void setAliveState(boolean is_alive) { alive_state = is_alive; }
    public boolean getAliveState() { return alive_state; }
}
