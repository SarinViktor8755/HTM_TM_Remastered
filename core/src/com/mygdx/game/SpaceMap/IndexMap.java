package com.mygdx.game.SpaceMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MainGaming;
import com.mygdx.game.SpaceMap.Groundmap.Tiled;
import com.mygdx.game.SpaceMap.Obstacles.*;
import com.mygdx.game.SpaceMap.Obstacles.BoxObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

/**
 * Created by 1 on 09.02.2020.
 */

public class IndexMap {
    public MainGaming mainGaming;

    private Tiled[][] lowerQualityMap;

    private HashMap<Integer, BoxObject> topQualityMap_Box;
    private HashMap<Integer, AverageObject> averageLevelObjects;
    private HashMap<Integer, CircleObject> topQualityMap_Circule;
    private HashMap<Integer, FakePerspektiveObject> fake_perspektive;

    public final int countMap = 50;
    public final int sizeTilde = 100;
    public final int lengthMap = sizeTilde * countMap;


    ShaderProgram def;


    TextureAtlas obstacles;

    public IndexMap(MainGaming mainGaming) {
        this.mainGaming = mainGaming;
        createAverageLevel();
        createLowerQualityMap();
        createTopQualityMap();



        def = new ShaderProgram(DefaultShader.getDefaultVertexShader(),DefaultShader.getDefaultFragmentShader());


        //createFakePerspektiveLaier();
        //createFakePerspektiveLaier();
    }

    ///////////////////////

    public void createFakePerspektiveLaier() {
        fake_perspektive = new HashMap<Integer, FakePerspektiveObject>();
        FakePerspektiveObject fakePerspektiveObject;
        int x, y;
        for (int i = 0; i < 55; i++) {
            fakePerspektiveObject = new FakePerspektiveObject(this, "wall_", 0, i * 64, true);
            fake_perspektive.put(i, fakePerspektiveObject);
        }


    }

    public HashMap<Integer, BoxObject> getTopQualityMap_Box() {
        return topQualityMap_Box;
    }

    public void renderFakePerspektiveLaier() {
        for (Map.Entry<Integer, FakePerspektiveObject> entry : fake_perspektive.entrySet()) {
            entry.getValue().renderObject();
        }
    }

    ////////////////////////////////
    private void createLowerQualityMap() {
        this.lowerQualityMap = new Tiled[countMap][countMap];
        for (int i = 0; i < countMap; i++) {
            for (int j = 0; j < countMap; j++) {
                lowerQualityMap[i][j] = new Tiled(mainGaming.getAssetsManagerGame().get("map/ground", TextureAtlas.class));
            }
        }
        obstacles = mainGaming.getAssetsManagerGame().get("map/obstacles", TextureAtlas.class);
    }


    public void renderBottomLevel() { // Рендер нижнего уровня
        ShaderProgram.pedantic = false;



        for (int i = 0; i < countMap; i++) {
            for (int j = 0; j < countMap; j++) {
                mainGaming.getBatch().draw(
                        lowerQualityMap[i][j].getTextureRender(),
                        i * sizeTilde, j * sizeTilde,
                        sizeTilde / 2, sizeTilde / 2,
                        sizeTilde, sizeTilde,
                        1, 1,
                        lowerQualityMap[i][j].getCorner()
                );
            }
        }
        mainGaming.getBatch().setShader(null);


    }

    private static Color determineColorCar(int x, int y) {
        float r = MathUtils.clamp(MathUtils.sin(x), .5f, 1);
        float g = MathUtils.clamp(MathUtils.cos(y), .5f, 1);
        float b = MathUtils.clamp(MathUtils.sin(x), .5f, 1);
        return new Color(r, g, b, 1);
    }

    static boolean dividesByTwo(int a) {
        return (a % 2 == 0);
    }

    private int determineTextureNomer(int x, int y) {

        return 1;
    }

    public MainGaming getMainGaming() {
        return mainGaming;
    }


