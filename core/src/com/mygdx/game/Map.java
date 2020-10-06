package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

//Класс Map отвечает за задний фон
public class Map {
    public static final int CELLS_X = 16;
    public static final int CELLS_Y = 9;
    public static final int CELLS_SIZE = 80;

    private Texture textureGrass;
    private Texture textureWalls;
    private TextureRegion[] regions;
    private byte[][] dataLayer;
    private byte[][] tapeLayer;

    public Map() {
        dataLayer = new byte[CELLS_X][CELLS_Y];
        tapeLayer = new byte[CELLS_X][CELLS_Y];
        // 0 - проходимая ячейка, 1 - непроходимая. надо поменять на enum
        // назначаем стены
        for (int i = 0; i < 5; i++) {
            int cellX = MathUtils.random(0, CELLS_X-1);
            int cellY = MathUtils.random(0, CELLS_Y-1);
            //камень
            dataLayer[cellX][cellY] = 1;
            //тип камня
            tapeLayer[cellX][cellY] = (byte) MathUtils.random(0,3);
        }
        textureGrass = new Texture("grass.png");
        textureWalls = new Texture("walls.png");
        regions = new TextureRegion(textureWalls).split(100, 100)[0];

    }

    public boolean isCellPassable(Vector2 position) {
        if (position.x < 0.0f || position.x > 1280.0f || position.y < 0.0f || position.y > 720.0f ) {
            return false;
        }
        //целочисленное деление координаты GameCharacter`а на размер квадратика даёт координату этого квадратика в сетке
        try {
            return dataLayer[(int) (position.x/Map.CELLS_SIZE)] [(int) (position.y/Map.CELLS_SIZE)] == 0;
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void render(SpriteBatch batch) {
        //отрисовка заднего фона
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {
                batch.draw(textureGrass, i * 80, j * 80);
            }
        }
        //расстоновка препятствий
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {
                if (dataLayer[i][j] == 1) {
                    batch.draw(regions[tapeLayer[i][j]], i * 80 - 40, j * 80 - 40);
                }
            }
        }
    }
}
