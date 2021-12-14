package com.mygdx.game.SpaceMap.Obstacles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.SpaceMap.IndexMap;


public class BoxObject { // Боксовый обьект верхнего уровня

    private IndexMap indexMap;

    private String texture;         //ntrcnehf
    private Color color;            // цвет
    private boolean corner;             // угол 0,90,180,270
    private Vector2 pointUpperRight;//верхняя правая
    private Vector2 pointLeftBottom; // левая нижняя
    private boolean flipX, flipY;


    public BoxObject(IndexMap indexMap, int texture, boolean mirror, int height, int width, boolean corner, Color color, Vector2 pointLeftBottom, boolean flipX, boolean flipY) {
        this.indexMap = indexMap;
        this.texture = "obstacles" + texture;
        this.color = color;
        this.corner = corner;
        if (!corner) {
            this.pointUpperRight = new Vector2(pointLeftBottom.x + height, pointLeftBottom.y + width);
            this.pointLeftBottom = new Vector2(pointLeftBottom.x, pointLeftBottom.y);
        } else {
            this.pointUpperRight = new Vector2(pointLeftBottom.x + width, pointLeftBottom.y + height);
            this.pointLeftBottom = new Vector2(pointLeftBottom.x, pointLeftBottom.y);
        }
        this.flipX = flipX;
        this.flipY = flipY;

    }

    public boolean intersection(int x, int y) {
        if (x + 15 < pointLeftBottom.x) return false;
        if (x - 15 > pointUpperRight.x) return false;
        if (y + 15 < pointLeftBottom.y) return false;
        if (y - 15 > pointUpperRight.y) return false;
        return true;
    }

    public String getTexture() {
        return this.texture;
    }


    private TextureRegion getTextureToIdObstacles(String texture) { // взять по номеру препятситвия
        TextureAtlas textureAtlas = indexMap.mainGaming.getAssetsManagerGame().get("map/obstacles", TextureAtlas.class);
        return textureAtlas.findRegion(texture);
    }

    public boolean isGorisont(){
        return !corner;
    }

    public void renderObject() {
        BoxObject e = this;
        float x, y;

        int mir;
        if (!corner) {
            x = e.getPointUpperRight().x - e.getPointLeftBottom().x;
            y = e.getPointUpperRight().y - e.getPointLeftBottom().y;
            mir = 1;
        } else {
            x = e.getPointUpperRight().y - e.getPointLeftBottom().y;
            y = e.getPointUpperRight().x - e.getPointLeftBottom().x;
            mir = -1;
        }
        TextureRegion textureRegion = getTextureToIdObstacles(e.getTexture());
        if (textureRegion.isFlipX() != flipX) textureRegion.flip(true, false);
        if (textureRegion.isFlipY() != flipY) textureRegion.flip(false, true);
        indexMap.mainGaming.getBatch().setColor(e.getColor());

        indexMap.mainGaming.getBatch().draw(
                textureRegion,
                e.getPointLeftBottom().x,
                e.getPointLeftBottom().y,
                0, 0,
                x, y,
                mir, 1,
                getAngel()
        );
        indexMap.mainGaming.getBatch().setColor(1, 1, 1, 1);
    }

    private int getAngel() {
        if (corner) return -90;
        else return 0;
    }

    public IndexMap getIndexMap() {
        return indexMap;
    }

    public void setIndexMap(IndexMap indexMap) {
        this.indexMap = indexMap;
    }

    public Vector2 getPointLeftBottom() {
        return pointLeftBottom;
    }

    public void setPointLeftBottom(Vector2 pointLeftBottom) {
        this.pointLeftBottom = pointLeftBottom;
    }

    public Vector2 getPointUpperRight() {
        return pointUpperRight;
    }

    public void setPointUpperRight(Vector2 pointUpperRight) {
        this.pointUpperRight = pointUpperRight;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }


    @Override
    public String toString() {
        return "BoxObject{" +
                "indexMap=" + indexMap +
                ", texture='" + texture + '\'' +
                ", color=" + color +
                ", corner=" + corner +
                ", pointLeftBottom=" + pointLeftBottom +
                ", pointUpperRight=" + pointUpperRight +

                '}';
    }
}
