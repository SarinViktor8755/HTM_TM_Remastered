package com.mygdx.game.BloodBullet;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Flash {
    private float time;
    private Vector2 position;

    public Flash(Vector2 position) {
        this.position = position;
    }

    public Flash(int x,int y) {
        this.position = new Vector2(x,y);
    }

    public void render(SpriteBatch spriteBatch, TextureRegion textureRegion){
        spriteBatch.setColor(1,1,1,1);
        spriteBatch.draw(textureRegion,position.x,position.y,1,1,textureRegion.getRegionWidth(),textureRegion.getRegionWidth(),1,1,0);
    }

    public void update(float dt){
        this.time+=dt;
    }





}
