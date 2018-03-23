package com.alissonrubim.fifaexpress;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.alissonrubim.fifaexpress.Model.DAO.TeamDAO;
import com.alissonrubim.fifaexpress.Model.Team;

import java.util.ArrayList;

public class FriendDetailActivity extends AppCompatActivity {
    public static final int IntentId = 100;
    private Button buttonConfirm;
    private Spinner spinnerTeam;

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

        fillSpinnerTeam();
    }

    private void fillSpinnerTeam(){
        ArrayList<Team> teams = (new TeamDAO(getApplicationContext())).GetAll();

        ArrayList<String> spinnerArray =  new ArrayList<String>();
        for (Team t:
             teams) {
            spinnerArray.add(t.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTeam.setAdapter(adapter);
    }

    private void confirm(){
        setResult(RESULT_OK);
        finish();
    }

    private void bindUI(){
        buttonConfirm = findViewById(R.id.buttonConfirm);
        spinnerTeam = findViewById(R.id.spinnerTeam);
    }
}
