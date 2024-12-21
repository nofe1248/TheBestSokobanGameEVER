package io.github.alkalimc.User;

import java.io.*;
import java.util.ArrayList;

public class UserDataManager {

    private static final String FILE_NAME = "user_data.ser";
    File file = new File(FILE_NAME);
    private static User user;

    public static void CreateFile(ArrayList<User> users) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //用于保存或更新用户数据
    public static void saveOrUpdateUser(User user) {
        ArrayList<User> users = loadUserFromFile();
        //检查用户是否已存在，如果存在则更新，否则新增用户，这个逻辑应该不难理解？
        boolean userExists = false;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getAccount().equals(user.getAccount())) {
                users.set(i, user); // 更新用户
                userExists = true;
                break;
            }
        }
        if (!userExists) {
            users.add(user);
        }
        updateUserToFile(users);
    }

    //从文件加载所有用户，会返回一个表
    public static ArrayList<User> loadUserFromFile() {
        ArrayList<User> users = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            users = (ArrayList<User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return users;
    }

    //更新用户数据到文件，这个就是暴力直写了
    public static void updateUserToFile(ArrayList<User> users) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //通过账户名查找用户
    public static User findUserByAccount(String account) {
        ArrayList<User> users = loadUserFromFile();
        for (User user : users) {
            if (user.getAccount().equals(account)) {
                return user;
            }
        }
        return null;
    }

    public static void setUser(User user) {
        UserDataManager.user = user;
    }

    public static User getUser() {
        return user;
    }
}