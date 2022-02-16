package ru.ryfi.bot.network.packet.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import ru.ryfi.bot.network.MinecraftConnection;
import ru.ryfi.bot.network.PacketBuf;
import ru.ryfi.bot.network.packet.PacketIn;
import ru.ryfi.bot.world.position.WorldLocation;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Log4j2
public class PacketSpawnPositionIn implements PacketIn {
    private WorldLocation worldLocation = new WorldLocation();
    @Override
    public void read(PacketBuf buf) {
        worldLocation = buf.readPosition();
    }

    @Override
    public void handle(MinecraftConnection connection) {

    }
}
