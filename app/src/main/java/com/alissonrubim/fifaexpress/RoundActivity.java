package com.alissonrubim.fifaexpress;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.alissonrubim.fifaexpress.Model.DAO.FriendDAO;
import com.alissonrubim.fifaexpress.Model.Friend;

import java.util.ArrayList;
import java.util.Random;

public class RoundActivity extends AppCompatActivity {

    public static int IntentId = 200;

    private ArrayList<Friend> allFriends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round);

        Toast.makeText(this, "Sorteando jogadores...", Toast.LENGTH_SHORT).show();

        allFriends = (new FriendDAO(getApplicationContext())).GetAll();
        if(allFriends.size() % 2 != 0){
            removeOddPlayer();
        }

    }

    private void removeOddPlayer(){
        Random rand = new Random();
        int  n = rand.nextInt(allFriends.size() - 1);
        Friend removedFriend = allFriends.get(n);
        Toast.makeText(this, "O jogador " + removedFriend.getName() + " foi removido do torneio!", Toast.LENGTH_SHORT).show();
        allFriends.remove(n);
    }


}
