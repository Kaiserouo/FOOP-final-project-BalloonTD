package com.balloontd.game;

import com.balloontd.game.bloons.BlueBloon;
import com.balloontd.game.bloons.RedBloon;

import java.util.List;
import java.util.Optional;

public class BloonSpawnerClass implements IBloonSpawner {
    private GameScreen game_screen;

    public BloonSpawnerClass(GameScreen game_screen) {
        this.game_screen = game_screen;
    }

    public Optional<Bloon> spawn(String bloon_name) {
        switch (bloon_name) {
            case "R":
                return Optional.of(new RedBloon(game_screen, 0F));
            case "B":
                return Optional.of(new BlueBloon(game_screen, 0F));
        }
    }

    @Override
    public List<String> getValidNames() {
        String[] names = {
                "R", "B"
        };
        return List.of(names)
    }
}
