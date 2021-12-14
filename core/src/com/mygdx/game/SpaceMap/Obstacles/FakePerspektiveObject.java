package com.mygdx.game.SpaceMap.Obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.SpaceMap.IndexMap;

public class FakePerspektiveObject {
    private IndexMap indexMap;
    private String texture;
    private Vector2 position;
    private float turn; // поворот
    private int layers[]; // слои 3д фейка
    private float perspective_anglel; // угол перспективы
    private int perspective_gap; // зазор межслойный
    private TextureRegion teturewell;

    public FakePerspektiveObject(IndexMap indexMap, String texture, float x, float y, boolean turn) {
        this.indexMap = indexMap;
        this.texture = texture;
        this.position = new Vector2(x, y);
        this.perspective_anglel = 0;
        this.perspective_gap = 0;
        this.layers = new int[]{1,1,1,1,1,1,1,1,1,1,1,1};
        if (turn) this.turn = 0;else this.turn = 90;
        teturewell = indexMap.mainGaming.getAssetsManagerGame().get("fake_Perspektive/fakePerspektive.pack", TextureAtlas.class).findRegion("wall_1");


        this.texture = "wall_";
    }

//wall_1

    public boolean intersection(int x, int y) { // принадлежность точк
        return false;
    }

    public void renderObject() {
        TextureRegion textureRegion;
        this.perspective_gap = 5;
        //if(Gdx.graphics.getFramesPerSecond() < 35 ) return;
        for (int i = 0; i < layers.length; i++) {
            textureRegion = teturewell;
            //System.out.println(position);
            indexMap.mainGaming.getBatch().draw(
                    textureRegion,
                    position.x - i * this.perspective_gap * 3, position.y  - i * this.perspective_gap * 3,
                    textureRegion.getRegionWidth() / 2, textureRegion.getRegionHeight() / 2,
                    textureRegion.getRegionWidth(), textureRegion.getRegionHeight(),
                    3, 1, this.turn
            );
            //this.turn++;



        }

//        TextureRegion textureRegion = getTextureToIdObstacles(this.getTexture());
//        indexMap.mainGaming.getBatch().setColor(this.color);
//        indexMap.mainGaming.getBatch().draw(
//                textureRegion,
//                this.getCentor().x,
//                this.getCentor().y,
//                radius,radius,
//                radius*2,
//                radius*2,
//                getScale(),getScale(),
//                rotation
//        );
    }




}