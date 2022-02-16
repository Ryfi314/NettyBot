package ru.ryfi.bot.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DecoderException;

import java.util.List;
import java.util.zip.Inflater;

/**
 * @author Greenpix
 */
public class CompressionDecoder extends ByteToMessageDecoder {

	private final Inflater inflater;

	private int threshold;

	public CompressionDecoder(int thresholdIn) {
		this.threshold = thresholdIn;
		this.inflater = new Inflater();
	}


	@Override
	protected void decode(ChannelHandlerContext context, ByteBuf byteBuf, List<Object> list) throws Exception {
		if (byteBuf.readableBytes() != 0) {
			PacketBuf packetbuffer = new PacketBuf(byteBuf);
			int i = packetbuffer.readVarInt();
			if (i == 0) {
				list.add(packetbuffer.readBytes(packetbuffer.readableBytes()));
			} else {
				if (i < this.threshold) {
					throw new DecoderException("Badly compressed packet - size of " + i + " is below server threshold of " + this.threshold);
				}

				if (i > 2097152) {
					throw new DecoderException("Badly compressed packet - size of " + i + " is larger than protocol maximum of " + 2097152);
				}

				byte[] abyte = new byte[packetbuffer.readableBytes()];
				packetbuffer.readBytes(abyte);
				this.inflater.setInput(abyte);
				byte[] abyte1 = new byte[i];
				this.inflater.inflate(abyte1);
				list.add(Unpooled.wrappedBuffer(abyte1));
				this.inflater.reset();
			}
		}
	}

	public void setCompressionThreshold(int threshold) {
		this.threshold = threshold;
	}
}
