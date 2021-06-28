package com.balloontd;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.balloontd.menu.*;

import java.util.ArrayList;

public class Page extends Actor {
    private BalloonTD balloonTD;
    private MenuScreen menuScreen;
    private ArrayList<TextureRegion> allRegions;
    private int nowPage;

    private NextPageButton nextPageButton;
    private PreviousPageButton previousPageButton;
    private CloseButton closeButton;

    public Page(BalloonTD balloonTD, ArrayList<TextureRegion> regions, MenuScreen menuScreen) {
        this.balloonTD = balloonTD;
        this.allRegions = regions;
        nowPage = 0;
        this.menuScreen = menuScreen;

        makeNextPageButton();
        makePreviousPageButton();
        makeCloseButton();
    }

    private void makeNextPageButton(){
        Texture buttonUpTexture = new Texture("");
        Texture buttonDownTexture = new Texture("");

        Button.ButtonStyle style = new Button.ButtonStyle();
        style.up = new TextureRegionDrawable(buttonUpTexture);
        style.down = new TextureRegionDrawable(buttonDownTexture);

        nextPageButton = new NextPageButton(style, this);
    }

    private void makePreviousPageButton(){
        Texture buttonUpTexture = new Texture("");
        Texture buttonDownTexture = new Texture("");

        Button.ButtonStyle style = new Button.ButtonStyle();
        style.up = new TextureRegionDrawable(buttonUpTexture);
        style.down = new TextureRegionDrawable(buttonDownTexture);

        previousPageButton = new PreviousPageButton(style, this);
    }

    private void makeCloseButton(){
        Texture buttonUpTexture = new Texture("");
        Texture buttonDownTexture = new Texture("");

        Button.ButtonStyle style = new Button.ButtonStyle();
        style.up = new TextureRegionDrawable(buttonUpTexture);
        style.down = new TextureRegionDrawable(buttonDownTexture);

        closeButton = new CloseButton(style, this);
    }

    private void goToNextPage(){
        nowPage++;
    }

    private void goToPreviousPage(){
        nowPage--;
    }


    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if (allRegions.get(nowPage) == null || !isVisible()) {
            return;
        }

        batch.draw(
                allRegions.get(nowPage),
                getX(), getY(),
                getOriginX(), getOriginY(),
                getWidth(), getHeight(),
                getScaleX(), getScaleY(),
                getRotation()
        );

        if(nowPage != 0){
            previousPageButton.draw(batch, parentAlpha);
        }

        if(nowPage != allRegions.size()-1){
            nextPageButton.draw(batch, parentAlpha);
        }

        nextPageButton.draw(batch, parentAlpha);
    }


    // Inner Class: NextPageButton
    // To go to the next page(If can)
    private class NextPageButton extends Button {

        public NextPageButton(ButtonStyle style, final Page page) {
            super(style);

            addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y){
                    page.goToNextPage();
                }
            });
        }
    }

    // Inner Class: PreviousPageButton
    // To go to the previous page(If can)
    private class PreviousPageButton extends Button {
        public PreviousPageButton(ButtonStyle style, final Page page) {
            super(style);

            addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y){
                    page.goToPreviousPage();
                }
            });
        }
    }

    // Inner class: CloseButton
    // To close the page
    private class CloseButton extends Button {
        public CloseButton(ButtonStyle style, final Page page) {
            super(style);

            addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y){
                    page.setVisible(false);
                    event.getListenerActor().setVisible(false);
                }
            });
        }
    }
}
