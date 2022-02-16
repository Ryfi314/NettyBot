package ru.ryfi.bot.network.enums;

public enum PlayerStatus {
    STARTDIGGING(0),
    CANCELDIGGING(1),
    FINISHEDDIGGING(2),
    DROPITEMSTACK(3);
    private int id;
    PlayerStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static  PlayerStatus getById(int id){
        for(PlayerStatus playerStatus : values()){
            if(playerStatus.getId() == id) {
                return playerStatus;
            }
        }
        return null;
    }
}
