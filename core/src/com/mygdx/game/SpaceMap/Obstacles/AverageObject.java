package com.mygdx.game.SpaceMap.Obstacles;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.MainGaming;


/**
 * Created by qwerty on 18.03.2020.
 */

public class AverageObject {
    private int x, y, score, rotation;
    private MainGaming mainGaming;
    private TextureRegion textureRegion;




    public AverageObject(int x, int y, int score, int rotation,  MainGaming mainGaming, TextureAtlas textureAtlas, TextureRegion texture) {
        mainGaming.getAssetsManagerGame().finishLoading();
        this.x = x;
        this.y = y;
        this.score = score;
        this.rotation = rotation;
        this.mainGaming = mainGaming;
        this.textureRegion = texture;

    }

    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    public void setTextureRegion(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }

    public AverageObject() {
        this.mainGaming = mainGaming;
//        this.x = x;
//        this.y = y;
//        this.score = score;
//        this.rotation = rotation;
//        this.texture = texture;
        //       this()
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public TextureRegion getTextureRegionFromRender(){
        return this.textureRegion;


    }

}
