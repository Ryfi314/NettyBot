package ru.ryfi.bot.utils;

import java.util.Random;

public class Uttils {
    public static String randomString(int chars){
        String name = "";
        int i = chars;
        while (i >= 0){
            Random r = new Random();
            char c = (char)(r.nextInt(26) + 'a');
            name = name + c;
            i--;
        }
        return name;
    }

}
