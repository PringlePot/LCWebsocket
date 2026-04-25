> [!WARNING]
> Lunar has updated their websocket to use protobuf. This means this version does not work anymore.
> I don't plan on updating it.

# LCWebsocket

### What is this?
This is a console version of Lunar Client. It connects to their assets websocket, allowing you to send messages and view cosmetics

### How to use?
Rename the `config.properties.example` to `config.properties` then fill it in. Run it using IntellIJ.

### Why???
I made this so I could play around with the actual websocket, and try to find out how it actually works. 
Most of the work consisted of deobfuscating Lunar Client. And reverse engineering what packets do.

### Credits
[Decencies](https://github.com/Decencies) for helping with with some of the packet names
