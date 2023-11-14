package com.stroin.game.elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class HorizontalPlatform extends Platform {
private Texture platformTexture;

    public HorizontalPlatform(String name, int x, int y, int width, int height) {
        super(name, x, y, width, height);
        
    }

    @Override
    public void move() {
        // This platform doesn't move
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
    public Rectangle getBounds() {
    return new Rectangle(x, y, width, height);
}
    public void setImage(Texture platformTexture) {
        this.platformTexture = platformTexture;
    }

    public Texture getImage() {
        return this.platformTexture;
    }
    
    public void draw(SpriteBatch batch) {
        if (platformTexture != null) {
            batch.draw(platformTexture, x, y, width, height);
        }
    }

}