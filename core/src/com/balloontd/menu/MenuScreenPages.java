package com.balloontd.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.balloontd.BalloonTD;

import java.util.ArrayList;

public class MenuScreenPages extends Actor {
    private MenuScreen menuScreen;

    private int pageOpen;
    public final static int PAGE_CLOSE = 0, INSTRUCTION_PAGE = 1, BMDLIST_PAGE = 2;

    private Button nextPageButton, previousPageButton, closePageButton;
    private ArrayList<Image> instructionPages;
    private ArrayList<Image> bmdListPages;
    private int nowPage = 0;

    public MenuScreenPages(MenuScreen menuScreen){
        this.menuScreen = menuScreen;

        pageOpen = PAGE_CLOSE;
        nowPage = 0;
        makeInstructionPages();
        makeBmdListPages();
        makeClosePageButton();
        makeNextPageButton();
        makePreviousPageButton();
    }

    private Image makePage(TextureRegion region){
        Image page = new Image(region);
        menuScreen.pageStageAddActor(page);
        page.setSize(page.getWidth() * 1.5F, page.getHeight() * 1.5F);
        page.setPosition(BalloonTD.WORLD_WIDTH/2 - page.getWidth()/2,
                BalloonTD.WORLD_HEIGHT/2 - page.getHeight()/2);
        return page;
    }

    private void makeInstructionPages(){
        instructionPages = new ArrayList<Image>();

        for(int i=1 ; i<=3;  i++) {
            instructionPages.add(makePage(new TextureRegion(new Texture("introduction/help_" + i + ".png"))));
        }
    }

    private void makeBmdListPages(){
        bmdListPages = new ArrayList<Image>();

        for(int i=1 ; i<=7;  i++) {
            bmdListPages.add(makePage(new TextureRegion(new Texture("introduction/list_" + i + ".png"))));
        }
    }

    private Button.ButtonStyle makeStyle(Texture upTexture, Texture downTexture){
        Button.ButtonStyle style = new Button.ButtonStyle();
        style.up = new TextureRegionDrawable(upTexture);
        style.over = new TextureRegionDrawable(downTexture);
        return style;
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
        menuScreen.pageStageAddActor(nextPageButton);

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
        menuScreen.pageStageAddActor(previousPageButton);

        previousPageButton.setPosition(160, 100);
    }

    private void makeClosePageButton(){
        Button.ButtonStyle style = makeStyle(new Texture("page-close.png"),
                new Texture("page-close-down.png"));

        closePageButton = new Button(style);
        closePageButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pageOpen = PAGE_CLOSE;
                menuScreen.stageSetInputProcessor();
            }
        });
        menuScreen.pageStageAddActor(closePageButton);

        closePageButton.setPosition(900, 500);
    }

    private void setButtonsVisibility(){
        if(nowPage == 0){
            previousPageButton.setVisible(false);
        }else{
            previousPageButton.setVisible(true);
            previousPageButton.toFront();
        }

        int totalPage = 0;
        if(pageOpen == INSTRUCTION_PAGE){
            totalPage = instructionPages.size();
        }else if(pageOpen == BMDLIST_PAGE){
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

    public void setPageOpen(int pageState){ pageOpen = pageState; }
    public int getPageOpen(){ return pageOpen; }
    public void setNowPage(int nowPage){ this.nowPage = nowPage; }
    public void setInstructionPageVisible(boolean visible){ instructionPages.get(nowPage).setVisible(visible); }
    public void setBmdListPageVisible(boolean visible){ bmdListPages.get(nowPage).setVisible(visible); }

    @Override
    public void act(float delta){
        super.act(delta);
        if(pageOpen == INSTRUCTION_PAGE) {
            instructionPages.get(nowPage).toFront();
        }else{
            bmdListPages.get(nowPage).toFront();
        }
        setButtonsVisibility();
    }

    @Override
    public void draw(Batch batch, float parentAlpha){ super.draw(batch, parentAlpha); }
}
