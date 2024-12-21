package io.github.alkalimc.User;

//这个println只是给样例，你可以改成对应的提示框什么的
public class Login {
    public static boolean login(String username, String password) {
        User user = UserDataManager.findUserByAccount(username);
        if (user == null) {
            System.out.println("用户不存在");
            Log.illegal(3, -1, username, "登入", "密钥错误");
            return false;
        } else {
            if (user.login(password)) {
                System.out.println("登入成功");
                UserDataManager.saveOrUpdateUser(user);
                return true;
            }
            else {
                System.out.println("登入失败");
                return false;
            }
        }
    }
    public static boolean register(String username, String password) {
        User user = UserDataManager.findUserByAccount(username);
        if (user == null) {
            user = new User();
            if (user.register(username, password)) {
                System.out.println("注册成功");
                UserDataManager.saveOrUpdateUser(user);
                return true;
            }
            else {
                System.out.println("注册失败");
                return false;
            }
        }
        else {
            System.out.println("用户已存在");
            return false;
        }
    }
}
