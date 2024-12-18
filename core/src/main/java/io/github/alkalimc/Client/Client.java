package io.github.alkalimc.Client;

import io.github.alkalimc.User.Log;
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

    public Map sendData(String data) {
        if (socket != null && socket.isConnected()) {
            try {
                // 获取输出流，并向服务器发送数据
                OutputStream outputStream = socket.getOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                objectOutputStream.writeObject(data);  // 向服务器发送请求数据

                // 获取输入流，从服务器接收响应
                InputStream inputStream = socket.getInputStream();
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

                // 读取服务器返回的 Map 对象
                Object response = objectInputStream.readObject();

                // 返回接收到的 Map 对象
                return (Map) response;
            } catch (IOException | ClassNotFoundException e) {
                Log.writeLogToFile("IOException: " + e.getMessage());
            }
        }
        return null;  // 如果连接关闭或发生异常，则返回 null
    }
}
