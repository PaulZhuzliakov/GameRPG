package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Monster {
    private Texture texture;
    private float x;
    private float y;
    private float moveSpeed;
    private float activityRadius;

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    //ссылка на игру для монстра
    private MyGdxGame game;

    public Monster(MyGdxGame game) {
        this.texture = new Texture("monster.png");
        this.x = 400.0f;
        this.y = 400.0f;
        this.moveSpeed = 40.0f;
        this.activityRadius = 200;
        this.game = game;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    public void update(float deltaTime) {
        //расстояние между монстром и героем
        float dst = (float) Math.sqrt((Math.pow((game.getHero().getX() - this.x), 2)) + (Math.pow((game.getHero().getY() - this.y), 2)));
        if (dst <= activityRadius) {
            //если монстр левее, то он идёт вправо и т.д.
            if (this.x < game.getHero().getX()) {
                x += moveSpeed * deltaTime;
            }
            if (this.x > game.getHero().getX()) {
                x -= moveSpeed * deltaTime;
            }
            if (this.y < game.getHero().getY()) {
                y += moveSpeed * deltaTime;
            }
            if (this.y > game.getHero().getY()) {
                y -= moveSpeed * deltaTime;
            }
        } else {
            //просто бежит вправо
            x += moveSpeed * deltaTime;
            if (x > 1280.0f) {
                x = 0.0f;
            }
        }
    }

}
