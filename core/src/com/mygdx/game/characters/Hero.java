package com.mygdx.game.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameScreen;
import com.mygdx.game.Weapon;

public class Hero extends GameCharacter {

    public Hero(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        texture = new Texture("hero.png");
        textureHp = new Texture("bar.png");
        position = new Vector2(200, 200);
        temp = new Vector2(0,0);
        direction = new Vector2(0, 0);
        hpMax = 100.0f;
        hp = hpMax;
        moveSpeed = 100.0f;
        weapon = new Weapon("Sword", 70.0f, 0.5f, 5.0f);
    }

    @Override
    public void update(float deltaTime) {

        damageEffectTimer -= deltaTime;
        if (damageEffectTimer < 0.0f) {
            damageEffectTimer = 0.0f;
        }

        //поиск ближайшего монстра
        Monster nearestMonster = null;
        float minDist = Float.MAX_VALUE;
        for (int i = 0; i < gameScreen.getAllMonsters().size(); i++) {
            float dst = gameScreen.getAllMonsters().get(i).getPosition().dst(this.position);
            if (dst < minDist) {
                minDist = dst;
                nearestMonster = gameScreen.getAllMonsters().get(i);
            }
        }

        //атака ближайшего монстра, если он в радиусе досягаемости оружия
        if (nearestMonster != null && minDist < weapon.getAttackRadius()) {
            attackTimer += deltaTime;
            if (attackTimer > weapon.getAttackPeriod()) {
                attackTimer = 0.0f;
                nearestMonster.takeDamage(weapon.getDamage());
            }
        }
        //герой никуда самостоятельно не собирается
        direction.set(0, 0);

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            direction.x = 1.0f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            direction.x = -1.0f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            direction.y = 1.0f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            direction.y = -1.0f;
        }

        moveGameCharacter(deltaTime);

        checkScreenBounds();
    }

}
