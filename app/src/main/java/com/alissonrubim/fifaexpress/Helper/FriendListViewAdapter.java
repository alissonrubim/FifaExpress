package com.alissonrubim.fifaexpress.Helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alissonrubim.fifaexpress.Model.Friend;
import com.alissonrubim.fifaexpress.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by alissonrubim on 04/04/2018.
 */

public class FriendListViewAdapter extends BaseAdapter{

    private LayoutInflater layoutInflater;
    private  ArrayList<Friend> friends = new ArrayList<Friend>();

    public FriendListViewAdapter(Context context, ArrayList<Friend> friends){
        this.friends = friends;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return friends.size();
    }

    @Override
    public Object getItem(int position) {
        return friends.get(position);
    }

    @Override
    public long getItemId(int position) {
        return friends.get(position).getFriendId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FriendListViewLine line;

        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.layout_friend_item, null);
            line = new FriendListViewLine();
            line.textViewFriendName = convertView.findViewById(R.id.textViewFriendName);
            line.textViewTeamName = convertView.findViewById(R.id.textViewTeamName);
            line.imageViewTeamLogo = convertView.findViewById(R.id.imageViewTeamLogo);
        }else{
            line = (FriendListViewLine) convertView.getTag();
        }

        Friend f = (Friend) getItem(position);
        line.textViewFriendName.setText(f.getName());
        line.textViewTeamName.setText(f.getTeam().getName());
        line.imageViewTeamLogo.setImageResource(TeamLogoCatcher.GetLogo(f.getTeam()));

        return convertView;
    }

    class FriendListViewLine {
        public TextView textViewFriendName;
        public TextView textViewTeamName;
        public ImageView imageViewTeamLogo;
    }
}
