package com.mygdx.game.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 * Created by 1 on 27.04.2021.
 */

public class MusicGame {
    private Music music;

    public MusicGame() {
        if (Gdx.files.internal("audio/music.ogg").exists())
            music = Gdx.audio.newMusic(Gdx.files.internal("audio/music.ogg"));
    }

    public void pleyMusic(){
        this.music.play();
        this.music.setVolume(100);
        this.music.setLooping(true);
    }

    public void stopMusic(){
        this.music.stop();
        this.music.setVolume(0);
    }

    public void musicStopPley(){
        this.music.stop();
    }

    public void dispose(){
        this.music.dispose();
    }

}
