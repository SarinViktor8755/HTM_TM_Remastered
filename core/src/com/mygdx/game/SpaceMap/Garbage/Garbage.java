package com.mygdx.game.SpaceMap.Garbage;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by 1 on 11.07.2020.
 */

public class Garbage {
    private float x,y;


    private float rotation;


    public Garbage(float x, float y) {
        this.x = x;
        this.y = y;

        rotation = MathUtils.random(-190,190);
    }

    public void update(float delta){

    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    private void recuperGarbage(){ // обновить мусор - уход с поля

    }
}
