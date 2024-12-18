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

    // 接收对象数据，只有String、Map和UserInfo三种类型
    public Object receiveObject() {
        if (socket != null && socket.isConnected()) {
            try (InputStream inputStream = socket.getInputStream();
                 ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
                Object response = objectInputStream.readObject();

                // 分类处理
                if (response instanceof String) {
                    return (String) response;
                } else if (response instanceof Map) {
                    return (Map) response;
                } else if (response instanceof UserInfo) {
                    return (UserInfo) response;
                } else {
                    Log.writeLogToFile("Received object is not a valid type. Received type: " + response.getClass().getName());
                }
            } catch (IOException | ClassNotFoundException e) {
                Log.writeLogToFile("Exception: " + e.getMessage());
            }
        }
        return null;
    }
}
