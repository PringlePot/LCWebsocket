package me.pringles.lcwebsocketclient.assets.packet;


import me.pringles.lcwebsocketclient.assets.AssetsPacket;
import me.pringles.lcwebsocketclient.assets.Websocket;
import me.pringles.lcwebsocketclient.util.ByteBufWrapper;

import java.io.IOException;

public class ShPacketFriendRequest
        extends AssetsPacket {
    private String playerId;
    private String name;

    public ShPacketFriendRequest() {
    }

    public ShPacketFriendRequest(String playerId, String name) {
        this.playerId = playerId;
        this.name = name;
    }

    @Override
    public void write(ByteBufWrapper buf) {
        buf.writeString(this.playerId);
        buf.writeString(this.name);
    }

    @Override
    public void read(ByteBufWrapper buf) throws IOException {
        this.playerId = buf.readStringFromBuffer(52);
        this.name = buf.readStringFromBuffer(32);
    }

    @Override
    public void handle(Websocket websocket) {
        websocket.handleFriendRequest(this);
    }

    public String getPlayerId() {
        return this.playerId;
    }

    public String getName() {
        return this.name;
    }
}

