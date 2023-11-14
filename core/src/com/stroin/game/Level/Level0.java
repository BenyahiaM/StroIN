package com.stroin.game.Level;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.stroin.game.controllers.MovementController;
import com.stroin.game.data.GameData;
import com.stroin.game.elements.HorizontalPlatform;
import com.stroin.game.entity.ClassicNpc;
import com.stroin.game.entity.EntityNpcMovement;
import com.stroin.game.entity.Player;
import com.stroin.game.items.Consumables.Consumable;
import com.stroin.game.items.Consumables.Seringue;

public class Level0 extends ApplicationAdapter implements Level {

    private MovementController movementController;
    private EntityNpcMovement entityMovement;
    private Player player;
    private ClassicNpc npc;
    private SpriteBatch batch;
    private TextureRegion[] sprites;
    private Texture healthBar;
    private int spriteWidth = 100;
    private Texture platformTexture;
    private List<HorizontalPlatform> platforms = new ArrayList<>();
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;
    private Seringue seringue;
    private Seringue seringue1;
    private Seringue seringue2;
    private Seringue seringue3;
    private BitmapFont font;
    private TextureRegion[] weaponsprites;
    private boolean gameOverScreenSetUp = false;
    private Stage stage;
    private Stage inventoryStage;  
    private GameData gameData;


    @Override
    public void create() {
        LevelFunctions.InitializationResult result = LevelFunctions.initializeBasics();

        font = result.font;
        camera = result.camera;
        batch = result.batch;
        map = result.map;
        mapRenderer = result.mapRenderer;
        sprites = result.sprites;
        weaponsprites = result.weaponsprites;
        healthBar = result.healthBar;
        movementController = result.movementController;
        entityMovement = result.entityMovement;
        player = result.player;
        npc = new ClassicNpc("Npc1", 100, new Vector2(0, 0), 5, sprites, weaponsprites, 5.0f);
        platformTexture = new Texture("PlateformCyber.png");
        gameData = new GameData();
        inventoryStage = new Stage();
    for (HorizontalPlatform platform : platforms) {
            platform.setImage(platformTexture);
        }
        seringue = new Seringue(player);
        seringue.setPosition(new Vector2(100, 100));
        seringue1 = new Seringue(player);
        seringue1.setPosition(new Vector2(150, 100));
        seringue2 = new Seringue(player);
        seringue2.setPosition(new Vector2(120, 100));
        seringue3 = new Seringue(player);
        seringue3.setPosition(new Vector2(170, 100));

        inventoryStage = new Stage();
        Gdx.input.setInputProcessor(inventoryStage);
    }

    public void setupGameOverScreen() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        // Create and position the "Game Over" label
        Label.LabelStyle fontStyle = new Label.LabelStyle(new BitmapFont(), Color.RED);
        Label gameOverLabel = new Label("Game Over", fontStyle);
        gameOverLabel.setFontScale(5); // Increase the font scale
        float labelWidth = gameOverLabel.getWidth() * gameOverLabel.getFontScaleX();
        float labelHeight = gameOverLabel.getHeight() * gameOverLabel.getFontScaleY();
        gameOverLabel.setPosition((Gdx.graphics.getWidth() - labelWidth) / 2, (Gdx.graphics.getHeight() - labelHeight) / 2);
        stage.addActor(gameOverLabel);

        // Create and position the "Retry" and "Main Menu" buttons
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = new BitmapFont();
        textButtonStyle.fontColor = Color.WHITE;

        TextButton retryButton = new TextButton("Retry", textButtonStyle);
        retryButton.getLabel().setFontScale(3);
        retryButton.setPosition((Gdx.graphics.getWidth() - retryButton.getWidth()) / 2, gameOverLabel.getY() - retryButton.getHeight() - 50);
        retryButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Code to restart the game
            }
        });
        stage.addActor(retryButton);

        TextButton mainMenuButton = new TextButton("Main Menu", textButtonStyle);
        mainMenuButton.getLabel().setFontScale(3);
        mainMenuButton.setPosition((Gdx.graphics.getWidth() - mainMenuButton.getWidth()) / 2, retryButton.getY() - mainMenuButton.getHeight() - 50);
        mainMenuButton.addListener(new ClickListener() {

        });
        stage.addActor(mainMenuButton);

        gameOverScreenSetUp = true;
    }
