package com.stroin.game.items.Inventory;

import com.stroin.game.items.Consumables.Consumable;
import java.io.Serializable;

public class Inventory implements Serializable {

    protected Consumable[] consomables = new Consumable[10];

    public Inventory() {

    }

    public void addConsumable(Consumable consumable) {
        for (int i = 0; i < consomables.length; i++) {
            if (consomables[i] == null) {
                consomables[i] = consumable;
                break;
            }
        }
    }

    public void removeConsumable(Consumable consumable) {
        for (int i = 0; i < consomables.length; i++) {
            if (consomables[i] == consumable) {
                consomables[i] = null;
                break;
            }
        }
    }

    public Consumable[] getConsomables() {
        return consomables;
    }

public void showInventory() {
    System.out.println("Showing inventory:");

    for (int i = 0; i < consomables.length; i++) {
        if (consomables[i] != null) {
            System.out.println(consomables[i].getName());
        }
    }
}
    
}
