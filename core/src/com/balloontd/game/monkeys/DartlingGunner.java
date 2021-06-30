package com.balloontd.game.monkeys;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.balloontd.BalloonTD;
import com.balloontd.game.Bloon;
import com.balloontd.game.GameScreen;
import com.balloontd.game.Monkey;

import java.util.List;

public class DartlingGunner extends Monkey {
    public static final Texture texture = new Texture(Gdx.files.internal("dartling_gunner.png"));
    public static final TextureRegion region = new TextureRegion(texture);
    public static final Float[] dartlinggunner_levelup_cost =
            new Float[] {700F, 200F, 600F, 1300F};

    public DartlingGunner(GameScreen game_screen, Vector2 coord) {
        super(game_screen, region, coord,
              region.getRegionWidth() * 0.5F + 10F, Float.MAX_VALUE);
        cd_time = 0.3F;
        levelup_cost = dartlinggunner_levelup_cost;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // may need to add animations...?
        batch.draw(
                region,
                getX(), getY(),
                getOriginX(), getOriginY(),
                getWidth(), getHeight(),
                getScaleX(), getScaleY(),
                getRotation()
        );
    }

    public String getName() {
        return "DartlingGunner";
    }
    public String getIntro() {
        return "Shoot dart to cursor";
    }
    public void levelUp() {
    }
    public String getLevelUpInfoDisplay(int cur_level) {
        return "";
    }
    public float getSellPrice() {
        return 0;
    }
    public void shoot(List<Bloon> in_range_bloons) {
        // get cursor's x, y
        Vector2 cursor = new Vector2(
                Gdx.input.getX(), BalloonTD.WORLD_HEIGHT - Gdx.input.getY()
        );

        // get difference
        Vector2 dir = cursor.sub(getCoords());

        // shoot
        game_screen.getDartManager().addDartInBuffer(
                new NormalDart(
                        game_screen, 1, getCoords(),
                        dir.setLength(2000F)
                )
        );
        setRotation(dir.angleDeg());
    }
    public Image getUIImage() {
        return null;

    }
    public Monkey cloneMonkey(GameScreen game_screen, Vector2 coords) {
        return null;

    }
}
