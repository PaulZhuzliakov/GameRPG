package com.mygdx.game.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameScreen;
import com.mygdx.game.Weapon;

public abstract class GameCharacter {
    GameScreen gameScreen;
    Texture texture;
    Texture textureHp;
    Vector2 position;
    Vector2 direction;
    Vector2 temp;
    float moveSpeed;
    float hp, hpMax;
    float damageEffectTimer;
    float attackTimer;
    Weapon weapon;
    StringBuilder stringHelper;

    public GameCharacter() {
        temp = new Vector2(0,0);
        stringHelper = new StringBuilder();
    }

    //проверка жив ли персонаж
    public boolean isAlive() {
        return hp > 0;
    }

    public Vector2 getPosition() {
        return position;
    }



    public void render(SpriteBatch batch, BitmapFont font24) {
        if (damageEffectTimer > 0.0f) {
            batch.setColor(1,1 - damageEffectTimer,1 - damageEffectTimer,1);
        }
        batch.draw(texture, position.x - 80, position.y - 80);

        batch.setColor(0, 0, 0, 1);
        batch.draw(textureHp, position.x - 80 - 2, position.y + 0 - 2, 80 + 4,12 + 4);

        batch.setColor(1, 0, 0, 1);
        batch.draw(textureHp, position.x - 80, position.y + 0, 0, 0, hp/hpMax*80, 12, 1, 1, 0, 0, 0, 80, 12, false, false);

        batch.setColor(1, 1, 1, 1);

        stringHelper.setLength(0);
        stringHelper.append((int)hp);
        //берем шрифт рисуем в батче HP, текст с левой стороны полосы здоровья, вписано в полоску длинной 80, выравнивание по центру, нет переноса по словам
        font24.draw(batch, stringHelper, position.x-80, position.y+17, 80, 1, false);
    }

    public abstract void update(float deltaTime);

    //запрет на перемещение за пределы экрана
    public void checkScreenBounds() {
        if (position.x > 1280.0f) {
            position.x = 1280.0f;
        }
        if (position.x < 0.0f) {
            position.x = 0.0f;
        }
        if (position.y > 720.0f) {
            position.y = 720.0f;
        }
        if (position.y < 0.0f) {
            position.y = 0.0f;
        }
    }

    public void takeDamage(float damageAmount) {
        hp -= damageAmount;
        damageEffectTimer += 0.5f;
        if (damageEffectTimer > 1.0f) {
            damageEffectTimer = 1.0f;
        }
    }

    public void moveGameCharacter(float deltaTime) {
        //если клетка свободна, перемещаемся в нее
        if (gameScreen.getMap().isCellPassable(temp.set(position).mulAdd(direction, moveSpeed * deltaTime))) {
            position.set(temp);
            //зануляем позицию по Y. движение только по X
        } else if ((gameScreen.getMap().isCellPassable(temp.set(position).mulAdd(direction, moveSpeed * deltaTime).set(temp.x, position.y)))) {
            position.set(temp);
            //зануляем позицию по X. движение только по Y
        } else if ((gameScreen.getMap().isCellPassable(temp.set(position).mulAdd(direction, moveSpeed * deltaTime).set(temp.x, position.y)))) {
            position.set(temp);
        }
    }

}
