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

    public TackShooter(GameScreen game_screen, Vector2 coord) {
        super(game_screen, region, coord,
                region.getRegionWidth() * 0.5F + 10F, 100F);
        cd_time = 1F;
        levelup_cost = tackshooter_levelup_cost;
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
        return "TackShooter";
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
                break;
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
    }
    
    //public float getSellPrice() {
        //return 0;
    //}
    public void shoot(List<Bloon> in_range_bloons) {
        // simply shoot 8 direction
        for(int i = 0; i != 360; i += 45 ){
            game_screen.getDartManager().addDartInBuffer(
                    new NormalDart(
                            game_screen, 3, getCoords(),
                            new Vector2(0,1).setAngleDeg(i).setLength(2000F)
                    )
            );
        }
    }
    public Image getUIImage() {
<<<<<<< HEAD
        return new Image(new TextureRegion(region));
    }
    public Monkey cloneMonkey(GameScreen game_screen, Vector2 coords) {
        return new TackShooter(game_screen, coords);
=======
        return null;

    }
    public Monkey cloneMonkey(GameScreen game_screen, Vector2 coords) {
        return null;

>>>>>>> d028d02d3fca13531fee192234c25e151782805a
    }
}
