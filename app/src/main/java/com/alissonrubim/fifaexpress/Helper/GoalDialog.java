package com.alissonrubim.fifaexpress.Helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.alissonrubim.fifaexpress.Model.Friend;
import com.alissonrubim.fifaexpress.R;

/**
 * Created by alissonrubim on 13/04/2018.
 */

public class GoalDialog  {
    private LayoutInflater layoutInflater;
    private AlertDialog.Builder dialogBuilder;
    private View view;
    private String title;
    private Spinner spinnerPlayers;

    public GoalDialog(Activity activity){
        dialogBuilder = new AlertDialog.Builder(activity);
        layoutInflater = LayoutInflater.from(activity.getApplicationContext());

        view = layoutInflater.inflate(R.layout.layout_goals_dialog, null);
        dialogBuilder.setView(view);

        spinnerPlayers = (Spinner) view.findViewById(R.id.spinnerPlayers);

        /*final EditText edt = (EditText) dialogView.findViewById(R.id.edit1);

        dialogBuilder.setTitle("Custom dialog");
        dialogBuilder.setMessage("Enter text below");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });*/

    }

    public void LoadPlayersSpinner(Friend friend){

    }

    public void SetTitle(String title){
        this.title = title;
    }

    public void Show(){
        AlertDialog alert = dialogBuilder.create();
        alert.setTitle(title);
        alert.show();
    }
}


