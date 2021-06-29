package com.balloontd.game;

import com.balloontd.game.bloons.*;

import java.util.List;
import java.util.Optional;

public class BloonSpawnerClass implements IBloonSpawner {
    private GameScreen game_screen;
    private List<String> names;
    private String[] names_arr = {
            "Red", "Blue", "Green", "Yellow", "Pink", "Black", "White", "Zebra", "Rainbow"
    };

    public BloonSpawnerClass(GameScreen game_screen) {
        this.game_screen = game_screen;
        names = List.of(names_arr);
    }

    public Optional<Bloon> spawn(String bloon_name) {
        switch (bloon_name) {
            case "Red":
                return Optional.of(new RedBloon(game_screen, 0F));
            case "Blue":
                return Optional.of(new BlueBloon(game_screen, 0F));
            case "Green":
                return Optional.of(new GreenBloon(game_screen, 0F));
            case "Yellow":
                return Optional.of(new YellowBloon(game_screen, 0F));
            case "Pink":
                return Optional.of(new PinkBloon(game_screen, 0F));
            case "Black":
                return Optional.of(new BlackBloon(game_screen, 0F));
            case "White":
                return Optional.of(new WhiteBloon(game_screen, 0F));
            case "Zebra":
                return Optional.of(new ZebraBloon(game_screen, 0F));
            case "Rainbow":
                return Optional.of(new RainbowBloon(game_screen, 0F));
        }
        return Optional.empty();
    }

    @Override
    public List<String> getValidNames() {
        return names;
    }
}
