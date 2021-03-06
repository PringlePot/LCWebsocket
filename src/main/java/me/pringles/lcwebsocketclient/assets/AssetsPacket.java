package me.pringles.lcwebsocketclient.assets;


import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import io.netty.buffer.ByteBuf;
import me.pringles.lcwebsocketclient.assets.packet.*;
import me.pringles.lcwebsocketclient.util.ByteBufWrapper;

import java.io.IOException;

public abstract class AssetsPacket {

    public static BiMap<Class<? extends AssetsPacket>, Integer> REGISTRY = HashBiMap.create();

    public abstract void write(ByteBufWrapper var1);

    public abstract void read(ByteBufWrapper var1) throws IOException;

    public abstract void handle(Websocket var1);

    protected void writeKey(ByteBuf byteBuf, byte[] key) {
        byteBuf.writeShort(key.length);
        byteBuf.writeBytes(key);
    }

    protected byte[] readKey(ByteBuf byteBuf) {
        short keySize = byteBuf.readShort();
        if (keySize >= 0) {
            byte[] key = new byte[keySize];
            byteBuf.readBytes(key);
            return key;
        }
        return new byte[0];
    }

    static {
        REGISTRY.put(SPacketAuthentication.class, 1);
        REGISTRY.put(ShPacketConsole.class, 2);
        REGISTRY.put(SPacketFormattedConsoleOutput.class, 3);
        REGISTRY.put(SPacketConnection.class, 4);
        REGISTRY.put(ShPacketMessage.class, 5);
        REGISTRY.put(ShPacketStatusUpdate.class, 6);
        REGISTRY.put(SPacketBulkFriends.class, 7);
        REGISTRY.put(SPacketCosmetics.class, 8);
        REGISTRY.put(ShPacketFriendRequest.class, 9);
        REGISTRY.put(SPacketFriendUpdate.class, 16);
        REGISTRY.put(ShPacketFriendRemove.class, 17);
        REGISTRY.put(ShPacketFriendUpdate.class, 18);
        REGISTRY.put(ShPacketFriendRequestUpdate.class, 21);
        REGISTRY.put(SPacketEmotes.class, 57);
        REGISTRY.put(CPacketMods.class, 64);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
