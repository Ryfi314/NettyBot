/*
 * Ryfi  2021.
 */

package ru.ryfi.bot.event.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.ryfi.bot.event.Event;
import ru.ryfi.bot.world.block.Block;
import ru.ryfi.bot.world.position.WorldLocation;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BlockPlaceEvent extends Event {
    Block block;
    WorldLocation worldLocation;
}
