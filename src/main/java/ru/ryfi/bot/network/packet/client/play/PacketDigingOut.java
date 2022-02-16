package ru.ryfi.bot.network.packet.client.play;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.ryfi.bot.network.PacketBuf;
import ru.ryfi.bot.network.enums.BlockFace;
import ru.ryfi.bot.network.enums.PlayerStatus;
import ru.ryfi.bot.network.packet.PacketOut;
import ru.ryfi.bot.world.position.BlockLocation;
import ru.ryfi.bot.world.position.WorldLocation;


@AllArgsConstructor
@Getter
@Setter
public class PacketDigingOut implements PacketOut {
    PlayerStatus status;
    BlockLocation worldLocation;
    BlockFace enu;
    @Override
    public void write(PacketBuf buf) {
        buf.writeVarInt(status.getId());
        buf.writeBlockLocation(worldLocation);
        buf.writeByte(enu.getId());
    }
}
