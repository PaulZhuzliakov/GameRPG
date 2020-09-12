package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

//Класс Map отвечает за задний фон
public class Map {
    public static final int CELLS_X = 16;
    public static final int CELLS_Y = 9;
    public static final int CELLS_SIZE = 80;

    private Texture textureGrass;
    private Texture textureWall;
    private byte[][] data;

    public Map() {
        data = new byte[CELLS_X][CELLS_Y];
        // 0 - проходимая ячейка, 1 - непроходимая. надо поменять на enum
        // назначаем стены
        for (int i = 0; i < 5; i++) {
            data[MathUtils.random(0, CELLS_X - 1)][MathUtils.random(0,CELLS_Y - 1)] = 1;
        }
        textureGrass = new Texture("grass.png");
        textureWall = new Texture("wall.png");
    }

    public boolean isCellPassable(Vector2 position) throws IndexOutOfBoundsException {
        if (position.x < 0.0f || position.x > 1280.0f || position.y < 0.0f || position.y > 720.0f ) {
            return false;
        }
        //целочисленное деление координаты GameCharacter`а на размер квадратика даёт координату этого квадратика в сетке
        return data[(int) (position.x/Map.CELLS_SIZE)] [(int) (position.y/Map.CELLS_SIZE)] == 0;
    }

    public void render(SpriteBatch batch) {
        //замастить фон плиткой
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {
                batch.draw(textureGrass, i * 80, j * 80);
                //ставим стену
                if (data[i][j] == 1) {
                    batch.draw(textureWall, (i * 80)-40, (j * 80)-40);
                }
            }
        }
    }
}
