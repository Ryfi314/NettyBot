package ru.ryfi.bot.network.packet.client.play;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.ryfi.bot.network.PacketBuf;
import ru.ryfi.bot.network.packet.PacketOut;



@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PacketChatOut implements PacketOut {

	private String message;

	@Override
	public void write(PacketBuf buf) {
		buf.writeString(message);
	}
}
