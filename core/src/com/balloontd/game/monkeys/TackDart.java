package com.balloontd.game.monkeys;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.balloontd.game.Bloon;
import com.balloontd.game.GameScreen;

public class TackDart extends NormalDart {
    // assume heading direction is same as moving direction (i.e. velocity)
    // also, assume the dart coords is at middle of dart
    private static final TextureRegion region = new TextureRegion(new Texture(Gdx.files.internal("tack_dart.png")));

    public TackDart(GameScreen game_screen, int pierce_cnt,
                      Vector2 coord, Vector2 velocity) {
        super(game_screen, pierce_cnt, coord, velocity);
        setScale(0.6F);
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
}
