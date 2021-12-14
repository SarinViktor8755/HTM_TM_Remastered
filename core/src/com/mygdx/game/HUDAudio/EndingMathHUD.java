package com.mygdx.game.HUDAudio;

import com.mygdx.game.MainGaming;

import java.util.ArrayDeque;
import java.util.Iterator;

/**
 * Created by 1 on 31.03.2020.
 */

public class EndingMathHUD { // класс отвечает за концовку матча
    private boolean weCanFinish; // говорит о том что обновилось время или есть контакт с сервером+
    private boolean updateToServer; // получал обновления с сервера
    private float counter;
    private MainGaming mainGaming;
    private boolean said;
    private static float ENDING_TIME = 1500;



    public EndingMathHUD(MainGaming mainGaming) {
        this.weCanFinish = false;
        this.counter = 0;
        this.mainGaming = mainGaming;
        updateToServer = false;
        this.said = false;


    }

    public void update(float dt){
        counter += dt;
//        if(updateToServer && mainGaming.getHud().getTimer() < ENDING_TIME && !said){
//            said = true;
//            if(mainGaming.getHud().getMyPosition() ==1)
//            {mainGaming.getAudioEngine().pleyYouWin();
//                mainGaming.getHero().getPoolBlood().startingAdWiner();
//            }
//            else {
//                mainGaming.getAudioEngine().pleyYouLoose();
//                mainGaming.getHero().getPoolBlood().startingAdLose();
//            }

 //       }
//        if(updateToServer && mainGaming.getHud().getTimer() < ENDING_TIME){
//            mainGaming.getHud().getTimerTextLabel().setText("");
//            if(mainGaming.getHud().getMyPosition() ==1)mainGaming.getHud().getTimerTextLabel().setText("YOU WIN!!!"); else mainGaming.getHud().getTimerTextLabel().setText("YOU LOOSE!!!");
//        }

    }



    public boolean isWeCanFinish() {
        return weCanFinish;
    }

    public void setWeCanFinish(boolean weCanFinish) {
        this.weCanFinish = weCanFinish;
    }

    public boolean isUpdateToServer() {
        return updateToServer;
    }

    public void setUpdateToServer(boolean updateToServer) {
        this.updateToServer = updateToServer;
    }

    public float getCounter() {
        return counter;
    }

    public void setCounter(float counter) {
        this.counter = counter;
    }

    public MainGaming getMainGaming() {
        return mainGaming;
    }

    public void setMainGaming(MainGaming mainGaming) {
        this.mainGaming = mainGaming;
    }
}
