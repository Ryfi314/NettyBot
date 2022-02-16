package ru.ryfi.bot.network.packet.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import ru.ryfi.bot.network.MinecraftConnection;
import ru.ryfi.bot.network.PacketBuf;
import ru.ryfi.bot.network.packet.PacketIn;
import ru.ryfi.bot.world.position.WorldLocation;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Log4j2
public class PacketPositionAndLookIn implements PacketIn {
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    private int teleportid;
    private byte flags;
    boolean dismount;
    private WorldLocation worldLocation = new WorldLocation();

    @Override
    public void read(PacketBuf buf) {
        x = buf.readDouble();
        y = buf.readDouble();
        z = buf.readDouble();
        yaw = buf.readFloat();
        pitch = buf.readFloat();
        flags = buf.readByte();
        teleportid = buf.readVarInt();
        dismount = buf.readBoolean();
        worldLocation.setX(x);
        worldLocation.setY(y);
        worldLocation.setZ(z);
        worldLocation.setYaw(pitch);
        worldLocation.setPitch(yaw);

    }

    @Override
    public void handle(MinecraftConnection connection) {
        connection.getBot().setWorldLocation(worldLocation);
    }



}
