package com.alissonrubim.fifaexpress;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alissonrubim.fifaexpress.Helper.TeamLogoCatcher;
import com.alissonrubim.fifaexpress.Model.DAO.RoundMatchDAO;
import com.alissonrubim.fifaexpress.Model.DAO.RoundMatchGoalDAO;
import com.alissonrubim.fifaexpress.Model.Friend;
import com.alissonrubim.fifaexpress.Model.RoundMatch;

import org.w3c.dom.Text;

public class RoundMatchResultActivity extends AppCompatActivity {
    public static int IntentId = 400;

    private Button buttonGoBack;
    private TextView textViewGame;
    private TextView textViewScore;
    private TextView textViewWinnerTitle;
    private TextView textViewLoserTitle;
    private TextView textViewWinnerFriend;
    private TextView textViewLoserFriend;
    private TextView textViewWinnerTeam;
    private TextView textViewLoserTeam;
    private TextView textViewWinnerPoints;
    private TextView textViewLoserPoints;
    private ImageView imageViewWinnerLogo;
    private ImageView imageViewLoserLogo;

    private RoundMatch currentRoundMatch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_match_result);

        bind();

        if(getIntent().getExtras().size() > 0){
            currentRoundMatch = (RoundMatch) getIntent().getSerializableExtra("RoundMatch");
        }

        if(currentRoundMatch == null){
            Toast.makeText(this, "Ops, RoundMatch não definido!", Toast.LENGTH_SHORT).show();
        }else {
            buttonGoBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goBack();
                }
            });

            updateUI();
        }
    }

    private void updateUI() {
        RoundMatchGoalDAO roundMatchGoalDAO = new RoundMatchGoalDAO(getApplicationContext());

        Friend friendWinner = currentRoundMatch.getFriend1();
        Friend friendLoser = currentRoundMatch.getFriend2();

        int goalsWinner = roundMatchGoalDAO.GetTotalGoalsByFriend(friendWinner, currentRoundMatch);
        int goalsLoser = roundMatchGoalDAO.GetTotalGoalsByFriend(friendLoser, currentRoundMatch);

        if (goalsWinner < goalsLoser) { //se for ao contrario, inverte tudo
            Friend aux = friendWinner;
            friendWinner = friendLoser;
            friendLoser = aux;

            int auxG = goalsWinner;
            goalsWinner = goalsLoser;
            goalsLoser = auxG;
        }

        textViewGame.setText("Rodada " + Integer.toString(currentRoundMatch.getNumber()) + " de " + Integer.toString(new RoundMatchDAO(getApplicationContext()).GetCounByRoundId(currentRoundMatch.getRound().getRoundId())));
        textViewScore.setText(Integer.toString(goalsWinner) + " x " + Integer.toString(goalsLoser));

        if (goalsWinner == goalsLoser) { //se estiver empatado
            textViewWinnerTitle.setText("Jogador"); //troca o text para Jogador
            textViewLoserTitle.setText("Jogador");
        }

        textViewWinnerFriend.setText(friendWinner.getName());
        textViewLoserFriend.setText(friendLoser.getName());

        textViewWinnerTeam.setText(friendWinner.getTeam().getName());
        textViewLoserTeam.setText(friendLoser.getTeam().getName());

        int pointsWinner = 0;
        int pointsLoser = 0;

        if (goalsWinner != goalsLoser) {
            pointsWinner = 5;
        }

        pointsWinner += (3 * goalsWinner) - (0.5 * goalsLoser);
        pointsLoser += (3 * goalsLoser) - (0.5 * goalsWinner);

        textViewWinnerPoints.setText((pointsWinner > -1 ? "+" : "") + pointsWinner + " pontos");
        textViewLoserPoints.setText((pointsLoser > -1 ? "+" : "") + pointsLoser + " pontos");

        imageViewWinnerLogo.setImageResource(TeamLogoCatcher.GetLogo(friendWinner.getTeam()));
        imageViewLoserLogo.setImageResource(TeamLogoCatcher.GetLogo(friendLoser.getTeam()));
    }

    private void goBack(){
        setResult(RESULT_OK);
        finish();
    }

    private void bind(){
        buttonGoBack = findViewById(R.id.buttonGoBack);
        textViewGame = findViewById(R.id.textViewGame);
        textViewScore = findViewById(R.id.textViewScore);
        textViewWinnerTitle = findViewById(R.id.textViewWinnerTitle);
        textViewLoserTitle = findViewById(R.id.textViewLoserTitle);
        textViewWinnerFriend = findViewById(R.id.textViewWinnerFriend);
        textViewLoserFriend = findViewById(R.id.textViewLoserFriend);
        textViewWinnerTeam = findViewById(R.id.textViewWinnerTeam);
        textViewLoserTeam = findViewById(R.id.textViewLoserTeam);
        textViewWinnerPoints = findViewById(R.id.textViewWinnerPoints);
        textViewLoserPoints = findViewById(R.id.textViewLoserPoints);
        imageViewWinnerLogo = findViewById(R.id.imageViewWinnerLogo);
        imageViewLoserLogo = findViewById(R.id.imageViewLoserLogo);

    }
}
