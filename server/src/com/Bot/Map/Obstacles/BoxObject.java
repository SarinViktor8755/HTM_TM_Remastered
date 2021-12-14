package com.Bot.Map.Obstacles;

import com.Bot.Map.IndexMap;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;



public class BoxObject { // Боксовый обьект верхнего уровня

    private boolean corner;             // угол 0,90,180,270
    private Vector2 pointUpperRight;//верхняя правая
    private Vector2 pointLeftBottom; // левая нижняя

    public BoxObject( int height, int width, boolean corner, Vector2 pointLeftBottom) {

        this.corner = corner;
        if (!corner) {
            this.pointUpperRight = new Vector2(pointLeftBottom.x + height, pointLeftBottom.y + width);
            this.pointLeftBottom = new Vector2(pointLeftBottom.x, pointLeftBottom.y);
        } else {
            this.pointUpperRight = new Vector2(pointLeftBottom.x + width, pointLeftBottom.y + height);
            this.pointLeftBottom = new Vector2(pointLeftBottom.x, pointLeftBottom.y);
        }
    }

    public BoxObject(int texture, boolean mirror, int height, int width, boolean corner, Color color, Vector2 pointLeftBottom, boolean flipX, boolean flipY) {
        if (!corner) {
            this.pointUpperRight = new Vector2(pointLeftBottom.x + height, pointLeftBottom.y + width);
            this.pointLeftBottom = new Vector2(pointLeftBottom.x, pointLeftBottom.y);
        } else {
            this.pointUpperRight = new Vector2(pointLeftBottom.x + width, pointLeftBottom.y + height);
            this.pointLeftBottom = new Vector2(pointLeftBottom.x, pointLeftBottom.y);
        }
    }

    public BoxObject(IndexMap indexMap, int random, boolean mirror, int height, int width, boolean corner, Object color, Vector2 pointLeftBottom, boolean flipX, boolean flipY) {
    }


    public boolean intersection(int x, int y,int borders) {
        if (x + borders < pointLeftBottom.x) return false;
        if (x - borders > pointUpperRight.x) return false;
        if (y + borders < pointLeftBottom.y) return false;
        if (y - borders > pointUpperRight.y) return false;
        return true;
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

    @Override
    public String toString() {
        return "BoxObject{" +
                ", corner=" + corner +
                ", pointLeftBottom=" + pointLeftBottom +
                ", pointUpperRight=" + pointUpperRight +

                '}';
    }
}
