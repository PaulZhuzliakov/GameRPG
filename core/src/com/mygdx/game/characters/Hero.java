package com.mygdx.game.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameScreen;
import com.mygdx.game.Weapon;
import com.mygdx.game.tems.Item;

public class Hero extends GameCharacter {
    private int coins;
    private int level;
    private int exp;
    private int[] expTo = {0, 0, 100, 300, 600, 1000, 5000};

    public Hero(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.level = 1;
        texture = new Texture("hero.png");
        textureHp = new Texture("bar.png");
        position = new Vector2(200, 200);
        temp = new Vector2(0, 0);
        direction = new Vector2(0, 0);
        hpMax = 100.0f;
        hp = hpMax;
        moveSpeed = 150.0f;
        weapon = new Weapon("Sword", 100.0f, 0.5f, 5.0f);
    }

    public void renderHUD(SpriteBatch batch, BitmapFont font24) {
        //с помощью font24 нарисовать в batch кол-во монет
        font24.draw(batch, "Knight: Marina" +
                "\nlevel: " + level +
                "\nExp: " + exp + "/" + expTo[level + 1] +
                "\nCoins: " + coins, 20, 700);
    }

    public void killMonster(Monster monster) throws ArrayIndexOutOfBoundsException {
        exp += monster.hpMax * 50;
        if (exp == expTo[expTo.length - 1]) {
            level = 1;
            exp = 0;
        } else {
            //если набран максимальный уровень
            if (exp >= expTo[level + 1]) {
                exp = 0;
                level++;
                hpMax *= 1.1;
                hp = hpMax;
            }
        }

    }

    @Override
    public void update(float deltaTime) {

        if (hp < hpMax) {
            hp += deltaTime / 5;
            if (hp > hpMax) {
                hp = hpMax;
            }
        }

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

    public void useItem(Item it) {
        switch (it.getType()) {
            case COINS:
                coins += MathUtils.random(3, 5);
                break;
        }
        it.deactivateItem();
    }
}
