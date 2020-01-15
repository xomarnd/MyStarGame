package ru.geekbrains.mystargame.screen;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;


import java.util.List;

import ru.geekbrains.mystargame.base.BaseScreen;
import ru.geekbrains.mystargame.math.Rect;
import ru.geekbrains.mystargame.pool.BulletPool;
import ru.geekbrains.mystargame.pool.EnemyPool;
import ru.geekbrains.mystargame.sprite.ButtonAttak;
import ru.geekbrains.mystargame.sprite.ButtonDodge;
import ru.geekbrains.mystargame.sprite.ButtonSound;
import ru.geekbrains.mystargame.sprite.IndicatorStamina;
import ru.geekbrains.mystargame.sprite.MainShip;
import ru.geekbrains.mystargame.sprite.Star;
import ru.geekbrains.mystargame.utils.EnemyGenerator;
import ru.geekbrains.mystargame.sprite.Enemy;
import ru.geekbrains.mystargame.sprite.Bullet;




public class GameScreen extends BaseScreen {
    private TextureAtlas atlas;
    private TextureAtlas atlasStar;
    private TextureAtlas atlasMenu;

    private MainShip mainShip;

    private BulletPool bulletPool;
    private EnemyPool enemyPool;

    private EnemyGenerator enemyGenerator;


    //Объявление переменных звука
    private Music music;
    private Sound laserSound;
    private Sound bulletSound;
    //объявляем объект звука попадания снаряда в корабль
    private Sound shellHitSound;

    //Объявление кнопок интерфейса
    private ButtonDodge buttonDodge;
    private ButtonAttak buttonAttak;
    private ButtonSound buttonSound;
    private IndicatorStamina indicatorStamina;
    private Star[] stars;

    @Override
    public void show() {
        super.show();
        //Иинициация фоновой музыки
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
        music.play();
        //Инициация боевых звуков
        laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        shellHitSound = Gdx.audio.newSound(Gdx.files.internal("sounds/shell-hit.mp3"));

        atlas = new TextureAtlas(Gdx.files.internal("textures/mainAtlas.tpack"));
        atlasStar = new TextureAtlas(Gdx.files.internal("textures/stars.tpack"));
        atlasMenu = new TextureAtlas(Gdx.files.internal("textures/atlasmenu.tpack"));

        buttonDodge = new ButtonDodge(atlasMenu);
        buttonAttak = new ButtonAttak(atlasMenu);

        indicatorStamina = new IndicatorStamina(atlasMenu);
        buttonSound = new ButtonSound(atlasMenu);

        //инициируем обьект коробля
        bulletPool = new BulletPool();
        mainShip = new MainShip(atlas, bulletPool, laserSound);
        enemyPool = new EnemyPool(bulletPool, bulletSound, worldBounds);
        enemyGenerator = new EnemyGenerator(atlas, enemyPool, worldBounds);

        stars = new Star[64];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlasStar);
        }

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollisions();
        freeAllDestroyed();
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        indicatorStamina.resize(worldBounds);
        buttonSound.resize(worldBounds);
        buttonDodge.resize(worldBounds);
        buttonAttak.resize(worldBounds);
        super.resize(worldBounds);


        mainShip.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
    }
    /**
     * Метод освобождения памяти от объектов.
     */
    @Override
    public void dispose() {
        atlas.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        music.dispose();
        laserSound.dispose();
        bulletSound.dispose();
        shellHitSound.dispose();
        super.dispose();
    }
    private void freeAllDestroyed() {
        bulletPool.freeAllDestroyedActiveObjects();
        enemyPool.freeAllDestroyedActiveObjects();
    }



    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        mainShip.touchDown(touch, pointer, button);

        if(!buttonDodge.isMe(touch)){
            mainShip.touchDown(touch, pointer, button);
        }else {
            buttonDodge.touchDown(touch, pointer, button);
        }

        if(!buttonAttak.isMe(touch)){
            mainShip.touchDown(touch, pointer, button);
        }else {
            buttonAttak.touchDown(touch, pointer, button);
        }
//        buttonSound.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        mainShip.touchUp(touch, pointer, button);
        if(!buttonDodge.isMe(touch)){
            mainShip.touchDown(touch, pointer, button);
        }else {
            buttonDodge.touchDown(touch, pointer, button);
        }
        if(!buttonAttak.isMe(touch)){
            mainShip.touchDown(touch, pointer, button);
        }else {
            buttonAttak.touchDown(touch, pointer, button);
        }
//        buttonSound.touchDown(touch, pointer, button);
        return false;
    }
    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return false;
    }
    private void update(float delta) {
        mainShip.update(delta);

        bulletPool.updateActiveSprites(delta);
        enemyPool.updateActiveSprites(delta);
        enemyGenerator.generate(delta);
        buttonDodge.update(delta);
        for (Star star : stars) {
            star.update(delta);
        }
    }


    private void draw() {
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.15f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        for (Star star : stars) {
            star.draw(batch);
        }
        mainShip.draw(batch);
        bulletPool.drawActiveSprites(batch);
        enemyPool.drawActiveSprites(batch);
        indicatorStamina.draw(batch);
        buttonSound.draw(batch);
        buttonDodge.draw(batch);
        buttonAttak.draw(batch);
        batch.end();
    }

    //Метод проверки столкновений объектов(снарядов, кораблей и т.п.)
    private void checkCollisions() {
        //инициируем временные коллекции для пулов кораблей противника и снарядов
        List<Enemy> enemyList = enemyPool.getActiveObjects();

        List<Bullet> bulletList = bulletPool.getActiveObjects();
        //листаем коллекцию кораблей противника - отрабатываем их столкновения
        for (Enemy enemy : enemyList) {
            //инициируем временную переменную для рассчета минимального расстояния между объектами
            //это нужно, чтобы соприкосновения происходили реалистично близко к центрам объектов
            float minDist = enemy.getHalfWidth() + mainShip.getHalfWidth();
            //если длина вектора между векторами позиции корабля противника и главного корабля
            // стал меньше минимального расстояния
            if (mainShip.pos.dst(enemy.pos) < minDist) {
                //вызываем метод уничтожения корабля противника(такая игровая логика)
                enemy.destroy();
            }
            //листаем коллекцию снарядов (главного корабля) - отрабатываем их столкновения
            for (Bullet bullet : bulletList) {
                //если пуля не принадлежит главному кораблю, значит это пуля корабля противника
                if (bullet.getOwner() != mainShip) {
                    //пропускаем остальной код на этой итерации - идем к следующей
                    continue;
                }
                //если снаряд главного корабля попал в корабль противника
                if (enemy.isBulletCollision(bullet)) {
                    //вызываем метод расчета повреждения корабля противника
                    enemy.damage(bullet.getDamage());
                    //вызываем метод уничтожения снаряда
                    bullet.destroy();
                }
            }
        }
        //листаем коллекцию снарядов (кораблей противника) - отрабатываем их столкновения
        for (Bullet bullet : bulletList) {
            //если пуля принадлежит главному кораблю, значит это не пуля корабля противника
            if (bullet.getOwner() == mainShip) {
                //пропускаем остальной код на этой итерации - идем к следующей
                continue;
            }
            //если снаряд корабля противника попал в главный корабль
            if (mainShip.isBulletCollision(bullet)) {
                //вызываем метод расчета повреждения главного корабля
                //вызываем метод уничтожения снаряда
                bullet.destroy();
            }
        }
    }

}
