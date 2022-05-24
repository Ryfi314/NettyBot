package ru.ryfi.bot.network.packet;


import ru.ryfi.bot.network.MinecraftConnection;
import ru.ryfi.bot.network.PacketBuf;


public interface PacketIn extends Packet {

	void handle(MinecraftConnection connection);

	@Override
	default void write(PacketBuf buf) {
		throw new UnsupportedOperationException();
	}
}
