package com.balloontd.game;

import java.util.List;
import java.util.Optional;

public interface IBloonSpawner {
    public Optional<Bloon> spawn(String bloon_name);
    public List<String> getValidNames();
}
