package com.lightbend.akka.sample;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

// extender da classe AbstractBehavior
// Determina que a classe terá um comportamento de ACTOR
// O tipo que AbstractBehavior recebe determina o tipo especifico
// de mesagem que o Actor pode receber
public class HelloWorld extends AbstractBehavior<HelloWorld.Command>{


    // A melhor prática para definir o tipo das mensagens que o ACTOR irá aceitar
    // É definir uma nested marker interface como o nível superior das mensagens
    interface Command {}

    // Aqui definiremos os dois tipos de mensagens concretas
    // que o Actor pode receber

    // A primeira Mensagem que aciona o Actor para dizer Hello.
    // Como essa mensagem não tem parâmetro
    // podemos defini-la como um singleton
    // usando um enum com valor único
    public enum SayHello implements Command {
        INSTANCE
    }

    public static class ChangeMessage implements Command {
        public final String newMessage;

        public ChangeMessage(String newMessage) {
            this.newMessage = newMessage;
        }
    }

    public static Behavior<Command> create(){
        return Behaviors.setup(HelloWorld::new);
    }

    private String message = "Hello World!!!";

    private HelloWorld(ActorContext<Command> context){
        super(context);
    }

    @Override
    public Receive<Command> createReceive() {
        return newReceiveBuilder()
                .onMessageEquals(SayHello.INSTANCE, this::onSayHello)
                .onMessage(ChangeMessage.class, this::onChangeMessage)
                .build();
    }

    private Behavior<Command> onChangeMessage(ChangeMessage command) {
        message = command.newMessage;
        return this;

    }


    private Behavior<Command> onSayHello(){
        System.out.println(message);
        return this;
    }
}
