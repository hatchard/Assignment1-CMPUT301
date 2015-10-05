package com.mycompany.assignment1;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
Reaction time stat requirements:
minimum time of all reaction times, the last 10 times, and the last 100 times.
maximum time of all reaction times, the last 10 times, and the last 100 times.
average time of all reaction times, the last 10 times, and the last 100 times.
median time of all reaction times, the last 10 times, and the last 100 times.
*/

/**
 * Created by Me on 2015-10-03.
 */
public class SortStats {
    Long max10 = Long.valueOf(0);
    Long min10 = Long.valueOf(0);
    Long avg10= Long.valueOf(0);
    Long med10 = Long.valueOf(0);
    Long max100= Long.valueOf(0);
    Long min100= Long.valueOf(0);
    Long avg100= Long.valueOf(0);
    Long med100 = Long.valueOf(0);
    Long maxAll= Long.valueOf(0);
    Long minAll= Long.valueOf(0);
    Long avgAll= Long.valueOf(0);
    Long medAll= Long.valueOf(0);
    ArrayList<Long> results = new ArrayList<>();

    //sort the stats so that they can be displayed later
    public ArrayList sortIt(ArrayList stats) {
        ArrayList statsCopy = (ArrayList)stats.clone(); //?


        // sort by increasing to get max and min
        //if bigger than or atleast 100 have to do all three

        //just for all
        if(statsCopy.size() < 10) {
            sortAll(statsCopy);
        }
        //have to do for 10 and all
        else if(statsCopy.size() <= 100){
            sort10(statsCopy);
            sortAll(statsCopy);
        }
        else if(statsCopy.size() >= 100) {
            sort10(statsCopy);
            sort100(statsCopy);
            sortAll(statsCopy);
        } else {}


        results.add(min10);
        results.add(max10);
        results.add(avg10);
        results.add(med10);
        results.add(min100);
        results.add(max100);
        results.add(avg100);
        results.add(med100);
        results.add(minAll);
        results.add(maxAll);
        results.add(avgAll);
        results.add(medAll);

        return results;

    }

    public void sortAll(ArrayList all) {
        Collections.sort(all);
        int midIndex = (all.size() / 2);
        maxAll = (Long) Collections.max(all);
        minAll = (Long) Collections.min(all);
        medAll = (Long) all.get(midIndex); //get middle number
        avgAll = calculateAverage(all);

    }

    public void sort100(ArrayList last100) {
        int end = last100.size();
        int start = end - 100;
        List miniList = last100.subList(start, end);
        Collections.sort(miniList);
        max100 = (Long)Collections.max(miniList);
        min100 = (Long)Collections.min(miniList);
        med100 = (Long)miniList.get(49); //get middle number
        avg100 = calculateAverage(miniList);

    }

    public void sort10(ArrayList last10) {
        int end = last10.size();
        int start = end - 10;
        List miniList = last10.subList(start, end);
        Collections.sort(miniList);
        max10 = (Long)Collections.max(miniList);
        min10 = (Long)Collections.min(miniList);
        med10 = (Long)miniList.get(4); //get middle number
        avg10 = calculateAverage(miniList);

    }

    //http://stackoverflow.com/questions/10791568/calculating-average-of-an-array-list Jesherun 10-03-2015
    public Long calculateAverage(List <Long>list) {
        Long sum = new Long(0);
        if(!list.isEmpty()) {
            for (Long i: list) {
                sum += i;
            }
            return sum / list.size();
        }
        return sum;
    }

}
