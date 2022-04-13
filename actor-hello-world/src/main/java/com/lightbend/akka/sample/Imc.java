package com.lightbend.akka.sample;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class Imc extends AbstractBehavior<Imc.Command> {

    private Imc( ActorContext<Command> context) {
        super(context);
    }

    public static Behavior<Command> create(){
       return Behaviors.setup(Imc::new);
    }

    public interface Command {
    }

    public static class newImc implements Command {
        public final double peso;
        public final double altura;

        public newImc(double peso, double altura) {
            this.peso = peso;
            this.altura = altura;
        }
    }

    @Override
    public Receive<Command> createReceive() {
        return newReceiveBuilder()
                .onMessage(newImc.class, this::onNewImc)
                .build();
    }

    private Behavior<Command> onNewImc(newImc imc) {
        double value = imc.peso / Math.pow(imc.altura, 2);
        System.out.printf("imc = %.2f\n", value);
        return this;
    }
}