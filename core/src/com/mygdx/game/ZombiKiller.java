package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.GdxNativesLoader;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.mygdx.game.Assets.AssetsManagerGame;
import com.mygdx.game.LoadingScreen.LoadingScreen;
import com.mygdx.game.LoadingScreen.StartScreen;
import com.mygdx.game.Pause.PauseScreen;
import com.mygdx.game.adMod.AdAds;


public class ZombiKiller extends Game {
    public AssetManager assetsManagerGame;

    static public final int WHIDE_SCREEN = 720;
    static public final int HIDE_SCREEN = 1280;

//    static public final int HIDE_SCREEN = 2340;
//    static public final int WHIDE_SCREEN = 1080;
    public byte tip = 0;
    private AdAds adMod;

    private MainGaming mGaming;
    private PauseScreen pauseScreen;
    private LoadingScreen loadingScreen;
    private StartScreen startScreen;

    public ZombiKiller(int tip,AdAds adMod) {

        this.adMod = adMod;
        this.tip = (byte) tip;

    }

    public ZombiKiller(int tip) {
        this.tip = (byte) tip;
        adMod = new AdAds() {
            @Override
            public void show() {

            }
        };
    }


    public boolean isAndroid(){
        if(tip == 1) return true; return false;
    }

    public void watchAds(){
        this.adMod.show();
    }

    @Override
    public void create() {
        GdxNativesLoader.load();
        this.startScreen = new StartScreen(this);
        this.assetsManagerGame = new AssetManager();
//       // while (AssetsManagerGame.loadAsset(assetsManagerGame))
//        this.assetsManagerGame = AssetsManagerGame.loadAllAsset(this.assetsManagerGame);
        getMainGaming();

    }

    public void getPauseScreen(){

        //this.mGaming.dispose();
        this.pauseScreen = new PauseScreen(this,true);
        this.setScreen(this.pauseScreen);
        //AssetsManagerGame.unloadAllAsset(mGaming.getAssetsManagerGame());
       // mGaming.musicGame.dispose();
    }

    public void getPauseScreen(int PauseTime){

      //  this.assetsManagerGame = AssetsManagerGame.loadAllAsset(this.assetsManagerGame);
        mGaming.getMainClient().client.stop();
        boolean ad = true;
        if(mGaming.getTimeInGame() < 10) ad = false;
        this.pauseScreen = new PauseScreen(this, PauseTime,ad);
        this.setScreen(this.pauseScreen);
        mGaming.dispose();
       // mGaming.getAssetsManagerGame().
        //AssetsManagerGame.unloadAllAsset(mGaming.getAssetsManagerGame());
        //mGaming.musicGame.dispose();

    }

    public void getLoadingScreen(){
        loadingScreen = new LoadingScreen(this);
        this.setScreen(loadingScreen);
    }

    public void loarToGame(){

       // this.loadingScreen.dispose();
        this.mGaming = new MainGaming(this);
        mGaming.dispose();
       // mGaming.musicGame.dispose();

       // this.setScreen(this.mGaming);
    }

    public void getMainGaming(){
        //if(this.loadingScreen != null) this.loadingScreen.dispose();
      //  if(this.pauseScreen != null) this.pauseScreen.dispose();
        this.mGaming = new MainGaming(this);
        this.setScreen(this.mGaming);
        //this.setScreen(this.startScreen);
    }

    public void getMainGameScreen(){
        this.setScreen(this.mGaming);
    }

    public Screen getMainGameScreen(boolean flag){
        return  this.mGaming;
    }

    public void render() {
            super.render(); // важно!
    }



}
