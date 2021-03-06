package com.alissonrubim.fifaexpress.Model.Connection;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by alissonrubim on 22/03/2018.
 */

public class Database extends SQLiteOpenHelper {
    private static int databaseVersion = 13;
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

        commands.add(new SQLCommand(1, "CREATE TABLE Team(" + //Tabela que guarda os times
                "TeamId integer not null primary key autoincrement," +
                "Name text not null" +
                ");"));

        commands.add(new SQLCommand(2, "INSERT INTO Team('Name') VALUES('Vasco');"));
        commands.add(new SQLCommand(3, "INSERT INTO Team('Name') VALUES('Flamengo'), ('Tupi'), ('Santos'), ('Barcelona'), ('Brasil');"));

        commands.add(new SQLCommand(4, "CREATE TABLE Friend(" + //Tabela que guarda os amigos
                "FriendId integer not null primary key autoincrement," +
                "TeamId integer not null," +
                "Name text not null" +
                ");"));
        commands.add(new SQLCommand(5, "INSERT INTO Friend('TeamId','Name') VALUES(1,'Zezinho');")); //Tabela que guarda as Rodadas
        commands.add(new SQLCommand(6, "CREATE TABLE Round(" +
                "RoundId integer not null primary key autoincrement," +
                "Finished integer not null" +
                ");"));
        commands.add(new SQLCommand(7, "CREATE TABLE RoundMatch(" +  //Tabela que liga Rodada aos Amigos (chamada de Match)
                "RoundMatchId integer not null primary key autoincrement," +
                "RoundId integer not null," +
                "Friend1Id integer not null," +
                "Friend2Id integer not null," +
                "Finished integer not null," +
                "Number integer not null" +
                ");"));
        commands.add(new SQLCommand(8, "CREATE TABLE Player(" +  //Tabela de jogadores (dos Times)
                "PlayerId integer not null primary key autoincrement," +
                "TeamId integer not null," +
                "Name integer not null" +
                ");"));
        commands.add(new SQLCommand(9, "INSERT INTO Player('TeamId','Name') VALUES(1,'Ze Chuteira'),(1, 'Fernando Canhoto'),(1, 'Euclides Matagal');"));
        commands.add(new SQLCommand(10, "INSERT INTO Player('TeamId','Name') VALUES(2,'João Martinez'),(2, 'Federico Pau de Cebo'),(2, 'Geremias Arteziano');"));
        commands.add(new SQLCommand(11, "INSERT INTO Player('TeamId','Name') VALUES(3,'Rafael Mão de Alface'),(3, 'Pedrinho Pernambuco'),(3, 'Joaquim Lamentável');"));
        commands.add(new SQLCommand(11, "INSERT INTO Player('TeamId','Name') VALUES(4,'Pedro Mamão'),(4, 'Fernando Paraibano'),(4, 'Artur Engenheiro');"));
        commands.add(new SQLCommand(11, "INSERT INTO Player('TeamId','Name') VALUES(5,'Wanderley Gigante'),(5, 'Odilon Meia Bomba'),(5, 'Adilson Pega Pau');"));
        commands.add(new SQLCommand(12, "INSERT INTO Player('TeamId','Name') VALUES(6,'Adiano Reinador'),(6, 'Luiz Lacerda'),(6, 'Genilson Talento');"));
        commands.add(new SQLCommand(13, "CREATE TABLE RoundMatchGoal(" +  //Tabela que marca os gols da partida
                "PlayerId integer not null," +
                "RoundMatchId integer not null" +
                ");"));
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

