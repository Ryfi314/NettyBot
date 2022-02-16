package ru.ryfi.bot.simpleAi;

import ru.ryfi.bot.network.Main;
import ru.ryfi.bot.network.MinecraftConnection;
import ru.ryfi.bot.network.packet.client.play.PacketPositionAndLookOut;

import java.util.concurrent.TimeUnit;

public class Movment {

    public synchronized static void SuncMove(MinecraftConnection connection, char forward, double steps){
        double shagi = steps / 0.100;




        if(forward == 'x'){
            if(shagi >= 0){
                while (shagi != 0){
                    Main.x = Main.x + 0.1;
                    connection.sendPacket(new PacketPositionAndLookOut(Main.x,Main.y,Main.z,Main.yaw,Main.pitch,false));
                    try {
                        TimeUnit.MILLISECONDS.sleep(25);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    shagi--;

                }
            }
            if(shagi <= 0){
                while (shagi != 0){

                    Main.x = Main.x - 0.1;
                    connection.sendPacket(new PacketPositionAndLookOut(Main.x,Main.y,Main.z,Main.yaw,Main.pitch,false));
                    try {
                        TimeUnit.MILLISECONDS.sleep(25);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    shagi++;
                }
            }

        }
        if(forward == 'y'){
            if(shagi >= 0){
                while (shagi != 0){
                    Main.y = Main.y + 0.1;
                    connection.sendPacket(new PacketPositionAndLookOut(Main.x,Main.y,Main.z,Main.yaw,Main.pitch,false));
                    try {
                        TimeUnit.MILLISECONDS.sleep(25);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    shagi--;
                }
            }
            if(shagi <= 0){
                while (shagi != 0){
                    Main.y = Main.y - 0.1;
                    connection.sendPacket(new PacketPositionAndLookOut(Main.x,Main.y,Main.z,Main.yaw,Main.pitch,false));
                    try {
                        TimeUnit.MILLISECONDS.sleep(25);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    shagi++;
                }
            }
        }
        if(forward == 'z'){
            if(shagi >= 0){
                while (shagi != 0){
                    Main.z = Main.z + 0.1;
                    connection.sendPacket(new PacketPositionAndLookOut(Main.x,Main.y,Main.z,Main.yaw,Main.pitch,false));
                    try {
                        TimeUnit.MILLISECONDS.sleep(25);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    shagi--;
                }
            }
            if(shagi <= 0){
                while (shagi != 0){
                    Main.z = Main.z - 0.1;
                    connection.sendPacket(new PacketPositionAndLookOut(Main.x,Main.y,Main.z,Main.yaw,Main.pitch,false));
                    try {
                        TimeUnit.MILLISECONDS.sleep(25);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    shagi++;
                }
            }
        }


    }


}
