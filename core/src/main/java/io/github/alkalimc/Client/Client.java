package io.github.alkalimc.Client;

import io.github.nofe1248.map.map.Map;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private Socket clientSocket = null;
    private String lastConnectedIp = null;
    private static final String CONFIG_FILE = "last_connected_ip.txt"; //最后一次成功连接的ip
    private static final String DEFAULT_IP = "xxx.xxx.xxx.xxx"; //这个要连接到label上去，默认返回这个，如果有上一次连接就返回文件里面存的那个

    //读文件用的
    private void loadLastConnectedIp() {
        try (BufferedReader reader = new BufferedReader(new FileReader(CONFIG_FILE))) {
            lastConnectedIp = reader.readLine();
        } catch (IOException e) {
            lastConnectedIp = null;
        }
    }

    //存文件用的
    private void saveLastConnectedIp() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CONFIG_FILE))) {
            if (lastConnectedIp != null) {
                writer.write(lastConnectedIp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean connect(String ip) {
        try {
            clientSocket = new Socket(ip, 11451);
            lastConnectedIp = ip;
            saveLastConnectedIp();
            return true;
        } catch (UnknownHostException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    public void disconnect() {
        if (clientSocket != null && !clientSocket.isClosed()) {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isConnected() {
        if (clientSocket != null && !clientSocket.isClosed()) {
            return true;
        }
        return false;
    }

    //这个是用来返回上一次成功连接的ip的，我觉得可以在ui里直接回车输入？
    public String getLastConnectedIp() {
        loadLastConnectedIp();
        if (lastConnectedIp == null) {
            return DEFAULT_IP;
        }
        return lastConnectedIp;
    }

    public Map sendData(String data) {
        if (clientSocket != null && !clientSocket.isClosed()) {
            try {
                // 获取输出流，并向服务器发送数据
                OutputStream outputStream = clientSocket.getOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                objectOutputStream.writeObject(data);  // 向服务器发送请求数据

                // 获取输入流，从服务器接收响应
                InputStream inputStream = clientSocket.getInputStream();
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

                // 读取服务器返回的 Map 对象
                Object response = objectInputStream.readObject();

                // 返回接收到的 Map 对象
                return (Map) response;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;  // 如果连接关闭或发生异常，则返回 null
    }

    }
