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
public class HelloWorld extends AbstractBehavior<HelloWorld.Command> {


    // A melhor prática para definir o tipo das mensagens que o ACTOR irá aceitar
    // É definir uma nested marker interface como o nível superior das mensagens
    interface Command {
    }

    // Aqui definiremos os dois tipos de mensagens concretas
    // que o Actor pode receber

    // A primeira Mensagem que aciona o Actor para dizer Hello.
    // Como essa mensagem não tem parâmetro
    // podemos defini-la como um singleton
    // usando um enum com valor único
    public enum SayHello implements Command {
        INSTANCE
    }

    //Como essa mensagem tem um parametro que deverá ser a nova msg hello
    // então foi definida como uma classe mesmo
    public static class ChangeMessage implements Command {
        // Como todas as mensagens no Akka devem ser imutáveis
        // podemos pular os getters e setters e definir os campos como public final
        public final String newMessage;

        //Definimos então um construtor que receberá o parâmetro
        public ChangeMessage(String newMessage) {
            this.newMessage = newMessage;
        }
    }


    public static Behavior<Command> create() {
        return Behaviors.setup(HelloWorld::new);
    }

    // Aqui é definido o estado do Actor
    // Pelo fato de podermos alterar a mensagem com um comando
    // precisamos manter a mensagem como um campo mutável dentro do actor
    // Essa é a mensagem inicial (default)
    private String message = "Hello World!!!";

    private HelloWorld(ActorContext<Command> context) {
        super(context);
    }


    // Aqui é definida a lógica, o comportamento que é acionado
    // ao receber uma mensagem
    @Override
    public Receive<Command> createReceive() {
        // A definição do recebido é iniciada através de um builder
        // onde é possível criar um método para cada tipo de mensagem recebida
        return newReceiveBuilder() // então definimos um manipulador
                .onMessageEquals(SayHello.INSTANCE, this::onSayHello) // como o primeiro metodo retorna a mensagem exata ao invés do tipo usamos onMessageEquals
                .onMessage(ChangeMessage.class, this::onChangeMessage)
                .build();
    }

    private Behavior<Command> onChangeMessage(ChangeMessage command) {
        message = command.newMessage;
        return this;
    }


    private Behavior<Command> onSayHello() {
        System.out.println(message);
        return this;
    }
}
