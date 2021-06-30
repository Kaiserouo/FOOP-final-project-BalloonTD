package com.balloontd.game.monkeys;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.balloontd.game.Bloon;
import com.balloontd.game.GameScreen;
import com.balloontd.game.Monkey;

import java.util.List;

public class DartShooter extends Monkey {
    public static final Texture texture = new Texture(Gdx.files.internal("dart_shooter.png"));
    public static final TextureRegion region = new TextureRegion(texture);
    public static final Float[] dartshooter_levelup_cost =
            new Float[] {200F, 50F, 120F, 150F, 300F};
    private static final float dart_speed = 1500F;
    private int pierce_cnt;
    private ShootBehavior shoot_behavior;

    public DartShooter(GameScreen game_screen, Vector2 coord) {
        super(game_screen, region, coord,
              region.getRegionWidth() * 0.5F + 10F, 100F);
        cd_time = 1F;
        levelup_cost = dartshooter_levelup_cost;
        pierce_cnt = 1;
        shoot_behavior = new ShootOne();
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
        return "DartShooter";
    }
    public String getIntro() {
        return "Shoot a dart";
    }
    
    public void levelUp() {
        cur_level++;
        if(cur_level > getMaxLevel()){
            cur_level--;
            return;
        }

        switch (cur_level) {
            case 1:
                shoot_radius = 175F; break;
            case 2:
                pierce_cnt = 2; break;
            case 3:
                cd_time = 0.65F; break;
            case 4:
                shoot_behavior = new ShootThree();
        }
    }
    public String getLevelUpInfoDisplay(int cur_level) {
        // writes upgrade name in HackMD
        switch (cur_level + 1) {
            case 1:

            case 2:

            case 3:

            case 4:
        }
    }

    public float getSellPrice() {
        return 0;
    }
    public void shoot(List<Bloon> in_range_bloons) {
        if(in_range_bloons.size() == 0) return;
        // choose nearest one
        Bloon bloon = in_range_bloons.stream().min(
                (b1, b2) -> {
                    Float f1 = b1.getCoords().dst2(getCoords());
                    Float f2 = b2.getCoords().dst2(getCoords());
                    return f1.compareTo(f2);
                }
        ).get();

        // shoot
        shoot_behavior.shoot(bloon);
    }
    public Image getUIImage() {
        return new Image(new TextureRegion(region));
    }
    public Monkey cloneMonkey(GameScreen game_screen, Vector2 coords) {
        return new DartShooter(game_screen, coords);
    }

    // shooting strategy pattern
    private interface ShootBehavior {
        void shoot(Bloon target);
    }
    private class ShootOne implements ShootBehavior {
        public void shoot(Bloon target) {
            game_screen.getDartManager().addDartInBuffer(
                    new NormalDart(
                            game_screen, pierce_cnt, getCoords(),
                            target.getCoords().sub(getCoords())
                                    .setLength(dart_speed)
                    )
            );
        }
    }
    private class ShootThree implements ShootBehavior {
        public void shoot(Bloon target) {
            Vector2 velocity = target.getCoords().sub(getCoords())
                                     .setLength(dart_speed);
            game_screen.getDartManager().addDartInBuffer(
                    new NormalDart(
                            game_screen, pierce_cnt, getCoords(), velocity
                    )
            );
            game_screen.getDartManager().addDartInBuffer(
                    new NormalDart(
                            game_screen, pierce_cnt, getCoords(),
                            velocity.cpy().setAngleDeg(velocity.angleDeg() + 15F)
                    )
            );
            game_screen.getDartManager().addDartInBuffer(
                    new NormalDart(
                            game_screen, pierce_cnt, getCoords(),
                            velocity.cpy().setAngleDeg(velocity.angleDeg() + 15F)
                    )
            );
        }
    }
}