package com.alissonrubim.fifaexpress.Model;

import com.alissonrubim.fifaexpress.Model.DAO.RoundMatchGoalDAO;

import java.io.Serializable;

/**
 * Created by alissonrubim on 27/03/2018.
 */

public class RoundMatch implements BaseModel {
    private long RoundMatchId;
    private Round Round;
    private Friend Friend1;
    private Friend Friend2;
    private int Number;
    private boolean Finished;

    public RoundMatch(long roundMatchId, Round round, Friend friend1, Friend friend2, int number, boolean finished) {
        RoundMatchId = roundMatchId;
        Friend1 = friend1;
        Friend2 = friend2;
        Finished = finished;
        Round = round;
        Number = number;
    }

    public long getRoundMatchId() {
        return RoundMatchId;
    }

    public void setRoundMatchId(long roundMatchId) {
        RoundMatchId = roundMatchId;
    }

    public com.alissonrubim.fifaexpress.Model.Round getRound() {
        return Round;
    }

    public void setRound(com.alissonrubim.fifaexpress.Model.Round round) {
        Round = round;
    }

    public Friend getFriend1() {
        return Friend1;
    }

    public void setFriend1(Friend friend1) {
        Friend1 = friend1;
    }

    public Friend getFriend2() {
        return Friend2;
    }

    public void setFriend2(Friend friend2) {
        Friend2 = friend2;
    }

    public boolean isFinished() {
        return Finished;
    }

    public void setFinished(boolean finished) {
        Finished = finished;
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int number) {
        Number = number;
    }
}