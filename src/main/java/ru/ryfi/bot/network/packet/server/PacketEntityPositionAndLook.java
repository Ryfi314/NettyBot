/*
 * Ryfi  2022.
 */

package ru.ryfi.bot.network.packet.server;

import ru.ryfi.bot.network.MinecraftConnection;
import ru.ryfi.bot.network.PacketBuf;
import ru.ryfi.bot.network.packet.PacketIn;
import ru.ryfi.bot.world.entity.Entity;

public class PacketEntityPositionAndLook implements PacketIn {
    int entid;
    double x;
    double y;
    double z;
    float yaw,pitch;
    boolean onground;
    @Override
    public void read(PacketBuf buf) {
        entid = buf.readVarInt();
        x = buf.readShort() / 4096.0D;
        y = buf.readShort() / 4096.0D;
        z = buf.readShort() / 4096.0D;
//        yaw = buf.readFloat();
//        pitch = buf.readFloat();
//        onground = buf.readBoolean();
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
