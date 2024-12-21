package io.github.alkalimc.User;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class User implements Serializable {

    //玩家计数
    private static int count = 0;

    private static final long serialVersionUID = 1L;

    //参数
    private int id;
    private String account;
    private String key;
    private boolean online = false;

    //信息
    private String firstLoginTime;
    private String lastLoginTime;
    private int attemptTimes = 0;
    private int maxScore = 0;
    private String lastConnectedIp = "xxx.xxx.xxx.xxx";

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
    public boolean register(String account, String password) {
        if (User.passwordValidityVerification(password)) {
            if (UserDataManager.findUserByAccount(account) == null) {
                this.id = ++count;
                this.account = account;
                this.key = getSHA256(password);
                this.firstLoginTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
                this.online = true;
                Log.logSuccessful(3, this.id, this.account, "注册");
                return true;
            }
            else {
                Log.illegal(4, this.id, this.account, "注册","用户已存在");
                return false;
            }
        }
        else {
            Log.illegal(2, this.id, this.account, "注册","非法密钥");
            return false;
        }
    }

    //用户登入
    public boolean login(String password) {
        if (User.passwordValidityVerification(password)) {
            if (Objects.equals(this.key, getSHA256(password))) {
                this.lastLoginTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
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
    public void logout(int permissions) {
        if (this.online) {
            this.online = false;
            Log.logSuccessful(4, this.id, this.account, "被登出");
        }
        else {
            Log.illegal(3, this.id, this.account, "登出", "意外的已被登出");
        }
    }

    //记录挑战数据
    public void newAttempts(int score) {
        attemptTimes++;
        if (this.maxScore < score) {
            this.maxScore = score;
        }
    }

    //这部分用于输出内容
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
    public int getMaxScore() {
        return maxScore;
    }

    //计算SHA-256，这个没验证能不能用，验证一下？
    public static String getSHA256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            Log.writeLogToFile("No SHA-256 algorithm available" + e.getMessage());
            return null;
        }
    }
}