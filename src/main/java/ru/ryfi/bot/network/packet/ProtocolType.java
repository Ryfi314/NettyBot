package ru.ryfi.bot.network.packet;

import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import ru.ryfi.bot.network.packet.client.handshake.PacketHandshake;
import ru.ryfi.bot.network.packet.client.handshake.PacketLoginStart;
import ru.ryfi.bot.network.packet.client.play.*;
import ru.ryfi.bot.network.packet.server.*;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;


public enum ProtocolType {

	HANDSHAKING(
			ImmutableMap.<Integer, Class<? extends Packet>>builder()
					.put(0x00, PacketHandshake.class)
					.build(),
			ImmutableMap.<Integer, Class<? extends Packet>>builder()
					.build()
	),
	STATUS(
			ImmutableMap.<Integer, Class<? extends Packet>>builder()
					.build(),
			ImmutableMap.<Integer, Class<? extends Packet>>builder()
					.build()
	),
	LOGIN(
			ImmutableMap.<Integer, Class<? extends Packet>>builder()
					.put(0x00, PacketLoginStart.class)
					.build(),
			ImmutableMap.<Integer, Class<? extends Packet>>builder()
					.put(0x00, PacketDisconnect.class)
					.put(0x02, PacketLoginSuccess.class)
					.put(0x03, PacketSetCompression.class)
					.build()
	),
	PLAY(
			ImmutableMap.<Integer, Class<? extends Packet>>builder()
					.put(0x03, PacketChatOut.class)
					.put(0x0F, PacketKeepAliveOut.class)
					.put(0x12, PacketPositionAndLookOut.class)
					.put(0x00, PacketTeleportConfirmOut.class)
					.put(0x2E, PacketPlaceBlockOut.class)
					.put(0x1A, PacketDigingOut.class)
					.put(0x04,PacketClientStatus.class)
					.put(0x25,PacketHeldItemChange.class)
					.put(0x2F,PacketUseItem.class)
					.put(0x2C,SwingArmPacket.class)
					.build(),
			ImmutableMap.<Integer, Class<? extends Packet>>builder()
					.put(0x3F, PacketMultiBlockChange.class)
					.put(0x1A, PacketDisconnect.class)
					.put(0x29,PacketEntityPosition.class)
					.put(0x0F, PacketChatIn.class)
					.put(0x21, PacketKeepAliveIn.class)
					.put(0x38, PacketPositionAndLookIn.class)
					.put(0x52, PacketUpdateHealthIn.class)
					.put(0x46, PacketSpawnPositionIn.class)
					.put(0x22,PacketChunkDataIn.class)
					.put(0x02,SpawnLivingEntity.class)
					.put(0x04,SpawnPlayer.class)
					.put(0x61,PacketEntityTeleport.class)
					.put(0x1D,PacketChunkUnload.class)
					.put(0x0C,PacketBlockChange.class)
					.put(0x08,PlayerDiggingResponse.class)
					.put(0x4F,PacketEntityVelocity.class)
					.put(0x3A,PacketEntitiesDestroy.class)
					.put(0x2A,PacketEntityPositionAndLook.class)
					.build()
	);

	// Пакеты с клиента на сервер
	private final Object2IntMap<Class<? extends Packet>> client = new Object2IntOpenHashMap<>();

	// Пакеты с сервера на клиент
	private final Int2ObjectMap<Class<? extends Packet>> server = new Int2ObjectOpenHashMap<>();

	ProtocolType(Map<Integer, Class<? extends Packet>> client, Map<Integer, Class<? extends Packet>> server) {
		this.client.putAll(HashBiMap.create(client).inverse());
		this.server.putAll(server);
	}

	public Packet getPacketById(int id) {
		if (!server.containsKey(id)) {
			return null;
		}
		try {
			return server.get(id).getConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int getIdByPacket(Packet packet) {
		return client.getInt(packet.getClass());
	}
}
