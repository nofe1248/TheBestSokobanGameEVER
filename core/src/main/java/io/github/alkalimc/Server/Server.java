package io.github.alkalimc.Server;

import io.github.alkalimc.Update.UpdateMap;
import io.github.alkalimc.Update.UpdateUserInfo;
import io.github.alkalimc.User.Log;
import io.github.alkalimc.User.UserInfo;
import io.github.nofe1248.map.map.Map;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private Socket socket = null;
    private boolean listening = false;
    private Map map;

    public Server(Map map) {
        this.map = map;
        this.listening = true;
        try (ServerSocket serverSocket = new ServerSocket(23456)) {
            while (this.listening) {
                this.socket = serverSocket.accept();
            }
        } catch (IOException e) {
            Log.writeLogToFile("IOException: " + e.getMessage());
        }
    }

    public boolean isConnected() {
        if (this.socket != null && this.socket.isConnected()) {
            return true;
        }
        return false;
    }

    public void disconnect() {
        if (this.socket != null && this.socket.isConnected()) {
            try {
                this.socket.close();
                this.listening = false;
            } catch (IOException e) {
                Log.writeLogToFile("IOException: " + e.getMessage());
            }
        }
    }

    public <T> void sendObject(T data) {
        if (this.socket != null && this.socket.isConnected()) {
            try (OutputStream outputStream = this.socket.getOutputStream();
                 ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
                objectOutputStream.writeObject(data);
            } catch (IOException e) {
                Log.writeLogToFile("IOException: " + e.getMessage());
            }
        }
    }

    public Object receiveObject() {
        while (this.listening) {
            if (this.socket != null && this.socket.isConnected()) {
                try (InputStream inputStream = this.socket.getInputStream();
                     ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
                    Object response = objectInputStream.readObject();

                    if (response instanceof Map) {
                        UpdateMap.updateMap((Map) response);
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