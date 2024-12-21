package io.github.alkalimc.User;

//这个println只是给样例，你可以改成对应的提示框什么的
public class Login {
    public static boolean login(String username, String password) {
        User user = UserDataManager.findUserByAccount(username);
        if (user == null) {
            Log.illegal(4, -1, username, "登入", "用户不存在");
            return false;
        }
        else {
            if (user.login(password)) {
                UserDataManager.saveOrUpdateUser(user);
                UserDataManager.setUser(user);
                return true;
            }
            else {
                return false;
            }
        }
    }
    public static boolean register(String username, String password) {
        User user = UserDataManager.findUserByAccount(username);
        if (user == null) {
            user = new User();
            if (user.register(username, password)) {
                UserDataManager.saveOrUpdateUser(user);
                UserDataManager.setUser(user);
                return true;
            }
            else {
                return false;
            }
        }
        else {
            Log.illegal(4, -1, username, "注册", "用户已存在");
            return false;
        }
    }
}
