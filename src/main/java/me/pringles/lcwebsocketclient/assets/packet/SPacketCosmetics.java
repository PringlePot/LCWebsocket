package me.pringles.lcwebsocketclient.assets.packet;

import me.pringles.lcwebsocketclient.assets.AssetsPacket;

import me.pringles.lcwebsocketclient.assets.Websocket;
import me.pringles.lcwebsocketclient.util.ByteBufWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SPacketCosmetics extends AssetsPacket {
    private UUID target;
    private List<Integer> cosmetics;
    private int color = -1;
    private boolean thing;
    private boolean thing2;
    private boolean thing3;

    @Override
    public void write(ByteBufWrapper var1) {

    }

    @Override
    public void read(ByteBufWrapper var1) {
       target = new UUID(var1.buf().readLong(), var1.buf().readLong());
       int n = var1.readVarInt();
       cosmetics = new ArrayList<Integer>(n);
        for (int i = 0; i < n; ++i) {
            int n2 = var1.readVarInt();
            boolean bl = var1.buf().readBoolean();
            System.out.println("Got cosmetic with id: " + n2 + " enabled: "  + bl);
            try {
                this.cosmetics.add(n2);
                continue;
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        this.color = var1.buf().readInt();
        this.thing = var1.buf().readBoolean();
        this.thing2 = var1.buf().readBoolean();
        //this.thing3 = var1.buf().readBoolean();
    }

    @Override
    public void handle(Websocket var1) {

    }


    @Override
    public String toString() {
        return "SPacketCosmetics{" +
                "target=" + target +
                ", cosmetics=" + cosmetics +
                ", color=" + color +
                ", thing=" + thing +
                ", thing2=" + thing2 +
                ", thing3=" + thing3 +
                '}';
    }
}
