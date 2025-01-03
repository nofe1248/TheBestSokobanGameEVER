package io.github.alkalimc.Info;

import io.github.alkalimc.User.User;

import java.util.ArrayList;

public class UserInfo {
    private int id;
    private String account;
    private boolean online;

    //信息
    private String firstLoginTime;
    private String lastLoginTime;
    private int attemptTimes;
    private ArrayList scoreList;
    private ArrayList timeList;
    private int maxScore;

    public void UserInfo(User user) {
        this.id = user.getId();
        this.account = user.getAccount();
        this.online = user.isOnline();
        this.firstLoginTime = user.getFirstLoginTime();
        this.lastLoginTime = user.getLastLoginTime();
        this.attemptTimes = user.getAttemptTimes();
        this.maxScore = user.getMaxScore();
    }

    public int getId() {
        return id;
    }
    public String getAccount() {
        return account;
    }
    public boolean isOnline() {
        return online;
    }
    public String getFirstLoginTime() {
        return firstLoginTime;
    }
    public String getLastLoginTime() {
        return lastLoginTime;
    }
    public int getAttemptTimes() {
        return attemptTimes;
    }
    public ArrayList getScoreList() {
        return scoreList;
    }
    public ArrayList getTimeList() {
        return timeList;
    }
    public int getMaxScore() {
        return maxScore;
    }
}
