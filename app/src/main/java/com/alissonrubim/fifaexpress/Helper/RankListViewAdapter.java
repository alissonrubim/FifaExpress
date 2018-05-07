package com.alissonrubim.fifaexpress.Helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alissonrubim.fifaexpress.Model.Friend;
import com.alissonrubim.fifaexpress.Model.Round;
import com.alissonrubim.fifaexpress.R;

import java.util.ArrayList;

/**
 * Created by alissonrubim on 07/05/2018.
 */

public class RankListViewAdapter extends BaseAdapter{

    private LayoutInflater layoutInflater;
    private ArrayList<Round.RoundResultPosition> list = new ArrayList<Round.RoundResultPosition>();

    public RankListViewAdapter(Context context, ArrayList<Round.RoundResultPosition> list){
        this.list = list;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).Friend.getFriendId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RankListViewLine line;

        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.layout_rank_item, null);
            line = new RankListViewLine();
            line.textViewFriendName = convertView.findViewById(R.id.textViewFriendName);
            line.textViewPosition = convertView.findViewById(R.id.textViewPosition);
            line.textViewPointsGoals = convertView.findViewById(R.id.textViewPointsGoals);
        }else{
            line = (RankListViewLine) convertView.getTag();
        }

        Round.RoundResultPosition f = (Round.RoundResultPosition) getItem(position);
        line.textViewFriendName.setText(f.Friend.getName() + " - " + f.Friend.getTeam().getName());
        line.textViewPosition.setText(Integer.toString(position + 1) + "º");
        line.textViewPointsGoals.setText(Integer.toString(f.Goals) + " Gols - " + Integer.toString(f.Points) + " Pontos");

        return convertView;
    }

    class RankListViewLine {
        public TextView textViewPosition;
        public TextView textViewFriendName;
        public TextView textViewPointsGoals;
    }
}

