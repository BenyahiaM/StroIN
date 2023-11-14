package com.stroin.game.Level;

import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.TimeUtils;
import com.stroin.game.controllers.MovementController;
import com.stroin.game.data.GameData;
import com.stroin.game.elements.BouleDeFeu;
import com.stroin.game.elements.HorizontalPlatform;
import com.stroin.game.elements.MovablePlatform;
import com.stroin.game.elements.SonicBall;
import com.stroin.game.entity.BossDrake;
import com.stroin.game.entity.Entity;
import com.stroin.game.entity.EntityNpcMovement;
import com.stroin.game.entity.Player;
import com.stroin.game.items.Consumables.Consumable;
import com.stroin.game.items.Consumables.Seringue;

public class LevelFunctions {

private static boolean showInventory = false;

public static void toggleInventory(Stage inventoryStage, Player player, Camera camera) {
    float cameraX = camera.position.x - camera.viewportWidth / 2;
    float cameraY = camera.position.y - camera.viewportHeight / 2;

    if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
        showInventory = !showInventory;
        inventoryStage.clear(); // Clear the inventory stage

        if (showInventory) {
            Table table = new Table();

            float width = 300; 
            float height = 400; 
            float red = 0.2f; 
            float green = 0.2f; 
            float blue = 0.2f; 
            float alpha = 0.8f; 

            Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
            pixmap.setColor(new Color(red, green, blue, alpha));
            pixmap.fill();

            TextureRegionDrawable backgroundDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
            table.setBackground(backgroundDrawable);

            for (Consumable consumable : player.getInventory().getConsomables()) {
                if (consumable != null) {
                    Image itemImage = new Image(consumable.getSprite());
                    itemImage.setSize(150, 150);
                    table.add(itemImage).padTop(10);
                }
            }

            table.pack(); // Update the table's dimensions

            // Placer la table en haut à droite
            float tableX = cameraX + camera.viewportWidth - width;
            float tableY = cameraY + camera.viewportHeight - height;
            table.setPosition(tableX, tableY);

            inventoryStage.getViewport().setCamera(camera);

            inventoryStage.addActor(table);
            Gdx.input.setInputProcessor(inventoryStage);

            pixmap.dispose();
        } else {
            Gdx.input.setInputProcessor(null); // Reset the input processor when the inventory is not shown
        }
    }

    if (showInventory) {
        updateInventoryPosition(inventoryStage, player, cameraX, cameraY);
        inventoryStage.act();
        inventoryStage.draw();
    }
}



private static void updateInventoryPosition(Stage inventoryStage, Player player, float cameraX, float cameraY) {
    Table table = (Table) inventoryStage.getActors().first();

    if (table != null) {
        table.setPosition(cameraX, cameraY);
    }
}

public static void handleKeyPresses(Seringue seringue1, Seringue seringue2, Seringue seringue3, Seringue seringue, Player player) {
    if (Gdx.input.isKeyPressed(Keys.E)) {
        if (!seringue1.isUsed()) {
            seringue1.isPickable(player);
        } else if (!seringue2.isUsed()) {
            seringue2.isPickable(player);
        } else if (!seringue3.isUsed()) {
            seringue3.isPickable(player);
        } else if (!seringue.isUsed()) {
            seringue.isPickable(player);
        }
    } else if (Gdx.input.isKeyJustPressed(Keys.A)) {
        useFirstAvailableSeringue(player);
    } else if (Gdx.input.isKeyPressed(Keys.SPACE)) {
        player.showInventory();
    }
}
public static void saveGameIfKeyPressed(GameData gameData) {
  if (Gdx.input.isKeyJustPressed(Keys.O)) {
      gameData.saveGame();
  }
}

private static void useFirstAvailableSeringue(Player player) {
    for (Consumable consumable : player.getInventory().getConsomables()) {
        if (consumable instanceof Seringue) {
            ((Seringue) consumable).use(player);
                System.out.println("You used a seringue from your inventory.");
                break;
        }
    }

    System.out.println("You don't have a seringue in your inventory.");
}

