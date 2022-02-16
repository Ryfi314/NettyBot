/*
 * Ryfi  2022.
 */

package ru.ryfi.bot.world.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MinecraftData {

    private final String path;

    public MinecraftData(String path) {
        this.path = path;
    }

    public synchronized void loadData(){
        JsonArray obj = null;

        try {
            JsonReader reader1 = new JsonReader(new FileReader(path));
            FileWriter myWriter = new FileWriter("data.txt");

            obj = (JsonArray) new JsonParser().parse(reader1);
            for (JsonElement ass : obj) {
                String name = ass.getAsJsonObject().get("name").getAsString();

                int id = ass.getAsJsonObject().get("id").getAsInt();
                String displayName = ass.getAsJsonObject().get("displayName").getAsString();
                int stackSize = ass.getAsJsonObject().get("stackSize").getAsInt();


                myWriter.write(name.toUpperCase()+"("+id+","+stackSize+",\""+displayName+"\"),\n");
            }
            myWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Дата загруженна");
    }
}
