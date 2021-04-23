package me.pringles.lcwebsocketclient.auth;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import me.pringles.lcwebsocketclient.Main;
import me.pringles.lcwebsocketclient.auth.packet.AuthPacket;
import me.pringles.lcwebsocketclient.auth.packet.auth.CPacketEncryptionResponse;
import me.pringles.lcwebsocketclient.auth.packet.auth.SPacketAuthenticatedRequest;
import me.pringles.lcwebsocketclient.auth.packet.auth.SPacketEncryptionRequest;
import me.pringles.lcwebsocketclient.util.AuthUtil;
import me.pringles.lcwebsocketclient.util.CryptManager;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import javax.crypto.SecretKey;
import java.math.BigInteger;
import java.net.Proxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class AuthWebsocket extends WebSocketClient {

    public final Consumer<String> consumer;

    public AuthWebsocket(final Map map, Consumer<String> consumer) throws URISyntaxException {
        super(new URI("wss://authenticator.lunarclientprod.com"), new Draft_6455(), map, 3000);
        this.consumer = consumer;
        System.out.println("Connecting to authenticator websocket for authentication key.");
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {

    }

    @Override
    public void onMessage(String s) {
        System.out.println(s);
    }

    @Override
    public void onMessage(ByteBuffer bytes) {
        JsonObject jsonObject = new JsonParser().parse(new String(bytes.array())).getAsJsonObject();
        System.out.println(jsonObject.toString());
        handleJson(jsonObject);
    }

    public void handleJson(JsonObject jsonObject) {
        AuthPacket authPacket;
        switch (jsonObject.get("packetType").getAsString()) {
            case "SPacketEncryptionRequest": {
                authPacket = new SPacketEncryptionRequest();
                break;
            }
            case "SPacketAuthenticatedRequest":{
                authPacket = new SPacketAuthenticatedRequest();
                break;
            }
            default:
                return;
        }
        authPacket.handle2(jsonObject);
        authPacket.handle(this);
    }

    public void handleEncryptionRequest(SPacketEncryptionRequest packetEncryptionRequest) {
        PublicKey publicKey = packetEncryptionRequest.publicKey;
        SecretKey secretKey = CryptManager.createNewSharedKey();
        byte[] byArray = CryptManager.getServerIdHash("", publicKey, secretKey);
        if (byArray == null) {
            return;
        }

        String serverId = new BigInteger(byArray).toString(16);
        System.out.println("Server ID: " + serverId);
        YggdrasilUserAuthentication auth = AuthUtil.getAuth(Main.getConfig().getEmail(), Main.getConfig().getPassword());
        try{
            new YggdrasilAuthenticationService(Proxy.NO_PROXY, UUID.randomUUID().toString()).createMinecraftSessionService().joinServer(auth.getSelectedProfile() ,auth.getAuthenticatedToken(), serverId);
        }catch (Exception e){
            e.printStackTrace();
        }
        this.sendPacket(new CPacketEncryptionResponse(secretKey, publicKey, packetEncryptionRequest.randomBytes));
    }

    public void handleAuthenticated(SPacketAuthenticatedRequest packetAuthenticatedRequest){
        this.close();
        this.consumer.accept(packetAuthenticatedRequest.jwtKey);
    }
    public void sendPacket(AuthPacket authPacket) {
        if(!this.isOpen()){
            return;
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("packetType", authPacket.getName());
        try{
            authPacket.handle1(jsonObject);
            this.send(Main.gson.toJson(jsonObject).getBytes(StandardCharsets.UTF_8));
            System.out.println("Sent packet " + jsonObject);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void onClose(int i, String s, boolean b) {
    }

    @Override
    public void onError(Exception e) {
        e.printStackTrace();
    }
}
