package com.balloontd;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ButtonListener extends ClickListener {
    @Override
    public void clicked(InputEvent event, float x, float y) {
        Button button = (Button) event.getListenerActor();

        button.beingClicked();
    }

    @Override
    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        Button button = (Button) event.getListenerActor();

        button.cursorIsOn();

    }

    @Override
    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        Button button = (Button) event.getListenerActor();

        button.cursorIsOff();

    }
}
