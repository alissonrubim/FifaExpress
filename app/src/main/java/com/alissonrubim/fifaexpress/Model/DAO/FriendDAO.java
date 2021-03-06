package com.alissonrubim.fifaexpress.Model.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.alissonrubim.fifaexpress.Model.Connection.Database;
import com.alissonrubim.fifaexpress.Model.Friend;
import com.alissonrubim.fifaexpress.Model.Team;

import java.util.ArrayList;

/**
 * Created by alissonrubim on 23/03/2018.
 */

public class FriendDAO implements BaseDAO<Friend>{
    private Database database;
    public FriendDAO(Context context){
        database = new Database(context);
    }

    @Override
    public ArrayList<Friend> GetAll() {
        ArrayList<Friend> friends = new ArrayList<Friend>();
        Cursor c = database.getReadableDatabase().rawQuery("SELECT FriendId, TeamId, Name FROM Friend", null);
        if (c.moveToFirst()){
            do {
                Team team = (new TeamDAO(database.Context)).GetById(c.getInt(c.getColumnIndex("TeamId")));
                friends.add(new Friend(c.getInt(c.getColumnIndex("FriendId")), team, c.getString(c.getColumnIndex("Name"))));
            } while(c.moveToNext());
        }
        c.close();
        database.close();
        return friends;
    }

    @Override
    public Friend GetById(int id) {
        Friend friend = null;
        Cursor c = database.getReadableDatabase().rawQuery("SELECT FriendId, TeamId, Name FROM Friend WHERE FriendId = " + id, null);
        if (c.moveToFirst()){
            Team team = (new TeamDAO(database.Context)).GetById(c.getInt(c.getColumnIndex("TeamId")));
            friend = new Friend(c.getInt(c.getColumnIndex("FriendId")),team, c.getString(c.getColumnIndex("Name")));
        }
        c.close();
        database.close();
        return friend;
    }

    @Override
    public long Insert(Friend obj) {
        ContentValues insertValues = new ContentValues();
        insertValues.put("Name", obj.getName());
        insertValues.put("TeamId", obj.getTeam().getTeamId());
        long id = database.getWritableDatabase().insert("Friend", null, insertValues);
        obj.setFriendId(id);
        return id;
    }

    @Override
    public void Update(Friend obj) {
        throw  new UnsupportedOperationException();
    }

    @Override
    public void Delete(Friend obj) {
        throw  new UnsupportedOperationException();
    }
}
