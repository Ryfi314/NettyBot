package ru.ryfi.bot.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import ru.ryfi.bot.network.packet.Packet;
import ru.ryfi.bot.network.packet.ProtocolType;


public class PacketEncoder extends MessageToByteEncoder<Packet> {

	@Override
	protected void encode(ChannelHandlerContext context, Packet packet, ByteBuf byteBuf) throws Exception {
		ProtocolType protocolType = context.channel().attr(MinecraftConnection.PROTOCOL_ATTRIBUTE_KEY).get();
		if (protocolType == null) {
			throw new RuntimeException("ConnectionProtocol unknown: " + packet);
		} else {
			int id = protocolType.getIdByPacket(packet);
			PacketBuf buf = new PacketBuf(byteBuf);
			buf.writeVarInt(id);
			packet.write(buf);
		}
	}
}
