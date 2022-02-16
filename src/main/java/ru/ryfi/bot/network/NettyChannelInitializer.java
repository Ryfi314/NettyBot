package ru.ryfi.bot.network;

import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.TimeUnit;

/**
 * @author Greenpix
 */
@RequiredArgsConstructor
@Log4j2
public class NettyChannelInitializer extends ChannelInitializer<Channel> {
	
	private final Bot bot;
	
	@Override
	protected void initChannel(Channel channel) throws Exception {
		try {
			channel.config().setOption(ChannelOption.TCP_NODELAY, true);
			channel.config().setConnectTimeoutMillis(30000);
		} catch (ChannelException e) {
			e.printStackTrace();
		}

		channel.pipeline()
				.addLast("timeout", new ReadTimeoutHandler(30, TimeUnit.SECONDS))
				.addLast("splitter", new Varint21Decoder())
				.addLast("decoder", new PacketDecoder())
				.addLast("prepender", new Varint21Encoder())
				.addLast("encoder", new PacketEncoder())
				.addLast("packet_handler", new MinecraftConnection(bot));
	}
}
