package ru.ryfi.bot.network.packet.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import ru.ryfi.bot.event.EventManager;
import ru.ryfi.bot.event.events.BotConnectEvent;
import ru.ryfi.bot.network.MinecraftConnection;
import ru.ryfi.bot.network.PacketBuf;
import ru.ryfi.bot.network.packet.PacketIn;
import ru.ryfi.bot.network.packet.ProtocolType;

import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Log4j2
public class PacketLoginSuccess implements PacketIn {

	private UUID uniqueId;

	private String username;

	@Override
	public void read(PacketBuf buf) {
		this.uniqueId = new UUID(buf.readLong(),buf.readLong());
		this.username = buf.readString();
	}

	@Override
	public void handle(MinecraftConnection connection) {
		connection.setState(ProtocolType.PLAY);
		EventManager.fireEvent(new BotConnectEvent(uniqueId,username));
		log.info("Joined {} with uuid {}", username, uniqueId);

	}
}
