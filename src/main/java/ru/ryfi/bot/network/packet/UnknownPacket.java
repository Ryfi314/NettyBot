package ru.ryfi.bot.network.packet;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

import ru.ryfi.bot.network.MinecraftConnection;

/**
 * @author Greenpix
 */
@RequiredArgsConstructor
@Log4j2
@ToString(onlyExplicitlyIncluded = true)
public class UnknownPacket implements PacketOut, PacketIn {

	@ToString.Include
	private final int id;

	private final byte[] data;

	@Override
	public void handle(MinecraftConnection connection) {

	}
}
