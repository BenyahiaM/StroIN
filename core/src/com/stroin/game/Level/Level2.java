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
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.stroin.game.controllers.MovementController;
import com.stroin.game.data.GameData;
import com.stroin.game.elements.HorizontalPlatform;
import com.stroin.game.elements.MovablePlatform;
import com.stroin.game.elements.BouleDeFeu;
import com.stroin.game.elements.SonicBall;
import com.stroin.game.entity.BossDrake;
import com.stroin.game.entity.ClassicNpc;
// import com.stroin.game.entity.EnemyNpc;
import com.stroin.game.entity.EntityNpcMovement;
import com.stroin.game.entity.Player;
import com.stroin.game.items.Consumables.Seringue;
public class Level2 extends ApplicationAdapter implements Level {

  private MovementController movementController;
  private EntityNpcMovement entityMovement;
  private Player player;
  private ClassicNpc npc;
  private BossDrake bossDrake;
  // private EnemyNpc enemyNpc;
  private SpriteBatch batch;
  private TextureRegion[] sprites;
  private Texture healthBar;
  private Texture bossHealthBar;
  private Texture bouleTexture;
  private Texture sonicBallTexture;
  private int spriteWidth = 100;
  private Texture platformTexture;
  private List<HorizontalPlatform> platforms = new ArrayList<>();
  public List<MovablePlatform> platforms2 = new ArrayList<>();
  public List<BouleDeFeu> boules = new ArrayList<>();
  private TiledMap map;
  private OrthogonalTiledMapRenderer mapRenderer;
  private OrthographicCamera camera;
  private Seringue seringue;
  private BitmapFont font;
  private TextureRegion[] weaponsprites;
  float platformSpawnTimer = 0;
  float bouleDeFeuSpawnTimer = 0;
  private float timeSinceLastAttack = 0;

