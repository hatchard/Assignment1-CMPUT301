package com.mycompany.assignment1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by Me on 2015-09-26.
 */
public class WinningPlayer {
    //creates pop ups for whichever player clicks the gameshow buzzer first
    public void popUpBox(Activity activity, String winner){
        AlertDialog.Builder popUp  = new AlertDialog.Builder(activity);
        popUp.setMessage(winner + " wins!").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
    }




}
