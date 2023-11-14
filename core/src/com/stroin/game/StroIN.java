// package com.stroin.game;

// import com.stroin.game.Level.Level0;
// import com.stroin.game.Level.Level1;
// import com.stroin.game.Level.Level2;
// import com.stroin.game.Level.LevelManager;
// import com.stroin.game.Menus.MenuScreen;
// import com.badlogic.gdx.Game;

// public class StroIN extends Game {
//     private LevelManager levelManager;
//     private String levelNumber;

//     public StroIN(String levelNumber) {
//         this.levelNumber = levelNumber;
//     }

//     @Override
//     public void create() {
//         setScreen(new MenuScreen());
//         levelManager = new LevelManager();

//         switch (levelNumber) {
//             case "0":
//                 levelManager.setLevel(new Level0());
//                 break;
//             case "1":
//                 levelManager.setLevel(new Level1());
//                 break;
//             case "2":
//                 levelManager.setLevel(new Level2());
//                 break;
//             default:
//                 System.out.println("Invalid level: " + levelNumber);
//                 break;
//         }
//     }

//     @Override
//     public void render() {
//         if (levelManager.getCurrentLevel() != null) {
//             levelManager.render();
//         }
//     }

//     @Override
//     public void dispose() {
//         levelManager.dispose();
//     }
// }
//Futur StroIN ci-dessous
package com.stroin.game;

import com.stroin.game.Level.Level0;
import com.stroin.game.Level.Level1;
import com.stroin.game.Level.Level2;
import com.stroin.game.Level.LevelManager;
import com.stroin.game.Menus.MenuScreen;
import com.badlogic.gdx.Game;

public class StroIN extends Game {
    public LevelManager levelManager;
    private MenuScreen menuScreen;

    @Override
    public void create() {
        levelManager = new LevelManager();
        menuScreen = new MenuScreen(this);
        setScreen(menuScreen);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        if (levelManager != null) {
            levelManager.dispose();
        }
        super.dispose();
    }
}