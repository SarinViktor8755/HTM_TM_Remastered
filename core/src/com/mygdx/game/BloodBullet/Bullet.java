package com.mygdx.game.BloodBullet;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Bullet {
    private float stepX, stepY;
    private Vector2 poition;
    private int numberSteps;
    private boolean live;

    private final float SPEED = MathUtils.random(4900,6000);

    public Bullet() {
        this.stepX = 0;
        this.stepY = 0;
        this.poition = new Vector2(Integer.MIN_VALUE, Integer.MIN_VALUE);
        this.numberSteps = 0;
        this.live = false;
    }

    public void zeroBullet() {
        this.stepX = 0;
        this.stepY = 0;
        this.poition = new Vector2(Integer.MIN_VALUE, Integer.MIN_VALUE);
        this.numberSteps = 0;
        this.live = false;
    }

    public static Bullet startBulletFly(PoolBlood poolBlood) {
        Bullet b = poolBlood.getMyBulletQueue().removeFirst();
        poolBlood.getMyBulletQueue().addLast(b);
        return b;
    }

    public float getStepX() {
        return stepX;
    }

    public void setStepX(float stepX) {
        this.stepX = stepX;
    }

    public float getStepY() {
        return stepY;
    }

    public void setStepY(float stepY) {
        this.stepY = stepY;
    }

    public Vector2 getPoition() {
        return poition;
    }

    public void setPoition(Vector2 poition) {
        this.poition = poition;
    }

    public void setPoition(int x, int y) {
        this.poition.set(x, y);
    }

    public int getNumberSteps() {
        return numberSteps;
    }

    public void setNumberSteps(int numberSteps) {
        this.numberSteps = numberSteps;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public boolean upDatePosition(PoolBlood poolBlood, float dt) {
        numberSteps++;
        this.poition.x += stepX * SPEED * dt;
        this.poition.y += stepY * SPEED * dt;
        if (!poolBlood.getMainGaming().getIndexMap().canMove((int) poition.x, (int) poition.y)) {
            this.setLive(false);
            return false;
        }
        return true;
    }


    @Override
    public String toString() {
        return "Bullet{" +
                "poition=" + poition +
                '}';
    }
}



