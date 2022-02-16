package ru.ryfi.bot.network.packet.client.play;

import lombok.AllArgsConstructor;
import lombok.Setter;
import ru.ryfi.bot.network.PacketBuf;
import ru.ryfi.bot.network.packet.PacketOut;
@AllArgsConstructor
@Setter
public class PacketHeldItemChange implements PacketOut {
    short id;
    @Override
    public void write(PacketBuf buf) {
        buf.writeShort(id);
    }
}
