package com.alissonrubim.fifaexpress.Model.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.alissonrubim.fifaexpress.Model.Connection.Database;
import com.alissonrubim.fifaexpress.Model.Friend;
import com.alissonrubim.fifaexpress.Model.Round;
import com.alissonrubim.fifaexpress.Model.RoundMatch;

import java.util.ArrayList;

/**
 * Created by alissonrubim on 27/03/2018.
 */

public class RoundMatchDAO implements BaseDAO<RoundMatch> {
    private Database database;

    public RoundMatchDAO(Context context){
        database = new Database(context);
    }

    @Override
    public ArrayList<RoundMatch> GetAll() {
        throw  new UnsupportedOperationException();
    }

    public ArrayList<RoundMatch> GetAllByRoundId(long roundId) {
        ArrayList<RoundMatch> rounds = new ArrayList<>();
        Cursor c = database.getReadableDatabase().rawQuery("SELECT RoundMatchId, RoundId, Friend1Id, Friend2Id, Finished, Number FROM RoundMatch WHERE RoundId = " + roundId + " ORDER BY Number", null);
        if (c.moveToFirst()){
            do {
            Round r = (new RoundDAO(database.Context)).GetById(c.getInt(c.getColumnIndex("RoundId")));
            Friend f1 = (new FriendDAO(database.Context)).GetById(c.getInt(c.getColumnIndex("Friend1Id")));
            Friend f2 = (new FriendDAO(database.Context)).GetById(c.getInt(c.getColumnIndex("Friend2Id")));
            rounds.add(new RoundMatch(c.getInt(c.getColumnIndex("RoundMatchId")), r, f1, f2, c.getInt(c.getColumnIndex("Number")), c.getInt(c.getColumnIndex("Finished")) == 1));
            } while(c.moveToNext());
        }
        c.close();
        database.close();
        return rounds;
    }

    public RoundMatch GetNextByRoundId(long roundId){
        RoundMatch round = null;
        Cursor c = database.getReadableDatabase().rawQuery("SELECT RoundMatchId, RoundId, Friend1Id, Friend2Id, Finished, Number FROM RoundMatch WHERE Finished = 0 AND RoundId = " + roundId + " ORDER BY Number", null);
        if (c.moveToFirst()){
            Round r = (new RoundDAO(database.Context)).GetById(c.getInt(c.getColumnIndex("RoundId")));
            Friend f1 = (new FriendDAO(database.Context)).GetById(c.getInt(c.getColumnIndex("Friend1Id")));
            Friend f2 = (new FriendDAO(database.Context)).GetById(c.getInt(c.getColumnIndex("Friend2Id")));
            round = new RoundMatch(c.getInt(c.getColumnIndex("RoundMatchId")), r, f1, f2, c.getInt(c.getColumnIndex("Number")), c.getInt(c.getColumnIndex("Finished")) == 1);
        }
        c.close();
        database.close();
        return round;
    }

    public boolean HasNextRoundMatch(long roundId){
        RoundMatch next = GetNextByRoundId(roundId);
        return next != null;
    }

    public int GetCounByRoundId(long roundId){
        int count = 0;
        Cursor c = database.getReadableDatabase().rawQuery("SELECT COUNT(RoundMatchId) as Count FROM RoundMatch WHERE RoundId = " + roundId, null);
        if (c.moveToFirst()){
            count = c.getInt(c.getColumnIndex("Count"));
        }
        c.close();
        database.close();
        return count;
    }

    @Override
    public RoundMatch GetById(int id) {
        throw  new UnsupportedOperationException();
    }

    @Override
    public long Insert(RoundMatch obj) {
        ContentValues insertValues = new ContentValues();
        insertValues.put("RoundId", obj.getRound().getRoundId());
        insertValues.put("Friend1Id", obj.getFriend1().getFriendId());
        insertValues.put("Friend2Id", obj.getFriend2().getFriendId());
        insertValues.put("Finished", obj.isFinished() ? 1 : 0);
        insertValues.put("Number", obj.getNumber());
        long id = database.getWritableDatabase().insert("RoundMatch", null, insertValues);
        obj.setRoundMatchId(id);
        return id;
    }

    @Override
    public void Update(RoundMatch obj) {
        String[] values = {String.valueOf(obj.getRoundMatchId())};
        ContentValues insertValues = new ContentValues();
        insertValues.put("RoundId", obj.getRound().getRoundId());
        insertValues.put("Friend1Id", obj.getFriend1().getFriendId());
        insertValues.put("Friend2Id", obj.getFriend2().getFriendId());
        insertValues.put("Finished", obj.isFinished() ? 1 : 0);
        insertValues.put("Number", obj.getNumber());
        database.getWritableDatabase().update("RoundMatch", insertValues, "RoundMatchId = ?", values);
    }

    @Override
    public void Delete(RoundMatch obj) {
        throw  new UnsupportedOperationException();
    }
}
