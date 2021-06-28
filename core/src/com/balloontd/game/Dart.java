package com.balloontd.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Dart extends Actor {
    // accept how much farther after a dart crossed the screen border
    // before it is killed
    // (use getCoords() (i.e. center point) to determine)
    private final float ALLOWED_DELTA = 50F;

    private int pierce_cnt;
    protected GameScreen game_screen;
    private boolean alive_state;

    public Dart(GameScreen game_screen, TextureRegion region,
                Vector2 coord, int pierce_cnt) {
        this.game_screen = game_screen;
        this.pierce_cnt = pierce_cnt;

        setSize(region.getRegionWidth(), region.getRegionHeight());
        setOrigin(region.getRegionWidth() * 0.5F, region.getRegionHeight() * 0.5F);
        setCoords(coord);

        setAliveState(true);
    }

    @Override
    public void act(float delta) {
        // move forward, delegete to subclass
        move(delta);

        // for all bloons, see if touched.
        //   if touched bloon, call bloon::pop, then decrease
        //   current dart pierce by 1 (and set alive state to dead if pierceCnt=0 or something)
        for(Bloon bloon: game_screen.getBloonManager().getBloonList()){
            if(!bloon.getAliveState()) continue;
            if(touched(bloon)){
                Gdx.app.log("Dart::act", "I see touched balloon!");
                hit(bloon);
                if((--pierce_cnt) <= 0){
                    setAliveState(false);
                    break;
                }
            }
        }

        // see if go out of screen
        if(!game_screen.inRange(getCoords(), ALLOWED_DELTA))
            setAliveState(false);
    }

    @Override
    abstract public void draw(Batch batch, float parentAlpha);
    abstract public void move(float delta);
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
    public int getPierceCnt() { return pierce_cnt; }
    abstract public void hit(Bloon bloon);
    public boolean touched(Bloon bloon) {
        // override if you want
        return getCoords().dst(bloon.getCoords()) < bloon.getTouchRadius();
    }
    public void setAliveState(boolean is_alive) { alive_state = is_alive; }
    public boolean getAliveState() { return alive_state; }
}
