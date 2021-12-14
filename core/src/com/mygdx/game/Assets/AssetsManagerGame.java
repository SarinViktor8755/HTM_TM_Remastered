package com.mygdx.game.Assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.GdxNativesLoader;

import java.util.HashMap;
import java.util.Map;

public class AssetsManagerGame {

    static HashMap<String, Class> assets;

    static public final AssetManager loadAllAsset(AssetManager assetManager) {
        GdxNativesLoader.load();
        assets = new HashMap<String, Class>();
        if (assets.size() < 1) {
            assets.put("pauseAsset/pause", TextureAtlas.class);
            assets.put("character/character", TextureAtlas.class);
            assets.put("map/ground", TextureAtlas.class);
            assets.put("audio/best.ogg", Sound.class);
            assets.put("audio/rockroll.ogg", Sound.class);
            assets.put("audio/best.ogg", Sound.class);
            assets.put("audio/music.ogg", Sound.class);
            assets.put("audio/hit1.ogg", Sound.class);
            assets.put("audio/hit2.ogg", Sound.class);
            assets.put("audio/death1.ogg", Sound.class);
            assets.put("audio/death2.ogg", Sound.class);
            assets.put("audio/top1.ogg", Sound.class);
            assets.put("audio/top2.ogg", Sound.class);
            assets.put("audio/top3.ogg", Sound.class);
            assets.put("audio/f.ogg", Sound.class);
            assets.put("audio/shotgun.ogg", Sound.class);
            assets.put("audio/loose.ogg", Sound.class);
            assets.put("audio/win.ogg", Sound.class);
            assets.put("audio/pistolShooting1.ogg", Sound.class);
            assets.put("audio/pistolShooting2.ogg", Sound.class);
            assets.put("voice/voice1.ogg", Sound.class);
            assets.put("voice/voice2.ogg", Sound.class);
            assets.put("voice/voice3.ogg", Sound.class);
            assets.put("voice/voice4.ogg", Sound.class);
            assets.put("voice/voice5.ogg", Sound.class);
            assets.put("audio/lostPrimuschestvo.ogg", Sound.class);
            assets.put("fonts/font.fnt", BitmapFont.class);
            assets.put("audio/shotgun.ogg", Sound.class);
            assets.put("map/obstacles", TextureAtlas.class);
        }

        //System.out.println("::::__ " + assets.size());
        for (Map.Entry<String, Class> entry : assets.entrySet()) {
            if (assetManager.isLoaded(entry.getKey(), entry.getValue())) continue;
                assetManager.load(entry.getKey(), entry.getValue());
//            assetManager.finishLoadingAsset(entry.getKey());
                //  System.out.println(assetManager.isLoaded(entry.getKey(), entry.getValue())+ " "+ entry.getKey());
            }
            assetManager.finishLoading();
            assetManager.update();
//        for (Map.Entry<String, Class> entry : assets.entrySet()) {
//            System.out.println(assetManager.isLoaded(entry.getKey(), entry.getValue())+ " "+ entry.getKey());
//        }

//            System.out.println(assets.size());
//            System.out.println(assetManager.getLoadedAssets());
//        System.out.println(assetManager.isLoaded("map/obstacles"));
            return assetManager;
        }

//    static public final AssetManager loadLoadingScreen(AssetManager assetManager) {
//        assetManager.load("pauseAsset/pause", TextureAtlas.class);
//        assetManager.finishLoading();
//        return assetManager;
//    }

    static public final AssetManager unloadAllAsset(AssetManager assetManager) {
        assetManager.dispose();
        return assetManager;
    }

    static public float getProgress(AssetManager assetManager) {
        return assetManager.getProgress();

    }

//    static public boolean loadAsset(AssetManager assetManager) {
//        if (!assetManager.isLoaded("pauseAsset/pause")) return false;
//        if (!assetManager.isLoaded("map/obs")) return false;
//        if (!assetManager.isLoaded("map/ground")) return false;
//        if (!assetManager.isLoaded("character/character")) return false;
//
//        if (!assetManager.isLoaded("audio/top3.ogg")) return false;
//        if (!assetManager.isLoaded("voice/voice3.ogg")) return false;
//        if (!assetManager.isLoaded("voice/voice3.ogg")) return false;
//        if (!assetManager.isLoaded("voice/voice4.ogg")) return false;
//        if (!assetManager.isLoaded("fonts/font.fnt")) return false;
//        return true;
//    }


}
