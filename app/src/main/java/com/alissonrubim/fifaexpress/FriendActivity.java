package com.alissonrubim.fifaexpress;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.alissonrubim.fifaexpress.Helper.FriendListViewAdapter;
import com.alissonrubim.fifaexpress.Model.DAO.FriendDAO;
import com.alissonrubim.fifaexpress.Model.DAO.RoundDAO;
import com.alissonrubim.fifaexpress.Model.Connection.Database;
import com.alissonrubim.fifaexpress.Model.Friend;
import com.alissonrubim.fifaexpress.Model.Round;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FriendActivity extends AppCompatActivity {
    private FloatingActionButton faAddFriend;
    private FloatingActionButton faPlay;
    private ListView listViewResume;


    /*
        Trocar o RegistrarGol para uma MESNGEM CUSTOMIZADA
             Ao lado de cada jogador, colocar uma botao que abre customizada com um spinner perguntando qual jogador marcou o gol
        Lista de partidas
            Ter um jeito de mostrar todas as partidas que serao jogadas (botar no final o botao de encerrar torneio)
        Remover botao de proxima partida


     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Database database = new Database(getApplicationContext());

        bindUI();

        Round currentRound = (new RoundDAO(getApplicationContext())).GetNotFinished();
        if(currentRound != null){
            showRoundActivity();
        }

        faAddFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFriendDetailActivity();
            }
        });

        faPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRoundActivity();
            }
        });

        fillListViewResume();
    }

    //Abre a activity de detalhe do amigo
    private void showFriendDetailActivity(){
        Intent intent = new Intent(getApplicationContext(), FriendDetailActivity.class);
        startActivityForResult(intent, FriendDetailActivity.IntentId);
    }

    //Abre a activity de Round
    private void showRoundActivity(){
        Intent intent = new Intent(getApplicationContext(), RoundActivity.class);
        startActivityForResult(intent, RoundActivity.IntentId);
        finish(); //Fecha este activity (impede o go back)
    }

    private void bindUI(){
        faAddFriend = findViewById(R.id.fabAddFriend);
        faPlay = findViewById(R.id.fabPlay);
        listViewResume = findViewById(R.id.listViewResume);
    }

    //Preenche a lista resumo com os amigos
    private void fillListViewResume(){
        ArrayList<Friend> friends = (new FriendDAO(getApplicationContext())).GetAll();
        FriendListViewAdapter adapter = new FriendListViewAdapter(getApplicationContext(), friends);
        listViewResume.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FriendDetailActivity.IntentId) { //Quando vier da tela de detalhe do amigo
            if (resultCode == RESULT_OK) {
                fillListViewResume();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friend, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
