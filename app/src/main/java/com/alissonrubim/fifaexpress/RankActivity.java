package com.alissonrubim.fifaexpress;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.alissonrubim.fifaexpress.Helper.RankListViewAdapter;
import com.alissonrubim.fifaexpress.Model.Round;

import java.util.ArrayList;

public class RankActivity extends AppCompatActivity {

    private ListView listViewRank;
    private ArrayList<Round.RoundResultPosition> list = new ArrayList();
    private Round currentRound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        currentRound = (Round) getIntent().getExtras().getSerializable("Round");

        bind();

        loadListView();
    }


    private void loadListView(){
        Round.RoundResult result = currentRound.GetResult(getApplicationContext());
        list = result.Positions;
        RankListViewAdapter adapter = new RankListViewAdapter(getApplicationContext(), list);
        listViewRank.setAdapter(adapter);
    }

    private void bind(){
        listViewRank = findViewById(R.id.listViewRank);
    }
}
