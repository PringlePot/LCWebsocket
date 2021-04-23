package me.pringles.lcwebsocketclient.assets.packet;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import me.pringles.lcwebsocketclient.assets.AssetsPacket;
import me.pringles.lcwebsocketclient.assets.Websocket;
import me.pringles.lcwebsocketclient.util.ByteBufWrapper;

import java.io.IOException;

@AllArgsConstructor
@NoArgsConstructor
public class SPacketFormattedConsoleOutput
        extends AssetsPacket {
    private String prefix;
    private String content;

    @Override
    public void write(ByteBufWrapper buf) {
        buf.writeString(this.prefix);
        buf.writeString(this.content);
    }

    @Override
    public void read(ByteBufWrapper buf) throws IOException {
        this.prefix = buf.readStringFromBuffer(128);
        this.content = buf.readStringFromBuffer(512);
    }

    @Override
    public void handle(Websocket websocket) {
        websocket.handleConsole(this);
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getContent() {
        return this.content;
    }
}
