package ru.geekbrains.mystargame.screen;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;


import java.util.List;

import ru.geekbrains.mystargame.StarGame;
import ru.geekbrains.mystargame.base.BaseScreen;
import ru.geekbrains.mystargame.base.Font;
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
import ru.geekbrains.mystargame.pool.ExplosionPool;
import ru.geekbrains.mystargame.sprite.GameOver;
import ru.geekbrains.mystargame.sprite.ButtonNewGame;




public class GameScreen extends BaseScreen {
    private static final float FONT_PADDING = 0.01f;
    private static final float FONT_SIZE = 0.02f;

    private static final String FRAGS = "Frags: ";
    private static final String HP = "HP: ";
    private static final String LEVEL = "Level: ";

    private enum State {PLAYING, PAUSE, GAME_OVER}

    private static float DISPLAYRED = 0.15f;
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

    private int frags;
    private Font font;
    private StringBuilder sbFrags;
    private StringBuilder sbHp;
    private StringBuilder sbLevel;

    public GameScreen(StarGame game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        frags = 0;
        //Иинициация фоновой музыки
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
        music.play();
        //Инициация боевых звуков
        laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        shellHitSound = Gdx.audio.newSound(Gdx.files.internal("sounds/shell-hit.mp3"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));

        atlasStar = new TextureAtlas(Gdx.files.internal("textures/stars.tpack"));
        atlasMenu = new TextureAtlas(Gdx.files.internal("textures/atlasmenu4.tpack"));

        buttonDodge = new ButtonDodge(atlasMenu, this);
        buttonAttak = new ButtonAttak(atlasMenu, this);
        indicatorStamina = new IndicatorStamina(atlasMenu, this);
        buttonSound = new ButtonSound(atlasMenu, this);
        //шрифты и информацию
        font = new Font("font/font.fnt", "font/font.png");
        sbFrags = new StringBuilder();
        sbHp = new StringBuilder();
        sbLevel = new StringBuilder();
        //инициируем "конец игры"
        gameOver = new GameOver(atlasMenu);
        //инициируем "новая игра"
        newGameButton = new ButtonNewGame(atlasMenu, this);

        //инициируем обьекты коробля
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlasMenu, explosionSound);
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

        font.setSize(FONT_SIZE);
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
        if (state == State.PLAYING) {
            if(buttonDodge.isMe(touch)){
                buttonDodge.touchDown(touch, pointer, button);
                buttonDodge.action();
            }
            if(buttonAttak.isMe(touch)){
                buttonAttak.touchDown(touch, pointer, button);
                buttonAttak.action();
            }
        } else if (state == State.GAME_OVER) {
            newGameButton.touchDown(touch, pointer, button);
        }
        if(buttonSound.isMe(touch)) {
            buttonSound.touchDown(touch, pointer, button);
            music.stop();
            laserSound.stop();
            bulletSound.stop();
            shellHitSound.stop();
            explosionSound.stop();
        }
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
            buttonSound.touchDown(touch, pointer, button);
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
        //устанавливаем 1 лвл
        enemyGenerator.setLevel(1);
        //обнуляем фраги
        frags = 0;
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
            enemyGenerator.generate(delta, frags);
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
        Gdx.gl.glClearColor(DISPLAYRED, 0.15f, 0.15f, 1);
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
            DISPLAYRED = 0.15f;
            mainShip.draw(batch);

            indicatorStamina.draw(batch);
            buttonSound.draw(batch);
            buttonDodge.draw(batch);
            buttonAttak.draw(batch);
        } else if (state == State.GAME_OVER) {
            DISPLAYRED = 0.8f;
            gameOver.draw(batch);
            newGameButton.draw(batch);
        }
        printInfo();
        batch.end();
    }


    private void checkCollisions() {
        if (state != State.PLAYING) {
            return;
        }
        //инициируем временные коллекции для пулов кораблей противника и снарядов
        List<Enemy> enemyList = enemyPool.getActiveObjects();
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        //листаем коллекцию кораблей противника
        for (Enemy enemy : enemyList) {
            //Считаем растояние между объектами
            float minDist = enemy.getHalfWidth() + mainShip.getHalfWidth();
            //отрабатываем столкновение кораблей
            if (mainShip.pos.dst(enemy.pos) < minDist) {
                frags += enemy.getConstHp();
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
                    if (enemy.isDestroyed()) {
                        mainShip.medHp(enemyGenerator.getLevel());
                        frags += enemy.getConstHp();
                    }
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
    private void printInfo() {
        sbFrags.setLength(0);
        sbHp.setLength(0);
        sbLevel.setLength(0);
        font.draw(batch, sbFrags.append(FRAGS).append(frags),
                worldBounds.getLeft() + FONT_PADDING, worldBounds.getTop() - FONT_PADDING);
        font.draw(batch, sbHp.append(HP).append(mainShip.getHP()),
                worldBounds.pos.x, worldBounds.getTop() - FONT_PADDING, Align.center);
        font.draw(batch, sbLevel.append(LEVEL).append(enemyGenerator.getLevel()),
                worldBounds.getRight() - FONT_PADDING, worldBounds.getTop() - FONT_PADDING, Align.right);
    }
}
