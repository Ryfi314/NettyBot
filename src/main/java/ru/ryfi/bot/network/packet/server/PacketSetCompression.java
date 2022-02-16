package ru.ryfi.bot.network.packet.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import ru.ryfi.bot.network.MinecraftConnection;
import ru.ryfi.bot.network.PacketBuf;
import ru.ryfi.bot.network.packet.PacketIn;

/**
 * @author Greenpix
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Log4j2
public class PacketSetCompression implements PacketIn {

	private int threshold;

	@Override
	public void read(PacketBuf buf) {
		this.threshold = buf.readVarInt();
	}

	@Override
	public void handle(MinecraftConnection connection) {
		if (!connection.isLocalChannel()) {
			connection.setCompressionThreshold(threshold);
			log.info("Set threshold compression: {}", threshold);
		}
	}
}
