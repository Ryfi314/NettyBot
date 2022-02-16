package ru.ryfi.bot.network.packet.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.ryfi.bot.network.MinecraftConnection;
import ru.ryfi.bot.network.PacketBuf;
import ru.ryfi.bot.network.packet.PacketIn;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class PacketKeepAliveIn implements PacketIn {
    private long id;
    @Override
    public void read(PacketBuf buf) {
        this.id = buf.readLong();
    }

    @Override
    public void handle(MinecraftConnection connection) {

    }
}
