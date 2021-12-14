package com.logicMatch;

import com.GameServer;

import java.util.ConcurrentModificationException;
import java.util.HashMap;

public class ScoreGame {
    private GameServer gameServer;

    public ScoreGame(GameServer gameServer) {
        this.gameServer = gameServer;
    }

    public void addFragToPlayer(int idPlayer) {     // добавить фраги
        gameServer.getSnapShots().setFragToID(idPlayer, gameServer.getSnapShots().getPlaeyrToId(idPlayer).getFrag() + 1);
    }

    public int getFragFromPlayer(int idPlayer) {     // взять фраги
        return gameServer.getSnapShots().getPlaeyrToId(idPlayer).getFrag();

    }

//    public int getRaitingFromPlayer(int idPl) { // вычесляет место в турнирной таблице
//        int myFrag = gameServer.getSnapShots().getFragToID(idPl);
//        int result = 1;
//
//        for (Integer key : gameServer.getSnapShots().getPlayrsLisn().keySet()) {
//            if (myFrag < gameServer.getSnapShots().getPlaeyrToId(key).getFrag()) {
//                result++;
//            }
//            if (myFrag == gameServer.getSnapShots().getPlaeyrToId(key).getFrag()) {
//                if (idPl > key) result++;
//            }
//        }
//        return result;
//    }


    public int fineFirstLiderFragsPlayer() { // наибольшее количество фрагов
        int result = 0;
        for (Integer key : gameServer.getSnapShots().getPlayrsLisn().keySet()) {
            if (gameServer.getSnapShots().getPlaeyrToId(key).getFrag() > result)
                result = gameServer.getSnapShots().getPlaeyrToId(key).getFrag();
        }
        return result;
    }

    public Integer getWhatRating(int raitings) { // кто на на 1 ... 2... месте
        try {
            GetRatingList();
            int rating;
            for (Integer myId : gameServer.getSnapShots().getPlayrsLisn().keySet()) {
                rating = 1;
                for (Integer idSr : gameServer.getSnapShots().getPlayrsLisn().keySet()) {
                    if (gameServer.getSnapShots().getFragToID(myId) < gameServer.getSnapShots().getFragToID(idSr))
                        rating++;
                    else if (gameServer.getSnapShots().getFragToID(myId) == gameServer.getSnapShots().getFragToID(idSr)) {
                        if (idSr > myId) rating++;
                    }
                }

                if (rating == raitings) return myId;
            }
        }catch (ConcurrentModificationException e){
            e.printStackTrace();
        }
        return null;
    }

//////////////////////////////////////////////////

    public synchronized int whatPositionPlayer(int id) { // на каком месте игрок nomer  id
        try {
            int rating;
            int myId = id;
            rating = 1;
            for (Integer idSr : gameServer.getSnapShots().getPlayrsLisn().keySet()) {
                if (gameServer.getSnapShots().getFragToID(myId) < gameServer.getSnapShots().getFragToID(idSr))
                    rating++;
                else if (gameServer.getSnapShots().getFragToID(myId) == gameServer.getSnapShots().getFragToID(idSr)) {
                    if (idSr > myId) rating++;
                }
            }
            return rating;
        }catch (ConcurrentModificationException e){
            System.out.println("whatPositionPlayer :: ConcurrentModificationException");
            return 99;
        }
    }

    public String GetRatingList() {
        int pos;
        HashMap<Integer, raitingTemp> raitingTree = new HashMap<>();
        for (Integer idPlayer : gameServer.getSnapShots().getPlayrsLisn().keySet()) {
            pos = whatPositionPlayer(idPlayer);
            raitingTree.put(pos, new raitingTemp(pos,idPlayer,gameServer.getSnapShots().getFragToID(idPlayer)));
        }
        //System.out.println(raitingTree);

        return "";
    }
}

class raitingTemp {
    int  r,id, f;

    public raitingTemp(int r,int id, int f) {
        this.id = id;
        this.f = f;
        this.r = r;
    }

    @Override
    public String toString() {
        return          r +
                        ":" + id +
                        ":" + f;
    }
}






