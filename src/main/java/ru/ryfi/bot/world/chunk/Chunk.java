package ru.ryfi.bot.world.chunk;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;
import ru.ryfi.bot.network.PacketBuf;
import ru.ryfi.bot.world.chunk.palette.GlobalPalette;
import ru.ryfi.bot.world.chunk.palette.ListPalette;
import ru.ryfi.bot.world.chunk.palette.MapPalette;
import ru.ryfi.bot.world.chunk.palette.Palette;

import java.io.IOException;

@Data
@Setter(AccessLevel.NONE)
@AllArgsConstructor
public class Chunk {
    private static final int CHUNK_SIZE = 4096;
    private static final int MIN_PALETTE_BITS_PER_ENTRY = 4;
    private static final int MAX_PALETTE_BITS_PER_ENTRY = 8;
    private static final int GLOBAL_PALETTE_BITS_PER_ENTRY = 14;

    private static final int AIR = 0;

    private int blockCount;
    private @NonNull Palette palette;
    private @NonNull BitStorage storage;

    public Chunk() {
        this(0, new ListPalette(MIN_PALETTE_BITS_PER_ENTRY), new BitStorage(MIN_PALETTE_BITS_PER_ENTRY, CHUNK_SIZE));
    }

    public static Chunk read(PacketBuf in){
        int blockCount = in.readShort();
        int bitsPerEntry = in.readUnsignedByte();

        Palette palette = readPalette(bitsPerEntry, in);

        BitStorage storage = new BitStorage(bitsPerEntry, CHUNK_SIZE, in.readLongArray());
        return new Chunk(blockCount, palette, storage);
    }



    public int get(int x, int y, int z) {
        int id = this.storage.get(index(x, y, z));
        return this.palette.idToState(id);
    }

    public void set(int x, int y, int z, @NonNull int state) {
        int id = this.palette.stateToId(state);
        if (id == -1) {
            this.resizePalette();
            id = this.palette.stateToId(state);
        }

        int index = index(x, y, z);
        int curr = this.storage.get(index);
        if (state != AIR && curr == AIR) {
            this.blockCount++;
        } else if (state == AIR && curr != AIR) {
            this.blockCount--;
        }

        this.storage.set(index, id);
    }

    public boolean isEmpty() {
        return this.blockCount == 0;
    }

    private int sanitizeBitsPerEntry(int bitsPerEntry) {
        if (bitsPerEntry <= MAX_PALETTE_BITS_PER_ENTRY) {
            return Math.max(MIN_PALETTE_BITS_PER_ENTRY, bitsPerEntry);
        } else {
            return GLOBAL_PALETTE_BITS_PER_ENTRY;
        }
    }

    private void resizePalette() {
        Palette oldPalette = this.palette;
        BitStorage oldData = this.storage;

        int bitsPerEntry = sanitizeBitsPerEntry(oldData.getBitsPerEntry() + 1);
        this.palette = createPalette(bitsPerEntry);
        this.storage = new BitStorage(bitsPerEntry, CHUNK_SIZE);

        for (int i = 0; i < CHUNK_SIZE; i++) {
            this.storage.set(i, this.palette.stateToId(oldPalette.idToState(oldData.get(i))));
        }
    }

    private static Palette createPalette(int bitsPerEntry) {
        if (bitsPerEntry <= MIN_PALETTE_BITS_PER_ENTRY) {
            return new ListPalette(bitsPerEntry);
        } else if (bitsPerEntry <= MAX_PALETTE_BITS_PER_ENTRY) {
            return new MapPalette(bitsPerEntry);
        } else {
            return new GlobalPalette();
        }
    }

    private static Palette readPalette(int bitsPerEntry, PacketBuf in){
        if (bitsPerEntry <= MIN_PALETTE_BITS_PER_ENTRY) {
            return new ListPalette(bitsPerEntry, in);
        } else if (bitsPerEntry <= MAX_PALETTE_BITS_PER_ENTRY) {
            return new MapPalette(bitsPerEntry, in);
        } else {
            return new GlobalPalette();
        }
    }

    private static int index(int x, int y, int z) {
        return y << 8 | z << 4 | x;
    }
}
