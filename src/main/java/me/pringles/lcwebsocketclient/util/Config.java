package me.pringles.lcwebsocketclient.util;

import lombok.Getter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Getter
public class Config {
    private String uuid;
    private String username;
    private String email;
    private String password;

    public Config(){
        Properties properties = new Properties();
        InputStream inputStream = null;
        try{
            inputStream = new FileInputStream("config.properties");
            properties.load(inputStream);

            this.uuid = properties.getProperty("uuid");
            this.username = properties.getProperty("user");
            this.email = properties.getProperty("email");
            this.password = properties.getProperty("password");
        }catch (Exception ignored){}
        finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
