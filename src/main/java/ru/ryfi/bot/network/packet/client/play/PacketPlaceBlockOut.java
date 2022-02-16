package ru.ryfi.bot.network.packet.client.play;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import ru.ryfi.bot.network.PacketBuf;
import ru.ryfi.bot.network.enums.BlockFace;
import ru.ryfi.bot.network.enums.Hand;
import ru.ryfi.bot.network.packet.PacketOut;
import ru.ryfi.bot.world.position.WorldLocation;


@AllArgsConstructor
@Getter
@Setter
@Log4j2
public class PacketPlaceBlockOut implements PacketOut {
    Hand hand;
    WorldLocation worldLocation;
    BlockFace face;
    float cursorx,cursory,cursorz;
    boolean insideblock;



    @Override
    public void write(PacketBuf buf) {
        buf.writeVarInt(hand.getId());
        buf.writePosition(worldLocation);
        buf.writeVarInt(face.getId());
        buf.writeFloat(cursorx);
        buf.writeFloat(cursory);
        buf.writeFloat(cursorz);
        buf.writeBoolean(insideblock);
    }


}
