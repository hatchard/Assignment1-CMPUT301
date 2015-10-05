package com.mycompany.assignment1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Players2 extends AppCompatActivity  {
    public Statistics stat = new Statistics();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_players2, menu);
        return true;
    }

    //Try adding a message attribute to html on click somehow
    //
    public void showWinner(View view) {
        WinningPlayer winPlayer = new WinningPlayer();
        Button button = (Button)view;
        String winner = button.getText().toString();
        winPlayer.popUpBox(this, winner);
        int playerNum;
        if(winner.contains("1")){ playerNum = 0; }
        else{ playerNum = 1;}
        stat.saveThatBuzzerShit("2player", playerNum, this.getBaseContext());

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
