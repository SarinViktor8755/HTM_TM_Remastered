package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by 1 on 11.07.2020.
 */

public class RenderStartScreen {
    private ZombiKiller zk;
    private float timer;

    private BitmapFont font;
    private Camera camera;
    private Viewport viewport;
    private SpriteBatch batch;


    public RenderStartScreen(ZombiKiller zk, Camera camera, Viewport viewport, SpriteBatch batch) {
        this.zk = zk;
        this.camera = camera;
        this.viewport = viewport;
        this.batch = batch;
        this.timer = 0;
        this.font = new BitmapFont();
        font.getData().setScale(3);
    }

    public void render(float delta) {
        this.timer += delta;
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        //System.out.println("render start game");
            font.draw(batch, getText(), -250, 0);
        batch.end();
    }

    private String getText() {
        if(this.timer > 3) this.timer = 0;
        if (this.timer > 2) {return "Server connection ...";}
        if (this.timer > 1) return "Server connection ..";
        if (this.timer > 0) return "Server connection .";
        return "Server connection";
    }
}
