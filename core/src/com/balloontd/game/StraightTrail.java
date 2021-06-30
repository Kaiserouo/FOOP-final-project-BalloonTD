package com.balloontd.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.balloontd.BalloonTD;

public class StraightTrail implements Trail{
    private Vector2 start_point;
    private Vector2 end_point;

    private float length;
    private Vector2 delta_vec;

//    private Texture texture;

    public StraightTrail(Vector2 start_point, Vector2 end_point) {
        Gdx.app.log("trail", "" + start_point + " " + end_point);
        if(start_point.equals(end_point))
            throw new RuntimeException("StraightTrail have length 0: " + start_point + " " + end_point);

        this.start_point = start_point.cpy();
        this.end_point = end_point.cpy();

        length = this.start_point.dst(this.end_point);
        delta_vec = this.end_point.cpy().sub(this.start_point);

//        Pixmap pixmap = new Pixmap(BalloonTD.WORLD_WIDTH, BalloonTD.WORLD_HEIGHT, Pixmap.Format.RGBA8888);
//        pixmap.setColor(0,0,0,1);
//        pixmap.drawLine(
//                (int) this.start_point.x,
//                (int) (BalloonTD.WORLD_HEIGHT-this.start_point.y),
//                (int) this.end_point.x,
//                (int) (BalloonTD.WORLD_HEIGHT-this.end_point.y)
//        );
//        texture = new Texture(pixmap);
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
//        batch.draw(texture,0, 0);
    }
    public Vector2 getStartPoint() {
        return new Vector2(start_point);
    }
    public Vector2 getEndPoint() {
        return new Vector2(end_point);
    }

    @Override
    public boolean checkIntersectCircle(Vector2 coord, float radius) {
        return Intersector.intersectSegmentCircle(
                start_point, end_point,
                coord, radius * radius
        );
    }
}