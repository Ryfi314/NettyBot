package ru.ryfi.bot.network.packet.client.play;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.ryfi.bot.network.PacketBuf;
import ru.ryfi.bot.network.packet.PacketOut;
import ru.ryfi.bot.world.position.WorldLocation;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PacketPositionAndLookOut implements PacketOut {
    private WorldLocation worldLocation;

    public PacketPositionAndLookOut(double x, double y, double z, float yaw, float pitch, boolean onGround) {
        worldLocation = new WorldLocation(x,y,z,yaw,pitch,onGround);
    }


    @Override
    public void write(PacketBuf buf) {
        buf.writeDouble(worldLocation.getX());
        buf.writeDouble(worldLocation.getY());
        buf.writeDouble(worldLocation.getZ());
        buf.writeFloat(worldLocation.getYaw());
        buf.writeFloat(worldLocation.getPitch());

        buf.writeBoolean(worldLocation.isOnGround());
    }
}
