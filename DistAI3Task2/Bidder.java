package DistAI3Task2;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

/**
 * Created by araxi on 2016-11-27.
 */
public class Bidder extends Agent {

    @Override
    protected void setup() {

addBehaviour(new TickerBehaviour(this, 5000) {
    @Override
    protected void onTick() {

        ACLMessage getMsg = receive();
        if (getMsg != null && getMsg.getPerformative() == ACLMessage.INFORM);

        System.out.println("I am a bidder and i received a message from " + getMsg.getSender().getLocalName());
    }
});
    }
}
