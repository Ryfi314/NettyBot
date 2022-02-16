/*
 * Ryfi  2021.
 */

package ru.ryfi.bot.world.position;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Setter
@Getter
@NoArgsConstructor
@Log4j2
public class WorldLocation {
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    private boolean onGround;
    public WorldLocation(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public WorldLocation(double x, double y, double z, float yaw, float pitch, boolean onGround){
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
    }

    public BlockLocation toBlockLocation(){
        return new BlockLocation((int)Math.floor(x),(int)Math.floor(y),(int)Math.floor(z));
    }
    public WorldLocation add(double x, double y, double z){
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }
    public WorldLocation offset(double x, double y, double z){
        return new WorldLocation(x + this.x,y + this.y,z + this.z);
    }
    public WorldLocation setGround(boolean ground){
        setOnGround(ground);
        return this;
    }
    public String toString() {
        return "WorldLocation[x:"+x+" y:"+y+" z:"+z+"]";
    }
}
