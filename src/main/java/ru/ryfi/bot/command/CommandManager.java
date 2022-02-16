/*
 * Ryfi  2022.
 */

package ru.ryfi.bot.command;

import lombok.extern.log4j.Log4j2;
import ru.ryfi.bot.network.Bot;
import ru.ryfi.bot.world.entity.PlayerEntity;

import java.util.HashMap;
@Log4j2
public class CommandManager {
    private final Bot bot;

    private HashMap<String,Command> commandHashMap;

    public CommandManager(Bot bot) {
        this.bot = bot;
        commandHashMap = new HashMap<>();
    }

    public void invoke(String name, PlayerEntity entity, String[] args){
        if(commandHashMap.containsKey(name)){
            commandHashMap.get(name).onCommand(entity, args);
        }
    }

    public void registerCommand(String name, Command command){
        commandHashMap.put(name, command);
        log.info("Команда "+name+" зарегестрированна");
    }

}
