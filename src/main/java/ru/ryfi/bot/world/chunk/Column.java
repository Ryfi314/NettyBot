package ru.ryfi.bot.world.chunk;


import lombok.Data;
import lombok.NonNull;
import ru.ryfi.bot.nbt.NBTTagCompound;

import java.util.Arrays;

@Data
public class Column {
    private final int x;
    private final int z;
    private final @NonNull Chunk[] chunks;
    private final @NonNull NBTTagCompound[] tileEntities;
    private final @NonNull NBTTagCompound heightMaps;
    private final @NonNull int[] biomeData;

    /**
     * @deprecated Non-full chunks no longer exist since 1.17.
     */
    @Deprecated
    public Column(int x, int z, @NonNull Chunk[] chunks, @NonNull NBTTagCompound[] tileEntities, @NonNull NBTTagCompound heightMaps) {
        this(x, z, chunks, tileEntities, heightMaps, new int[1024]);
    }

    public Column(int x, int z, @NonNull Chunk[] chunks, @NonNull NBTTagCompound[] tileEntities, @NonNull NBTTagCompound heightMaps, @NonNull int[] biomeData) {
        this.x = x;
        this.z = z;
        this.chunks = Arrays.copyOf(chunks, chunks.length);
        this.biomeData = biomeData != null ? Arrays.copyOf(biomeData, biomeData.length) : null;
        this.tileEntities = tileEntities != null ? tileEntities : new NBTTagCompound[0];
        this.heightMaps = heightMaps;
    }
}
