/*
 * Ryfi  2022.
 */

package ru.ryfi.bot.command.commands;

import ru.ryfi.bot.ai.WalkActivity;
import ru.ryfi.bot.command.Command;
import ru.ryfi.bot.network.Bot;
import ru.ryfi.bot.world.entity.PlayerEntity;

public class ComeCommand implements Command {
    private final Bot bot;

    public ComeCommand(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void onCommand(PlayerEntity entity, String[] args) {
        bot.setActivity(new WalkActivity(entity.getLocation().toBlockLocation(), false,bot));
        bot.sendChatMessage("Бегу");
    }
}
