package ru.ryfi.bot.network.packet;

import ru.ryfi.bot.network.PacketBuf;

/**
 * @author Greenpix
 */
public interface PacketOut extends Packet {

	@Override
	default void read(PacketBuf buf) {
		throw new UnsupportedOperationException();
	}
}
