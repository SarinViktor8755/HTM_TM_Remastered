package com;

import com.Bot.IndexBot;
import com.Network.PleyerPosition;
import com.Network.rc;
import com.RatingSystem.Index;
import com.SteckApi.CalculationСontact;
import com.SteckApi.RouteResponseRequests;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.logicMatch.CommandLogic;
import com.logicMatch.IndexMatch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import static com.Network.register;


/**
 * Created by 1 on 07.09.2019.
 */

public class GameServer extends Listener {
    public ServerLauncher serverLauncher;
    public Server server;
    public IndexMatch indexMatch;
    public IndexBot indexBot;
    public SnapShots snapShots;
    public int realTime;
    public RouteResponseRequests responseRequests;
    public CalculationСontact calculationСontact;
    public Index raitingSystem;

    private CommandLogic commandLogic;


    public CalculationСontact getCalculationСontact() {
        return calculationСontact;
    }

    public void setCalculationСontact(CalculationСontact calculationСontact) {
        this.calculationСontact = calculationСontact;
    }

    public IndexMatch getIndexMatch() {
        return indexMatch;
    }

    public String[] args;

    public Server getServer() {
        return server;
    }

    public SnapShots getSnapShots() {
        return snapShots;
    }

    public int getRealTime() {
        return realTime;
    }

    public ArrayList<Integer> getListP(int room) { //возвращет Лист играков
        ArrayList<Integer> arrayListPleyer = new ArrayList<>();
        for (int i = 0; i < server.getConnections().length; i++) {
            arrayListPleyer.add(server.getConnections()[i].getID());
        }
        return arrayListPleyer;
    }

    public ArrayList<Integer> getListPlayerNotId(int myId, int room) { //возвращет Лист играков  кроме игрока с ид ИД
        ArrayList<Integer> arrayListPleyer = new ArrayList<>();
        Integer[] array = snapShots.getPlayrsLisn().keySet().toArray(new Integer[snapShots.getPlayrsLisn().keySet().size()]);
        Collections.addAll(arrayListPleyer, array);
        for (int i = 0; i < arrayListPleyer.size(); i++) {
            if (arrayListPleyer.get(i) == myId) arrayListPleyer.remove(i);
        }
        //System.out.println("ListPleyer^^ "+arrayListPleyer);
        return arrayListPleyer;
    }

    public ArrayList<Integer> getListPlayer() { //возвращет Лист играков
        ArrayList<Integer> arrayListPleyer = new ArrayList<>();
        Integer[] array = snapShots.getPlayrsLisn().keySet().toArray(new Integer[snapShots.getPlayrsLisn().keySet().size()]);
        Collections.addAll(arrayListPleyer, array);
        return arrayListPleyer;
    }

    public CommandLogic getCommandLogic() {
        return commandLogic;
    }

    public RouteResponseRequests getResponseRequests() {
        return responseRequests;
    }

    public GameServer(String[] args, ServerLauncher serverLauncher) throws IOException {
        try {
            try {
                indexBot = new IndexBot(this, Integer.parseInt(args[0]));
            } catch (ArrayIndexOutOfBoundsException e) {
                indexBot = new IndexBot(this, MathUtils.random(5, 10));
                e.printStackTrace();
            }
            snapShots = new SnapShots(this);
            this.raitingSystem = new Index();
            server = new Server();
            responseRequests = new RouteResponseRequests(this);
            calculationСontact = new CalculationСontact(this);
            register(server);
            server.bind(Network.tcpPort, Network.udpPort);
            server.start();
            indexMatch = new IndexMatch(this);
            indexBot.start();
            indexMatch.start();
            this.args = args;
            this.serverLauncher = serverLauncher;
            this.commandLogic = new CommandLogic();

//            raitingSystem.fillRatingSystem();
//            System.out.println(raitingSystem);


            server.addListener(new Listener() {
                                   public void received(Connection c, Object object) { // ответы
                                       if (object instanceof Network.StockMess) {  // получение из стока сообщения - и отпрвка ответа
                                           Network.Answer answer = new Network.Answer();
//                                       synchronized (answer) {
                                           answer.nomber = ((Network.StockMess) object).time_even;
                                           server.sendToUDP(c.getID(), answer); // ответ
                                           //Network.StockMess a = (Network.StockMess) object;
                                           // System.out.println(a);
                                           //System.out.println(" <<<---  Ответ Клиенту на запроса. Номер клиента :: " + c.getID() + " номер на запроса на каторый ответ идет :: " + answer.nomber);
                                           //         FileLog.saveToFileNewThred("L Player:: " + getSnapShots().getPlayrsLisn().keySet().size());
                                           //   System.out.println();

//                                       }
                                           responseRequests.handleRequest(((Network.StockMess) object), c.getID());      // БОЛЬШОЙ РОУТЕР
                                       }

                                       if (!indexMatch.isMatchRun()) {
                                           indexMatch.SendPlayerToPause(c.getID());
                                           return;
                                       }

                                       if (object instanceof PleyerPosition) {
                                           int x = ((PleyerPosition) object).xp;
                                           int y = ((PleyerPosition) object).yp;
                                           int r = ((PleyerPosition) object).rot;

                                           try {
                                               snapShots.getPlaeyrToId(c.getID()).updateCoordinatPleyer(x, y, r); // обновленеи координат
                                               snapShots.addSnapShot(x, y, r, c.getID());              // добавленеи Спаншота
                                               snapShots.sendCoordinatesAll(c.getID());             // отдал всем координаты
                                               responseRequests.getStockBase().sendOutMessege(); // рассылка сообщений
                                           } catch (NullPointerException e) {
                                               e.printStackTrace();
                                           }

                                       }

                                       if (object instanceof Network.rc) {
                                           long x = ((Network.rc) object).id;
                                           ///System.out.println(x);
                                       }

                                       if (object instanceof Network.Answer) {// полученеи ответа
                                           //System.out.println("Ответ " + (((Network.Answer) object).nomber));
                                           try {
                                               snapShots.getStockBase().getOutMess().markAsCompleted(((Network.Answer) object).nomber);
                                               snapShots.getStockBase().getOutMess().getStockRequestArr().get(((Network.Answer) object).nomber).workOff = true;
                                           } catch (NullPointerException e) {
                                               //e.printStackTrace();
                                           }
                                       }
                                   }

                                   public void disconnected(Connection c) { // отключение игкро
                                       snapShots.removePlayer(c.getID());
                                       rc r = new rc();
                                       r.id = c.getID();
                                       server.sendToAllUDP(r);
                                       snapShots.delatePlayer(c.getID());
                                       // FileLog.saveToFileNewThred("disconnected  " + c.getID());

                                   }

                                   public void connected(Connection c) {// подключение
                                       snapShots.addPlayer(c.getID());
                                       raitingSystem.addNewPersone(c.getID());


                                       snapShots.getStockBase().packageTransferWeaponsForPlayer(c.getID());
                                       // FileLog.saveToFileNewThred("connected  " + c.getID());
                                       responseRequests.getStockBase().messageRatingKillTime(c.getID());
                                       //FileLog.saveToFileNewThred("LivePlayer::  " + getSnapShots().getSizeLivePlayers());


                                   }
                               }
            );
        } catch (Exception e) {
            ServerLauncher.main(args);
            System.out.println("ReStartServer");
        }
    }
}
