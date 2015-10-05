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
import android.content.DialogInterface;
import android.os.Handler;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by Me on 2015-09-26.
 */
//tells who won for the multiplayer modes
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
