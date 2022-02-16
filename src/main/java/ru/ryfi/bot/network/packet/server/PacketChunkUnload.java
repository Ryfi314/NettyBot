/*
 * Ryfi  2021.
 */

package ru.ryfi.bot.network.packet.server;

import ru.ryfi.bot.network.MinecraftConnection;
import ru.ryfi.bot.network.PacketBuf;
import ru.ryfi.bot.network.packet.PacketIn;
import ru.ryfi.bot.world.position.ChunkLocation;

public class PacketChunkUnload implements PacketIn {
    int chunkx;
    int chunkz;
    @Override
    public void read(PacketBuf buf) {
    chunkx = buf.readInt();
    chunkz = buf.readInt();

    }

    @Override
    public void handle(MinecraftConnection connection) {
        connection.getBot().getWorld().unloadChunk(new ChunkLocation(chunkx,chunkz));
        //System.out.println("Чанк выгружен");
    }
}
