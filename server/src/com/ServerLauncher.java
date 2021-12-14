package com;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by 1 on 23.09.2019.
 */

public class ServerLauncher {
    static GameServer server;

    public static void main(String[] args) throws IOException {
//        UUID uuid = UUID.randomUUID();
//        String uuidAsString = uuid.toString();
//        System.out.println("Your UUID is: " + uuidAsString);


           // FileLog.saveToFileNewThred("StartServer<br>");
            server = new GameServer(args,new ServerLauncher());


            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                       // e.printStackTrace();
           //             FileLog.saveToFileNewThred("__________________________Exception >>");
//                        FileLog.saveToFileNewThred(e.toString());
//                        FileLog.saveToFileNewThred("<<<<<<<<__________________________Exception");
                    }
                }
            }).start();
    }


    public void restartServer(){
        try {
            server.server.stop();
            String[] nom = new String[1];
            nom[0] = "9";
            this.main(nom);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}