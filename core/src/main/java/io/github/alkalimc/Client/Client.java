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
    private Socket socket = null;
    private boolean listening = false;
    private static boolean firstMap = false;

    public Client(String ip) {
        try {
            this.socket = new Socket(ip, 23456);
            this.listening = true;
            this.firstMap = true;
        } catch (UnknownHostException e) {
            Log.writeLogToFile("UnknownHostException: " + e.getMessage());
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
                this.firstMap = false;
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
                        if (this.firstMap) {
                            new GetMap((Map) response);
                            this.firstMap = false;
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
