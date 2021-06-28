package com.balloontd.menu;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.balloontd.*;

import java.util.ArrayList;

public class MenuScreen implements Screen{
    private BalloonTD balloonTD;
    private Stage stage;

    private StartGameButton startGameButton;

    private ExitGameButton exitGameButton;

    private OpenPageButton instructionButton;
    private Page instructionPage;

    private OpenPageButton bmdListButton;
    private Page bmdListPage;


    public MenuScreen(BalloonTD balloonTD){
        this.balloonTD = balloonTD;

        stage = new Stage(new StretchViewport(BalloonTD.WORLD_WIDTH, BalloonTD.WORLD_HEIGHT));

        Gdx.input.setInputProcessor(stage);

        makeStartGameButton();
        makeExitGameButton();

        makeInstructionPage();
        makeInstructionButton(instructionPage);

        makeBmdListPage();
        makeBmdListButton(bmdListPage);
    }

    private Button.ButtonStyle makeStyle(Texture upTexture, Texture downTexture){
        Button.ButtonStyle style = new Button.ButtonStyle();
        style.up = new TextureRegionDrawable(upTexture);
        style.down = new TextureRegionDrawable(downTexture);
        return style;
    }

    private void makeStartGameButton(){
        Button.ButtonStyle style = makeStyle(new Texture(""),
                                             new Texture(""));

        startGameButton = new StartGameButton(balloonTD, style);
        stage.addActor(startGameButton);
    }

    private void makeExitGameButton(){
        Button.ButtonStyle style = makeStyle(new Texture(""),
                new Texture(""));

        exitGameButton = new ExitGameButton(balloonTD, style);
        stage.addActor(exitGameButton);
    }

    private void makeInstructionPage(){
        ArrayList<TextureRegion> regions = new ArrayList<TextureRegion>();

        // maybe be multiple
        regions.add(new TextureRegion(new Texture("")));

        instructionPage = new Page(balloonTD, regions, this);
        instructionPage.setVisible(false);
        stage.addActor(instructionPage);
    }
    private void makeInstructionButton(Page page){
        Button.ButtonStyle style = makeStyle(new Texture(""),
                new Texture(""));

        instructionButton = new OpenPageButton(style, page);
        stage.addActor(instructionButton);
    }

    private void makeBmdListPage(){
        ArrayList<TextureRegion> regions = new ArrayList<TextureRegion>();

        //maybe be multiple
        regions.add(new TextureRegion(new Texture("")));

        bmdListPage = new Page(balloonTD, regions, this);
        bmdListPage.setVisible(false);
        stage.addActor(bmdListPage);
    }
    private void makeBmdListButton(Page page){
        Button.ButtonStyle style = makeStyle(new Texture(""),
                new Texture(""));

        bmdListButton = new OpenPageButton(style, page);
        stage.addActor(bmdListButton);
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
    }



}
