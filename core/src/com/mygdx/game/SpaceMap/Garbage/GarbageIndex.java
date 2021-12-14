package com.mygdx.game.SpaceMap.Garbage;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Created by 1 on 11.07.2020.
 */

public class GarbageIndex {
    private TextureRegion textureRegion;
    private ArrayList<Garbage> garbageList;

    private float changeDirection; // такст смены направления ветра
    private Vector2 windSpeed;


    public GarbageIndex() {
        garbageList = new ArrayList<Garbage>();
        windSpeed = new Vector2(1,1);
    }

    public void updateGarbage(float delta){
        for (int i = 0; i < garbageList.size(); i++) {
            garbageList.get(i).update(delta);
        }
    }

    public void renderGarbage(){

    }




}
