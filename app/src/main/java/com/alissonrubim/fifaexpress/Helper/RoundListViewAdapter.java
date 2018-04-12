package com.alissonrubim.fifaexpress.Helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alissonrubim.fifaexpress.Model.DAO.RoundMatchGoalDAO;
import com.alissonrubim.fifaexpress.Model.Friend;
import com.alissonrubim.fifaexpress.Model.Round;
import com.alissonrubim.fifaexpress.Model.RoundMatch;
import com.alissonrubim.fifaexpress.R;

import java.util.ArrayList;

/**
 * Created by alissonrubim on 11/04/2018.
 */

public class RoundListViewAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private Round currentRound;
    private ArrayList<RoundMatch> roundMatches = new ArrayList<RoundMatch>();
    private Context currentContext;

    public RoundListViewAdapter(Context context, ArrayList<RoundMatch> roundMatches){
        this.currentContext = context;
        this.roundMatches = roundMatches;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return roundMatches.size();
    }

    @Override
    public Object getItem(int position) {
        return roundMatches.get(position);
    }

    @Override
    public long getItemId(int position) {
        return roundMatches.get(position).getRoundMatchId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RoundListViewAdapter.RoundListViewLine line;

        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.layout_round_item, null);
            line = new RoundListViewAdapter.RoundListViewLine();
            line.textViewFriend1Name = convertView.findViewById(R.id.textViewFriend1Name);
            line.textViewFriend2Name = convertView.findViewById(R.id.textViewFriend2Name);
            line.textViewFriend1Team = convertView.findViewById(R.id.textViewFriend1Team);
            line.textViewFriend2Team = convertView.findViewById(R.id.textViewFriend2Team);
            line.textViewRoundRoundNumber = convertView.findViewById(R.id.textViewRoundRoundNumber);
            line.imageViewFriend1 = convertView.findViewById(R.id.imageViewFriend1);
            line.imageViewFriend2 = convertView.findViewById(R.id.imageViewFriend2);
            line.textViewFriend1Goals = convertView.findViewById(R.id.textViewFriend1Goals);
            line.textViewFriend2Goals = convertView.findViewById(R.id.textViewFriend2Goals);
            line.textViewFinished = convertView.findViewById(R.id.textViewFinished);
        }else{
            line = (RoundListViewAdapter.RoundListViewLine) convertView.getTag();
        }

        RoundMatchGoalDAO roundMatchGoalDAO = new RoundMatchGoalDAO(currentContext);
        RoundMatch rm = (RoundMatch) getItem(position);
        line.textViewFriend1Name.setText(rm.getFriend1().getName());
        line.textViewFriend2Name.setText(rm.getFriend2().getName());
        line.textViewFriend1Team.setText(rm.getFriend1().getTeam().getName());
        line.textViewFriend2Team.setText(rm.getFriend2().getTeam().getName());
        line.textViewRoundRoundNumber.setText(Integer.toString(rm.getNumber()));
        line.textViewFriend1Goals.setText(Integer.toString(roundMatchGoalDAO.GetTotalGoalsByFriend(rm.getFriend1(), rm)));
        line.textViewFriend2Goals.setText(Integer.toString(roundMatchGoalDAO.GetTotalGoalsByFriend(rm.getFriend2(), rm)));
        line.imageViewFriend1.setImageResource(TeamLogoCatcher.GetLogo(rm.getFriend1().getTeam()));
        line.imageViewFriend2.setImageResource(TeamLogoCatcher.GetLogo(rm.getFriend2().getTeam()));
        if(rm.isFinished()){
            line.textViewFinished.setVisibility(View.VISIBLE);
        }else{
            line.textViewFinished.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }

    class RoundListViewLine {
        public TextView textViewFriend1Name;
        public TextView textViewFriend2Name;
        public TextView textViewFriend1Team;
        public TextView textViewFriend2Team;
        public TextView textViewRoundRoundNumber;
        public ImageView imageViewFriend1;
        public ImageView imageViewFriend2;
        public TextView textViewFriend1Goals;
        public TextView textViewFriend2Goals;
        public TextView textViewFinished;
    }
}
