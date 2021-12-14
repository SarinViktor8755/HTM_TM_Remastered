package com.mygdx.game.Pause;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.Assets.AssetsManagerGame;
import com.mygdx.game.Service.StaticService;
import com.mygdx.game.ZombiKiller;

import java.util.ArrayDeque;

public class PauseScreen implements Screen {
    private ZombiKiller zk;
    private float timer;
    private float durationPause;
    private boolean adShow;
    private SpriteBatch spriteBatch;

    private BitmapFont font;
    private BitmapFont cardText;
    private OrthographicCamera camera;
    private FillViewport viewport;
    private ShaderProgram shaderProgram;
    private TextureAtlas TextureAtlas;

    private long promoTimeStart, promoTimeEnd;

    private float textAlpha;

    private TextureRegion helper, pauseText, vignette, head, arm, blood;

    private ArrayDeque<BodyPart> bodyParts;

    Music music;


    public PauseScreen(ZombiKiller zombiKiller, boolean adShow) {
        this.zk = zombiKiller;
        this.durationPause = 10;
        TextureAtlas = zombiKiller.assetsManagerGame.get("pauseAsset/pause", TextureAtlas.class);
        this.adShow = adShow;
    }

    private float getMoveKefMove() {
        float result = Interpolation.bounceIn.apply(MathUtils.sin(this.timer + 2.34f)) + 2;
        if (result <= 0) result = result * -1;
        return result;
    }

    private float getMoveKefRotation() {
        return MathUtils.sin(this.timer * 1.5f);
    }

    public PauseScreen(ZombiKiller zombiKiller, float time, boolean adShow) { // запуск если игрок не отыграл полной игры - и не должен видеть рекламы
        this.zk = zombiKiller;
        this.durationPause = time;
        TextureAtlas = zombiKiller.assetsManagerGame.get("pauseAsset/pause", TextureAtlas.class);
        this.adShow = adShow;
    }

//    public PauseScreen(ZombiKiller zk, float time, boolean advertising, ZombiKiller zombiKiller) { // запуск с рекламммой
//        this.zk = zk;
//        this.durationPause = time;
//        TextureAtlas = zombiKiller.assetsManagerGame.get("pauseAsset/pause", TextureAtlas.class);
//
//    }

    @Override
    public void show() {
       // AssetsManagerGame.

        this.timer = 0;
        spriteBatch = new SpriteBatch();
        camera = new OrthographicCamera(zk.WHIDE_SCREEN, zk.HIDE_SCREEN);
        viewport = new FillViewport(zk.WHIDE_SCREEN, zk.HIDE_SCREEN, camera);
        font = new BitmapFont(Gdx.files.internal("fonts/font.fnt"));
        cardText = new BitmapFont(Gdx.files.internal("fonts/font.fnt"));
        shaderProgram = new ShaderProgram(Gdx.files.internal("shaders/default.vert"), Gdx.files.internal("shaders/default.frag"));

        helper = TextureAtlas.findRegion("helper");
        pauseText = TextureAtlas.findRegion("puse");
        vignette = TextureAtlas.findRegion("vignette");
        textAlpha = 0;

        head = zk.assetsManagerGame.get("character/character", TextureAtlas.class).findRegion("tr7");
        arm = zk.assetsManagerGame.get("character/character", TextureAtlas.class).findRegion("tr8");
        blood = zk.assetsManagerGame.get("character/character", TextureAtlas.class).findRegion("blood");
        vignette = TextureAtlas.findRegion("vignette");

        bodyParts = new ArrayDeque();
        for (int i = 0; i < 200; i++) {
            this.bodyParts.add(new BodyPart());
        }
        //System.out.println(bodyParts.size());
        // Gdx.app.error("ANDROID", "1111111111111111111111");
        promoTimeStart = System.currentTimeMillis();
        //S
        if (adShow) zk.watchAds(); //реклама включение


        if (Gdx.files.internal("audio/rockroll.ogg").exists())
            music = Gdx.audio.newMusic(Gdx.files.internal("audio/rockroll.ogg"));
        //System.out.println("adShow  % " + adShow);

    }

