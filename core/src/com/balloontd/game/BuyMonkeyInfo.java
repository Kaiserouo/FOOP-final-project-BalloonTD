package com.balloontd.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class BuyMonkeyInfo extends Actor {
    private Texture backgroundTexture;
    private Image background;
    private BitmapFont font;
    private Monkey monkey;
    private Image monkeyImage;

    private String info;

    public BuyMonkeyInfo(Monkey monkey, Image monkeyImage){
        super();
        backgroundTexture = new Texture("ui-monkeyInfo.png");
        background = new Image(new TextureRegion(backgroundTexture));
        background.setPosition(955, 180);

        this.monkey = monkey;
        this.monkeyImage = monkeyImage;
        // set Image Size: ......
        monkeyImage.setPosition(955, 200);

        font = new BitmapFont(Gdx.files.internal("font/ComicSansMS.fnt"));
        font.getData().setScale(0.7F);
        setInfo();
    }

    private void setInfo(){
        info = "";
        info += (monkey.getName() + "\n");
        info += ("Price: $" + monkey.getBuyPrice() + "\n");
        info += (monkey.getIntro());
    }

    public Monkey getMonkey(){return monkey;}

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        super.draw(batch, parentAlpha);

        background.draw(batch, parentAlpha);
        monkeyImage.draw(batch, parentAlpha);
        font.draw(batch, info, 965, 410);

    }

    public void dispose(){
        if(backgroundTexture != null) {
            backgroundTexture.dispose();
        }
    }
}
