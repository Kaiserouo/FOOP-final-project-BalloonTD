package com.balloontd.start;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.balloontd.*;


public class StartScreen extends ScreenAdapter{
    private final BalloonTD balloonTD;

    private Stage stage;
    private Texture backgroundTexture;
    private Image backgroundImage;

    private  StartScreenButtons startScreenButtons;
    public StartScreen(BalloonTD balloonTD){
        this.balloonTD = balloonTD;

        stage = new Stage(new StretchViewport(BalloonTD.WORLD_WIDTH, BalloonTD.WORLD_HEIGHT));

        makeBackground();

        startScreenButtons = new StartScreenButtons(this);
    }

    private void makeBackground(){
        backgroundTexture = new Texture("bloons-td-logo.png");
        backgroundImage = new Image(new TextureRegion(backgroundTexture));
        backgroundImage.setPosition(0, 0);
        backgroundImage.setSize((float)BalloonTD.WORLD_WIDTH, (float)BalloonTD.WORLD_HEIGHT);
        stage.addActor(backgroundImage);
    }

    public void addActor(Actor actor){ stage.addActor(actor); }

    public BalloonTD getBalloonTD(){ return  balloonTD; }


    @Override
    public void show(){
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.75F, 1, 0.98F, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }




    @Override
    public void dispose() {
        if (stage != null) {
            stage.dispose();
        }
        if (backgroundTexture != null) {
            backgroundTexture.dispose();
        }
        if(startScreenButtons != null){
            startScreenButtons.dispose();
        }

    }

}
