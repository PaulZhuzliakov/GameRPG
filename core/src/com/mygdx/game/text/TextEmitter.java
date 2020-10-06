package com.mygdx.game.text;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public class TextEmitter {
    private FlyingText[] items;

    public FlyingText[] getItems() {
        return items;
    }

    public TextEmitter() {
        items = new FlyingText[50];
        for (int i = 0; i < items.length; i++) {
            items[i] = new FlyingText();
        }
    }

    public void render(SpriteBatch batch, BitmapFont font24) {
        for (int i = 0; i < items.length; i++) {
            if (items[i].isActive()) {
                font24.draw(batch, items[i].getText(), items[i].getPosition().x, items[i].getPosition().y);
            }
        }
    }

    //из всех подсказок находим неактивную и активируем её
    public void setUpItem(float x, float y, StringBuilder text) {
        for (int i = 0; i < items.length; i++) {
            if (!items[i].isActive()) {
                items[i].setUpItem(x, y, text);
                break;
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
