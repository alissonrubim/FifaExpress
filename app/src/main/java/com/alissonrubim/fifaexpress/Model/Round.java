package com.alissonrubim.fifaexpress.Model;

import android.content.Context;
import android.os.Build;

import com.alissonrubim.fifaexpress.Model.DAO.RoundMatchDAO;
import com.alissonrubim.fifaexpress.Model.DAO.RoundMatchGoalDAO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

        RoundResult result = new RoundResult();

        result.TopScorer = (new RoundMatchGoalDAO(context)).GetBestScorePlayer(this);

        for (RoundMatch rm:
                roundMatches) {
            RoundMatch.RoundMatchResult rmResult = rm.GetResult(context);

            result.Positions.add(new RoundResultPosition(rmResult.Winner, rmResult.WinnerPoints, rmResult.WinnerGoals));
            result.Positions.add(new RoundResultPosition(rmResult.Loser, rmResult.LoserPoints, rmResult.LoserGoals));
        }

        Comparator<RoundResultPosition> comparator = new Comparator<RoundResultPosition>() {
            @Override
            public int compare(RoundResultPosition o1, RoundResultPosition o2) {
                return (int)(o2.Points - o1.Points);
            }
        };
        Collections.sort(result.Positions, comparator);

        return result;
    }

    public class RoundResult {
        public TopScorer TopScorer;
        public ArrayList<RoundResultPosition> Positions = new ArrayList<RoundResultPosition>();
    }

    public static class TopScorer {
        public int Goals = 0;
        public Player Player;
    }

    public class RoundResultPosition{
        public RoundResultPosition(com.alissonrubim.fifaexpress.Model.Friend friend, double points, int goals) {
            Friend = friend;
            Points = points;
            Goals = goals;
        }

        public Friend Friend;
        public double Points;
        public int Goals;
    }
}
