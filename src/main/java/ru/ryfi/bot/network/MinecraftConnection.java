package ru.ryfi.bot.network;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.local.LocalChannel;
import io.netty.channel.local.LocalServerChannel;
import io.netty.util.AttributeKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ru.ryfi.bot.network.packet.Packet;
import ru.ryfi.bot.network.packet.PacketIn;
import ru.ryfi.bot.network.packet.ProtocolType;
import ru.ryfi.bot.network.packet.UnknownPacket;
import ru.ryfi.bot.network.packet.client.play.PacketChatOut;
import ru.ryfi.bot.network.packet.client.play.PacketClientStatus;
import ru.ryfi.bot.network.packet.client.play.PacketKeepAliveOut;
import ru.ryfi.bot.network.packet.server.*;

import java.awt.*;
import java.io.StringReader;
import java.net.SocketAddress;


@RequiredArgsConstructor
@Log4j2
public class MinecraftConnection extends SimpleChannelInboundHandler<Packet> {

	public static final AttributeKey<ProtocolType> PROTOCOL_ATTRIBUTE_KEY = AttributeKey.valueOf("protocol");
	
	public final Bot bot;
	
	private boolean closed;

	private Channel channel;

	public Bot getBot() {
		return bot;
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.error("An occurred netty exception", cause);
		this.closeChannel();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		this.channel = ctx.channel();
		this.setState(ProtocolType.HANDSHAKING);

		this.bot.initializeBot(this);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		this.closeChannel();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext channelHandlerContext, Packet packet) throws Exception {
		if (this.channel.isOpen()) {
			if (packet instanceof PacketIn packetIn) {
				packetIn.handle(this);
				this.bot.receivePacket(packetIn);
			}
			if(packet instanceof PacketLoginSuccess success){
				sendPacket(new PacketChatOut("/reg pidaras pidaras"));
				sendPacket(new PacketClientStatus(0));
			}
			if(packet instanceof PacketChatIn packetChatOut){
				log.info(packetChatOut.getJson());
				String parsedChat = chatMessageToString(packetChatOut);
				log.info(parsedChat);
				if(parsedChat != null) {
					if (parsedChat.startsWith("!")) {
						String[] args = parsedChat.replaceFirst("!", "").split(" ");
						bot.getCommandManager().invoke(args[0], bot.getWorld().getPlayerEntityByUUID(packetChatOut.getUuid()), args);
					}
				}

			}

			if(packet instanceof PacketKeepAliveIn packetKeepAlliveOut){

				bot.setKeepAliveId(packetKeepAlliveOut.getId());
				sendPacket(new PacketKeepAliveOut(packetKeepAlliveOut.getId()));


			}
			if(packet instanceof  PacketDisconnect disconnect){
				log.info("Disconected: {}",disconnect.getReason());
				channel.close();

				System.exit(0);
			}
			if(packet instanceof PacketUpdateHealthIn packetUpdateHealthOut){
				log.info("Recived health:{}",packetUpdateHealthOut.getHealth());
				if(packetUpdateHealthOut.getHealth() >= 0.0 ){
					sendPacket(new PacketClientStatus(0));
				}

			}
			if(packet instanceof PacketPositionAndLookIn a){
				bot.setWorldLocation(a.getWorldLocation());
				log.info("Recived position x:{},y:{},z:{}",a.getWorldLocation().getX(),a.getWorldLocation().getY(),a.getWorldLocation().getZ());
				sendPacket(new PacketTeleportConfirmOut(a.getTeleportid()));


			}




			if(packet instanceof  UnknownPacket packet1){
				//log.info(packet1.toString());
			}
		}
	}

	public static String chatMessageToString(PacketChatIn packet) {
		//System.out.println(packet.getMessage().toString());

			JsonReader reader1 = new JsonReader(new StringReader(packet.getJson()));
			JsonElement asds =  new JsonParser().parse(reader1);
			String as = "";
			try {
				if (asds.getAsJsonObject().get("content").getAsString() == "" && asds.getAsJsonObject().get("content").getAsString() == null) {
				} else {
					for (JsonElement asdsa : asds.getAsJsonObject().get("extra").getAsJsonArray()) {
						as += asdsa.getAsJsonObject().get("text");
						as += " ";
					}
				}
				as = as.replace("\"", "");
				return as;
			} catch (Exception e){

				if (asds.getAsJsonObject().get("with") == null) return null;
				JsonArray asd = asds.getAsJsonObject().get("with").getAsJsonArray();
				if (asd.size() == 2) {
					JsonElement asdasd = asd.get(1);
					if (asdasd.getAsJsonObject().get("text") != null) return asdasd.getAsJsonObject().get("text").getAsString();
				}
				return "";
			}

	}

	public void sendPacket(Packet packet) {
		if (this.channel.isOpen()) {
			if (this.channel.eventLoop().inEventLoop()) {
				this.channel.writeAndFlush(packet);
			} else {
				this.channel.eventLoop().execute(() -> this.channel.writeAndFlush(packet));
			}
		}
	}

	public ProtocolType getState() {
		return this.channel.attr(PROTOCOL_ATTRIBUTE_KEY).get();
	}

	public void setState(ProtocolType newState) {
		this.channel.attr(PROTOCOL_ATTRIBUTE_KEY).set(newState);
		this.channel.config().setAutoRead(true);
	}

	public void setCompressionThreshold(int threshold) {
		if (threshold >= 0) {
			if (this.channel.pipeline().get("decompress") instanceof CompressionDecoder) {
				((CompressionDecoder) this.channel.pipeline().get("decompress")).setCompressionThreshold(threshold);
			} else {
				this.channel.pipeline().addBefore("decoder", "decompress", new CompressionDecoder(threshold));
			}

			if (this.channel.pipeline().get("compress") instanceof CompressionEncoder) {
				((CompressionEncoder)this.channel.pipeline().get("compress")).setCompressionThreshold(threshold);
			} else {
				this.channel.pipeline().addBefore("encoder", "compress", new CompressionEncoder(threshold));
			}
		} else {
			if (this.channel.pipeline().get("decompress") instanceof CompressionDecoder) {
				this.channel.pipeline().remove("decompress");
			}

			if (this.channel.pipeline().get("compress") instanceof CompressionEncoder) {
				this.channel.pipeline().remove("compress");
			}
		}
	}

	public void closeChannel() {
		if (!this.closed) {
			if (this.channel.isOpen()) {
				this.channel.close();
			}
			this.closed = true;
		}
	}

	public boolean isChannelOpen() {
		return this.channel != null && this.channel.isOpen();
	}

	public boolean isLocalChannel() {
		return this.channel instanceof LocalChannel || this.channel instanceof LocalServerChannel;
	}

	public SocketAddress getRemoteAddress() {
		return this.channel != null ? this.channel.remoteAddress() : null;
	}

	public EventLoop eventLoop() {
		return this.channel.eventLoop();
	}

	public Channel getChannel(){
		return this.channel;
	}
}
