package com.balloontd.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.balloontd.BalloonTD;
import com.balloontd.ButtonListener;
import com.balloontd.start.EnterMenuButton;


public class GameScreen implements Screen{
    private MonkeyManager monkey_manager;
    private DartManager dart_manager;
    private BloonManager bloon_manager;

    private Trail trail;

    private Stage stage;

    public GameScreen(BalloonTD balloonTD){
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
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
    }
}
