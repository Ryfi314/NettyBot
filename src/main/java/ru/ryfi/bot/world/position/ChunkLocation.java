/*
 * Ryfi  2022.
 */

package ru.ryfi.bot.world.position;

public record ChunkLocation(int chunkX, int chunkZ) {

    public int getChunkX() {
        return chunkX;
    }

    public int getChunkZ() {
        return chunkZ;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ChunkLocation)) {
            return false;
        }
        ChunkLocation coordsObj = (ChunkLocation) obj;
        if (coordsObj.getChunkX() == chunkX && coordsObj.getChunkZ() == chunkZ) {
            return true;
        } else {
            return false;
        }
    }
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + chunkX;
        hash = 31 * hash + chunkZ;
        return hash;
    }
}