  @Override
  public void create() {
    LevelFunctions.InitializationResult result = LevelFunctions.initializeBasics2();

    font = result.font;
    camera = result.camera;
    batch = result.batch;
    map = result.map;
    mapRenderer = result.mapRenderer;
    sprites = result.sprites;
    healthBar = result.healthBar;
    bossHealthBar = result.bossHealthBar;
    movementController = result.movementController;
    entityMovement = result.entityMovement;
    player = result.player;
    npc = new ClassicNpc("Npc1", 100, new Vector2(0, 0), 5, sprites, weaponsprites, 5.0f);
    platformTexture = new Texture("PlateformCyber.png");
    bouleTexture = new Texture("boule.png");
    sonicBallTexture = new Texture("SonicBall.png");
    TextureRegion[] bossDrakeSprites = new TextureRegion[3];
    bossDrakeSprites[0] = new TextureRegion(new Texture(Gdx.files.internal("BossDrake1.png")));
    bossDrakeSprites[1] = new TextureRegion(new Texture(Gdx.files.internal("BossDrake2.png")));
    bossDrakeSprites[2] = new TextureRegion(new Texture(Gdx.files.internal("BossDrake3.png")));

    bossDrake = new BossDrake("BossDrake", 1000, new Vector2(1800, 2750), 10, bossDrakeSprites, 0);

    for (HorizontalPlatform platform : platforms) {
      platform.setImage(platformTexture);
    }
    for (MovablePlatform platform : platforms2) {
      platform.setImage(platformTexture);
    }
    seringue = new Seringue(player);
    seringue.setPosition(new Vector2(100, 100));
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
    TiledMapTileLayer plateforme1Layer = (TiledMapTileLayer) map.getLayers().get("Plateforme1Layer");
    TiledMapTileLayer plateforme2Layer = (TiledMapTileLayer) map.getLayers().get("Plateforme2Layer");
    //TiledMapTileLayer damageLayer = (TiledMapTileLayer) map.getLayers().get("DamageLayer");
    //Movement controllers
    platformSpawnTimer += Gdx.graphics.getDeltaTime();
    bouleDeFeuSpawnTimer += Gdx.graphics.getDeltaTime();

    if (platformSpawnTimer > 0.2) { // Spawn a new platform every 0.25 seconds
        LevelFunctions.spawnMovablePlatform(150,50,platforms2,platformTexture, camera);
        platformSpawnTimer = 0;
    }

    if (bouleDeFeuSpawnTimer > 2) { // Spawn a new BouleDeFeu every 1 second
        LevelFunctions.spawnBouleDeFeu(150,50,boules,bouleTexture, camera);
        bouleDeFeuSpawnTimer = 0;
    }
    bossDrake.update(Gdx.graphics.getDeltaTime());
    timeSinceLastAttack += Gdx.graphics.getDeltaTime();
    if (timeSinceLastAttack >= 1) {
      bossDrake.attack();
      timeSinceLastAttack = 0;
    }

    float delta = Gdx.graphics.getDeltaTime();

    bossDrake.moveUp(delta);
    bossDrake.moveDown(delta);

    movementController.updateMovement();
    entityMovement.updateMovement(npc, player.getPosition());
    player.savePreviousPosition();
    npc.savePreviousPosition();
    movementController.updatePlayerPosition(player, plateforme1Layer, platforms2);
    entityMovement.updateNpcPosition(npc, player.getPosition());

    //Basics IMPORTANTS Functions
    LevelFunctions.handleKeyPresses(seringue, seringue, seringue, seringue , player);
    LevelFunctions.preventFallOffScreen(player, npc);
    LevelFunctions.updateCamera(camera, player, map);
    batch.begin();
    //Basics IMPORTANTS Functions , that need visual
    LevelFunctions.renderMap(batch, camera, mapRenderer);
    LevelFunctions.handlePlatformInteractions(platforms2, player, npc);
    LevelFunctions.attackPlayer(player);
    //LevelFunctions.handleDamageLayer(damageLayer, player);
    LevelFunctions.handleDamageLayerForBouleDeFeu(boules, player);
    LevelFunctions.handleDamageLayerForBouleDeFeu(bossDrake.getProjectiles(), player);
    LevelFunctions.handleDamageForBossDrake(bossDrake, player.getProjectiles());
    LevelFunctions.handleCollisionLayer(plateforme1Layer, player, npc);
    LevelFunctions.handleCollisionLayer(plateforme2Layer, player, npc);
    LevelFunctions.drawHealthBar(batch, player, healthBar, font, camera);
    LevelFunctions.drawBossHealthBar(batch, bossDrake, healthBar, font);
    LevelFunctions.drawSeringue(batch, seringue);
    LevelFunctions.drawPlatforms(batch, platforms);
    // LevelFunctions.drawPlayerDamage(batch, player);
    //Draw entities (Maybe we can do a function for that)
    batch.draw(player.getCurrentSprite(), player.getPosition().x, player.getPosition().y, spriteWidth, spriteWidth);
    batch.draw(npc.getCurrentSprite(), npc.getPosition().x, npc.getPosition().y, spriteWidth, spriteWidth);
    List<MovablePlatform> toRemove = new ArrayList<>();
    for (MovablePlatform platform : platforms2) {
        platform.update(Gdx.graphics.getDeltaTime());
        float leftEdgeOfScreen = camera.position.x - camera.viewportWidth / 2;
        if (platform.getX() + platform.getWidth() < leftEdgeOfScreen) {
            toRemove.add(platform);
        } else {
            platform.draw(batch);
        }
    }
    
    System.out.println("Before removal: " + platforms2.size());
    platforms2.removeAll(toRemove);
    System.out.println("After removal: " + platforms2.size());
    
    List<BouleDeFeu> toRemove2 = new ArrayList<>();
    for (BouleDeFeu boule : boules) {
      boule.update(Gdx.graphics.getDeltaTime());
      float leftEdgeOfScreen = camera.position.x - camera.viewportWidth / 2;
      if (boule.getX() + boule.getWidth() < leftEdgeOfScreen) {
        toRemove2.add(boule);
      } else {
        boule.draw(batch);
      }
    }
    TextureRegion currentSprite = bossDrake.getSprites()[bossDrake.getCurrentSpriteIndex()];
    float scale = 2f;
    batch.draw(
      currentSprite,
      bossDrake.getPosition().x,
      bossDrake.getPosition().y,
      currentSprite.getRegionWidth() * scale, // Scale the width
      currentSprite.getRegionHeight() * scale // Scale the height
  );
  for (SonicBall sonicBall : player.getProjectiles()) {
    sonicBall.update(Gdx.graphics.getDeltaTime());
    sonicBall.setImage(sonicBallTexture);
    sonicBall.draw(batch);
}
  for (BouleDeFeu boule : bossDrake.getProjectiles()) {
    boule.update(Gdx.graphics.getDeltaTime());
    boule.setImage(bouleTexture);
    boule.draw(batch);
  }

  batch.end();
}

  @Override
  public void dispose() {
    batch.dispose();
  }
}