    private void updaeteVolmeMusic() {
        if (!music.isLooping()) {
            music.play();
            music.setVolume(0);
            music.setLooping(true);
        }
        if (timer / 3 <= 1) {
            music.setVolume(timer / 3);
            textAlpha = Interpolation.fade.apply(timer / 3);
        } else {
            textAlpha = 1;
            if ((durationPause / 1000 - timer) / 3 <= 1) {
                music.setVolume((durationPause / 1000 - timer) / 3);
                textAlpha = (durationPause / 1000 - timer) / 3;
            }
        }
    }

    @Override
    public void render(float delta) {
        updaeteVolmeMusic();
        this.timer += Gdx.graphics.getDeltaTime();
        if (timer > durationPause / 1000) zk.getMainGaming();
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
        if ((StaticService.selectWithProbability(35)) && (getMoveKefRotation() > 0)) addBodyPart();
        spriteBatch.setColor(.5f, .2f + MathUtils.sinDeg(timer) / 5, .5f, textAlpha);
        spriteBatch.draw(vignette, 0, 0, zk.WHIDE_SCREEN / 2, zk.HIDE_SCREEN / 2, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 1 + getMoveKefRotation() / 10, 1 + getMoveKefRotation() / 10, 0);
        spriteBatch.setColor(1, 1, 1, textAlpha);
        spriteBatch.setColor(.1f, .2f, .1f, textAlpha);
        updateBodyPartAndRender(delta, spriteBatch);
        spriteBatch.setColor(1, 1, 1, textAlpha);
        spriteBatch.draw(helper, Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10, Gdx.graphics.getWidth() - (Gdx.graphics.getWidth() / 10), Gdx.graphics.getHeight() - (Gdx.graphics.getHeight() / 10));
        spriteBatch.draw(pauseText, Gdx.graphics.getWidth() / 3f, Gdx.graphics.getHeight() * 0.75f, pauseText.getRegionWidth() / 2, pauseText.getRegionHeight() / 2, pauseText.getRegionWidth(), pauseText.getRegionHeight(),
                getMoveKefMove(), getMoveKefMove(),
                0 + (getMoveKefRotation()) * 7);

        //System.out.println(getMoveKefMove());
        spriteBatch.setColor(1, 1, 1, 1);
        font.draw(spriteBatch, "Next round : " + (int) ((durationPause / 1000) - timer) + " sec", pauseText.getRegionWidth() * .1f, 150);
        cardText.draw(spriteBatch, "donat account:", pauseText.getRegionWidth() * .1f ,90);
        cardText.draw(spriteBatch, "4048 4150 3097 7066", pauseText.getRegionWidth() * .1f ,50);
        spriteBatch.end();
        //promoTimeEnd = System.currentTimeMillis();
        if (promoTimeStart != 0 && adShow) {
            durationPause -= System.currentTimeMillis() - promoTimeStart;
            promoTimeStart = 0;

        }

    }

    @Override
    public void resize(int width, int height) {
        //viewport.update(width, height);

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
        shaderProgram.dispose();
        spriteBatch.dispose();
        music.stop();
    }

    private void updateBodyPartAndRender(float dt, SpriteBatch spriteBatch) {
        for (BodyPart bodyPart : bodyParts) {
            bodyPart.update(dt);
            bodyPart.render(spriteBatch);
        }
    }

    private void addBodyPart() {
        BodyPart bp = bodyParts.removeLast();
        TextureRegion textureRegion;

        if (StaticService.selectWithProbability(50)) {
            textureRegion = head;
        } else if (StaticService.selectWithProbability(40)) {
            textureRegion = arm;
        } else textureRegion = blood;
        bp.getBodyPart(textureRegion);
        bodyParts.addFirst(bp);
    }

}
