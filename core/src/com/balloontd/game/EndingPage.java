package com.balloontd.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.balloontd.BalloonTD;

public class EndingPage extends Actor {
    GameScreen gameScreen;
    private Texture backgroundTexture;
    private Image backgroundImage;

    private Texture returnToMenuTextureUp, returnToMenuTextureOn;
    private Button returnToMenuButton;

    private Texture pageTexture;
    private Image page;

    private BitmapFont font;
    private String info;

    private boolean is_win;


    public EndingPage(GameScreen gameScreen){
        this.gameScreen = gameScreen;
        backgroundTexture = new Texture("ending-background.png");
        backgroundImage = new Image(new TextureRegion(backgroundTexture));
        backgroundImage.setPosition(0, 0);

        pageTexture = new Texture("page.png");
        page = new Image(new TextureRegion(pageTexture));
        page.setPosition(BalloonTD.WORLD_WIDTH/2 - page.getWidth()/2,
                BalloonTD.WORLD_HEIGHT/2 - page.getHeight()/2);
        makeButton();

        font = new BitmapFont(Gdx.files.internal("font/ComicSansMS.fnt"));
    }

    private void makeButton(){
        returnToMenuTextureUp = new Texture("ending-button-up.png");
        returnToMenuTextureOn = new Texture("ending-button-on.png");

        Button.ButtonStyle style = new Button.ButtonStyle();
        style.up = new TextureRegionDrawable(new TextureRegion(returnToMenuTextureUp));
        style.over = new TextureRegionDrawable(new TextureRegion(returnToMenuTextureOn));

        returnToMenuButton = new Button(style);
        returnToMenuButton.setPosition(BalloonTD.WORLD_WIDTH/2 - returnToMenuButton.getWidth()/2 ,
                page.getY() + 50);


        returnToMenuButton.addListener(new ClickListener(){
           @Override
           public void clicked(InputEvent event, float x, float y){
               gameScreen.getBalloonTD().leaveGameScreen();
           }
        });
    }

    public void gameOver(boolean is_win){
        this.is_win = is_win;
        gameScreen.addActor(this);
        gameScreen.addActor(returnToMenuButton);
        if(is_win) {
            info = "You win";
        }else{
            info = "You lose";
        }

        returnToMenuButton.setVisible(true);
    }

    @Override
    public void act(float delta){
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        super.draw(batch, parentAlpha);

        if(this.isVisible()){
            backgroundImage.draw(batch, parentAlpha);
            page.draw(batch, parentAlpha);
            font.draw(batch, info, BalloonTD.WORLD_WIDTH/2 - 50, BalloonTD.WORLD_HEIGHT/2 + 20);
        }
    }

    public void dispose(){
        if(backgroundTexture != null) {
            backgroundTexture.dispose();
        }
        if(returnToMenuTextureOn != null) {
            returnToMenuTextureOn.dispose();
        }
        if(returnToMenuTextureUp != null){
            returnToMenuTextureUp.dispose();
        }
        if(pageTexture != null){
            pageTexture.dispose();
        }
        if(font != null){
            font.dispose();
        }
    }
}
