package com.mygdx.game.Characters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.Characters.Animation.AnimatonBody;
import com.mygdx.game.Service.OperationVector;

public class Player {
    private int x, y, rot, rotBoots;
    private int velocity;
    private AnimatonBody animatonBody;
    private boolean live;
    private Color color;
    private boolean viseble;
    private int weapons;
    Integer nom_mask;
    private float alpha =1;

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    private long updateTime;


    int tackt;                  // Номер такта для ног всего их 12
    float timerCurrenCycle;     // tаймер текущего цикла

    public Player(int x, int y, int rot, int id) {
        this.animatonBody = new AnimatonBody();
        this.x = x;
        this.y = y;
        this.rotBoots = rot;
        this.rot = rot;
        this.tackt = 0;
        this.timerCurrenCycle = 0;
        this.live = true;
        float r, g, b;
        r = Math.abs(MathUtils.sin(id));
        g = Math.abs(MathUtils.cos(id));
        b = Math.abs(MathUtils.cos(r - g));
        this.color = new Color(r, g, b, r + b + .5f);
        this.viseble = false;
        this.weapons = 1;
        this.alpha = 1;
    }

    public int getWeapons() {
        return weapons;
    }

    public int getNomMask(int id) { //распределение масок
        if (this.nom_mask == null) {
            int nom = Math.abs(id);
            for (; ; ) {
                if (nom >= 5) {
                    nom = nom - 5;
                } else {
                    this.nom_mask = nom;
                    break;
                }
            }

        }
        return this.nom_mask;
    }

    public void setWeapons(int weapons) {
        this.weapons = weapons;
    }

    public boolean getLiveToId(int id) {
        return live;
    }

    public int getRotBoots() {
        return rotBoots;
    }

    public void setRotBoots(int rotBoots) {
        this.rotBoots = rotBoots;
    }

    public int getTackt() {
        return tackt;
    }

    public void setTackt(int tackt) {
        this.tackt = tackt;
    }

    public float getTimerCurrenCycle() {
        return timerCurrenCycle;
    }

    public void setTimerCurrenCycle(float timerCurrenCycle) {
        this.timerCurrenCycle = timerCurrenCycle;
    }

    public Color getColor() {
        return color;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public Player setX(int x) {
        this.x = x;
        return this;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Player setY(int y) {
        this.y = y;
        return this;
    }

    public boolean isViseble() {
        return viseble;
    }

    public void setViseble(boolean viseble) {
        this.viseble = viseble;
    }

    public int getRot() {
        return rot;
    }

    public Player setRot(int rot) {
        this.rot = rot;
        return this;
    }

    public void updateCoordinatPleyer(int x, int y, int rot) {
        int v = updateVelocity(x, y, this.x, this.y);
        this.setVelocity(v);
        this.setRotBoots(getDirection(x, y, this.x, this.y, v)); // обновление ног вращение
        this.x = x;
        this.y = y;
        this.rot = rot;
        this.updateTime = System.currentTimeMillis();

    }


    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public AnimatonBody getAnimatonBody() {
        return animatonBody;
    }


    private int updateVelocity(int x, int y, int sx, int sy) { //обновление скорости для всех юзеров по старой - новой коорденате
        if ((x == sx) && (y == sy)) return this.getRotBoots();
        OperationVector.setTemp_vector(x - sx, y - sy);
        return (int) OperationVector.getTemp_vector().len2() / 10;
    }

    private int getDirection(int x, int y, int sx, int sy, int v) { //Обновляет вращение ног
        if (v < 2) return getRotBoots();
        OperationVector.setTemp_vector((x - sx), (y - sy));
        return (int) OperationVector.getTemp_vector().angle();

    }

    public int getVelocity() {
        return velocity;
    }

    public Player setVelocity(int velocity) {
        this.velocity = velocity;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                "x=" + x +
                ", y=" + y +
                ", rot=" + rot +
                ", rotBoots=" + rotBoots +
                ", velocity=" + velocity +
                ", animatonBody=" + animatonBody +
                ", live=" + live +
                ", color=" + color +
                ", viseble=" + viseble +
                ", weapons=" + weapons +
                ", tackt=" + tackt +
                ", timerCurrenCycle=" + timerCurrenCycle +
                '}';
    }
}
