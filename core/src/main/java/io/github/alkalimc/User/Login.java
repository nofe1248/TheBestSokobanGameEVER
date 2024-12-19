package io.github.alkalimc.User;

import java.util.ArrayList;

//这个println只是给样例，你可以改成对应的提示框什么的
public class Login {
    private User user;
    public void Login(String username, String password) {
        user = UserDataManager.findUserByAccount(username);
        if (user == null) {
            System.out.println("用户不存在");
            Log.illegal(3, -1, username, "登入", "密钥错误");
        } else {
            if (user.login(password)) {
                System.out.println("登入成功");
            }
            else {
                System.out.println("登入失败");
            }
        }
    }
    public void Register(String username, String password) {
        user = UserDataManager.findUserByAccount(username);
        if (user == null) {
            user = new User();
            if (user.User(username, password)) {
                System.out.println("注册成功");
            }
            else {
                System.out.println("注册失败");
            }
        }
        else {
            System.out.println("用户已存在");
        }
    }
}
