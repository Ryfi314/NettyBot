/*
 * Ryfi  2021.
 */

package ru.ryfi.bot.event.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.ryfi.bot.event.Event;
import ru.ryfi.bot.network.PacketBuf;
import ru.ryfi.bot.world.chunk.Column;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ChunkReciveEvent extends Event {
    private Column column;
}
