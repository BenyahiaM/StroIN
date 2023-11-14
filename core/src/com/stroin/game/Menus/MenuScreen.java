package com.stroin.game.Menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.stroin.game.Level.Level0;
import com.stroin.game.Level.Level1;
import com.stroin.game.Level.Level2;
import com.stroin.game.StroIN;
import com.stroin.game.data.GameData;

public class MenuScreen implements Screen {

  private Stage stage;
  private StroIN game;

  public MenuScreen(StroIN game) {
    this.game = game;
  }

  @Override
  public void show() {
    stage = new Stage();
    Gdx.input.setInputProcessor(stage);

    LabelStyle labelStyle = new LabelStyle();
    labelStyle.font = new BitmapFont();
    labelStyle.font.getData().setScale(5, 5);

    Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
    pixmap.setColor(Color.YELLOW);
    pixmap.fill();
    Texture texture = new Texture(pixmap);
    pixmap.dispose();
    TextureRegionDrawable drawable = new TextureRegionDrawable(
      new TextureRegion(texture)
    );

    final TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
    buttonStyle.font = labelStyle.font;
    buttonStyle.up = drawable;

    final TextButton playButton = new TextButton("Play", buttonStyle);
    playButton.pack();
    playButton.setScale(1.2f);
    playButton.setSize(
      playButton.getWidth() * playButton.getScaleX(),
      playButton.getHeight() * playButton.getScaleY()
    );
    playButton.getLabel().setAlignment(Align.center);
    final TextButton exitButton = new TextButton("Exit", buttonStyle);
    exitButton.pack();
    exitButton.setScale(1.2f);
    exitButton.setSize(
      exitButton.getWidth() * exitButton.getScaleX(),
      exitButton.getHeight() * exitButton.getScaleY()
    );

    playButton.setPosition(
      Gdx.graphics.getWidth() / 2 - playButton.getWidth() / 2,
      Gdx.graphics.getHeight() / 2
    );
    exitButton.setPosition(
      Gdx.graphics.getWidth() / 2 - exitButton.getWidth() / 2,
      Gdx.graphics.getHeight() / 2 - playButton.getHeight() - 10
    );
    final TextButton loadSaveButton = new TextButton("Load Save", buttonStyle);
    loadSaveButton.pack();
    loadSaveButton.setScale(1.2f);
    loadSaveButton.setSize(
      loadSaveButton.getWidth() * loadSaveButton.getScaleX(),
      loadSaveButton.getHeight() * loadSaveButton.getScaleY()
    );
    loadSaveButton.setPosition(
      Gdx.graphics.getWidth() / 2 - loadSaveButton.getWidth() / 2,
      Gdx.graphics.getHeight() /
      2 -
      playButton.getHeight() -
      exitButton.getHeight() -
      20
    );
 
    stage.addActor(loadSaveButton);

    stage.addActor(playButton);
    stage.addActor(exitButton);
    float buttonAdjustment = 50; // Adjust this value as needed

    playButton.setPosition(
        Gdx.graphics.getWidth() / 2 - playButton.getWidth() / 2,
        Gdx.graphics.getHeight() / 2 - buttonAdjustment
    );
    
    exitButton.setPosition(
        Gdx.graphics.getWidth() / 2 - exitButton.getWidth() / 2,
        Gdx.graphics.getHeight() / 2 - playButton.getHeight() - 10 - buttonAdjustment
    );
    
    loadSaveButton.setPosition(
        Gdx.graphics.getWidth() / 2 - loadSaveButton.getWidth() / 2,
        Gdx.graphics.getHeight() / 2 - playButton.getHeight() - exitButton.getHeight() - 20 - buttonAdjustment
    );
    playButton.addListener(
      new ClickListener() {
        @Override
        public void enter(
          InputEvent event,
          float x,
          float y,
          int pointer,
          Actor fromActor
        ) {
          playButton.setColor(Color.RED); // Change the color to red when the mouse enters the button's area
        }

        @Override
        public void exit(
          InputEvent event,
          float x,
          float y,
          int pointer,
          Actor toActor
        ) {
          playButton.setColor(Color.WHITE); // Change the color back to white when the mouse exits the button's area
        }

        @Override
        public void clicked(InputEvent event, float x, float y) {
          playButton.remove();
          exitButton.remove();
          loadSaveButton.remove();

          TextButton level0Button = new TextButton("Level 0", buttonStyle);
          TextButton level1Button = new TextButton("Level 1", buttonStyle);
          TextButton level2Button = new TextButton("Level 2", buttonStyle);

          // Position the buttons
          level0Button.setPosition(
            Gdx.graphics.getWidth() / 2 - level0Button.getWidth() / 2,
            Gdx.graphics.getHeight() / 2
          );
          level1Button.setPosition(
            Gdx.graphics.getWidth() / 2 - level1Button.getWidth() / 2,
            Gdx.graphics.getHeight() / 2 - level0Button.getHeight() - 10
          );
          level2Button.setPosition(
            Gdx.graphics.getWidth() / 2 - level2Button.getWidth() / 2,
            Gdx.graphics.getHeight() /
            2 -
            level0Button.getHeight() -
            level1Button.getHeight() -
            20
          );

          stage.addActor(level0Button);
          stage.addActor(level1Button);
          stage.addActor(level2Button);

          level0Button.addListener(
            new ClickListener() {
              @Override
              public void clicked(InputEvent event, float x, float y) {
                game.levelManager.setLevel(new Level0());
                game.setScreen(new GameScreen(game));
              }
            }
          );

          level1Button.addListener(
            new ClickListener() {
              @Override
              public void clicked(InputEvent event, float x, float y) {
                game.levelManager.setLevel(new Level1());
                game.setScreen(new GameScreen(game));
              }
            }
          );

          level2Button.addListener(
            new ClickListener() {
              @Override
              public void clicked(InputEvent event, float x, float y) {
                game.levelManager.setLevel(new Level2());
                game.setScreen(new GameScreen(game));
              }
            }
          );
        }
      }
    );
    // exitButton.addListener(
    //   new ClickListener() {
    //     @Override
    //     public void clicked(InputEvent event, float x, float y) {
    //       Gdx.app.exit();
    //     }
    //   }
    // );
    playButton.setPosition(
      Gdx.graphics.getWidth() / 2 - playButton.getWidth() / 2,
      Gdx.graphics.getHeight() / 2
    );
    exitButton.setPosition(
      Gdx.graphics.getWidth() / 2 - exitButton.getWidth() / 2,
      Gdx.graphics.getHeight() / 2 - playButton.getHeight() - 10
    );

    loadSaveButton.addListener(
      new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
          // Switch to the LoadSaveScreen
          game.setScreen(new LoadSaveScreen(game));
        }
      }
    );
    stage.addActor(playButton);
    stage.addActor(exitButton);
  }
  ShapeRenderer shapeRenderer = new ShapeRenderer();
  @Override
  public void render(float delta) {
    
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    stage.act(delta);
    stage.draw();
    
  }

  @Override
  public void resize(int width, int height) {}

  @Override
  public void pause() {}

  @Override
  public void resume() {}

  @Override
  public void hide() {}

  @Override
  public void dispose() {
    stage.dispose();
  }
}
