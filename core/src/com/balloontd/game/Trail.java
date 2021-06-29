package com.balloontd.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

public interface Trail {
    public float getTotalLength();
    public Vector2 getCoordinate(float dist);
    public void draw(Batch batch, float parentAlpha);
    public Vector2 getStartPoint();
    public Vector2 getEndPoint();
    public boolean checkIntersectCircle(Vector2 coord, float radius);
}
