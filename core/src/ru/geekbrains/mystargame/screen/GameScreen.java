package ru.geekbrains.mystargame.screen;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;


import java.util.List;

import ru.geekbrains.mystargame.StarGame;
import ru.geekbrains.mystargame.base.BaseScreen;
import ru.geekbrains.mystargame.base.ScaledButton;
import ru.geekbrains.mystargame.math.Rect;
import ru.geekbrains.mystargame.pool.BulletPool;
import ru.geekbrains.mystargame.pool.EnemyPool;
import ru.geekbrains.mystargame.sprite.ButtonAttak;
import ru.geekbrains.mystargame.sprite.ButtonDodge;
import ru.geekbrains.mystargame.sprite.ButtonPlay;
import ru.geekbrains.mystargame.sprite.ButtonSound;
import ru.geekbrains.mystargame.sprite.IndicatorStamina;
import ru.geekbrains.mystargame.sprite.MainShip;
import ru.geekbrains.mystargame.sprite.Star;
import ru.geekbrains.mystargame.utils.EnemyGenerator;
import ru.geekbrains.mystargame.sprite.Enemy;
import ru.geekbrains.mystargame.sprite.Bullet;
import ru.geekbrains.mystargame.pool.ExplosionPool;
import ru.geekbrains.mystargame.sprite.GameOver;
import ru.geekbrains.mystargame.sprite.ButtonNewGame;




public class GameScreen extends BaseScreen {

    private enum State {PLAYING, PAUSE, GAME_OVER}

    private TextureAtlas atlas;
    private TextureAtlas atlasStar;
    private TextureAtlas atlasMenu;

    private MainShip mainShip;

    private BulletPool bulletPool;
    private EnemyPool enemyPool;
    private ExplosionPool explosionPool;


    private EnemyGenerator enemyGenerator;

    //Объявление переменных звука
    private Music music;
    private Sound laserSound;
    private Sound bulletSound;
    //объявляем объект звука попадания снаряда в корабль
    private Sound shellHitSound;
    private Sound explosionSound;


    //Объявление кнопок интерфейса
    private ButtonDodge buttonDodge;
    private ButtonAttak buttonAttak;
    private ButtonSound buttonSound;
    private IndicatorStamina indicatorStamina;
    //объявляем спрайт для сообщения "конец игры"
    private GameOver gameOver;
    //объявляем регион для картинки "новая игра"
    private ButtonNewGame newGameButton;


    private Star[] stars;

    private State state;
    private State preState;

    public GameScreen(StarGame game) {
        super(game);
    }

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
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));

        atlas = new TextureAtlas(Gdx.files.internal("textures/mainAtlas.tpack"));
        atlasStar = new TextureAtlas(Gdx.files.internal("textures/stars.tpack"));
        atlasMenu = new TextureAtlas(Gdx.files.internal("textures/atlasmenu2.tpack"));

        buttonDodge = new ButtonDodge(atlasMenu, this);
        buttonAttak = new ButtonAttak(atlasMenu, this);
        indicatorStamina = new IndicatorStamina(atlasMenu, this);
        buttonSound = new ButtonSound(atlasMenu, this);

        //инициируем "конец игры"
        gameOver = new GameOver(atlasMenu);
        //инициируем "новая игра"
        newGameButton = new ButtonNewGame(atlasMenu, this);

        //инициируем обьекты коробля
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas, explosionSound);
        mainShip = new MainShip(atlasMenu, bulletPool, explosionPool, laserSound);
        enemyPool = new EnemyPool(bulletPool, explosionPool, bulletSound, worldBounds);
        enemyGenerator = new EnemyGenerator(atlasMenu, enemyPool, worldBounds);

        stars = new Star[64];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlasStar);
        }
        state = State.PLAYING;
        preState = State.PLAYING;


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
        gameOver.resize(worldBounds);
        super.resize(worldBounds);
        newGameButton.resize(worldBounds);


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
        mainShip.dispose();
        atlas.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        music.dispose();
        laserSound.dispose();
        bulletSound.dispose();
        shellHitSound.dispose();
        explosionSound.dispose();
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
            buttonDodge.action();

        }
        if(!buttonAttak.isMe(touch)){
            mainShip.touchDown(touch, pointer, button);
        }else {
            buttonAttak.touchDown(touch, pointer, button);
            buttonAttak.action();
        }
        if (state == State.PLAYING) {
            mainShip.touchDown(touch, pointer, button);
        } else if (state == State.GAME_OVER) {
            newGameButton.touchDown(touch, pointer, button);
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
            IndicatorStamina.setAddPointCount(1);
            buttonDodge.touchDown(touch, pointer, button);
        }
        if(!buttonAttak.isMe(touch)){
            mainShip.touchDown(touch, pointer, button);
        }else {
            IndicatorStamina.setPointCount();
            buttonAttak.touchDown(touch, pointer, button);
        }
        if (state == State.PLAYING) {
            mainShip.touchUp(touch, pointer, button);
        } else if (state == State.GAME_OVER) {
            newGameButton.touchUp(touch, pointer, button);
        }
