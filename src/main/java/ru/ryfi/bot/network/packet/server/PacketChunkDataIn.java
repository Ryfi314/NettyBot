package ru.ryfi.bot.network.packet.server;

import io.netty.buffer.Unpooled;
import lombok.*;
import ru.ryfi.bot.nbt.NBTTagCompound;
import ru.ryfi.bot.network.MinecraftConnection;
import ru.ryfi.bot.network.PacketBuf;
import ru.ryfi.bot.network.packet.PacketIn;
import ru.ryfi.bot.world.chunk.Chunk;
import ru.ryfi.bot.world.chunk.Column;
import ru.ryfi.bot.world.position.ChunkLocation;

import java.util.BitSet;



@NoArgsConstructor
public class PacketChunkDataIn implements PacketIn {
    @Getter
    private @NonNull Column column;




    @Override
    public void handle(MinecraftConnection connection) {
        connection.getBot().getWorld().loadChunk(new ChunkLocation(column.getX(),column.getZ()),column);
    }

    @Override
    public void read(PacketBuf in) {
        int x = in.readInt();
        int z = in.readInt();
        BitSet chunkMask = BitSet.valueOf(in.readLongArray());
        NBTTagCompound heightMaps = in.readNBTCompound();
        int[] biomeData = new int[in.readVarInt()];
        for (int index = 0; index < biomeData.length; index++) {
            biomeData[index] = in.readVarInt();
        }
        byte[] data = in.readByteArray(in.readVarInt());

        PacketBuf dataIn = new PacketBuf(Unpooled.wrappedBuffer(data));
        Chunk[] chunks = new Chunk[chunkMask.size()];
        for (int index = 0; index < chunks.length; index++) {
            if (chunkMask.get(index)) {
                chunks[index] = Chunk.read(dataIn);
            }
        }

        NBTTagCompound[] tileEntities = new NBTTagCompound[in.readVarInt()];
        for (int i = 0; i < tileEntities.length; i++) {
            tileEntities[i] = in.readNBTCompound();
        }

        this.column = new Column(x, z, chunks, tileEntities, heightMaps, biomeData);

    }

}

