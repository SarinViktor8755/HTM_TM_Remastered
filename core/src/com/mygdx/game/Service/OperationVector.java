package com.mygdx.game.Service;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;


public class OperationVector {

    private static Vector2 temp_vector = new Vector2(0, 0);
    private static Vector2 temp_vector_two = new Vector2(0, 0);
    private static Vector3 temp_vector_camera = new Vector3(0, 0, 0);


    public static Vector2 getBufferVector(Vector2 vec) {
        temp_vector.set(vec);
        return temp_vector;
    }

    public static Vector2 getBufferVector(float x, float y) {
        temp_vector.set(x, y);
        return temp_vector;
    }

    public static Vector3 getBufferVectorFromCamera(float x, float y) {
        temp_vector_camera.set(x, y, 0);
        return temp_vector_camera;
    }


    public static Vector3 getBufferVectorFromCamera(Vector2 vec) {
        temp_vector_camera.set(vec.x, vec.y, 0);
        return temp_vector_camera;
    }

    public static Vector3 getBufferVectorFromCamera(Vector3 vec) {
        temp_vector_camera.set(vec);
        return temp_vector_camera;
    }


    public static boolean sclPrVectarov(Vector2 vec1, Vector2 vec2) { // скаларность двух векторов
        if (vec1.x * vec2.x + vec1.y * vec2.y > 0) return true;
        else return false;
    }


    public static Vector2 getTemp_vector() {
        return temp_vector;
    }

    public static Vector3 getTemp_vector_camera() {
        return temp_vector_camera;
    }

    public static void setTemp_vector(Vector2 vec) {
        setTemp_vector(vec.x, vec.y);
    }


    public static void setTemp_vector(float x, float y) {
        temp_vector.set(x, y);
    }

    public static void setTemp_vector(int x, int y) {
        temp_vector.set(x, y);
    }

    public static Vector2 get_Setter_Temp_vector() {
        return temp_vector;
    }


    static public int getDistance(int x1, int y1, int x2, int y2) { // посчитать расстояние
        return (int) Math.sqrt((float)((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)));
    }


}



