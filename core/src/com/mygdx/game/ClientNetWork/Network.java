package com.mygdx.game.ClientNetWork;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;


public class Network {
    final public static int udpPort = 37000, tcpPort = 37000;
    //final  public static String ip = "127.0.0.1";
    final public static String ip = "92.124.144.204";

    static public void register(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(Integer.class);
        kryo.register(PleyerPosition.class);
        kryo.register(PleyerPositionNom.class);
        kryo.register(rc.class);
        kryo.register(StockMess.class);
        kryo.register(Answer.class);
    }

    /////////////////////////////////////
    public static class PleyerPosition {   //позиция
        public Integer xp;
        public Integer yp;
        public Integer rot;
    }

    public static class PleyerPositionNom {   //ответ позиция с номером
        public Integer nom;
        public Integer xp;
        public Integer yp;
        public Integer rot;
    }

    public static class StockMess {   //сообщение из стока
        public Integer time_even;
        public Integer tip;
        public Integer p1;
        public Integer p2;
        public Integer p3;
        public Integer p4;
        public Integer p5;
        public Integer p6;
        public String textM;
        public Integer nomer_pley;

    }

    static public class rc {
        public Integer id;
    }

    static public class Answer {
        public Integer nomber;
    }
}
