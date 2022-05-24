package ru.ryfi.bot.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import ru.ryfi.bot.network.packet.Packet;
import ru.ryfi.bot.network.packet.UnknownPacket;

import java.util.List;


public class PacketDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext context, ByteBuf byteBuf, List<Object> list) throws Exception {
		if (byteBuf.readableBytes() != 0) {
			PacketBuf packetbuffer = new PacketBuf(byteBuf);
			int id = packetbuffer.readVarInt();
			Packet packet = context.channel().attr(MinecraftConnection.PROTOCOL_ATTRIBUTE_KEY).get().getPacketById(id);
			if (packet == null) {
				int readable = packetbuffer.readableBytes();
				byte[] bytes = new byte[readable];
				packetbuffer.readBytes(bytes);
				UnknownPacket unknownPacket = new UnknownPacket(id, bytes);
				list.add(unknownPacket);
				//throw new IOException("Bad packet id " + id);
			} else {
				packet.read(packetbuffer);
				int readable = packetbuffer.readableBytes();
				if (readable > 0) {
					//throw new IOException("Packet " + context.channel().attr(NetworkManager.PROTOCOL_ATTRIBUTE_KEY).get().name() + "/" + id + " (" + packet.getClass().getSimpleName() + ") was larger than I expected, found " + packetbuffer.readableBytes() + " bytes extra whilst reading packet " + id);
					packetbuffer.skipBytes(readable);
				}
				list.add(packet);
			}
		}
	}
}
