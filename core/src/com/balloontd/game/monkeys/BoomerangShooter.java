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

public class BoomerangShooter extends Monkey {
    public static final Texture texture = new Texture(Gdx.files.internal("boomerang_shooter.png"));
    public static final TextureRegion region = new TextureRegion(texture);
    public static final Float[] boomerangshooter_levelup_cost =
            new Float[] {600F, 200F, 300F};

    private int pierce_cnt;

    public BoomerangShooter(GameScreen game_screen, Vector2 coord) {
        super(game_screen, region, coord,
              region.getRegionWidth() * 0.5F + 10F, 150F);
        cd_time = 1.5F;
        levelup_cost = boomerangshooter_levelup_cost;

        pierce_cnt = 3;
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
        return "BoomerangShooter";
    }
    public String getIntro() {
        return "Shoot a boomerang";
    }
    public void levelUp() {
        cur_level++;
        if(cur_level > getMaxLevel()){
            cur_level--;
            return;
        }

        switch (cur_level) {
            case 1:
                shoot_radius = 250F; break;
            case 2:
                break;
        }
    }
    public String getLevelUpInfoDisplay(int cur_level) {
        // writes upgrade name in HackMD
        switch (cur_level + 1) {
            case 1:
                return "Longer Range";
            case 2:
                return "Sharper Edge";   // 5
        }
    }
    //public float getSellPrice() {
        //return 0;
    //}
    public void shoot(List<Bloon> in_range_bloons) {
        // choose nearest one
        if(in_range_bloons.size() == 0) return;
        Bloon bloon = in_range_bloons.stream().min(
                (b1, b2) -> {
                    Float f1 = b1.getCoords().dst2(getCoords());
                    Float f2 = b2.getCoords().dst2(getCoords());
                    return f1.compareTo(f2);
                }
        ).get();

        // shoot
        game_screen.getDartManager().addDartInBuffer(
                new BoomerangDart(
                        game_screen, pierce_cnt, getCoords(), bloon.getCoords(),
                        getShootRadius() * 1.3F,
                        0.9F, 0.3F, 0.15F
                )
        );
    }
    public Image getUIImage() {
        return new Image(new TextureRegion(region));
    }
    public Monkey cloneMonkey(GameScreen game_screen, Vector2 coords) {
        return new BoomerangShooter(game_screen, coords);
    }
}
