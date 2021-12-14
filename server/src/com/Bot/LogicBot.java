package com.Bot;

import com.SnapShots;
import com.StaticService;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.logicMatch.CommandLogic;

import java.util.Map;

/**
 * Created by 1 on 24.02.2020.
 */

public class LogicBot {
    private SimulationUnit simulationUnit;
    private float[] actionClockCounter = new float[5]; // счетчик тактов действий :: 0 - аццепт, направление, 1 - удар, 3- ожидание респауна, 4 - проверк(рядом соперники, и направление к нему)
    private int obstacle = 0;
    private int searchTarget = Integer.MAX_VALUE; // ID цели находящегося соперника

    private final float levelBot;

    public LogicBot(SimulationUnit simulationUnit) {
        this.simulationUnit = simulationUnit;
        obstacle = 0;
        actionClockCounter[0] = 100;
        searchTarget = Integer.MAX_VALUE;
        getSu().getCookAngle().set(1, 0);
        //levelBot = .3f;
        levelBot = MathUtils.random(2.6f, 3.0f);
    }

    public float[] getActionClockCounter() {
        return actionClockCounter;
    }

    long dt;
    /////////////////


    public void actionUnits() {             // все принятия решения все должно быть тут !!!!!!!!!!!!!!!!!!!!!!! точка входа

        dt = simulationUnit.getDeltaTime(); // обновление времени
        updateActionClockCounter(dt);           // обновляет ячейки дейвий.

        if (respown()) return; // проверяет жив ли бот
        if (searchTarget == Integer.MAX_VALUE) {
            decide(dt); //ПРОСТО ХОДИМ В какую ЛИБО СТОРОНУ
            makeDecisionWalking();//ОБХОД припятсвий
        } else {
            if (StaticService.selectWithProbability(10)) {

                decide(dt);//ПРОСТО ХОДИМ В какую ЛИБО СТОРОНУ
                return;
            }//ПРОСТО ХОДИМ В какую ЛИБО СТОРОНУ
            launchAttack();
        }

        updateCookAngle(dt);
        searchTargetAttack();
        getSu().sendCoordinates(); // сообщаем координаты серверу
        actionSpeed(); //выполнение движений !! - не трогать все отлично
        searchTarget = searchTargetAttack();
        // gринятие решений
        getOutTrap(); // еще раз порверяем на припятсвия слева спрво
    }

    private void updateCookAngle(float dt) {
        if (Math.abs(getSu().getCookAngle().angle() - getSu().getAcceleration().angle()) < 10)
            return;
        if (!StaticService.getDirectionRotation360((int) getSu().getAcceleration().angle(), (int) getSu().getCookAngle().angle()))
            getSu().getCookAngle().setAngle((getSu().getCookAngle().angle() + (dt * 0.5f)));
        else getSu().getCookAngle().setAngle((getSu().getCookAngle().angle() - (dt * 0.5f)));
    }

    private void getOutTrap() {
        if (StaticService.selectWithProbability(99)) return;
        if (!getSu().getIndexBot().getIndexMap().canMove(getSu().getPosition().cpy().sub(getSu().getCookAngle().cpy().rotate90(-1).scl(15)))) {
            getSu().getAcceleration().rotate(MathUtils.random(-90, -70));
            return;
        }
        if (!getSu().getIndexBot().getIndexMap().canMove(getSu().getPosition().cpy().sub(getSu().getCookAngle().cpy().rotate90(1).scl(15))))
            getSu().getAcceleration().rotate(MathUtils.random(70, 90));
    }


    private void decide(float dt) {
        if (actionClockCounter[0] < 0) {
            disconectWhereGo();
        }

    }

    private void launchAttack() {
        if (StaticService.selectWithProbability(80)) return;
        if (actionClockCounter[4] > 0) return;
        try {
            int gans = getSu().indexBot.getGameServer().getSnapShots().getWeaponByPlayersID(getMyID());
            if (gans == 1)
                actionClockCounter[4] = MathUtils.random(40 + (200 * levelBot), 180 + (200 * levelBot));
            if (gans == 2)
                actionClockCounter[4] = MathUtils.random(110 + (200 * levelBot), 200 + (200 * levelBot));
            if (gans == 3)
                actionClockCounter[4] = MathUtils.random(100 + (200 * levelBot), 200 + (200 * levelBot));
            if (gans == 1) {
                makeHitStick();
            } else {
                makeFirearms();
            }
        } catch (NullPointerException e) {
            //System.out.println("launchAttack");
            e.printStackTrace();
        }
    }

