package com.balloontd;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import  com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public abstract class Button extends Actor{
    protected TextureRegion region;
    protected BalloonTD balloonTD;

    public Button(BalloonTD balloonTD, TextureRegion region, ClickListener clickListener){
        super();
        this.balloonTD = balloonTD;
        this.region = region;

        setSize(this.region.getRegionWidth(), this.region.getRegionHeight());

        addListener(clickListener);
    }

    public abstract void beingClicked();

    public void cursorIsOn(){
        setScale(1.07F);
    }

    public void cursorIsOff(){
        setScale(1.0F/1.07F);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if (region == null || !isVisible()) {
            return;
        }

        batch.draw(
                region,
                getX(), getY(),
                getOriginX(), getOriginY(),
                getWidth(), getHeight(),
                getScaleX(), getScaleY(),
                getRotation()
        );
    }
}
