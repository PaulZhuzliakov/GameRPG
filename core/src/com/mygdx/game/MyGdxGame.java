package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {
    /*
    ==Идеи===
    1. Движение по пикселям
    2. Преграды
    3. Анимация
    4. Пускание снарядов
    5. Хаотичное движение для монстра
    6. + Преследование монстром героя
    7. Аптечки, Монеты, Зелья (все что можно поднять)
    8. Пароаметры героям/монстрам (ХП, крит шанс, скорость)
    9. Система уровней игры
    10. Опыт героя
    11. Оружие
    12. Финальный босс
    13. Драка с монстрами
    14. +Полоска ХП
    15. Привязать логику полоски к ХП героя
     */


	private SpriteBatch batch;
	private Hero hero;
	private Monster monster;

    public Hero getHero() {
        return hero;
    }
    public Monster getMonster() {
        return monster;
    }

    @Override
	//подготовка проекта к работе(инициализация)
	public void create () {
		//batch-область отрисовки
		batch = new SpriteBatch();
		hero = new Hero();
		monster = new Monster(this);
	}

	@Override
	//отрисовка. запускается во внешнем цикле. где-то 30 раз в секунду
	public void render () {
		//сколько времени прошло между предыдущим кадром и новым
		float deltaTime = Gdx.graphics.getDeltaTime();
		update(deltaTime);
		//цвет очистки фона
		Gdx.gl.glClearColor(0, 0, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//отрисовка
		batch.begin();
		hero.render(batch);
		monster.render(batch);
		batch.end();
	}

	//логика игры(расчеты)
	public void update(float deltaTime) {
		hero.update(deltaTime);
		monster.update(deltaTime);
		//расстояние между монстром и героем. через вектора
		float dst = (float) Math.sqrt((Math.pow((hero.getX() - monster.getX()), 2)) + (Math.pow((hero.getY() - monster.getY()), 2)));
		if (dst < 40.0f) {
			//10 единиц урона в секунду
			hero.takeDamage(deltaTime * 10);
		}
    }

	//освобождение ресурсов
	@Override
	public void dispose () {
		batch.dispose();
	}
}
