package me.pringles.lcwebsocketclient;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import me.pringles.lcwebsocketclient.assets.Websocket;
import me.pringles.lcwebsocketclient.assets.packet.ShPacketConsole;
import me.pringles.lcwebsocketclient.assets.packet.ShPacketMessage;
import me.pringles.lcwebsocketclient.auth.AuthWebsocket;
import me.pringles.lcwebsocketclient.util.Config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.function.Consumer;

public class Main {
    public static Gson gson = new GsonBuilder().create();
    @Getter private static Config config;

    public static void main(String[] args) throws IOException {
        config = new Config();
        new Thread(() -> connectToAuth(s -> {
            try {
                System.out.println("Got auth key, connecting to assets websocket");
                Websocket websocket = new Websocket(ImmutableMap.builder().put("accountType", "MOJANG").put("version", "pringles is pog").put("gitCommit", "who cares").put("branch", "master").put("os", System.getProperty("os.name")).put("arch", "").put("server", "pornhub.com").put("launcherVersion", "2.4.1").put("username", config.getUsername()).put("playerId", config.getUuid()).put("Authorization", s).build());
                websocket.connect();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        })).start();
        while (true) {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(System.in));

            String message = reader.readLine();
            Websocket.instance.sendPacket(new ShPacketConsole(message));
        }
    }

    public static void connectToAuth(Consumer<String> consumer) {
        try {
            new AuthWebsocket(ImmutableMap.builder().put("username", config.getUsername()).put("playerId", config.getUuid()).build(), consumer).connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            consumer.accept(null);
        }

    }
}
