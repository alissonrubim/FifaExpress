package com.alissonrubim.fifaexpress.Model;

/**
 * Created by alissonrubim on 22/03/2018.
 */

public class Friend implements BaseModel{
    private long FriendId;
    private Team Team;
    private String Name;

    public Friend(long friendId, com.alissonrubim.fifaexpress.Model.Team team, String name) {
        FriendId = friendId;
        Team = team;
        Name = name;
    }

    public long getFriendId() {
        return FriendId;
    }

    public void setFriendId(long friendId) {
        FriendId = friendId;
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
