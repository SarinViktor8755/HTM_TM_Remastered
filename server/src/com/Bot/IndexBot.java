package com.Bot;

import com.Bot.Map.IndexMap;
import com.GameServer;
import com.badlogic.gdx.math.MathUtils;
import com.logicMatch.CommandLogic;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by qwerty on 18.02.2020.
 */

public class IndexBot extends Thread {
    private GameServer gameServer;
    private HashMap<Integer, SimulationUnit> units;
    private IndexMap indexMap;
    private final int MIN_PLEYSES;
    private boolean interrupted; // если ТРУЕ - выключает поток


    public IndexBot(GameServer gameServer, int mixPlayer) {
        indexMap = new IndexMap();
        this.gameServer = gameServer;
        units = new HashMap<>();
        MIN_PLEYSES = mixPlayer;
        interrupted = false;


        ////////////////////////////////////////////
    }

    public void run() {
        final long tackt = 24;
        while (true) {
            try {
                Thread.sleep(tackt);
                //System.out.println("bots 1" + this.units);
//                System.out.println(gameServer.getSnapShots().getPlayrsLisn().size());
//                //System.out.println(gameServer.getSnapShots().numberLivePlayers());
                if(!gameServer.getSnapShots().numberLivePlayers()) Thread.sleep(500);

                ///////////////////////////////
                if(gameServer.getSnapShots().getPlayrsLisn().size() < MIN_PLEYSES ) {
                    int id = MathUtils.random(-1000, 1);

                    while (!gameServer.getCommandLogic().thisBotComand(id,gameServer.getListPlayer())){
                        id = MathUtils.random(-1000, 1);
                    }
                    units.put(id, new SimulationUnit(this, id, tackt));
                }

                //////////////////////////////

                try {
                    if(!getGameServer().getIndexMatch().isMatchRun()) continue;
                for (Map.Entry<Integer, SimulationUnit> entry : units.entrySet()) {
                        entry.getValue().getLogicBot().actionUnits();
                    if (gameServer.getSnapShots().getPlayrsLisn().size() > MIN_PLEYSES) {
                        entry.getValue().disconect(entry.getKey());
                    }
                }

                }catch (NullPointerException e){
                    if(!gameServer.getIndexMatch().isMatchRun()) {
                        Thread.sleep(2000);
                    }continue;
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
                continue;
            } catch (ConcurrentModificationException e) {
                //e.printStackTrace();
                continue;
            }
        }
    }

    private int getSizePlayers() {
        return gameServer.getListP(0).size();
    }

    public HashMap<Integer, SimulationUnit> getUnits() {
        return units;
    }

    public GameServer getGameServer() {
        return gameServer;
    }

    public IndexMap getIndexMap() {
        return indexMap;
    }



}
