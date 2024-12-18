package io.github.alkalimc.Test;

import io.github.alkalimc.Client.Client;

import java.net.PasswordAuthentication;
import java.util.Scanner;

public class TestClient {
    public static void main(String[] args) {
        Client client = new Client();
        test(client);

        System.out.println("Press Enter to execute testConnect...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        testConnect(client);

        System.out.println("Press Enter to execute testRecevieObject...");
        scanner.nextLine();

        testRecevieObject(client);
    }
    public static void test(Client client) {
        if (client.connect("0.0.0.0")) {
            System.out.println("Client Start Successfully");
        }
        else {
            System.out.println("Client Start Failed");
        }
    }
    public static void testConnect(Client client) {
        if (client.isConnected()) {
            System.out.println("Client Connected Successfully");
        }
        else {
            System.out.println("Client Connect Failed");
        }
    }
    public static void testRecevieObject(Client client) {
        client.receiveObject();
    }
}
