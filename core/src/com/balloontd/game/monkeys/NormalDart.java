package com.balloontd.game.monkeys;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.balloontd.game.Bloon;
import com.balloontd.game.Dart;
import com.balloontd.game.GameScreen;

public class NormalDart extends Dart {
    // assume heading direction is same as moving direction (i.e. velocity)
    // also, assume the dart coords is at needle point
    // i.e.
    private Vector2 velocity;
    private static final TextureRegion region = new TextureRegion(new Texture(Gdx.files.internal("tack_dart.png")));

    public NormalDart(GameScreen game_screen, int pierce_cnt,
                      Vector2 coord, Vector2 velocity) {
        super(game_screen, region, coord, pierce_cnt);

        this.velocity = velocity.cpy();
        setScale(0.5F);
        setRotation(velocity.angleDeg());
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
        setCoords(getCoords().cpy().mulAdd(velocity, delta));
    }
    public boolean touched(Bloon bloon) {
        return false;
    }
    public void hit(Bloon bloon) {

    }
}
