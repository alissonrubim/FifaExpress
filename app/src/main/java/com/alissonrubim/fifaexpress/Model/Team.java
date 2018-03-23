package com.alissonrubim.fifaexpress.Model;

/**
 * Created by alissonrubim on 22/03/2018.
 */

public class Team {
    public Team(int teamId, String name) {
        TeamId = teamId;
        Name = name;
    }

    public int getTeamId() {
        return TeamId;
    }

    public void setTeamId(int teamId) {
        TeamId = teamId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    private int TeamId;
    private String Name;
}
