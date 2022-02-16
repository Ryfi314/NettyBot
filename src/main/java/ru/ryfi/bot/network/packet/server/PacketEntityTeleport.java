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
public class PacketEntityTeleport implements PacketIn {

    int entid;
    double x;
    double y;
    double z;
    boolean onground;
    @Override
    public void read(PacketBuf buf) {
        entid = buf.readVarInt();
        x = buf.readDouble();
        y = buf.readDouble();
        z = buf.readDouble();
    }

    @Override
    public void handle(MinecraftConnection connection) {
        Entity entity = connection.getBot().getWorld().getEntityById(entid);
        if(entity == null) return;
        entity.getLocation().setX(x);
        entity.getLocation().setY(y);
        entity.getLocation().setZ(z);



    }
}
