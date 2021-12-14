package com;

import com.Network.PleyerPositionNom;
import com.SteckApi.StockBase;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;

/**
 * Created by 1 on 25.09.2019.
 */

public class SnapShots {
    private GameServer gs;
    private HashMap<Integer, player> playrsLisn; //интегер -номер плеера , плеер и есть плеер;
    private StockBase stockBase; /// очереди заданий

    //private boolean live;

    public SnapShots(GameServer gs) {
        this.gs = gs;
        this.playrsLisn = new HashMap<>();
        this.stockBase = new StockBase(gs);

        // live = true;
    }

    public void cleaningSnapSgots() {
        this.stockBase.cleanOutQuerry();
        this.stockBase.cleanInQuerry();
        this.playrsLisn.clear();

    }

    ////////////////////
    public boolean isLive(int id) {
        try {
            if(!gs.getIndexMatch().isMatchRun()) return false;
            return this.getPlaeyrToId(id).isLive();
        } catch (NullPointerException e) {
            this.getPlayrsLisn().put(id, new player());
//            System.out.println(id);
//            System.out.println("isLive :: NullPointerException");
//            e.printStackTrace();
            return false;
        }
    }

    public void setLive(int id, boolean live) {
        try {
            this.getPlaeyrToId(id).setLive(live);
        } catch (NullPointerException e) {
            e.printStackTrace();
            //setLive(id, live);
        }

    }
///////////////

    public HashMap<Integer, player> getPlayrsLisn() {
        return playrsLisn;
    }

    public StockBase getStockBase() {
        return stockBase;
    }


    public void delatePlayer(Integer idP) {
        this.playrsLisn.remove(idP);
    }

    public static int predictCoordinate(long t1, int c1, long t2, int c2, long newT) { // строит прямую и после этого стороит 3 точку
        try {
            return (int) (((c2 - c1) / (t2 - t1)) * (newT - t1) + c1);
        } catch (ArithmeticException e) {
            e.printStackTrace();
            int resilt = c1;
            return resilt;
        }
    }

//    public int getAndUpdateRealTime(){
//        Long res = System.currentTimeMillis() - (604800000 * (System.currentTimeMillis() / 604800000));
//        return res.intValue();
//    }


    public void addPlayer(int id) {
        this.playrsLisn.put(id, new player());
        //System.out.println("connect" + this.getPlayrsLisn());
    }

    public void removePlayer(int id) {
        this.playrsLisn.remove(id);
    }

    public void updatePlayer(int id, int x, int y, int rot) {
        this.playrsLisn.get(id).updateCoordinatPleyer(x, y, rot);
    }

    public player getPlaeyrToId(int id) {
        return this.playrsLisn.get(id);
    }

    public Object[] getAllKey() {
        return this.playrsLisn.keySet().toArray();
    }

