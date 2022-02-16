package ru.ryfi.bot.network.packet.client.play;

import lombok.AllArgsConstructor;
import lombok.Setter;
import ru.ryfi.bot.network.PacketBuf;
import ru.ryfi.bot.network.enums.Hand;
import ru.ryfi.bot.network.packet.PacketOut;
@AllArgsConstructor
@Setter
public class PacketUseItem implements PacketOut {
    Hand hand;
    @Override
    public void write(PacketBuf buf) {
        buf.writeVarInt(hand.getId());
    }
}
