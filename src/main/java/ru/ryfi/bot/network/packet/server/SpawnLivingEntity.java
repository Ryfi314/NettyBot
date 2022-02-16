package ru.ryfi.bot.network.packet.server;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import ru.ryfi.bot.network.MinecraftConnection;
import ru.ryfi.bot.network.PacketBuf;

import ru.ryfi.bot.network.packet.PacketIn;
import ru.ryfi.bot.world.entity.Entity;
import ru.ryfi.bot.world.position.WorldLocation;

import java.util.UUID;

@Getter
@Setter
@Log4j2
public class SpawnLivingEntity implements PacketIn {
    int entid;
    UUID uuid;
    int type;
    double x;
    double y;
    double z;

    @Override
    public void read(PacketBuf buf) {
    entid = buf.readVarInt();
    uuid = new UUID(buf.readLong(),buf.readLong());
    type = buf.readVarInt();
    x = buf.readDouble();
    y = buf.readDouble();
    z = buf.readDouble();
    }

    @Override
    public void handle(MinecraftConnection connection) {
        Entity entity = new Entity(uuid,connection.getBot().getWorld(), entid);
        entity.setLocation(new WorldLocation(x,y,z));
        connection.getBot().getWorld().addEntity(entity);
    }
}
