package io.github.alkalimc.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final int PORT = 11451;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);

            while (true) {
                // 等待客户端连接
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                // 处理客户端请求
                //handleClientRequest(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


