package com.balloontd.game.bloons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.balloontd.game.Bloon;
import com.balloontd.game.Dart;
import com.balloontd.game.GameScreen;

public class RedBloon extends Bloon {
    private static final TextureRegion region =
            new TextureRegion(new Texture(Gdx.files.internal("red_bloon.png")));

    public RedBloon(GameScreen game_screen, Float distance) {
        super(game_screen, region, distance);
        this.speed = base_speed * 1F;
        setTouchRadius(getTouchRadius() * 0.5F);
        setScale(0.5F);
    }

    @Override
    public void pop(Dart dart) {
        game_screen.getPlayer().addMoney(1);
        setAliveState(false);
    }

    @Override
    public float costHealthAmt() {
        return 1;
    }
}