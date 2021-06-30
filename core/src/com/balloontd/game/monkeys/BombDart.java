package com.balloontd.game.monkeys;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.balloontd.game.Bloon;
import com.balloontd.game.Dart;
import com.balloontd.game.GameScreen;

import java.util.ArrayList;
import java.util.List;

public class BombDart extends Dart {
    private static final TextureRegion region = new TextureRegion(new Texture(Gdx.files.internal("bomb.png")));
    private float bomb_radius_squ;
    private Vector2 velocity;
    public BombDart(GameScreen game_screen, Vector2 coord,
                    float bomb_radius, Vector2 velocity) {
        super(game_screen, region, coord, 1);
        this.bomb_radius_squ = bomb_radius * bomb_radius;
        this.velocity = velocity.cpy();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(
                region,
                getX(), getY(),
                getOriginX(), getOriginY(),
                getWidth(), getHeight(),
                getScaleX(), getScaleY(),
                getRotation()
        );
    }
    public void move(float delta) {
        setCoords(
                getCoords().cpy().mulAdd(velocity, delta)
        );
    }
    public boolean touched(Bloon bloon) {
        return bloon.getCoords().dst(getCoords()) <= bloon.getTouchRadius();
    }
    public void hit(Bloon bloon) {
        for(Bloon b_iter: game_screen.getBloonManager().getBloonList()){
            if(!b_iter.getAliveState()) continue;
            if(b_iter.getAliveState()
               && b_iter.getCoords().dst2(bloon.getCoords()) < bomb_radius_squ)
                    b_iter.pop(this);
        }
    }
}
