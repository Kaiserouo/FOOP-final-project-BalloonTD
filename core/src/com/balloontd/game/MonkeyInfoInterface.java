package com.balloontd.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MonkeyInfoInterface extends Actor {
    private GameScreen gameScreen;
    private UserInterface userInterface;
    private Monkey monkey;

    private Texture sellMonkeyTextureUp, sellMonkeyTextureOn;
    private Button sellMonkeyButton;

    private Texture upgradeMonkeyTextureUp, upgradeMonkeyTextureOn;
    private Button upgradeMonkeyButton;

    private Texture enterBuyModeTextureUp, enterBuyModeTextureOn;
    private Button enterBuyModeButton;

    private MonkeyInfo monkeyInfo;

    public MonkeyInfoInterface(GameScreen gameScreen, UserInterface userInterface){
        this.gameScreen = gameScreen;
        this.userInterface = userInterface;
        monkeyInfo = new MonkeyInfo();
        makeSellMonkeyButton();
        makeUpgradeMonkeyButton();
        makeEnterBuyModeButton();
    }

    public void setMonkey(Monkey monkey){
        this.monkey = monkey;
        monkeyInfo.setMonkey(monkey);
    }

    private void makeSellMonkeyButton(){
        sellMonkeyTextureUp = new Texture("ui-sell-monkey-up.png");
        sellMonkeyTextureOn = new Texture("ui-sell-monkey-on.png");
        Button.ButtonStyle style = new Button.ButtonStyle();
        style.up = new TextureRegionDrawable(new TextureRegion(sellMonkeyTextureUp));
        style.over = new TextureRegionDrawable(new TextureRegion(sellMonkeyTextureOn));

        sellMonkeyButton = new Button(style);
        sellMonkeyButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                gameScreen.getPlayer().addMoney(monkey.getSellPrice());
                monkey.setAliveState(false);
                monkey = null;
            }
        });
        gameScreen.addActor(sellMonkeyButton);
        sellMonkeyButton.setPosition(955, 105);
    }

    private void makeUpgradeMonkeyButton(){
        upgradeMonkeyTextureUp = new Texture("ui-upgrade-monkey-up.png");
        upgradeMonkeyTextureOn = new Texture("ui-upgrade-monkey-on.png");
        Button.ButtonStyle style = new Button.ButtonStyle();
        style.up = new TextureRegionDrawable(new TextureRegion(upgradeMonkeyTextureUp));
        style.over = new TextureRegionDrawable(new TextureRegion(upgradeMonkeyTextureOn));

        upgradeMonkeyButton = new Button(style);
        upgradeMonkeyButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Player player = gameScreen.getPlayer();
                if(player.getMoney() >= monkey.getLevelUpCost(monkey.getCurLevel())) {
                    player.setMoney(player.getMoney() - monkey.getLevelUpCost(monkey.getCurLevel()));
                    monkey.levelUp();
                }
            }
        });
        gameScreen.addActor(upgradeMonkeyButton);
        upgradeMonkeyButton.setPosition(1055, 105);
    }

    private void makeEnterBuyModeButton(){
        enterBuyModeTextureUp = new Texture("ui-enter-buyMode-up.png");
        enterBuyModeTextureOn = new Texture("ui-enter-buyMode-on.png");
        Button.ButtonStyle style = new Button.ButtonStyle();
        style.up = new TextureRegionDrawable(new TextureRegion(enterBuyModeTextureUp));
        style.over = new TextureRegionDrawable(new TextureRegion(enterBuyModeTextureOn));

        enterBuyModeButton = new Button(style);
        enterBuyModeButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                userInterface.setInterfaceMode(UserInterface.BUY_MONKEY_MODE);
            }
        });
        enterBuyModeButton.setPosition(960, 430);
        gameScreen.addActor(enterBuyModeButton);
    }

    @Override
    public void act(float delta){
        super.act(delta);
        if(userInterface.getInterfaceMode() == UserInterface.MONKEY_INFO_MODE){
            sellMonkeyButton.setVisible(true);
            upgradeMonkeyButton.setVisible(true);
            enterBuyModeButton.setVisible(true);
            this.setVisible(true);
        } else{
            sellMonkeyButton.setVisible(false);
            upgradeMonkeyButton.setVisible(false);
            enterBuyModeButton.setVisible(false);
            this.setVisible(false);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        super.draw(batch, parentAlpha);

        if(monkey != null && this.isVisible()) {
            monkeyInfo.draw(batch, parentAlpha);
        }
    }

    public void dispose(){
        if(sellMonkeyTextureUp != null) {
            sellMonkeyTextureUp.dispose();
        }
        if(sellMonkeyTextureOn != null) {
            sellMonkeyTextureOn.dispose();
        }
        if(upgradeMonkeyTextureUp != null){
            upgradeMonkeyTextureUp.dispose();
        }
        if(upgradeMonkeyTextureOn != null){
            upgradeMonkeyTextureOn.dispose();
        }
        if(enterBuyModeTextureOn != null){
            enterBuyModeTextureOn.dispose();
        }
        if(enterBuyModeTextureUp != null){
            enterBuyModeTextureUp.dispose();
        }
        if(monkeyInfo != null){
            monkeyInfo.dispose();
        }

    }
}
