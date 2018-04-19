package com.alissonrubim.fifaexpress;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alissonrubim.fifaexpress.Model.DAO.RoundMatchDAO;
import com.alissonrubim.fifaexpress.Model.Friend;
import com.alissonrubim.fifaexpress.Model.Round;
import com.alissonrubim.fifaexpress.Model.RoundMatch;

import java.util.ArrayList;

public class RoundResultActivity extends AppCompatActivity {

    private Button buttonGoBack;

    private Round currentRound;

    private TextView textViewFriendName;
    private TextView textViewWinnerPoints;
    private TextView textViewWinnerGoals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_result);

        currentRound = (Round) getIntent().getExtras().getSerializable("Round");

        bind();

        updateUI();

        buttonGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
    }

    private void goBack(){
        Intent intent = new Intent(getApplicationContext(), FriendActivity.class);
        startActivity(intent);
        finish();
    }

    private void updateUI() {
        Round.RoundResult result =  currentRound.GetResult(getApplicationContext());

        textViewFriendName.setText(result.Winner.getName());
        textViewWinnerPoints.setText(textViewWinnerPoints.getText().toString().replace("$points", Integer.toString(result.WinnerPoints)));
        textViewWinnerGoals.setText(textViewWinnerGoals.getText().toString().replace("$goals", Integer.toString(result.WinnerGoals)));
    }


    private void bind(){
        buttonGoBack = findViewById(R.id.buttonGoBack);
        textViewFriendName = findViewById(R.id.textViewFriendName);
        textViewWinnerPoints = findViewById(R.id.textViewWinnerPoints);
        textViewWinnerGoals = findViewById(R.id.textViewWinnerGoals);
    }
}
