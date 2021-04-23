package me.pringles.lcwebsocketclient.assets.packet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.pringles.lcwebsocketclient.assets.AssetsPacket;
import me.pringles.lcwebsocketclient.assets.Websocket;
import me.pringles.lcwebsocketclient.util.ByteBufWrapper;

import java.io.IOException;

@AllArgsConstructor
@NoArgsConstructor
public class ShPacketConsole extends AssetsPacket {
    @Getter
    private String output;

    @Override
    public void write(ByteBufWrapper buf) {
        buf.writeString(this.output);
    }

    @Override
    public void read(ByteBufWrapper buf) throws IOException {
        this.output = buf.readStringFromBuffer(32767);
    }

    @Override
    public void handle(Websocket websocket) {
        websocket.handleRawConsole(this);
    }

}
