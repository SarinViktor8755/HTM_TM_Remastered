package com.mygdx.game.Ip;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MainGaming;

/**
 * Created by 1 on 03.09.2019.
 */

public class DesktopInputProcessorGamePley implements InputProc {
    MainGaming mg;
    private final float tacktBubleClick = .25f; // это время за которе должно прозойти двойное нажатие
    private boolean move;
    private Vector2 move_vector;
    private Integer mauseX;

    boolean leftB, rideB, bottomB, upB, lefButtonMause;


    public DesktopInputProcessorGamePley(MainGaming mg) {
        this.mg = mg;
        move = false;
        move_vector = new Vector2(0, 0);

        leftB = false;
        rideB = false;
        bottomB = false;
        upB = false;
        lefButtonMause = false;
    }


    @Override
    public boolean keyUp(int keycode) {
       // System.out.println(keycode);
        if (keycode == 32) {
//            move = true;
//            move_vector.add(-1000, 0);
            rideB = false;
        }

        if (keycode == 29) {
//
//            move = true;
//            move_vector.add(1000, 0);
            leftB = false;
        }

        if (keycode == 51) {
//            move = true;
//            move_vector.add(0, 1000);
            upB = false;

        }

        if (keycode == 47) {
//            move = true;
//            move_vector.add(0, -1000);
            bottomB = false;
        }

        if(keycode == 131) {
            Gdx.app.exit();

        }
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {

        if (keycode == 32) {
//            move = true;
//            move_vector.add(-1000, 0);
            rideB = true;
        }

        if (keycode == 29) {
//
//            move = true;
//            move_vector.add(1000, 0);
            leftB = true;
        }

        if (keycode == 51) {
//            move = true;
//            move_vector.add(0, 1000);
            upB = true;

        }

        if (keycode == 47) {
//            move = true;
//            move_vector.add(0, -1000);
            bottomB = true;
        }
        return false;
    }


    @Override
    public boolean keyTyped(char character) {
        //  move_vector.setZero();
        //  System.out.println(character);
//
//        if (character == 'a') {
//            move = true;
//            move_vector.x -= 1000;
//
//        }
//        if (character == 'd') {
//            move = true;
//            move_vector.x +=1000;
//        }
//
//        if (character == 'w') {
//            move = true;
//            move_vector.y -=1000;
//        }
//
//        if (character == 's') {
//            // System.out.println("-------------------");
//            move = true;
//            move_vector.y += 1000;
//
//        }
//        System.out.println(move_vector);

        // move_vector.setAngle(mg.getHero().getAcceleration().angleRad());
        mg.getHero().getAcceleration().add(move_vector);

        //move_vector.setZero();
//move_vector.set(mg.getHero().getAcceleration());
//        System.out.println(move_vector);
//        //move_vector.angleRad(mg.getHero().getCookAngle());
//        //System.out.println(move_vector.);
//        move_vector.setZero();


        //System.out.println(mg.getHero().getCookAngle().angle());

//
//        move_vector.angle(mg.getHero().getCookAngle());
//        mg.getHero().getAcceleration().setZero();
//        mg.getHero().getAcceleration().add(move_vector);

        return false;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
//        move_vector.angle(mg.getHero().getCookAngle());
//        move = true;
        lefButtonMause = true;
        //move_vector.setZero();

//        if (x <= 720 / 2) {
//            mov.setZero();
//            mov.setXY(x,y);
//            mov.setActiv(true);
//            mov.setButtton(pointer);
//        } else {
////            rotation.getStartPosition().set(x,y);
////            rotation.setXY(x,y);
////            rotation.setActiv(true);
////            rotation.setButtton(pointer);
//        }
        return false;
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        return false;
    }


    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        lefButtonMause = false;
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        int delta = 0;
        if (mauseX == null) mauseX = screenX;
        else {
            delta = mauseX - screenX;
            mauseX = screenX;
        }
        mg.getHero().getCookAngle().rotate((float) delta/6f);
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }


    private void movePers(float deltaX, float deltaY) {


        //mg.getHero().goToPoint(OperationVector.getBufferVector(deltaX,deltaY).rotate(mg.getHero().getCookAngle().angle() - 90));
        //mg.getHero().goToPoint(OperationVector.getBufferVector(deltaX,deltaY).rotate(mg.getHero().getCookAngle().angle() - 90));
    }


    private void rotationPers(float deltaX) {
        mg.getHero().getCookAngle().rotate(deltaX * (-1));
    }


    @Override
    public boolean isMove() {
        return move;
    }

    @Override
    public void act(float deltTime) {

        if(lefButtonMause) mg.getHero().makeHit();
        move_vector.setZero();
        if (leftB) move_vector.x -= 1000;
        if (rideB) move_vector.x += 1000;

        if (upB) move_vector.y += 1000;
        if (bottomB) move_vector.y -= 1000;
        move_vector.limit(1000);

        if (move_vector.len() == 0) move = false;
        else {
            move = true;
            move_vector.setAngle(move_vector.angle() + mg.getHero().getCookAngle().angle() - 90);
            mg.getHero().getAcceleration().add(move_vector);
        }


        //System.out.println(move_vector);


    }
}
