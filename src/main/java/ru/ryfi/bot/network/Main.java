package ru.ryfi.bot.network;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.log4j.Log4j2;
import ru.ryfi.bot.ai.WalkActivity;
import ru.ryfi.bot.network.enums.BlockFace;
import ru.ryfi.bot.network.enums.Hand;
import ru.ryfi.bot.network.enums.PlayerStatus;
import ru.ryfi.bot.network.packet.client.play.*;
import ru.ryfi.bot.simpleAi.Movment;

import ru.ryfi.bot.world.pathfinding.PathNode;
import ru.ryfi.bot.world.pathfinding.PathSearch;
import ru.ryfi.bot.world.position.WorldLocation;
import ru.ryfi.bot.world.position.BlockLocation;

import java.io.*;


@Log4j2

public class Main {
	public static double x;
	public static double y;
	public static double z;
	public static float pitch;
	public static float yaw;

	public final static String ADDRESS = "localhost";

	public final static int PORT = 25567;

	public static void main(String[] args) {
		//EventManager.addListener(new test());
		//new MinecraftData("data/items.json").loadData();

		Bot bob = Bot.builder()
				.serverHost(ADDRESS)
				.serverPort(PORT)
				.username("ZlotoiGnom")
				.build();


		connect(bob);
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		Thread thread = new Thread(() -> {
			while (true){
				String command = null;
				try {
					command = reader.readLine();
					if(command.startsWith("chat")){
						String message = command.replace("chat ","");
						bob.getConnection().sendPacket(new PacketChatOut(message));
					}
					if(command.startsWith("go")){
						String[] strings = command.split(" ");
						Movment.SuncMove(bob.getConnection(),strings[1].charAt(0),Integer.parseInt(strings[2]));
					}
					if(command.startsWith("slot")){
						String[] strings = command.split(" ");
						bob.getConnection().sendPacket(new PacketHeldItemChange(Short.parseShort(strings[1])));

					}
					if(command.startsWith("drop")){
						String[] strings = command.split(" ");
//						WorldLocation worldLocation = new WorldLocation(x,y,z+1);
//
//
//						bob.getConnection().sendPacket(new PacketDigingOut(PlayerStatus.DROPITEMSTACK, worldLocation, BlockFace.Bottom));


					}


					if(command.startsWith("eat")){
						String[] strings = command.split(" ");
						bob.getConnection().sendPacket(new PacketUseItem(Hand.MAIN_HAND));



					}

					if(command.startsWith("test1")){
						bob.setActivity(new WalkActivity(new BlockLocation(-263,70,121),true,bob));
					}
					if(command.startsWith("test2")){
						PathSearch pathSearch = bob.getWorld().getPathFinder().provideSearch(bob.getWorldLocation().toBlockLocation(),new BlockLocation(-236,76,112));
						while (!pathSearch.isDone()){
							System.out.println("Stepping...");
							pathSearch.step();
						}
						PathNode pathNode = pathSearch.getPath();
						while (!pathNode.isEnd()){


							pathNode = pathNode.getNext();
							bob.sendChatMessage("/setblock "+pathNode.getLocation().forCommnad()+" diamond_block");

						}
					}
					if(command.startsWith("ss")){
						System.out.println(bob.getWorld().getBlock(bob.getWorldLocation().add(0,-1,0).toBlockLocation()).getMaterial().name());
					}
					if(command.startsWith("stop")) {

					System.exit(0);
					}

				} catch (Exception e) {


				}

			}

		});
		thread.start();

	}


	public static ChannelFuture connect(Bot bot) {
		Bootstrap client = new Bootstrap();
		int nThread = Runtime.getRuntime().availableProcessors();
		if (Epoll.isAvailable()) {
			client.group(new EpollEventLoopGroup(nThread));
			client.channel(EpollSocketChannel.class);
		} else {
			client.group(new NioEventLoopGroup(nThread));
			client.channel(NioSocketChannel.class);
		}
		client.handler(new NettyChannelInitializer(bot));
		return client.connect(bot.getServerAddress(), bot.getServerPort());
	}

}
