package com.mygdx.game.ClientNetWork.SteckApi;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.ClientNetWork.Network;
import com.mygdx.game.MainGaming;
import com.mygdx.game.Service.OperationVector;
import com.mygdx.game.Service.RaitingService;
import com.mygdx.game.Service.StaticService;
import com.mygdx.game.Service.TimeService;


import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;

import java.util.Map;

public class StockRequestService {
    public MainGaming mg;


    private HashMap<Integer, RequestStock> stockRequestArr;

    public StockRequestService(MainGaming mg) {
        this.mg = mg;
        this.stockRequestArr = new HashMap();
    }

    private void clear_log() { // удалить все лишнее их очереди
        if (this.stockRequestArr.size() < 1000) return;
        Iterator<Map.Entry<Integer, RequestStock>> entries = this.stockRequestArr.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<Integer, RequestStock> entry = entries.next();
            if (TimeService.getTimeGame() - entry.getValue().eventTime > 6000)
                entries.remove();
        }
    }

    public void addStockInQuery(RequestStock rs) { // добавляет элемент
        this.stockRequestArr.put(rs.eventTime, rs);
        clear_log();
    }

    public boolean isExists(int nom) { // существует или нет такой номер
        if (this.stockRequestArr.get(nom) != null) return true;
        return false;
    }

    public void markCompleted(int nomberInQuerry) {  // переключить как выполненный - или принят овет от сервера ИЛИ отработан в алгоритме на стоки входждения
        //System.out.println(this.stockRequestArr.get(nomberInQuerry) + "!!!!!!!!!!!!++++++++++++++" + nomberInQuerry);
        try {
            this.stockRequestArr.get(nomberInQuerry).workOff = true; //StockRequestService
        } catch (NullPointerException e) {
            // System.out.println("nom" + nomberInQuerry);
        }

    }

    /// БАГ после которого зацикливаются сообщения в сети
    public boolean isCompleted(int nomberInQuerry) {     // проверяет ::  выполненный - или принят овет от сервера ИЛИ отработан в алгоритме на стоки входждения
        try {
            return this.stockRequestArr.get(nomberInQuerry).workOff;  // java.lang.NullPointerException  java.lang.NullPointerException  java.lang.NullPointerException  java.lang.NullPointerException
        } catch (NullPointerException e) {
            // System.out.println("In sent there is no such request :: " + nomberInQuerry);
            return false;
        }
    }
