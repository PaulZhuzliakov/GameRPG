package com.mygdx.game.tems;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public class ItemsEmitter {
    private FlyingText[] items;
    private Texture texture;
    private TextureRegion[] regions;

    public FlyingText[] getItems() {
        return items;
    }

    public ItemsEmitter() {
        //лучше использовать Паттерн пул объектов
        items = new FlyingText[50];
        for (int i = 0; i < items.length; i++) {
            items[i] = new FlyingText();
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

    public void generateRandomItem(float x, float y, float count, float probability) {
        for (int q = 0; q < count; q++) {
            float n = MathUtils.random(0.0f, 1.0f);
            if (n <= probability) {
                //длинна массива значений enum`а Type
                int type = MathUtils.random(0, FlyingText.Type.values().length - 1);
                for (int i = 0; i < items.length; i++) {
                    if (!items[i].isActive()) {
                        items[i].setUpItem(x, y, FlyingText.Type.values()[type]);
                        break;
                    }
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
