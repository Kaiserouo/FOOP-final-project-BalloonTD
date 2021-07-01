package com.balloontd.start;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.balloontd.BalloonTD;

public class StartScreenButtons extends Actor {
    private StartScreen startScreen;
    private Texture playButtonUpTexture;
    private Texture playButtonOnTexture;
    private Button playButton;

    public StartScreenButtons(StartScreen startScreen){
        this.startScreen = startScreen;
        makePlayButton();

    }

    private void makePlayButton(){
        playButtonUpTexture = new Texture("start-screen-play-button.png");
        playButtonOnTexture = new Texture("start-screen-play-button-down.png");

        Button.ButtonStyle style = new Button.ButtonStyle();
        style.up = new TextureRegionDrawable(new TextureRegion(playButtonUpTexture));
        style.over = new TextureRegionDrawable(new TextureRegion(playButtonOnTexture));

        playButton = new Button(style);
        playButton.setPosition(
                BalloonTD.WORLD_WIDTH *  0.4F -  playButton.getWidth()/2,
                BalloonTD.WORLD_HEIGHT * 0.17F - playButton.getHeight()/2
        );
        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                startScreen.getBalloonTD().enterMenuScreen();
            }
        });
        playButton.setSize(playButton.getWidth() * 2F,
                playButton.getHeight() * 2F);

        startScreen.addActor(playButton);
    }

    @Override
    public void act(float delta){ super.act(delta); }

    @Override
    public void draw(Batch batch, float parentAlpha){ super.draw(batch, parentAlpha); }

    public void dispose(){
        if(playButtonUpTexture != null){
            playButtonUpTexture.dispose();
        }
        if(playButtonOnTexture != null){
            playButtonOnTexture.dispose();
        }
    }
}
