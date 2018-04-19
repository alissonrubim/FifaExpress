package com.alissonrubim.fifaexpress;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alissonrubim.fifaexpress.Model.DAO.RoundMatchDAO;
import com.alissonrubim.fifaexpress.Model.Friend;
import com.alissonrubim.fifaexpress.Model.Round;
import com.alissonrubim.fifaexpress.Model.RoundMatch;

import java.util.ArrayList;

public class RoundResultActivity extends AppCompatActivity {

    private Button buttonGoBack;

    private Round currentRound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_result);

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

    }


    private void bind(){
        buttonGoBack = findViewById(R.id.buttonGoBack);
    }
}
