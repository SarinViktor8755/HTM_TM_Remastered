package com.mygdx.game.ClientNetWork.SteckApi;

import com.mygdx.game.ClientNetWork.Network;
import com.mygdx.game.MainGaming;


/**
 * Created by 1 on 23.11.2019.
 */

public class RequestStock {
    public Integer eventTime;
    public Integer tip;
    public Integer p1, p2, p3, p4, p5, p6;
    public boolean workOff;
    public String string;
    public Integer nomer_pley;

    private MainGaming mainGaming;

    public RequestStock(int eventTime, Integer tip, Integer p1, Integer p2, Integer p3, Integer p4, Integer p5, Integer p6, Integer nomer_pley, String text) {
        this.eventTime = eventTime;
        this.tip = tip;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
        this.p5 = p5;
        this.p6 = p6;
        this.nomer_pley = nomer_pley;
        this.string = text;
        workOff = false;
    }

    public RequestStock(Network.StockMess stockMess) {
        this.eventTime = stockMess.time_even;
        this.tip = stockMess.tip;
        this.p1 = stockMess.p1;
        this.p2 = stockMess.p2;
        this.p3 = stockMess.p3;
        this.p4 = stockMess.p4;
        this.p5 = stockMess.p5;
        this.p6 = stockMess.p6;
        this.string = stockMess.textM;
        this.nomer_pley = stockMess.nomer_pley;
        workOff = false;
    }

    public void sendStockUDP() {
        Network.StockMess stockMess = new Network.StockMess();
        stockMess.time_even = this.eventTime;
        stockMess.tip = this.tip;
        stockMess.p1 = this.p1;
        stockMess.p2 = this.p2;
        stockMess.p3 = this.p3;
        stockMess.p4 = this.p4;
        stockMess.p5 = this.p5;
        stockMess.p6 = this.p6;
        stockMess.textM = this.string;
        stockMess.nomer_pley = nomer_pley;
        workOff = false;
        mainGaming.getMainClient().client.sendUDP(eventTime);
    }

    @Override
    public String toString() {
        return "RequestStock{" +
                "tip=" + tip +
                ", p1=" + p1 +
                ", p2=" + p2 +
                ", p3=" + p3 +
                ", p4=" + p4 +
                ", p5=" + p5 +
                ", p6=" + p6 +
                '}';
    }
}
