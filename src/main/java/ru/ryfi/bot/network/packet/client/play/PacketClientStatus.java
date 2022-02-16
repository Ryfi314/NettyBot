package ru.ryfi.bot.network.packet.client.play;

import lombok.AllArgsConstructor;
import lombok.Setter;
import ru.ryfi.bot.network.PacketBuf;
import ru.ryfi.bot.network.packet.PacketOut;
@Setter
@AllArgsConstructor
public class PacketClientStatus implements PacketOut {
    int id;

    @Override
    public void write(PacketBuf buf) {
        buf.writeVarInt(id);
    }
}
