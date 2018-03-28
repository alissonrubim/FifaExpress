package com.alissonrubim.fifaexpress.Model.DAO;

import android.content.ContentValues;
import android.content.Context;

import com.alissonrubim.fifaexpress.Model.Connection.Database;
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
        return null;
    }

    public ArrayList<RoundMatch> GetAllByRoundId(long roundId) {
        return null;
    }

    @Override
    public RoundMatch GetById(int id) {
        return null;
    }

    @Override
    public long Insert(RoundMatch obj) {
        ContentValues insertValues = new ContentValues();
        insertValues.put("RoundId", obj.getRound().getRoundId());
        insertValues.put("Friend1Id", obj.getFriend1().getFriendId());
        insertValues.put("Friend2Id", obj.getFriend2().getFriendId());
        insertValues.put("Fineshed", obj.isFinished() ? 1 : 0);
        long id = database.getWritableDatabase().insert("Round", null, insertValues);
        obj.setRoundMatchId(id);
        return id;
    }

    @Override
    public void Update(RoundMatch obj) {

    }

    @Override
    public void Delete(RoundMatch obj) {

    }
}
