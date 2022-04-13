package com.lightbend.akka.sample;

import akka.actor.typed.ActorSystem;

public class ImcApp  {
    public static void main(String[] args) {
        ActorSystem<Imc.Command> imcSys = ActorSystem.create(Imc.create(), "imcSys");
        imcSys.tell(new Imc.newImc(70, 1.8));
    }
}
