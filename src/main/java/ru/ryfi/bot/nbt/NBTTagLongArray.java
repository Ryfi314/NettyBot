package ru.ryfi.bot.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagLongArray extends NBTBase{
    public long[] longs;

    protected NBTTagLongArray(String par1Str) {
        super(par1Str);

    }


    @Override
    void write(DataOutput dataoutput) throws IOException {
        dataoutput.writeInt(longs.length);

        for (long l : longs) {
            dataoutput.writeLong(l);
        }
    }

    @Override
    void load(DataInput datainput) throws IOException {


        longs = new long[datainput.readInt()];

        for (int i = 0; i < longs.length; i++) {
            longs[i] = datainput.readLong();
        }



    }

    @Override
    public byte getId() {
        return 12;
    }

    public long[] getLongs() {
        return longs;
    }

    @Override
    public NBTBase copy() {
        return null;
    }
}
