package com.alissonrubim.fifaexpress;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alissonrubim.fifaexpress.Model.DAO.RoundMatchDAO;
import com.alissonrubim.fifaexpress.Model.RoundMatch;

public class RoundMatchResultActivity extends AppCompatActivity {
    public static int IntentId = 400;

    private Button buttonNextMatch;
    private Button buttonShowRoundResult;
    private TextView textViewGame;

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
            buttonNextMatch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goBack();
                }
            });

            if (!(new RoundMatchDAO(getApplicationContext())).HasNextRoundMatch(currentRoundMatch.getRound().getRoundId())) {
                buttonNextMatch.setVisibility(View.INVISIBLE);
                buttonShowRoundResult.setVisibility(View.VISIBLE);
            } else {
                buttonNextMatch.setVisibility(View.VISIBLE);
                buttonShowRoundResult.setVisibility(View.INVISIBLE);
            }

            textViewGame.setText("Rodada "+currentRoundMatch.getNumber()+" de " + (new RoundMatchDAO(getApplicationContext()).GetCounByRoundId(currentRoundMatch.getRound().getRoundId())));
        }
    }

    private void goBack(){
        setResult(RESULT_OK);
        finish();
    }

    private void bind(){
        buttonNextMatch = findViewById(R.id.buttonNextMatch);
        buttonShowRoundResult = findViewById(R.id.buttonShowRoundResult);
        textViewGame = findViewById(R.id.textViewGame);
    }
}