public static void spawnMovablePlatform(int platformWidth, int platformHeight, List<MovablePlatform> platforms, Texture platformTexture, Camera camera) {
  Random random = new Random();
  int x = 2496;
  int y = random.nextInt(2200 - platformHeight); // Ensure the platform is fully visible

  // Adjust the spawning point if there's a platform too close
  for (MovablePlatform platform : platforms) {
      float distance = Math.abs(platform.getX() - x) + Math.abs(platform.getY() - y);
      if (distance < platformWidth * 2) { // Adjust this value to change the minimum distance between platforms
          System.out.println("A platform is too close to the spawning point. Adjusting the spawning point.");
          if (y < 1200) {
              y += platformHeight * 2; // Move the spawning point up
          } else {
              y -= platformHeight * 2; // Move the spawning point down
          }
          break;
      }
  }

  System.out.println("Spawned platform at " + x + ", " + y);
  float speedX = -20; // Adjust this value to change the speed of the platforms
  float speedY = 0; // Adjust this value to change the vertical speed of the platforms
  MovablePlatform platform = new MovablePlatform("MovablePlatform", x, y, platformWidth, platformHeight, speedX, speedY);
  platforms.add(platform);
  platform.setImage(platformTexture);
}

public static void spawnBouleDeFeu(int platformWidth, int platformHeight, List<BouleDeFeu> platforms, Texture platformTexture, Camera camera) {
  Random random = new Random();
  int x = 2496;
  int y = random.nextInt(Gdx.graphics.getHeight() - platformHeight); // Ensure the platform is fully visible

  // Adjust the spawning point if there's a platform too close
  for (BouleDeFeu platform : platforms) {
      float distance = Math.abs(platform.getX() - x) + Math.abs(platform.getY() - y);
      if (distance < platformWidth * 2) { // Adjust this value to change the minimum distance between platforms
          System.out.println("A platform is too close to the spawning point. Adjusting the spawning point.");
          y += platformHeight * 2; // Move the spawning point up
          break;
      }
  }

  System.out.println("Spawned platform at " + x + ", " + y);
  float speedX = -20; // Adjust this value to change the speed of the platforms
  float speedY = 0; // Adjust this value to change the vertical speed of the platforms
  BouleDeFeu platform = new BouleDeFeu("BouleDeFeu", x, y, platformWidth, platformHeight, speedX, speedY);
  platforms.add(platform);
  platform.setImage(platformTexture);
}

  public static void preventFallOffScreen(Entity... entities) {
    for (Entity entity : entities) {
      if (entity.getPosition().y < 0) {
        entity.getPosition().y = 0;
      }
    }
  }

  public static void updateCamera(Camera camera, Entity player, TiledMap map) {
    float mapWidth = map.getProperties().get("width", Integer.class) * (Integer)map.getProperties().get("tilewidth", Integer.class);
    float mapHeight = map.getProperties().get("height", Integer.class) * (Integer)map.getProperties().get("tileheight", Integer.class);

    float cameraX = Math.min(mapWidth - camera.viewportWidth / 2, Math.max(player.getPosition().x, camera.viewportWidth / 2));
    float cameraY = Math.min(mapHeight - camera.viewportHeight / 2, Math.max(player.getPosition().y, camera.viewportHeight / 2));

    camera.position.set(cameraX, cameraY, 0);
    camera.update();
}

  public static void renderMap(
    SpriteBatch batch,
    OrthographicCamera camera,
    OrthogonalTiledMapRenderer mapRenderer
  ) {
    batch.setProjectionMatrix(camera.combined);
    mapRenderer.setView(camera);
    mapRenderer.render();
  }

