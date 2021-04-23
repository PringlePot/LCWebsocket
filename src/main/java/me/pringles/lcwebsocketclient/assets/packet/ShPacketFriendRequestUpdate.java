package me.pringles.lcwebsocketclient.assets.packet;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import me.pringles.lcwebsocketclient.assets.AssetsPacket;
import me.pringles.lcwebsocketclient.assets.Websocket;
import me.pringles.lcwebsocketclient.util.ByteBufWrapper;

import java.io.IOException;

@NoArgsConstructor
@AllArgsConstructor
public class ShPacketFriendRequestUpdate
        extends AssetsPacket {
    private boolean add;
    private String playerId;


    @Override
    public void write(ByteBufWrapper buf) {
        buf.buf().writeBoolean(this.add);
        buf.writeString(this.playerId);
    }

    @Override
    public void read(ByteBufWrapper buf) throws IOException {
        this.add = buf.buf().readBoolean();
        this.playerId = buf.readStringFromBuffer(52);
    }

    @Override
    public void handle(Websocket websocket) {
    }

    public boolean isAdd() {
        return this.add;
    }

    public String lIIIIIIIIIlIllIIllIlIIlIl() {
        return this.playerId;
    }
}