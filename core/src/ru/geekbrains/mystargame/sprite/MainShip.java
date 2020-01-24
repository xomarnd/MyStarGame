package ru.geekbrains.mystargame.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.mystargame.base.Ship;
import ru.geekbrains.mystargame.math.Rect;
import ru.geekbrains.mystargame.pool.BulletPool;
import ru.geekbrains.mystargame.pool.ExplosionPool;
import ru.geekbrains.mystargame.utils.Regions;

/**
 * Класс спрайта корабля игрока(главного корабля).
 */
public class MainShip extends Ship {
    private static final int HP = 30;//30
    private int DAMAGE = 1;
    private static final int SUPERDAMAGE = 10;

    private static boolean moves = false;
    public static boolean vectorDodge = false;
    public static boolean pointStaminaFull = true;
    public static boolean setAttack;
    public MainShip(TextureAtlas atlas, BulletPool bulletPool, ExplosionPool explosionPool, Sound shootSound) {
        super(atlas.findRegion("main_ship"), 1, 4, 4);
        this.bulletPool = bulletPool;
        this.shootSound = shootSound;


        this.explosionPool = explosionPool;

        TextureRegion bulletMainShip = atlas.findRegion("bulletMainShip");
        this.bulletRegion = Regions.split(bulletMainShip, 1, 4, 4);
        TextureRegion bulletBigMainShip = atlas.findRegion("bulletBigMainShip");
        this.bulletBigRegion = Regions.split(bulletBigMainShip, 1, 4, 4);

        this.bulletHeight = 0.03f; //0.01f
        this.superBulletHeight = 0.06f; //0.01f

        this.bulletV = new Vector2(0, 0.5f);
        this.damage = DAMAGE;
        this.superdamage = SUPERDAMAGE;

        this.v = new Vector2();
        this.v0 = new Vector2(0.9f, 0);
        this.reloadInterval = 0.25f;
        this.reloadTimer = 0f;
        this.hp = HP;
    }

    public static void buttonDodge() {
        moves = true;
        if (vectorDodge){
            vectorDodge = false;
        }else {
            vectorDodge = true;
        }
    }

    public static void buttonAttack() {
        setAttack = true;
    }


    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
        setHeightProportion(0.15f);
        setBottom(worldBounds.getBottom() + 0.25f);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        reloadTimer += delta;
        if (moves) {
            if (vectorDodge) {
                v.set(v0);
                //ограничения движения коробля
                if (getRight() > worldBounds.getRight()) {
                    setRight(worldBounds.getRight());
                    stop();
                }
            } else {
                v.set(v0).rotate(180);
                //ограничения движения коробля
                if (getLeft() < worldBounds.getLeft()) {
                    setLeft(worldBounds.getLeft());
                    stop();
                }
            }
        }
        //автоатака
        if (reloadTimer > reloadInterval) {
            reloadTimer = 0f;
            shoot();
        }
        //супер атака
        if (pointStaminaFull && setAttack){
            bigShoot();
            pointStaminaFull = false;
            setAttack = false;
        }
    }

    public boolean isBulletCollision(Rect bullet) {
        return !(
                bullet.getRight() < getLeft()
                || bullet.getLeft() > getRight()
                || bullet.getBottom() > pos.y
                || bullet.getTop() < getBottom()
        );
    }

    public void dispose() {
        sound.dispose();
    }

    private void stop() {
        v.setZero();
    }

    public int getHP() {
        return hp;
    }

    public void startNewGame(Rect worldBounds) {
        //останавливаем движение главного корабля
        stop();
        hp = HP;
        CONSTHP = HP;
        damage = DAMAGE;
        CONSTDAMAGE = DAMAGE;
        //устанавливаем дефолтную позицию корабля
        pos.x = worldBounds.pos.x;
        //сбрасываем флаг видимости главного корабля
        destroyed = false;
    }
}
