package com.logicMatch;


import com.FileLog;
import com.GameServer;
import com.Network;
import com.mygdx.game.Service.TimeService;


import java.io.IOException;

import static com.Network.register;

public class IndexMatch extends Thread {
    private GameServer gameServer;
    private float MAX_TIMER_MATH = 4_000 * 3;
    private float MAX_TIMER_PAUSE = 5_000 * 5000;
    private final float STEP = 250;
    private float currentTimerMatch;
    private ScoreGame scoreGame;
    private boolean matchRun = false;


    public void setMatchRun(boolean matchRun) {
        this.matchRun = matchRun;
    }


    public IndexMatch(GameServer gameServer) {
        //System.out.println("logic of the match");
        this.gameServer = gameServer;
    }

    public float getMAX_TIMER_MATH() {
        return MAX_TIMER_MATH;
    }

    public float getMAX_TIMER_PAUSE() {
        return MAX_TIMER_PAUSE;
    }

    private void upDateTimeMatch() {
        if (currentTimerMatch <= 0 && matchRun) {
            stopMatch();
        }
        if (currentTimerMatch <= 0 && !matchRun) {
            gameServer.getSnapShots().zirosFrags();
            starMatch();
        }
        currentTimerMatch -= STEP;
        ////////////
        gameServer.raitingSystem.checkEmptiness();
       // gameServer.
    }

    public void addFragDromPlayer(int idPlayer) {
        this.scoreGame.addFragToPlayer(idPlayer);
        //System.out.println(this.scoreGame);
        //System.out.println(this.scoreGame.getFirstPlayer());

    }

    private void starMatch() {
        gameServer.getResponseRequests().getStockBase().cleanerQueuesAll();
        gameServer.getSnapShots().cleaningSnapSgots();
        this.matchRun = true;
        this.currentTimerMatch = MAX_TIMER_MATH;
        this.scoreGame = new ScoreGame(gameServer);
        gameServer.getCommandLogic().zeroPoint();
        System.out.println("!!!Start server");
        FileLog.saveToFileNewThred("Start server");


    }

    private void stopMatch() {
        gameServer.getSnapShots().cleaningSnapSgots();
//        gameServer.server.close();
//        register(gameServer.server);
//        try {
//            gameServer.server.bind(Network.tcpPort, Network.udpPort);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        gameServer.server.start();

        currentTimerMatch = MAX_TIMER_PAUSE;
        this.matchRun = false;
        gameServer.getCommandLogic().zeroPoint();
        System.out.println("-----Stop");
        FileLog.saveToFileNewThred("<<< STOP SERVER >>>");
        FileLog.saveToFileNewThred("LP ::  " + gameServer.getSnapShots().getPlayrsLisn().keySet().size());
       // this.messageMatchOver();

    }

    public void run() {
        try {
            while (true) {
                upDateTimeMatch();
//                Thread.sleep((long) STEP);
//                System.out.println(currentTimerMatch);
//                if (currentTimerMatch <= 0) stopMatch();
//                if (!isMatchRun()) this.starMatch();
//                else upDateTimeMatch();



                Thread.sleep((long) STEP);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

//    private void messageMatchOver() {
//        gameServer.getSnapShots().getStockBase().addMessEndMath();
//    }

//    private void messageMatchStart() {
//        gameServer.getSnapShots().getStockBase().addMessStartMath();
//    }


    public void SendPlayerToPause(int nomPlayer){
        if(this.isMatchRun()) return;
        Network.StockMess stockMess = new Network.StockMess();
        stockMess.tip = -9;
        stockMess.nomer_pley = nomPlayer;
        stockMess.time_even = TimeService.getTimeGame() * (-1);
        stockMess.p1 = (int) gameServer.indexMatch.getCurrentTimerMatch();// ВРЕМЯ МАТЧАЯ все ОК или время паузы
        stockMess.p2 = -99; // ФРАГИ
        gameServer.server.sendToAllUDP(stockMess);

    }
/////////////////////////////
    public boolean isMatchRun() {
        return matchRun;
    }

    public float getCurrentTimerMatch() {
        return currentTimerMatch;
    }

    public int getFragFromPlayer(int idPlayer) {
        return scoreGame.getFragFromPlayer(idPlayer);
    }


    public int getRaitongFromPlayer(int idPl){
        return scoreGame.whatPositionPlayer(idPl);
    }

    public int fineFirstLiderFragsPlayer(){  // пришлось отключить не понятно по какой причине
        return gameServer.getSnapShots().getFragToID(scoreGame.getWhatRating(1));
    }
/////////////////////////////
}