public static void handlePlatformInteractions(List<MovablePlatform> platforms, Entity... entities) {
    for (MovablePlatform platform : platforms) {
        for (Entity entity : entities) {
            if (entity.getBounds().overlaps(platform.getBounds())) {
                if (
                    entity.getVelocity() < 0 &&
                    entity.getPosition().y - entity.getBounds().height / 2 < platform.getBounds().y + platform.getBounds().height &&
                    entity.getPosition().y > platform.getBounds().y
                ) {
                    entity.getPosition().y = platform.getBounds().y + platform.getBounds().height;
                    entity.setVelocityY(0);
                } else if (entity.getPosition().y <= platform.getBounds().y) {
                    entity.getPosition().set(entity.getPreviousPosition());
                }
            }
        }
    }
}

  public static void handleDamageLayer(
    TiledMapTileLayer damageLayer,
    Entity player
  ) {
    float offset = 5;
    for (int y = 0; y < damageLayer.getHeight(); y++) {
      for (int x = 0; x < damageLayer.getWidth(); x++) {
        TiledMapTileLayer.Cell cell = damageLayer.getCell(x, y);
        if (cell != null) {
          Rectangle tileBounds = new Rectangle(
            x * damageLayer.getTileWidth() + offset,
            y * damageLayer.getTileHeight() + offset,
            damageLayer.getTileWidth()-2 * offset,
            damageLayer.getTileHeight()- 2 * offset
          );
          if (player.getBounds().overlaps(tileBounds)) {
            player.takeDamage(50);
          }
        }
      }
    }
  }

  public static void handleDamageForBossDrake(BossDrake bossDrake, List<SonicBall> projectiles) {
    Rectangle bossHitbox = bossDrake.getBounds();

    for (SonicBall sonicBall : projectiles) {
        Rectangle sonicBallHitbox = sonicBall.getBounds();

        if (bossHitbox.overlaps(sonicBallHitbox)) {
            bossDrake.takeDamage(1); // Decrease the BossDrake's health by 1
            projectiles.remove(sonicBall); // Remove the SonicBall from the projectiles list
            break;
        }
    }
}

  public static void handleDamageLayerForBouleDeFeu(List<BouleDeFeu> boulesDeFeu, Entity... entities) {
    for (BouleDeFeu bouleDeFeu : boulesDeFeu) {
        for (Entity entity : entities) {
            if (entity.getBounds().overlaps(bouleDeFeu.getBounds())) {
              if (entity instanceof Player) {
                entity.takeDamage(50);
            }
          }
        }
    }
}

  public static void handleCollisionLayer(
    TiledMapTileLayer collisionLayer,
    Entity... entities
  ) {
    for (int y = 0; y < collisionLayer.getHeight(); y++) {
      for (int x = 0; x < collisionLayer.getWidth(); x++) {
        TiledMapTileLayer.Cell cell = collisionLayer.getCell(x, y);
        if (cell != null) {
          Rectangle tileBounds = new Rectangle(
            x * collisionLayer.getTileWidth(),
            y * collisionLayer.getTileHeight(),
            collisionLayer.getTileWidth(),
            collisionLayer.getTileHeight()
          );
          for (Entity entity : entities) {
            if (entity.getBounds().overlaps(tileBounds)) {
              if (
                entity.getVelocity() < 0 &&
                entity.getPosition().y -
                entity.getBounds().height <
                tileBounds.y +
                tileBounds.height &&
                entity.getPosition().y > tileBounds.y
              ) {
                entity.getPosition().y = tileBounds.y + tileBounds.height;
                entity.setVelocityY(0);
              } else if (entity.getPosition().y <= tileBounds.y) {
                entity.getPosition().set(entity.getPreviousPosition());
              }
            }
          }
        }
      }
    }
  }

  public static void moveLayer(TiledMap map, String layerName, float speed, float deltaTime, float screenWidth) {
    TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(layerName);

    if (layer != null) {
      for (int y = 0; y < layer.getHeight(); y++) {
        for (int x = 0; x < layer.getWidth(); x++) {
          TiledMapTileLayer.Cell cell = layer.getCell(x, y);
          if (cell != null) {
            float newX = x * layer.getTileWidth() - speed * deltaTime;
            if (newX < 0) {
              newX = screenWidth;
            }
            cell.setTile(layer.getCell((int) (newX / layer.getTileWidth()), y).getTile());
          }
        }
      }
    } else {
        System.out.println("Layer " + layerName + " not found.");
    }
}

  public static void drawHealthBar(
    SpriteBatch batch,
    Entity player,
    Texture healthBar,
    BitmapFont font,
    Camera camera
  ) {
    // Draw health bar
    float healthPercentage = (float) player.getHealth() / player.getMaxHealth();
    float xOffset = 100; // Adjust this value to move the health bar to the right
    float yOffset = -50; // Adjust this value to move the health bar down
    batch.draw(
      healthBar,
      camera.position.x - camera.viewportWidth / 2 + xOffset,
      camera.position.y + camera.viewportHeight / 2 - healthBar.getHeight() + yOffset,
      Math.round(healthBar.getWidth() * healthPercentage),
      healthBar.getHeight()
    );

    // Draw health text
    font.setColor(Color.BLACK);
    font.draw(
      batch,
      "Health: " + player.getHealth(),
      camera.position.x - camera.viewportWidth / 2 + 60 + xOffset,
      camera.position.y +
      camera.viewportHeight /
      2 -
      healthBar.getHeight() /
      2 + 5 +
       yOffset
    );
  }

  public static void drawBossHealthBar(
    SpriteBatch batch,
    BossDrake bossDrake,
    Texture healthBar,
    BitmapFont font
) {
    // Draw health bar
    float healthPercentage = (float) bossDrake.getHealth() / bossDrake.getMaxHealth();
    float xOffset = bossDrake.getPosition().x - bossDrake.getWidth()/2 - 100; // Position the health bar above the boss
    float yOffset = bossDrake.getPosition().y + bossDrake.getHeight() + 140; // 10 pixels above the boss

    batch.draw(
        healthBar,
        xOffset,
        yOffset,
        Math.round(healthBar.getWidth() * healthPercentage),
        healthBar.getHeight()
    );

    // Draw health text
    font.setColor(Color.BLACK);
    font.draw(
        batch,
        "Boss Health: " + bossDrake.getHealth(),
        xOffset,
        yOffset + healthBar.getHeight() / 2
    );
}

  public static void drawSeringue(SpriteBatch batch, Seringue seringue) {
    if (!seringue.isUsed()) {
      batch.draw(
        seringue.getSprite(),
        seringue.getPosition().x,
        seringue.getPosition().y
      );
    }
  }

  public static void drawPlatforms(
    SpriteBatch batch,
    List<HorizontalPlatform> platforms
  ) {
    for (HorizontalPlatform platform : platforms) {
      platform.draw(batch);
    }
  }

  public static void attackPlayer(
    Player player
  ) {
    if (Gdx.input.isKeyPressed(Keys.F)) {
      player.attack();
    } else {
      player.stopAttack();
    }
  }


  public static void drawPlayerDamage(SpriteBatch batch, Player player) {
    TextureRegion region = player.getCurrentSprite();
    Sprite sprite = new Sprite(region);
    if (player.isDamageTaken()) {
        sprite.setColor(Color.YELLOW);
    } else {
        sprite.setColor(Color.WHITE);
    }
    sprite.setPosition(player.getPosition().x, player.getPosition().y);
    sprite.draw(batch);
}

  public static void updateDamageTaken(Player player) {
      if (TimeUtils.timeSinceNanos(player.getLastDamageTime()) < 2000000000 && TimeUtils.timeSinceNanos(player.getLastDamageTime()) > 0) { // 2 seconds in nanoseconds
        player.setDamageTaken(true);
      } else {
        player.setDamageTaken(false);
      }
  }

