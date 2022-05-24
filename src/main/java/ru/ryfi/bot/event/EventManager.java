package ru.ryfi.bot.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class EventManager{
    // боже что за кринж
    static public List<Listener> eventContainers = new ArrayList<>();
    public static void addListener(Listener listener){
        eventContainers.add(listener);
    }
    static public void fireEvent(Event e) {

        Method[] methods;

        for (Object o : eventContainers) {
            methods = o.getClass().getMethods();
            for (Method m : methods) {
                if (m.getAnnotation(EventHandler.class) != null) {
                    try {

                        if (m.getParameterTypes()[0].isAssignableFrom(e.getClass())) {
                            m.invoke(o, e);
                        }
                    } catch (IllegalAccessException ex) {
                    } catch (IllegalArgumentException ex) {
                    } catch (InvocationTargetException ex) {
                    }
                }
            }
        }
    }
}
