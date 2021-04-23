package me.pringles.lcwebsocketclient.assets.packet;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import me.pringles.lcwebsocketclient.assets.AssetsPacket;
import me.pringles.lcwebsocketclient.assets.Websocket;
import me.pringles.lcwebsocketclient.util.ByteBufWrapper;

import java.io.IOException;

@Getter
public class SPacketBulkFriends extends AssetsPacket {

    private String rawRequests;
    private JsonArray friends;

    @Override
    public void write(ByteBufWrapper wrapper) {
    }

    @Override
    public void read(ByteBufWrapper wrapper) throws IOException {
        this.rawRequests = wrapper.readString();
        JsonObject jsonObject = new JsonParser().parse(this.rawRequests).getAsJsonObject();
        System.out.println(jsonObject);
        this.friends = jsonObject.getAsJsonArray("bulk");
    }

    @Override
    public void handle(Websocket websocket) {
        //websocket.handleAuthentication(this);
    }

}