//        buttonSound.touchDown(touch, pointer, button);
        return false;
    }
    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ESCAPE) {
            //если установлено состояние пауза игры
            if (state == State.PAUSE) {
                //восстанавливаем игру после паузы
                resume();
                //если игра не в режиме паузы
            } else {
                //ставим игру на паузу
                pause();
            }
        }
        if (keycode == Input.Keys.SPACE) {
            //если установлено состояние пауза игры
            if (state == State.PLAYING) {
                //перемещение корабля
                IndicatorStamina.setAddPointCount(1);
                buttonDodge.action();
            }
        }
        if (keycode == Input.Keys.ENTER) {
            //если установлено состояние пауза игры
            if (state == State.PLAYING) {
                //суператака
                IndicatorStamina.setPointCount();
                buttonAttak.action();

            }
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }
    public void startNewGame() {
        //устанавливаем текущий режим игры в положение "играть"
        gameOver.startNewGame();
        //задаем начальные параметры главному кораблю
        mainShip.startNewGame(worldBounds);
        bulletPool.freeAllActiveObjects();
        enemyPool.freeAllActiveObjects();
        explosionPool.freeAllActiveObjects();
        state = State.PLAYING;
    }

    @Override
    public void pause() {
        //запоминаем текущий уровень игры
        preState = state;
        //устанавливаем текущему уровню игры режим "пауза"
        state = State.PAUSE;
        //преостанавливаем воспроизведение музыки
        music.pause();
    }
    @Override
    public void resume() {
        //устанавливаем текущий режим игру на сохраненный предыдущий
        state = preState;
        //воспроизводим музыку с того места, где она была преостановлена
        music.play();
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
        explosionPool.updateActiveSprites(delta);
        if (state == State.PLAYING) {
            mainShip.update(delta);
            bulletPool.updateActiveSprites(delta);
            enemyPool.updateActiveSprites(delta);
            enemyGenerator.generate(delta);
            buttonDodge.update(delta);
            buttonAttak.update(delta);
            indicatorStamina.update(delta);
        }else if(state == State.PAUSE) {
            return;
        }
        if (state == State.GAME_OVER){
            newGameButton.update(delta);
        }
    }


    private void draw() {
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.15f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        for (Star star : stars) {
            star.draw(batch);
        }
        explosionPool.drawActiveSprites(batch);

        if (state == State.PLAYING) {
            explosionPool.drawActiveSprites(batch);
            bulletPool.drawActiveSprites(batch);
            enemyPool.drawActiveSprites(batch);

            mainShip.draw(batch);

            indicatorStamina.draw(batch);
            buttonSound.draw(batch);
            buttonDodge.draw(batch);
            buttonAttak.draw(batch);
        } else if (state == State.GAME_OVER) {
            gameOver.draw(batch);
            newGameButton.draw(batch);
        }
        batch.end();
    }


    private void checkCollisions() {
        if (state != State.PLAYING) {
            return;
        }
        List<Enemy> enemyList = enemyPool.getActiveObjects();
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (Enemy enemy : enemyList) {
            float minDist = enemy.getHalfWidth() + mainShip.getHalfWidth();
            if (mainShip.pos.dst(enemy.pos) < minDist) {
                mainShip.damage(enemy.getDamage());
                enemy.destroy();
                if (mainShip.isDestroyed()) {
                    mainShip.setHp(0);
                    state = State.GAME_OVER;
                }
            }
            for (Bullet bullet : bulletList) {
                if (bullet.getOwner() != mainShip) {
                    continue;
                }
                if (enemy.isBulletCollision(bullet)) {
                    enemy.damage(bullet.getDamage());
                    bullet.destroy();
                }
            }
        }
        for (Bullet bullet : bulletList) {
            if (bullet.getOwner() == mainShip) {
                continue;
            }
            if (mainShip.isBulletCollision(bullet)) {
                mainShip.damage(bullet.getDamage());
                bullet.destroy();
                if (mainShip.isDestroyed()) {
                    mainShip.setHp(0);
                    state = State.GAME_OVER;
                }
            }
        }
    }
}
