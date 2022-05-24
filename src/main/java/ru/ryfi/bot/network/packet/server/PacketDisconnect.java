package ru.ryfi.bot.network.packet.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import ru.ryfi.bot.network.MinecraftConnection;
import ru.ryfi.bot.network.PacketBuf;
import ru.ryfi.bot.network.packet.PacketIn;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Log4j2
public class PacketDisconnect implements PacketIn {

	private String reason;

	@Override
	public void read(PacketBuf buf) {
		this.reason = buf.readString();
	}

	@Override
	public void handle(MinecraftConnection connection) {


		connection.closeChannel();
	}
}
