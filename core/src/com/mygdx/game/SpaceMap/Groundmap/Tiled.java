package com.mygdx.game.SpaceMap.Groundmap;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by 1 on 09.03.2020.
 */

public class Tiled {
   // private String texture;
    private int corner; // поворот

    private boolean flipX;
    private boolean flipY;

    TextureRegion ground1;
    TextureRegion ground2;


    public Tiled(int corner, boolean flipX, boolean flipY) {
        this.corner = corner;
        this.flipX = flipX;
        this.flipY = flipY;
    }

    public Tiled(TextureAtlas textureAtlas) {
        corner = MathUtils.random(1, 4);
        this.flipX = MathUtils.randomBoolean();
        this.flipY = MathUtils.randomBoolean();
        ground1 = textureAtlas.findRegion("ground1");
    }

    public int getCorner() {
        if (corner == 1) return 90;
        if (corner == 2) return 180;
        if (corner == 3) return 270;
        return 0;
    }

    public TextureRegion getTextureRender() {

        TextureRegion result = ground1;
        if(this.flipX!= result.isFlipX())result.flip(true,false);
        if(this.flipY!= result.isFlipY())result.flip(false,true);
        return result;


    }
}
