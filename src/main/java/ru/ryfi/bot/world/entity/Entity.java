/*
 * Ryfi  2022.
 */

package ru.ryfi.bot.world.entity;

import ru.ryfi.bot.world.World;
import ru.ryfi.bot.world.position.BlockLocation;
import ru.ryfi.bot.world.position.WorldLocation;

import java.util.UUID;

public class Entity {
    private final UUID uuid;
    protected final World world;
    protected final int id;
    private WorldLocation location;
    protected double velocityX, velocityY, velocityZ;
    protected double sizeX, sizeY, sizeZ;
    protected Entity rider, riding;
    protected boolean dead;

    public Entity(UUID uuid, World world, int id) {
        this.uuid = uuid;
        this.world = world;
        this.id = id;
    }

    public void update() {

        if(velocityX >= -1E-6 && velocityX <= 1E-6) velocityX = 0;
        if(velocityY >= -1E-6 && velocityY <= 1E-6) velocityY = 0;
        if(velocityZ >= -1E-6 && velocityZ <= 1E-6) velocityZ = 0;

    }


    public World getWorld() {
        return world;
    }

    public int getId() {
        return id;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public double getVelocityZ() {
        return velocityZ;
    }

    public double getVelocity() {
        return Math.sqrt(velocityX * velocityX + velocityY * velocityY + velocityZ * velocityZ);
    }

    public double getVelocityHorizontalAngle() {
        return Math.atan2(velocityZ, velocityX);
    }

    public double getVelocityVerticalAngle() {
        return Math.atan2(velocityY, Math.hypot(velocityX, velocityZ));
    }

    public double getSizeX() {
        return sizeX;
    }

    public double getSizeY() {
        return sizeY;
    }

    public double getSizeZ() {
        return sizeZ;
    }

    public Entity getRider() {
        return rider;
    }

    public Entity getRiding() {
        return riding;
    }

    public boolean isDead() {
        return dead;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public void setVelocityZ(double velocityZ) {
        this.velocityZ = velocityZ;
    }

    public void setRider(Entity rider) {
        this.rider = rider;
    }

    public void setRiding(Entity riding) {
        this.riding = riding;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public WorldLocation getLocation() {
        return location;
    }

    public void setLocation(WorldLocation location) {
        this.location = location;
    }



    public void accelerate(double horizAngle, double vertAngle, double accel) {
        velocityX += accel * Math.cos(horizAngle) * Math.cos(vertAngle);
        velocityZ += accel * Math.sin(horizAngle) * Math.cos(vertAngle);
        velocityY += accel * Math.sin(vertAngle);
    }

    public void accelerate(double horizAngle, double vertAngle, double accel, double velocityBound) {
        double ax = Math.abs(accel * Math.cos(horizAngle) * Math.cos(vertAngle));
        double az = Math.abs(accel * Math.sin(horizAngle) * Math.cos(vertAngle));
        double ay = Math.abs(accel * Math.sin(vertAngle));

        double vxb = velocityBound * Math.cos(horizAngle) * Math.cos(vertAngle);
        double vzb = velocityBound * Math.sin(horizAngle) * Math.cos(vertAngle);
        double vyb = velocityBound * Math.sin(vertAngle);

        if(vxb < 0 && velocityX > vxb)
            velocityX = Math.max(vxb, velocityX - ax);
        else if(vxb > 0 && velocityX < vxb)
            velocityX = Math.min(vxb, velocityX + ax);

        if(vzb < 0 && velocityZ > vzb)
            velocityZ = Math.max(vzb, velocityZ - az);
        else if(vzb > 0 && velocityZ < vzb)
            velocityZ = Math.min(vzb, velocityZ + az);

        if(vyb < 0 && velocityY > vyb)
            velocityY = Math.max(vyb, velocityY - ay);
        else if(vyb > 0 && velocityY < vyb)
            velocityY = Math.min(vyb, velocityY + ay);
    }

    public double getDistanceTo(double x, double y, double z) {
        return Math.sqrt(Math.pow(this.location.getX() - x, 2) + Math.pow(this.location.getY() - y, 2) + Math.pow(this.location.getZ() - z, 2));
    }

    public int getDistanceToSquared(double x, double y, double z) {
        return (int) (Math.pow(this.location.getX() - x, 2) + Math.pow(this.location.getY() - y, 2) + Math.pow(this.location.getZ() - z, 2));
    }

    public double getDistanceTo(Entity other) {
        return getDistanceTo(other.getLocation().getX(), other.getLocation().getY(), other.getLocation().getZ());
    }

    public int getDistanceToSquared(Entity other) {
        return getDistanceToSquared(other.getLocation().getX(), other.getLocation().getY(), other.getLocation().getZ());
    }

    public double getDistanceTo(WorldLocation location) {
        return getDistanceTo(location.getX(), location.getY(), location.getZ());
    }

    public double getDistanceToSquared(WorldLocation location) {
        return getDistanceToSquared(location.getX(), location.getY(), location.getZ());
    }

    public double getDistanceTo(BlockLocation location) {
        return getDistanceTo(location.getX() + 0.5, location.getY(), location.getZ() + 0.5);
    }

    public double getDistanceToSquared(BlockLocation location) {
        return getDistanceToSquared(location.getX() + 0.5, location.getY(), location.getZ() + 0.5);
    }
}
