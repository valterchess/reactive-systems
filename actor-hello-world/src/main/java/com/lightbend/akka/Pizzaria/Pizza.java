package com.lightbend.akka.Pizzaria;

import akka.actor.typed.javadsl.AbstractBehavior;

import java.util.HashMap;

public class Pizza extends AbstractBehavior<Pizza.Command> {
    public interface Command {
    }

    private static HashMap<String, Integer> ingredientes = new HashMap<String, Integer>() {
        {
            put("mussarela", 30);
            put("calabresa", 30);
            put("frango", 30);
            put("portuguesa", 30);
            put("margherita", 30);
            put("camarão", 20);
            put("atum", 20);
            put("escarola", 20);
        }
    };

    public enum addIngrediente implements Command {
        MUSSARELA,
        CALABRESA,
        FRANGO,
        PORTUGUESA,
        MARGHERITA,
        CAMARAO,
        ATUM,
        ESCAROLA
    }

    public enum gastos implements Command {
        INSTANCE
    }

    
}
