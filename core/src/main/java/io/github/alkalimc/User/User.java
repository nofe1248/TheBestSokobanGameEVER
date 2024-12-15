package io.github.alkalimc.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class User {

    //玩家计数
    private static int count = 0;

    //参数
    private int id;
    private String account;
    private String key;
    private boolean online = false;

    //信息
    private LocalDateTime firstLoginTime;
    private LocalDateTime lastLoginTime;
    private int attemptTimes = 0;
    private ArrayList scoreList;
    private ArrayList timeList;
    private int maxScore = 0;

    //密码合法性校验
    private static boolean passwordValidityVerification(String password) {
        if (password.length() < 8 || password.length() > 36) {
            return false;
        }
        String regex = "[^a-zA-Z0-9]";
        if (password.matches(".*" + regex + ".*")) {
            return false;
        }
        return true;
    }

    //新用户注册
    public boolean add(String account, String password) {
        if (User.passwordValidityVerification(password)) {
            this.id = ++count;
            this.account = account;
            this.key = getMD5(password);
            this.firstLoginTime = LocalDateTime.now();
            this.online = true;
            Log.logSuccessful(3, this.id, this.account, "注册");
            return true;
        }
        else {
            Log.illegal(2, this.id, this.account, "注册","非法密钥");
            return false;
        }
    }

    //用户登入
    public boolean login(String password) {
        if (User.passwordValidityVerification(password)) {
            if (this.key == getMD5(password)) {
                this.lastLoginTime = LocalDateTime.now();
                this.online = true;
                Log.logSuccessful(3, this.id, this.account, "登入");
                return true;
            }
            else {
                Log.illegal(3, this.id, this.account, "登入", "密钥错误");
                return false;
            }
        }
        else {
            Log.illegal(2, this.id, this.account, "登入", "非法密钥");
            return false;
        }
    }

    //登出
    public boolean logout(int permissions) {
        if (this.online) {
            this.online = false;
            Log.logSuccessful(4, this.id, this.account, "被登出");
            return true;
        }
        else {
            Log.illegal(3, this.id, this.account, "登出", "意外的已被登出");
            return true;
        }
    }

    //记录挑战数据
    public boolean newAttempts(int score) {
        attemptTimes++;
        if (this.maxScore < score) {
            this.maxScore = score;
        }
        scoreList.add(score);
        timeList.add(LocalDateTime.now());
        return true;
    }

    //这部分没什么用，实际用起来肯定是一条一条返回的
    @Override
    public String toString() {
        return "用户: " + this.id + "用户名: " + this.account + "于" + this.firstLoginTime + "首次加入游戏，最后一次登入的时间是: " + this.lastLoginTime + " 目前的在线状态: " + this.online;
    }

    //这部分用于输出给settings的内容
    public String getAccount() {
        return account;
    }
    public LocalDateTime getFirstLoginTime() {
        return firstLoginTime;
    }
    public int getAttemptTimes() {
        return attemptTimes;
    }
    public int getMaxScore() {
        return maxScore;
    }

    //计算md5，这个没验证能不能用，验证一下？
    public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not available", e);
        }
    }
}