    private void makeHitStick() {
        try {
            int dist = StaticService.getDistance(getSu().getPosition().x, getSu().getPosition().y, getSu().getIndexBot().getGameServer().getSnapShots().getPlaeyrToId(searchTarget).getX(), getSu().getIndexBot().getGameServer().getSnapShots().getPlaeyrToId(searchTarget).getY());
            if ((dist > 350) && (StaticService.selectWithProbability(2 * levelBot)))
                if ((dist < 350) && (dist > 200) && (StaticService.selectWithProbability(10 * levelBot)))
                    getSu().makeHit();
            if ((dist < 200) && (dist > 110) && (StaticService.selectWithProbability(65 * levelBot)))
                getSu().makeHit();
            if ((dist < 110) && (StaticService.selectWithProbability(90 * levelBot)))
                getSu().makeHit();
            if (getSu().getVelocity().len() < 10) getSu().getAcceleration().set(1, 1);
            Vector2 a = new Vector2(getSu().getIndexBot().getGameServer().getSnapShots().getPlaeyrToId(searchTarget).getX(), getSu().getIndexBot().getGameServer().getSnapShots().getPlaeyrToId(searchTarget).getY());
            //getSu().getCookAngle().setAngle(a.cpy().sub(getSu().getPosition().cpy()).angle() - MathUtils.random(-60, 60));
            getSu().getAcceleration().setAngle(a.cpy().sub(getSu().getPosition().cpy()).angle() - MathUtils.random(-5, 5));
            getSu().getAcceleration().nor().scl(5000);
        } catch (NullPointerException e) {
            //e.printStackTrace();
        }
    }

