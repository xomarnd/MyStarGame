package ru.geekbrains.mystargame.pool;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.mystargame.base.SpritesPool;
import ru.geekbrains.mystargame.sprite.Explosion;

public class ExplosionPool extends SpritesPool<Explosion> {

    private TextureAtlas atlas;
    private Sound explosionSound;

    public ExplosionPool(TextureAtlas atlas, Sound explosionSound) {
        this.atlas = atlas;
        this.explosionSound = explosionSound;
    }

    @Override
    public Explosion newObject() {
        return new Explosion(atlas, explosionSound);
    }

    public void setExplosionEndFrame(int frame){
        //если в коллекции активных взрывов есть объекты
        if(!activeObjects.isEmpty()){
            //пролистываем коллекцию
            for (Explosion exp: activeObjects) {
                //устанавливаем всем объектам взрывов кадр "последнего вздоха"
                exp.setFrame(frame);
            }
        }
    }
}
