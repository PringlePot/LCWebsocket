package me.pringles.lcwebsocketclient.auth.packet;

import me.pringles.lcwebsocketclient.auth.AuthWebsocket;

public abstract class AuthPacket extends Packet{

    public abstract void handle(AuthWebsocket websocket);

    public abstract String getName();
}
