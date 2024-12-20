package io.github.alkalimc.Info;

public class WinInfo {
    private String account;
    private int steps;
    private double time;
    private int score;

    public WinInfo(String account, int steps, double time, int score) {
        this.account = account;
        this.steps = steps;
        this.time = time;
        this.score = score;
    }

    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public int getSteps() {
        return steps;
    }
    public void setSteps(int steps) {
        this.steps = steps;
    }
}
