package com.mygdx.game.HUDAudio;

import com.mygdx.game.MainGaming;
import com.mygdx.game.Service.StaticService;

/**
 * Created by 1 on 28.03.2020.
 */

public class SoundTrack {
    private MainGaming mainGaming;
    private boolean startGame;
    private float timer;
    private float voiceTimer;
    private int realFrag;
    private boolean finalVoice;
    private boolean lidrVoice;
    private int position;

    private byte liderGame;

    public SoundTrack(MainGaming mainGaming) {
        this.mainGaming = mainGaming;
        startGame = false;
        timer = 0;
        position = 0;
        this.realFrag = 0;
        lidrVoice = true;
        finalVoice = false;
        this.liderGame = 0;
        this.liderGame = -1;
    }

    public void ubdate(float dt) {
        if (!mainGaming.getMainClient().isConnectToServer()) {
            timer = 0;
            this.startGame = false;
        } else
            timer += dt;
        voiceTimer += dt;
        pleyStart();

        if (mainGaming.getHud().getTimer() < 3000) {
            pleyFinal();
        }

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void pleyFinal() { // определяется победитель
        if (finalVoice) return;
        finalVoice = true;
        // int pn = mainGaming.getMainClient().getMyIdConnect();
        //System.out.println(StaticService.determineTeamPlayer(mainGaming.getMainClient().getMyIdConnect()) + "!!!!!!!");
        boolean imRead = StaticService.determineTeamPlayer(mainGaming.getMainClient().getMyIdConnect()) == 0;
        boolean redWin = (mainGaming.getHud().getLider() == 1);
        ////////////////////////////////////////////////////////////////////
//        System.out.println("imRead:: " + imRead);
//        System.out.println("redWin:: " + redWin);
        ////////////////////////////////////////////////////////////////////


        //  if (mainGaming.getHud().getLider() != StaticService.determineTeamPlayer(pn)) { // тут ошибка!!!
        if (imRead) { // красный
            if (redWin) {
                youWin();
            } else {
                youLose();
            }
        } else {        // синий
            if (redWin) {
                youLose();
            } else {
                youWin();
            }
        }
    }

    private void youWin() {
        mainGaming.getAudioEngine().pleyYouWin();
        mainGaming.getHero().getPoolBlood().startingAdWiner();
        mainGaming.musicGame.stopMusic();
        //   System.out.println("WINER");
    }


    private void youLose() {
        mainGaming.getAudioEngine().pleyYouLoose();
        mainGaming.getHero().getPoolBlood().startingAdLose();
        mainGaming.musicGame.stopMusic();
        //  System.out.println("LOSE");
    }

    private void pleyStart() {
        if (!startGame && (timer > 1)) {
            mainGaming.getAudioEngine().pleyFight();
            startGame = true;
            voiceTimer = 0;
            mainGaming.getHero().getPoolBlood().startingAdStart();
        }

    }

    public void pleyLostPrimuschestvo() {
        if (mainGaming.getTimeInGame() < 5) return;
        byte stlider = this.liderGame;
        beatMatchLeader();//обносить лидера
        if (stlider != this.liderGame) {
            if (
                    mainGaming.getHud().getFCommand1() == mainGaming.getHud().getFCommand2()
                    ) return;
            int myComand = StaticService.determineTeamPlayer(mainGaming.getMainClient().getMyIdConnect());
//            System.out.println("nLider " + this.liderGame);
//            System.out.println((myComand + 1) + " -  - - -- - -  - " + this.liderGame);
            if (myComand + 1 != liderGame) {
                mainGaming.getAudioEngine().pleyBestLead();
                mainGaming.getHero().getPoolBlood().startingAdFirst(); // ПЕРВЫЙ
            } else {
                mainGaming.getAudioEngine().pleyLostLead();
                mainGaming.getHero().getPoolBlood().startingAdLostLead(); // ПЕРВЫЙ
            }


            return;

        }

        int frag = mainGaming.getHud().getMyFrags();
        if (frag > this.realFrag) {
            //   System.out.println("DUKE1");
            if (voiceTimer > 3) {
                //        System.out.println("DUKE2");
                mainGaming.getAudioEngine().pleyVoice();
                voiceTimer = 0;
            }
            this.realFrag = frag;
        }
        return;
    }

    public void pleyVoice() {
        //System.out.println("sound 1");
        if (voiceTimer < 5) return;
        if (timer < 3) return;
        //System.out.println("sound 2");
        if (StaticService.selectWithProbability(80)) {
            //System.out.println("sound 3");
            mainGaming.getAudioEngine().pleyVoice();
            this.voiceTimer = 0;
        }

    }

    public void beatMatchLeader() {
        int fragCommand_1 = mainGaming.getHud().getFCommand1();
        int fragCommand_2 = mainGaming.getHud().getFCommand2();
        if (fragCommand_1 > fragCommand_2) this.liderGame = 1;
        else if (fragCommand_1 < fragCommand_2) this.liderGame = 2;
        else this.liderGame = 0;
    }


}
