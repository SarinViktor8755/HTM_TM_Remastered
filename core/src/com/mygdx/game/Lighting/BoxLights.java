package com.mygdx.game.Lighting;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

import box2dLight.ConeLight;
import box2dLight.DirectionalLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;

/**
 * Created by 1 on 26.03.2021.
 */

public class BoxLights {
    private World world;
    private RayHandler rayHandler;

    private PointLight pointLightHero;
    private DirectionalLight pointLightHeroDirectiona;

    private ConeLight coneLight;

    private Box2DDebugRenderer box2DDebugRenderer;

    private ArrayList<PointLight> pointLightsList;


    private int count = 0;

    public BoxLights() {


        this.world = new World(new Vector2(0, 0), true);
        this.rayHandler = new RayHandler(this.world);
        rayHandler.setAmbientLight(.1f);

        box2DDebugRenderer = new Box2DDebugRenderer();

        RayHandler.useDiffuseLight(true);


////////////////////
        Color colorPoint;
        pointLightsList = new ArrayList<PointLight>();
        PointLight pl;
        pointLightHero = new PointLight(rayHandler, 20, Color.WHITE, 1800, 0, 0);
        for (int i = 0; i < 5000; i += 1000) {
            for (int j = 0; j < 5000; j += 1000) {
                if (MathUtils.randomBoolean()) colorPoint = Color.CHARTREUSE;
                else if ((MathUtils.randomBoolean())) colorPoint = Color.RED;
                else if ((MathUtils.randomBoolean())) colorPoint = Color.NAVY;
                else colorPoint = Color.BROWN;

                pl = new PointLight(rayHandler, 7, colorPoint, MathUtils.random(1200,2000), j + MathUtils.random(-200, 200), i + MathUtils.random(-200, 200));
                pointLightsList.add(pl);
                //System.out.println("add");
            }
        }


    }

    public void upDateLights(float xHero, float yHero, float align) {
        pointLightHero.setPosition(xHero, yHero);

        for (PointLight p : pointLightsList) {
            p.setPosition(p.getX()+ MathUtils.random(-10,10),p.getY()+ MathUtils.random(-10,10));
            p.setPosition(MathUtils.clamp(p.getPosition().x,0,5000),MathUtils.clamp(p.getPosition().y,0,5000));
        }



    }

    public void renderLights(Camera camera) {
        rayHandler.setCombinedMatrix((OrthographicCamera) camera);
        rayHandler.updateAndRender();
        //  box2DDebugRenderer.render(world,camera.combined);
    }
}
