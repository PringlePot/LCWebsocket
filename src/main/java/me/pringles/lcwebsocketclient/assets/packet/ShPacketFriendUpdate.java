package me.pringles.lcwebsocketclient.assets.packet;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import me.pringles.lcwebsocketclient.assets.AssetsPacket;
import me.pringles.lcwebsocketclient.assets.Websocket;
import me.pringles.lcwebsocketclient.util.ByteBufWrapper;

import java.io.IOException;

@AllArgsConstructor
@NoArgsConstructor
public class ShPacketFriendUpdate extends AssetsPacket {
    private String playerId;
    private String name;
    private long status;
    private boolean online;

    @Override
    public void write(ByteBufWrapper buf) {
        buf.writeString(this.playerId);
        buf.writeString(this.name);
        buf.buf().writeLong(status);
        buf.buf().writeBoolean(online);
    }

    @Override
    public void read(ByteBufWrapper buf) throws IOException {
        this.playerId = buf.readStringFromBuffer(52);
        this.name = buf.readStringFromBuffer(32);
        this.status = buf.buf().readLong();
        this.online = buf.buf().readBoolean();
    }

    @Override
    public void handle(Websocket var1) {

    }
}