public static void displayGameOverLayer() {
    Stage stage = new Stage();
    Gdx.input.setInputProcessor(stage);

    Label.LabelStyle fontStyle = new Label.LabelStyle(new BitmapFont(), Color.RED);
    Label gameOverLabel = new Label("Game Over", fontStyle);
    gameOverLabel.setFontScale(10);
    
    // Calculate the width and height of the label
    float labelWidth = gameOverLabel.getWidth() * gameOverLabel.getFontScaleX();
    float labelHeight = gameOverLabel.getHeight() * gameOverLabel.getFontScaleY();
    
    // Center the label
    gameOverLabel.setPosition((Gdx.graphics.getWidth() - labelWidth) / 2, (Gdx.graphics.getHeight() - labelHeight) / 2);
    
    stage.addActor(gameOverLabel);

    TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
    textButtonStyle.font = new BitmapFont();
    textButtonStyle.fontColor = Color.WHITE;
    
    TextButton mainMenuButton = new TextButton("Main Menu", textButtonStyle);
    mainMenuButton.getLabel().setFontScale(2); // Increase the font scale
    mainMenuButton.setPosition((Gdx.graphics.getWidth() - mainMenuButton.getWidth()) / 2, gameOverLabel.getY() - mainMenuButton.getHeight() - 80);
    stage.addActor(mainMenuButton);
    
    TextButton retryButton = new TextButton("Retry", textButtonStyle);
    retryButton.getLabel().setFontScale(2); // Increase the font scale
    retryButton.setPosition((Gdx.graphics.getWidth() - retryButton.getWidth()) / 2, mainMenuButton.getY() - retryButton.getHeight() - 40);
    stage.addActor(retryButton);
        stage.act();
        stage.draw();
    }

  public static class InitializationResult {

    public BitmapFont font;
    public OrthographicCamera camera;
    public SpriteBatch batch;
    public TiledMap map;
    public OrthogonalTiledMapRenderer mapRenderer;
    public TextureRegion[] sprites;
    public TextureRegion[] weaponsprites;
    public Texture healthBar;
    public MovementController movementController;
    public EntityNpcMovement entityMovement;
    public Player player;
    public Texture bossHealthBar;
  }

  public static InitializationResult initializeBasics() {
    InitializationResult result = new InitializationResult();

    result.font = new BitmapFont();
    result.camera = new OrthographicCamera();
    result.camera.setToOrtho(
      false,
      Gdx.graphics.getWidth(),
      Gdx.graphics.getHeight()
    );
    result.batch = new SpriteBatch();
    result.map = new TmxMapLoader().load("Map1.tmx");
    result.mapRenderer = new OrthogonalTiledMapRenderer(result.map);
    result.sprites = new TextureRegion[16];
    result.weaponsprites = new TextureRegion[16];
    result.healthBar = new Texture(Gdx.files.internal("barre3.png"));
        for (int i = 0; i < 8; i++) {
            Texture weaponTexture = new Texture("spriteweapon" + i + ".png");
            result.weaponsprites[i] = new TextureRegion(weaponTexture);
            result.weaponsprites[i + 8] = new TextureRegion(weaponTexture);
            result.weaponsprites[i + 8].flip(true, false);
        }
    for (int i = 0; i < 8; i++) {
        Texture texture = new Texture("sprite" + i + ".png");
        result.sprites[i] = new TextureRegion(texture);
        result.sprites[i + 8] = new TextureRegion(texture);
        result.sprites[i + 8].flip(true, false);
    }
    result.movementController = new MovementController();
    result.entityMovement = new EntityNpcMovement();
    result.player =
      new Player(
        "Player1",
        100,
        new Vector2(0, 0),
        5,
        result.movementController,
        result.sprites,
        result.weaponsprites,
        5.0f
      );
    return result;
  }

  public static InitializationResult initializeBasics3() {
    InitializationResult result = new InitializationResult();

    result.font = new BitmapFont();
    result.camera = new OrthographicCamera();
    result.camera.setToOrtho(
      false,
      Gdx.graphics.getWidth(),
      Gdx.graphics.getHeight()
    );
    result.batch = new SpriteBatch();
    result.map = new TmxMapLoader().load("MapProcedural.tmx");
    result.mapRenderer = new OrthogonalTiledMapRenderer(result.map);
    result.sprites = new TextureRegion[16];
    result.weaponsprites = new TextureRegion[16];
    result.healthBar = new Texture(Gdx.files.internal("barre3.png"));
        for (int i = 0; i < 8; i++) {
            Texture weaponTexture = new Texture("spriteweapon" + i + ".png");
            result.weaponsprites[i] = new TextureRegion(weaponTexture);
            result.weaponsprites[i + 8] = new TextureRegion(weaponTexture);
            result.weaponsprites[i + 8].flip(true, false);
        }
    for (int i = 0; i < 8; i++) {
        Texture texture = new Texture("sprite" + i + ".png");
        result.sprites[i] = new TextureRegion(texture);
        result.sprites[i + 8] = new TextureRegion(texture);
        result.sprites[i + 8].flip(true, false);
    }
    result.movementController = new MovementController();
    result.entityMovement = new EntityNpcMovement();
    result.player =
      new Player(
        "Player1",
        100,
        new Vector2(0, 0),
        5,
        result.movementController,
        result.sprites,
        result.weaponsprites,
        5.0f
      );
    return result;
  }

    public static InitializationResult initializeBasics2() {
    InitializationResult result = new InitializationResult();

    result.font = new BitmapFont();
    result.camera = new OrthographicCamera();
    result.camera.setToOrtho(
      false,
      Gdx.graphics.getWidth(),
      Gdx.graphics.getHeight()
    );
    result.batch = new SpriteBatch();
    result.map = new TmxMapLoader().load("MapProcedural.tmx");
    result.mapRenderer = new OrthogonalTiledMapRenderer(result.map);
    result.sprites = new TextureRegion[16];
    result.weaponsprites = new TextureRegion[16];
    result.healthBar = new Texture(Gdx.files.internal("barre3.png"));
    result.bossHealthBar = new Texture(Gdx.files.internal("BossBarre.png"));
        for (int i = 0; i < 8; i++) {
            Texture weaponTexture = new Texture("spriteweapon" + i + ".png");
            result.weaponsprites[i] = new TextureRegion(weaponTexture);
            result.weaponsprites[i + 8] = new TextureRegion(weaponTexture);
            result.weaponsprites[i + 8].flip(true, false);
        }
    for (int i = 0; i < 8; i++) {
        Texture texture = new Texture("sprite" + i + ".png");
        result.sprites[i] = new TextureRegion(texture);
        result.sprites[i + 8] = new TextureRegion(texture);
        result.sprites[i + 8].flip(true, false);
    }
    result.movementController = new MovementController();
    result.entityMovement = new EntityNpcMovement();
    result.player =
      new Player(
        "Player1",
        100,
        new Vector2(0, 0),
        5,
        result.movementController,
        result.sprites,
        result.weaponsprites,
        5.0f
      );
    return result;
  }
}
