package ru.ryfi.bot.network.packet.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.ryfi.bot.network.MinecraftConnection;
import ru.ryfi.bot.network.PacketBuf;
import ru.ryfi.bot.network.enums.Hand;
import ru.ryfi.bot.network.packet.PacketIn;
import ru.ryfi.bot.network.packet.client.play.PacketHeldItemChange;
import ru.ryfi.bot.network.packet.client.play.PacketUseItem;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PacketUpdateHealthIn implements PacketIn {
    private float health;
    private int food;
    private float saturation;

    @Override
    public void read(PacketBuf buf) {
        health = buf.readFloat();
        food = buf.readVarInt();
        saturation = buf.readFloat();

    }

    @Override
    public void handle(MinecraftConnection connection) {
        if(food <= 19){
//            System.out.println(food);
//            connection.sendPacket(new PacketHeldItemChange((short) 0));
//            connection.sendPacket(new PacketUseItem(Hand.MAIN_HAND));
        }
    }
}
