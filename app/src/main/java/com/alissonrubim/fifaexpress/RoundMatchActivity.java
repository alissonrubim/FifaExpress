package com.alissonrubim.fifaexpress;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alissonrubim.fifaexpress.Model.DAO.FriendDAO;
import com.alissonrubim.fifaexpress.Model.DAO.RoundDAO;
import com.alissonrubim.fifaexpress.Model.DAO.RoundMatchDAO;
import com.alissonrubim.fifaexpress.Model.DAO.RoundMatchGoalDAO;
import com.alissonrubim.fifaexpress.Model.Friend;
import com.alissonrubim.fifaexpress.Model.Round;
import com.alissonrubim.fifaexpress.Model.RoundMatch;

import java.util.ArrayList;
import java.util.Random;

public class RoundMatchActivity extends AppCompatActivity {

    private Button buttonFinish;
    private Button buttonGoal;
    private TextView textViewPlayer1Name;
    private TextView textViewPlayer1Goals;
    private TextView textViewPlayer2Goals;
    private TextView textViewGame;
    private TextView textViewPlayer2Name;
    public static int IntentId = 200;

    private RoundMatch currentRoundMatch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_match);

        bind();

        if(getIntent().getExtras().size() > 0){
            currentRoundMatch = (RoundMatch) getIntent().getSerializableExtra("RoundMatch");
        }

        if(currentRoundMatch == null){
            Toast.makeText(this, "Ops, RoundMatch não definido!", Toast.LENGTH_SHORT).show();
        }else {
            updateUI();

            buttonFinish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finishRoundMatch();
                }
            });

            buttonGoal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showRegisterGoalActivity();
                }
            });
        }
    }

    //Abre a activity de RegisterGoal
    private void showRegisterGoalActivity(){
        Intent intent = new Intent(getApplicationContext(), RegisterGoalActivity.class);
        intent.putExtra("RoundMatch", this.currentRoundMatch);
        startActivityForResult(intent, RegisterGoalActivity.IntentId);
    }

    private int getTotalGoalsByFriend(Friend friend){
        RoundMatchGoalDAO dao = new RoundMatchGoalDAO(getApplicationContext());
        return dao.GetTotalGoalsByFriend(friend, currentRoundMatch);
    }

    //Atualiza informações na tela
    private void updateUI(){
        textViewPlayer1Name.setText(currentRoundMatch.getFriend1().getName());
        textViewPlayer2Name.setText(currentRoundMatch.getFriend2().getName());
        textViewPlayer1Goals.setText(Integer.toString(getTotalGoalsByFriend(currentRoundMatch.getFriend1())));
        textViewPlayer2Goals.setText(Integer.toString(getTotalGoalsByFriend(currentRoundMatch.getFriend2())));
        textViewGame.setText("Rodada "+Integer.toString(currentRoundMatch.getNumber())+" de " + Integer.toString((new RoundMatchDAO(getApplicationContext()).GetCounByRoundId(currentRoundMatch.getRound().getRoundId()))));
    }

    private void bind(){
        buttonFinish = findViewById(R.id.buttonFinish);
        textViewPlayer1Name = findViewById(R.id.textViewPlayer1Name);
        textViewPlayer2Name = findViewById(R.id.textViewPlayer2Name);
        textViewGame = findViewById(R.id.textViewGame);
        buttonGoal = findViewById(R.id.buttonGoal);
        textViewPlayer1Goals = findViewById(R.id.textViewPlayer1Goals);
        textViewPlayer2Goals = findViewById(R.id.textViewPlayer2Goals);
    }


    //Ao terminar uma Partida
    private void finishRoundMatch(){
        currentRoundMatch.setFinished(true);
        (new RoundMatchDAO(getApplicationContext())).Update(currentRoundMatch);

        Intent intent = new Intent(getApplicationContext(), RoundMatchResultActivity.class);
        intent.putExtra("RoundMatch", this.currentRoundMatch);
        startActivityForResult(intent, RoundMatchResultActivity.IntentId);
        finish(); //Fecha este activity (impede o go back)
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RegisterGoalActivity.IntentId) { //Veio da tela de registro de gol
            if (resultCode == RESULT_OK) {
                updateUI();
            }
        }
    }
}