public void create(Player loadedPlayer) {
  LevelFunctions.InitializationResult result = LevelFunctions.initializeBasics();

  font = result.font;
  camera = result.camera;
  batch = result.batch;
  map = result.map;
  mapRenderer = result.mapRenderer;
  sprites = result.sprites;
  weaponsprites = result.weaponsprites;
  healthBar = result.healthBar;
  movementController = result.movementController;
  entityMovement = result.entityMovement;
  player = loadedPlayer;  // Use the loaded player instead of creating a new one
  npc = new ClassicNpc("Npc1", 100, new Vector2(0, 0), 5, sprites, weaponsprites, 5.0f);
  platformTexture = new Texture("PlateformCyber.png");
  gameData = new GameData();
  seringue = new Seringue(player);
        seringue.setPosition(new Vector2(100, 100));
        seringue1 = new Seringue(player);
        seringue1.setPosition(new Vector2(150, 100));
        seringue2 = new Seringue(player);
        seringue2.setPosition(new Vector2(120, 100));
        seringue3 = new Seringue(player);
        seringue3.setPosition(new Vector2(170, 100));

        inventoryStage = new Stage();
        Gdx.input.setInputProcessor(inventoryStage);
}

@Override
public void render() {
    if (player.isGameOver()) {
        if (!gameOverScreenSetUp) {
            setupGameOverScreen();
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    } else {
        gameOverScreenSetUp = false;

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        TiledMapTileLayer collisionLayer = (TiledMapTileLayer) map.getLayers().get("ColisionLayer");
        TiledMapTileLayer damageLayer = (TiledMapTileLayer) map.getLayers().get("DamageLayer");

        // Movement controllers, update positions, etc.
        movementController.updateMovement();
        entityMovement.updateMovement(npc, player.getPosition());
        player.savePreviousPosition();
        npc.savePreviousPosition();
        movementController.updatePlayerPosition(player, collisionLayer);
        entityMovement.updateNpcPosition(npc, player.getPosition());

        // Basics IMPORTANT Functions
        LevelFunctions.handleKeyPresses(seringue, seringue1, seringue2, seringue3, player);
        LevelFunctions.preventFallOffScreen(player, npc);
        LevelFunctions.updateCamera(camera, player, map);
    gameData = new GameData();
gameData.setHealth(player.getHealth());
gameData.setPosition(player.getPosition());
gameData.setInventory(player.getInventory());
gameData.setEquippedWeapon(player.getEquippedWeapon());
gameData.setLevelNumber(0); // for Level0
LevelFunctions.saveGameIfKeyPressed(gameData);

batch.begin();
// Basics IMPORTANT Functions, that need visual
LevelFunctions.renderMap(batch, camera, mapRenderer);
//LevelFunctions.handlePlatformInteractions(platforms, player, npc);
LevelFunctions.handleDamageLayer(damageLayer, player);
LevelFunctions.handleCollisionLayer(collisionLayer, player, npc);
LevelFunctions.drawHealthBar(batch, player, healthBar, font, camera);
LevelFunctions.drawSeringue(batch, seringue);
LevelFunctions.drawSeringue(batch, seringue1);
LevelFunctions.drawSeringue(batch, seringue2);
LevelFunctions.drawSeringue(batch, seringue3);
        LevelFunctions.drawPlatforms(batch, platforms);
        LevelFunctions.drawPlayerDamage(batch, player);
        LevelFunctions.updateDamageTaken(player);
        
        // Draw entities (Maybe we can do a function for that)
        batch.draw(npc.getCurrentSprite(), npc.getPosition().x, npc.getPosition().y, spriteWidth, spriteWidth);
        LevelFunctions.toggleInventory(inventoryStage, player, camera);

        // End batch
        batch.end();
    }
}

      @Override
      public void dispose() {
        batch.dispose();
        inventoryStage.dispose();
      }
    }

