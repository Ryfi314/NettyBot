/*
 * Ryfi  2022.
 */

package ru.ryfi.bot.network.packet.server;

import ru.ryfi.bot.network.MinecraftConnection;
import ru.ryfi.bot.network.PacketBuf;
import ru.ryfi.bot.network.packet.PacketIn;
import ru.ryfi.bot.world.block.Block;
import ru.ryfi.bot.world.block.BlockType;
import ru.ryfi.bot.world.position.BlockLocation;

import java.util.ArrayList;
import java.util.List;

public class PacketMultiBlockChange implements PacketIn {
    private int chunkX;
    private int chunkY;
    private int chunkZ;
    private boolean ignoreOldLight;
    List<Block> blocks;
    @Override
    public void read(PacketBuf in) {
        blocks = new ArrayList<>();
        long chunkPosition = in.readLong();
        this.chunkX = (int) (chunkPosition >> 42);
        this.chunkY = (int) (chunkPosition << 44 >> 44);
        this.chunkZ = (int) (chunkPosition << 22 >> 42);
        this.ignoreOldLight = in.readBoolean();
        int size = in.readVarInt();
        for (int index = 0; index < size; index++) {
            long blockData = in.readVarLong();
            short position = (short) (blockData & 0xFFFL);
            int x = (this.chunkX << 4) + (position >>> 8 & 0xF);
            int y = (this.chunkY << 4) + (position & 0xF);
            int z = (this.chunkZ << 4) + (position >>> 4 & 0xF);
            int i = (int) (blockData >>> 12);
            blocks.add(new Block(new BlockLocation(x,y,z), BlockType.getByStateId(i)));
        }
    }

    @Override
    public void handle(MinecraftConnection connection) {
        for(Block block : blocks){
            connection.getBot().getWorld().setBlock(block.getPosition(),block.getMaterial().getId());
        }
    }
}
