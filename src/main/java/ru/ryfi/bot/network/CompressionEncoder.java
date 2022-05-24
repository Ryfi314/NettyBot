package ru.ryfi.bot.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.zip.Deflater;


public class CompressionEncoder extends MessageToByteEncoder<ByteBuf> {

	private final byte[] buffer = new byte[8192];

	private final Deflater deflater;

	private int threshold;

	public CompressionEncoder(int thresholdIn) {
		this.threshold = thresholdIn;
		this.deflater = new Deflater();
	}

	@Override
	protected void encode(ChannelHandlerContext context, ByteBuf byteBuf, ByteBuf byteBuf2) throws Exception {
		int i = byteBuf.readableBytes();
		PacketBuf packetbuffer = new PacketBuf(byteBuf2);
		if (i < this.threshold) {
			packetbuffer.writeVarInt(0);
			packetbuffer.writeBytes(byteBuf);
		} else {
			byte[] abyte = new byte[i];
			byteBuf.readBytes(abyte);
			packetbuffer.writeVarInt(abyte.length);
			this.deflater.setInput(abyte, 0, i);
			this.deflater.finish();

			while(!this.deflater.finished()) {
				int j = this.deflater.deflate(this.buffer);
				packetbuffer.writeBytes(this.buffer, 0, j);
			}

			this.deflater.reset();
		}
	}

	public void setCompressionThreshold(int thresholdIn) {
		this.threshold = thresholdIn;
	}
}
