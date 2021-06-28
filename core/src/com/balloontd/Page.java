package com.balloontd;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.balloontd.menu.*;

import java.util.ArrayList;

public class Page extends Group{
    private BalloonTD balloonTD;
    private MenuScreen menuScreen;
    private ArrayList<TextureRegion> allRegions;
    private int nowPage;

    private Button nextPageButton;
    private Button previousPageButton;
    private Button closeButton;

    public Page(BalloonTD balloonTD, ArrayList<TextureRegion> regions, MenuScreen menuScreen) {
        this.balloonTD = balloonTD;
        this.allRegions = regions;
        nowPage = 0;
        this.menuScreen = menuScreen;

        makeNextPageButton(this);
        makePreviousPageButton(this);
        makeCloseButton(this);
    }

    private Button.ButtonStyle makeStyle(Texture upTexture, Texture onTexture){
        Button.ButtonStyle style = new Button.ButtonStyle();
        style.up = new TextureRegionDrawable(upTexture);
        style.down = new TextureRegionDrawable(onTexture);

        return style;
    }
    private void makeNextPageButton(final Page page){
        Button.ButtonStyle style = makeStyle(new Texture("page-next.png"),
                new Texture("page-next-down.png")   );

        nextPageButton = new Button(style);
        nextPageButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                page.goToNextPage();
            }
        });

        nextPageButton.setPosition(200, 100);
        menuScreen.addActor(nextPageButton);
    }

    private void makePreviousPageButton(final Page page){
        Button.ButtonStyle style = makeStyle(new Texture("page-previous.png"),
                new Texture("page-previous-down.png")   );

        previousPageButton = new Button(style);
        previousPageButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                page.goToPreviousPage();
            }
        });
        previousPageButton.setPosition(0, 0);
        menuScreen.addActor(previousPageButton);
    }

    private void makeCloseButton(final Page page){
        Button.ButtonStyle style = makeStyle(new Texture("page-close.png"),
                new Texture("page-close.png")   );

        closeButton = new Button(style);
        closeButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                page.setVisible(false);
            }
        });
        closeButton.setPosition(400, 300);
        menuScreen.addActor(closeButton);
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

        if (allRegions.size() == 0 || !isVisible()) {
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

        closeButton.draw(batch, parentAlpha);
    }

}