    private void makeFirearms() {
        try {
            int dist = StaticService.getDistance(getSu().getPosition().x, getSu().getPosition().y, getSu().getIndexBot().getGameServer().getSnapShots().getPlaeyrToId(searchTarget).getX(), getSu().getIndexBot().getGameServer().getSnapShots().getPlaeyrToId(searchTarget).getY());
            if ((dist > 1500) && (StaticService.selectWithProbability(2)))
                if ((dist < 1500) && (dist > 500) && (StaticService.selectWithProbability(10 * levelBot)))
                    getSu().makeHit();
            if ((dist < 500) && (dist > 110) && (StaticService.selectWithProbability(65 * levelBot)))
                getSu().makeHit();
            if ((dist < 110) && (StaticService.selectWithProbability(90 * levelBot))) getSu().makeHit();
            if (getSu().getVelocity().len() < 10) getSu().getAcceleration().set(1, 1);
            Vector2 a = new Vector2(getSu().getIndexBot().getGameServer().getSnapShots().getPlaeyrToId(searchTarget).getX(), getSu().getIndexBot().getGameServer().getSnapShots().getPlaeyrToId(searchTarget).getY());
            //getSu().getCookAngle().setAngle(a.cpy().sub(getSu().getPosition().cpy()).angle() - MathUtils.random(-60, 60));
            getSu().getAcceleration().setAngle(a.cpy().sub(getSu().getPosition().cpy()).angle() - MathUtils.random(-15, 15));
            getSu().getAcceleration().nor().scl(5000);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void makeDecisionWalking() { // обход припятсвий
        if (checkingForObstacles(getSu().getVelocity(), 400) > 0) {
            int r = MathUtils.random(35, 90);
            if (obstacle == 0) {
                obstacle = MathUtils.random(1, 2);
            }
            int checker = 400;

            if (obstacle == 1) r *= -1;
            while (checkingForObstacles(getSu().getAcceleration(), checker) > -1) {
                checker--;
                getSu().getAcceleration().rotate(r);

                if (checker < 0) return;

            }
            getSu().getVelocity().scl(.8f);
            this.actionClockCounter[0] = MathUtils.random(500, 1500);
        }
        obstacle = 0;
    }

    public void disconectWhereGo() {
        float delta = MathUtils.random(1000, 4000) * (1.3f * levelBot);
        Vector2 rn = new Vector2(MathUtils.random(-100, 100), MathUtils.random(-100, 100));
        rn.nor().scl(10000, 1000);
        //System.out.println("RM   " + rn);
        getSu().getAcceleration().set(rn).rotateRad(MathUtils.random(-3, 3));
        if (StaticService.selectWithProbability(15)) {
            getSu().getAcceleration().setZero();
            actionClockCounter[0] = MathUtils.random(200 * (2 - levelBot), 1000 * (2 - levelBot));
        }
        if (StaticService.selectWithProbability(25)) getSu().getAcceleration().scl(.03f);
    }
/////////////////////////////////////////////

    private boolean updateActionClockCounter(float dt) { // обновление делты по принятию решени
        boolean acction = false;
        for (int i = 0; i < actionClockCounter.length; i++) {
            actionClockCounter[i] = actionClockCounter[i] - dt;
            if (actionClockCounter[i] < 0) acction = true;
        }
        return acction;
    }


    private boolean respown() { // респаун после смерти
        boolean oldSteteLive = this.simulationUnit.isLive();
        this.simulationUnit.setLive(this.simulationUnit.getIndexBot().getGameServer().getSnapShots().isLive(getMyID()));
        if (this.simulationUnit.isLive() == false && oldSteteLive == true)
            this.actionClockCounter[3] = MathUtils.random(500, 2000);
        if (!simulationUnit.isLive() && this.actionClockCounter[3] <= 0) {
//            getSu().getIndexBot().getGameServer().getSnapShots().setLive(getMyID(), false);
//            getSu().getIndexBot().getGameServer().getSnapShots().addSnapShot(Integer.MIN_VALUE,0,0,getMyID());
            simulationUnit.respownBots();
            return true;
        }

        return false;
    }


    private void actionSpeed() { // обрабатывет скорость передвижения в зависимости от аццепт - NE TROGATYYYY!!!!!!!!!!!
        float x = getSu().getPosition().x;
        float y = getSu().getPosition().y;

        float time = dt * .001f;
        if (getSu().getAcceleration().len() < 5) getSu().getVelocity().scl(.7f);
        getSu().getVelocity().add(getSu().getAcceleration().cpy().limit(6000).scl(time));
        getSu().getPosition().add(getSu().getVelocity().cpy().limit(700).scl(time));
////////////////////////////
        if (!getSu().getIndexBot().getIndexMap().canMove(getSu().getPosition()))
            getSu().getPosition().set(x, y);
        int deltaCor;
        if (getSu().getVelocity().y < 0) deltaCor = (int) (420 * -dt);
        else deltaCor = (int) (420 * dt);
        if (getSu().getIndexBot().getIndexMap().canMove((int) getSu().getPosition().x, (int) getSu().getPosition().y + deltaCor)) {
            getSu().getPosition().add(0, deltaCor);
        } else {
            if (getSu().getVelocity().x < 0) deltaCor = (int) (420 * -dt);
            else deltaCor = (int) (420 * dt);
            if (getSu().getIndexBot().getIndexMap().canMove((int) getSu().getPosition().x + deltaCor, (int) getSu().getPosition().y)) {
                getSu().getPosition().add(deltaCor, 0);
            }

        }


    }

    private int getMyID() {
        return simulationUnit.getId();
    }

    private SimulationUnit getSu() {
        return simulationUnit;
    }

    private int checkingForObstacles(int x, int y, int m) { //проверка На Наличие Препятствий
        return checkingForObstacles(new Vector2(x, y), m);
    }

    private int checkingForObstacles(Vector2 direction, int m) { //проверка На Наличие Препятствий
        for (int i = 0; i <= m; i += 45) {
            if (!getSu().getIndexBot().getIndexMap().canMove(getSu().getPosition().cpy().add(direction.cpy().nor().scl(i))))
                return i;
        }
        return -1;
    }

    private Integer searchTargetAttack() { // поиск Цели Атаки
        Map<Integer, SnapShots.player> playrsLisn = getSu().getIndexBot().getGameServer().getSnapShots().getPlayrsLisn();
//        System.out.println("--------------");
//        System.out.println(playrsLisn);
        int idTaget = Integer.MAX_VALUE;
        int distanceTaget = Integer.MAX_VALUE;
        int distance = Integer.MAX_VALUE;
        int detuningDistance; // дистанция обноружения
        //System.out.println("----------------------");
        int guns = getSu().indexBot.getGameServer().getSnapShots().getFragToID(getMyID());
        if (guns == 1) detuningDistance = 500;
        else detuningDistance = 1500;

        for (Map.Entry<Integer, SnapShots.player> entry : playrsLisn.entrySet()) {
            // System.out.println(entry.getKey());
            //if (getMyID() == entry.getKey()) continue;
            if(!CommandLogic.isOpponents(getMyID(),entry.getKey())) continue; // проверка н аодну команду двух челов
            if (!entry.getValue().isLive()) continue;
            distance = StaticService.getDistance(getSu().getPosition().x, getSu().getPosition().y, entry.getValue().getX(), entry.getValue().getY());
            //System.out.println("Key: " + entry.getKey() + " Value: " + entry.getValue());


            if (distance < MathUtils.random(150, detuningDistance)) { // Расстояние определения
//                System.out.println(
//                        entry.getKey() + " :: "+StaticService.getDistance(getSu().getPosition().x, getSu().getPosition().y, entry.getValue().getX(), entry.getValue().getY())
//                );
                if (checkingForObstacles(entry.getValue().getX(), entry.getValue().getY(), distance) != -1)
                    continue;
                if (distance < distanceTaget) {
                    distanceTaget = distance;
                    idTaget = entry.getKey();
                }
            }
            ///проверяем можно ли дойти до этого перса
            //  если все ок - то присваиваем этот номер ИВ  и поехали
        }
        return idTaget;

    }
}
