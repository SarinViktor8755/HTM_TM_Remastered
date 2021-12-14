package com.SteckApi;

import com.FileLog;
import com.GameServer;

import java.util.Map;

public class ProcessingOutputQueue extends Thread {
    StockRequestService outSteck;
    GameServer gameServer;

    ProcessingOutputQueue(StockRequestService outQuery, GameServer gs) {
        this.outSteck = outQuery;
        this.gameServer = gs;

        this.start();

    }

    @Override
    public void run() {
        if (outSteck.getSize() < 1) return;
        outSteck.processingOfIncomingMessages();
    }
}
