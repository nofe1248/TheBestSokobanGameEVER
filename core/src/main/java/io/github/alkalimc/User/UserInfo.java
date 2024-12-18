package io.github.alkalimc.User;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class UserInfo {
    private int id;
    private String account;
    private boolean online;

    //信息
    private LocalDateTime firstLoginTime;
    private LocalDateTime lastLoginTime;
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
    public LocalDateTime getFirstLoginTime() {
        return firstLoginTime;
    }
    public LocalDateTime getLastLoginTime() {
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
