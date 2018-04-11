package com.alissonrubim.fifaexpress.Model;

/**
 * Created by alissonrubim on 22/03/2018.
 */

public class Team implements BaseModel{
    public Team(int teamId, String name) {
        TeamId = teamId;
        Name = name;
    }

    public long getTeamId() {
        return TeamId;
    }

    public void setTeamId(long teamId) {
        TeamId = teamId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    private long TeamId;
    private String Name;
}
