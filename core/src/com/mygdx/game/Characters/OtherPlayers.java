package com.mygdx.game.Characters;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.Characters.Animation.AnimatonBody;

import java.util.ConcurrentModificationException;
import java.util.HashMap;

public class OtherPlayers {
    private HashMap<Integer, Player> playersList;

    public OtherPlayers() {
        playersList = new HashMap<Integer, Player>();
    }

    //////////////////////////////////////////////
    public float getTimerCurrenCycleDromPlayer(int id) {
        try {
            return getPlayerToID(id).getTimerCurrenCycle(); //получить текущий цикл таймера
        } catch (NullPointerException e) {
            Gdx.app.error("MyTag", "!!!ConcurrentModificationException");
            return Float.MIN_VALUE;
        }
    }

    public void cleaningSnapShots(){
        this.playersList.clear();
    }

    public void setTimerCurrenCycleDromPlayer(int id, float timerCurrenCycleDrom) {
        getPlayerToID(id).setTimerCurrenCycle(timerCurrenCycleDrom);
    }

    ////

    public int getSizeGamePlayer(){
        return playersList.size();
    }

    public int getTacktPlayer(int id) {
        return getPlayerToID(id).getTackt();
    }

    public void setTacktPlayer(int id, int TacktPlayer) {
        getPlayerToID(id).setTackt(TacktPlayer);
    }

    public float getSpeedPlayer(int id) {
        try {
            return getPlayerToID(id).getVelocity();
        } catch (NullPointerException ex) {
            return 0;
        }

    }

    public int getXplayToId(int id) {
        try {
            return getPlayerToID(id).getX();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return Integer.MIN_VALUE;
        }
    }

    public float getAlphaFromId(int id){
        try {
            return getPlayerToID(id).getAlpha();
        } catch (NullPointerException ex) {
            return 0;
        }
    }


    public void setAlphaFromId(int id, float apha){
        try {
            getPlayerToID(id).setAlpha(apha);
        } catch (NullPointerException ex) {
        }
    }

    public int getYplayToId(int id) {
        return getPlayerToID(id).getY();
    }

    public int getRotationToId(int id) {
        return getPlayerToID(id).getRot();
    }

    public void setTimeUpdate(int id) {
        getPlayerToID(id).setUpdateTime(System.currentTimeMillis());
    }

    public  boolean isDeprecated(int id){
        //System.out.println(System.currentTimeMillis() - getPlayerToID(id).getUpdateTime());
        return System.currentTimeMillis() - getPlayerToID(id).getUpdateTime() > 4000;
    }
    public int getMaskToID(int id){
        try {
            return getPlayerToID(id).getNomMask(id);
        }catch (NullPointerException e){
            return 1;
        }

    }



    public int getRotationBotsToId(int id) {
        return getPlayerToID(id).getRotBoots();
    }

    public boolean getLiveTiId(int id)throws NullPointerException  {
        return getPlayerToID(id).getLiveToId(id);
    }


    public Color getColorPfromId(int id){
       return getPlayerToID(id).getColor();
    }

    public void setLiveTiId(int id, boolean live) {
        try {
            getPlayerToID(id).setLive(live); //java.lang.NullPointerException: Attempt to invoke virtual method 'void com.mygdx.game.Characters.Player.setLive(boolean)' on a null object reference
        }catch (NullPointerException e){
            this.setLiveTiId(id,live);
           // System.out.println("operation is ready " + this.getLiveTiId(id));
            e.printStackTrace();  // если ошибка надо тупо создать такова пользователя
        }
    }

    //////////////////////
    public Player addPlayer(int id) {
        return playersList.put(id, new Player(Integer.MIN_VALUE, 0, 0 , id));
    }

    public HashMap<Integer, Player> getPlayersList() {return playersList;
    }

    public void upDateDeltaTimeAllPlayer(float delta) {
        //System.out.println(playersList);
        try {
            for (Player key : playersList.values()) {
                key.setTimerCurrenCycle(key.getTimerCurrenCycle() + delta);
                // System.out.println(playersList);
            }
        }catch (ConcurrentModificationException e){
            System.out.println("upDateDeltaTimeAllPlayer ^^ ConcurrentModificationException");
        }

    }

    public Player getPlayerToID(int id) {
        Player result = playersList.get(id);
        if (result == null) {
            result = playersList.put(id, new Player(Integer.MIN_VALUE, 0, 0 , id));
        }
        return result;
    }


    public AnimatonBody getAnimationFromIdPl(int id) {
        return getPlayerToID(id).getAnimatonBody();
    }

    public Player getPlayerToIDfromList(int id) {
        return playersList.get(id);
    }

    //////////////////////////////






}
