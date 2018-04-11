package com.alissonrubim.fifaexpress.Model;

/**
 * Created by alissonrubim on 23/03/2018.
 */

public class Player implements BaseModel {
    private int PlayerId;
    private Team Team;
    private String Name;

    public Player(int playerId, com.alissonrubim.fifaexpress.Model.Team team, String name) {
        PlayerId = playerId;
        Team = team;
        Name = name;
    }

    public int getPlayerId() {
        return PlayerId;
    }

    public void setPlayerId(int playerId) {
        PlayerId = playerId;
    }

    public com.alissonrubim.fifaexpress.Model.Team getTeam() {
        return Team;
    }

    public void setTeam(com.alissonrubim.fifaexpress.Model.Team team) {
        Team = team;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
