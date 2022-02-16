package ru.ryfi.bot.network.packet.client.play;

import ru.ryfi.bot.network.PacketBuf;
import ru.ryfi.bot.network.packet.PacketOut;

public class PacketKeepAliveOut implements PacketOut {
    public PacketKeepAliveOut(long id){
        this.id = id;
    }
    private long id;
    @Override
    public void write(PacketBuf buf) {
        buf.writeLong(id);
    }
}
