package com.mygdx.game.Characters;

/**
 * Created by 1 on 12.04.2020.
 */

public class Weapons {

    private int fragWithLife;// Фраги в течение одной жизни
    private int weapon;// оружие в руках
    private int temp_weapon; // слудщее оружие


    public Weapons() {
        this.fragWithLife = 0;
        this.weapon = 1;

    }

    public int getTemp_weapon() {
        return temp_weapon;
    }


    public void setTemp_weapon(int temp_weapon) {
        this.temp_weapon = temp_weapon;
    }

    public void updateWeapon(){
        if(temp_weapon!=0){setWeapon(temp_weapon); setTemp_weapon(0);}
    }

    public int getFragWithLife() {
        return fragWithLife;
    }

    public void setFragWithLife(int fragWithLife) {
        this.fragWithLife = fragWithLife;
    }

    public int getWeapon() {
        return weapon;
    }

    public void setWeapon(int weapon) {
        this.weapon = weapon;
    }

    @Override
    public String toString() {
        return "Weapons{" +
                "fragWithLife=" + fragWithLife +
                ", weapon=" + weapon +
                '}';
    }
}
