package com.Bot.Map;

import com.Bot.Map.Obstacles.BoxObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by 1 on 09.02.2020.
 */

public class IndexMap {
    private HashMap<Integer, BoxObject> topQualityMap;

    public final int countMap = 50;
    public final int sizeTilde = 100;
    public final int lengthMap = sizeTilde * countMap;

    public IndexMap() {
        topQualityMap = new HashMap<>();
        createTopQualityMap();
    }

    private void createTopQualityMap() {
//        for (int i = 5; i < 20; i++) {
//            topQualityMap.put(topQualityMap.size(), new BoxObject(200, 450, false, new Vector2(i * 300, i * 300)));
//        }


        for (int i = 0; i < countMap * sizeTilde - 10; i += 455) {

            topQualityMap.put(topQualityMap.size(), new BoxObject(200, 450, false, new Vector2(0, i)));
            topQualityMap.put(topQualityMap.size(), new BoxObject(200, 450, true, new Vector2(i + 200, 0)));
        }

        for (int i = 240; i < countMap * sizeTilde; i += 455) {
            topQualityMap.put(topQualityMap.size(), new BoxObject(200, 450, false, new Vector2(countMap * sizeTilde - 200, i)));
            //       topQualityMap_Box.put(topQualityMap_Box.size(), new BoxObject(this, MathUtils.random(2,3), true, 200, 450, true, color, new Vector2(i + 210, 5 * MathUtils.sin(i) + countMap * sizeTilde - 220), MathUtils.randomBoolean(), MathUtils.randomBoolean()));
        }

        for (int i = 0; i < 4500; i += 455) {
            topQualityMap.put(topQualityMap.size(), new BoxObject(200, 450, true, new Vector2(i + 200, 0 + countMap * sizeTilde - 200)));
        }

       ////////////////////////////////////////////////////////////////////

        topQualityMap.put(topQualityMap.size(), new BoxObject(MathUtils.random(1, 7), true, 200, 450, true, null, new Vector2(2500, 4000), MathUtils.randomBoolean(), MathUtils.randomBoolean()));

        topQualityMap.put(topQualityMap.size(), new BoxObject(MathUtils.random(1, 7), true, 200, 450, false, null, new Vector2(2500, 3300), MathUtils.randomBoolean(), MathUtils.randomBoolean()));

        topQualityMap.put(topQualityMap.size(), new BoxObject(MathUtils.random(1, 7), true, 200, 450, true, null, new Vector2(1500, 3650), MathUtils.randomBoolean(), MathUtils.randomBoolean()));

        topQualityMap.put(topQualityMap.size(), new BoxObject( MathUtils.random(1, 7), true, 200, 450, false, null, new Vector2(500, 3650), MathUtils.randomBoolean(), MathUtils.randomBoolean()));

        topQualityMap.put(topQualityMap.size(), new BoxObject( MathUtils.random(1, 7), true, 200, 450, false, null, new Vector2(950, 1650), MathUtils.randomBoolean(), MathUtils.randomBoolean()));
        ///////////////////////////

        topQualityMap.put(topQualityMap.size(), new BoxObject(MathUtils.random(1, 7), true, 200, 450, true, null, new Vector2(200, 2050), MathUtils.randomBoolean(), MathUtils.randomBoolean()));

        topQualityMap.put(topQualityMap.size(), new BoxObject(MathUtils.random(1, 7), true, 200, 450, false, null, new Vector2(1500, 1050), MathUtils.randomBoolean(), MathUtils.randomBoolean()));

        topQualityMap.put(topQualityMap.size(), new BoxObject(MathUtils.random(1, 7), true, 200, 450, true, null, new Vector2(900, 2800), MathUtils.randomBoolean(), MathUtils.randomBoolean()));


        topQualityMap.put(topQualityMap.size(), new BoxObject(MathUtils.random(1, 7), true, 200, 450, true, null, new Vector2(3959, 801), MathUtils.randomBoolean(), MathUtils.randomBoolean()));

        topQualityMap.put(topQualityMap.size(), new BoxObject(MathUtils.random(1, 7), true, 200, 450, false, null, new Vector2(3077, 878), MathUtils.randomBoolean(), MathUtils.randomBoolean()));

        topQualityMap.put(topQualityMap.size(), new BoxObject(MathUtils.random(1, 7), true, 200, 450, false, null, new Vector2(2000, 878), MathUtils.randomBoolean(), MathUtils.randomBoolean()));
//////////////////////////////////////////////////////////////

        topQualityMap.put(topQualityMap.size(), new BoxObject(MathUtils.random(1, 7), true, 200, 450, false, null, new Vector2(2665, 959), MathUtils.randomBoolean(), MathUtils.randomBoolean()));

        topQualityMap.put(topQualityMap.size(), new BoxObject(MathUtils.random(1, 7), false, 200, 450, false, null, new Vector2(2347, 1310), MathUtils.randomBoolean(), MathUtils.randomBoolean()));
//////////

        topQualityMap.put(topQualityMap.size(), new BoxObject( MathUtils.random(1, 7), false, 200, 450, false, null, new Vector2(603, 292), MathUtils.randomBoolean(), MathUtils.randomBoolean()));

        topQualityMap.put(topQualityMap.size(), new BoxObject(MathUtils.random(1, 7), false, 200, 450, true, null, new Vector2(3879, 1668), MathUtils.randomBoolean(), MathUtils.randomBoolean()));

        topQualityMap.put(topQualityMap.size(), new BoxObject(MathUtils.random(1, 7), false, 200, 450, false, null, new Vector2(1392, 4317), MathUtils.randomBoolean(), MathUtils.randomBoolean()));

        topQualityMap.put(topQualityMap.size(), new BoxObject(MathUtils.random(1, 7), false, 200, 450, false, null, new Vector2(2150, 4297), MathUtils.randomBoolean(), MathUtils.randomBoolean()));

        topQualityMap.put(topQualityMap.size(), new BoxObject(MathUtils.random(1, 7), false, 200, 450, false, null, new Vector2(3532, 4356), MathUtils.randomBoolean(), MathUtils.randomBoolean()));

        topQualityMap.put(topQualityMap.size(), new BoxObject(MathUtils.random(1, 7), false, 200, 450, true, null, new Vector2(3917, 3823), MathUtils.randomBoolean(), MathUtils.randomBoolean()));

        topQualityMap.put(topQualityMap.size(), new BoxObject(MathUtils.random(1, 7), false, 200, 450, false, null, new Vector2(4148, 2902), MathUtils.randomBoolean(), MathUtils.randomBoolean()));

        topQualityMap.put(topQualityMap.size(), new BoxObject(MathUtils.random(1, 7), false, 200, 450, false, null, new Vector2(4483, 2143), MathUtils.randomBoolean(), MathUtils.randomBoolean()));

        topQualityMap.put(topQualityMap.size(), new BoxObject(MathUtils.random(1, 7), false, 200, 450, false, null, new Vector2(3545, 610), MathUtils.randomBoolean(), MathUtils.randomBoolean()));

        topQualityMap.put(topQualityMap.size(), new BoxObject(MathUtils.random(1, 7), false, 200, 450, false, null, new Vector2(3929, 1937), MathUtils.randomBoolean(), MathUtils.randomBoolean()));

        topQualityMap.put(topQualityMap.size(), new BoxObject(MathUtils.random(1, 7), false, 200, 450, false, null, new Vector2(3464, 3554), MathUtils.randomBoolean(), MathUtils.randomBoolean()));

        topQualityMap.put(topQualityMap.size(), new BoxObject(MathUtils.random(1, 7), false, 200, 450, false, null, new Vector2(2034, 2456), MathUtils.randomBoolean(), MathUtils.randomBoolean()));




    }

