package com.balloontd.menu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.balloontd.*;

import java.util.ArrayList;

public class OpenPageButton extends Button {

    private Page page;
    public OpenPageButton(ButtonStyle style, final Page page){
        super(style);
        this.page = page;

        addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                page.setVisible(true);
            }
        });
    }

}
