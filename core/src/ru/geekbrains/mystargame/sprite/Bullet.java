package ru.geekbrains.mystargame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.mystargame.base.Sprite;
import ru.geekbrains.mystargame.math.Rect;

public class Bullet extends Sprite {

    private Rect worldBounds;
    private Vector2 v;
    private int damage;
    private Sprite owner;
    protected int bulletFrame = 0;
    private float animateStartInterval = 10f;
    private float animateTimer;

//    public Bullet(TextureAtlas atlas) {
//        super(atlas.findRegion("attakbutton"), 1, 2, 4);
//        v = new Vector2();
//
//    }

    public Bullet() {
        regions = new TextureRegion[1];
        v = new Vector2();
    }

    public void set(
            Sprite owner,
            TextureRegion[] region,
            Vector2 pos0,
            Vector2 v0,
            float height,
            Rect worldBounds,
            int damage
    ) {
        this.owner = owner;
        this.regions = region;
        this.pos.set(pos0);
        this.v.set(v0);
        setHeightProportion(height);
        this.worldBounds = worldBounds;
        this.damage = damage;
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        if (isOutside(worldBounds)) {
            destroy();
        }
        for (int i = 0; i <= 3; i++){
            frame = rndFrame(0, 3);
        }
        //Вращение снаряда
        animateTimer += delta;
        setAngle(angle+5);


    }
    public int rndFrame(int min, int max)
    {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }

    public int getDamage() {
        return damage;
    }

    public Sprite getOwner() {
        return owner;
    }
}
