package com.RatingSystem;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by 1 on 08.04.2021.
 */

public class FileReaderWriter {
    private String urlFile = "raytingDate.txt";

    public void readFromFile(ArrayList<Personality> rl) {

    }

    public void saveFromFile(ArrayList<Personality> rl) { // сохранение Обьектов в файл
        FileWriter writer = null;
        try {
            writer = new FileWriter(urlFile);

        for (Personality pers : rl) {
            writer.write(rl.toString()+ pers.getUuid() + System.getProperty("line.separator"));
            writer.close();
        }

        } catch (IOException e) {
            e.printStackTrace();
        }

//
//        try {
//            FileWriter fileWriter = null;
//            fileWriter = new FileWriter("raytingDate.txt");
//
//            PrintWriter printWriter = new PrintWriter(fileWriter);
//            printWriter.print("Some String");
//            printWriter.print("Some String1111 /n asdad a");
//            //printWriter.printf("Product name is %s and its price is %d $", "iPhone", 1000);
//            printWriter.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        //String read = Files.readAllLines(path).get(0);
        // assertEquals(str, read);


    }


}
