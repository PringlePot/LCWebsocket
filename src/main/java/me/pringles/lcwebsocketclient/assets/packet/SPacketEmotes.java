package me.pringles.lcwebsocketclient.assets.packet;


import me.pringles.lcwebsocketclient.assets.AssetsPacket;
import me.pringles.lcwebsocketclient.assets.Websocket;
import me.pringles.lcwebsocketclient.util.ByteBufWrapper;

import java.util.ArrayList;
import java.util.List;

public class SPacketEmotes extends AssetsPacket {
    private List<Integer> list1;
    private List<Integer> list2;

    @Override
    public void write(ByteBufWrapper wrapper) {
    }

    @Override
    public void read(ByteBufWrapper wrapper) {
        int n;
        int n2 = wrapper.readVarInt();
        this.list1 = new ArrayList<Integer>();
        for (n = 0; n < n2; ++n) {
            this.list1.add(wrapper.readVarInt());
        }
        this.list2 = new ArrayList<>();
        for (n = 0; n < n2; ++n) {
            this.list2.add(wrapper.readVarInt());
        }
    }

    @Override
    public void handle(Websocket websocket) {

    }

}

