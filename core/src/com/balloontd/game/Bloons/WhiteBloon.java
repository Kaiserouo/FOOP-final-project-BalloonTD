package com.balloontd.game.bloons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.balloontd.game.Bloon;
import com.balloontd.game.Dart;
import com.balloontd.game.GameScreen;

import java.util.List;

public class WhiteBloon extends Bloon {
    private static final TextureRegion region =
            new TextureRegion(new Texture(Gdx.files.internal("white_bloon.png")));

    public BlueBloon(GameScreen game_screen, Float distance) {
        super(game_screen, region, distance);
        this.speed = base_speed * 1F;
        setTouchRadius(getTouchRadius() * 0.5F);
        setScale(0.5F);
    }

    @Override
    public void pop(Dart dart) {
        game_screen.getPlayer().addMoney(1);
        game_screen.getBloonManager().spawnBloons(this,
                List.of(
                    // list of new bloons
                    // distance will be set in function so no worries
                    new PinkBloon(game_screen, 0F),
                    new PinkBloon(game_screen, 0F)
                ), 15F
        );
        setAliveState(false);
    }

    @Override
    public float costHealthAmt() {
        return 6;
    }
}
