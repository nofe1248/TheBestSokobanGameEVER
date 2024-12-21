package io.github.alkalimc.User;

import java.util.ArrayList;

public class CreateFile {
    public static void main(String[] args) {
        ArrayList<User> users = new ArrayList<User>();
        UserDataManager.CreateFile(users);
    }
}
