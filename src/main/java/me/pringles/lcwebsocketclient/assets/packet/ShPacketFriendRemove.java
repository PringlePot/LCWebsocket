package me.pringles.lcwebsocketclient.assets.packet;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.pringles.lcwebsocketclient.assets.AssetsPacket;
import me.pringles.lcwebsocketclient.assets.Websocket;
import me.pringles.lcwebsocketclient.util.ByteBufWrapper;

import java.io.IOException;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShPacketFriendRemove extends AssetsPacket {
    private String playerId;

    @Override
    public void write(ByteBufWrapper buf) {
        buf.writeString(this.playerId);
    }

    @Override
    public void read(ByteBufWrapper buf) throws IOException {
        this.playerId = buf.readStringFromBuffer(52);
    }

    @Override
    public void handle(Websocket websocket) {
        websocket.handleFriendRemove(this);
    }
}
