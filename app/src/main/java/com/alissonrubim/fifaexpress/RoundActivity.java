package com.alissonrubim.fifaexpress;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.alissonrubim.fifaexpress.Model.DAO.FriendDAO;
import com.alissonrubim.fifaexpress.Model.DAO.RoundDAO;
import com.alissonrubim.fifaexpress.Model.DAO.RoundMatchDAO;
import com.alissonrubim.fifaexpress.Model.Friend;
import com.alissonrubim.fifaexpress.Model.Round;
import com.alissonrubim.fifaexpress.Model.RoundMatch;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class RoundActivity extends AppCompatActivity {

    public static int IntentId = 200;

    private Round currentRound;
    private ArrayList<RoundMatch> currentRoundMatchs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round);

        if(!loadRound()){
            createRound();
        }
    }


    private boolean loadRound(){ //Carrega uma rodada que nao esteja finalizada
        currentRound = (new RoundDAO(getApplicationContext())).GetNotFinished();

        if(currentRound == null) {
            return false;
        }else{
            return true;
        }
    }

    private Friend getRandonFriend(ArrayList<Friend> allFriends, boolean delete){
        int  n = 0;
        if(allFriends.size() > 1){
            Random rand = new Random();
            n = rand.nextInt(allFriends.size() - 1);
        }
        Friend f = allFriends.get(n);
        if (delete)
            allFriends.remove(f);
        return f;
    }

    private void goBack(){ //Volta pra outra tela
        setResult(RESULT_CANCELED);
        finish();
    }


    private void createRound(){ //Cria uma rodada nova
        ArrayList<Friend> allFriends = (new FriendDAO(getApplicationContext())).GetAll();
        if(allFriends.size() < 2){
            Toast.makeText(this, "Número de jogadores insuficientes para iniciar um torneio.", Toast.LENGTH_SHORT).show();
            goBack();
        }else {
            if (allFriends.size() % 2 != 0) { //remove jogador IMPAR
                Friend removedFriend = getRandonFriend(allFriends, true);
                Toast.makeText(this, "O jogador " + removedFriend.getName() + " foi removido do torneio!", Toast.LENGTH_SHORT).show();
            }

            Toast.makeText(this, "Sorteando jogadores...", Toast.LENGTH_SHORT).show();

            currentRound = new Round(-1, false);
            (new RoundDAO(getApplicationContext())).Insert(currentRound);

            currentRoundMatchs = new ArrayList<RoundMatch>(); //Gera os Matchs dos jogadores
            for (int i = 0; i < allFriends.size() / 2; i++) {
                Friend f1 = getRandonFriend(allFriends, true);
                Friend f2 = getRandonFriend(allFriends, true);
                currentRoundMatchs.add(new RoundMatch(-1, currentRound, f1, f2, false));
            }

            for (RoundMatch m : currentRoundMatchs
                    ) {
                (new RoundMatchDAO(getApplicationContext())).Insert(m);
            }
        }
    }


}
