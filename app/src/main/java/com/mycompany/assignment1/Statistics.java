package com.mycompany.assignment1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
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

public class Statistics extends AppCompatActivity {
    //Save Reaction Times
/*
Reaction time stat requirements:
minimum time of all reaction times, the last 10 times, and the last 100 times.
maximum time of all reaction times, the last 10 times, and the last 100 times.
average time of all reaction times, the last 10 times, and the last 100 times.
median time of all reaction times, the last 10 times, and the last 100 times.
*/

    private static final String FILENAME = "reactionTimerStats";
    private ArrayList<ReactionTimer> times = new ArrayList<>(); //took out specifics for now
    private  ArrayAdapter<ReactionTimer> adapter;
    private TableLayout results; //not so sure??

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        Button emailButton = (Button) findViewById(R.id.email);
        Button clearButton = (Button) findViewById(R.id.clear);
        results = (TableLayout) findViewById(R.id.table); //table??

        /*
        emailButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                String text = bodyText.getText().toString();
                tweets.add(new NormalTweet(text));
                saveInFile();
                adapter.notifyDataSetChanged();
            }
        });*/

        clearButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                times.clear();
                saveInFile();
                adapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        loadFromFile();
    }

    private void loadFromFile() {

        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            //https://google-gson.googlecode.com/svn/trunk/gsn/docs/javadocs/com/google/gson/Gson.html, 2015-09-23l
            Type arrayListType = new TypeToken<ArrayList<ReactionTimer>>() {}.getType();
            times = gson.fromJson(in,arrayListType);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            times = new ArrayList<>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
    }



    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,0);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(times, out);
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





    //Save Buzzer info according to number of players



}
