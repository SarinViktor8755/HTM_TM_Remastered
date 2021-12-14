package com.logicMatch;


import com.GameServer;

import java.util.ArrayList;

public class CommandLogic {
    private int teamPoints1;
    private int teamPoints2;

    static public int determineTeamPlayer(int nomerPlayer) { // определяем команду )))
        if (Math.abs(nomerPlayer) % 2 == 0) return 1;
        else return 0;
    }

    public void zeroPoint() {
        this.teamPoints1 = 0;
        this.teamPoints2 = 0;
    }

    public int whoIsWinner() {
        if (teamPoints1 > teamPoints2) return 1;
        if (teamPoints1 < teamPoints2) return 2;
        return 0;
    }

    public static boolean isOpponents(int player1, int player2) { // соперник ??
        if (determineTeamPlayer(player1) != determineTeamPlayer(player2)) return true;
        else return false;
    }


    ///////////////////
///////////////////
    public int getTeamPoints1() {
        return teamPoints1;
    }

    public void setTeamPoints1(int teamPoints1) {
        this.teamPoints1 = teamPoints1;
    }

    public int getTeamPoints2() {
        return teamPoints2;
    }

    public void setTeamPoints2(int teamPoints2) {
        this.teamPoints2 = teamPoints2;
    }

    public void addPointcommand(int idPlayer, int point) {
//        System.out.println("---------------------------------");
//        System.out.println("id " + idPlayer);
//        System.out.println("coomand " + CommandLogic.determineTeamPlayer(idPlayer));

       // if (idPlayer == 0) return;

        if (CommandLogic.determineTeamPlayer(idPlayer) == 0) {
            this.setTeamPoints1(this.getTeamPoints1() + point);

        } else this.setTeamPoints2(this.getTeamPoints2() + point);


    }

    public boolean thisBotComand(int id, ArrayList<Integer> listPlayer){
        int blue = 0;
        int red = 0;
       // System.out.println("-------------");
        for (Integer player : listPlayer) {
            if(CommandLogic.determineTeamPlayer(player) == 0) blue++; else red ++;
        }
        //System.out.println(blue + " - " + red);
        if(red == blue) return true;
        int command = CommandLogic.determineTeamPlayer(id);
        if(red < blue && command == 0) return false;
        if(red > blue && command == 1) return false;
        return true;
    }



    @Override
    public String toString() {
        return "team BLUE=" + teamPoints1 +
                ", team RED =" + teamPoints2 +
                '}';
    }
}
