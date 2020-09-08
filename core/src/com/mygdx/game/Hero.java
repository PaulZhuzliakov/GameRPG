package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Hero {
    private Texture texture;
    private Texture textureHp;
    private float x;
    private float y;
    private float moveSpeed;
    private float hp, hpMax;

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Hero() {
        texture = new Texture("knight.png");
        textureHp = new Texture("bar.png");
        x = 200.0f;
        y = 200.0f;
        hpMax = 100.0f;
        hp = hpMax;
        moveSpeed = 100.0f;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y);
        //окрасить полосу здоровья
        batch.setColor(1,0,0,1);
        //привязать полосу к ХП, через масштабирование картинки
        batch.draw(textureHp, x, y+80, 0,0,hp, 20, 1, 1,0, 0,0,80,20,false,false);
        //сбросить окрас полосы здоровья
        batch.setColor(1,1,1,1);
    }

    public void takeDamage(float damageAmount) {
        hp -= damageAmount;
    }

    public void update(float deltaTime) {
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            x += moveSpeed * deltaTime;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            x -= moveSpeed * deltaTime;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            y += moveSpeed * deltaTime;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            y -= moveSpeed * deltaTime;
        }
    }

}
