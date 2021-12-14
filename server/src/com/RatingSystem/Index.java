package com.RatingSystem;

import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by 1 on 08.04.2021.
 */

public class Index {
    private ArrayList<Personality> raitingList = new ArrayList<>();
    private FileReaderWriter fileReaderWriter;

    public Index() {
        this.fileReaderWriter = new FileReaderWriter();
        this.raitingList = new ArrayList<>();
    }

    public void fillRatingSystem() {
//
//        for (int i = 0; i < MathUtils.random(40); i++) {
//            this.raitingList.add(new Personality("Vity", MathUtils.random(600), MathUtils.random(600), MathUtils.random(600)));
//        }

//        fileReaderWriter.saveFromFile(raitingList);

    }

    public void addNewPersone(int idConnect) {
        this.raitingList.add(new Personality(null, 0, 0, 0, idConnect));
    }

    public void checkEmptiness() { // проеряет на не заполненую персону
        Personality p;
        for (int i = 0; i < this.raitingList.size(); i++) {
            p = this.raitingList.get(i);
            if (p.getUuid() == null) {
               // System.out.println(p);

            }

          //  System.out.println("checkEmptiness");


        }
    }

    @Override
    public String toString() {
        return raitingList.toString();
    }

    public void sortRaiting() {

        Comparator<Integer> cmp = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
//            // TODO Auto-generated method stub
//            int pos=0;
//            if(pos%2==0){
//                if(o1<o2){
//                    return 1;
//                }
//            }
//            if(pos%2!=0){
//                if(o1>o2){
//                    return -1;
//                }
//            }
//            pos++;
//        }
                return 1;
            }
        };


    }


}
