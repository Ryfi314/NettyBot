package ru.ryfi.bot.network.packet.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.ryfi.bot.network.PacketBuf;
import ru.ryfi.bot.network.packet.PacketOut;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PacketTeleportConfirmOut implements PacketOut {
    private int teleportid;
    @Override
    public void write(PacketBuf buf) {
        buf.writeVarInt(teleportid);
    }


}
