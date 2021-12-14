package com.mygdx.game.Service;



public class TimeService {
    static public int getTimeGame() {
        return (int) (System.currentTimeMillis() - (604800000 * (System.currentTimeMillis() / 604800000)));
    }

    static public Long getTimeGameTOrealTime(int gameTime) {
        return gameTime + (604800000 * (System.currentTimeMillis() / 604800000));
    }




}
