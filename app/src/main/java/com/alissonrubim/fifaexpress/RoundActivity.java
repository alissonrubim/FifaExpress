package com.alissonrubim.fifaexpress;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.alissonrubim.fifaexpress.Helper.RoundListViewAdapter;
import com.alissonrubim.fifaexpress.Model.DAO.FriendDAO;
import com.alissonrubim.fifaexpress.Model.DAO.RoundDAO;
import com.alissonrubim.fifaexpress.Model.DAO.RoundMatchDAO;
import com.alissonrubim.fifaexpress.Model.Friend;
import com.alissonrubim.fifaexpress.Model.Round;
import com.alissonrubim.fifaexpress.Model.RoundMatch;

import java.util.ArrayList;
import java.util.Random;

public class RoundActivity extends AppCompatActivity {
    public static final int IntentId = 700;

    private Round currentRound;
    private Button buttonFinishRound;
    private ListView listViewResume;
    private ArrayList<RoundMatch> listRounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round);

        bind();

        if(!loadRound()){
            createRound();
        }

        listViewResume.setOnItemClickListener(new AdapterView.OnItemClickListener() {
              @Override
              public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                  showRoundMatch(listRounds.get(position));
              }
        });

        buttonFinishRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishRound();
            }
        });

        fillListViewResume();
    }

    private void bind(){
        listViewResume = findViewById(R.id.listViewResume);
        buttonFinishRound = findViewById(R.id.buttonFinishRound);
    }

    private void showRoundMatch(RoundMatch roundMatch){
        if(roundMatch.isFinished()){
            Toast.makeText(this, "Esta rodada já foi finalizada!", Toast.LENGTH_SHORT).show();
        }else {
            Intent intent = new Intent(getApplicationContext(), RoundMatchActivity.class);
            intent.putExtra("RoundMatch", roundMatch);
            startActivityForResult(intent, RoundMatchActivity.IntentId);
        }
    }

    //Preenche a lista resumo com os rounds
    private void fillListViewResume(){
        listRounds = (new RoundMatchDAO(getApplicationContext())).GetAllByRoundId(currentRound.getRoundId());
        RoundListViewAdapter adapter = new RoundListViewAdapter(getApplicationContext(), listRounds);
        listViewResume.setAdapter(adapter);
    }

    //Volta pra outra tela
    private void goBack(){
        setResult(RESULT_CANCELED);
        finish();
    }

    //Pega um Amigo randomicamente
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

    //Carrega uma rodada que nao esteja finalizada
    private boolean loadRound(){
        currentRound = (new RoundDAO(getApplicationContext())).GetNotFinished();

        if(currentRound == null) {
            return false;
        }else{
            return true;
        }
    }

    //Cria uma rodada nova
    private void createRound(){
        ArrayList<Friend> allFriends = (new FriendDAO(getApplicationContext())).GetAll();
        if(allFriends.size() < 2){
            Toast.makeText(this, "Número de jogadores insuficientes para iniciar um torneio.", Toast.LENGTH_SHORT).show();
            goBack();
        }else {
            if (allFriends.size() % 2 != 0) { //remove jogador IMPAR
                Friend removedFriend = getRandonFriend(allFriends, true);
                Toast.makeText(this, "O jogador " + removedFriend.getName() + " foi removido do torneio!", Toast.LENGTH_SHORT).show();
            }

            currentRound = new Round(-1, false);
            (new RoundDAO(getApplicationContext())).Insert(currentRound);

            ArrayList<RoundMatch> roundMatchs = new ArrayList<RoundMatch>(); //Gera os Matchs dos jogadores
            int originalFriendSize = allFriends.size() / 2;
            for (int i = 0; i < originalFriendSize; i++) {
                Friend f1 = getRandonFriend(allFriends, true);
                Friend f2 = getRandonFriend(allFriends, true);
                roundMatchs.add(new RoundMatch(-1, currentRound, f1, f2, (i+1), false));
            }

            for (RoundMatch m : roundMatchs
                    ) {
                (new RoundMatchDAO(getApplicationContext())).Insert(m);
            }
        }
    }

    //finaliza a rodada e vai para a tela de RoundResult
    private void finishRound(){
        currentRound.setFinished(true);
        (new RoundDAO(getApplicationContext())).Update(currentRound);
        Intent intent = new Intent(getApplicationContext(), RoundResultActivity.class);
        intent.putExtra("Round", currentRound);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        fillListViewResume();
    }
}
