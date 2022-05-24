package ru.ryfi.bot.network;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.ryfi.bot.ai.Activity;
import ru.ryfi.bot.ai.NoneActivity;
import ru.ryfi.bot.command.CommandManager;
import ru.ryfi.bot.command.commands.BreakBlockCommand;
import ru.ryfi.bot.command.commands.ComeCommand;
import ru.ryfi.bot.command.commands.StateCommand;
import ru.ryfi.bot.network.enums.BlockFace;
import ru.ryfi.bot.network.enums.Hand;
import ru.ryfi.bot.network.enums.PlayerStatus;
import ru.ryfi.bot.network.packet.PacketIn;
import ru.ryfi.bot.network.packet.ProtocolType;
import ru.ryfi.bot.network.packet.client.handshake.PacketHandshake;
import ru.ryfi.bot.network.packet.client.handshake.PacketLoginStart;
import ru.ryfi.bot.network.packet.client.play.PacketChatOut;
import ru.ryfi.bot.network.packet.client.play.PacketDigingOut;
import ru.ryfi.bot.network.packet.client.play.PacketPositionAndLookOut;

import ru.ryfi.bot.network.packet.client.play.SwingArmPacket;
import ru.ryfi.bot.world.Physics;
import ru.ryfi.bot.world.World;

import ru.ryfi.bot.world.entity.Entity;
import ru.ryfi.bot.world.position.WorldLocation;

import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;


@RequiredArgsConstructor
public class Bot {

	@Getter
	private final String serverAddress;
	
	@Getter
	private final int serverPort;
	
	private final String username;
	
	private final Collection<BiConsumer<Bot, PacketIn>> receivePacketListeners;
	@Getter
	@Setter
	private BotState state;

	private long KeepAliveId;
	@Setter
	@Getter
	private WorldLocation worldLocation;

	private Physics physics;



	@Getter
	private CommandManager commandManager;

	@Getter
	private MinecraftConnection connection;

	public void setActivity(Activity activity) {
//		this.activity.stop();
		this.activity = activity;
	}

	@Getter

	private Activity activity;



	public void initializeBot(MinecraftConnection connection) {
		world =  new World();
		this.connection = connection;
		this.connection.sendPacket(new PacketHandshake(756, serverAddress, serverPort, 2));
		this.connection.setState(ProtocolType.LOGIN);
		this.connection.sendPacket(new PacketLoginStart(username));
		commandManager = new CommandManager(this);

		commandManager.registerCommand("come",new ComeCommand(this));
		commandManager.registerCommand("state",new StateCommand(this));
		commandManager.registerCommand("br",new BreakBlockCommand(this));

		physics = new Physics(this);
		state = BotState.WAITING;
		startTicking();
		activity = new NoneActivity();
	}
	@Getter
	private World world;

	private boolean isTicking;

	public void face(WorldLocation point){

		double xDiff = point.getX() - worldLocation.getX();
		double yDiff = point.getY() - worldLocation.getY();
		double zDiff = point.getZ() - worldLocation.getZ();

		double DistanceXZ = Math.sqrt(xDiff * xDiff + zDiff * zDiff);
		double DistanceY = Math.sqrt(DistanceXZ * DistanceXZ + yDiff * yDiff);
		double newYaw = Math.acos(xDiff / DistanceXZ) * 180 / Math.PI;
		double newPitch = Math.acos(yDiff / DistanceY) * 180 / Math.PI - 90;
		if (zDiff < 0.0)
			newYaw = newYaw + Math.abs(180 - newYaw) * 2;
		newYaw = (newYaw - 90);


		float pitch = (float) newPitch;
		float yaw = (float) newYaw;

		if(Float.isNaN(yaw)){
			yaw = 0;
		}
		if(Float.isNaN(pitch)){
			pitch = 0;
		}

		worldLocation.setPitch(pitch);
		worldLocation.setYaw(yaw);
	}
	public void face(Entity entity){
		face(entity.getLocation());
	}

	private void startTicking(){
		isTicking = true;
		new Thread(() ->{
			while (isTicking) {
				try {
					TimeUnit.MILLISECONDS.sleep(50);
//					position.setPitch(position.getPitch() + 100);
//					if(position.getPitch() > 360) {
//						position.setPitch(0);
//					}
					physics.fallTick();
					world.tick();
					if(activity != null) {
						if(activity.isActive()) {
							activity.run();
							if(!activity.isActive()) {
								activity.stop();
								activity = null;
							}
						} else {
							activity.stop();
							activity = null;
						}
					}
//						for(Entity entity : world.getEntities().values()){
//							if(entity instanceof PlayerEntity){
//								face(entity);
//							}
//						}
					connection.sendPacket(new PacketPositionAndLookOut(worldLocation));
				} catch (InterruptedException e) {
					System.out.println("Ошибка тика.");
				}

			}
		}).start();
	}

	public void sendChatMessage(String text){
		connection.sendPacket(new PacketChatOut(text));
	}

	public void setTicking(boolean bool){
		this.isTicking = isTicking;
	}
	public void receivePacket(PacketIn packet) {
		this.receivePacketListeners.forEach(listener -> listener.accept(this, packet));
	}

	public boolean isConnected() {
		return this.connection.isChannelOpen();
	}

	public String getUsername() {
		return username;
	}

	public void setKeepAliveId(long keepAliveId) {
		KeepAliveId = keepAliveId;
	}

	public long getKeepAliveId() {
		return KeepAliveId;
	}

	// мои попытки заставить бота ломать блоки
	public synchronized void digBlock(){
		getConnection().sendPacket(new SwingArmPacket(Hand.MAIN_HAND));
		face(getWorldLocation().offset(0,-1,0));
		getConnection().sendPacket(new PacketDigingOut(PlayerStatus.STARTDIGGING, getWorldLocation().offset(0,-1,0).toBlockLocation(), BlockFace.UP));
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		getConnection().sendPacket(new PacketDigingOut(PlayerStatus.FINISHEDDIGGING, getWorldLocation().offset(0,-1,0).toBlockLocation(), BlockFace.UP));
	}

	public static BotBuilder builder() {
		return new BotBuilder();
	}
}
