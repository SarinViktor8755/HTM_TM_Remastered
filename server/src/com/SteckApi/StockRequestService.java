package com.SteckApi;

import com.GameServer;
import com.mygdx.game.ClientNetWork.Network;

import java.util.Iterator;
import java.util.Map;

public class StockRequestService {
    public GameServer gameServer;
    private Map<Integer, RequestStockServer> stockRequestArr;
    volatile private int endToTndQueueNumberOut;
    //volatile private int endToTndQueueNumberOIn;


    public StockRequestService(GameServer gameServer, Map quar) {
        this.gameServer = gameServer;
        this.stockRequestArr = quar;
        this.endToTndQueueNumberOut = 0;
    }

    public synchronized void  addStockInQuery(RequestStockServer rs) { // добавляет элемент в сток входящих сообщений
        this.stockRequestArr.put(rs.eventTime, rs);
    }

    public synchronized void addStockOutQuery(RequestStockServer rs) { // добавляет элемент в сток исходящих сообщений --- НАДО РЕАКТИРОВАТЬ
        endToTndQueueNumberOut++;
        //System.out.println(endToTndQueueNumberOut);
        if(endToTndQueueNumberOut > 600000000) endToTndQueueNumberOut = 0;
       // System.out.println(rs.eventTime);
        int time = rs.eventTime;
        rs.eventTime = endToTndQueueNumberOut;
        this.stockRequestArr.put(endToTndQueueNumberOut, rs);
        gameServer.getSnapShots().getStockBase().incomingMessageHeader.put(endToTndQueueNumberOut,time);
        //System.out.println(gameServer.getSnapShots().getStockBase().incomingMessageHeader);
       // System.out.println(gameServer.getSnapShots().getStockBase());
    }


    public boolean isExists(int nom) { // существует или нет такой номер
        if (this.stockRequestArr.get(nom) != null) return true;
        return false;
    }


    public int getSize() {
        return stockRequestArr.size();
    }

    @Override
    public String toString() {
        return "StockRequestService{" +
                "gameServer=" + gameServer +
                ", stockRequestArr=" + stockRequestArr +
                '}';
    }

    public void markCompleted(int nomberInQuerry) {  // переключить как выполненный - или принят овет от сервера ИЛИ отработан в алгоритме на стоки входждения
        this.stockRequestArr.get(nomberInQuerry).workOff = true;
    }

    public boolean isCompleted(int nomberInQuerry) {     // проверяет ::  выполненный - или принят овет от сервера ИЛИ отработан в алгоритме на стоки входждения
        return this.stockRequestArr.get(nomberInQuerry).workOff;
    }

    public Map<Integer, RequestStockServer> getStockRequestArr() {
        return stockRequestArr;
    }

    public RequestStockServer getToNomber(int nom) { // взять по номеру запрос
        return this.stockRequestArr.get(nom);
    }

    public void sendMyMessToClient() { // Отправить очередь по всем клиентам
        for (Map.Entry<Integer, RequestStockServer> entry : this.stockRequestArr.entrySet()) {
            if (entry.getValue().workOff == true) return;
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
            gameServer.getServer().sendToUDP(entry.getValue().fromPleayer, stockMess);
        }
    }

    public Iterator<Map.Entry<Integer, RequestStockServer>> getInterator() {
        return this.stockRequestArr.entrySet().iterator();
    }


    public void removeObjInSteck(Integer nom) { // удалить обьект по номеру обекта по ключу
        this.stockRequestArr.remove(nom);
    }

    ////////////////////////////////////
    public void processingOfIncomingMessages() { // обработка сообщений входящего Стека - раскидывает их по клиентам
        for (Map.Entry<Integer, RequestStockServer> entry : stockRequestArr.entrySet()) {
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
            stockMess.nomer_pley = entry.getValue().nomer_pley;
            gameServer.getServer().sendToUDP(entry.getValue().fromPleayer, stockMess);
        }
    }

    public void markAsCompleted(int idNomerZadaniya) throws NullPointerException { // отметить задание как сделанное
        this.stockRequestArr.remove(this.stockRequestArr.get(idNomerZadaniya));
    }

    public void cleanQueueOut() { // почистить очередь исходящих
//        if (stockRequestArr.hry() < 1000) return;
//        Iterator<Map.Entry<Integer, RequestStockServer>> entries = this.inMess.getInterator();
//        while (entries.hasNext()) {
//            Map.Entry<Integer, RequestStockServer> entrie = entries.next();
//            if (TimeService.getTimeGame() - entrie.getValue().eventTime > 8000)
//                entries.remove();
//        }

    }

//////////////////////////////////////


//////////////////////
//    public synchronized void sendMessageFromOutSteck(){ // рассылка соообщений по клиентам
//        for (Map.Entry<Integer, RequestStockServer> entry : this.stockRequestArr.entrySet()) {
//            //System.out.println(   this.getSize() );
//            if (!entry.getValue().workOff) {
//                System.out.println("не обработан ответ");
//            }
//            System.out.println("СООБЩЕНИЕ");
//        }
//
//    }


}
