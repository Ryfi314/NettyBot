package ru.ryfi.bot.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;

import java.util.List;


public class Varint21Decoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
		byteBuf.markReaderIndex();
		byte[] abyte = new byte[3];

		for(int i = 0; i < abyte.length; ++i) {
			if (!byteBuf.isReadable()) {
				byteBuf.resetReaderIndex();
				return;
			}

			abyte[i] = byteBuf.readByte();
			if (abyte[i] >= 0) {
				PacketBuf packetbuffer = new PacketBuf(Unpooled.wrappedBuffer(abyte));

				try {
					int j = packetbuffer.readVarInt();
					if (byteBuf.readableBytes() >= j) {
						list.add(byteBuf.readBytes(j));
						return;
					}

					byteBuf.resetReaderIndex();
				} finally {
					packetbuffer.release();
				}

				return;
			}
		}

		throw new CorruptedFrameException("length wider than 21-bit");
	}
}
