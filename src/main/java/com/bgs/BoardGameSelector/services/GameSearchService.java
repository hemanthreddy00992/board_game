package com.bgs.BoardGameSelector.services;
import java.util.List;

public class GameSearchService {
    public int minRank;
    public int maxRank;
    public int minNumOfPlayers;
    public int maxNumOfPlayers;
    public int minYearPublished;
    public int maxYearPublished;
    public int minAvgPlayTime;
    public int maxAvgPlayTime;
    public int minMinPlayTime;
    public int maxMinPlayTime;
    public int minMaxPlayTime;
    public int maxMaxPlayTime;
    public int minVotes;
    public int maxVotes;
    public int minAge;
    public int maxAge;
    public int minFans;
    public int maxFans;

    public GameSearchService(int minRank, int maxRank, int minNumOfPlayers, int maxNumOfPlayers, int minYearPublished,
                             int maxYearPublished, int minAvgPlayTime, int maxAvgPlayTime, int minMinPlayTime,
                             int maxMinPlayTime, int minMaxPlayTime, int maxMaxPlayTime, int minVotes, int maxVotes,
                             int minAge, int maxAge, int minFans, int maxFans)
    {
        this.minRank = minRank;
        this.maxRank = maxRank;
        this.minNumOfPlayers = minNumOfPlayers;
        this.maxNumOfPlayers = maxNumOfPlayers;
        this.minYearPublished = minYearPublished;
        this.maxYearPublished = maxYearPublished;
        this.minAvgPlayTime = minAvgPlayTime;
        this.maxAvgPlayTime = maxAvgPlayTime;
        this.minMinPlayTime = minMinPlayTime;
        this.maxMinPlayTime = maxMinPlayTime;
        this.minMaxPlayTime = minMaxPlayTime;
        this.maxMaxPlayTime = maxMaxPlayTime;
        this.minVotes = minVotes;
        this.maxVotes = maxVotes;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.minFans = minFans;
        this.maxFans = maxFans;
    }

    public GameSearchService() {
        this.minRank = 0;
        this.maxRank = 0;
        this.minNumOfPlayers = 0;
        this.maxNumOfPlayers = 0;
        this.minYearPublished = 0;
        this.maxYearPublished = 0;
        this.minAvgPlayTime = 0;
        this.maxAvgPlayTime = 0;
        this.minMinPlayTime = 0;
        this.maxMinPlayTime = 0;
        this.minMaxPlayTime = 0;
        this.maxMaxPlayTime = 0;
        this.minVotes = 0;
        this.maxVotes = 0;
        this.minAge = 0;
        this.maxAge = 0;
        this.minFans = 0;
        this.maxFans = 0;
    }

}
