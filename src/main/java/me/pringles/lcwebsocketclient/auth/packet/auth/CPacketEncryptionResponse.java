package me.pringles.lcwebsocketclient.auth.packet.auth;

import com.google.gson.JsonObject;
import me.pringles.lcwebsocketclient.auth.AuthWebsocket;
import me.pringles.lcwebsocketclient.auth.packet.AuthPacket;
import me.pringles.lcwebsocketclient.util.CryptManager;

import javax.crypto.SecretKey;
import java.security.PublicKey;
import java.util.Base64;

public class CPacketEncryptionResponse extends AuthPacket {

    public final byte[] secretKey;
    public final byte[] publicKey;

    public CPacketEncryptionResponse(SecretKey secretKey, PublicKey publicKey, byte[] bytes) {
        this.secretKey = CryptManager.encryptData(publicKey, secretKey.getEncoded());
        this.publicKey = CryptManager.encryptData(publicKey, bytes);
    }

    @Override
    public void handle(AuthWebsocket websocket) {

    }

    @Override
    public String getName() {
        return "CPacketEncryptionResponse";
    }

    @Override
    public void handle1(Object var1) {
        this.parseJson((JsonObject) var1);

    }

    public void parseJson(JsonObject jsonObject){
        Base64.Encoder encoder = Base64.getUrlEncoder();
        jsonObject.addProperty("secretKey", new String(encoder.encode(this.secretKey)));
        jsonObject.addProperty("publicKey", new String(encoder.encode(this.publicKey)));
    }

    @Override
    public void handle2(Object var1) {
        this.parseJson((JsonObject) var1);
    }
}
