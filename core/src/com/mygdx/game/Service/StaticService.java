package com.mygdx.game.Service;

import com.badlogic.gdx.math.MathUtils;

/**
 * Created by 1 on 29.03.2020.
 */

public class StaticService {
    public static boolean selectWithProbability(float probably) {
        if (MathUtils.random(100) < probably) return true;
        else return false;
    }

    public static boolean selectWithProbability(int probably) {
        if (MathUtils.random(100) < probably) return true;
        else return false;
    }

    static public int determineTeamPlayer(int nomerPlayer) { // определяем команду )))
        if (Math.abs(nomerPlayer) % 2 == 0) return 1;
        else return 0;
    }


    static public int determineTeamPlayer(Integer nomerPlayer) { // определяем команду )))
        if (Math.abs(nomerPlayer) % 2 == 0) return 1;
        else return 2;
    }


}
