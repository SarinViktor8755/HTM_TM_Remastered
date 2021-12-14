package com.mygdx.game.LoadingScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Assets.AssetsManagerGame;
import com.mygdx.game.ZombiKiller;

/**
 * Created by 1 on 04.05.2020.
 */

public class LoadingScreen implements Screen {
    private ZombiKiller zk;
    private float timer;

    private final float ENDING = 1;

    private TextureRegion centr;

    private OrthographicCamera camera;
    private FillViewport viewport;
    private SpriteBatch spriteBatch;
    private float[] arrArm;

    public LoadingScreen(ZombiKiller zk) {
        this.zk = zk;
    }

    @Override
    public void show() {
     //   AssetsManagerGame.loadLoadingScreen(zk.assetsManagerGame);
        this.timer = 0;
        this.spriteBatch = new SpriteBatch();
        this.camera = new OrthographicCamera();
        centr = zk.assetsManagerGame.get("pauseAsset/pause", TextureAtlas.class).findRegion("aim");

        spriteBatch = new SpriteBatch();
        camera = new OrthographicCamera(zk.WHIDE_SCREEN, zk.HIDE_SCREEN);
        viewport = new FillViewport(zk.WHIDE_SCREEN, zk.HIDE_SCREEN, camera);
        //zk.assetsManagerGame = AssetsManagerGame.loadAllAsset(zk.assetsManagerGame);

        arrArm = new float[]{0, 0, 0, 0, 0};

    }

    @Override
    public void render(float delta) {
        camera.update();
        //System.out.println(timer);
        if (timer > ENDING) zk.loarToGame();
        timer += delta;
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
        spriteBatch.draw(centr, 0, 0, zk.WHIDE_SCREEN / 2, zk.HIDE_SCREEN / 2, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 1, 1, 0);
        spriteBatch.end();


    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
    }
}
