/*
   Copyright 2015 Jenna Hatchard

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

package com.mycompany.assignment1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Me on 2015-10-03.
 */
public class SortStats {
    //initializes all of them to zero
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
        ArrayList statsCopy = (ArrayList)stats.clone(); //need to do this otherwise it can mess up later stats

        //just for all
        if(statsCopy.size() < 10) {
            sortAll(statsCopy);
        }
        //have to do for 10 and all
        else if(statsCopy.size() <= 100){
            sort10(statsCopy);
            sortAll(statsCopy);
        }
        //for everything!
        else if(statsCopy.size() >= 100) {
            sort10(statsCopy);
            sort100(statsCopy);
            sortAll(statsCopy);
        } else {}

        //add the new sorted results in
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
        Long zeros = Long.valueOf(0);
        Collections.sort(all);
        ArrayList subAll = new ArrayList();
        // get rid of the zero place holders
        //checks that there are not zeros messing with the stats
        int zeroFrequency = Collections.frequency(all, zeros);
        if(zeroFrequency != 0) {
            for(Object i:all){
                if(i.equals(zeros)){}
                else {
                    subAll.add(i);
                }
            }
            //if the entire list was empty
            if(subAll.isEmpty()) {
                maxAll = zeros;
                minAll = zeros;
                medAll = zeros;
                avgAll = zeros;
            }
            else {
                int midIndex = (subAll.size() / 2);
                maxAll = (Long) Collections.max(subAll);
                minAll = (Long) Collections.min(subAll);
                medAll = (Long) all.get(midIndex); //get middle number
                avgAll = calculateAverage(subAll);
            }
        } else {
            int midIndex = (all.size() / 2);
            maxAll = (Long) Collections.max(all);
            minAll = (Long) Collections.min(all);
            medAll = (Long) all.get(midIndex); //get middle number
            avgAll = calculateAverage(all);
        }

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
