package io.github.alkalimc.Server;

import io.github.alkalimc.Update.UpdateMap;
import io.github.alkalimc.Update.UpdateUserInfo;
import io.github.alkalimc.Update.Lose;
import io.github.alkalimc.User.Log;
import io.github.alkalimc.Info.UserInfo;
import io.github.alkalimc.Info.WinInfo;
import io.github.nofe1248.map.map.Map;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static Socket socket = null;
    private boolean listening = false;
    private Map map;

    public void start(Map map) {
        this.map = map;
        this.listening = true;
        try (ServerSocket serverSocket = new ServerSocket(23456)) {
            while (this.listening) {
                socket = serverSocket.accept();
            }
        } catch (IOException e) {
            Log.writeLogToFile("IOException: " + e.getMessage());
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
                this.listening = false;
            } catch (IOException e) {
                Log.writeLogToFile("IOException: " + e.getMessage());
            }
        }
    }

    public <T> void sendObject(T data) {
        if (socket != null && socket.isConnected()) {
            try (OutputStream outputStream = socket.getOutputStream();
                 ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
                objectOutputStream.writeObject(data);
            } catch (IOException e) {
                Log.writeLogToFile("IOException: " + e.getMessage());
            }
        }
    }

    public void receiveObject() {
        while (this.listening) {
            if (socket != null && socket.isConnected()) {
                try (InputStream inputStream = socket.getInputStream();
                     ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
                    Object response = objectInputStream.readObject();

                    if (response instanceof Map) {
                        new UpdateMap((Map) response);
                    }
                    else if (response instanceof UserInfo) {
                        new UpdateUserInfo((UserInfo) response);
                    }
                    else if (response instanceof WinInfo) {
                        new Lose((WinInfo) response);
                    }
                    else {
                        Log.writeLogToFile("Received object is not a valid type. Received type: " + response.getClass().getName());
                    }
                } catch (IOException | ClassNotFoundException e) {
                    Log.writeLogToFile("Exception: " + e.getMessage());
                }
            }
        }
    }
}