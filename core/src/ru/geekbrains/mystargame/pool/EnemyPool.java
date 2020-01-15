package ru.geekbrains.mystargame.pool;

import com.badlogic.gdx.audio.Sound;

import ru.geekbrains.mystargame.base.SpritesPool;
import ru.geekbrains.mystargame.math.Rect;
import ru.geekbrains.mystargame.sprite.EnemyShip;

public class EnemyPool extends SpritesPool<EnemyShip> {

    private BulletPool bulletPool;
    private Sound sound;
    private Rect worldBounds;

    public EnemyPool(BulletPool bulletPool, Sound sound, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.sound = sound;
        this.worldBounds = worldBounds;
    }

    @Override
    public EnemyShip newObject() {
        return new EnemyShip(bulletPool, sound, worldBounds);
    }
}
