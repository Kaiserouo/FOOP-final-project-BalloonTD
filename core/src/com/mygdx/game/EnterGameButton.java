package com.balloontd;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class EnterGameButton extends Button{
    public EnterGameButton(BalloonTD balloonTD, TextureRegion region, ClickListener clickListener){
        super(balloonTD, region, clickListener);
    }

    public void beingClicked(){
        balloonTD.enterGameScreen();
    }
}
