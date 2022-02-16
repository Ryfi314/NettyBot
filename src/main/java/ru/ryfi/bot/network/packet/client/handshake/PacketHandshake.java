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
public class PacketHandshake implements PacketOut {

	private int protocolVersion;

	private String serverAddress;

	private int serverPort;

	private int nextState;

	@Override
	public void write(PacketBuf buf) {
		buf.writeVarInt(this.protocolVersion);
		buf.writeString(this.serverAddress);
		buf.writeShort(this.serverPort);
		buf.writeVarInt(this.nextState);
	}
}
