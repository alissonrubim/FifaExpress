package com.alissonrubim.fifaexpress.Model.DAO;

import android.content.Context;
import android.database.Cursor;

import com.alissonrubim.fifaexpress.Model.Connection.Database;
import com.alissonrubim.fifaexpress.Model.Player;
import com.alissonrubim.fifaexpress.Model.Team;

import java.util.ArrayList;

/**
 * Created by alissonrubim on 11/04/2018.
 */

public class PlayerDAO implements BaseDAO<Player> {
    private Database database;
    public PlayerDAO(Context context){
        database = new Database(context);
    }

    @Override
    public ArrayList<Player> GetAll() {
        return null;
    }

    public ArrayList<Player> GetAllByTeam(Team team) {
        ArrayList<Player> players = new ArrayList<Player>();
        Cursor c = database.getReadableDatabase().rawQuery("SELECT PlayerId, TeamId, Name FROM Player WHERE TeamId = " + team.getTeamId(), null);
        if (c.moveToFirst()){
            do {
                players.add(new Player(c.getInt(c.getColumnIndex("PlayerId")), team, c.getString(c.getColumnIndex("Name"))));
            } while(c.moveToNext());
        }
        c.close();
        database.close();
        return players;
    }

    @Override
    public Player GetById(int id) {
        return null;
    }

    @Override
    public long Insert(Player obj) {
        return 0;
    }

    @Override
    public void Update(Player obj) {

    }

    @Override
    public void Delete(Player obj) {

    }
}
