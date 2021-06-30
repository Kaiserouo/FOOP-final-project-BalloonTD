package com.balloontd.game.monkeys;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.balloontd.game.Bloon;
import com.balloontd.game.Dart;
import com.balloontd.game.GameScreen;

public class BoomerangDart extends Dart {
    private static final TextureRegion region = new TextureRegion(new Texture(Gdx.files.internal("boomerang.png")));
    private Vector2 start_pos;  // i.e. monkey position
    private Vector2 delta;
    private float a;   // half major axis
    private float b;   // half minor axis
    private float ang_v;    // i.e. omega
    private float spinning_ang_v;    // i.e. omega
    private float period;
    private float elapsed_time;    // i.e. omega


    public BoomerangDart(GameScreen game_screen, int pierce_cnt,
                         Vector2 start_pos, Vector2 toward_pos, // i.e. bloon pos
                         float major_axis, float eccentricity,
                         float period, float spinning_period) {
        super(game_screen, region, start_pos, pierce_cnt);

        this.start_pos = start_pos.cpy();
        this.delta = toward_pos.cpy().sub(start_pos);
        this.a = major_axis * 0.5F;
        this.b = (float) Math.sqrt(a*a * (1F - eccentricity * eccentricity));
        this.ang_v = 360 / period;
        this.spinning_ang_v = 360 / spinning_period;
        this.period = period;
        elapsed_time = 0;
//        setScale(0.5F);

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
        // boomerang itself's rotation
        elapsed_time += delta;
        setRotation(elapsed_time * spinning_ang_v);

        // boomerang
        Vector2 pos = new Vector2(0,1).setAngleDeg(elapsed_time * ang_v)
                .scl(a, b).sub(a, 0);
        pos.setAngleDeg(pos.angleDeg() + 180 + this.delta.angleDeg())
                .add(start_pos);
        setCoords(pos);

        if(elapsed_time > period)
            setAliveState(false);
    }
    public boolean touched(Bloon bloon) {
        return bloon.getCoords().dst(getCoords()) <= bloon.getTouchRadius();
    }
    public void hit(Bloon bloon) {
        // note that `pierce_cnt - 1` is done in Dart::act
        // only need to pop whole lot of bloons here
        bloon.pop(this);
    }
}
