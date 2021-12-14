package com.mygdx.game.BloodBullet;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;


public class Blood {
    public int x, y; // коорденаты , направление
    public Vector2 angle; //нправление перемещения
    public float score, timer, actiontimer;// маштаб , таймер, таймер действий
    public TextureRegion texture;// текстура пула - тело , или кровь
    public int flip;
    public boolean transparent; //transparent - прозрачный
    public Color color; // цвет анимации   - это желетов

    public float getTimer() {
        return timer;
    }

    public void setTimer(float timer) {
        this.timer = timer;
    }

    public float getActiontimer() {
        return actiontimer;
    }

    public void setActiontimer(float actiontimer) {
        this.actiontimer = actiontimer;
    }

    public int getFlip() {
        return flip;
    }

    public void setFlip(int flip) {
        this.flip = flip;
    }

    public boolean isTransparent() {
        return transparent;
    }

    public void setTransparent(boolean transparent) {
        this.transparent = transparent;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Blood(int x, int y, Vector2 angle, float score, TextureRegion texture, float actiontimer, int flip) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.score = score;
        this.texture = texture;
        this.timer = 0;
        this.actiontimer = actiontimer;
        this.flip = flip;
        this.score = 1;
        this.transparent = false;

    }

    public Blood(int x, int y, Vector2 angle, float score, TextureRegion texture, float actiontimer, int flip,boolean transparent) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.score = score;
        this.texture = texture;
        this.timer = 0;
        this.actiontimer = actiontimer;
        this.flip = flip;
        this.score = 1;
        this.transparent = transparent;
    }


    public Blood() {
        this.x = Integer.MIN_VALUE;
        this.y = Integer.MIN_VALUE;
        this.angle = new Vector2();
        this.score = 0;
        this.texture = null;
        this.timer = 0;
        this.actiontimer = 0;
        this.flip = 0;
    }

    public boolean isLive() {
        if (this.getTexture() != null) return true;
        return false;
    }

    public TextureRegion getTexture() {
        return texture;
    }

    public void setTexture(TextureRegion texture) {
        this.texture = texture;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Vector2 getAngle() {
        return angle;
    }

    public void setAngle(Vector2 angle) {
        this.angle = angle;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public Blood(TextureRegion texture) {
        this.texture = texture;
    }


    @Override
    public String toString() {
        return "Blood{" +
                "x=" + x +
                ", y=" + y +
                ", angle=" + angle +
                ", score=" + score +
                ", timer=" + timer +
                ", actiontimer=" + actiontimer +
                ", texture=" + texture +
                ", flip=" + flip +
                ", transparent=" + transparent +
                ", color=" + color +
                '}';
    }
}
