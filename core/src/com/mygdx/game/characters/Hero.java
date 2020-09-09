package com.mygdx.game.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameScreen;
import com.mygdx.game.Weapon;

public class Hero extends GameCharacter {

    final static int HERO_SIZE_X = 80;
    final static int HERO_SIZE_Y = 75;

    public Hero(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        texture = new Texture("hero.png");
        textureHp = new Texture("bar.png");
        position = new Vector2(200, 200);
        hpMax = 100.0f;
        hp = hpMax;
        moveSpeed = 100.0f;
        weapon = new Weapon("Sword", 50.0f, 0.5f, 4.0f);
    }

    @Override
    public void update(float deltaTime) {
        damageEffectTimer -= deltaTime;
        if (damageEffectTimer < 0.0f) {
            damageEffectTimer = 0.0f;
        }

        float dst = gameScreen.getMonster().getPosition().dst(this.position);
        if (dst < weapon.getAttackRadius()) {
            attackTimer += deltaTime;
            if (attackTimer > weapon.getAttackPeriod()) {
                attackTimer = 0.0f;
                gameScreen.getMonster().takeDamage(weapon.getDamage());
            }
        }


        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            position.x += moveSpeed * deltaTime;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            position.x -= moveSpeed * deltaTime;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            position.y += moveSpeed * deltaTime;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            position.y -= moveSpeed * deltaTime;
        }
        checkScreenBounds();
    }

}
