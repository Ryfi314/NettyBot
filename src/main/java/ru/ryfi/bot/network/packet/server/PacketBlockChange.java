/*
 * Ryfi  2022.
 */

package ru.ryfi.bot.network.packet.server;

import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.ryfi.bot.network.MinecraftConnection;
import ru.ryfi.bot.network.PacketBuf;
import ru.ryfi.bot.network.packet.PacketIn;
import ru.ryfi.bot.world.position.WorldLocation;
import ru.ryfi.bot.world.position.BlockLocation;

@NoArgsConstructor
@Getter
public class PacketBlockChange implements PacketIn {
    private WorldLocation worldLocation;
    private int blockId;
    @Override
    public void read(PacketBuf buf) {
        worldLocation = buf.readPosition();
        blockId = buf.readVarInt();
    }

    @Override
    public void handle(MinecraftConnection connection) {
        BlockLocation location = new BlockLocation((int) worldLocation.getX(),(int) worldLocation.getY(),(int) worldLocation.getZ());
        connection.getBot().getWorld().setBlock(location,blockId);
//        System.out.println(blockId + "  " + position.getX() + " "+ position.getY() + " "+ position.getZ() + " ");

    }
}
