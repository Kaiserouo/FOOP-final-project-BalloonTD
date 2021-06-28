package com.balloontd.menu;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
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

    private Button startGameButton;

    private Button exitGameButton;

    private Button instructionButton;
    private Page instructionPage;

    private Button bmdListButton;
    private Page bmdListPage;


    public MenuScreen(BalloonTD balloonTD){
        this.balloonTD = balloonTD;

        stage = new Stage(new StretchViewport(BalloonTD.WORLD_WIDTH, BalloonTD.WORLD_HEIGHT));
        //Gdx.input.setInputProcessor(stage);

        Texture backgroundTexture = new Texture("menu-background.png");
        background = new Image(new TextureRegion(backgroundTexture));
        stage.addActor(background);

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
        style.over = new TextureRegionDrawable(downTexture);
        return style;
    }

    private void makeStartGameButton(){
        Button.ButtonStyle style = makeStyle(new Texture("menu-start-button.png"),
                                             new Texture("menu-start-button-down.png"));

        startGameButton = new Button(style);
        startGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                balloonTD.enterGameScreen();
            }
        });
        stage.addActor(startGameButton);

        startGameButton.setPosition(0, 180);
        startGameButton.setSize(startGameButton.getWidth() * 1.5F, startGameButton.getHeight() * 1.5F);
    }

    private void makeExitGameButton(){
        Button.ButtonStyle style = makeStyle(new Texture("menu-exit-button.png"),
                new Texture("menu-exit-button-down.png"));

        exitGameButton = new Button(style);
        exitGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                balloonTD.dispose();
            }
        });
        stage.addActor(exitGameButton);

        exitGameButton.setPosition(780, 40);
        exitGameButton.setSize(exitGameButton.getWidth() * 1.8F, exitGameButton.getHeight() * 1.8F);
    }

    private void makeInstructionPage(){
        ArrayList<TextureRegion> regions = new ArrayList<TextureRegion>();

        // maybe be multiple
        regions.add(new TextureRegion(new Texture("page.png")));

        instructionPage = new Page(balloonTD, regions, this);
        stage.addActor(instructionPage);

        instructionPage.setPosition(stage.getWidth()/2 - instructionPage.getWidth()/2,
                stage.getHeight()/2 - instructionPage.getHeight()/2);
        //instructionPage.setVisible(false);
    }
    private void makeInstructionButton(final Page page){
        Button.ButtonStyle style = makeStyle(new Texture("menu-instruction.png"),
                new Texture("menu-instruction-down.png"));

        instructionButton = new Button(style);
        instructionButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                instructionPage.setVisible(true);
                instructionPage.toFront();
            }
        });
        stage.addActor(instructionButton);

        instructionButton.setPosition(820, 335);
        instructionButton.setSize(instructionButton.getWidth() * 1.8F, instructionButton.getHeight() * 1.8F);
    }

    private void makeBmdListPage(){
        ArrayList<TextureRegion> regions = new ArrayList<TextureRegion>();

        //maybe be multiple
        regions.add(new TextureRegion(new Texture("page.png")));

        bmdListPage = new Page(balloonTD, regions, this);
    }
    private void makeBmdListButton(final Page page){
        Button.ButtonStyle style = makeStyle(new Texture("menu-bmdList.png"),
                new Texture("menu-bmdList-down.png"));

        bmdListButton = new Button(style);
        bmdListButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                stage.addActor(page);
            }
        });
        stage.addActor(bmdListButton);

        bmdListButton.setPosition(30, 495);
        bmdListButton.setSize(bmdListButton.getWidth() * 1.6F, bmdListButton.getHeight() * 1.6F);
    }

    public void addActor(Actor actor){
        stage.addActor(actor);
    }


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
    }


    @Override
    public void dispose() {
        if (stage != null) {
            stage.dispose();
        }
    }

}
