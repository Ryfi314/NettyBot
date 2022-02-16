package ru.ryfi.bot.network.packet.client.handshake;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import ru.ryfi.bot.network.PacketBuf;
import ru.ryfi.bot.network.packet.PacketOut;

/**
 * @author Greenpix
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PacketLoginStart implements PacketOut {

	private String name;

	@Override
	public void write(PacketBuf buf) {
		buf.writeString(this.name);
	}
}
