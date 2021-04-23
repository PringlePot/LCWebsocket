package me.pringles.lcwebsocketclient.auth.packet.auth;

import com.google.gson.JsonObject;
import me.pringles.lcwebsocketclient.auth.AuthWebsocket;
import me.pringles.lcwebsocketclient.auth.packet.AuthPacket;

public class SPacketAuthenticatedRequest extends AuthPacket {

    public String jwtKey;

    @Override
    public void handle(AuthWebsocket websocket) {
        websocket.handleAuthenticated(this);
    }

    @Override
    public String getName() {
        return "SPacketAuthenticatedRequest";
    }

    public void handleJson(JsonObject jsonObject){
        this.jwtKey = jsonObject.get("jwtKey").getAsString();
    }

    @Override
    public void handle1(Object var1) {
        this.handleJson((JsonObject) var1);
    }

    @Override
    public void handle2(Object var1) {
        this.handleJson((JsonObject) var1);
    }
}
