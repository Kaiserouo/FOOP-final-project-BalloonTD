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

public class TackShooter extends Monkey {
    public static final Texture texture = new Texture(Gdx.files.internal("tack_shooter.png"));
    public static final TextureRegion region = new TextureRegion(texture);
    public static final Float[] tackshooter_levelup_cost =
            new Float[] {350F, 200F, 150F, 300F};
    private float dart_speed = 1500F;

    private int shoot_cnt;

    public TackShooter(GameScreen game_screen, Vector2 coord) {
        super(game_screen, region, coord,
                region.getRegionWidth() * 0.5F + 3F, 100F);
        cd_time = 1F;
        levelup_cost = tackshooter_levelup_cost;
        shoot_cnt = 8;
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
        return "Tack Shooter";
    }
    public String getIntro() {
        return "Shoot all directions";
    }
    public void levelUp() {
        cur_level++;
        if(cur_level > getMaxLevel()){
            cur_level--;
            return;
        }

        switch (cur_level) {
            case 1:
                cd_time = 0.8F; break;
            case 2:
                shoot_radius = 150F; break;
            case 3:
                shoot_cnt = 12; break;
        }
    }
    public String getLevelUpInfoDisplay(int cur_level) {
        // writes upgrade name in HackMD
        switch (cur_level + 1) {
            case 1:
                return "Faster Shooting";
            case 2:
                return "Longer Range";
            case 3:
                return "Tack Sprayer";  // 12
        }
        return "";

    }

    public void shoot(List<Bloon> in_range_bloons) {
        for(int i = 0; i != shoot_cnt; i++ ){
            game_screen.getDartManager().addDartInBuffer(
                    new TackDart(
                            game_screen, 1, getCoords(),
                            new Vector2(0,1)
                                    .setAngleDeg(360F * i / shoot_cnt)
                                    .setLength(dart_speed)
                    )
            );
        }
    }
    public Image getUIImage() {
        return new Image(new TextureRegion(region));
    }
    public Monkey cloneMonkey(GameScreen game_screen, Vector2 coords) {
        return new TackShooter(game_screen, coords);
    }
}
