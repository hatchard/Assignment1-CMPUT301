package com.mycompany.assignment1;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;



public class Statistics extends AppCompatActivity {
    //Save Reaction Times


    //declaring so many variables and things
    public SortStats sortStats = new SortStats();
    private static final String FILENAME = "reactionTimerStats";
    private static final String BUZZERFILENAME = "buzzerStats";
    public static HashMap<String, ArrayList> oldWinnerList;
    public ArrayList<Long> oldTimesArray;
    public static ArrayList results;
    //initialize array lists for in the hashmap
    public static ArrayList players2;
    public static ArrayList players3;
    public static ArrayList players4;
    //boolean initialized;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        //initialized = true;
        if(oldWinnerList == null){
            makeStartList(getBaseContext());
        }

        Button emailButton = (Button) findViewById(R.id.email);
        Button clearButton = (Button) findViewById(R.id.clear);


        emailButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String sendStats = getStatistics().toString();
                //String[] recipiant = "";
                composeEmail("Gameshow Buzzer/Reaction Timer Stats", sendStats);
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Long temp = Long.valueOf(0);
                oldTimesArray.clear();
                oldTimesArray.add(temp);
                results.clear();
                results.add(temp);
                saveInFile(getBaseContext());
                makeStartList(getBaseContext());
                saveInBuzzerFile(getBaseContext());
                displayStats(results);
                displayBuzzerStats();
            }
        });
        loadFromBuzzerFile(getBaseContext());
        loadFromFile(getBaseContext());
        results = sortStats.sortIt(oldTimesArray);
        displayBuzzerStats(); //will have to go into buzzer before can go into stats this way..
        displayStats(results); // THIS WILL HAVE TO END UP HERE
    }

    public StringBuilder getStatistics(){
        String startString = "Format: [Min, Max, Avg, Med] \n 10:";
        int counter = 0;
        StringBuilder statsString = new StringBuilder(startString);
        for(Object i: results) {
            if(counter == 4) {
                statsString.append("\n 100:");
            }
            if(counter == 8) {
                statsString.append("\n All:");
            }
            counter = counter + 1;
            statsString.append(i);
            statsString.append(", ");
        }
        return statsString;
    }

    //https://developer.android.com/guide/components/intents-common.html 10-04-15
    public void composeEmail(String subject, String info) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("text/plain");
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, info);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


    public void makeStartList(Context context) {
        players2 = new ArrayList();
        players3 = new ArrayList();
        players4 = new ArrayList();
        oldTimesArray = new ArrayList<>();
        oldWinnerList = new HashMap<>();
        results = new ArrayList<>(); //stores min,max,avg, med of each
        players2.add(0);
        players2.add(0);
        oldWinnerList.put("2player", players2);
        players3.add(0);
        players3.add(0);
        players3.add(0);
        oldWinnerList.put("3player", players3);
        players4.add(0);
        players4.add(0);
        players4.add(0);
        players4.add(0);
        oldWinnerList.put("4player", players4);
        saveInBuzzerFile(context);

    }
    //maybe i dont actually need this here? I'm really not even sure anymore..what is life?
    public void saveThatShit(Long saveIT, Context context) {
        if(oldTimesArray == null){
            makeStartList(context);
            saveInBuzzerFile(context);
        }
        loadFromFile(context);
        this.oldTimesArray.add(saveIT); //add the new item to the list
        saveInFile(context);

    }

    public void saveThatBuzzerShit(String playerMode, int winner, Context context) { //pass the winner in as the index so can use it as the index?
        if(oldWinnerList == null){
            makeStartList(context);
            saveInBuzzerFile(context);
        }
        loadFromBuzzerFile(context);
        ArrayList whoWon = oldWinnerList.get(playerMode);
        int previousWins;
        try {
            previousWins= (int) whoWon.get(winner) + 1;
        } catch (NullPointerException e) {
            previousWins = 1;
        }
        whoWon.set(winner, previousWins);
        oldWinnerList.put(playerMode,whoWon);
        saveInBuzzerFile(context);
    }
        public void loadFromFile(Context context) {
        try {
            FileInputStream fis = context.openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            //https://google-gson.googlecode.com/svn/trunk/gsn/docs/javadocs/com/google/gson/Gson.html, 2015-09-23l
            Type arrayListType = new TypeToken<ArrayList<Long>>() {}.getType();
            oldTimesArray = gson.fromJson(in, arrayListType);
            //displayStats(results);


        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            oldTimesArray = new ArrayList<>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
    }

    private void loadFromBuzzerFile(Context context) {
        try {
            FileInputStream fisB = context.openFileInput(BUZZERFILENAME);
            BufferedReader inB = new BufferedReader(new InputStreamReader(fisB));
            Gson gsonB = new Gson();
            //https://google-gson.googlecode.com/svn/trunk/gsn/docs/javadocs/com/google/gson/Gson.html, 2015-09-23l
            Type hashMapType = new TypeToken<HashMap<String, ArrayList<Integer>>>() {}.getType();
            oldWinnerList = gsonB.fromJson(inB, hashMapType);
            //resultsB = winTracker(oldWinnerList);
            //displayBuzzerStats();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            oldWinnerList = new HashMap<>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
    }

    //public ArrayList winTracker(HashMap oldWinnerList){

    //}
    public void displayBuzzerStats(){
        //wins for player 1 2player mode
        TextView textviewB = (TextView) findViewById(R.id.row2col2b);
        textviewB.setText(oldWinnerList.get("2player").get(0).toString());
        //wins for player 2 2player
        TextView textview1B = (TextView) findViewById(R.id.row3col2b);
        textview1B.setText(oldWinnerList.get("2player").get(1).toString());
        //wins for player 1 3 player mode

        TextView textview4B = (TextView) findViewById(R.id.row2col3b);
        textview4B.setText(oldWinnerList.get("3player").get(0).toString());
        //wins for player 2 3 player mode
        TextView textview5B = (TextView) findViewById(R.id.row3col3b);
        textview5B.setText(oldWinnerList.get("3player").get(1).toString());
        //wins for player 3 3 player mode
        TextView textview6B = (TextView) findViewById(R.id.row4col3b);
        textview6B.setText(oldWinnerList.get("3player").get(2).toString());

        //player 1 4 player mode
        TextView textview8B = (TextView) findViewById(R.id.row2col4b);
        textview8B.setText(oldWinnerList.get("4player").get(0).toString());
        //player 2 4 player mode
        TextView textview9B = (TextView) findViewById(R.id.row3col4b);
        textview9B.setText(oldWinnerList.get("4player").get(1).toString());
        //player 3 4 player mode
        TextView textview10B = (TextView) findViewById(R.id.row4col4b);
        textview10B.setText(oldWinnerList.get("4player").get(2).toString());
        //player 4 4 player mode
        TextView textview11B = (TextView) findViewById(R.id.row5col4b);
        textview11B.setText(oldWinnerList.get("4player").get(3).toString());
    }

    public void displayStats(ArrayList<Long> results) {
        if(results.size() < 12){
            int addFill = 12 - results.size();
            Long temp = Long.valueOf(0);
            for(int i = 0; i < addFill; i++){
                results.add(temp);
            }
        }
       //min10
        TextView textview = (TextView) findViewById(R.id.row2col2);
        textview.setText(results.get(0).toString());
        //max10
        TextView textview1 = (TextView) findViewById(R.id.row3col2);
        textview1.setText(results.get(1).toString());
        //avg10
        TextView textview2 = (TextView) findViewById(R.id.row4col2);
        textview2.setText(results.get(2).toString());
        //med10
        TextView textview3 = (TextView) findViewById(R.id.row5col2);
        textview3.setText(results.get(3).toString());
        //min100
        TextView textview4 = (TextView) findViewById(R.id.row2col3);
        textview4.setText(results.get(4).toString());
        //max100
        TextView textview5 = (TextView) findViewById(R.id.row3col3);
        textview5.setText(results.get(5).toString());
        //avg100
        TextView textview6 = (TextView) findViewById(R.id.row4col3);
        textview6.setText(results.get(6).toString());
        //med100
        TextView textview7 = (TextView) findViewById(R.id.row5col3);
        textview7.setText(results.get(7).toString());
        //minAll
        TextView textview8 = (TextView) findViewById(R.id.row2col4);
        textview8.setText(results.get(8).toString());
        //maxAll
        TextView textview9 = (TextView) findViewById(R.id.row3col4);
        textview9.setText(results.get(9).toString());
        //avgAll
        TextView textview10 = (TextView) findViewById(R.id.row4col4);
        textview10.setText(results.get(10).toString());
        //medAll
        TextView textview11 = (TextView) findViewById(R.id.row5col4);
        textview11.setText(results.get(11).toString());

    }

    private void saveInFile(Context context) {
        try {
            FileOutputStream fos = context.openFileOutput(FILENAME,Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(this.oldTimesArray, out);
            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
    }

    private void saveInBuzzerFile(Context context) {
        try {
            String hi = BUZZERFILENAME;
            FileOutputStream fosB = context.openFileOutput(BUZZERFILENAME,Context.MODE_PRIVATE);
            BufferedWriter outB = new BufferedWriter(new OutputStreamWriter(fosB));
            Gson gsonB = new Gson();
            gsonB.toJson(oldWinnerList, outB);
            outB.flush();
            fosB.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
    }



    //Save Buzzer info according to number of players



}
