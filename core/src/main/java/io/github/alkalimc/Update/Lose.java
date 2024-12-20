package io.github.alkalimc.Update;

import io.github.alkalimc.Server.Server;
import io.github.alkalimc.Info.WinInfo;

public class Lose {
    public Lose(WinInfo info) {
        //在这里实现lose的方法
    }
    public void win(WinInfo info) {
        Server server = new Server();
        server.sendObject(info);
    }
}
