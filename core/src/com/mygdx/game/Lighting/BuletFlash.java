package com.mygdx.game.Lighting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Interpolation;

import box2dLight.PointLight;
import box2dLight.RayHandler;

/**
 * Created by Виктор on 08.04.2021.
 */

public class BuletFlash {
    private float timerLife;
    private float x, y;
    private PointLight bullet;
    private ShaderProgram shaderProgram;

    public BuletFlash(RayHandler rh) {
        this.timerLife = 0;
        this.x = 0;
        this.y = 0;
        bullet = new PointLight(rh, 15, Color.YELLOW, 800, 0, 0); /// свитильник героя
        bullet.getColor().set(0,0,0,-.5f);
        bullet.setActive(false);
    }

    public void upDate() {
        if (this.timerLife > 0) {
            this.timerLife -= Gdx.graphics.getDeltaTime();
            bullet.setPosition(x, y);
            bullet.setDistance(Interpolation.swingOut.apply(timerLife) * 2000);

        } else {
            bullet.setActive(false);
        }
    }

    public void newFlesh(float x, float y) {
        this.timerLife = .25f;
        this.bullet.setActive(true);
        this.x = x;
        this.y = y;
        //System.out.println("start Flash");
    }


}
