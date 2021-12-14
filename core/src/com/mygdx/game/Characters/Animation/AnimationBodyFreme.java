package com.mygdx.game.Characters.Animation;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by 1 on 12.12.2019.
 */

public class AnimationBodyFreme {
    int nomerTexture;
    boolean flop;  // переваравчивает всю текстуру
    public float timeDuration;


    public AnimationBodyFreme(float timeDuration, int nomerTexture, boolean flop) {
        this.nomerTexture = nomerTexture;
        this.flop = flop;
        this.flop = false;
    }

    public AnimationBodyFreme(float timeDuration, int nomerTexture) {
        this.timeDuration = timeDuration;
        this.nomerTexture = nomerTexture;
        this.flop = false;
    }

    @Override
    public String toString() {
        return "AnimationBodyFreme{" +
                "nomerTexture=" + nomerTexture +
                ", flop=" + flop +
                ", timeDuration=" + timeDuration +
                '}';
    }
}
