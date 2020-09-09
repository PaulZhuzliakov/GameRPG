package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {
    /*
    ==Идеи===
    1. +Движение по пикселям
    2. Преграды
    3. Анимация
    4. Пускание снарядов
    5. + Хаотичное движение для монстра
    6. + Преследование монстром героя
    7. Аптечки, Монеты, Зелья (все что можно поднять)
    8. +-Пароаметры героям/монстрам (ХП, крит шанс, скорость)
    9. Система уровней игры
    10. Опыт героя
    11. +Оружие
    12. Финальный босс
    13. +Драка с монстрами
    14. +Полоска ХП
    15. +Привязать логику полоски к ХП героя
    16. +Перенос на вектора
    17. Инвентарь
    18. Отображение текста
    19. Хот бар
    **. Камера
     */


	private SpriteBatch batch;
	private GameScreen gameScreen;

    @Override
	//подготовка проекта к работе(инициализация)
	public void create () {
		//batch-область отрисовки
		this.batch = new SpriteBatch();
		this.gameScreen = new GameScreen(batch);
        this.gameScreen.create();
	}

	@Override
	//отрисовка. запускается во внешнем цикле. где-то 30 раз в секунду
	public void render () {
        gameScreen.render();
	}

	//логика игры(расчеты)
	public void update(float deltaTime) {
        gameScreen.update(deltaTime);
    }

	//освобождение ресурсов
	@Override
	public void dispose () {
		batch.dispose();
	}
}
