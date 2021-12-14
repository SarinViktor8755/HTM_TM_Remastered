package com.SteckApi;

import com.*;
import com.mygdx.game.Service.TimeService;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StockBase {
    private StockRequestService inMess;
    private StockRequestService outMess;
    public HashMap<Integer, Integer> incomingMessageHeader;
    private GameServer gameServer;

    public StockBase(GameServer gameServer) {
        this.gameServer = gameServer;
        this.inMess = new StockRequestService(gameServer, new HashMap());
        this.outMess = new StockRequestService(gameServer, new ConcurrentHashMap());
        this.incomingMessageHeader = new HashMap<>();
        //new Thread(new ProcessingOutputQueue(outMess)); // запуск обрабочик асобытий - почтальена на клиенты
    }

    public StockRequestService getInMess() {
        return inMess;
    }

    public void setInMess(StockRequestService inMess) {
        this.inMess = inMess;
    }

    public StockRequestService getOutMess() {
        return outMess;
    }

    public void setOutMess(StockRequestService outMess) {
        this.outMess = outMess;
    }

    public void addOutSteckOut(Network.StockMess requestStock, int nomerPlayer) {
        //  System.out.println("OUT ::  " + outMess);
        //System.out.println("IM ::  " + inMess);
        ArrayList<Integer> plroom = gameServer.getListPlayerNotId(nomerPlayer, 0);
        for (int i = 0; i < plroom.size(); i++) {
            requestStock.nomer_pley = nomerPlayer;
            requestStock.time_even = TimeService.getTimeGame();
            getOutMess().addStockOutQuery(new RequestStockServer(requestStock, plroom.get(i)));
        }
        try {
            cleanQueuesInAndOut();
        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
        }
        cleanQueuesInAndOut();
    }


    public boolean addInSteckIn(Network.StockMess requestStock, int customerNumber) { // добавление в журнал входящих сообщения - для хранения архивов - если такое сообщение уже есть в очереди
        if (inMess.isExists(requestStock.time_even)) {
            return false;
        }
        RequestStockServer inQuerrry = new RequestStockServer(requestStock);
        inQuerrry.nomer_pley = customerNumber;
        inMess.addStockInQuery(new RequestStockServer(requestStock));
        return true;
    }

    public void cleanInQuerry() {  //очистить очреррдь входждения
        try {
            if (inMess.getSize() < gameServer.getSnapShots().getMuchSnap() * 1) return;

            Iterator<Map.Entry<Integer, RequestStockServer>> entries = this.inMess.getInterator();
            while (entries.hasNext()) {
                Map.Entry<Integer, RequestStockServer> entrie = entries.next();
                if (com.StaticService.getTimeGame() - entrie.getValue().eventTime > 8000)
                    entries.remove();
            }
        } catch (ConcurrentModificationException e) {
            System.out.println("ConcurrentModificationException cleanInQuerry");
            e.printStackTrace();
        }

    }

    public void cleanOutQuerry() {     //очистить очреррдь Выхода

        try {
            //if (getOutMess().getSize() < gameServer.getSnapShots().getMuchSnap() * 50) return;
            if (outMess.getSize() < 350) return;
            int time = com.StaticService.getTimeGame();
            Iterator<Map.Entry<Integer, Integer>> entries = gameServer.getSnapShots().getStockBase().incomingMessageHeader.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<Integer, Integer> entry = entries.next();
                if (time - Math.abs(gameServer.getSnapShots().getStockBase().incomingMessageHeader.get(entry.getKey())) > 8000) {
                    int k = entry.getKey();
                    getOutMess().removeObjInSteck(k);
                    //entries = gameServer.getSnapShots().getStockBase().incomingMessageHeader.entrySet().iterator();
                    entries.remove();
                    incomingMessageHeader.remove(k);
                    //entries = gameServer.getSnapShots().getStockBase().incomingMessageHeader.entrySet().iterator();
                }
            }
        } catch (ConcurrentModificationException e) {
            System.out.println("ConcurrentModificationException cleanOutQuerry");
            e.printStackTrace();
        }
    }

    public void sendOutMessege() { // разослать сообщения из очереди
        Iterator<Map.Entry<Integer, RequestStockServer>> entries = this.outMess.getInterator();
        while (entries.hasNext()) {
            Map.Entry<Integer, RequestStockServer> entrie = entries.next();
            if (!entrie.getValue().workOff) {
                //System.out.println(" Otpravil clientu  " + entrie.getValue());
                gameServer.server.sendToUDP(entrie.getValue().fromPleayer, entrie.getValue().getStockMess());
            }
        }
    }


    public void messagePlayerDestruction(int nomP) {    // --- сообщение уничтичтожение игрока палкой
        ArrayList<Integer> plroom = gameServer.getListPlayer();
        Network.StockMess stockMess = new Network.StockMess();
        stockMess.time_even = TimeService.getTimeGame() * (-1);
        stockMess.tip = -2;
        stockMess.nomer_pley = nomP;
        for (int i = 0; i < plroom.size(); i++) {
            getOutMess().addStockOutQuery(new RequestStockServer(stockMess, plroom.get(i)));
        }
        this.messageRatingKillTime(nomP);
    }

    public void messagePlayerDestruction(int nomP,int angel,int weapon) {    // --- сообщение уничтичтожение игрока - ОГНЕСТРЕЛОМ
        ArrayList<Integer> plroom = gameServer.getListPlayer();
        Network.StockMess stockMess = new Network.StockMess();
        stockMess.time_even = TimeService.getTimeGame() * (-1);
        stockMess.tip = -2;
        stockMess.p1 = angel;
        stockMess.p2 = weapon;
        stockMess.nomer_pley = nomP;
        for (int i = 0; i < plroom.size(); i++) {
            getOutMess().addStockOutQuery(new RequestStockServer(stockMess, plroom.get(i)));
        }
        this.messageRatingKillTime(nomP);
    }

    public void messageRatingKillTime(int nomP) { // пакет : Рейтинг, Убийства, Время
        ArrayList<Integer> plroom = gameServer.getListPlayer();
        for (int i = 0; i < plroom.size(); i++) {
            try {
                if (plroom.get(i) < 0) continue;
                Network.StockMess stockMess = new Network.StockMess();
                stockMess.tip = -9;
                stockMess.nomer_pley = plroom.get(i);
                stockMess.time_even = TimeService.getTimeGame() * (-1);
                stockMess.p1 = (int) gameServer.indexMatch.getCurrentTimerMatch();// ВРЕМЯ МАТЧАЯ все ОК
                stockMess.p2 = gameServer.indexMatch.getFragFromPlayer(plroom.get(i)); // ФРАГИ

                stockMess.p3 = gameServer.getCommandLogic().getTeamPoints1();// МЕСТО в РЕЙТЕНГЕ
                stockMess.p4 = gameServer.getCommandLogic().getTeamPoints2();


                getOutMess().addStockOutQuery(new RequestStockServer(stockMess, plroom.get(i)));
            }catch (NullPointerException e){
                System.out.println("NullPointerException  :: messageRatingKillTime");
                continue;
            }catch (ConcurrentModificationException e){
                System.out.println("ConcurrentModificationException  :: messageRatingKillTime");
            }


        }
    }

    /////////////////////////
    public void packageTransferWeaponsForPlayer(int forPlayer) { // сообщает о смене оржия - одному обо всех
        try {
            ArrayList<Integer> plroom = gameServer.getListPlayer();
            Network.StockMess stockMess = new Network.StockMess();
            stockMess.nomer_pley = forPlayer;
            stockMess.tip = 78;
            stockMess.time_even = TimeService.getTimeGame();
            for (int plNom : plroom) {
                stockMess.p1 = plNom;
                stockMess.p2 = gameServer.getSnapShots().getPlaeyrToId(plNom).getWeapon();
                getOutMess().addStockOutQuery(new RequestStockServer(stockMess, forPlayer));
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }

    }

    public void packageTransferWeaponsForAllAboutBottom(int aboutPlayer) { // сообщает о смене оржия -  всем об одном
        ArrayList<Integer> plroom = gameServer.getListPlayer();
        Network.StockMess stockMess = new Network.StockMess();
        stockMess.tip = 78;

        stockMess.time_even = TimeService.getTimeGame();
        stockMess.p1 = aboutPlayer;
        stockMess.p2 = gameServer.getSnapShots().getPlaeyrToId(aboutPlayer).getWeapon();
        //System.out.println("--------------------------");
//        System.out.println(stockMess.p1);
//        System.out.println(stockMess.p2);
        for (int plNom : plroom) {
            //System.out.println("!!!!!!!!!!!!!!!!!!!!!!@@@");
            stockMess.nomer_pley = plNom;
            getOutMess().addStockOutQuery(new RequestStockServer(stockMess, plNom));
        }


    }
    /////////////////////////

    public void revivePlayersMessage(int nomP) { // сообщение - игрок возраждается
        ArrayList<Integer> plroom = gameServer.getListPlayer();
        Network.StockMess stockMess = new Network.StockMess();
        stockMess.time_even = TimeService.getTimeGame();
        stockMess.tip = -666;
        for (int i = 0; i < plroom.size(); i++) {
            getOutMess().addStockOutQuery(new RequestStockServer(stockMess, plroom.get(i)));
        }
    }

//    public void changingPlayerWeapon(int nomP, int nomerWeapon) { // смена оружия игрока
//        ArrayList<Integer> plroom = gameServer.getListPlayer();
//        Network.StockMess stockMess = new Network.StockMess();
//        stockMess.time_even = TimeService.getTimeGame();
//        stockMess.tip = 78;
//        stockMess.p1 = nomerWeapon;
//        for (int i = 0; i < plroom.size(); i++) {
//            getOutMess().addStockOutQuery(new RequestStockServer(stockMess, plroom.get(i)));
//        }
//    }

    private void cleanQueuesInAndOut() {
        //System.out.println("in: " + this.getInMess().getSize() + " OUT::" + +this.getOutMess().getSize());
        if (outMess.getSize() + inMess.getSize() > 750) {
            cleanOutQuerry();// Чистка исходящих сообщений
            cleanInQuerry();  // Чистка входящих сообщений
        }
        //System.out.println("in: " + this.getInMess().getSize() + " OUT::" + +this.getOutMess().getSize());
    }


    public void cleanerQueuesAll() {
        this.getOutMess().getStockRequestArr().clear();
        this.getInMess().getStockRequestArr().clear();
    }

//    public void addMessStartMath() { // сообщяем что матч начинается
//        ArrayList<Integer> plroom = gameServer.getListPlayer(0);
//        Network.StockMess stockMess = new Network.StockMess();
//        stockMess.time_even = TimeService.getTimeGame();
//        stockMess.tip = 500;
//        for (int i = 0; i < plroom.size(); i++) {
//            getOutMess().addStockOutQuery(new RequestStockServer(stockMess, plroom.get(i)));
//        }
//    }

//    public void addMessEndMath() { // сообщяем что матч -конец
//        ArrayList<Integer> plroom = gameServer.getListPlayer(0);
//        Network.StockMess stockMess = new Network.StockMess();
//        stockMess.time_even = TimeService.getTimeGame();
//        stockMess.tip = 600;
//        stockMess.p1 = (int)gameServer.indexMatch.getMAX_TIMER_PAUSE();
//        for (int i = 0; i < plroom.size(); i++) {
//            getOutMess().addStockOutQuery(new RequestStockServer(stockMess, plroom.get(i)));
//        }
//    }


}
