package me.pringles.lcwebsocketclient.assets.packet;


import me.pringles.lcwebsocketclient.assets.AssetsPacket;
import me.pringles.lcwebsocketclient.assets.Websocket;
import me.pringles.lcwebsocketclient.util.ByteBufWrapper;

public class SPacketAuthentication extends AssetsPacket {

    @Override
    public void write(ByteBufWrapper wrapper) {
    }

    @Override
    public void read(ByteBufWrapper wrapper) {
    }

    @Override
    public void handle(Websocket websocket) {
        websocket.handleAuthentication(this);
    }

}

