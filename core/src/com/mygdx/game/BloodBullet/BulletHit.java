package com.mygdx.game.BloodBullet;

import com.badlogic.gdx.math.Vector2;

public class BulletHit extends Bullet {
    private Vector2 poition;
    private boolean live;
    private float rotation;

    public BulletHit(Vector2 poition, boolean live, float rotation) {
        this.poition = poition;
        this.live = live;
        this.rotation = rotation;
    }

    @Override
    public Vector2 getPoition() {
        return poition;
    }

    @Override
    public void setPoition(Vector2 poition) {
        this.poition = poition;
    }

    @Override
    public boolean isLive() {
        return live;
    }

    @Override
    public void setLive(boolean live) {
        this.live = live;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    @Override
    public boolean upDatePosition(PoolBlood poolBlood, float dt) {
        return super.upDatePosition(poolBlood, dt);
    }
}
