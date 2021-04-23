package me.pringles.lcwebsocketclient.assets.packet;

import com.google.common.collect.ImmutableList;
import lombok.*;
import me.pringles.lcwebsocketclient.assets.AssetsPacket;
import me.pringles.lcwebsocketclient.assets.Websocket;
import me.pringles.lcwebsocketclient.util.ByteBufWrapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Getter
@ToString
public class SPacketConnection extends AssetsPacket {

    private boolean consoleAllowed;
    public boolean friendRequestsEnabled;
    public Map<String, ImmutableList<String>> onlineFriends;
    public Map<String, ImmutableList<String>> offlineFriends;

    @Override
    public void write(ByteBufWrapper var1) {

    }

    @Override
    public void read(ByteBufWrapper var1) throws IOException {
        int n;
        this.consoleAllowed = var1.buf().readBoolean();
        this.friendRequestsEnabled = var1.buf().readBoolean();
        int n2 = var1.buf().readInt();
        int n3 = var1.buf().readInt();;
        this.onlineFriends = new HashMap<>();
        for (n = 0; n < n2; ++n) {
            this.onlineFriends.put(var1.readStringFromBuffer(52), ImmutableList.of(var1.readStringFromBuffer(32), String.valueOf(var1.buf().readInt()), var1.readStringFromBuffer(256)));
        }
        this.offlineFriends = new HashMap<>();
        for (n = 0; n < n3; ++n) {
            this.offlineFriends.put(var1.readStringFromBuffer(52), ImmutableList.of(var1.readStringFromBuffer(32), String.valueOf(var1.buf().readLong())));
        }
        System.out.println(this);
    }

    @Override
    public void handle(Websocket var1) {
        var1.handleConnection(this);
    }
}
