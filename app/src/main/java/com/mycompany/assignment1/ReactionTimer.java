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

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class ReactionTimer extends AppCompatActivity implements Reaction{
    private Long time;
    Long totalTime;
    //public ReactionTimer(){}
    public Statistics stat = new Statistics();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //WinningPlayer winPlayer = new WinningPlayer();
        //yourTime = new ReactionTimer();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reaction_timer);
        preReaction();

        final AlertDialog.Builder popUp = new AlertDialog.Builder(this);
        popUp.setMessage("When prompted to go click 'CLICK!' as quickly as you can!").setPositiveButton("Play now!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                preReaction();
            }
        }).show();
    }

    public void setTime(Long time) {
        this.time = time;
    }
    public Long getTime() {
        return time;
    }

    //creates initial popup for the reaction timer game
    public void preReaction(){

                Random random = new Random();
                int min = 10;
                int max = 2000;
                int delay = random.nextInt(max - min) + min; //delay between min and max values
                final long delayTime = System.currentTimeMillis() + delay;

                //http://stackoverflow.com/questions/1520887/how-to-pause-sleep-thread-or-process-in-android 2015-09-27
                final Handler handler = new Handler();
                final Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    public void run() {
                        handler.post(new Runnable() {
                            public void run() {
                                TextView textview = (TextView) findViewById(R.id.textView5);
                                textview.setText("CLICK NOW!");
                                long start = System.currentTimeMillis();
                                if (delayTime > start) {
                                    //stop the timer
                                    timer.cancel();
                                    complain();
                                } else {
                                    displayReactionTime(start, timer);
                                }

                            }
                        });
                    }
                }, delay);

    }

    //calculate the time between message displayed and user click
    public void displayReactionTime(final long begin, final Timer timer) {
        Button button = (Button) findViewById(R.id.button15);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Long end = System.currentTimeMillis();
                totalTime = end - begin;
                setTime(totalTime);
                //stop the timer
                timer.cancel();
                reactionTimePopUp(totalTime);


            }
        });

    }

    //display pop u box with reaction time results
    public void reactionTimePopUp(Long totalTime){
        stat.saveThatShit(totalTime, this.getBaseContext());
        AlertDialog.Builder popUp  = new AlertDialog.Builder(this);
        popUp.setMessage("Your reaction time was " + totalTime + " ms! ").setPositiveButton("Try again!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //reset to play again
                resetIt();
            }
        }).show();
    }

    public void resetIt(){
        //reset the original text
        TextView textview = (TextView) findViewById(R.id.textView5);
        textview.setText("get Ready..");
        preReaction();


    }

    public void complain(){
        //complain to the user for clicking to soon
        //LATER WILL HAVE TO ACCOUNT FOR THIS IN HOW THE STATS ARE RECORDED
        TextView textview = (TextView) findViewById(R.id.textView5);
        textview.setText("You clicked too soon..try again without cheating! Get ready..");
        //start over
        preReaction();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reaction_timer, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
