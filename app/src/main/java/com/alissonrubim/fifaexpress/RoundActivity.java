package com.alissonrubim.fifaexpress;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.Date;
import java.util.Random;

public class RoundActivity extends AppCompatActivity {

    private Button buttonFinish;
    private Button buttonGoal;
    private TextView textViewPlayer1Name;
    private TextView textViewPlayer1Goals;
    private TextView textViewPlayer2Goals;
    private TextView textViewGame;
    private TextView textViewPlayer2Name;
    public static int IntentId = 200;

    private Round currentRound;
    private RoundMatch currentRoundMatch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round);

        bind();

        updateUI();

        if(!loadRound()){
            createRound();
        }

        startNextRound();

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

    //Abre a activity de RegisterGoal
    private void showRegisterGoalActivity(){
        Intent intent = new Intent(getApplicationContext(), RegisterGoalActivity.class);
        intent.putExtra("RoundMatch", this.currentRoundMatch);
        startActivityForResult(intent, RegisterGoalActivity.IntentId);
    }

    //Pega a proxima rodada a ser iniciada (null caso nao tenha)
    private RoundMatch getNextRoundMatch(){
        return (new RoundMatchDAO(getApplicationContext())).GetNextByRoundId(currentRound.getRoundId());
    }

    //Inicia a proxima rodada
    private void startNextRound(){
        currentRoundMatch = getNextRoundMatch();
        if(currentRoundMatch != null){
            updateUI();
        }else{
            finishRound();
        }
    }

    private int getTotalGoalsByFriend(Friend friend){
        RoundMatchGoalDAO dao = new RoundMatchGoalDAO(getApplicationContext());
        return dao.GetTotalGoalsByFriend(friend, currentRoundMatch);
    }

    //Atualiza informações na tela
    private void updateUI(){
        if(currentRoundMatch != null)
        {
            textViewPlayer1Name.setText(currentRoundMatch.getFriend1().getName());
            textViewPlayer2Name.setText(currentRoundMatch.getFriend2().getName());
            textViewPlayer1Goals.setText(Integer.toString(getTotalGoalsByFriend(currentRoundMatch.getFriend1())));
            textViewPlayer2Goals.setText(Integer.toString(getTotalGoalsByFriend(currentRoundMatch.getFriend2())));
            textViewGame.setText("Rodada "+currentRoundMatch.getNumber()+" de " + (new RoundMatchDAO(getApplicationContext()).GetCounByRoundId(currentRound.getRoundId())));
        }
        else
        {
            textViewPlayer1Name.setText("?");
            textViewPlayer2Name.setText("?");
            textViewPlayer1Goals.setText("0");
            textViewPlayer2Goals.setText("0");
            textViewGame.setText("Rodada 0 de 0");
        }
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

    //Carrega uma rodada que nao esteja finalizada
    private boolean loadRound(){
        currentRound = (new RoundDAO(getApplicationContext())).GetNotFinished();

        if(currentRound == null) {
            return false;
        }else{
            return true;
        }
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

    //Ao terminar uma Partida
    private void finishRoundMatch(){
        currentRoundMatch.setFinished(true);
        (new RoundMatchDAO(getApplicationContext())).Update(currentRoundMatch);

        if(!(new RoundMatchDAO(getApplicationContext())).HasNextRoundMatch(currentRound.getRoundId())){
            finishRound();
        }

        Intent intent = new Intent(getApplicationContext(), RoundMatchResultActivity.class);
        intent.putExtra("RoundMatch", this.currentRoundMatch);
        startActivityForResult(intent, RoundMatchResultActivity.IntentId);
        finish(); //Fecha este activity (impede o go back)
    }

    //Ao terminar todas as partidas
    private void finishRound(){
        currentRound.setFinished(true);
        (new RoundDAO(getApplicationContext())).Update(currentRound);
    }

    //Volta pra outra tela
    private void goBack(){
        setResult(RESULT_CANCELED);
        finish();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RegisterGoalActivity.IntentId) { //Veio da tela de registro de gol
            if (resultCode == RESULT_OK) {
                updateUI();
            }
        }else if(resultCode == RoundMatchResultActivity.IntentId){ //Veio da tela de finalizacao
            startNextRound(); //Inicia uma nova partida
        }
    }
}
