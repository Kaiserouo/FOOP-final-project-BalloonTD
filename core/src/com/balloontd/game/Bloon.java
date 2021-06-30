package com.balloontd.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Bloon extends Actor {
    private TextureRegion region;
<<<<<<< HEAD
    protected static final Float base_speed = 300F;
=======
    protected static final Float base_speed = 150F;    // to be decide
>>>>>>> d028d02d3fca13531fee192234c25e151782805a
    private boolean is_alive;
    protected Float distance, radius, speed;
    protected GameScreen game_screen;

    public Bloon(GameScreen game_screen, TextureRegion region, float dist) {
        this.region = region;
        this.game_screen = game_screen;
        this.distance = dist;

        this.radius = region.getRegionHeight() * 0.5F;

        setSize(region.getRegionWidth(), region.getRegionHeight());
        setOrigin(region.getRegionWidth() * 0.5F, region.getRegionHeight() * 0.5F);
        setCoords(game_screen.getTrail().getCoordinate(dist));

        setAliveState(true);
    }

    @Override
    public void act(float delta_t) {
        setDistance(getDistance() + delta_t*speed);
        if(getDistance() > game_screen.getTrail().getTotalLength()){
            // walked to end, cost health
            game_screen.getPlayer().costHP(costHealthAmt());
            setAliveState(false);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setCoords(game_screen.getTrail().getCoordinate(distance));

        super.draw(batch, parentAlpha);
        batch.draw(region, getX(), getY(), 
                getOriginX(), getOriginY(), 
                getWidth(), getHeight(), 
                getScaleX(), getScaleY(), 
                getRotation());
    }

    public float getDistance() {
        return this.distance;
    }

    public void setDistance(float dist) {
        this.distance = dist;

    }

    public Vector2 getCoords() {
        return new Vector2(
                getX() + getWidth() / 2,
                getY() + getHeight() / 2
        );
    }
    public void setCoords(Vector2 new_coord) {
        setX(new_coord.x - getWidth() / 2);
        setY(new_coord.y - getHeight() / 2);
    }


    public float getTouchRadius() {
        return this.radius;
    }
    public void setTouchRadius(float radius) { this.radius = radius; }

    public float getSpeed() {
        return this.speed;
    }
    public void setSpeed(float speed) { this.speed = speed; }

    abstract public void pop(Dart dart);

    // how much health cost if bloon reach end?
    // please convert to RBE(Red Bloon Equivalent), i.e. how many
    // needles
    abstract public float costHealthAmt();

    public void setAliveState(boolean is_alive) {
        this.is_alive = is_alive;
    }
    
    public boolean getAliveState() {
        return this.is_alive;
    };
}
