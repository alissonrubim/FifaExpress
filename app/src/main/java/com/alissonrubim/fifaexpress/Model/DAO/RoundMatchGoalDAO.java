package com.alissonrubim.fifaexpress.Model.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.alissonrubim.fifaexpress.Model.Connection.Database;
import com.alissonrubim.fifaexpress.Model.Friend;
import com.alissonrubim.fifaexpress.Model.Player;
import com.alissonrubim.fifaexpress.Model.Round;
import com.alissonrubim.fifaexpress.Model.RoundMatch;

import java.util.ArrayList;

/**
 * Created by alissonrubim on 11/04/2018.
 */

public class RoundMatchGoalDAO {
    private Database database;
    public RoundMatchGoalDAO(Context context){
        database = new Database(context);
    }

    public int GetTotalGoalsByFriend(Friend friend, RoundMatch roundMatch){
        int goals = 0;
        Cursor c = database.getReadableDatabase().rawQuery("SELECT COUNT(R.PlayerId) as Goals FROM RoundMatchGoal as R " +
                                                            "  INNER JOIN Player P ON P.PlayerId = R.PlayerId  "+
                                                            "  WHERE P.TeamId = "+friend.getTeam().getTeamId()+" AND RoundMatchId = " + roundMatch.getRoundMatchId(), null);
        if (c.moveToFirst()){
            goals  = c.getInt(c.getColumnIndex("Goals"));
        }
        c.close();
        database.close();
        return goals;
    }

    public void Insert(Player player, RoundMatch roundMatch){
        ContentValues insertValues = new ContentValues();
        insertValues.put("PlayerId", player.getPlayerId());
        insertValues.put("RoundMatchId", roundMatch.getRoundMatchId());
        database.getWritableDatabase().insert("RoundMatchGoal", null, insertValues);
    }
}
