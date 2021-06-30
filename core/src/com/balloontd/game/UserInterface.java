package com.balloontd.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
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

    private Texture bodyRangeTexture, shootRangeTexture, invalidRangeTexture;
    private Image bodyRangeCircle, shootRangeCircle, invalidRangeCircle;

    private EndingPage endingPage;

    boolean mouseOnMonkey;

    public UserInterface(GameScreen gameScreen) {
        this.gameScreen = gameScreen;

        makeBackground();
        setRoundInfo();
        makeStartRoundButton();
        makeExitGameButton();

        monkeyInfoInterface = new MonkeyInfoInterface(gameScreen, this);
        gameScreen.addActor(monkeyInfoInterface);

        buyMonkeyInterface = new BuyMonkeyInterface(gameScreen, this);
        gameScreen.addActor(buyMonkeyInterface);

        font = new BitmapFont(Gdx.files.internal("font/ComicSansMS.fnt"));

        interfaceMode = BUY_MONKEY_MODE;

        makeRangeCircle();

        endingPage = new EndingPage(gameScreen);
        mouseOnMonkey = false;
    }


    private void makeBackground(){
        backgroundTexture = new Texture("userInterface-background.png");
        background = new Image(new TextureRegion(backgroundTexture));
        background.setPosition(950, 0);
        gameScreen.addActor(background);
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
                gameScreen.getRoundManager().startRound();
                startRoundButton.setVisible(false);
            }
        });
        startRoundButton.setPosition(960, 37);
        gameScreen.addActor(startRoundButton);
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
                gameScreen.getBalloonTD().leaveGameScreen();
            }
        });
        exitGameButton.setPosition(1030, 0);
        gameScreen.addActor(exitGameButton);
    }

    public boolean getInterfaceMode(){ return interfaceMode;   }

    public void setInterfaceMode(boolean mode){ interfaceMode = mode; }

    public void makeRangeCircle(){
        bodyRangeTexture = new Texture("blue-circle.png");
        bodyRangeCircle = new Image(new TextureRegion(bodyRangeTexture));
        bodyRangeCircle.setVisible(false);

        shootRangeTexture = new Texture("blue-circle.png");
        shootRangeCircle = new Image(new TextureRegion(shootRangeTexture));
        shootRangeCircle.setVisible(false);

        invalidRangeTexture = new Texture("red-circle.png");
        invalidRangeCircle = new Image(new TextureRegion(invalidRangeTexture));
        invalidRangeCircle.setVisible(false);
    }

    // deal with placing the new monkey on the valid place in the map

    public void setWithMonkeyMode(boolean mode){buyMonkeyInterface.setWithMonkeyMode(mode);}

    public Monkey getNewMonkey(){
        return buyMonkeyInterface.getNewMonkey();
    }


    public void addBuyMonkeyInfo(Monkey monkey){
        buyMonkeyInterface.addBuyMonkeyInfo(monkey);
    }

    public void showShootRange(boolean show, Monkey monkey){
        if(show){
            shootRangeCircle.setVisible(true);
            shootRangeCircle.setSize(monkey.getShootRadius() * 2, monkey.getShootRadius() * 2);
            shootRangeCircle.setPosition(monkey.getCoords().x - monkey.getShootRadius(),
                    monkey.getCoords().y - monkey.getShootRadius());
        }else{
            shootRangeCircle.setVisible(false);
        }
    }
    public void showBodyRange(boolean show, Monkey monkey){
        if(show){
            bodyRangeCircle.setVisible(true);
            bodyRangeCircle.setSize(monkey.getBodyRadius() * 2, monkey.getBodyRadius() * 2);
            bodyRangeCircle.setPosition(monkey.getCoords().x - monkey.getBodyRadius(),
                    monkey.getCoords().y - monkey.getBodyRadius());
        }else{
            bodyRangeCircle.setVisible(false);
        }
    }
    public void showInvalidRange(boolean show, Monkey monkey){
        if(show){
            invalidRangeCircle.setVisible(true);
            invalidRangeCircle.setSize(monkey.getShootRadius() * 2, monkey.getShootRadius() * 2);
           invalidRangeCircle.setPosition(monkey.getCoords().x - monkey.getShootRadius(),
                    monkey.getCoords().y - monkey.getShootRadius());
        }else{
            invalidRangeCircle.setVisible(false);
        }
    }

    public void gameOver(boolean is_win){
        endingPage.gameOver(is_win);
    }

    public void setMouseOnMonkey(boolean isOn){
        mouseOnMonkey = isOn;
    }
    public void setMonkey(Monkey monkey){monkeyInfoInterface.setMonkey(monkey);}

    @Override
    public void act(float delta){
        super.act(delta);
        setRoundInfo();
        if(gameScreen.getRoundManager().isInRound()){
            startRoundButton.setVisible(false);
        }else {
            startRoundButton.setVisible(true);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        super.draw(batch, parentAlpha);

        if(bodyRangeCircle.isVisible() || mouseOnMonkey){
            bodyRangeCircle.draw(batch, parentAlpha);
        }
        if(shootRangeCircle.isVisible() || mouseOnMonkey){
            shootRangeCircle.draw(batch, parentAlpha);
        }
        if(invalidRangeCircle.isVisible()){
            invalidRangeCircle.draw(batch, parentAlpha);
        }
        font.draw(batch, roundInfo, 960, 630);
    }

    public void dispose(){
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
        if(bodyRangeTexture != null){
            bodyRangeTexture.dispose();
        }
        if(shootRangeTexture != null){
            shootRangeTexture.dispose();
        }
        if(invalidRangeTexture != null){
            invalidRangeTexture.dispose();
        }

    }
}
