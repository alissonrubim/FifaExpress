package com.alissonrubim.fifaexpress.Model.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.widget.ArrayAdapter;

import com.alissonrubim.fifaexpress.Model.DAO.TeamDAO;

import java.util.ArrayList;

/**
 * Created by alissonrubim on 22/03/2018.
 */

public class Database extends SQLiteOpenHelper {
    private static int databaseVersion = 5;
    private static String databaseName = "fifaexpress.db";
    public static Context Context;

    private ArrayList<SQLCommand> commands = new ArrayList<SQLCommand>();
    private class SQLCommand {
        public SQLCommand(int databaseVersion, String command) {
            DatabaseVersion = databaseVersion;
            Command = command;
        }

        public int DatabaseVersion;
        public String Command;
    }

    public Database(Context context){
        super(context, databaseName, null, databaseVersion);
        this.Context = context;

        commands.add(new SQLCommand(1, "CREATE TABLE Team(" +
                "TeamId integer not null primary key autoincrement," +
                "Name text not null" +
                ");"));

        commands.add(new SQLCommand(2, "INSERT INTO Team('Name') VALUES('Vasco');"));
        commands.add(new SQLCommand(3, "INSERT INTO Team('Name') VALUES('Flamengo'), ('Tupi'), ('Santos'), ('Barcelona'), ('Brasil');"));

        commands.add(new SQLCommand(4, "CREATE TABLE Friend(" +
                "FriendId integer not null primary key autoincrement," +
                "TeamId interger not null," +
                "Name text not null" +
                ");"));
        commands.add(new SQLCommand(5, "INSERT INTO Friend('TeamId','Name') VALUES(1,'Zezinho');"));
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create TeamTable
        for (SQLCommand c: commands
             ) {
            db.execSQL(c.Command);
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (SQLCommand c: commands
                ) {
            if(c.DatabaseVersion > oldVersion && c.DatabaseVersion <= newVersion) {
                db.execSQL(c.Command);
            }
        }
    }
}