    /////////////////////////////
    private void createTopQualityMap() {// препятсвия
        topQualityMap_Box = new HashMap<Integer, BoxObject>();
        topQualityMap_Circule = new HashMap<Integer, CircleObject>();
        Color color = new Color(MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), 1);
        for (int i = 5; i < 20; i++) {
            color.set(MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), MathUtils.random(.2f, 1f), 1);
            // topQualityMap_Box.put(topQualityMap_Box.size(), new BoxObject(this, 1, false, 200, 450, false, color, new Vector2(MathUtils.random(5000), MathUtils.random(5000)),true,true));
            //topQualityMap_Box.put(topQualityMap_Box.size(), new BoxObject(this, MathUtils.random(1, 3), true, 200, 450, false, color, new Vector2(i * 300, i * 300), MathUtils.randomBoolean(), MathUtils.randomBoolean()));
        }

        for (int i = 0; i < countMap * sizeTilde - 10; i += 458) {
            color = new Color(MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), 1);
            topQualityMap_Box.put(topQualityMap_Box.size(), new BoxObject(this, MathUtils.random(1, 7), true, 200, 450, false, determineColorCar(0, i), new Vector2(0, i), MathUtils.randomBoolean(), MathUtils.randomBoolean()));
            topQualityMap_Box.put(topQualityMap_Box.size(), new BoxObject(this, MathUtils.random(1, 7), true, 200, 450, true, determineColorCar(i + 200, 0), new Vector2(i + 200, 0), MathUtils.randomBoolean(), MathUtils.randomBoolean()));
        }
        color = new Color(MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), 1);
        for (int i = 240; i < countMap * sizeTilde; i += 458) {
            topQualityMap_Box.put(topQualityMap_Box.size(), new BoxObject(this, MathUtils.random(1, 7), true, 200, 450, false, determineColorCar(countMap * sizeTilde - 210, i), new Vector2(countMap * sizeTilde - 210, i), MathUtils.randomBoolean(), MathUtils.randomBoolean()));
            //       topQualityMap_Box.put(topQualityMap_Box.size(), new BoxObject(this, MathUtils.random(2,3), true, 200, 450, true, color, new Vector2(i + 210, 5 * MathUtils.sin(i) + countMap * sizeTilde - 220), MathUtils.randomBoolean(), MathUtils.randomBoolean()));
        }

        for (int i = 0; i < 4500; i += 458) {
            color = new Color(MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), 1);
            topQualityMap_Box.put(topQualityMap_Box.size(), new BoxObject(this, MathUtils.random(1, 7), true, 200, 450, true, determineColorCar(i + 200, 0 + countMap * sizeTilde - 200), new Vector2(i + 200, 0 + countMap * sizeTilde - 200), MathUtils.randomBoolean(), MathUtils.randomBoolean()));
        }
//////////////
        color = new Color(MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), 1);
        topQualityMap_Box.put(topQualityMap_Box.size(), new BoxObject(this, 2, true, 200, 450, true, color, new Vector2(2500, 4000), MathUtils.randomBoolean(), MathUtils.randomBoolean()));

        color = new Color(MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), 1);
        topQualityMap_Box.put(topQualityMap_Box.size(), new BoxObject(this, 3, true, 200, 450, false, color, new Vector2(2500, 3300), MathUtils.randomBoolean(), MathUtils.randomBoolean()));

        color = new Color(MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), 1);
        topQualityMap_Box.put(topQualityMap_Box.size(), new BoxObject(this, 1, true, 200, 450, true, color, new Vector2(1500, 3650), MathUtils.randomBoolean(), MathUtils.randomBoolean()));

        color = new Color(MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), 1);
        topQualityMap_Box.put(topQualityMap_Box.size(), new BoxObject(this, 4, true, 200, 450, false, color, new Vector2(500, 3650), MathUtils.randomBoolean(), MathUtils.randomBoolean()));

        color = new Color(MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), 1);
        topQualityMap_Box.put(topQualityMap_Box.size(), new BoxObject(this, 1, true, 200, 450, false, color, new Vector2(950, 1650), MathUtils.randomBoolean(), MathUtils.randomBoolean()));
        ///////////////////////////
        color = new Color(MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), 1);
        topQualityMap_Box.put(topQualityMap_Box.size(), new BoxObject(this, 5, true, 200, 450, true, color, new Vector2(200, 2050), MathUtils.randomBoolean(), MathUtils.randomBoolean()));

        color = new Color(MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), 1);
        topQualityMap_Box.put(topQualityMap_Box.size(), new BoxObject(this, 6, true, 200, 450, false, color, new Vector2(1500, 1050), MathUtils.randomBoolean(), MathUtils.randomBoolean()));

        color = new Color(MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), 1);
        topQualityMap_Box.put(topQualityMap_Box.size(), new BoxObject(this, 3, true, 200, 450, true, color, new Vector2(900, 2800), MathUtils.randomBoolean(), MathUtils.randomBoolean()));

        color = new Color(MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), 1);
        topQualityMap_Box.put(topQualityMap_Box.size(), new BoxObject(this, 2, true, 200, 450, true, color, new Vector2(3959, 801), MathUtils.randomBoolean(), MathUtils.randomBoolean()));

        color = new Color(MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), 1);
        topQualityMap_Box.put(topQualityMap_Box.size(), new BoxObject(this, 4, true, 200, 450, false, color, new Vector2(3077, 878), MathUtils.randomBoolean(), MathUtils.randomBoolean()));

        color = new Color(MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), 1);
        topQualityMap_Box.put(topQualityMap_Box.size(), new BoxObject(this, 1, true, 200, 450, false, color, new Vector2(2000, 878), MathUtils.randomBoolean(), MathUtils.randomBoolean()));
