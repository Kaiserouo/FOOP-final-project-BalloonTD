package com.balloontd.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

public class StraightTrail implements Trail{
    private Vector2 start_point;
    private Vector2 end_point;

    private float length;
    private Vector2 delta_vec;

    public StraightTrail(Vector2 start_point, Vector2 end_point) {
        if(start_point.equals(end_point))
            throw new RuntimeException("StraightTrail have length 0.");

        this.start_point = new Vector2(start_point);
        this.end_point = new Vector2(end_point);

        length = start_point.dst(end_point);
        delta_vec = end_point.sub(start_point);
    }

    public float getTotalLength() {
        return length;
    }

    public Vector2 getCoordinate(float dist) {
        if(dist < 0) return getStartPoint();
        if(dist > getTotalLength()) return getEndPoint();
        return new Vector2(start_point).lerp(
                end_point, dist / getTotalLength()
        );
    }
    public void draw(Batch batch, float parentAlpha) {
        // don't draw anything for now
        return;
    }
    public Vector2 getStartPoint() {
        return new Vector2(start_point);
    }
    public Vector2 getEndPoint() {
        return new Vector2(end_point);
    }

}