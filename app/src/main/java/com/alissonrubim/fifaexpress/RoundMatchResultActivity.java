package com.alissonrubim.fifaexpress;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alissonrubim.fifaexpress.Model.DAO.RoundMatchDAO;
import com.alissonrubim.fifaexpress.Model.DAO.RoundMatchGoalDAO;
import com.alissonrubim.fifaexpress.Model.Friend;
import com.alissonrubim.fifaexpress.Model.RoundMatch;

public class RoundMatchResultActivity extends AppCompatActivity {
    public static int IntentId = 400;

    private Button buttonGoBack;
    private TextView textViewGame;
    private TextView textViewScore;

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
        }
    }

    private void updateUI(){
        RoundMatchGoalDAO roundMatchGoalDAO = new RoundMatchGoalDAO(getApplicationContext());

        int goalsFriend01 = roundMatchGoalDAO.GetTotalGoalsByFriend(currentRoundMatch.getFriend1(), currentRoundMatch);
        int goalsFriend02 = roundMatchGoalDAO.GetTotalGoalsByFriend(currentRoundMatch.getFriend2(), currentRoundMatch);

        Friend friendWinner = null;
        Friend friendLoser = null;

        if(goalsFriend01 > goalsFriend02){

        }else if(goalsFriend01 < goalsFriend02){

        }else{
            //emapte
        }

        textViewGame.setText("Rodada "+currentRoundMatch.getNumber()+" de " + (new RoundMatchDAO(getApplicationContext()).GetCounByRoundId(currentRoundMatch.getRound().getRoundId())));
        textViewScore.setText(Integer.toString(goalsFriend01) + " x " + Integer.toString(goalsFriend02));
    }

    private void goBack(){
        setResult(RESULT_OK);
        finish();
    }

    private void bind(){
        buttonGoBack = findViewById(R.id.buttonGoBack);
        textViewGame = findViewById(R.id.textViewGame);
        textViewScore = findViewById(R.id.textViewScore);
    }
}
