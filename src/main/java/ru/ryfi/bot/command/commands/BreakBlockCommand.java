/*
 * Ryfi  2022.
 */

package ru.ryfi.bot.command.commands;

import ru.ryfi.bot.command.Command;
import ru.ryfi.bot.network.Bot;
import ru.ryfi.bot.world.entity.PlayerEntity;

public class BreakBlockCommand implements Command {
    private final Bot bot;

    public BreakBlockCommand(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void onCommand(PlayerEntity entity, String[] args) {
        bot.digBlock();
    }
}
