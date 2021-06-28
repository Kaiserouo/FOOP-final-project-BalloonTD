package com.balloontd;

import com.badlogic.gdx.Game;
import com.balloontd.start.*;
import com.balloontd.menu.*;
import com.balloontd.game.*;

public class BalloonTD extends Game {

	public static final String TAG = BalloonTD.class.getSimpleName();

	// For screen
	public static final int WORLD_WIDTH = 1150;
	public static final int WORLD_HEIGHT = 650;

	private StartScreen startScreen;
	private MenuScreen menuScreen;
	private GameScreen gameScreen;

	@Override
	public void create () {
		startScreen = new StartScreen(this);
		menuScreen = new MenuScreen(this);
		setScreen(startScreen);
	}

	// From startScreen to menuScreen
	public void enterMenuScreen(){
		setScreen(menuScreen);

		if(startScreen != null){
			startScreen.dispose();
			startScreen = null;
		}
	}

	// From menuScreen to GameScreen
	public void enterGameScreen(){
		gameScreen = new GameScreen(this);
		setScreen(gameScreen);
	}

	// From gameScreen to menuScreen
	public void leaveGameScreen(){
		setScreen(menuScreen);

		if(gameScreen != null){
			gameScreen.dispose();
			gameScreen = null;
		}
	}


	@Override
	public void dispose () {
		this.dispose();
		if(menuScreen != null) {
			menuScreen.dispose();
			menuScreen = null;
		}
	}

}
