package io.github.alkalimc.Client;

import io.github.alkalimc.Server.Server;
import io.github.alkalimc.Update.UpdateMap;
import io.github.alkalimc.Update.UpdateUserInfo;
import io.github.alkalimc.User.Log;
import io.github.alkalimc.User.UserInfo;
import io.github.nofe1248.map.map.Map;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private Socket socket = null;
    private boolean listening = true;
    private static boolean firstMap = false;

    public boolean connect(String ip) {
        try {
            socket = new Socket(ip, 23456);
            listening = true;
            firstMap = true;
            return true;
        } catch (UnknownHostException e) {
            Log.writeLogToFile("UnknownHostException: " + e.getMessage());
            firstMap = false;
            return false;
        } catch (IOException e) {
            Log.writeLogToFile("IOException: " + e.getMessage());
            firstMap = false;
            return false;
        }
    }

    public boolean isConnected() {
        if (socket != null && socket.isConnected()) {
            return true;
        }
        return false;
    }

    public void disconnect() {
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

    public <T> boolean sendObject(T data) {
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

    public Object receiveObject() {
        while (listening) {
            if (socket != null && socket.isConnected()) {
                try (InputStream inputStream = socket.getInputStream();
                     ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
                    Object response = objectInputStream.readObject();

                    if (response instanceof Map) {
                        if (firstMap) {
                            firstMap = false;
                        }
                        else {
                            UpdateMap.updateMap((Map) response);
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
