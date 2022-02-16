/*
 * Ryfi  2022.
 */

package ru.ryfi.bot.world.position;


import lombok.Getter;
import lombok.Setter;
import ru.ryfi.bot.network.Bot;
import ru.ryfi.bot.utils.NumberConversions;
import ru.ryfi.bot.world.World;
import ru.ryfi.bot.world.block.Block;

@Getter
@Setter
public class BlockLocation {

    public static final BlockLocation ORIGIN = new BlockLocation(0, 0, 0);
    @Getter
    @Setter
    private int x,y,z;

    public BlockLocation(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (int)x;
        hash = 31 * hash + (int)y;
        hash = 31 * hash + (int)z;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BlockLocation)) {
            return false;
        }
        BlockLocation co = (BlockLocation) obj;
        return co.x == x && co.y == y && co.z == z;
    }

    public BlockLocation down() {
        return new BlockLocation(x,y-1,z);
    }

//    public boolean IsOnGround(Bot client) {
//        return VectorUtils.BTavoid(this.add(0,-1,0).getBlock(client).type);
//    }

    public WorldLocation toPosition(){
        return new WorldLocation(x,y,z);
    }

    public BlockLocation VecToInt() {
        return new BlockLocation((int)x,(int)y,(int)z);
    }


    public BlockLocation add(BlockLocation other) {
        if (other == null) throw new IllegalArgumentException("other cannot be NULL");
        return new BlockLocation(x + other.x, y + other.y, z + other.z);
    }

    public BlockLocation add(int x, int y, int z) {
        return new BlockLocation(this.x + x, this.y + y, this.z + z);
    }

    public BlockLocation subtract(BlockLocation other) {
        if (other == null) throw new IllegalArgumentException("other cannot be NULL");
        return new BlockLocation(x - other.x, y - other.y, z - other.z);
    }

    public BlockLocation subtract(int x, int y, int z) {
        return new BlockLocation(this.x - x, this.y - y, this.z - z);
    }

    public BlockLocation multiply(int factor) {
        return new BlockLocation(x * factor, y * factor, z * factor);
    }


    public BlockLocation divide(int divisor) {
        if (divisor == 0) throw new IllegalArgumentException("Cannot divide by null.");
        return new BlockLocation(x / divisor, y / divisor, z / divisor);
    }


    public BlockLocation abs() {
        return new BlockLocation(Math.abs(x), Math.abs(y), Math.abs(z));
    }

    public String toString() {
        return "x:"+x+" y:"+y+" z:"+z;
    }

    public WorldLocation toWorldLocation(){
        return new WorldLocation(x,y,z);
    }

    public String toStringInt() {
        return "x:"+(int)x+" y:"+(int)y+" z:"+(int)z;
    }

    public String forCommnad() {
        return (int)x+" "+(int)y+" "+(int)z;
    }

    public double distanceSq(double toX, double toY, double toZ) {
        double var7 = (double)this.getX() - toX;
        double var9 = (double)this.getY() - toY;
        double var11 = (double)this.getZ() - toZ;
        return var7 * var7 + var9 * var9 + var11 * var11;
    }

    public double getDistanceTo(BlockLocation other) {
        return Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2)
                + Math.pow(z - other.z, 2));
    }

    public double distanceSq(BlockLocation to) {
        return this.distanceSq((double)to.getX(), (double)to.getY(), (double)to.getZ());
    }
    public BlockLocation toBlockLocation(){
        return new BlockLocation((int)x,(int)y,(int)z);
    }
    public int toBlockX(){
        return this.x;
    }
    public int toBlockY(){
        return this.y;
    }
    public int toBlockZ(){
        return this.z;
    }
    public Block getBlock(Bot client) {
        Block b = client.getWorld().getBlock(this);
        return b;
    }

    public BlockLocation offset(BlockLocation location) {
        return offset(location.x, location.y, location.z);
    }

    public BlockLocation offset(int x, int y, int z) {
        return new BlockLocation(this.x + x, this.y + y, this.z + z);
    }

    public BlockLocation normalize() {
        double length = this.length();
        this.x /= length;
        this.y /= length;
        this.z /= length;
        return this;
    }

    public double length() {
        return Math.sqrt(NumberConversions.square(this.x) + NumberConversions.square(this.y) + NumberConversions.square(this.z));
    }



    public World getWorld(Bot client) {
        return client.getWorld();
    }

    public BlockLocation clone() {
        return new BlockLocation(x,y,z);
    }


}