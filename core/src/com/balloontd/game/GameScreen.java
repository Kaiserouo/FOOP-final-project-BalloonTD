package com.balloontd.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.balloontd.BalloonTD;


public class GameScreen implements Screen{
    private MonkeyManager monkey_manager;
    private DartManager dart_manager;
    private BloonManager bloon_manager;
    private RoundManager round_manager;
    private UserInterface userInterface;

    private Trail trail;
    private Player player;

    private Stage stage;

    public GameScreen(BalloonTD balloonTD){
        // stage act sequence:
        // background -> dart -> monkey -> bloon -> userinterface
        // -> (shoot range circle (if want to add now))
        dart_manager = new DartManager();
        monkey_manager = new MonkeyManager();
        bloon_manager = new BloonManager();
        stage.addActor(dart_manager);
        stage.addActor(monkey_manager);
        stage.addActor(bloon_manager);

        round_manager = new RoundManager(
                this, "game_round.txt",
                new BloonSpawnerClass(this)
        );

        userInterface = new UserInterface(this);
        stage.addActor(userInterface);

        //use  userInterface.addBuyMonkeyInfo(monkey, image); to add new Monkey in Buy Monkey Mode
    }

    @Override
    public void show() {
        userInterface.setInputProcessor();
    }

    @Override
    public void render(float delta) {
        // render all textures
        stage.act();    // managers will simply draw all things
        stage.draw();

        round_manager.act(delta);

        // at final stage of render, process all managers' buffer
        monkey_manager.dumpBufferToList();
        monkey_manager.cleanDeadMonkeys();
        bloon_manager.dumpBufferToList();
        bloon_manager.cleanDeadBloons();
        dart_manager.dumpBufferToList();
        dart_manager.cleanDeadDarts();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        if(stage != null){
            stage.dispose();
        }
    }

    public MonkeyManager getMonkeyManager() { return monkey_manager; }
    public DartManager getDartManager() { return dart_manager; }
    public BloonManager getBloonManager() { return bloon_manager; }
    public Player getPlayer() { return player; }
    public Trail getTrail() { return trail; }
    public RoundManager getRoundManager(){ return round_manager; }

    public boolean inRange(Vector2 coord, float allowed_delta) {
        // test if in range of screen
        // with tolerance `allowed_delta`
        float x = coord.x, y = coord.y;
        return x >= -allowed_delta
                && y >= -allowed_delta
                && x <= BalloonTD.WORLD_WIDTH + allowed_delta
                && y <= BalloonTD.WORLD_HEIGHT + allowed_delta;
    }
}
