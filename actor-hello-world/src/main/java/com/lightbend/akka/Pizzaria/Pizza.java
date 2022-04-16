package com.lightbend.akka.Pizzaria;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;

import java.util.HashMap;

public class Pizza extends AbstractBehavior<Pizza.Command> {

    public interface Command {
    }

    public Behavior<Command> create() {
        return Behaviors.setup(Pizza::new);
    }

    private Pizza(ActorContext<Command> context) {
        super(context);
    }

    @Override
    public Receive<Command> createReceive() {
        return newReceiveBuilder()
                .onMessageEquals(addIngrediente.ATUM, this::onAddAtum)
                .onMessageEquals(addIngrediente.CALABRESA, this::onAddCalabresa)
                .onMessageEquals(addIngrediente.CAMARAO, this::onAddCamarao)
                .onMessageEquals(addIngrediente.ESCAROLA, this::onAddEscarola)
                .onMessageEquals(addIngrediente.FRANGO, this::onAddFrango)
                .onMessageEquals(addIngrediente.MARGHERITA, this::onAddMargherita)
                .onMessageEquals(addIngrediente.MUSSARELA, this::onAddMussarela)
                .onMessageEquals(addIngrediente.PORTUGUESA, this::onAddPortuguesa)
                .onMessageEquals(gastos.INSTANCE, this::onFinalSet)
                .build();
    }

    public enum addIngrediente implements Command {
        ATUM,
        CALABRESA,
        CAMARAO,
        ESCAROLA,
        FRANGO,
        MARGHERITA,
        MUSSARELA,
        PORTUGUESA
    }

    public enum gastos implements Command {
        INSTANCE
    }

    private static HashMap<String, Integer> ingredientes = new HashMap<>() {
        {
            put("atum", 30);
            put("calabresa", 30);
            put("camarão", 30);
            put("escarola", 30);
            put("frango", 30);
            put("mussarela", 30);
            put("margherita", 30);
            put("portuguesa", 30);
        }
    };

    private Behavior<Command> onAddAtum() {
        var key = "atum";
        var newValue = ingredientes.get(key) - 1;
        ingredientes.put(key, newValue);
        return this;
    }

    private Behavior<Command> onAddCalabresa() {
        onAddPizza("calabresa");
        return this;
    }

    private Behavior<Command> onAddCamarao() {
        onAddPizza("camarão");
        return this;
    }

    private Behavior<Command> onAddEscarola() {
        onAddPizza("escarola");
        return this;
    }

    private Behavior<Command> onAddFrango() {
        onAddPizza("frango");
        return this;
    }

    private Behavior<Command> onAddMussarela() {
        onAddPizza("mussarela");
        return this;
    }

    private Behavior<Command> onAddMargherita() {
        onAddPizza("margherita");
        return this;
    }

    private Behavior<Command> onAddPortuguesa() {
        onAddPizza("portuguesa");
        return this;
    }

    private Behavior<Command> onFinalSet() {
        ingredientes.forEach((key, value) -> {
            if (ingredientes.get(key) < 30) {
                System.out.printf("Foram gastos %d unidades do ingrediente %s\n", (30 - ingredientes.get(key)), key);
            }
        });
        return this;
    }

    private void onAddPizza(String key) {
        stokeAlert(key);
        if (empty(key)) {
            System.out.println("Verifique o estoque dos demais produtos!");
        } else {
            var newValue = ingredientes.get(key) - 1;
            ingredientes.put(key, newValue);
        }
    }

    private void stokeAlert(String key) {
        if (ingredientes.get(key) < 10) {
            System.out.printf("O ingrediente %s está quase no fim. Reponha o estoque!\n", key);
        } else if (empty(key)) {
            System.out.printf("estoque no limite, parar saida do ingrediente %s\n", key);
        }
    }

    private boolean empty(String key) {
        return ingredientes.get(key) < 3;
    }
}