package com.stroin.game.Level;

import com.stroin.game.entity.Player;

public interface Level {
    void create();
    void create(Player loadedPlayer);
    void render();
    void dispose();
}
