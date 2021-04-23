package me.pringles.lcwebsocketclient.auth.packet.auth;

import com.google.gson.JsonObject;
import me.pringles.lcwebsocketclient.auth.AuthWebsocket;
import me.pringles.lcwebsocketclient.auth.packet.AuthPacket;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class SPacketEncryptionRequest extends AuthPacket {

    public PublicKey publicKey;
    public  byte[] randomBytes;
    @Override
    public void handle(AuthWebsocket websocket) {
        websocket.handleEncryptionRequest(this);
    }

    @Override
    public String getName() {
        return "SPacketEncryptionRequest";
    }

    public void getFromJson(JsonObject jsonObject){
        Base64.Decoder decoder = Base64.getUrlDecoder();
        this.publicKey =getPublicKey(decoder.decode(jsonObject.get("publicKey").getAsString()));
        this.randomBytes = decoder.decode(jsonObject.get("randomBytes").getAsString());

    }
    @Override
    public void handle1(Object var1) {
        this.getFromJson(((JsonObject) var1));
    }

    @Override
    public void handle2(Object var1) {
        this.getFromJson(((JsonObject) var1));
    }

    public PublicKey getPublicKey(byte[] key){
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(key);
        try {
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePublic(x509EncodedKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }
}
