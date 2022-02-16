package ru.ryfi.bot.network.enums;

public enum BlockFace {
    DOWN(0),
    UP(1),
    NORTH(2),
    SOUTH(3),
    WEST(4),
    EAST(5),
    SPECIAL(6);



    private int id;
    BlockFace(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
