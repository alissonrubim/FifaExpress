package com.alissonrubim.fifaexpress.Helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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

    public RoundListViewAdapter(Context context, ArrayList<RoundMatch> roundMatches){
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
        }else{
            line = (RoundListViewAdapter.RoundListViewLine) convertView.getTag();
        }

        RoundMatch rm = (RoundMatch) getItem(position);
        line.textViewFriend1Name.setText(rm.getFriend1().getName());
        line.textViewFriend2Name.setText(rm.getFriend2().getName());

        return convertView;
    }

    class RoundListViewLine {
        public TextView textViewFriend1Name;
        public TextView textViewFriend2Name;
    }
}
