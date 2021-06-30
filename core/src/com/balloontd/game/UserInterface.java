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
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.balloontd.BalloonTD;

import java.util.ArrayList;

public class UserInterface extends Actor {
    private GameScreen gameScreen;
    private Stage stage;

    private Texture backgroundTexture;
    private Image background;

    private String roundInfo;
    private BitmapFont font;

    private Texture startRoundTextureUp, startRoundTextureOn;
    private Button startRoundButton;

    private Texture exitGameTextureUp, exitGameTextureOn;
    private Button exitGameButton;


    private MonkeyInfoInterface monkeyInfoInterface;

    private BuyMonkeyInterface buyMonkeyInterface;

    private boolean interfaceMode;
    public final static boolean BUY_MONKEY_MODE = true;
    public final static boolean MONKEY_INFO_MODE = false;


    public UserInterface(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        stage = new Stage(new StretchViewport(BalloonTD.WORLD_WIDTH, BalloonTD.WORLD_HEIGHT));

        makeBackground();
        setRoundInfo();
        makeStartRoundButton();
        makeExitGameButton();

        monkeyInfoInterface = new MonkeyInfoInterface(gameScreen, this);
        stage.addActor(monkeyInfoInterface);

        buyMonkeyInterface = new BuyMonkeyInterface(gameScreen, this);
        stage.addActor(buyMonkeyInterface);

        font = new BitmapFont(Gdx.files.internal("font/ComicSansMS.fnt"));

        interfaceMode = BUY_MONKEY_MODE;
    }

    public void setInputProcessor(){
        Gdx.input.setInputProcessor(stage);
    }

    private void makeBackground(){
        backgroundTexture = new Texture("userInterface-background.png");
        background = new Image(new TextureRegion(backgroundTexture));
        background.setPosition(950, 0);
        stage.addActor(background);
    }

    private void setRoundInfo(){
        roundInfo = "";
        roundInfo += ("Round: " + gameScreen.getRoundManager().getCurrentRound() + "\n");
        roundInfo += ("Money: " + gameScreen.getPlayer().getMoney() + "\n");
        roundInfo += ("HP: " + gameScreen.getPlayer().getHP());
    }

    private void makeStartRoundButton(){
        startRoundTextureUp = new Texture("ui-start-round-up.png");
        startRoundTextureOn = new Texture("ui-start-round-on.png");
        Button.ButtonStyle style = new Button.ButtonStyle();
        style.up = new TextureRegionDrawable(new TextureRegion(startRoundTextureUp));
        style.over = new TextureRegionDrawable(new TextureRegion(startRoundTextureOn));

        startRoundButton = new Button(style);
        startRoundButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                gameScreen.startRound();
                startRoundButton.setVisible(false);
            }
        });
        startRoundButton.setPosition(960, 37);
        stage.addActor(startRoundButton);
    }

    private void makeExitGameButton(){
        exitGameTextureUp = new Texture("ui-exit-up.png");
        exitGameTextureOn = new Texture("ui-exit-on.png");
        Button.ButtonStyle style = new Button.ButtonStyle();
        style.up = new TextureRegionDrawable(new TextureRegion(exitGameTextureUp));
        style.over = new TextureRegionDrawable(new TextureRegion(exitGameTextureOn));

        exitGameButton = new Button(style);
        exitGameButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                gameScreen.dispose();
            }
        });
        exitGameButton.setPosition(1030, 0);
        stage.addActor(exitGameButton);
    }

    public boolean getInterfaceMode(){ return interfaceMode;   }

    public void setInterfaceMode(boolean mode){ interfaceMode = mode; }

    // deal with placing the new monkey on the valid place in the map

    public void stageAddListener(){
        stage.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(checkIntersection() == false){
                    gameScreen.getMonkeyManager().addMonkeyInBuffer(buyMonkeyInterface.getNewMonkey());
                    stage.removeListener(this);
                    buyMonkeyInterface.setWithMonkeyMode(false);
                }
            }
        });
    }

    public boolean checkIntersection(){
        MonkeyManager monkeyManager = gameScreen.getMonkeyManager();
        Monkey newMonkey = buyMonkeyInterface.getNewMonkey();
        for(Monkey monkey: monkeyManager.getMonkeyList()){
            if(monkey.getCoords().dst(newMonkey.getCoords()) < monkey.getBodyRadius() + newMonkey.getBodyRadius()){
                return true;
            }
        }
        return  gameScreen.getTrail().checkIntersectCircle(newMonkey.getCoords(), newMonkey.getBodyRadius());
    }

    public void addActor(Actor actor){
        stage.addActor(actor);
    }
    public void addBuyMonkeyInfo(Monkey monkey, Image img){
        buyMonkeyInterface.addBuyMonkeyInfo(monkey, img);
    }



    @Override
    public void act(float delta){
        super.act(delta);
        stage.act();
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        super.draw(batch, parentAlpha);

        stage.draw();

        font.draw(batch, roundInfo, 960, 630);
    }

    public void dispose(){
        if(stage != null){
            stage.dispose();
        }
        if(backgroundTexture != null) {
            backgroundTexture.dispose();
        }
        if(startRoundTextureOn != null) {
            startRoundTextureOn.dispose();
        }
        if(startRoundTextureUp != null){
            startRoundTextureUp.dispose();
        }
        if(exitGameTextureOn != null){
            exitGameTextureOn.dispose();
        }
        if(exitGameTextureUp != null){
            exitGameTextureUp.dispose();
        }
        if(monkeyInfoInterface != null){
            monkeyInfoInterface.dispose();
        }
        if(buyMonkeyInterface != null){
            buyMonkeyInterface.dispose();
        }

    }
}
