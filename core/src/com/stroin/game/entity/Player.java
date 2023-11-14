package com.stroin.game.entity;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.stroin.game.controllers.MovementController;
import com.stroin.game.elements.BouleDeFeu;
import com.stroin.game.items.Consumables.Consumable;
import com.stroin.game.items.Inventory.Inventory;
import com.stroin.game.items.Weapons.Weapons;
import com.stroin.game.elements.SonicBall;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Text;

public class Player extends Entity implements Serializable {
    private MovementController movementController;
    private long lastDamageTime;
    protected Weapons equippedWeapon;
    protected boolean damageTaken = false;
    protected boolean gameOver = false;
    private Inventory inventory;
    private boolean attacking = false;
    private List<SonicBall> projectiles;


    public Player(String name, int health, Vector2 position, int speed, MovementController movementController, TextureRegion[] sprites,TextureRegion[] weaponsprites, float velocityY) {
        super(name, health, position, speed,sprites,weaponsprites,velocityY);
        this.movementController = movementController;
        this.inventory = new Inventory();
        this.projectiles = new ArrayList<>();
    }
    public Player(String name, int health, Vector2 position, int speed, MovementController movementController,float velocityY, String spriteBaseName, String weaponSpriteBaseName, int numSprites) {
        super("Joueur", health, position, speed, Entity.loadSprites("sprite",8), Entity.loadSprites("spriteweapon", 8), velocityY);
        this.movementController = movementController;
        this.inventory = new Inventory();
        System.out.println("Player created with the values : " + name + " " + health + " " + position + " " + speed + " " + movementController + " " + velocityY + " " + spriteBaseName + " " + weaponSpriteBaseName + " " + numSprites);
        
    }

    public boolean hasWeapon() {
        if(this.getEquippedWeapon() == null) {
            return false;
        }else{
            System.out.println("You already have a weapon");
            return true;
        }
    }
    @Override
    public void move(boolean b, boolean c) {
        //The player don't move by itself
    };

    @Override
    public void attack() {
        this.attacking = true;
        System.out.println("Player attacks");
        if (this.isMovingRight) {
        SonicBall sonicBall = new SonicBall(
            "SonicBall", 
            (int) this.position.x+50, 
            (int) this.position.y+45, 
            10, // width
            10, // height
            50, // speedX
            0 // speedY
        );
            this.projectiles.add(sonicBall);
            }
        else if (this.isMovingLeft) {
        SonicBall sonicBall = new SonicBall(
            "SonicBall", 
            (int) this.position.x+40, 
            (int) this.position.y+45,  
            10, // width
            10, // height
            -50, // speedX
            0 // speedY
        );
        this.projectiles.add(sonicBall);
        } else {
            if (this.movementController.getLastDirection().equals("right")) {
                SonicBall sonicBall = new SonicBall(
                    "SonicBall", 
                    (int) this.position.x+50, 
                    (int) this.position.y+45, 
                    10, // width
                    10, // height
                    50, // speedX
                    0 // speedY
                );
                this.projectiles.add(sonicBall);
            } else {
                SonicBall sonicBall = new SonicBall(
                    "SonicBall", 
                    (int) this.position.x+40, 
                    (int) this.position.y+45,  
                    10, // width
                    10, // height
                    -50, // speedX
                    0 // speedY
                );
                this.projectiles.add(sonicBall);
            
            }
        }
    }

    public void stopAttack() {
        this.attacking = false;
    }

    @Override
    public TextureRegion getCurrentSprite() {
        TextureRegion sprite = movementController.getCurrentSprite(this , sprites);
        this.width = sprite.getRegionWidth();
        this.height = sprite.getRegionHeight();
        if(this.hasWeapon()){
            for (int i = 0; i < weaponsprites.length; i++) {
                if (sprites[i] == sprite) {
                    sprite = weaponsprites[i];
                    break;
                }
            }
    }
                if(this.attacking){
                // If the player is moving right AND attacking , i will put the sprite number 7 , if it's moving left i will put the sprite number 15
                if (this.isMovingRight) {
                    this.currentSpriteIndex = 7;
                    sprite = sprites[currentSpriteIndex]; // Use the 8th sprite in the array when moving right
                } else if (this.isMovingLeft) {
                    this.currentSpriteIndex = 15; // Use the 16th sprite in the array when moving left
                    sprite = sprites[currentSpriteIndex];
                } else {
                    if (this.movementController.getLastDirection().equals("right")) {
                        this.currentSpriteIndex = 7;
                        sprite = sprites[currentSpriteIndex];
                    } else {
                        this.currentSpriteIndex = 15;
                        sprite = sprites[currentSpriteIndex];
                    }
                }
            }
        

        return sprite;
    }

    public List<SonicBall> getProjectiles() {
        return projectiles;
    }

    @Override
    public void die() {
        gameOver = true;
    }

    public boolean isGameOver() {
        return gameOver;
    }


    @Override
    public int getHealth() {
        return health;
    }


    public void setHealth(int newHealth) {
        if (newHealth > 100) {
            health = 100;
        } else if (newHealth < 0) {
            health = 0;
            die();
        } else {
            health = newHealth;
        }
    }

    public void useConsumable(Consumable consumable) {
    int healthRestore = consumable.getHealthRestore();
    if(this.getHealth() == 100){
        System.out.println("You are already at full health");}
    else{
    setHealth(getHealth() + healthRestore);
    }
}

    @Override
    public void takeDamage(int damage) {
        if (TimeUtils.timeSinceNanos(lastDamageTime) > 2000000000) { // 2 seconds in nanoseconds
            setHealth(getHealth() - damage);
            lastDamageTime = TimeUtils.nanoTime();
        }
    }

    public boolean isDamageTaken() {
        return damageTaken;
    }

    public long getLastDamageTime() {
        return lastDamageTime;
    }

    public void setDamageTaken(boolean damageTaken) {
        this.damageTaken = damageTaken;
    }


    @Override
    public void setDamage(int damage) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setDamage'");
    }


    @Override
    public int getDamage() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDamage'");
    }


    @Override
    public void setEquippedWeapon(Weapons weapon) {
        this.equippedWeapon = weapon;
    }


    @Override
    public Weapons getEquippedWeapon() {
        return equippedWeapon;
    }

    public Inventory getInventory() {
        return inventory;
    }
    public void showInventory() {
         this.inventory.showInventory();
    }


    public void setPosition(Vector2 position) {
        this.position = position;
    }
    public void takeWeapon(Weapons weapon) {
        this.setEquippedWeapon(weapon);
    }
}