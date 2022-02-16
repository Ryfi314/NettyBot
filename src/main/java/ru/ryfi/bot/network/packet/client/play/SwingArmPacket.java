
/*
 * Ryfi  2022.
 */

package ru.ryfi.bot.network.packet.client.play;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.ryfi.bot.network.PacketBuf;
import ru.ryfi.bot.network.enums.Hand;
import ru.ryfi.bot.network.packet.PacketOut;
@NoArgsConstructor
@AllArgsConstructor
public class SwingArmPacket implements PacketOut {
    private Hand hand;
    @Override
    public void write(PacketBuf buf) {
        buf.writeVarInt(hand.getId());
    }
}
