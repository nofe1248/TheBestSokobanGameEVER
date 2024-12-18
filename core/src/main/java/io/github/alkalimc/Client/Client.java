package io.github.alkalimc.Client;

import io.github.alkalimc.User.Log;
import io.github.alkalimc.User.UserInfo;
import io.github.nofe1248.map.map.Map;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private Socket socket = null;

    public boolean Client(String ip) {
        try {
            socket = new Socket(ip, 11451);
            return true;
        } catch (UnknownHostException e) {
            Log.writeLogToFile("UnknownHostException: " + e.getMessage());
            return false;
        } catch (IOException e) {
            Log.writeLogToFile("IOException: " + e.getMessage());
            return false;
        }
    }

    public boolean isConnected() {
        return socket.isConnected();
    }

    public void disconnect() {
        if (socket != null && socket.isConnected()) {
            try {
                socket.close();
            } catch (IOException e) {
                Log.writeLogToFile("IOException: " + e.getMessage());
            }
        }
    }

    public boolean sendData(String data) {
        if (socket != null && socket.isConnected()) {
            try {
                OutputStream outputStream = socket.getOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                objectOutputStream.writeObject(data);
                return true;
            } catch (IOException e) {
                Log.writeLogToFile("IOException: " + e.getMessage());
            }
        }
        return false;
    }
    public String receiveData() {
        if (socket != null && socket.isConnected()) {
            try {
                InputStream inputStream = socket.getInputStream();
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                Object response = objectInputStream.readObject();
                if (response instanceof String) {
                    return (String) response;
                }
                else {
                    Log.writeLogToFile("Received object is not a String: " + response.getClass().getName());
                }
            }
            catch (IOException | ClassNotFoundException e) {
                Log.writeLogToFile("Exception: " + e.getMessage());
            }
        }
        return null;
    }
    public boolean sendMap(Map map) {
        if (socket != null && socket.isConnected()) {
            try {
                OutputStream outputStream = socket.getOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                objectOutputStream.writeObject(map);
                return true;
            } catch (IOException e) {
                Log.writeLogToFile("IOException: " + e.getMessage());
            }
        }
        return false;
    }
    public Map receiveMap() {
        if (socket != null && socket.isConnected()) {
            try {
                InputStream inputStream = socket.getInputStream();
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                Object response = objectInputStream.readObject();
                if (response instanceof Map) {
                    return (Map) response;
                }
                else {
                    Log.writeLogToFile("Received object is not a Map: " + response.getClass().getName());
                }
            }
            catch (IOException | ClassNotFoundException e) {
                Log.writeLogToFile("Exception: " + e.getMessage());
            }
        }
        return null;
    }
    public boolean sendUserInfo(UserInfo userInfo) {
        if (socket != null && socket.isConnected()) {
            try {
                OutputStream outputStream = socket.getOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                objectOutputStream.writeObject(userInfo);
                return true;
            } catch (IOException e) {
                Log.writeLogToFile("IOException: " + e.getMessage());
            }
        }
        return false;
    }
    public UserInfo receiveUserInfo() {
        if (socket != null && socket.isConnected()) {
            try {
                InputStream inputStream = socket.getInputStream();
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                Object response = objectInputStream.readObject();
                if (response instanceof UserInfo) {
                    return (UserInfo) response;
                }
                else {
                    Log.writeLogToFile("Received object is not a UserInfo: " + response.getClass().getName());
                }
            }
            catch (IOException | ClassNotFoundException e) {
                Log.writeLogToFile("Exception: " + e.getMessage());
            }
        }
        return null;
    }
}
