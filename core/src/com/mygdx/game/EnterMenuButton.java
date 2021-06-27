package com.balloontd;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class EnterMenuButton extends Button{

    public EnterMenuButton(BalloonTD balloonTD, TextureRegion region, ClickListener clickListener){
        super(balloonTD, region, clickListener);
    }

    public void beingClicked(){
        balloonTD.enterMenuScreen();
    }
}
