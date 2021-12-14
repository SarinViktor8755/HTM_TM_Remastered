package com.Bot;

import com.Network;
import com.SnapShots;
import com.StaticService;
import com.badlogic.gdx.math.Vector2;
import com.logicMatch.CommandLogic;


import java.util.Map;

public class SimulationUnit {

    LogicBot logicBot;

    private Vector2 cookAngle; // навправление тела
    private Vector2 acceleration; // навправление движения
    private Vector2 position; // позиция
    private Vector2 velocity; // ускарение

    Vector2 goTo_run;

    private boolean live;

    IndexBot indexBot;      //
    private int id;

    private long deltaTime; // время для определение Дельты () - разница между временем
    private long timeFix;   // это последнее время которое отмечает предыдущую точку

    public SimulationUnit(IndexBot indexBot, int id, long tackt) {
        this.indexBot = indexBot;
        this.id = id;

        ///////////////////////////
        this.position = new Vector2(getIndexBot().getIndexMap().getFreeSpace());
        this.velocity = new Vector2(0, 0);

        this.acceleration = new Vector2(500, 500);
        this.cookAngle = new Vector2(0, 0);


        ///////////////////////////
        this.connectoToServer(id);
        this.sendCoordinates();
        /////////////////////////
        this.live = true;
        this.logicBot = new LogicBot(this);

        this.timeFix = System.currentTimeMillis();

    }

    public long getDeltaTime() {
        long rt = System.currentTimeMillis();
        this.deltaTime = rt - this.timeFix;
        this.timeFix = rt;
        // System.out.println(deltaTime + "!!!");
        return this.deltaTime;
    }

    public void sendCoordinates() {  // сообщить кодинаты на сервер
        int x = (int) roundOneРundred(position.x);
        int y = (int) roundOneРundred(position.y);
        indexBot.getGameServer().snapShots.updateCoordinatP(id, x, y, Math.round(getCookAngle().angle()));          // обновленеи координат
        indexBot.getGameServer().snapShots.addSnapShot(x, y, Math.round(getCookAngle().angle()), id);              // добавленеи Спаншота
        indexBot.getGameServer().snapShots.sendCoordinatesAll(id);             // отдал всем координаты
        indexBot.getGameServer().responseRequests.getStockBase().sendOutMessege(); // рассылка сообщений

    }

    public void connectoToServer(int id) {
        indexBot.getGameServer().snapShots.addPlayer(id);
        this.sendCoordinates();
    }

    public void disconect(int id) {
        indexBot.getUnits().remove(id);
        indexBot.getGameServer().getSnapShots().removePlayer(id);
        getIndexBot().getGameServer().getSnapShots().removePlayer(getId());
        Network.rc r = new Network.rc();
        r.id = getId();
        getIndexBot().getGameServer().getServer().sendToAllUDP(r);
        getIndexBot().getGameServer().getSnapShots().delatePlayer(getId());
    }
    ////////////////

    public void makeHit() {  //произвести удар
        if (getLogicBot().getActionClockCounter()[1] > 0) return;
        getLogicBot().getActionClockCounter()[1] = 500;
        Network.StockMess in = new Network.StockMess();

        in.time_even = StaticService.getTimeGame();
        in.tip = indexBot.getGameServer().getSnapShots().getWeaponByPlayersID(id);


        Vector2 point = position.cpy().add(cookAngle.cpy().nor().scl(70));
        in.p1 = (int) point.x;       // поравить точку нападения
        in.p2 = (int) point.y;
        indexBot.getGameServer().getSnapShots().getStockBase().addOutSteckOut(in, this.id); // записывает в стек ИН - если такое сообщение уже было - то просто хранться в ИН если нет, наоборот записывает
        int gans = indexBot.getGameServer().getSnapShots().getWeaponByPlayersID(id);

        if (gans == 1) {
            indexBot.getGameServer().calculationСontact.getHit(in.p1, in.p2, this.id, 0, 110);//подсчет попадания удара
            return;
        }

        Vector2 temp_vector = new Vector2(cookAngle).nor();
        //System.out.println("!!!!!!!          !!!!!!!!!!  " + gans);
        if (gans == 2) {
            int x, y;

            for (int step = 1; step <= 1200; step += 50) {
                x = (int) (position.x + temp_vector.x * step);
                y = (int) (position.y + temp_vector.y * step);
                if ((step >= 1) & !indexBot.getIndexMap().canMove(x, y))
                    return;  // проверяет столкновения -надо поравить
                if (indexBot.getGameServer().calculationСontact.getHit(x, y, id, 0, 50, (int) cookAngle.angle(), 2) != Integer.MIN_VALUE)
                    return;
            }
        }

        if (gans == 3) {
            int x, y;
            for (int step = 1; step <= 900; step += 55) {
                x = (int) (position.x + temp_vector.x * step);
                y = (int) (position.y + temp_vector.y * step);
                if ((step >= 1) & !indexBot.getIndexMap().canMove(x, y))
                    return; // проверяет столкновения - надо поравить
                indexBot.getGameServer().calculationСontact.getHit(x, y, id, 0, 60 + step / 5, (int) cookAngle.angle(), 3);
            }
        }
    }
    ////////////////////

    public float roundOneРundred(double n) { // округлить до 1000
        n = n * 1000;
        int result = (int) Math.round(n);
        return (float) result / 100;
    }

    public float roundOneРundred(float n) { // округлить до 1000
        n = n * 1000;
        int result = (int) Math.round(n);
        return (float) result / 1000;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public void respownBots() {
        if (this.live) return;
        this.sendCoordinates();
        boolean truePosition = true;
        int x, y;
        while (truePosition) {
            truePosition = false;
            Vector2 temp = getIndexBot().getIndexMap().getFreeSpace(CommandLogic.determineTeamPlayer(getId()) == 1);
            x = (int) temp.x;
            y = (int) temp.y;
            Map<Integer, SnapShots.player> playrsLisn = getIndexBot().getGameServer().getSnapShots().getPlayrsLisn();
//            int cheker = 400;
//            for (Map.Entry<Integer, SnapShots.player> entry : playrsLisn.entrySet()) {
//                float dist = OperationVector.getDistance(x, y, entry.getValue().getX(), entry.getValue().getY());
//                if (dist < cheker) {
//                    truePosition = true;
//                    break;
//                }
//                cheker--;
//                if (cheker < 0) disconect(this.getId());
//            }
            getPosition().set(x, y);
        }
        this.sendCoordinates();
        this.velocity.setZero();
        this.acceleration.setZero();
        this.getLogicBot().getActionClockCounter()[0] = 100;
        Network.StockMess in = new Network.StockMess();
        in.time_even = StaticService.getTimeGame();
        in.nomer_pley = this.id;
        in.tip = -666;
        indexBot.getGameServer().getSnapShots().getStockBase().addOutSteckOut(in, this.id);
        this.setLive(true);
        indexBot.getGameServer().getSnapShots().setLive(getId(), true);// мертвый -> живой
        indexBot.getGameServer().getSnapShots().setWeaponByPlayersID(id, 1);
    }

    public LogicBot getLogicBot() {
        return logicBot;
    }

    public Vector2 getCookAngle() {
        return cookAngle;
    }

    public Vector2 getAcceleration() {
        return acceleration;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public Vector2 getGoTo_run() {
        return goTo_run;
    }

    public boolean isLive() {
        return live;
    }

    public IndexBot getIndexBot() {
        return indexBot;
    }

    public int getId() {
        return id;
    }
}
