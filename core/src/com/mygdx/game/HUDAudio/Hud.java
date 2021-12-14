package com.mygdx.game.HUDAudio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MainGaming;
import com.mygdx.game.Service.StaticService;

import java.util.ArrayDeque;


/**
 * Created by 1 on 07.10.2019.
 */

public class Hud implements Disposable {

    private Viewport viewport;
    private MainGaming mainGaming;
    private Stage stageHUD;
    private boolean connect;
    private EndingMathHUD endingMathHUD;
    private int point1, point2, timeM, timeS, myFrags, timer, liderMath, scorePlayer;
    private TextureRegion textureAim;
    public boolean first;
    boolean reversinterfece = false;

    private BitmapFont font;

    Label coinCountLabel;
    Label raitingTextLabel; // 1/3
    Label fragsTextLabel;
    Label timerTextLabel;
    Label liderMathLabel;
    Label notConnectLabel;


    public int getTimer() {
        return timer;
    }

    public int getMyFrags() {
        return myFrags;
    }

    public boolean isConnect() {
        return connect;
    }

    public void setConnect(boolean connect) {
        this.connect = connect;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public Hud(MainGaming mainGaming) {
        font = mainGaming.getAssetsManagerGame().get("fonts/font.fnt", BitmapFont.class);
        this.reversinterfece = false;
        endingMathHUD = new EndingMathHUD(mainGaming);
        viewport = new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        stageHUD = new Stage(viewport, mainGaming.getBatch());
        first = false;


        font.getData().setScale(.8f);
        font.getColor().set(.5f, .5f, .5f, 1);
        point1 = 0;
        point2 = 0;
        timeM = 0;
        timeS = 0;
        myFrags = -10;
        liderMath = 0;
        this.timer = 180000;
        connect = true;

        scorePlayer = -50;

        Label.LabelStyle style = new Label.LabelStyle(font, Color.WHITE);

        raitingTextLabel = new Label("0/0", style);
        fragsTextLabel = new Label("frag : 0", style);
        timerTextLabel = new Label("0:0", style);
        liderMathLabel = new Label("", style);
        notConnectLabel = new Label("", style);

        style.font.getData().scale(Gdx.graphics.getWidth()/720);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        table.add(raitingTextLabel).expandX().padTop(12.0f);
        table.add();
        table.add(timerTextLabel).expandX().padTop(12.0f);
        table.add();

        table.add(liderMathLabel).expandX().padTop(12.0f);
///////////////////////////////////////////
        table.row();
        table.add();
        table.add(notConnectLabel);
        table.add();
        table.add();
        table.add(fragsTextLabel).expandX().padTop(12.0f);

        coinCountLabel = new Label("", style);

        //raitingTextLabel.setColor(.3f,.3f,1,1);
        stageHUD.addActor(table);

    }

    public Stage getStageHUD() {
        return stageHUD;
    }

    public boolean isActual() {
        if (this.myFrags < 0) {
            return false;
        }
        return true;
    }


//    public void update(int myPosition, int sizePlayer, int myFrags, int timer, int max_fargs) {
//        this.nPlayer = sizePlayer;
//        this.myPosition = myPosition;
//        this.myFrags =


    public void update(int point2, int myFrags, int timer, int point1) {

//        System.out.println();
//        System.out.println(myFrags);
//        // System.out.println(max_fargs);
//        System.out.println(point1);
//        System.out.println(point2);
        this.point1 = point2;
        this.point1 = point1;

        this.myFrags = myFrags;
        this.timer = timer;
        this.liderMathLabel.setText("");
        //   this.liderMath = max_fargs;
        this.endingMathHUD.setUpdateToServer(true);

        //if(this.scorePlayer == )

    }


    public void update(int point2, int myFrags, int timer, int point1, boolean revers) {
//        System.out.println();
//        System.out.println(myFrags);
//        // System.out.println(max_fargs);
//        System.out.println(point1);
//        System.out.println(point2);

        this.point1 = point2;
        this.point2 = point1;
        this.myFrags = myFrags;
        this.timer = timer;
        this.liderMathLabel.setText("");
        //   this.liderMath = max_fargs;
        this.endingMathHUD.setUpdateToServer(true);
        this.reversinterfece = revers;
    }

    public void update(float delta) {

        //////////////////////////
        this.timer = (int) (this.timer - (delta * 1000));
        // raitingTextLabel.setText(myPosition + "/" + nPlayer);


        if (timeS > 9) timerTextLabel.setText(timeM + ":" + timeS);
        else timerTextLabel.setText(timeM + ":0" + timeS);
        fragsTextLabel.setText("k: " + myFrags);//крассный
        if (myFrags < 0) fragsTextLabel.setText("Frag: 0");
        transformationTime(this.timer);
        //if (myPosition > nPlayer) raitingTextLabel.setText(myPosition + "/" + myPosition);
        if (timer < 0) timerTextLabel.setText("0:00");

        if (!isConnect()) {
            timerTextLabel.setText("No connection..!!!");
            raitingTextLabel.setText("N/C");
            raitingTextLabel.setText("N/C");
            liderMathLabel.setText("N/C");
            raitingTextLabel.setText("N/C");
            fragsTextLabel.setText("N/C");
            this.endingMathHUD.setUpdateToServer(false);
            this.endingMathHUD.setWeCanFinish(false);
        }

        if (!reversinterfece) {
            liderMathLabel.setText("" + point1);
            raitingTextLabel.setText("" + point2);

            raitingTextLabel.setColor(1, .3f, .3f, 1);
            liderMathLabel.setColor(.3f, .3f, 1, 1);
        } else {
            liderMathLabel.setText("" + point2);
            raitingTextLabel.setText("" + point1);

            liderMathLabel.setColor(1, .3f, .3f, 1);
            raitingTextLabel.setColor(.3f, .3f, 1, 1);
        }

        this.endingMathHUD.update(delta);
//запрос счета
//        if ((scorePlayer == -50) && (MathUtils.randomBoolean(.1f))) {
//            //if() System.out.println("Запрос !!!");
//            try {
//
//            } catch (NullPointerException e) {
//            }
////            mainGaming.getMainClient().sendToServer(88, 1, 2, 3, 4, 6, 5, 3, "id");
//            System.out.println("Отправить запрос");
//            ///запрашиваем счет
//
//        }
    }

    public Label getTimerTextLabel() {
        return timerTextLabel;
    }

    public void setTimerTextLabel(Label timerTextLabel) {
        this.timerTextLabel = timerTextLabel;
    }

    private void transformationTime(int timer) {
        timeM = (timer / 1000) / 60;
        timeS = (timer / 1000) % 60;
    }

    public void render(float dt) {
        stageHUD.draw();

    }


    public int getLider() {
        if (point1 > point2) return 0; // blue wind
        if (point2 > point1) return 1; // red wind
        return MathUtils.random(0, 1);
    }


    @Override
    public void dispose() {
        stageHUD.dispose();
    }

    public int getFCommand1() {
        return point1;
    }

    public int getFCommand2() {
        return point2;
    }

    public boolean scoreIsNull() {
        if (scorePlayer == -50) return true;
        return false;

    }
}
