package me.pringles.lcwebsocketclient.assets.packet;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import me.pringles.lcwebsocketclient.assets.AssetsPacket;
import me.pringles.lcwebsocketclient.assets.Websocket;
import me.pringles.lcwebsocketclient.util.ByteBufWrapper;

import java.io.IOException;

@AllArgsConstructor
@NoArgsConstructor
public class ShPacketMessage
        extends AssetsPacket {
    private String playerId;
    private String message;

    @Override
    public void write(ByteBufWrapper buf) {
        buf.writeString(this.playerId);
        buf.writeString(this.message);
    }

    @Override
    public void read(ByteBufWrapper buf) throws IOException {
        this.playerId = buf.readStringFromBuffer(52);
        this.message = buf.readStringFromBuffer(1024);
    }

    @Override
    public void handle(Websocket websocket) {
        websocket.handleMessage(this);
    }

    public String getPlayerId() {
        return this.playerId;
    }

    public String getMessage() {
        return this.message;
    }
}

