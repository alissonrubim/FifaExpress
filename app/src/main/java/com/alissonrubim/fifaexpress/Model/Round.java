package com.alissonrubim.fifaexpress.Model;

import android.content.Context;

import com.alissonrubim.fifaexpress.Model.DAO.RoundMatchDAO;

import java.util.ArrayList;

/**
 * Created by alissonrubim on 27/03/2018.
 */

public class Round implements BaseModel{
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

    public RoundResult GetResult(Context context){
        ArrayList<RoundMatch> roundMatches = (new RoundMatchDAO(context)).GetAllByRoundId(this.getRoundId());

        int roundWinnerPoints = 0;
        int roundWinnerGoals = 0;
        Friend roundWinner = null;

        for (RoundMatch rm:
                roundMatches) {
            RoundMatch.RoundMatchResult rmResult = rm.GetResult(context);

            Friend roundMatchWinner = rmResult.Winner;
            int roundMatchWinnerPoints = rmResult.WinnerPoints;
            int roundMatchWinnerGoals = rmResult.WinnerGoals;

            if(roundWinner == null || roundMatchWinnerPoints > roundWinnerPoints){
                roundWinner = roundMatchWinner;
                roundWinnerPoints = roundMatchWinnerPoints;
                roundWinnerGoals = roundMatchWinnerGoals;
            }
        }

        return new RoundResult(roundWinner, roundWinnerPoints, roundWinnerGoals);
    }

    public class RoundResult {
        public RoundResult(Friend winner, int winnerPoints, int winnerGoals) {
            Winner = winner;
            WinnerPoints = winnerPoints;
            WinnerGoals = winnerGoals;
        }

        public Friend Winner;
        public int WinnerPoints;
        public int WinnerGoals;
    }
}
