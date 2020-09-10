package com.mygdx.game.tems;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;
import java.util.List;

public class ItemsEmitter {
    private Item[] items;
    private Texture texture;
    private TextureRegion[] regions;

    public ItemsEmitter() {
        //лучше использовать Паттерн пул объектов
        items = new Item[50];
        for (int i = 0; i < items.length; i++) {
            items[i] = new Item();
        }
        texture = new Texture("items.png");
        regions = new TextureRegion(texture).split(32, 32)[0];
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < items.length; i++) {
            if (items[i].isActive()) {
                //index-индекс картинки. например, монетка
                batch.draw(regions[items[i].getType().index], items[i].getPosition().x - 16, items[i].getPosition().y - 16);
            }
        }
    }

    public void generateRandomItem(float x, float y) {
        float chance = 1.0f;
        float n = MathUtils.random(0.0f, 1.0f);
        if (n <= chance) {
            //длинна массива значений enum`а Type
            int type = MathUtils.random(0, Item.Type.values().length - 1);
            for (int i = 0; i < items.length; i++) {
                if (!items[i].isActive()) {
                    items[i].setUpItem(x, y, Item.Type.values()[type]);
                    break;
                }
            }
        }
    }

    public void update(float deltaTime) {
        for (int i = 0; i < items.length; i++) {
            if (items[i].isActive()) {
                items[i].update(deltaTime);
            }
        }
    }

}
