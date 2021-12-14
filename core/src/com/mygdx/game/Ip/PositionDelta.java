package com.mygdx.game.Ip;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by 1 on 03.09.2019.
 */

public class PositionDelta {
    private Vector2 position, startPosition;
    private int pointer;


    public int getPointer() {
        return pointer;
    }

    public void setPointer(int pointer) {
        this.pointer = pointer;
    }

    public PositionDelta() {
        this.position = new Vector2(0, 0);
        this.startPosition = new Vector2(0, 0);
        this.pointer = -1;
    }

    public Vector2 getPosition() {
        return position;
    }

    public PositionDelta setPosition(Vector2 position) {
        this.position = position;
        return this;
    }

    public void setZero() {
        position.set(0, 0);
        startPosition.set(0, 0);
        this.setPointer(-1);
    }

    public void setXY(int x, int y) {
        position.set(x, y);
    }


    public boolean isActiv() {
        if (this.getPointer() == -1) return false;
        return true;
    }

    public void setStartPositionXY(int x, int y) {
        startPosition.set(x, y);
    }

    public void setPosition(int x, int y) {
        position.set(x, y);
    }

    public Vector2 getStartPosition() {
        return startPosition;
    }

    public PositionDelta setStartPosition(Vector2 startPosition) {
        this.startPosition = startPosition;
        return this;
    }
}
