package com.stroin.game.Level;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.stroin.game.controllers.MovementController;
import com.stroin.game.data.GameData;
import com.stroin.game.elements.HorizontalPlatform;
import com.stroin.game.entity.ClassicNpc;
import com.stroin.game.entity.EntityNpcMovement;
import com.stroin.game.entity.Player;
import com.stroin.game.items.Consumables.Seringue;
import com.stroin.game.items.Weapons.Chaos;
public class Level1 extends ApplicationAdapter implements Level {

  private MovementController movementController;
  private EntityNpcMovement entityMovement;
  private Player player;
  private ClassicNpc npc;
  private SpriteBatch batch;
  private TextureRegion[] sprites;
  private TextureRegion[] weaponsprites;
  private TextureRegion currentSprite;
  private Texture healthBar;
  private int spriteWidth = 100;
  private TextureRegion currentSpriteNpc;
  private Texture platformTexture;
  private List<HorizontalPlatform> platforms = new ArrayList<>();
  private TiledMap map;
  private OrthogonalTiledMapRenderer mapRenderer;
  private OrthographicCamera camera;
  private Seringue seringue;
  private BitmapFont font;

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
    for (HorizontalPlatform platform : platforms) {
      platform.setImage(platformTexture);
    }
    seringue = new Seringue(player);
    result.player.setEquippedWeapon(new Chaos());

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
  GameData gameData = new GameData();
  seringue = new Seringue(player);
        seringue.setPosition(new Vector2(100, 100));
        Seringue seringue1 = new Seringue(player);
        seringue1.setPosition(new Vector2(150, 100));
        Seringue seringue2 = new Seringue(player);
        seringue2.setPosition(new Vector2(120, 100));
        Seringue seringue3 = new Seringue(player);
        seringue3.setPosition(new Vector2(170, 100));

        Stage inventoryStage = new Stage();
        Gdx.input.setInputProcessor(inventoryStage);
}

  @Override
  public void render() {
    Gdx.gl.glClearColor(1, 1, 1, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    TiledMapTileLayer collisionLayer = (TiledMapTileLayer) map.getLayers().get("ColisionLayer");
    TiledMapTileLayer damageLayer = (TiledMapTileLayer) map.getLayers().get("DamageLayer");
    //Movement controllers
    movementController.updateMovement();
    entityMovement.updateMovement(npc, player.getPosition());
    player.savePreviousPosition();
    npc.savePreviousPosition();
    movementController.updatePlayerPosition(player, collisionLayer);
    entityMovement.updateNpcPosition(npc, player.getPosition());

    //Basics IMPORTANTS Functions
    LevelFunctions.handleKeyPresses(seringue, seringue, seringue, seringue, player);
    LevelFunctions.preventFallOffScreen(player, npc);
    LevelFunctions.updateCamera(camera, player, map);
    batch.begin();
    //Basics IMPORTANTS Functions , that need visual
    LevelFunctions.renderMap(batch, camera, mapRenderer);
    //LevelFunctions.handlePlatformInteractions(platforms, player, npc);
    LevelFunctions.handleDamageLayer(damageLayer, player);
    LevelFunctions.handleCollisionLayer(collisionLayer, player, npc);
    LevelFunctions.drawHealthBar(batch, player, healthBar, font, camera);
    // LevelFunctions.drawSeringue(batch, seringue);
    LevelFunctions.drawPlatforms(batch, platforms);
    // LevelFunctions.drawPlayerDamage(batch, player);
    //Draw entities (Maybe we can do a function for that)
    batch.draw(player.getCurrentSprite(), player.getPosition().x, player.getPosition().y, spriteWidth, spriteWidth);
    batch.draw(npc.getCurrentSprite(), npc.getPosition().x, npc.getPosition().y, spriteWidth, spriteWidth);
    // End batch
    batch.end();
  }

  @Override
  public void dispose() {
    batch.dispose();
  }
}
