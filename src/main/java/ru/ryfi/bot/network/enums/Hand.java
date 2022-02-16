package ru.ryfi.bot.network.enums;

public enum Hand {
    MAIN_HAND(0),
    OFF_HAND(1);



    private int id;
    Hand(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
