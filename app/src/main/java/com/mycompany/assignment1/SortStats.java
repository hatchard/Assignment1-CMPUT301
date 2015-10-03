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
    static Long max10, min10, avg10, med10;
    Long max100, min100, avg100, med100;
    Long maxAll, minAll, avgAll, medAll;

    //sort the stats so that they can be displayed later
    public void sortIt(ArrayList stats) {
        ArrayList statsCopy = (ArrayList)stats.clone(); //?

        // sort by increasing to get max and min
        //if bigger than or atleast 100 have to do all three
        if(statsCopy.size() >= 100) {
            sort10(statsCopy);
            sort100(statsCopy);
            sortAll(statsCopy);
        }
        //have to do for 10 and all
        if(10 < statsCopy.size() && statsCopy.size() <= 100){
            sort10(statsCopy);
            sortAll(statsCopy);
        }
        //just for all
        if(statsCopy.size() < 10) {
            sortAll(statsCopy);
        }

    }

    public void sortAll(ArrayList all) {
        Collections.sort(all);
        int midIndex = all.size()/2 +1;
        maxAll = (Long)Collections.max(all);
        minAll = (Long)Collections.min(all);
        medAll = (Long)all.get(midIndex); //get middle number
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
