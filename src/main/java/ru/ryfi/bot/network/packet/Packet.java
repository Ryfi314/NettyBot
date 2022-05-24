package ru.ryfi.bot.network.packet;


import ru.ryfi.bot.network.PacketBuf;


public interface Packet {

	void read(PacketBuf buf);

	void write(PacketBuf buf);

}
