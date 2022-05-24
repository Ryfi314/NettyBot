package ru.ryfi.bot.network;


import ru.ryfi.bot.network.packet.PacketIn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static com.google.common.base.Preconditions.*;


public class BotBuilder {
	
	private String serverHost = "localhost";
	
	private int serverPort = 25565;
	
	private String username = "Bot";
	
	private Collection<BiConsumer<Bot, PacketIn>> receivePacketListeners = new ArrayList<>();
	
	public BotBuilder serverHost(String host) {
		checkNotNull(host);
		this.serverHost = host;
		return this;
	}

	public BotBuilder serverPort(int port) {
		this.serverPort = port;
		return this;
	}

	public BotBuilder username(String username) {
		checkNotNull(username);
		this.username = username;
		return this;
	}

	public BotBuilder onReceivePacket(Consumer<PacketIn> action) {
		return onReceivePacket((bot, packet) -> action.accept(packet));
	}
	
	public BotBuilder onReceivePacket(BiConsumer<Bot, PacketIn> action) {
		this.receivePacketListeners.add(action);
		return this;
	}
	
	public Bot build() {
		return new Bot(serverHost, serverPort, username, receivePacketListeners);
	}
}
