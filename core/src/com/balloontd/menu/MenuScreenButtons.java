package com.balloontd.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MenuScreenButtons extends Actor {
    MenuScreen menuScreen;

    private Button startGameButton;
    private Button exitGameButton;
    private Button instructionButton;
    private Button bmdListButton;

    public MenuScreenButtons(MenuScreen menuScreen){
        this.menuScreen = menuScreen;

        makeStartGameButton();
        makeExitGameButton();
        makeInstructionButton();
        makeBmdListButton();
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
                menuScreen.getBalloonTD().enterGameScreen();
            }
        });
        menuScreen.stageAddActor(startGameButton);

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
        menuScreen.stageAddActor(exitGameButton);

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
                MenuScreenPages menuScreenPages = menuScreen.getMenuScreenPages();
                menuScreenPages.setPageOpen(MenuScreenPages.INSTRUCTION_PAGE);
                menuScreen.pageStageSetInputProcessor();
                menuScreenPages.setNowPage(0);
                menuScreenPages.setInstructionPageVisible(true);
            }
        });
        menuScreen.stageAddActor(instructionButton);

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
                MenuScreenPages menuScreenPages = menuScreen.getMenuScreenPages();
                menuScreenPages.setPageOpen(MenuScreenPages.BMDLIST_PAGE);
                menuScreen.pageStageSetInputProcessor();
                menuScreenPages.setNowPage(0);
                menuScreenPages.setBmdListPageVisible(true);
            }
        });
        menuScreen.stageAddActor(bmdListButton);

        bmdListButton.setPosition(30, 495);
        bmdListButton.setSize(bmdListButton.getWidth() * 1.6F, bmdListButton.getHeight() * 1.6F);
    }

    @Override
    public void act(float delta){ super.act(delta); }

    @Override
    public void draw(Batch batch, float parentAlpha){ super.draw(batch, parentAlpha); }
}
