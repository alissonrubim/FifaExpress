package com.alissonrubim.fifaexpress.Model;

/**
 * Created by alissonrubim on 23/03/2018.
 */

public class Player {
    private int PlayerId;
    private int TeamId;
    private Team Team;
    private String Name;

    public Player(int playerId, int teamId, com.alissonrubim.fifaexpress.Model.Team team, String name) {
        PlayerId = playerId;
        TeamId = teamId;
        Team = team;
        Name = name;
    }

    public int getPlayerId() {
        return PlayerId;
    }

    public void setPlayerId(int playerId) {
        PlayerId = playerId;
    }

    public int getTeamId() {
        return TeamId;
    }

    public void setTeamId(int teamId) {
        TeamId = teamId;
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
