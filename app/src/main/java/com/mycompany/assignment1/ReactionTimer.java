package com.mycompany.assignment1;

import android.app.Activity;
import android.app.AlertDialog;
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
    public ReactionTimer(){}

    public ReactionTimer(Long time){
        this.setTime(time);
    }

    public void setTime(Long time) {
        this.time = time;
    }
    public Long getTime() {
        return time;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        WinningPlayer winPlayer = new WinningPlayer();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reaction_timer);
        preReaction(this);

    }

    //creates initial popup for the reaction timer game
    public void preReaction(Activity activity){
        AlertDialog.Builder popUp = new AlertDialog.Builder(activity);
        popUp.setMessage("When prompted to go click 'CLICK!' as quickly as you can!").setPositiveButton("Play now!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
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
        }).show();
    }

    //calculate the time between message displayed and user click
    public void displayReactionTime(final long begin, final Timer timer) {
        Button button = (Button) findViewById(R.id.button15);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long end = System.currentTimeMillis();
                long totalTime = end - begin;

                //stop the timer
                timer.cancel();
                reactionTimePopUp(totalTime);


            }
        });
    }

    //display pop u box with reaction time results
    public void reactionTimePopUp(Long totalTime){
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
        preReaction(this);


    }

    public void complain(){
        //complain to the user for clicking to soon
        //LATER WILL HAVE TO ACCOUNT FOR THIS IN HOW THE STATS ARE RECORDED
        TextView textview = (TextView) findViewById(R.id.textView5);
        textview.setText("You clicked too soon..try again without cheating! Get ready..");
        //start over
        preReaction(this);
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
