/*
 * Ryfi  2022.
 */

package ru.ryfi.bot.world.entity;

import lombok.Getter;
import ru.ryfi.bot.world.World;

import java.util.UUID;

public class PlayerEntity extends Entity{
    @Getter
    private final UUID uuid;
    public PlayerEntity(World world, int id, UUID uuid) {
        super(uuid, world, id);
        this.uuid = uuid;
    }
}
