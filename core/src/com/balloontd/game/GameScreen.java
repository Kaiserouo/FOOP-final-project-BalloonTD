package com.balloontd.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.balloontd.BalloonTD;
import com.balloontd.game.monkeys.*;

import java.awt.*;
import java.awt.image.BaseMultiResolutionImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.awt.image.SinglePixelPackedSampleModel;


public class GameScreen implements Screen{
    private MonkeyManager monkey_manager;
    private DartManager dart_manager;
    private BloonManager bloon_manager;
    private RoundManager round_manager;
    private UserInterface userInterface;
    private BalloonTD balloon_td;
    private Image map;

    private Trail trail;
    private Player player;

    private Stage stage;
    private boolean is_game_over;

    public GameScreen(BalloonTD balloonTD){
        is_game_over = false;
        balloon_td = balloonTD;

        // stage act sequence:
        // background -> dart -> monkey -> bloon -> userinterface
        // -> (shoot range circle (if want to add now))
        stage = new Stage(new StretchViewport(
                BalloonTD.WORLD_WIDTH, BalloonTD.WORLD_HEIGHT
        ));
        map = new Image(new TextureRegion(new Texture("map.png")));
        dart_manager = new DartManager();
        monkey_manager = new MonkeyManager();
        bloon_manager = new BloonManager();
        stage.addActor(map);
        stage.addActor(dart_manager);
        stage.addActor(monkey_manager);
        stage.addActor(bloon_manager);

        round_manager = new RoundManager(
                this, "game_round.txt",
                new BloonSpawnerClass(this)
        );

        trail = CompositeTrail.makeTrailByLines("trail.txt");
        player = new Player();

        userInterface = new UserInterface(this);
        stage.addActor(userInterface);

        addUIBuyMonkeyInfo();
        addAllMonkeyListener();
    }
    
    
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        // render all textures
        stage.act();    // managers will simply draw all things
        stage.draw();

        round_manager.act(delta);

        if(player.getHP() <= 0){
            gameOver(false);
        }

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
    public BalloonTD getBalloonTD(){ return balloon_td; }

    public void gameOver(boolean is_win) {
        if(is_game_over) return;
        is_game_over = true;
        userInterface.gameOver(is_win);
    }

    public void addActor(Actor actor){ stage.addActor(actor);}
    public void stageAddListener(){
        stage.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(checkIntersection() == false){
                    monkey_manager.addMonkeyInBuffer(userInterface.getNewMonkey());
                    stage.removeListener(this);
                    userInterface.setWithMonkeyMode(false);
                }
            }
        });
    }
    public boolean checkIntersection(){
        Monkey newMonkey = userInterface.getNewMonkey();
        for(Monkey monkey: monkey_manager.getMonkeyList()){
            if(monkey.getCoords().dst(newMonkey.getCoords()) < monkey.getBodyRadius() + newMonkey.getBodyRadius()){
                return true;
            }
        }
        return  trail.checkIntersectCircle(newMonkey.getCoords(), newMonkey.getBodyRadius());
    }
    

    public boolean inRange(Vector2 coord, float allowed_delta) {
        // test if in range of screen
        // with tolerance `allowed_delta`
        float x = coord.x, y = coord.y;
        return x >= -allowed_delta
                && y >= -allowed_delta
                && x <= BalloonTD.WORLD_WIDTH + allowed_delta
                && y <= BalloonTD.WORLD_HEIGHT + allowed_delta;
    }

    //use  userInterface.addBuyMonkeyInfo(monkey, image); to add new Monkey info
    public void addUIBuyMonkeyInfo(){
        userInterface.addBuyMonkeyInfo(new DartShooter(this, new Vector2(0, 0)));
        userInterface.addBuyMonkeyInfo(new TackShooter(this, new Vector2(0, 0)));
        userInterface.addBuyMonkeyInfo(new BoomerangShooter(this, new Vector2(0, 0)));
        userInterface.addBuyMonkeyInfo(new BombShooter(this, new Vector2(0, 0)));
        userInterface.addBuyMonkeyInfo(new DartlingGunner(this, new Vector2(0, 0)));
        userInterface.addBuyMonkeyInfo(new SuperMonkey(this, new Vector2(0, 0)));
    }
    public void addMonkeyListener(final Monkey monkey){
        monkey.addListener(new ClickListener(){
           @Override
           public void clicked(InputEvent event, float x, float y){
               userInterface.setInterfaceMode(UserInterface.MONKEY_INFO_MODE);
           }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                userInterface.showShootRange(true, monkey);

            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                userInterface.showShootRange(false, monkey);

            }
        });
    }

    // call addMonkeyListener(monkey) to add listener to monkey
    public void addAllMonkeyListener(){
        for(Monkey monkey: monkey_manager.getMonkeyList()){
            addMonkeyListener(monkey);
        }
    }
}
