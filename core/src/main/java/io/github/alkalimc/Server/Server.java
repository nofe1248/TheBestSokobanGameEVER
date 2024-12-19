package io.github.alkalimc.Server;

import io.github.alkalimc.Update.UpdateMap;
import io.github.alkalimc.Update.UpdateUserInfo;
import io.github.alkalimc.User.Log;
import io.github.alkalimc.User.UserInfo;
import io.github.nofe1248.map.map.Map;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static Socket socket = null;
    private static boolean listening = false;
    private static ExecutorService executorService = Executors.newSingleThreadExecutor();

    public static boolean connect() {
        try (ServerSocket serverSocket = new ServerSocket(23456)) {
            while (true) {
                socket = serverSocket.accept();
                listening = true;
                executorService.submit(Server::receiveObject);
                return true;
            }
        } catch (IOException e) {
            Log.writeLogToFile("IOException: " + e.getMessage());
            listening = false;
            return false;
        }
    }

    public static boolean isConnected() {
        if (socket != null && socket.isConnected()) {
            return true;
        }
        return false;
    }

    public static void disconnect() {
        if (socket != null && socket.isConnected()) {
            try {
                socket.close();
                listening = false;
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