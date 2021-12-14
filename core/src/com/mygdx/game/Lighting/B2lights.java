package com.mygdx.game.Lighting;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import com.mygdx.game.MainGaming;

import java.util.ArrayList;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;

public class B2lights {
    private World world;

    private PointLight pointLightHero;
    private ObFromLight object;
    private RayHandler rayHandlerHero;

    private ArrayList<PointLight> pointLightsList = new ArrayList<PointLight>();

    private ConeLight coneLightHero;

    private BuletFlash buletFlash;

    public B2lights(MainGaming mg) {
       //Gdx.app.log("Gdx version", com.badlogic.gdx.Version.VERSION);
        this.world = mg.getWorld();
        pointLightsList = new ArrayList<PointLight>();
        RayHandler.useDiffuseLight(true);
        this.rayHandlerHero = new RayHandler(this.world);
        object = new ObFromLight(this.world); // припятсвия
        object.crearBodys(mg.getIndexMap().getTopQualityMap_Box());
        PointLight pl;

        for (int i = 0; i < 5000; i += 1000) {
            for (int j = 0; j < 5000; j += 1000) {
                pl = new PointLight(rayHandlerHero, 8, getColorFromPoint(), 1300, j, i);
                pl.setIgnoreAttachedBody(false);
                pointLightsList.add(pl);
            }
        }

        pointLightHero = new PointLight(rayHandlerHero, 4, Color.WHITE, 700, 0, 0); /// свитильник героя
        coneLightHero = new ConeLight(rayHandlerHero, 65, Color.WHITE, 1500, 0, 0, 90, 60);
//
        buletFlash = new BuletFlash(rayHandlerHero);


        for (Body cars : object.getBodyList()) {
            pl = new PointLight(rayHandlerHero, 8, getColorFromPoint(), 1500, cars.getPosition().x, cars.getPosition().y);
            pl.attachToBody(cars);
        }

        rayHandlerHero.setAmbientLight(.7f);
       // rayHandlerHero.setShadows();
    }


    Color getColorFromPoint() {
        Color colorPoint;
        if (MathUtils.randomBoolean(.1f)) colorPoint = Color.CHARTREUSE;
        else if ((MathUtils.randomBoolean(.1f))) colorPoint = Color.RED;
        else if ((MathUtils.randomBoolean(.1f))) colorPoint = Color.NAVY;
        else if ((MathUtils.randomBoolean(.1f))) colorPoint = Color.BLUE;
        else if ((MathUtils.randomBoolean(.2f))) colorPoint = Color.OLIVE;
        else if ((MathUtils.randomBoolean(.3f))) colorPoint = Color.YELLOW;
        else colorPoint = Color.BROWN;
        return colorPoint;
    }


    public void upDateLights(float xHero, float yHero, float align) {
        world.step(1 / 60f, 1, 1);
        coneLightHero.setPosition(xHero, yHero);
        pointLightHero.setPosition(xHero, yHero);
        coneLightHero.setDirection(align);
        buletFlash.upDate();




    }

    public void renderLights(Camera camera) {
        rayHandlerHero.setCombinedMatrix((OrthographicCamera) camera);
        rayHandlerHero.updateAndRender();
//        float a = MathUtils.random(1f);
//        System.out.println(a);
//        rayHandlerHero.setAmbientLight(a);
    }


    public boolean isAtShadow(float x, float y) {
        return rayHandlerHero.pointAtShadow(x, y);
    }

    public Texture getTexture() {
        return rayHandlerHero.getLightMapTexture();
    }


    public void startBulletFlash(float x, float y) {
        buletFlash.newFlesh(x, y);
    }

}
