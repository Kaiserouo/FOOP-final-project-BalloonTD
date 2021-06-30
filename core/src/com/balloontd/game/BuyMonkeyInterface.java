package com.balloontd.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.balloontd.BalloonTD;

import java.util.ArrayList;

public class BuyMonkeyInterface extends Actor {
    private GameScreen gameScreen;
    private UserInterface userInterface;

    private ArrayList<BuyMonkeyInfo> buyMonkeyInfoList;
    private int infoIndex;

    private Texture nextMonkeyTextureUp, nextMonkeyTextureOn;
    private Button nextMonkeyButton;

    private Texture previousMonkeyTextureUp, previousMonkeyTextureOn;
    private Button previousMonkeyButton;

    private Texture buyMonkeyTextureUp, buyMonkeyTextureOn;
    private Button buyMonkeyButton;

    private Monkey newMonkey;
    private boolean withMonkeyMode;

    public BuyMonkeyInterface(GameScreen gameScreen, UserInterface userInterface){
        this.gameScreen = gameScreen;
        this.userInterface = userInterface;
        buyMonkeyInfoList = new ArrayList<BuyMonkeyInfo>();
        infoIndex = 0;
        withMonkeyMode = false;

        makeNextMonkeyButton();
        makePreviousMonkeyButton();
        makeBuyMonkeyButton();
    }


    public void addBuyMonkeyInfo(Monkey monkey){
        BuyMonkeyInfo buyMonkeyInfo = new BuyMonkeyInfo(monkey, monkey.getUIImage());
        buyMonkeyInfoList.add(buyMonkeyInfo);
    }

    private void makeNextMonkeyButton(){
        nextMonkeyTextureUp = new Texture("ui-buy-monkey-next-up.png");
        nextMonkeyTextureOn = new Texture("ui-buy-monkey-next-on.png");
        Button.ButtonStyle style = new Button.ButtonStyle();
        style.up = new TextureRegionDrawable(new TextureRegion(nextMonkeyTextureUp));
        style.over = new TextureRegionDrawable(new TextureRegion(nextMonkeyTextureOn));

        nextMonkeyButton = new Button(style);
        nextMonkeyButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(infoIndex < buyMonkeyInfoList.size() - 1 ) {
                    infoIndex++;
                }
            }
        });
        gameScreen.addActor(nextMonkeyButton);
        nextMonkeyButton.setPosition(1055, 105);
    }

    private void makePreviousMonkeyButton(){
        previousMonkeyTextureUp = new Texture("ui-buy-monkey-pre-up.png");
        previousMonkeyTextureOn = new Texture("ui-buy-monkey-pre-on.png");
        Button.ButtonStyle style = new Button.ButtonStyle();
        style.up = new TextureRegionDrawable(new TextureRegion(previousMonkeyTextureUp));
        style.over = new TextureRegionDrawable(new TextureRegion(previousMonkeyTextureOn));

        previousMonkeyButton = new Button(style);
        previousMonkeyButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(infoIndex > 0) {
                    infoIndex--;
                }
            }
        });
        gameScreen.addActor(previousMonkeyButton);
        previousMonkeyButton.setPosition(955, 105);
    }

    private void makeBuyMonkeyButton(){
        buyMonkeyTextureUp = new Texture("ui-buy-monkey-buy-up.png");
        buyMonkeyTextureOn = new Texture("ui-buy-monkey-buy-on.png");
        Button.ButtonStyle style = new Button.ButtonStyle();
        style.up = new TextureRegionDrawable(new TextureRegion(buyMonkeyTextureUp));
        style.over = new TextureRegionDrawable(new TextureRegion(buyMonkeyTextureOn));

        buyMonkeyButton = new Button(style);
        buyMonkeyButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Monkey monkey = buyMonkeyInfoList.get(infoIndex).getMonkey();
                Player player = gameScreen.getPlayer();
                if(player.getMoney() >= monkey.getBuyPrice()) {
                    player.setMoney(player.getMoney() - monkey.getBuyPrice());
                    newMonkey = monkey.cloneMonkey(gameScreen, new Vector2(Gdx.input.getX(), Gdx.input.getY()));
                    setWithMonkeyMode(true);
                    gameScreen.stageAddListener();
                }

            }
        });
        gameScreen.addActor(buyMonkeyButton);
        buyMonkeyButton.setPosition(960, 430);
    }

    public boolean getWithMonkeyMode(){return withMonkeyMode;}

    public void setWithMonkeyMode(boolean mode){withMonkeyMode = mode;}

    public  Monkey getNewMonkey(){
        return  newMonkey;
    }

    @Override
    public void act(float delta){
        super.act(delta);
        if(userInterface.getInterfaceMode() == UserInterface.BUY_MONKEY_MODE) {
            if (getWithMonkeyMode()) {
                newMonkey.setCoords(new Vector2(Gdx.input.getX(), BalloonTD.WORLD_HEIGHT - Gdx.input.getY()));
                userInterface.showBodyRange(true, newMonkey);
                if(gameScreen.checkIntersection()){
                    userInterface.showInvalidRange(true, newMonkey);
                    userInterface.showShootRange(false, newMonkey);
                }else{
                    userInterface.showInvalidRange(false, newMonkey);
                    userInterface.showShootRange(true, newMonkey);
                }
            }else{
                userInterface.showBodyRange(false, newMonkey);
                userInterface.showInvalidRange(false, newMonkey);
                userInterface.showShootRange(false, newMonkey);
            }
            nextMonkeyButton.setVisible(true);
            previousMonkeyButton.setVisible(true);
            buyMonkeyButton.setVisible(true);
        }
        else{
            nextMonkeyButton.setVisible(false);
            previousMonkeyButton.setVisible(false);
            buyMonkeyButton.setVisible(false);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        super.draw(batch, parentAlpha);

        if(userInterface.getInterfaceMode() == UserInterface.BUY_MONKEY_MODE) {
            if (buyMonkeyInfoList.size() > infoIndex) {
                buyMonkeyInfoList.get(infoIndex).draw(batch, parentAlpha);
            }
            if (getWithMonkeyMode()) {
                newMonkey.draw(batch, parentAlpha);
            }
        }

    }

    public void dispose(){
        if(nextMonkeyTextureUp != null) {
            nextMonkeyTextureUp.dispose();
        }
        if(nextMonkeyTextureOn != null) {
            nextMonkeyTextureOn.dispose();
        }
        if(previousMonkeyTextureUp != null){
            previousMonkeyTextureUp.dispose();
        }
        if(previousMonkeyTextureOn != null){
            previousMonkeyTextureOn.dispose();
        }
        for(BuyMonkeyInfo info : buyMonkeyInfoList){
            info.dispose();
        }

    }
}
