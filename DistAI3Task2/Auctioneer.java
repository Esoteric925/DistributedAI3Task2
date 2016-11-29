package DistAI3Task2;


import jade.content.ContentElement;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.basic.Action;
import jade.content.onto.basic.Result;
import jade.core.*;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.JADEAgentManagement.QueryPlatformLocationsAction;
import jade.domain.mobility.MobilityOntology;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


import jade.wrapper.*;


/**
 * Created by araxi on 2016-11-27.
 */
public class Auctioneer extends Agent {


    @Override
    protected void setup() {

        addBehaviour(new TickerBehaviour(this, 2000) {
            @Override
            protected void onTick() {
                System.out.println("Hello jag är" + getLocalName());

            }
        });


        }

}









