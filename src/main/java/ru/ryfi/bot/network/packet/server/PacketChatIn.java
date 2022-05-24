package ru.ryfi.bot.network.packet.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import ru.ryfi.bot.network.MinecraftConnection;
import ru.ryfi.bot.network.PacketBuf;
import ru.ryfi.bot.network.packet.PacketIn;

import java.util.UUID;



@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Log4j2
public class PacketChatIn implements PacketIn {

	private String json;

	private byte position;

	private UUID uuid;
	@Override
	public void read(PacketBuf buf) {
		this.json = buf.readString();
		this.position = buf.readByte();
		this.uuid = new UUID(buf.readLong(),buf.readLong());
	}

	@Override
	public void handle(MinecraftConnection connection) {

	}
}
