package me.pringles.lcwebsocketclient.assets.packet;


import me.pringles.lcwebsocketclient.assets.AssetsPacket;
import me.pringles.lcwebsocketclient.assets.Websocket;
import me.pringles.lcwebsocketclient.util.ByteBufWrapper;

import java.util.HashMap;
import java.util.Map;

public class CPacketMods extends AssetsPacket {

    private final String string;
    private final Map<String, Boolean> map ;


    public CPacketMods(){
        map = new HashMap<>();
        this.string = "";
        this.map.put("skyblockaddons", false);
    }
    @Override
    public void write(ByteBufWrapper wrapper) {
        wrapper.writeVarInt(map.size());
        this.map.forEach((s, aBoolean) -> {
            wrapper.writeString(s);
            wrapper.buf().writeBoolean(aBoolean);
        });
        wrapper.writeString(string);
    }

    @Override
    public void read(ByteBufWrapper wrapper) {
    }

    @Override
    public void handle(Websocket websocket) {
    }

}

