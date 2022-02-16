package ru.ryfi.bot.event.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.ryfi.bot.event.Event;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BotConnectEvent extends Event {
    UUID uuid;
    String name;
}
