package com.SteckApi;

import com.Network;

public class RequestStockServer {
    public Integer eventTime;
    public Integer tip;
    public Integer p1, p2, p3, p4, p5, p6;
    public boolean workOff;
    public String string;
    public Integer nomer_pley;
    public Integer fromPleayer;


    public Network.StockMess getStockMess() {
        Network.StockMess stockMess = new Network.StockMess();

        stockMess.p1 = this.p1;
        stockMess.p2 = this.p2;
        stockMess.p3 = this.p3;
        stockMess.p4 = this.p4;
        stockMess.p5 = this.p5;
        stockMess.p6 = this.p6;
        stockMess.tip = this.tip;
        stockMess.nomer_pley = this.nomer_pley;
        stockMess.time_even = this.eventTime;
        return stockMess;
    }


    public RequestStockServer(Integer eventTime, Integer tip, Integer p1, Integer p2, Integer p3, Integer p4, Integer p5, Integer p6, String string, Integer nomer_pley, Integer from) {
        this.eventTime = eventTime;
        this.tip = tip;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
        this.p5 = p5;
        this.p6 = p6;
        this.workOff = false;
        this.string = string;
        this.nomer_pley = nomer_pley;
        this.fromPleayer = from;
    }

    public RequestStockServer(Network.StockMess requestStock) {
        this.eventTime = requestStock.time_even;
        this.tip = requestStock.tip;
        this.p1 = requestStock.p1;
        this.p2 = requestStock.p2;
        this.p3 = requestStock.p3;
        this.p4 = requestStock.p4;
        this.p5 = requestStock.p5;
        this.p6 = requestStock.p6;
        this.workOff = false;
        this.string = requestStock.textM;
        this.nomer_pley = requestStock.nomer_pley;

    }

    public RequestStockServer(Network.StockMess requestStock, int from) {
        this.eventTime = requestStock.time_even;
        this.tip = requestStock.tip;
        this.p1 = requestStock.p1;
        this.p2 = requestStock.p2;
        this.p3 = requestStock.p3;
        this.p4 = requestStock.p4;
        this.p5 = requestStock.p5;
        this.p6 = requestStock.p6;
        this.workOff = false;
        this.string = requestStock.textM;
        this.nomer_pley = requestStock.nomer_pley;
        this.fromPleayer = from;

    }


    @Override
    public String toString() {
        return "RequestStockServer{" +
                "eventTime=" + eventTime +
                ", tip=" + tip +
                ", p1=" + p1 +
                ", p2=" + p2 +
                ", p3=" + p3 +
                ", p4=" + p4 +
                ", p5=" + p5 +
                ", p6=" + p6 +
                ", workOff=" + workOff +
                ", string='" + string + '\'' +
                ", nomer_pley=" + nomer_pley +
                ", fromPleayer=" + fromPleayer +
                '}';
    }
}
