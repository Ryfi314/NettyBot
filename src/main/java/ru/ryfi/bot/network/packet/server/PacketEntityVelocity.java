/*
 * Ryfi  2022.
 */

package ru.ryfi.bot.network.packet.server;

import ru.ryfi.bot.network.MinecraftConnection;
import ru.ryfi.bot.network.PacketBuf;
import ru.ryfi.bot.network.packet.PacketIn;
import ru.ryfi.bot.world.entity.Entity;

public class PacketEntityVelocity implements PacketIn {
    int entId;
    short x,y,z;
    @Override
    public void read(PacketBuf buf) {
        entId = buf.readVarInt();
        x = buf.readShort();
        y = buf.readShort();
        z = buf.readShort();
    }

    @Override
    public void handle(MinecraftConnection connection) {
        Entity entity = connection.getBot().getWorld().getEntityById(entId);
        if(entity == null) return;
        entity.setVelocityX(x);
        entity.setVelocityY(y);
        entity.setVelocityZ(z);
    }
}
