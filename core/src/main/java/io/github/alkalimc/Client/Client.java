package io.github.alkalimc.Client;

import io.github.alkalimc.Update.UpdateMap;
import io.github.alkalimc.Update.UpdateUserInfo;
import io.github.alkalimc.User.Log;
import io.github.alkalimc.User.UserInfo;
import io.github.nofe1248.map.map.Map;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private static Socket socket = null;
    private static boolean listening = false;
    private static boolean firstMap = false;

    public static boolean Client(String ip) {
        try {
            socket = new Socket(ip, 11451);
            listening = true;
            firstMap = true;
            return true;
        } catch (UnknownHostException e) {
            Log.writeLogToFile("UnknownHostException: " + e.getMessage());
            listening = false;
            firstMap = false;
            return false;
        } catch (IOException e) {
            Log.writeLogToFile("IOException: " + e.getMessage());
            listening = false;
            firstMap = false;
            return false;
        }
    }

    public static boolean isConnected() {
        return socket.isConnected();
    }

    public static void disconnect() {
        if (socket != null && socket.isConnected()) {
            try {
                socket.close();
                listening = false;
                firstMap = false;
            } catch (IOException e) {
                Log.writeLogToFile("IOException: " + e.getMessage());
            }
        }
    }

    public static <T> boolean sendObject(T data) {
        if (socket != null && socket.isConnected()) {
            try (OutputStream outputStream = socket.getOutputStream();
                 ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
                objectOutputStream.writeObject(data);
                return true;
            } catch (IOException e) {
                Log.writeLogToFile("IOException: " + e.getMessage());
            }
        }
        return false;
    }

    public static Object receiveObject() {
        while (listening) {
            if (socket != null && socket.isConnected()) {
                try (InputStream inputStream = socket.getInputStream();
                     ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
                    Object response = objectInputStream.readObject();

                    if (response instanceof Map) {
                        if (firstMap) {
                            new GetMap((Map) response);
                            firstMap = false;
                        }
                        else {
                            new UpdateMap((Map) response);
                        }
                        return (Map) response;
                    }
                    else if (response instanceof UserInfo) {
                        new UpdateUserInfo((UserInfo) response);
                        return (UserInfo) response;
                    }
                    else {
                        Log.writeLogToFile("Received object is not a valid type. Received type: " + response.getClass().getName());
                    }
                } catch (IOException | ClassNotFoundException e) {
                    Log.writeLogToFile("Exception: " + e.getMessage());
                }
            }
            return null;
        }
        return null;
    }
}
