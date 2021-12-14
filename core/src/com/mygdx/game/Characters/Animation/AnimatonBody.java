package com.mygdx.game.Characters.Animation;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class AnimatonBody {

    private boolean flip;
    //public int next_weapon = 0;
    private ArrayList<AnimationBodyFreme> animationBodyFreme;

    public boolean isFlip() {
        return flip;
    }

    public void setFlip(boolean flip) {
        this.flip = flip;
    }

    public ArrayList<AnimationBodyFreme> getAnimationBodyFreme() {
        return animationBodyFreme;
    }

    public void setAnimationBodyFreme(ArrayList<AnimationBodyFreme> animationBodyFreme) {
        this.animationBodyFreme = animationBodyFreme;
    }

    public AnimatonBody() {
        animationBodyFreme = new ArrayList<AnimationBodyFreme>();
        flip = true;
        //this.next_weapon = 0;
    }

    public AnimatonBody(Texture baseFrame) {
        this.animationBodyFreme = new ArrayList<AnimationBodyFreme>();
        //this.next_weapon = 0;
    }

    public String getSpriteFromBody() {
        if (animationBodyFreme.size() == 0) return "defoult";
        else {
            //return  animationBodyFreme.get(0);
        }
        return "";
    }

    public void updateAnimation(float deltaTime) {
        if (animationBodyFreme.size() == 0) return;
        animationBodyFreme.get(0).timeDuration -= deltaTime;
        if (animationBodyFreme.get(0).timeDuration <= 0) {
            if (animationBodyFreme.get(0).flop) reverzFlip();
            animationBodyFreme.remove(animationBodyFreme.get(0));
        }
    }

    public void reverzFlip() {
        if (flip) flip = false;
        else {
            flip = true;
        }
    }

    public void addAnimationAttackPipe() {
        if (animationBodyFreme.size() != 0) return;
        this.animationBodyFreme.add(new AnimationBodyFreme(.05f, 2));
        this.animationBodyFreme.add(new AnimationBodyFreme(.05f, 3));
        this.animationBodyFreme.add(new AnimationBodyFreme(.05f, 4));
        this.animationBodyFreme.add(new AnimationBodyFreme(.05f, 5, true));
        this.animationBodyFreme.add(new AnimationBodyFreme(.3f, 1));

    }

    public void addAnimationAttackPistols() {
        if (animationBodyFreme.size() != 0) return;
        this.animationBodyFreme.add(new AnimationBodyFreme(.06f, 2));
        this.animationBodyFreme.add(new AnimationBodyFreme(.09f, 3));
        this.animationBodyFreme.add(new AnimationBodyFreme(.3f, 1));
    }

    public void addAnimationAttackShotgun() {
        if (animationBodyFreme.size() != 0) return;
        this.animationBodyFreme.add(new AnimationBodyFreme(.05f, 2));
        this.animationBodyFreme.add(new AnimationBodyFreme(.35f, 1));
        this.animationBodyFreme.add(new AnimationBodyFreme(.3f, 3));
        this.animationBodyFreme.add(new AnimationBodyFreme(.1f, 1));
    }

    public int getNomberanimation() {
        if (animationBodyFreme.size() == 0) {
            return 1;
        }
        return animationBodyFreme.get(0).nomerTexture;
    }


    @Override
    public String toString() {
        return "AnimatonBody{" +
                "flip=" + flip +
                ", animationBodyFreme=" + animationBodyFreme +
                '}';
    }
}

