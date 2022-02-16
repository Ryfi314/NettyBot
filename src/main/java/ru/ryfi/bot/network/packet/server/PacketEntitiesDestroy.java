/*
 * Ryfi  2022.
 */

package ru.ryfi.bot.network.packet.server;

import lombok.extern.log4j.Log4j2;
import ru.ryfi.bot.network.MinecraftConnection;
import ru.ryfi.bot.network.PacketBuf;
import ru.ryfi.bot.network.packet.PacketIn;
@Log4j2
public class PacketEntitiesDestroy implements PacketIn {
    int[] ents;
    @Override
    public void read(PacketBuf buf) {
        ents = buf.readVarIntArray();
    }

    @Override
    public void handle(MinecraftConnection connection) {

        for(int i : ents){
            //log.info("Ent {} is destroyed",i);
            connection.getBot().getWorld().removeEntity(i);
        }
    }
}
