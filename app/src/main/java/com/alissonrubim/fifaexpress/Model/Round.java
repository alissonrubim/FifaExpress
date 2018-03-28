package com.alissonrubim.fifaexpress.Model;

/**
 * Created by alissonrubim on 27/03/2018.
 */

public class Round {
    private long RoundId;
    private boolean Finished;

    public Round(int roundId, boolean finished) {
        RoundId = roundId;
        Finished = finished;
    }

    public long getRoundId() {
        return RoundId;
    }

    public void setRoundId(long roundId) {
        RoundId = roundId;
    }

    public boolean isFinished() {
        return Finished;
    }

    public void setFinished(boolean finished) {
        Finished = finished;
    }
}