    //////////////
    public boolean canMove(int x, int y) {       // проверка на пересечение с препятсвиями
        if (!intersectionWithEdgeMap(x, y, 300)) return false;
        for (Map.Entry<Integer, BoxObject> entry : topQualityMap.entrySet()) {
            if (entry.getValue().intersection(x, y, 15)) return false;
        }
        return true;
    }



    public boolean canMove(Vector2 v) {       // проверка на пересечение с препятсвиями
        return canMove((int) v.x, (int) v.y);
    }
///////////////////////

    private boolean intersectionWithEdgeMap(int x, int y, int border) {// пересечение с краем карты, с припятсвиями
        if (x > lengthMap -border) return false;
        if (x < border) return false;
        if (y > lengthMap - border) return false;
        if (y < border) return false;
        return true;
    }

    public Vector2 getFreeSpace() {
        //System.out.println("!!!!");
        int x, y;
        do {
            x = MathUtils.random(300, lengthMap - 300);
            y = MathUtils.random(300, lengthMap  - 300);
        } while (!canMove(x, y));
        return new Vector2(x, y);
    }

    public Vector2 getFreeSpace(boolean redCommand) {
        //System.out.println("!!!!");
        int x, y;
        do {
            if (redCommand)  {
                x = MathUtils.random(0, lengthMap/2);
                y = MathUtils.random(0, lengthMap/2);
            } else {
                x = MathUtils.random(lengthMap/2, lengthMap);
                y = MathUtils.random(lengthMap/2, lengthMap);
            }
        } while (!canMove(x, y));
        return new Vector2(x, y);
    }



}
