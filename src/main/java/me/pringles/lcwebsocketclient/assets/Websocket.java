package me.pringles.lcwebsocketclient.assets;

import io.netty.buffer.Unpooled;
import lombok.Getter;
import me.pringles.lcwebsocketclient.assets.packet.*;
import me.pringles.lcwebsocketclient.assets.user.Status;
import me.pringles.lcwebsocketclient.util.ByteBufWrapper;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Websocket extends WebSocketClient {

    private ServerStatus status = ServerStatus.DISCONNECTED;
    private String server;
    @Getter
    public static String lastMessage;

    @Getter
    public static Websocket instance;

    public Websocket(final Map map) throws URISyntaxException {
        super(new URI("wss://assetserver.lunarclientprod.com/connect"), new Draft_6455(), map, 0);
        server = null;
        instance = this;
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        this.status = ServerStatus.AWAITING_ENCRYPTION_REQUEST;
    }

    @Override
    public void onMessage(String s) {
    }

    @Override
    public void onMessage(ByteBuffer bytes) {
        this.handleIncoming(new ByteBufWrapper(Unpooled.wrappedBuffer(bytes.array())));

    }

    public void handleIncoming(ByteBufWrapper buf) {
        int n = buf.readVarInt();
        Class<? extends AssetsPacket> packetClass = AssetsPacket.REGISTRY.inverse().get(n);
        try {
            AssetsPacket packet = packetClass == null ? null : packetClass.newInstance();
            if (packet == null) {
                System.out.println("Got unknown packet: " + n);
                return;
            }
            System.out.println("Got packet: " + packet);
            packet.read(buf);
            packet.handle(this);
        } catch (Exception exception) {
            System.out.println("Error from: " + packetClass);
            exception.printStackTrace();
        }
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        System.out.println(i + s);
        this.status = ServerStatus.DISCONNECTED;
    }

    public void handleAuthentication(SPacketAuthentication packetAuthentication) {
        this.status = ServerStatus.AUTHENTICATING;
    }

    public void sendConsoleMessage(String command){
        sendPacket(new ShPacketConsole(command));
        System.out.println("Sending " + command + " to lunar console");
    }

    @Override
    public void onError(Exception e) {
        e.printStackTrace();
    }

    public void sendPacket(AssetsPacket packet) {
        if (!this.isOpen()) {
            return;
        }
        ByteBufWrapper buf = new ByteBufWrapper(Unpooled.buffer());
        buf.writeVarInt(AssetsPacket.REGISTRY.get(packet.getClass()));
        try {
            packet.write(buf);
            byte[] bytes = new byte[buf.buf().readableBytes()];
            buf.buf().readBytes(bytes);
            buf.buf().release();
            this.send(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setServer(String server) {
        final Matcher ipRegex = Pattern.compile("(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)").matcher(server);
        ;
        this.server = server;
        if (!ipRegex.find()) {
            //Ip not numeric.. Sending it.
            this.sendPacket(new ShPacketStatusUpdate("", server));
        } else {
            this.sendPacket(new ShPacketStatusUpdate("", "_numeric_"));
        }
    }

    public void handleConnection(SPacketConnection packetConnection) {
        this.status = ServerStatus.READY;
        setServer("hypixel.net");

        new Thread(() -> {
            while (true) {
                sendPacket(new CPacketMods());
                try {
                    Thread.sleep(60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void handleServerChange(ShPacketStatusUpdate packetServerUpdate) {
        System.out.println("Server changed for user: " + packetServerUpdate.getPlayerId() + " to: " + packetServerUpdate.getServer());
    }

    public void handleFriendRequest(ShPacketFriendRequest packetFriendRequest) {
        System.out.println("Got friend request from: " + packetFriendRequest.getName() + ", accepting it.");
        this.sendPacket(new ShPacketFriendRequestUpdate(true, packetFriendRequest.getPlayerId()));
    }

    public void handleMessage(ShPacketMessage packetMessage) {
        System.out.println("Got message: " + packetMessage.getMessage() + " From: " + packetMessage.getPlayerId() + " Setting user as console target.");
        lastMessage = packetMessage.getPlayerId();

        if(packetMessage.getMessage().startsWith("!")){
            String[] args = packetMessage.getMessage().substring("!".length()).split(" ");
            if(args[0].equals("setserver")){
                setServer(args[1]);
                sendPacket(new ShPacketMessage(packetMessage.getPlayerId(), "Set server to " + args[1]));
            }

        }
    }

    public void handleConsole(SPacketFormattedConsoleOutput packetFormattedConsoleOutput) {
        System.out.println("[" + packetFormattedConsoleOutput.getPrefix() + "] " + packetFormattedConsoleOutput.getContent());
    }

    public void handleRawConsole(ShPacketConsole packetConsole) {
        System.out.println(packetConsole.getOutput());
    }

    public void handleFriendUpdate(ShPacketFriendUpdate packetFriendUpdate) {
        if(packetFriendUpdate.getStatus() < 10L){
            Status status = Status.ONLINE;
            for(Status status1 : Status.values()){
                if(status1.ordinal() == packetFriendUpdate.getStatus()){
                    status = status1;
                }
            }
            System.out.println(packetFriendUpdate.getName() + " went on status " + status.getName());
            return;
        }
        System.out.println(packetFriendUpdate.getName() + " went offline! they were last seen at: " + new Date(packetFriendUpdate.getStatus()));
    }

    public void handleFriendRemove(ShPacketFriendRemove packetClientFriendRemove){
        System.out.println(packetClientFriendRemove.getPlayerId() + " Removed us as friend! Sending new friend request.");
        this.sendPacket(new ShPacketFriendRequest(packetClientFriendRemove.getPlayerId(), ""));
    }

    public void handleFriendUpdate(SPacketFriendUpdate packetFriendUpdate){
        System.out.println("Got friend update, " + packetFriendUpdate.toString());
    }
}
