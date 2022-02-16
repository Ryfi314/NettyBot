package ru.ryfi.bot.utils;

import ru.ryfi.bot.network.Main;
import ru.ryfi.bot.network.MinecraftConnection;
import ru.ryfi.bot.network.packet.client.play.PacketPositionAndLookOut;
import ru.ryfi.bot.world.position.WorldLocation;

public class Calculator {
    public static WorldLocation calculateRotation(WorldLocation npcLoc, WorldLocation point){


        double xDiff = point.getX() - npcLoc.getX();
        double yDiff = point.getY() - npcLoc.getY();
        double zDiff = point.getZ() - npcLoc.getZ();

        double DistanceXZ = Math.sqrt(xDiff * xDiff + zDiff * zDiff);
        double DistanceY = Math.sqrt(DistanceXZ * DistanceXZ + yDiff * yDiff);
        double newYaw = Math.acos(xDiff / DistanceXZ) * 180 / Math.PI;
        double newPitch = Math.acos(yDiff / DistanceY) * 180 / Math.PI - 90;
        if (zDiff < 0.0)
            newYaw = newYaw + Math.abs(180 - newYaw) * 2;
        newYaw = (newYaw - 90);


       float pitch = (float) newPitch;
       float yaw = (float) newYaw;


        return new WorldLocation(npcLoc.getX(),npcLoc.getY(),npcLoc.getZ(),yaw,pitch,true);
    }

    public static void calculateRotation(MinecraftConnection connection, double x, double y , double z){


        double xDiff = x - Main.x;
        double yDiff = y - Main.y;
        double zDiff = z - Main.z;

        double DistanceXZ = Math.sqrt(xDiff * xDiff + zDiff * zDiff);
        double DistanceY = Math.sqrt(DistanceXZ * DistanceXZ + yDiff * yDiff);
        double newYaw = Math.acos(xDiff / DistanceXZ) * 180 / Math.PI;
        double newPitch = Math.acos(yDiff / DistanceY) * 180 / Math.PI - 90;
        if (zDiff < 0.0)
            newYaw = newYaw + Math.abs(180 - newYaw) * 2;
        newYaw = (newYaw - 90);


        float pitch = (float) newPitch;
        float yaw = (float) newYaw;


        connection.sendPacket(new PacketPositionAndLookOut(Main.x,Main.y,Main.z,pitch,yaw,true));
    }
}
