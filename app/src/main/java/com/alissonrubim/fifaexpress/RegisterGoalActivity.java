package com.alissonrubim.fifaexpress;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.alissonrubim.fifaexpress.Model.DAO.PlayerDAO;
import com.alissonrubim.fifaexpress.Model.DAO.RoundMatchGoalDAO;
import com.alissonrubim.fifaexpress.Model.Friend;
import com.alissonrubim.fifaexpress.Model.Player;
import com.alissonrubim.fifaexpress.Model.RoundMatch;

import java.io.IOException;
import java.util.ArrayList;

public class RegisterGoalActivity extends AppCompatActivity {
    public static final int IntentId = 400;

    private Spinner spinnerFriend;
    private Spinner spinnerPlayer;
    private Button buttonConfirm;

    private RoundMatch currentRoundMatch;
    private ArrayList<Friend> currentFriendList;
    private ArrayList<Player> currentPlayerList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_goal);

        bind();

        if(getIntent().getExtras().size() > 0){
            currentRoundMatch = (RoundMatch) getIntent().getSerializableExtra("RoundMatch");
        }

        if(currentRoundMatch == null){
            Toast.makeText(this, "Ops, RoundMatch não definido!", Toast.LENGTH_SHORT).show();
        }else{
            loadFriendSpinner();

            spinnerFriend.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Message message = new Message();
                    message.obj = currentFriendList.get(position);
                    eventHandle.sendMessage(message);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    return;
                }
            });

            buttonConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    register();
                }
            });
        }
    }

    private void bind(){
        spinnerFriend = findViewById(R.id.spinnerFriend);
        spinnerPlayer = findViewById(R.id.spinnerPlayer);
        buttonConfirm = findViewById(R.id.buttonConfirm);
    }

    private void register(){
        (new RoundMatchGoalDAO(getApplicationContext())).Insert(currentPlayerList.get(spinnerPlayer.getSelectedItemPosition()), currentRoundMatch);
        setResult(RESULT_OK);
        finish();
    }

    private void loadFriendSpinner(){
        currentFriendList = new ArrayList<>();
        currentFriendList.add(currentRoundMatch.getFriend1());
        currentFriendList.add(currentRoundMatch.getFriend2());

        ArrayList<String> spinnerArray =  new ArrayList<String>();
        for(Friend f : currentFriendList){
            spinnerArray.add(f.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFriend.setAdapter(adapter);
    }

    private void loadTeamSpinner(Friend friend){
        currentPlayerList = (new PlayerDAO(getApplicationContext())).GetAllByTeam(friend.getTeam());

        ArrayList<String> spinnerArray =  new ArrayList<String>();
        for(Player f : currentPlayerList){
            spinnerArray.add(f.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPlayer.setAdapter(adapter);
    }


    private Handler eventHandle = new Handler(){
        @Override
        public void handleMessage(Message message) {
            loadTeamSpinner((Friend) message.obj);
        }
    };
}
