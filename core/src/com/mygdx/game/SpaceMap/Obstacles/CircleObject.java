package com.mygdx.game.SpaceMap.Obstacles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.SpaceMap.IndexMap;

/**
 * Created by 1 on 07.03.2020.
 */

public class CircleObject {
    private Vector2 centor;
    private int radius;
    private String texture;
    private float scale;
    private IndexMap indexMap;
    private Color color;
    private int rotation;


    public boolean intersection(int x, int y) { // принадлежность точке
        float r = radius*scale;
        return (x - this.centor.x) * (x - this.centor.x) + (y - this.centor.y) * (y - this.centor.y) <= r * r;
    }

    public Vector2 getCentor() {
        return centor;
    }

    public void setCentor(Vector2 centor) {
        this.centor = centor;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public String getTexture() {
        return texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    private TextureRegion getTextureToIdObstacles(String texture) { // взять по номеру препятситвия
        TextureAtlas textureAtlas = indexMap.mainGaming.getAssetsManagerGame().get("map/obstacles", TextureAtlas.class);
        return textureAtlas.findRegion(texture);
    }

    public void renderObject() {
        TextureRegion textureRegion = getTextureToIdObstacles(this.getTexture());
        indexMap.mainGaming.getBatch().setColor(this.color);
        indexMap.mainGaming.getBatch().draw(
                textureRegion,
                this.getCentor().x,
                this.getCentor().y,
                radius,radius,
                radius*2,
                radius*2,
                getScale(),getScale(),
                rotation
        );
    }
}