/// БАГ после которого зацикливаются сообщения в сети  - при наличии большова количества играков )) чт опроисходит пока не понятно

    public RequestStock getToNomber(int nom) { // взять по номеру
        return this.stockRequestArr.get(nom);
    }

    public void clean_log() { // укоротить очерель журнала
    }

    public void sendMyMessToServer() { // отправить сообщения очереди
        for (Map.Entry<Integer, RequestStock> entry : this.stockRequestArr.entrySet()) {
            if (!entry.getValue().workOff) {
                Network.StockMess stockMess = new Network.StockMess();
                stockMess.time_even = entry.getValue().eventTime;
                stockMess.tip = entry.getValue().tip;
                stockMess.p1 = entry.getValue().p1;
                stockMess.p2 = entry.getValue().p2;
                stockMess.p3 = entry.getValue().p3;
                stockMess.p4 = entry.getValue().p4;
                stockMess.p5 = entry.getValue().p5;
                stockMess.p6 = entry.getValue().p6;
                stockMess.textM = entry.getValue().string;
                mg.getMainClient().client.sendUDP(stockMess);
                //System.out.println("in << " + mg.getMainClient().getInStock().getSize() + "  " + mg.getMainClient().getInStock());
                //System.out.println("out >> " + mg.getMainClient().getOutStock().getSize() + "  " + mg.getMainClient().getOutStock());
            }
        }
    }

    public int getSize() {
        return stockRequestArr.size();
    }

    public void removeObjInSteck(Integer nom) { // удалить обьект по номеру обекта по ключу
        this.stockRequestArr.remove(nom);
    }


    public void processingOfIncomingMessages() { // обработка сообщений входящего Стека // выполняем экшены всех входящих сообщщщений - применяем параметры
        try {
            for (Map.Entry<Integer, RequestStock> entry : this.stockRequestArr.entrySet()) {
                if (!entry.getValue().workOff) {
                    consumer(entry);
                    entry.getValue().workOff = true;
                    //System.out.println("не обработан ответ");
                    //sendMyMessToServer();
                }
            }
        } catch (ConcurrentModificationException e) {
            System.out.println("processingOfIncomingMessages");
        }
    }

    @Override
    public String toString() {
        return "StockRequestService{" +
                ", stockRequestArr=" + stockRequestArr +
                '}';
    }

    private void consumer(Map.Entry<Integer, RequestStock> entry) {
        //System.out.println("tip ^ " + entry.getValue());
        int tip = entry.getValue().tip;
        if (tip == 1) { // атака палкой
            if (checkTimeEven(5000, entry.getValue().eventTime)) return;
            if (entry.getValue().nomer_pley == mg.getMainClient().getMyIdConnect()) return;
            mg.getHero().changeWeaponsForOlayer(entry.getValue().nomer_pley, 1);
            try {
                mg.getHero().getOtherPlayers().getPlayerToID(entry.getValue().nomer_pley).getAnimatonBody().addAnimationAttackPipe(); // NullPointerException
                mg.getAudioEngine().pleySoundKickStick(mg.getHero().getOtherPlayers().getXplayToId(entry.getValue().nomer_pley), mg.getHero().getOtherPlayers().getYplayToId(entry.getValue().nomer_pley));
            } catch (NullPointerException e) {
                System.out.println("Error consumer Stick");
                e.printStackTrace();
            }


            return;
        }

        if (tip == 2) {
            if (checkTimeEven(5000, entry.getValue().eventTime)) return;
            if (entry.getValue().nomer_pley == mg.getMainClient().getMyIdConnect()) return;
            mg.getHero().changeWeaponsForOlayer(entry.getValue().nomer_pley, 2);
            try {
                mg.getHero().getOtherPlayers().getPlayerToID(entry.getValue().nomer_pley).getAnimatonBody().addAnimationAttackPistols(); // NullPointerException
                mg.getAudioEngine().pleySoundKickPistols(mg.getHero().getOtherPlayers().getXplayToId(entry.getValue().nomer_pley), mg.getHero().getOtherPlayers().getYplayToId(entry.getValue().nomer_pley));
                mg.getHero().getPoolBlood().addBulletOtherPlayerPistol(entry.getValue().nomer_pley);
            } catch (NullPointerException e) {
                System.out.println("Error^ consumer Pistols");
                e.printStackTrace();
            }
            return;
        }

        if (tip == 3) {
            if (checkTimeEven(5000, entry.getValue().eventTime)) return;
            if (entry.getValue().nomer_pley == mg.getMainClient().getMyIdConnect()) return;
            mg.getHero().changeWeaponsForOlayer(entry.getValue().nomer_pley, 3);
            try {
                mg.getHero().getOtherPlayers().getPlayerToID(entry.getValue().nomer_pley).getAnimatonBody().addAnimationAttackShotgun(); // NullPointerException
                mg.getAudioEngine().pleySoundKickShotgun(mg.getHero().getOtherPlayers().getXplayToId(entry.getValue().nomer_pley), mg.getHero().getOtherPlayers().getYplayToId(entry.getValue().nomer_pley));
///////////////////
                mg.getHero().getPoolBlood().addBulletOtherPlayerShootGun(entry.getValue().nomer_pley);
            } catch (NullPointerException e) {
                System.out.println("Error^ consumer ShotGun");
            }
            return;
        }

        if (tip == -2) { // кого то убили
            //System.out.println(entry.getValue());
            int x = mg.getHero().getOtherPlayers().getXplayToId(entry.getValue().nomer_pley);
            int y = mg.getHero().getOtherPlayers().getYplayToId(entry.getValue().nomer_pley);


            boolean red = StaticService.determineTeamPlayer(entry.getValue().nomer_pley) == 1;

            if (entry.getValue().nomer_pley == mg.getMainClient().getMyIdConnect())
                mg.getHero().setLive(false);
            else mg.writeDead(entry.getValue().nomer_pley);
            mg.getAudioEngine().pleySoundTrampFot(x, y);
            //if(tip == -3) mg.getSoundtrack().pleyVoice();
            mg.getHero().getOtherPlayers().getPlayerToID(entry.getValue().nomer_pley).setX(Integer.MIN_VALUE);
            mg.getHero().getOtherPlayers().getPlayerToID(entry.getValue().nomer_pley).setWeapons(1);

            if (entry.getValue().p2 == null) {
                mg.getHero().getPoolBlood().getDistroyAnimation(MathUtils.random(1, 8), x, y, entry.getValue().nomer_pley, red);
                return;
            }// анимация кровм и тел
            if (entry.getValue().p2 == 2) {
                mg.getHero().getPoolBlood().getDistroyAnimation(MathUtils.random(1, 8), x, y, entry.getValue().nomer_pley, 2, entry.getValue().p1, red);
                return;
            }// анимация кровм и тел
            if (entry.getValue().p2 == 3) {
                mg.getHero().getPoolBlood().getDistroyAnimation(MathUtils.random(1, 8), x, y, entry.getValue().nomer_pley, 3, entry.getValue().p1, red);
                return;
            }// анимация кровм и тел

            return;
        }
        //System.out.println(entry.getValue());
        if (tip == -666) { // сервер говорит что кто то ожил
            if (checkTimeEven(5000, entry.getValue().eventTime)) return;
            mg.getHero().getOtherPlayers().setLiveTiId(entry.getValue().nomer_pley, true);
            return;
        }

        if (tip == 500) { // Старт Матча
            System.out.println("Старт Матча!!!!!!!!!!!!!!");
            //FileLog.saveToFileNewThred();

            mg.getZk().getMainGaming();
            return;
        }

        if (tip == 78) { // смена оружие
            mg.getHero().changeWeaponsForOlayer(entry.getValue().p1, entry.getValue().p2);
            //System.out.println(entry.getValue());
            return;
        }

        if (tip == -42) { // Сервер запрашивает ваш ID
            String id = RaitingService.generateTokken();
            mg.getMainClient().getOutStock().addStockInQuery(new RequestStock(// отправить на сервер
                    mg.getMainClient().getAndUpdateRealTime(), -42,
                    null, null,
                    null, null, null, null, null, id
            ));
            return;
        }


        if (tip == -9) { // получение параметров
//            System.out.println("-----");
//            System.out.println(entry.getValue());
            if (entry.getValue().p2 == -99) {
                mg.getZk().getPauseScreen(entry.getValue().p1);
                mg.getZk().dispose();
                return;
            }

            if ((mg.getHud().getMyFrags() != entry.getValue().p2) && (entry.getValue().p2 != 0)) {
                if (entry.getValue().p3 == 1 && !mg.getHud().first) {
                    mg.getHero().getPoolBlood().startingAdFirst();
                    mg.getHud().setFirst(true);
                } else
                    mg.getHero().getPoolBlood().startingAdPlus(); // вызывает надпись + 1
                if (entry.getValue().p3 == 1) mg.getHud().setFirst(true);
                else mg.getHud().setFirst(false);

            }
            boolean revers;
            if (StaticService.determineTeamPlayer(mg.getMainClient().getMyIdConnect()) == 1)
                revers = true;
            else revers = false;
            mg.getHud().update(entry.getValue().p3, entry.getValue().p2, entry.getValue().p1, entry.getValue().p4, revers);
            mg.getSoundtrack().pleyLostPrimuschestvo();



            //if(entry.getValue().p2 != mg.getHero().myPositionTablica) mg.getHero().fragWithLife++;
            //System.out.println(entry.getValue().p2 + " --  frm " + mg.getHero().fragWithLife + " :: " + mg.getHero().myPositionTablica);
//            if (entry.getValue().p2 != mg.getHero().myPositionTablica) { // добавить фраг а жизнь  ЭТО НУЖНО вводить в игру
//                mg.getHero().fragWithLife++;
//                mg.getHero().myPositionTablica = entry.getValue().p2;
//            }
            return;
        }
    }

    public boolean checkTimeEven(int secondLong, float timeEven) {// проверить на устарелость пакета
        if (Math.abs(timeEven - TimeService.getTimeGame()) > secondLong) return false;
        return true;
    }

}
