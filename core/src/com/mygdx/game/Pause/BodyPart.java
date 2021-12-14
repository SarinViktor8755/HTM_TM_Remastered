package com.mygdx.game.Pause;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by 1 on 02.05.2020.
 */

public class BodyPart {
    private Vector2 position;
    private TextureRegion textureRegion;
    private float rotation;
    private float score;
    private float dSpeed;
    private float dRotation;

    public BodyPart() {
        this.position = new Vector2(0, 0);
        this.textureRegion = null;
        this.rotation = 0;
        this.score = 5;
        this.dSpeed = 0;
        this.dRotation = 0;
    }

    public void getBodyPart(TextureRegion textureRegion) {
        this.position = new Vector2(MathUtils.random(Gdx.graphics.getWidth()), Gdx.graphics.getHeight() + 100);
        this.textureRegion = textureRegion;
        this.rotation = MathUtils.random(360);
        this.score = 5;
        this.dSpeed = MathUtils.random(50,501);
        this.dRotation = MathUtils.random(-5.5f, 5.5f);

    }

    public void update(float dt) {
        if (!this.isLive()) return;
        dSpeed += 350 * dt;
        position.y += -dSpeed * dt;
        this.score = dRotation * dt;
        this.rotation += dRotation;
        if(position.y < 150) deatdPart();

    }

    public void render(SpriteBatch spriteBatch) {
        if (!this.isLive()) return;
        spriteBatch.draw(this.textureRegion, position.x, position.y, 50, 50, 100, 100, 3, 3, rotation);
    }

    public void deatdPart() {
        this.textureRegion = null;
    }

    public boolean isLive() {
        if (this.textureRegion == null) return false;
        else return true;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    public void setTextureRegion(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
