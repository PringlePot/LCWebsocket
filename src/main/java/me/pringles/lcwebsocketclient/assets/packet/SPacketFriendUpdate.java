package me.pringles.lcwebsocketclient.assets.packet;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import me.pringles.lcwebsocketclient.assets.AssetsPacket;
import me.pringles.lcwebsocketclient.assets.Websocket;
import me.pringles.lcwebsocketclient.util.ByteBufWrapper;

import java.io.IOException;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class SPacketFriendUpdate extends AssetsPacket {
    private String playerId;
    private String name;
    private boolean friend;

    @Override
    public void write(ByteBufWrapper wrapper) {
        wrapper.writeString(this.playerId);
        wrapper.writeString(this.name);
        wrapper.buf().writeBoolean(this.friend);
    }

    @Override
    public void read(ByteBufWrapper wrapper) throws IOException {
        this.playerId = wrapper.readStringFromBuffer(52);
        this.name = wrapper.readStringFromBuffer(32);
        this.friend = wrapper.buf().readBoolean();
    }

    @Override
    public void handle(Websocket websocket) {
        websocket.handleFriendUpdate(this);
    }

}

