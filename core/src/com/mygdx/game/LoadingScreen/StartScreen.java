package com.mygdx.game.LoadingScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.mygdx.game.ZombiKiller;

/**
 * Created by 1 on 11.07.2020.
 */

public class StartScreen implements Screen {

    private ZombiKiller zk;
    private float timer;

    private BitmapFont font;
    private OrthographicCamera camera;
    private FillViewport viewport;
    private SpriteBatch batch;


    public StartScreen(ZombiKiller zk) {
        this.zk = zk;
        this.camera = new OrthographicCamera(zk.WHIDE_SCREEN, zk.HIDE_SCREEN);
        this.viewport = new FillViewport(zk.WHIDE_SCREEN, zk.HIDE_SCREEN, camera);
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        this.font.getData().setScale(3);
        this.timer = 0;

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        this.timer += delta;
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        font.draw(batch, getText(), -250, 0);
        batch.end();
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

    }

    private String getText() {
        if(this.timer > 3) this.timer = 0;
        if (this.timer > 2) {return "Server connection ...";}
        if (this.timer > 1) return "Server connection ..";
        if (this.timer > 0) return "Server connection .";
        return "Server connection";
    }
}
