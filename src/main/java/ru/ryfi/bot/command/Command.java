/*
 * Ryfi  2022.
 */

package ru.ryfi.bot.command;

import ru.ryfi.bot.world.entity.Entity;
import ru.ryfi.bot.world.entity.PlayerEntity;

public interface Command {
    public void onCommand(PlayerEntity entity, String[] args);
}
