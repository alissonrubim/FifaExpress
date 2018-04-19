package com.alissonrubim.fifaexpress.Helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.alissonrubim.fifaexpress.Model.DAO.PlayerDAO;
import com.alissonrubim.fifaexpress.Model.Friend;
import com.alissonrubim.fifaexpress.Model.Player;
import com.alissonrubim.fifaexpress.R;

import java.util.ArrayList;

/**
 * Created by alissonrubim on 13/04/2018.
 */

public class GoalDialog  {
    private LayoutInflater layoutInflater;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog alert;
    private View view;
    private String title;
    private Spinner spinnerPlayers;
    private Activity activity;
    private ArrayList<Player> currentPlayerList;
    private Button buttonConfirm;

    public GoalDialog(Activity activity, String title, Friend friend){
        this.activity = activity;
        this.title = title;

        dialogBuilder = new AlertDialog.Builder(activity);
        layoutInflater = LayoutInflater.from(activity.getApplicationContext());
        view = layoutInflater.inflate(R.layout.layout_goals_dialog, null);
        dialogBuilder.setView(view);

        spinnerPlayers = (Spinner) view.findViewById(R.id.spinnerPlayers);
        buttonConfirm = (Button) view.findViewById(R.id.buttonConfirm);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnConfirm(currentPlayerList.get(spinnerPlayers.getSelectedItemPosition()));
            }
        });

        loadPlayersSpinner(friend);
    }

    public void OnConfirm(Player p){
        //@Override this
        Close();
    }

    private void loadPlayersSpinner(Friend friend){
        currentPlayerList = (new PlayerDAO(activity.getApplicationContext())).GetAllByTeam(friend.getTeam());

        ArrayList<String> spinnerArray =  new ArrayList<String>();
        for(Player f : currentPlayerList){
            spinnerArray.add(f.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                activity, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPlayers.setAdapter(adapter);
    }

    public void SetTitle(String title){
        this.title = title;
    }

    public void Show(){
        alert = dialogBuilder.create();
        alert.setTitle(title);
        alert.show();
    }

    public void Close(){
        if(alert != null){
            alert.hide();
        }
    }
}


