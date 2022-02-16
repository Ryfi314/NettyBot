/*
 * Ryfi  2022.
 */

package ru.ryfi.bot.command.commands;

import ru.ryfi.bot.ai.WalkActivity;
import ru.ryfi.bot.command.Command;
import ru.ryfi.bot.network.Bot;
import ru.ryfi.bot.world.entity.PlayerEntity;

public class StateCommand implements Command {
    private final Bot bot;

    public StateCommand(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void onCommand(PlayerEntity entity, String[] args) {

        bot.sendChatMessage(bot.getState().name());
    }
}