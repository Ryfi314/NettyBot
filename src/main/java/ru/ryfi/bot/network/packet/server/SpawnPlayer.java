package ru.ryfi.bot.network.packet.server;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import ru.ryfi.bot.network.MinecraftConnection;
import ru.ryfi.bot.network.PacketBuf;
import ru.ryfi.bot.network.packet.PacketIn;
import ru.ryfi.bot.world.entity.PlayerEntity;
import ru.ryfi.bot.world.position.WorldLocation;

import java.util.UUID;
@Getter
@Setter
@Log4j2
public class SpawnPlayer implements PacketIn {
    int entid;
    UUID uuid;
    double x;
    double y;
    double z;

    @Override
    public void read(PacketBuf buf) {
        entid = buf.readVarInt();
        uuid = new UUID(buf.readLong(),buf.readLong());
        x = buf.readDouble();
        y = buf.readDouble();
        z = buf.readDouble();
    }

    @Override
    public void handle(MinecraftConnection connection) {
        PlayerEntity player = new PlayerEntity(connection.bot.getWorld(), entid,uuid);
        connection.getBot().getWorld().addEntity(player);
        player.setLocation(new WorldLocation(x,y,z));

    }
}
