package io.github.alkalimc.Server;

import io.github.alkalimc.User.Log;
import io.github.nofe1248.map.map.Map;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    Socket socket = null;

    public boolean Server(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(11451)) {
            while (true) {
                socket = serverSocket.accept();
                return true;
            }
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

    private static void handleClient(Socket socket) {
        try (
                InputStream inputStream = socket.getInputStream();
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                OutputStream outputStream = socket.getOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)
        ) {
            // 从客户端接收数据
            Object requestData = objectInputStream.readObject();

            //创建一个 Map 对象作为响应
            Map responseMap = new Map("");  // 这里可以构建具体的 Map 对象，进行一些处理

            // 向客户端发送 Map 对象
            objectOutputStream.writeObject(responseMap);
            System.out.println("Sent response to client");

        } catch (IOException | ClassNotFoundException e) {
            Log.writeLogToFile("IOException: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                Log.writeLogToFile("IOException: " + e.getMessage());
            }
        }
    }
}
