package me.pringles.lcwebsocketclient.assets.user;

public enum Status {
    ONLINE( "Online"),
    AWAY("Away"),
    BUSY("Busy"),
    OFFLINE( "Offline");

    public final String name;

    Status(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
