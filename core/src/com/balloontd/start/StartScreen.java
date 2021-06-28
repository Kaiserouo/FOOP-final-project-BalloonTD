package com.balloontd.start;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.balloontd.*;


public class StartScreen extends ScreenAdapter {
    private BalloonTD balloonTD;

    private Stage stage;
    private Texture logoTexture;
    private Image logoImage;

    private Texture playButtonUpTexture, playButtonDownTexture;
    private EnterMenuButton playButton;


    public StartScreen(BalloonTD balloonTD){
        this.balloonTD = balloonTD;

        stage = new Stage(new StretchViewport(BalloonTD.WORLD_WIDTH, BalloonTD.WORLD_HEIGHT));

        Gdx.input.setInputProcessor(stage);

        logoTexture = new Texture("bloons-td-logo.png");
        logoImage = new Image(new TextureRegion(logoTexture));
        logoImage.setPosition(0, 0);
        logoImage.setSize((float)BalloonTD.WORLD_WIDTH, (float)BalloonTD.WORLD_HEIGHT);

        makePlayButton();


        stage.addActor(logoImage);
        stage.addActor(playButton);

    }

    private void makePlayButton(){
        playButtonUpTexture = new Texture("start-screen-play-button.png");
        playButtonDownTexture = new Texture("start-screen-play-button-down.png");

        Button.ButtonStyle style = new Button.ButtonStyle();
        style.up = new TextureRegionDrawable(playButtonUpTexture);
        style.down = new TextureRegionDrawable(playButtonDownTexture);

        playButton = new EnterMenuButton(balloonTD, style);
        playButton.setPosition(
                stage.getWidth() *  0.4F -  playButton.getWidth()/2,
                stage.getHeight() * 0.17F - playButton.getHeight()/2
        );
        playButton.setSize(playButton.getWidth() * 2F,
                playButton.getHeight() * 2F);
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
        if (logoTexture != null) {
            logoTexture.dispose();
        }
    }

}
