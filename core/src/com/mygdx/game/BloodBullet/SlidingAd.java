package com.mygdx.game.BloodBullet;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.MainGaming;

/**
 * Created by 1 on 02.05.2020.
 */

public class SlidingAd {
    private Vector2 positi;
    private TextureRegion textureRegion;
    private float scale, timer, endTime, alpha;


    public SlidingAd() {
        this.positi = new Vector2(250, 0);
        this.textureRegion = null;
        this.scale = 1;
        this.timer = 0;
        this.endTime = 0;
        this.alpha = 0;
    }

    public void upDateAd(float delta) {
        if (!isLive()) return;
        this.timer += delta;
        if (timer > endTime) deathAd();
        float k = MathUtils.clamp(Interpolation.pow5Out.apply(timer *.65f), 0, 1);
        scale = 1 + k;
        alpha = k;
        if(endTime - timer < endTime /10) alpha -=delta * 100;
    }

    public void renderAd(SpriteBatch spriteBatch, MainGaming mainGaming) {
        //System.out.println(MathUtils.sin(timer));
        Vector2 rot = new Vector2(mainGaming.getCamera().up.x, mainGaming.getCamera().up.y);
        float x = (mainGaming.getHero().getPosition().x + rot.x * 700) - textureRegion.getRegionWidth() / 2;
        float y = (mainGaming.getHero().getPosition().y + rot.y * 700) - textureRegion.getRegionHeight() / 2;
        spriteBatch.setColor(1, 1, 1, 1);
        spriteBatch.draw(this.textureRegion,
                x, y,
                textureRegion.getRegionWidth() / 2, textureRegion.getRegionHeight() / 2,
                textureRegion.getRegionWidth(), textureRegion.getRegionHeight(),
                scale, scale, rot.angle() - 90);
        spriteBatch.setColor(1, 1, 1, 1);
    }


    public void starterNewAd(float x, float y, float endTimer, TextureRegion textureRegion, float scale) {
        this.positi.set(x, y);
        this.textureRegion = textureRegion;
        this.scale = 1;
        this.endTime = endTimer;
        this.alpha = 0;
        this.timer = 0;
        this.scale = scale;
    }


    private void deathAd() {
        this.textureRegion = null;
    }

    public boolean isLive() {
        if (textureRegion == null) return false;
        else return true;
    }


}
