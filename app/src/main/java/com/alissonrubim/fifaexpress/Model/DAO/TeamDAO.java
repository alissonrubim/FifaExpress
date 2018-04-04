package com.alissonrubim.fifaexpress.Model.DAO;

import com.alissonrubim.fifaexpress.Model.Connection.Database;
import com.alissonrubim.fifaexpress.Model.Team;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

/**
 * Created by alissonrubim on 23/03/2018.
 */

public class TeamDAO implements BaseDAO<Team>{
    private Database database;

    public TeamDAO(Context context){
        database = new Database(context);
    }

    @Override
    public ArrayList<Team> GetAll() {
        ArrayList<Team> teams = new ArrayList<Team>();
        Cursor c = database.getReadableDatabase().rawQuery("SELECT TeamId, Name FROM Team", null);
        if (c.moveToFirst()){
            do {
                teams.add(new Team(c.getInt(c.getColumnIndex("TeamId")), c.getString(c.getColumnIndex("Name"))));
            } while(c.moveToNext());
        }
        c.close();
        database.close();
        return teams;
    }

    public ArrayList<Team> GetAllNotSelected() {
        ArrayList<Team> teams = new ArrayList<Team>();
        Cursor c = database.getReadableDatabase().rawQuery("SELECT TeamId, Name FROM Team WHERE TeamId NOT IN (SELECT TeamId FROM Friend)", null);
        if (c.moveToFirst()){
            do {
                teams.add(new Team(c.getInt(c.getColumnIndex("TeamId")), c.getString(c.getColumnIndex("Name"))));
            } while(c.moveToNext());
        }
        c.close();
        database.close();
        return teams;
    }

    @Override
    public Team GetById(int id) {
        Team team = null;
        Cursor c = database.getReadableDatabase().rawQuery("SELECT TeamId, Name FROM Team WHERE TeamId = " + id, null);
        if (c.moveToFirst()){
            team = new Team(c.getInt(c.getColumnIndex("TeamId")), c.getString(c.getColumnIndex("Name")));
        }
        c.close();
        database.close();
        return team;
    }

    @Override
    public long Insert(Team obj) {
        ContentValues insertValues = new ContentValues();
        insertValues.put("Name", obj.getName());
        long id =  database.getWritableDatabase().insert("Team", null, insertValues);
        obj.setTeamId(id);
        return id;
    }

    @Override
    public void Update(Team obj) {
        throw  new UnsupportedOperationException();
    }

    @Override
    public void Delete(Team obj) {
        throw  new UnsupportedOperationException();
    }
}
