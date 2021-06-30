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

public class BombShooter extends Monkey {
    public static final Texture texture = new Texture(Gdx.files.internal("bomb_shooter.png"));
    public static final TextureRegion region = new TextureRegion(texture);
    public static final Float[] bombshooter_levelup_cost =
            new Float[] {700F, 250F, 400F};
    private float bomb_radius;
    private float bomb_speed;

    public BombShooter(GameScreen game_screen, Vector2 coord) {
        super(game_screen, region, coord,
              region.getRegionWidth() * 0.5F + 3F, 120F);
        cd_time = 1.3F;
        levelup_cost = bombshooter_levelup_cost;
        bomb_radius = 50F;
        bomb_speed = 750F;
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
        return "Bomb Shooter";
    }
    public String getIntro() {
        return "Shoot a bomb";
    }
    public void levelUp() {
        cur_level++;
        if(cur_level > getMaxLevel()){
            cur_level--;
            return;
        }

        switch (cur_level) {
            case 1:
                shoot_radius = 160F; break;
            case 2:
                bomb_radius = 70F; break;
        }
    }
    public String getLevelUpInfoDisplay(int cur_level) {
        // writes upgrade name in HackMD
        switch (cur_level + 1) {
            case 1:
                return "Longer Range";
            case 2:
                return "Larger Bomb";   // 50
        }
        return "";
    }
    
    public void shoot(List<Bloon> in_range_bloons) {
        // choose nearest one
        Bloon bloon = in_range_bloons.stream().min(
                (b1, b2) -> {
                    Float f1 = b1.getCoords().dst2(getCoords());
                    Float f2 = b2.getCoords().dst2(getCoords());
                    return f1.compareTo(f2);
                }
        ).get();

        // shoot
        game_screen.getDartManager().addDartInBuffer(
                new BombDart(
                        game_screen, getCoords(), bomb_radius,
                        bloon.getCoords().sub(getCoords()).setLength(bomb_speed)
                )
        );
        setRotation(bloon.getCoords().sub(getCoords()).angleDeg());
    }

    public Image getUIImage() {
        return new Image(new TextureRegion(region));
    }
    public Monkey cloneMonkey(GameScreen game_screen, Vector2 coords) {
        return new BombShooter(game_screen, coords);
    }
}
