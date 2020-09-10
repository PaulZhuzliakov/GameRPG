package com.mygdx.game.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameScreen;
import com.mygdx.game.Weapon;


public class Monster extends GameCharacter {
    private float moveTimer;
    private float activityRadius;

    public Monster(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        texture = new Texture("monster.png");
        textureHp = new Texture("bar.png");

        position = new Vector2(MathUtils.random(0, 1280), MathUtils.random(0, 720));
        while (!gameScreen.getMap().isCellPassable(position)) {
            position = new Vector2(MathUtils.random(0, 1280), MathUtils.random(0, 720));
        }
        //при запуске никуда не пытается идти
        direction = new Vector2(0, 0);
        temp = new Vector2(0, 0);
        moveSpeed = 40.0f;
        activityRadius = 200.0f;
        weapon = new Weapon("Rusty sword", 50.0f, 0.8f, 5.0f);
        hpMax = 20;
        hp = hpMax;

    }

    @Override
    public void update(float deltaTime) {

        damageEffectTimer -= deltaTime;
        if (damageEffectTimer < 0.0f) {
            damageEffectTimer = 0.0f;
        }

        //dst - расстояние между монстром и героем
        float dst = gameScreen.getHero().getPosition().dst(this.position);
        //если герой в радиусе действия, бежим к герою
        if (dst < activityRadius) {
            //direction смотрит в сторону героя. из вектора монстра вычитаем вектор героя и нормируем
            direction.set(gameScreen.getHero().getPosition()).sub(this.position).nor();
            //иначе монстр слоняется без дела
        } else {
            moveTimer -= deltaTime;
            if (moveTimer < 0.0f) {
                moveTimer = MathUtils.random(3.0f, 4.0f);
                //генерация вектора направления движения. и нормирование вектора. вектор должен быть равен 1
                direction.set(MathUtils.random(-1.0f, 1.0f), MathUtils.random(-1.0f, 1.0f)).nor();
            }
        }

        moveGameCharacter(deltaTime);

        if (dst < weapon.getAttackRadius()) {
            attackTimer += deltaTime;
            if (attackTimer >= weapon.getAttackPeriod()) {
                attackTimer = 0.0f;
                gameScreen.getHero().takeDamage(weapon.getDamage());
            }
        }
        checkScreenBounds();
    }

}