    public Vector2 getPlayersPositionMoment(long time, int nPlayer) { // поиск снап шота согласно времени
        int x = this.getPlaeyrToId(nPlayer).x;
        int y = this.getPlaeyrToId(nPlayer).y;
        // if(time - StaticService.getTimeGame() < 200) return new Vector2(x,y);
        ArrayList<SnapShot> snapShots = this.getPlaeyrToId(nPlayer).getListSShot();
        //System.out.println(snapShots);
        try {
            for (int i = 0; i < snapShots.size(); i++) {
                if (snapShots.get(i).getTime() - time < 0) {
                    x = snapShots.get(i - 1).x;
                    y = snapShots.get(i - 1).y;
                    //System.out.println("time : " + snapShots.get(i));
                    //break;
                    return new Vector2(x, y);
                }
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Vector2(x, y);
        }

//        System.out.println("x : " + x + "y : " + y +"time even : " + time);
        return new Vector2(x, y);

    }

    public void clenSnapShotsToPleyer(int id) { // очищает снап шот для игрока - нужен после смерти игрока
        getPlaeyrToId(id).getListSShot().clear();
    }

    public void sendCoordinatesAll(int id) { //  Сообщить координаты других играков

        try {
            for (int key : playrsLisn.keySet()) {
                if (!getPlaeyrToId(key).isLive()) continue;// проверка на жизнь
                float distant = StaticService.getDistance(this.getPlaeyrToId(id).getX(), this.getPlaeyrToId(id).getY(), this.getPlaeyrToId(key).getX(), this.getPlaeyrToId(key).getY());
                if (!decisionDistance(distant)) continue;
                if (id == key) continue; // Самому себе отправлять только 4% коорденат
                if (isRoomOne(id, key)) // проверка на одну комнату или нет ))
                    //        System.out.println(key);
                    gs.server.sendToUDP(id, packeCcoordinatesPlayer(key));
            }
        } catch (ConcurrentModificationException e) {
           // e.printStackTrace();
        }
    }

    public boolean decisionDistance(float dist) {
        if (dist > 1300 && dist <= 2500) return StaticService.selectWithProbability(15);
        if (dist > 2500) return StaticService.selectWithProbability(2);
        return true;
    }

    /////////// оружие
    public int getWeaponByPlayersID(int id) {
        try {
            return getPlaeyrToId(id).weapon;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void setWeaponByPlayersID(int id, int weapon) {
        try {
            getPlaeyrToId(id).weapon = weapon;
        } catch (NullPointerException e) {
            e.printStackTrace();

        }
    }

    ///////////количество фрагов за жизнь
    public int getТumberEnemiesLifeFromID(int id) {
        try {
            return getPlaeyrToId(id).getNumberEnemiesLife();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return 0;
        }
    }



    public void setТumberEnemiesLifeFromID(int idPlayer, int frag) {
        try {
            getPlaeyrToId(idPlayer).setNumberEnemiesLife(frag);
        } catch (NullPointerException e) {e.printStackTrace();}

    }

    ///////////
    public boolean isRoomOne(int idPlayerOne, int idPlayerTwo) {
        try {
            if (getPlaeyrToId(idPlayerOne).getRoom() == getPlaeyrToId(idPlayerTwo).getRoom())
                return true;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public void addSnapShot(int x, int y, int r, int id) { // добавление плееру снапшота
        try {
            getPlaeyrToId(id).addSShot(new SnapShot(x, y, r));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        //System.out.println(this.playrsLisn);
    }

    public int getMuchSnap() { // взять количество играков
        return getPlayrsLisn().size();
    }

    public void updateCoordinatP(int id, int x, int y, int r) {
        try {
            getPlaeyrToId(id).setX(x);
            getPlaeyrToId(id).setY(y);
            getPlaeyrToId(id).setRot(r);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    public int getFragToID(int idP) {
        return getPlaeyrToId(idP).getFrag();
    }

    public void setFragToID(int idP, int frag) {
        getPlaeyrToId(idP).setFrag(frag);
    }

    public void clearSnapShots() {
        this.playrsLisn.clear();

    }

    public void zirosFrags() {
        try {
            for (int key : playrsLisn.keySet()) {
                getPlaeyrToId(key).zeroFrag();
            }
        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
           // zirosFrags();
        }
    }

    public void zirosWeapons() { // надо дорабоать
        try {
            for (int key : playrsLisn.keySet()) {
                getPlaeyrToId(key).setWeapon(1);
            }
        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
            //zirosWeapons();
        }
    }

    public boolean numberLivePlayers() { // количество живых играков
        ArrayList<Integer> pl = gs.getListP(0);
        for (int i = 0; i < pl.size(); i++) {
            if (pl.get(i) > 0) return true;
        }
        return false;
    }

    public int getSizeLivePlayers() { // количество живых играков
        ArrayList<Integer> pl = gs.getListP(0);
        int nom = 0;
        for (int i = 0; i < pl.size(); i++) {
            nom ++;
        }
        return nom;
    }

    public PleyerPositionNom packeCcoordinatesPlayer(int id) { // формирование пакета для отправки координат
        //System.out.println(getPlayrsLisn());
        float snap_x1, snap_y1, snap_x2, snap_y2;
        long t1, t2;
        PleyerPositionNom pack = new PleyerPositionNom();
        pack.nom = id;
        pack.xp = getPlaeyrToId(id).x;
        pack.yp = getPlaeyrToId(id).y;
        pack.rot = getPlaeyrToId(id).rot;
        return pack;
    }


    ///////////////////////
    public class player {
        private int x, y, rot;
        private int room;
        private ArrayList<SnapShot> listSShot;
        private boolean live = true;
        private int frag = 0;

        private int weapon = 1; /// оружие в руках играках
        private int numberEnemiesLife = 0; /// количество фрагов за жизнь


        public player(int x, int y, int rot, int room) {
            this.live = true;
            this.x = x;
            this.y = y;
            this.rot = rot;
            this.room = room;
            listSShot = new ArrayList<>();
            this.frag = 0;
            this.live = true;
        }

        public player() {
            this.x = 0;
            this.y = 0;
            this.rot = 0;
            this.room = 0;
            listSShot = new ArrayList<>();
            this.live = true;
            this.frag = 0;
            this.weapon = 1;
            this.numberEnemiesLife = 0;
        }

        public int getX() {
            return x;
        }

        public int getFrag() {
            return this.frag;
        }

        public void setFrag(int frag) {
            this.frag = frag;
        }

        public player setX(int x) {

            this.x = x;
            return this;
        }

        public int getY() {
            return y;
        }

        public player setY(int y) {
            this.y = y;
            return this;
        }

        public int getRot() {
            return rot;
        }

        public void zeroFrag() {
            this.frag = 0;
        }

        public player setRot(int rot) {
            this.rot = rot;
            return this;
        }

        public void updateCoordinatPleyer(int x, int y, int rot) {
            setRot(rot);
            setX(x);
            setY(y);
        }

        public void addSShot(SnapShot ss) {
//            System.out.println(ss);
            this.listSShot.add(0, ss);
            this.clearShot();
            //System.out.println(listSShot.size());
        }


        private void clearShot() {
            while (listSShot.size() > 450) {
                listSShot.remove(listSShot.size() - 1);
            }
        }

        public boolean isLive() {
            return live;
        }

        public void setLive(boolean live) {
            this.live = live;
            this.setWeapon(1);
            this.setNumberEnemiesLife(0);
        }

        public ArrayList<SnapShot> getListSShot() {
            return listSShot;
        }

        public int getRoom() {
            return room;
        }

        public int getWeapon() {
            return weapon;
        }

        public void setWeapon(int weapon) {
            this.weapon = weapon;
        }

        public int getNumberEnemiesLife() {
            return numberEnemiesLife;
        }

        public void setNumberEnemiesLife(int numberEnemiesLife) {
            this.numberEnemiesLife = numberEnemiesLife;
        }


    }

    ///////////////////////
    class SnapShot {
        private int x, y, r;
        private long time;

        public SnapShot(int x, int y, int r) {
            this.time = System.currentTimeMillis();
            this.x = x;
            this.y = y;
            this.r = r;
        }


        public long getTime() {
            return time;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getR() {
            return r;
        }


    }

    ///////////////////////

}
