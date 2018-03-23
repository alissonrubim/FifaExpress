package com.alissonrubim.fifaexpress;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.alissonrubim.fifaexpress.Model.DAO.FriendDAO;
import com.alissonrubim.fifaexpress.Model.DAO.TeamDAO;
import com.alissonrubim.fifaexpress.Model.Friend;
import com.alissonrubim.fifaexpress.Model.Team;

import java.util.ArrayList;

public class FriendDetailActivity extends AppCompatActivity {
    public static final int IntentId = 100;
    private Button buttonConfirm;
    private Button buttonCancel;
    private Spinner spinnerTeam;
    private EditText editTextName;
    private ArrayList<Team> teamsCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_detail);

        bindUI();

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm();
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

        fillSpinnerTeam();
    }

    private void fillSpinnerTeam(){
        teamsCache = (new TeamDAO(getApplicationContext())).GetAll();

        ArrayList<String> spinnerArray =  new ArrayList<String>();
        for (Team t:
                teamsCache) {
            spinnerArray.add(t.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTeam.setAdapter(adapter);
    }

    private void confirm(){
        Team team = teamsCache.get(spinnerTeam.getSelectedItemPosition());
        Friend friend = new Friend(-1, team, editTextName.getText().toString());
        (new FriendDAO(getApplicationContext())).Insert(friend);
        setResult(RESULT_OK);
        finish();
    }

    private void cancel(){
        setResult(RESULT_CANCELED);
        finish();
    }


    private void bindUI(){
        buttonConfirm = findViewById(R.id.buttonConfirm);
        spinnerTeam = findViewById(R.id.spinnerTeam);
        buttonCancel = findViewById(R.id.buttonCancel);
        editTextName = findViewById(R.id.editTextName);
    }
}
