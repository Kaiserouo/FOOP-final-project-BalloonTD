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

    private Button startGameButton;
    private Button exitGameButton;
    private Button instructionButton;
    private Button bmdListButton;


    private Stage pageStage;
    private boolean instructionPageOpen, bmdListPageOpen;
    private Button nextPageButton, previousPageButton, closePageButton;
    private ArrayList<Image> instructionPages;
    private ArrayList<Image> bmdListPages;
    private int nowPage = 0;

    public MenuScreen(BalloonTD balloonTD){
        this.balloonTD = balloonTD;

        stage = new Stage(new StretchViewport(BalloonTD.WORLD_WIDTH, BalloonTD.WORLD_HEIGHT));

        Texture backgroundTexture = new Texture("menu-background.png");
        background = new Image(new TextureRegion(backgroundTexture));
        stage.addActor(background);

        makeStartGameButton();
        makeExitGameButton();
        makeInstructionButton();
        makeBmdListButton();


        pageStage = new Stage(new StretchViewport(BalloonTD.WORLD_WIDTH, BalloonTD.WORLD_HEIGHT));
        instructionPageOpen = false; bmdListPageOpen = false;
        nowPage = 0;
        makeInstructionPages();
        makeBmdListPages();
        makeClosePageButton();
        makeNextPageButton();
        makePreviousPageButton();

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
                Gdx.app.exit();
            }
        });
        stage.addActor(exitGameButton);

        exitGameButton.setPosition(780, 40);
        exitGameButton.setSize(exitGameButton.getWidth() * 1.8F, exitGameButton.getHeight() * 1.8F);
    }

    private void makeInstructionButton(){
        Button.ButtonStyle style = makeStyle(new Texture("menu-instruction.png"),
                new Texture("menu-instruction-down.png"));

        instructionButton = new Button(style);
        instructionButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                instructionPageOpen = true;
                Gdx.input.setInputProcessor(pageStage);
                nowPage = 0;
                instructionPages.get(nowPage).setVisible(true);
            }
        });
        stage.addActor(instructionButton);

        instructionButton.setPosition(820, 335);
        instructionButton.setSize(instructionButton.getWidth() * 1.8F, instructionButton.getHeight() * 1.8F);
    }

    private void makeBmdListButton(){
        Button.ButtonStyle style = makeStyle(new Texture("menu-bmdList.png"),
                new Texture("menu-bmdList-down.png"));

        bmdListButton = new Button(style);
        bmdListButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                bmdListPageOpen = true;
                Gdx.input.setInputProcessor(pageStage);
                nowPage = 0;
                bmdListPages.get(nowPage).setVisible(true);
            }
        });
        stage.addActor(bmdListButton);

        bmdListButton.setPosition(30, 495);
        bmdListButton.setSize(bmdListButton.getWidth() * 1.6F, bmdListButton.getHeight() * 1.6F);
    }


    private Image makePage(TextureRegion region){
        Image page = new Image(region);
        pageStage.addActor(page);
        page.setSize(page.getWidth() * 1.5F, page.getHeight() * 1.5F);
        page.setPosition(pageStage.getWidth()/2 - page.getWidth()/2,
                pageStage.getHeight()/2 - page.getHeight()/2);
        return page;
    }
    private void makeInstructionPages(){
        instructionPages = new ArrayList<Image>();
        // maybe be multiple
        for(int i=1 ; i<=3;  i++) {
            instructionPages.add(makePage(new TextureRegion(new Texture("introduction/help_" + i + ".png"))));
        }
    }

    private void makeBmdListPages(){
        bmdListPages = new ArrayList<Image>();
        //maybe be multiple
        for(int i=1 ; i<=7;  i++) {
            bmdListPages.add(makePage(new TextureRegion(new Texture("introduction/list_" + i + ".png"))));
        }
    }

    private void makeNextPageButton(){
        Button.ButtonStyle style = makeStyle(new Texture("page-next.png"),
                new Texture("page-next-down.png"));

        nextPageButton = new Button(style);
        nextPageButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                nowPage++;
            }
        });
        pageStage.addActor(nextPageButton);
        //nextPageButton.setVisible(false);

        nextPageButton.setPosition(890, 100);
    }

    private void makePreviousPageButton(){
        Button.ButtonStyle style = makeStyle(new Texture("page-previous.png"),
                new Texture("page-previous-down.png"));

        previousPageButton = new Button(style);
            previousPageButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                nowPage--;
            }
        });
            pageStage.addActor(previousPageButton);
            //previousPageButton.setVisible(false);

            previousPageButton.setPosition(160, 100);
    }

    private void makeClosePageButton(){
        Button.ButtonStyle style = makeStyle(new Texture("page-close.png"),
                new Texture("page-close-down.png"));

        closePageButton = new Button(style);
        closePageButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                instructionPageOpen = false;
                bmdListPageOpen = false;
                Gdx.input.setInputProcessor(stage);
            }
        });
        pageStage.addActor(closePageButton);

        closePageButton.setPosition(900, 500);
    }

    private void setButtonsVisibility(){
        if(nowPage == 0){
            previousPageButton.setVisible(false);
        }else{
            previousPageButton.setVisible(true);
            previousPageButton.toFront();
        }

        int totalPage;
        if(instructionPageOpen){
            totalPage = instructionPages.size();
        }else{
            totalPage = bmdListPages.size();
        }

        if(nowPage == totalPage - 1){
            nextPageButton.setVisible(false);
        }else{
            nextPageButton.setVisible(true);
            nextPageButton.toFront();
        }

        closePageButton.toFront();
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
        if(instructionPageOpen || bmdListPageOpen){
            if(instructionPageOpen) {
                instructionPages.get(nowPage).toFront();
            }else{
                bmdListPages.get(nowPage).toFront();
            }
            setButtonsVisibility();
            pageStage.act();
            pageStage.draw();
        }
    }


    @Override
    public void dispose() {
        if (stage != null) {
            stage.dispose();
        }
    }

}
