/*******************************************************************************
 *     Copyright (C) 2015 Jordan Dalton (jordan.8474@gmail.com)
 *
 *     This program is free software; you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation; either version 2 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License along
 *     with this program; if not, write to the Free Software Foundation, Inc.,
 *     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *******************************************************************************/
package ru.ryfi.bot.nbt;

import java.io.*;

public class NBTTagByte extends NBTBase
{
    /** The byte value for the tag. */
    public byte data;

    public NBTTagByte(String par1Str)
    {
        super(par1Str);
    }

    public NBTTagByte(String par1Str, byte par2)
    {
        super(par1Str);
        data = par2;
    }

    /**
     * Write the actual data contents of the tag, implemented in NBT extension classes
     */
    void write(DataOutput par1DataOutput) throws IOException
    {
        par1DataOutput.writeByte(data);
    }

    /**
     * Read the actual data contents of the tag, implemented in NBT extension classes
     */
    void load(DataInput par1DataInput) throws IOException
    {
        data = par1DataInput.readByte();
    }

    /**
     * Gets the type byte for the tag.
     */
    public byte getId()
    {
        return 1;
    }

    public String toString()
    {
        return (new StringBuilder()).append("").append(data).toString();
    }

    /**
     * Creates a clone of the tag.
     */
    public NBTBase copy()
    {
        return new NBTTagByte(getName(), data);
    }

    public boolean equals(Object par1Obj)
    {
        if (super.equals(par1Obj))
        {
            NBTTagByte nbttagbyte = (NBTTagByte)par1Obj;
            return data == nbttagbyte.data;
        }
        else
        {
            return false;
        }
    }

    public int hashCode()
    {
        return super.hashCode() ^ data;
    }
}
