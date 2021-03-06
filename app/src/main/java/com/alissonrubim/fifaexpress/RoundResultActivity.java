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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RoundResultActivity extends AppCompatActivity {

    private Button buttonGoBack;

    private Round currentRound;

    private TextView textViewFriendName;
    private TextView textViewWinnerPoints;
    private TextView textViewWinnerGoals;
    private TextView textViewTopScorer;
    private Button buttonRanking;

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

        buttonRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRankActivity();
            }
        });
    }

    private void goBack(){
        Intent intent = new Intent(getApplicationContext(), FriendActivity.class);
        startActivity(intent);
        finish();
    }

    private void showRankActivity(){
        Intent intent = new Intent(getApplicationContext(), RankActivity.class);
        intent.putExtra("Round", currentRound);
        startActivity(intent);
    }

    private void updateUI() {
        Round.RoundResult result =  currentRound.GetResult(getApplicationContext());

        Round.RoundResultPosition winner = result.Positions.get(0);

        textViewFriendName.setText(winner.Friend.getName());
        textViewWinnerPoints.setText(textViewWinnerPoints.getText().toString().replace("$points", Double.toString(winner.Points)));
        textViewWinnerGoals.setText(textViewWinnerGoals.getText().toString().replace("$goals", Integer.toString(winner.Goals)));
        textViewTopScorer.setText(result.TopScorer.Player.getName()  + " foi o artilheiro com " + result.TopScorer.Goals + " gols!");
    }


    private void bind(){
        buttonGoBack = findViewById(R.id.buttonGoBack);
        textViewFriendName = findViewById(R.id.textViewFriendName);
        textViewWinnerPoints = findViewById(R.id.textViewWinnerPoints);
        textViewWinnerGoals = findViewById(R.id.textViewWinnerGoals);
        buttonRanking = findViewById(R.id.buttonRanking);
        textViewTopScorer = findViewById(R.id.textViewTopScorer);
    }
}
