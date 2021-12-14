package com.mygdx.game.Lighting;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MainGaming;


/**
 * Created by 1 on 06.05.2020.
 */

public class Lighting {
    private float shadow;



    private TextureRegion nith;
    //    private TextureRegion lith;
    private MainGaming mainGaming;
    private int maxFreme;
    private Vector2 tempVector;
    private final int size_t = 50;
//    private TreeSet<Integer> tempBulet;

    public Lighting(MainGaming mg) {
        this.mainGaming = mg;
        this.nith = mainGaming.getZk().assetsManagerGame.get("character/character", TextureAtlas.class).findRegion("night");
        maxFreme = 4;
        tempVector = new Vector2();


    }

    public void updateLighting(float delta) {
        shadow += degreeIllumination() * -delta * 2;
        shadow = MathUtils.clamp(shadow, .3f, 1f);
    }

    public void renderLighting(Batch spriteBatch) {

        int x = (int) mainGaming.getHero().getPosition().x;
        int y = (int) mainGaming.getHero().getPosition().y;
        for (int i = 0; i < size_t * 100; i += size_t) {
            for (int j = 0; j < 100 * size_t; j += size_t) {
                if (x < i - 1200) continue;
                if (x > i + 1200) continue;
                if (y < j - 1200) continue;
                if (y > j + 1200) continue;
                tempVector.set(i - size_t / 2, j - size_t / 2);
                // float count = Interpolation.pow3Out.apply(MathUtils.clamp(mainGaming.getHero().getPosition().cpy().sub(i, j).len2()/1506282 - time_index,.0f,.99f));


                float count = MathUtils.clamp(Interpolation.pow4Out.apply(MathUtils.clamp(mainGaming.getHero().getPosition().cpy().sub(i, j).len2() / 1006282 - shadow, .0f, .4f)), 0, .75f);

                spriteBatch.setColor(1, 1, 1, count);
                spriteBatch.draw(nith, i, j, size_t, size_t);
//                int s = ((i / size_t) * 1000) + (j / size_t);
//                System.out.println(s);
//                if (this.tempBulet.contains(s)) {
//                    int SRC = spriteBatch.getBlendSrcFunc();
//                    int DST = spriteBatch.getBlendDstFunc();
//                    spriteBatch.setColor(1, 1, .5f, alpha - .3f);
//                    spriteBatch.setBlendFunction(Gdx.gl.GL_SRC_ALPHA, Gdx.gl.GL_ONE_MINUS_CONSTANT_ALPHA);
//                    spriteBatch.draw(lith, i-size_t, j - size_t, size_t * 2, size_t * 2);
//                    spriteBatch.setBlendFunction(SRC, DST);
//                }
            }
        }
        spriteBatch.setColor(1, 1, 1, 1);
    }

//    private void updateBulletTreeSet() {
//        this.tempBulet.clear();
//        for (Bullet bullet : mainGaming.getHero().getPoolBlood().getMyBulletQueue()) {
//            if (!bullet.isLive()) continue;
//            int x = (int) bullet.getPoition().x / size_t;
//            int y = (int) bullet.getPoition().y / size_t;
//            this.tempBulet.add((x * 1000) + y);
//        }
//    }

    private float degreeIllumination() {
        float time = mainGaming.getHero().getVelocity().len2() / 490000 - .5f;
        return time;
//return Interpolation.exp5In.apply(time/(60000*3))/20 ;
//        System.out.println(time);
//        System.out.println("-----------ww");

    }


}



