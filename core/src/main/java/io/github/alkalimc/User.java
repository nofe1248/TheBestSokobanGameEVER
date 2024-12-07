import java.time.LocalDateTime;
import java.util.ArrayList;

//用户
public class User {

    //玩家计数
    private static int count = 0;

    //参数
    private int id;
    private String account;
    private int key;
    private boolean loginPermission = true;
    private boolean online = false;

    //信息
    private String banReason = "未知原因";
    private LocalDateTime banTime;
    private LocalDateTime firstLoginTime;
    private LocalDateTime lastLoginTime;
    private int attemptTimes = 0;
    private ArrayList scoreList;
    private ArrayList timeList;
    private int maxScore = 0;

    //密码合法性校验
    private static boolean passwordValidityVerification(String password) {
        if (password.length() < 8 || password.length() > 16) {
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
            this.key = password.hashCode();
            this.firstLoginTime = LocalDateTime.now();
            this.online = true;
            System.out.println(Log.logSuccessful(3, this.id, this.account, "注册"));
            return true;
        }
        else {
            System.out.println(Log.illegal(2, this.id, this.account, "注册","非法密钥"));
            return false;
        }
    }

    //用户登入
    public boolean login(String password) {
        if (User.passwordValidityVerification(password)) {
            if (this.key == password.hashCode()) {
                if (this.loginPermission) {
                    this.lastLoginTime = LocalDateTime.now();
                    this.online = true;
                    System.out.println(Log.logSuccessful(3, this.id, this.account, "登入"));
                    return true;
                }
                else {
                    System.out.println(Log.loginProhibited(this.id, this.account, this.banReason, this.banTime));
                    return false;
                }
            }
            else {
                System.out.println(Log.illegal(3, this.id, this.account, "登入", "密钥错误"));
                return false;
            }
        }
        else {
            System.out.println(Log.illegal(2, this.id, this.account, "登入", "非法密钥"));
            return false;
        }
    }

    //登出
    public boolean logout(int permissions) {
        if (permissions == 0) {
            if (this.online) {
                this.online = false;
                System.out.println(Log.logSuccessful(3, this.id, this.account, "被管理员登出"));
                return true;
            }
            else {
                System.out.println(Log.illegal(3, this.id, this.account, "被管理员登出", "用户已登出"));
                return false;
            }
        }
        if (permissions == 1) {
            if (this.online) {
                this.online = false;
                System.out.println(Log.logSuccessful(4, this.id, this.account, "被自动登出"));
                return true;
            }
            else {
                System.out.println(Log.logSuccessful(4, this.id, this.account, "被检查了一次登入状态"));
                return true;
            }
        }
        if (permissions == 2) {
            this.online = false;
            System.out.println(Log.logSuccessful(3, this.id, this.account, "登出"));
            return true;
        }
        else {
            System.out.println(LocalDateTime.now() + "登出功能出现非法的权限掩码");
            return false;
        }
    }

    //封禁
    public boolean ban(int permissions, String reason) {
        if (permissions == 0) {
            if (this.loginPermission) {
                this.loginPermission = false;
                this.banReason = reason;
                this.banTime = LocalDateTime.now();
                logout(1);
                System.out.println(Log.logSuccessful(3, this.id, this.account, "被管理员封禁"));
                return true;
            }
            else {
                System.out.println(Log.illegal(3, this.id, this.account, "被管理员封禁", "用户已被封禁"));
                return false;
            }
        }
        if (permissions == 1) {
            if (this.loginPermission) {
                this.loginPermission = false;
                System.out.println(Log.logSuccessful(3, this.id, this.account, "被自动封禁"));
                return true;
            }
            else {
                System.out.println(Log.logSuccessful(4, this.id, this.account, "被检查了一次封禁状态"));
                return true;
            }
        }
        else {
            System.out.println(LocalDateTime.now() + "封禁功能出现非法的权限掩码");
            return false;
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





    public String introduce() {
        return "用户: " + this.id + "用户名: " + this.account + "于" + this.firstLoginTime + "首次加入游戏，最后一次登入的时间是: " + this.lastLoginTime + " 目前的在线状态: " + this.online;
    }
}