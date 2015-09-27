package com.mycompany.assignment1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;

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


    //creates initial popip for the reaction timer game
    public void preReaction(Activity activity){
        AlertDialog.Builder popUp = new AlertDialog.Builder(activity);
        popUp.setMessage("When prompted to go click 'CLICK!' as quickly as you can!").setPositiveButton("Play now!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
    }
}
