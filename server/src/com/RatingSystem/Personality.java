package com.RatingSystem;

/**
 * Created by 1 on 08.04.2021.
 */

public class Personality {
    private String uuid; // это id игрока
    private int score; // счет игрока
    private int gameCounter;// счетчик  чего то я забыл ))))
    private int deathCounter; // счетчик Убийств

    private int nomberConnect;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getGameCounter() {
        return gameCounter;
    }

    public void setGameCounter(int gameCounter) {
        this.gameCounter = gameCounter;
    }

    public int getDeathCounter() {
        return deathCounter;
    }

    public void setDeathCounter(int deathCounter) {
        this.deathCounter = deathCounter;
    }


    public Personality(String uuid, int score, int gameCounter, int deathCounter, int nomerCoonect) {
        this.uuid = uuid;
        this.score = score;
        this.gameCounter = gameCounter;
        this.deathCounter = deathCounter;
        this.nomberConnect = nomerCoonect;
    }

    @Override
    public String toString() {
        return "{" +
                "uuid='" + uuid + '\'' +
                ", score=" + score +
                ", gameCounter=" + gameCounter +
                ", deathCounter=" + deathCounter +
                '}';
    }
}
