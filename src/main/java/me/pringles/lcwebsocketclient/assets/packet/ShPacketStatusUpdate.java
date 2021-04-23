package me.pringles.lcwebsocketclient.assets.packet;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import me.pringles.lcwebsocketclient.assets.AssetsPacket;
import me.pringles.lcwebsocketclient.assets.Websocket;
import me.pringles.lcwebsocketclient.util.ByteBufWrapper;

import java.io.IOException;

@AllArgsConstructor
@NoArgsConstructor
public class ShPacketStatusUpdate extends AssetsPacket {
    private String playerId;
    private String server;


    @Override
    public void write(ByteBufWrapper buf) {
        buf.writeString(this.playerId);
        buf.writeString(this.server);
    }

    @Override
    public void read(ByteBufWrapper buf) throws IOException {
        this.playerId = buf.readStringFromBuffer(52);
        this.server = buf.readStringFromBuffer(100);
    }

    public void handle(Websocket websocket) {
        websocket.handleServerChange(this);
    }

    public String getServer() {
        return this.server;
    }

    public String getPlayerId() {
        return this.playerId;
    }
}