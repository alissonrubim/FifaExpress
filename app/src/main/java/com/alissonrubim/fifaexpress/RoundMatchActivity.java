package com.alissonrubim.fifaexpress;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alissonrubim.fifaexpress.Helper.GoalDialog;
import com.alissonrubim.fifaexpress.Helper.TeamLogoCatcher;
import com.alissonrubim.fifaexpress.Model.DAO.FriendDAO;
import com.alissonrubim.fifaexpress.Model.DAO.RoundDAO;
import com.alissonrubim.fifaexpress.Model.DAO.RoundMatchDAO;
import com.alissonrubim.fifaexpress.Model.DAO.RoundMatchGoalDAO;
import com.alissonrubim.fifaexpress.Model.Friend;
import com.alissonrubim.fifaexpress.Model.Player;
import com.alissonrubim.fifaexpress.Model.Round;
import com.alissonrubim.fifaexpress.Model.RoundMatch;

import java.util.ArrayList;
import java.util.Random;

public class RoundMatchActivity extends AppCompatActivity {

    private Button buttonFinish;
    private TextView textViewPlayer1Name;
    private TextView textViewPlayer1Goals;
    private TextView textViewFriend1Team;
    private ImageView imageViewFriend1TeamLogo;
    private ImageView imageViewFriend2TeamLogo;
    private TextView textViewPlayer2Goals;
    private TextView textViewFriend2Team;
    private TextView textViewGame;
    private TextView textViewPlayer2Name;
    private ImageView imageViewFriend1RemoveGoal;
    private ImageView imageViewFriend2RemoveGoal;
    private ImageView imageViewFriend1AddGoal;
    private ImageView imageViewFriend2AddGoal;


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

            imageViewFriend1AddGoal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addGoal(currentRoundMatch.getFriend1());
                }
            });

            imageViewFriend2AddGoal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addGoal(currentRoundMatch.getFriend2());
                }
            });

            imageViewFriend1RemoveGoal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeGoal(currentRoundMatch.getFriend1());
                }
            });

            imageViewFriend2RemoveGoal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeGoal(currentRoundMatch.getFriend2());
                }
            });
        }
    }

    private void addGoal(Friend friend){
        GoalDialog dialog = new GoalDialog(this, "Registrar GOL", friend) {
            @Override
            public void OnConfirm(Player player){
                (new RoundMatchGoalDAO(getApplicationContext())).Insert(player, currentRoundMatch);
                super.OnConfirm(player);
                updateUI();
            }
        };
        dialog.Show();
    }

    private void removeGoal(Friend friend){
        if(getTotalGoalsByFriend(friend) > 0) {
            GoalDialog dialog = new GoalDialog(this, "Remover GOL", friend) {
                @Override
                public void OnConfirm(Player player) {
                    (new RoundMatchGoalDAO(getApplicationContext())).Delete(player, currentRoundMatch);
                    super.OnConfirm(player);
                    updateUI();
                }
            };
            dialog.Show();
        }else{
            Toast.makeText(getApplicationContext(), "Ainda não foram registrados gols para esse time", Toast.LENGTH_LONG);
        }
    }

    private int getTotalGoalsByFriend(Friend friend){
        RoundMatchGoalDAO dao = new RoundMatchGoalDAO(getApplicationContext());
        return dao.GetTotalGoalsByFriend(friend, currentRoundMatch);
    }

    //Atualiza informações na tela
    private void updateUI(){
        textViewPlayer1Name.setText(currentRoundMatch.getFriend1().getName());
        textViewPlayer2Name.setText(currentRoundMatch.getFriend2().getName());
        imageViewFriend1TeamLogo.setImageResource(TeamLogoCatcher.GetLogo(currentRoundMatch.getFriend1().getTeam()));
        imageViewFriend2TeamLogo.setImageResource(TeamLogoCatcher.GetLogo(currentRoundMatch.getFriend2().getTeam()));
        textViewPlayer1Goals.setText(Integer.toString(getTotalGoalsByFriend(currentRoundMatch.getFriend1())));
        textViewPlayer2Goals.setText(Integer.toString(getTotalGoalsByFriend(currentRoundMatch.getFriend2())));
        textViewFriend1Team.setText(currentRoundMatch.getFriend1().getTeam().getName());
        textViewFriend2Team.setText(currentRoundMatch.getFriend2().getTeam().getName());
        textViewGame.setText("Rodada "+Integer.toString(currentRoundMatch.getNumber())+" de " + Integer.toString((new RoundMatchDAO(getApplicationContext()).GetCounByRoundId(currentRoundMatch.getRound().getRoundId()))));
    }

    private void bind(){
        buttonFinish = findViewById(R.id.buttonFinish);
        textViewPlayer1Name = findViewById(R.id.textViewPlayer1Name);
        textViewPlayer2Name = findViewById(R.id.textViewPlayer2Name);
        textViewGame = findViewById(R.id.textViewGame);
        textViewPlayer1Goals = findViewById(R.id.textViewPlayer1Goals);
        textViewPlayer2Goals = findViewById(R.id.textViewPlayer2Goals);
        imageViewFriend1TeamLogo = findViewById(R.id.imageViewFriend1TeamLogo);
        imageViewFriend2TeamLogo = findViewById(R.id.imageViewFriend2TeamLogo);
        textViewFriend1Team = findViewById(R.id.textViewFriend1Team);
        textViewFriend2Team = findViewById(R.id.textViewFriend2Team);
        imageViewFriend1RemoveGoal = findViewById(R.id.imageViewFriend1RemoveGoal);
        imageViewFriend2RemoveGoal = findViewById(R.id.imageViewFriend2RemoveGoal);
        imageViewFriend1AddGoal = findViewById(R.id.imageViewFriend1AddGoal);
        imageViewFriend2AddGoal = findViewById(R.id.imageViewFriend2AddGoal);
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
}
