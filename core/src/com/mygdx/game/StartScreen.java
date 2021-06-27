package com.balloontd;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;


public class StartScreen implements Screen{
    private BalloonTD balloonTD;

    private Stage stage;
    private Texture logoTexture;
    private MyActor logoActor;

    private Texture playButtonTexture;
    private EnterMenuButton playButton;


    public StartScreen(BalloonTD balloonTD){
        this.balloonTD = balloonTD;

        stage = new Stage(new StretchViewport(BalloonTD.WORLD_WIDTH, BalloonTD.WORLD_HEIGHT));

        Gdx.input.setInputProcessor(stage);

        logoTexture = new Texture("bloons-td-logo.png");
        logoActor = new MyActor(new TextureRegion(logoTexture));
        logoActor.setPosition(0, 0);
        logoActor.setSize((float)BalloonTD.WORLD_WIDTH, (float)BalloonTD.WORLD_HEIGHT);

        playButtonTexture = new Texture("start-screen-play-button.png");
        playButton = new EnterMenuButton(balloonTD, new TextureRegion(playButtonTexture), new ButtonListener());
        playButton.setPosition(
                stage.getWidth() *  0.4F -  playButton.getWidth()/2,
                stage.getHeight() * 0.17F - playButton.getHeight()/2
                );
        playButton.setSize(playButton.getWidth() * 2F,
                playButton.getHeight() * 2F);

        stage.addActor(logoActor);
        stage.addActor(playButton);

    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.75F, 1, 0.98F, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
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
