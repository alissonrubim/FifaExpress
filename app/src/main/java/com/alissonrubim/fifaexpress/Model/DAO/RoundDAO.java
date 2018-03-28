package com.alissonrubim.fifaexpress.Model.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.alissonrubim.fifaexpress.Model.Connection.Database;
import com.alissonrubim.fifaexpress.Model.Round;

import java.util.ArrayList;

/**
 * Created by alissonrubim on 27/03/2018.
 */

public class RoundDAO implements BaseDAO<Round> {
    private Database database;

    public RoundDAO(Context context){
        database = new Database(context);
    }

    public Round GetNotFinished(){
        Round round = null;
        Cursor c = database.getReadableDatabase().rawQuery("SELECT RoundId, Finished FROM Round WHERE Finished = 0", null);
        if (c.moveToFirst()){
            round = new Round(c.getInt(c.getColumnIndex("RoundId")), c.getInt(c.getColumnIndex("Finished")) == 1);
        }
        c.close();
        database.close();
        return round;
    }

    @Override
    public ArrayList<Round> GetAll() {
        return null;
    }

    @Override
    public Round GetById(int id) {
        return null;
    }

    @Override
    public long Insert(Round obj) {
        ContentValues insertValues = new ContentValues();
        insertValues.put("Finished", obj.isFinished() ? 1 : 0);
        long id = database.getWritableDatabase().insert("Round", null, insertValues);
        obj.setRoundId(id);
        return id;
    }

    @Override
    public void Update(Round obj) {
        String[] values = {String.valueOf(obj.getRoundId())};
        ContentValues insertValues = new ContentValues();
        insertValues.put("Finished", obj.isFinished() ? 1 : 0);
        database.getWritableDatabase().update("Round", insertValues, "RoundId = ?", values);
    }

    @Override
    public void Delete(Round obj) {

    }
}
