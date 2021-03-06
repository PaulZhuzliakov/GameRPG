package com.mygdx.game.tems;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class FlyingText {
    public enum Type {
        COINS(0), MEDKIT(1);

        int index;

        Type(int index) {
            this.index = index;
        }
    }

    private Vector2 position;
    private Vector2 velocity;
    private Type type;
    private float time;
    private float timeMax;
    private boolean active;

    public boolean isActive() {
        return active;
    }

    public void deactivateItem() {
        this.active = false;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Type getType() {
        return type;
    }

    public FlyingText() {
        this.position = new Vector2(0,0);
        this.velocity = new Vector2(0,0);
        this.type = Type.COINS;
        this.timeMax = 5.0f;
        this.time = 0.0f;
        this.active = false;
    }

    //стандартная структура переиспользуемых объектов
    public void setUpItem(float x, float y, Type type) {
        this.position.set(x, y);
        this.velocity.set(MathUtils.random(-50, 50), MathUtils.random(-50, 50));
        this.type = type;
        //сбросить время. иначе вновь созданный Item будет закончившимся
        this.time = 0.0f;
        this.active = true;
    }

    public void update(float deltaTime) {
        time += deltaTime;
        position.mulAdd(velocity, deltaTime);
        if (time > timeMax) {
            deactivateItem();
        }
    }
}
