package ru.ryfi.bot.network.packet.server;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import ru.ryfi.bot.network.MinecraftConnection;
import ru.ryfi.bot.network.PacketBuf;
import ru.ryfi.bot.network.packet.PacketIn;
import ru.ryfi.bot.world.entity.Entity;

@Getter
@Setter

@Log4j2
public class PacketEntityPosition implements PacketIn {
    int entid;
    double x;
    double y;
    double z;
    boolean onground;
    @Override
    public void read(PacketBuf buf) {
        entid = buf.readVarInt();
        x = buf.readShort() / 4096.0D;
        y = buf.readShort() / 4096.0D;
        z = buf.readShort() / 4096.0D;
        onground = buf.readBoolean();
    }

    @Override
    public void handle(MinecraftConnection connection) {
        Entity entity = connection.getBot().getWorld().getEntityById(entid);
        if(entity == null) return;

        entity.getLocation().setX(entity.getLocation().getX() + x);
        entity.getLocation().setY(entity.getLocation().getY() + y);
        entity.getLocation().setZ(entity.getLocation().getZ() + z);
    }

}
