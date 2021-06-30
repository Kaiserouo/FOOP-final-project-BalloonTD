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
            new Float[] {700F, 200F, 600F, 1300F, 1500F};
    private int pierce_cnt;
    private ShootBehavior shoot_behavior;
    private float dart_speed;
    private float shoot_deg_bias;

    public DartlingGunner(GameScreen game_screen, Vector2 coord) {
        super(game_screen, region, coord,
              region.getRegionWidth() * 0.5F + 10F, 3000F);
        cd_time = 0.3F;
        levelup_cost = dartlinggunner_levelup_cost;
        pierce_cnt = 1;
        shoot_behavior = new ShootOneBarrel();
        dart_speed = 1500F;
        shoot_deg_bias = 15F;
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
        return "Dartling Gunner";
    }
    public String getIntro() {
        return "Shoot dart to cursor";
    }
    public void levelUp() {
        cur_level++;
        if(cur_level > getMaxLevel()){
            cur_level--;
            return;
        }

        switch (cur_level) {
            case 1:
                shoot_deg_bias = 8F; break;
            case 2:
                cd_time = 0.2F; break;
            case 3:
                pierce_cnt = 2; break;
            case 4:
                shoot_behavior = new ShootTwoBarrel(20F); break;
        }
    }
    public String getLevelUpInfoDisplay(int cur_level) {
        // writes upgrade name in HackMD
        switch (cur_level + 1) {
            case 1:
                return "Focused Shooting"; // 8
            case 2:
                return "Faster Barrel";   // 1.5
            case 3:
                return "Stronger Dart"; // 2
            case 4:
                return "Double Barrel"; // 2
        }
        return "";

    }

    public void shoot(List<Bloon> in_range_bloons) {
        // get cursor's x, y
        Vector2 cursor = new Vector2(
                Gdx.input.getX(), BalloonTD.WORLD_HEIGHT - Gdx.input.getY()
        );

        // get difference
        Vector2 dir = cursor.sub(getCoords());

        // shoot
        shoot_behavior.shoot(dir);
        setRotation(dir.angleDeg());
    }
    public Image getUIImage() {
        return new Image(new TextureRegion(region));
    }
    public Monkey cloneMonkey(GameScreen game_screen, Vector2 coords) {
        return new DartlingGunner(game_screen, coords);
    }

    private interface ShootBehavior {
        void shoot(Vector2 dir);
        static Vector2 getRandomDegVec(Vector2 vec, float bias) {
            float random_deg =
                    (float) (Math.random() * 2 - 1) * bias;
            return vec.cpy().setAngleDeg(vec.angleDeg() + random_deg);
        }
    }
    private class ShootOneBarrel implements ShootBehavior {
        @Override
        public void shoot(Vector2 dir) {
            game_screen.getDartManager().addDartInBuffer(
                    new NormalDart(
                            game_screen, pierce_cnt, getCoords(),
                            ShootBehavior.getRandomDegVec(
                                    dir.cpy().setLength(dart_speed),
                                    shoot_deg_bias
                            )
                    )
            );
        }
    }
    private class ShootTwoBarrel implements ShootBehavior {
        private float spacing;
        public ShootTwoBarrel(float spacing) {
            this.spacing = spacing;
        }
        @Override
        public void shoot(Vector2 dir) {
            Vector2 delta = dir.cpy().rotate90(1)
                               .setLength(spacing * 0.5F);
            game_screen.getDartManager().addDartInBuffer(
                    new NormalDart(
                            game_screen, pierce_cnt, getCoords().add(delta),
                            ShootBehavior.getRandomDegVec(
                                    dir.cpy().setLength(dart_speed),
                                    shoot_deg_bias
                            )
                    )
            );
            game_screen.getDartManager().addDartInBuffer(
                    new NormalDart(
                            game_screen, pierce_cnt, getCoords().sub(delta),
                            ShootBehavior.getRandomDegVec(
                                    dir.cpy().setLength(dart_speed),
                                    shoot_deg_bias
                            )
                    )
            );
        }
    }
}
