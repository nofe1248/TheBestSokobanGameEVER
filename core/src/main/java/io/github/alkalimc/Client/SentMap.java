package io.github.alkalimc.Client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import io.github.nofe1248.map.map.Map;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class SentMap {
    public SentMap(Map map) {
        SocketHints hints = new SocketHints();
        Socket client = Gdx.net.newClientSocket(Net.Protocol.TCP, "localhost", 11451, hints);

        try{
            ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
            out.writeObject(map);
            out.close();
        }catch (IOException e) {
            Gdx.app.log("PingPongClient", "An error occurred", e);
        } finally {
            client.dispose();
        }
    }
}