//////////////////////////////////////////////////////////////

        color = new Color(MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), 1);
        topQualityMap_Box.put(topQualityMap_Box.size(), new BoxObject(this, 7, true, 200, 450, false, color, new Vector2(2665, 959), MathUtils.randomBoolean(), MathUtils.randomBoolean()));

//        color = new Color(MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), 1);
//        topQualityMap_Box.put(topQualityMap_Box.size(), new BoxObject(this, MathUtils.random(1, 7), true, 200, 450, false, color, new Vector2(2666, 1310), MathUtils.randomBoolean(), MathUtils.randomBoolean()));

        color = new Color(MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), 1);
        topQualityMap_Box.put(topQualityMap_Box.size(), new BoxObject(this, 6, false, 200, 450, false, color, new Vector2(2347, 1310), MathUtils.randomBoolean(), MathUtils.randomBoolean()));
//////////

        color = new Color(MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), 1);
        topQualityMap_Box.put(topQualityMap_Box.size(), new BoxObject(this, 4, false, 200, 450, false, color, new Vector2(603, 292), MathUtils.randomBoolean(), MathUtils.randomBoolean()));

        color = new Color(MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), 1);
        topQualityMap_Box.put(topQualityMap_Box.size(), new BoxObject(this, 4, false, 200, 450, true, color, new Vector2(3879, 1668), MathUtils.randomBoolean(), MathUtils.randomBoolean()));

        color = new Color(MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), 1);
        topQualityMap_Box.put(topQualityMap_Box.size(), new BoxObject(this, 2, false, 200, 450, false, color, new Vector2(1392, 4317), MathUtils.randomBoolean(), MathUtils.randomBoolean()));

        color = new Color(MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), 1);
        topQualityMap_Box.put(topQualityMap_Box.size(), new BoxObject(this, 5, false, 200, 450, false, color, new Vector2(2150, 4297), MathUtils.randomBoolean(), MathUtils.randomBoolean()));

        color = new Color(MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), 1);
        topQualityMap_Box.put(topQualityMap_Box.size(), new BoxObject(this, 5, false, 200, 450, false, color, new Vector2(3532, 4356), MathUtils.randomBoolean(), MathUtils.randomBoolean()));

        color = new Color(MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), 1);
        topQualityMap_Box.put(topQualityMap_Box.size(), new BoxObject(this, 4, false, 200, 450, true, color, new Vector2(3917, 3823), MathUtils.randomBoolean(), MathUtils.randomBoolean()));


        color = new Color(MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), 1);
        topQualityMap_Box.put(topQualityMap_Box.size(), new BoxObject(this, 2, false, 200, 450, false, color, new Vector2(4148, 2902), MathUtils.randomBoolean(), MathUtils.randomBoolean()));

        color = new Color(MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), 1);
        topQualityMap_Box.put(topQualityMap_Box.size(), new BoxObject(this, 3, false, 200, 450, false, color, new Vector2(4483, 2143), MathUtils.randomBoolean(), MathUtils.randomBoolean()));


        color = new Color(MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), 1);
        topQualityMap_Box.put(topQualityMap_Box.size(), new BoxObject(this, 5, false, 200, 450, false, color, new Vector2(3545, 610), MathUtils.randomBoolean(), MathUtils.randomBoolean()));

        color = new Color(MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), 1);
        topQualityMap_Box.put(topQualityMap_Box.size(), new BoxObject(this, 6, false, 200, 450, false, color, new Vector2(3929, 1937), MathUtils.randomBoolean(), MathUtils.randomBoolean()));

        color = new Color(MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), 1);
        topQualityMap_Box.put(topQualityMap_Box.size(), new BoxObject(this, 3, false, 200, 450, false, color, new Vector2(3464, 3554), MathUtils.randomBoolean(), MathUtils.randomBoolean()));

        color = new Color(MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), MathUtils.random(.6f, 1f), 1);
        topQualityMap_Box.put(topQualityMap_Box.size(), new BoxObject(this, 5, false, 200, 450, false, color, new Vector2(2034, 2456), MathUtils.randomBoolean(), MathUtils.randomBoolean()));
    }

    private void createAverageLevel() {


        mainGaming.getAssetsManagerGame().finishLoading();
        mainGaming.getAssetsManagerGame().isLoaded("map/obstacles");

        // System.out.println(mainGaming.getAssetsManagerGame().isLoaded("map/obstacles"));
        mainGaming.getAssetsManagerGame().get("map/obstacles");

        mainGaming.getAssetsManagerGame().finishLoading();


//        obstacles.findRegion("logo");
        //mainGaminggerGame.get("pauseAsset/pause", TextureAtlas.class).findRegion("aim");
        mainGaming.getAssetsManagerGame().get("map/obstacles", TextureAtlas.class).findRegion("logo");
        this.averageLevelObjects = new HashMap<Integer, AverageObject>();
        AverageObject ao;

        TextureRegion textureRegion = mainGaming.getAssetsManagerGame().get("map/obstacles", TextureAtlas.class).findRegion("logo");
        ao = new AverageObject(2500, 2500, 5, 26, mainGaming, obstacles, textureRegion);
        averageLevelObjects.put(averageLevelObjects.size() + 1, ao);
//       // textureRegion = mainGaming.getAssetsManagerGame().get("map/groiund", TextureAtlas.class).findRegion("zed");
//        ao = new AverageObject(1000, 2500, 2, 0, mainGaming, obstacles, textureRegion);
//        averageLevelObjects.put(averageLevelObjects.size() + 1, ao);

    }

    public void renderAverageLevel() {
        TextureRegion texture;

        for (Map.Entry<Integer, AverageObject> entry : averageLevelObjects.entrySet()) {
            // разобратся
            texture = entry.getValue().getTextureRegionFromRender();
            //System.out.println(texture);
            mainGaming.getBatch().draw(texture, entry.getValue().getX(), entry.getValue().getY(), texture.getRegionWidth() * entry.getValue().getScore(), texture.getRegionHeight() * entry.getValue().getScore());

        }
    }

    public boolean canMove(int x, int y) {       // проверка на пересечение с препятсвиями
        if (!intersectionWithEdgeMap(x, y)) return false;
        if (checObstacleObject(x, y)) return false;
        return true;
    }


    private boolean checObstacleObject(int x, int y) { // проверяет пересеченеи с фигурами
        for (Map.Entry<Integer, BoxObject> entry : topQualityMap_Box.entrySet()) {
            if (entry.getValue().intersection(x, y)) return true;
        }
        for (Map.Entry<Integer, CircleObject> entry : topQualityMap_Circule.entrySet()) {
            if (entry.getValue().intersection(x, y)) return true;
        }
        return false;
    }


    public void renderTopQualityMap() { // Рендер верхнего уровня
        for (Map.Entry<Integer, BoxObject> entry : topQualityMap_Box.entrySet()) {
            entry.getValue().renderObject();
        }

        for (Map.Entry<Integer, CircleObject> entry : topQualityMap_Circule.entrySet()) {
//            mainGaming.getBatch().draw(
//                    getTextureToIdObstacles(entry.getValue().getTexture()),
//                    entry.getValue().getPointReference().x, entry.getValue().getPointReference().y,
//                    0, 0,
//                    1, 1,
//                    200, 450,
//                    0
//            );

        }


    }

    private boolean intersectionWithEdgeMap(int x, int y) {// пересечение с краем карты, с припятсвиями
        if (x > lengthMap) return false;
        if (x < 0) return false;

        if (y > lengthMap) return false;
        if (y < 0) return false;

        return true;
    }

    public Vector2 getFreeSpace(boolean comand) { // зять свободную точку
        int x, y;
        final int edge = 50;
        do {
            if (comand) {
                x = MathUtils.random(0 + edge, 250);
                y = MathUtils.random(0, 250);
            } else {
                x = MathUtils.random(lengthMap - 250, lengthMap);
                y = MathUtils.random(lengthMap - 250, lengthMap);
            }
        } while (!canMove(x, y));
        // System.out.println(x + "   " + y);
        return new Vector2(x, y);
    }


    public void getObjectFromLightis() {
        for (Map.Entry<Integer, AverageObject> entry : this.averageLevelObjects.entrySet()) ;
        //  System.out.println(entry.getKey() + " = " + entry.getValue());

    }


}
