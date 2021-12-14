package com.SteckApi;

import com.*;
import com.badlogic.gdx.math.Vector2;
import com.logicMatch.CommandLogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CalculationСontact { // класс для расчета поападания оруживем
    GameServer gameServer;

    public CalculationСontact(GameServer gameServer) {
        this.gameServer = gameServer;
    }

    public int getHit(int x, int y, int nomPlayer, int room, int radius) { // расчет удара - для палки
       // System.out.println(nomPlayer);
        HashMap<Integer, SnapShots.player> plMap = gameServer.snapShots.getPlayrsLisn();
        Vector2 position = new Vector2();
        SnapShots.player p;
        for (Map.Entry<Integer, SnapShots.player> pl : plMap.entrySet()) {
            if(!CommandLogic.isOpponents(nomPlayer,pl.getKey())) continue; // проверка из одной команды или нет
            p = gameServer.getSnapShots().getPlaeyrToId(pl.getKey());
            if (p.getRoom() != room || !p.isLive()) continue;
            if (!gameServer.getSnapShots().getPlaeyrToId(nomPlayer).isLive())
                return Integer.MIN_VALUE; // test String
            position.set(gameServer.getSnapShots().getPlaeyrToId(pl.getKey()).getX(), gameServer.getSnapShots().getPlaeyrToId(pl.getKey()).getY());
            if (StaticService.getDistance(position.x, position.y, x, y) < radius) { // замер расстояния
                gameServer.getCommandLogic().addPointcommand(CommandLogic.determineTeamPlayer(nomPlayer),1);

              //  System.out.println(gameServer.getCommandLogic());

                gameServer.indexMatch.addFragDromPlayer(nomPlayer);
                gameServer.getSnapShots().getStockBase().messagePlayerDestruction(pl.getKey()); // тут уже кто-то умер убили
                p.setLive(false);
                gameServer.getCalculationСontact().checkFragsWeapon(nomPlayer, pl.getKey());
                return pl.getKey();
            }
        }
        return Integer.MIN_VALUE;
    }

    public int getHit(int x, int y, int nomPlayer, int room, int radius, int angel, int weapon) { // расчет удара - для огнестрела
        //System.out.println(nomPlayer);
        HashMap<Integer, SnapShots.player> plMap = gameServer.snapShots.getPlayrsLisn();
        Vector2 position = new Vector2();
        SnapShots.player p;
        for (Map.Entry<Integer, SnapShots.player> pl : plMap.entrySet()) {
            if(!CommandLogic.isOpponents(nomPlayer,pl.getKey())) continue; // проверка из одной команды или нет
            p = gameServer.getSnapShots().getPlaeyrToId(pl.getKey());
            if (p.getRoom() != room || !p.isLive()) continue;
            if (!gameServer.getSnapShots().getPlaeyrToId(nomPlayer).isLive())
                return Integer.MIN_VALUE; // test String
            position.set(gameServer.getSnapShots().getPlaeyrToId(pl.getKey()).getX(), gameServer.getSnapShots().getPlaeyrToId(pl.getKey()).getY());
            if (StaticService.getDistance(position.x, position.y, x, y) < radius) { // замер расстояния
                gameServer.getCommandLogic().addPointcommand(CommandLogic.determineTeamPlayer(nomPlayer),1);
               // System.out.println(gameServer.getCommandLogic());

                gameServer.indexMatch.addFragDromPlayer(nomPlayer);
                gameServer.getSnapShots().getStockBase().messagePlayerDestruction(pl.getKey(),angel,weapon); // тут уже кто-то умер
                p.setLive(false);
                gameServer.getCalculationСontact().checkFragsWeapon(nomPlayer, pl.getKey());
                return pl.getKey();
            }
        }
        return Integer.MIN_VALUE;
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void checkFragsWeapon(int idKiller, int idVictim) { // метод для подсчета фрагов и оружия - мена оружия
        gameServer.getSnapShots().setТumberEnemiesLifeFromID(idVictim, 1);
        gameServer.getSnapShots().setТumberEnemiesLifeFromID(idKiller, gameServer.getSnapShots().getТumberEnemiesLifeFromID(idKiller) + 1);
        //System.out.println(idKiller + "   "+gameServer.getSnapShots().getТumberEnemiesLifeFromID(idKiller));
        if (gameServer.getSnapShots().getТumberEnemiesLifeFromID(idKiller) == 1)
            gameServer.snapShots.getPlaeyrToId(idKiller).setWeapon(2);
        if (gameServer.getSnapShots().getТumberEnemiesLifeFromID(idKiller) == 5)
            gameServer.snapShots.getPlaeyrToId(idKiller).setWeapon(3);
        gameServer.getSnapShots().getStockBase().packageTransferWeaponsForAllAboutBottom(idKiller);
    }


    public boolean respawnPlayer(int nomPlayer, int room) {
        if (gameServer.snapShots.isLive(nomPlayer)) return false;
        ArrayList<Integer> pleyers = gameServer.getListPlayerNotId(nomPlayer, 0);
        for (int i = 0; i < pleyers.size(); i++) {

            if (!gameServer.snapShots.isLive(pleyers.get(i))) continue;
            gameServer.snapShots.setLive(pleyers.get(i), false);
        }
        return false;
    }


    private boolean intersectionWithThisPlayer() {
        /// этот класс нужно срочно редактировать
        return false;
    }


}
