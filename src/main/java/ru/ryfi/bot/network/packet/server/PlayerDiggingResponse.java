/*
 * Ryfi  2022.
 */

package ru.ryfi.bot.network.packet.server;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ru.ryfi.bot.network.MinecraftConnection;
import ru.ryfi.bot.network.PacketBuf;
import ru.ryfi.bot.network.enums.PlayerStatus;
import ru.ryfi.bot.network.packet.PacketIn;
import ru.ryfi.bot.world.position.WorldLocation;

@Log4j2
@NoArgsConstructor
public class PlayerDiggingResponse implements PacketIn {
    private WorldLocation worldLocation;
    private int stateid;
    private int status;
    boolean successful;
    @Override
    public void read(PacketBuf buf) {
        worldLocation = buf.readPosition();
        stateid = buf.readVarInt();
        status = buf.readVarInt();
        successful = buf.readBoolean();
    }

    @Override
    public void handle(MinecraftConnection connection) {
        connection.getBot().getWorld().setBlock(worldLocation.toBlockLocation(),stateid);
        log.info("Block br: {} {} {} {}", worldLocation,stateid, PlayerStatus.getById(status),successful);
    }
}
