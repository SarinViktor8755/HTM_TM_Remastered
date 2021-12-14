package com;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Created by 1 on 22.12.2019.
 */

public class StaticService {
    static public int getTimeGame() { // Перевети реальное время в во время игры
        return (int) (System.currentTimeMillis() - (604800000 * (System.currentTimeMillis() / 604800000)));
    }

    static public Long getTimeGameTOrealTime(int gameTime) { // Перевести из времени игры - в реальное время
        return gameTime + (604800000 * (System.currentTimeMillis() / 604800000));
    }

    /////////////
    static public int getDistance(float x1, float y1, float x2, float y2) { // посчитать расстояние
        return (int) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    static public int getDistance(int x1, int y1, int x2, int y2) { // посчитать расстояние
        return (int) Math.sqrt((float) ((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)));
    }

    static public int getDistance(Vector2 a, Vector2 b) { // посчитать расстояние
        return getDistance(a.x, a.y, b.x, b.y);
    }

    /////////////
    static int nearest(int nomber, int[] arr) { //Наиблежайшее значение
        int result = Integer.MAX_VALUE;
        for (int i : arr) if (Math.abs(nomber - result) > Math.abs(nomber - i)) result = i;
        return result;
    }

    public static boolean selectWithProbability(float probably) {
        if (MathUtils.random(100) < probably) return true;
        else return false;
    }

    public static boolean selectWithProbability(int probably) {
        if (MathUtils.random(100) < probably) return true;
        else return false;
    }

    public static boolean getDirectionRotation360(int anglOriginal, int anglUltimate) {
        boolean result = true;
        int tempVar = anglOriginal - anglUltimate;
        if (tempVar < 0) if (result) result = false;
        else result = true; // если менее 0 сменить знак
        if (Math.abs(tempVar) < 180) if (result) result = false;
        else result = true;
        return result;
    }


}
