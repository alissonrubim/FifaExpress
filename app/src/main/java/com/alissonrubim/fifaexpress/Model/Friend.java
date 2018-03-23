package com.alissonrubim.fifaexpress.Model;

/**
 * Created by alissonrubim on 22/03/2018.
 */

public class Friend {
    public Friend(int friendId, com.alissonrubim.fifaexpress.Model.Team team, String name) {
        FriendId = friendId;
        TeamId = team.getTeamId();
        Team = team;
        Name = name;
    }

    public int getFriendId() {
        return FriendId;
    }

    public void setFriendId(int friendId) {
        FriendId = friendId;
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

    private int FriendId;
    private int TeamId;
    private Team Team;
    private String Name;
}
