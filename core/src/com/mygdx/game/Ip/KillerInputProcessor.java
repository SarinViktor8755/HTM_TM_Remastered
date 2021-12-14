package com.mygdx.game.Ip;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MainGaming;
import com.mygdx.game.Service.OperationVector;

/**
 * Created by 1 on 14.08.2019.
 */

public class KillerInputProcessor implements GestureDetector.GestureListener {
    MainGaming mg;
    private Vector2 startSwapPosition;
    private Vector2 startPoint1;
    private Vector2 startPoint2;
    float grad;


    public KillerInputProcessor(MainGaming mg) {
        this.mg = mg;
        startSwapPosition = new Vector2(0, 0);
        startPoint1 = new Vector2();
        startPoint2 = new Vector2();
        grad = 0;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        //Gdx.app.error("touchDown", "x^ " + x + "y^ " + y);
        //mg.getHero().goToPoint((1),(1));
//        System.out.println(Gdx.graphics.getWidth());
//        System.out.println(Gdx.graphics.getHeight());


        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {

        // Gdx.app.error("tap", "x " + x + "y " + y + " count " + count + " button " + button);
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        //dx.app.error("longPress", "x " + x + "y " + y);
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        //Gdx.app.error("fling", "velocityX " + velocityX + " velocityY " + velocityY + " button " + button);
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {

        if (x <= 360)
            movePers(deltaX, deltaY);
        else rotationPers(deltaX);


        return true;

    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        //Gdx.app.error("RRRR", "EEEEEEEEEEE" + button );
        startSwapPosition.setZero();
        mg.getHero().stopSpeed();
//        mg.getHero().getVelocity().setZero();
//        //mg.getHero().stopSpeed();
//        //mg.getCamera().position.set(   0,0,1    );
//        Gdx.app.error("panStop ", "x " + x + " y " + y + " pointer " + pointer + " button" + button);
        return true;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        Vector2 m = new Vector2(OperationVector.getBufferVector(initialPointer1).sub(pointer1));

        if (initialPointer2.x <= 360) movePers(m.x, m.y);
        else {
            float rotation = 0;

//            startPoint1 = pointer2;
//            rotation = startPoint1.sub(pointer2).len();
//            rotationPers(rotation);
            rotationPers((initialPointer2.x - pointer2.x) * Gdx.graphics.getDeltaTime() * -1);

        }

        return false;
    }

    @Override
    public void pinchStop() {

    }

    private void movePers(float deltaX, float deltaY) {
        startSwapPosition.add(deltaX, deltaY * -1);
        mg.getHero().goToPoint(startSwapPosition.scl(Gdx.graphics.getDeltaTime()).clamp(0, 10).rotate(mg.getHero().getCookAngle().angle() - 90));
    }

    private void rotationPers(float deltaX) {
        //Gdx.app.error("fling", x + " : : " + y +" ::: deltaX "+ deltaX + " : : deltaY " + deltaY);
        mg.getHero().getCookAngle().rotate(deltaX * (-1));
    }


}
