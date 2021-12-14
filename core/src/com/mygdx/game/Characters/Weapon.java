package com.mygdx.game.Characters;

/**
 * Created by qwerty on 08.04.2020.
 */

public class Weapon { // не используется
    public int fragWithLife;            // Фраги в течение одной жизни
    public int myPositionTablica;       // Моя позиция в таблице
    private int weapons;                // Предмет  рукпах

    public Weapon() {
        this.fragWithLife = 0;
        this.myPositionTablica = 0;
        this.weapons = 1;
    }

    public Weapon(int fragWithLife, int myPositionTablica, int weapons) {
        this.fragWithLife = fragWithLife;
        this.myPositionTablica = myPositionTablica;
        this.weapons = weapons;
    }

    public int getFragWithLife() {
        return fragWithLife;
    }

    public void setFragWithLife(int fragWithLife) {
        this.fragWithLife = fragWithLife;
    }

    public int getMyPositionTablica() {
        return myPositionTablica;
    }

    public void setMyPositionTablica(int myPositionTablica) {
        this.myPositionTablica = myPositionTablica;
    }

    public int getWeapons() {
        return weapons;
    }

    public void setWeapons(int weapons) {
        this.weapons = weapons;
    }

    @Override
    public String toString() {
        return "{" +
                " fragWithLife=" + fragWithLife +
                ", myPositionTablica=" + myPositionTablica +
                ", weapons=" + weapons +
                '}';
    }
}
