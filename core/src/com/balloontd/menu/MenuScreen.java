package com.balloontd.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.balloontd.*;

import java.util.ArrayList;

public class MenuScreen extends ScreenAdapter {
    private BalloonTD balloonTD;
    private Stage stage;
    private Image background;

    private MenuScreenButtons menuScreenButtons;

    private MenuScreenPages menuScreenPages;
    private Stage pageStage;


    public MenuScreen(BalloonTD balloonTD){
        this.balloonTD = balloonTD;

        stage = new Stage(new StretchViewport(BalloonTD.WORLD_WIDTH, BalloonTD.WORLD_HEIGHT));

        Texture backgroundTexture = new Texture("menu-background.png");
        background = new Image(new TextureRegion(backgroundTexture));
        stage.addActor(background);

        menuScreenButtons = new MenuScreenButtons(this);
        stage.addActor(menuScreenButtons);

        pageStage = new Stage(new StretchViewport(BalloonTD.WORLD_WIDTH, BalloonTD.WORLD_HEIGHT));
        menuScreenPages = new MenuScreenPages(this);
        pageStage.addActor(menuScreenPages);
    }

    public BalloonTD getBalloonTD(){ return balloonTD; }

    public void stageAddActor(Actor actor){ stage.addActor(actor);}

    public void pageStageAddActor(Actor actor){ pageStage.addActor(actor);}

    public void pageStageSetInputProcessor(){ Gdx.input.setInputProcessor(pageStage); }

    public void stageSetInputProcessor(){ Gdx.input.setInputProcessor(stage); }

    public MenuScreenPages getMenuScreenPages(){ return  menuScreenPages; }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.75F, 1, 0.98F, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
        if(menuScreenPages.getPageOpen() != MenuScreenPages.PAGE_CLOSE){
            pageStage.act();
            pageStage.draw();
        }
    }


    @Override
    public void dispose() {
        if (stage != null) {
            stage.dispose();
        }
        if(pageStage != null){
            pageStage.dispose();
        }
    }

}
