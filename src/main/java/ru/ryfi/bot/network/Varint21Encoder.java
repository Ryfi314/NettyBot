package ru.ryfi.bot.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;


public class Varint21Encoder extends MessageToByteEncoder<ByteBuf> {

	@Override
	protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, ByteBuf byteBuf2) throws Exception {
		int i = byteBuf.readableBytes();
		int j = PacketBuf.getVarIntSize(i);
		if (j > 3) {
			throw new IllegalArgumentException("unable to fit " + i + " into " + 3);
		} else {
			PacketBuf packetbuffer = new PacketBuf(byteBuf2);
			packetbuffer.ensureWritable(j + i);
			packetbuffer.writeVarInt(i);
			packetbuffer.writeBytes(byteBuf, byteBuf.readerIndex(), i);
		}
	}
}
