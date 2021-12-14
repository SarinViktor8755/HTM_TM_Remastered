package com.mygdx.game.Ip;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MainGaming;
import com.mygdx.game.Service.OperationVector;

/**
 * Created by 1 on 03.09.2019.
 */

public class AndroidInputProcessorGamePley implements InputProc {
    private MainGaming mg;

    private PositionDelta mov, rotation;
    private int centor;
    private float kefLeftSide;

    private final float tacktBubleClick = .25f; // это время за которе должно прозойти двойное нажатие
    private float dubleClickTimer = 0; // счетчик на двойное нажатие


    public AndroidInputProcessorGamePley(MainGaming mg) {
        this.mg = mg;
        mov = new PositionDelta();
        rotation = new PositionDelta();
//        centor = Gdx.app.getGraphics().getWidth() / 2;
//        kefLeftSide = Gdx.app.getGraphics().getWidth() / 720;
        centor = Gdx.app.getGraphics().getWidth() / 2;
        kefLeftSide = Gdx.app.getGraphics().getWidth() / (centor * 2);
        //System.out.println(centor);
        //System.out.println(kefLeftSide);
//        System.out.println(":: "+kefLeftSide);
//        System.out.println(Gdx.graphics.getWidth());
//        System.out.println(Gdx.app.getGraphics().getWidth());
//        System.out.println("----------------------------");

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        if (x < centor && (!mov.isActiv())) {
            //Gdx.app.error("MyTag", "!ПЕРЕДВИЖЕНИЕ " + pointer);
            mov.setPointer(pointer);
            mov.setStartPositionXY(x, y);
            return false;
        } else {
            if (!mg.getHero().isLive()) {
                mg.getHero().respawn();
            } else {
                if (dubleClickTimer != 0)
                    mg.getHero().makeHit(); // нанести удар порпобовать )))
            }

            //Gdx.app.error("MyTag", "!ВРАЩЕНИЕ " + pointer);
            rotation.setPointer(pointer);
            rotation.setStartPositionXY(x, y);
            return false;
        }
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {

        if (pointer == rotation.getPointer()) {
            float rot = (x - rotation.getStartPosition().x) * kefLeftSide;
            if ((rot < 50) && (rot > -50) && (rot != 0)) {
                rotationPers(rot);
                //Gdx.app.error("Поворот", x + " ::: " + rotation.getStartPosition().x +" ::: "+ (x - rotation.getStartPosition().x + " ::: "+ pointer + " :: "+ rot * kefLeftSide) );
                rotation.setStartPositionXY(x, y);
            }
            return false;
        }

        if (pointer == mov.getPointer()) {
            OperationVector.setTemp_vector(((mov.getStartPosition().x - x)) * -1, ((mov.getStartPosition().y - y)));
            movePers(OperationVector.get_Setter_Temp_vector());

            return false;
        }
        return true;
    }


    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        if (pointer == mov.getPointer()) {
            mov.setPointer(-1);
            //Gdx.app.error("MyTag", "ПЕРЕДВИЖЕНИЕ !! " + pointer);
        } else if (pointer == rotation.getPointer()) {
            rotation.setPointer(-1);
            setDubleClickTimer(tacktBubleClick);
            //Gdx.app.error("MyTag", "ВРАЩЕНИЕ !! " + pointer);
        }
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }


    ///////////////////
    private void movePers(float deltaX, float deltaY) {
        mg.getHero().goToPoint(OperationVector.getBufferVector(deltaX, deltaY).rotate(mg.getHero().getCookAngle().angle() - 90));
    }

    private void movePers(Vector2 mov) {
        movePers(mov.x, mov.y);
    }

    /////
    private void rotationPers(float deltaX) { // РАБОТАЕТ НЕ ВЕРНО
        //Gdx.app.error("rotationPers", "                  "+   (deltaX));
        mg.getHero().getCookAngle().rotate(deltaX * (-1));
    }


    public boolean isMove() {
        return this.mov.isActiv();
    }

    public PositionDelta getMov() {
        return mov;
    }

    public PositionDelta getRotation() {
        return rotation;
    }


    /////////////////////////////////////////////////// Двойной клик в левой стороне экрана
    private boolean upDateDubleClickTimer(float dt) {
        if (dubleClickTimer > 0) {
            dubleClickTimer = dubleClickTimer - dt;
            return true;
        } else {
            dubleClickTimer = 0;
            return false;
        }
    }

    public void setDubleClickTimer(float dubleClickTimer) {
        this.dubleClickTimer = dubleClickTimer;
    }

    @Override
    public void act(float deltTime) {
        upDateDubleClickTimer(deltTime);
        //System.out.println(this.dubleClickTimer);
    }
}
