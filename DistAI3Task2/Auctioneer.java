package DistAI3Task2;

import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;
import jade.core.AID;
import jade.core.Agent;
import jade.core.AgentContainer;
import jade.core.ContainerID;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.AMSService;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.domain.FIPAException;
import jade.domain.JADEAgentManagement.WhereIsAgentAction;
import jade.lang.acl.ACLMessage;

/**
 * Created by araxi on 2016-11-27.
 */
public class Auctioneer extends Agent{

    @Override
    protected void setup() {




        addBehaviour(new TickerBehaviour(this, 3000) {
            @Override
            protected void onTick() {

                try {
                    AMSAgentDescription[] agents;
                    SearchConstraints c = new SearchConstraints();
                    c.setMaxResults(new Long (-1));
                    agents = AMSService.search(myAgent, getAMS(),new AMSAgentDescription(),c);
                    AID myID = getAID();// this methode to get the idesntification of //agents such as (Name , adress , host ....etc)
                    for (int i=0; i<agents.length;i++)
                    {
                        AID agentID = agents[i].getName();
                        System.out.println(
                                ( agentID.equals( myID ) ? "*** " : "    ")
                                        + i + ": " + agentID.getName()
                        );
                }

                 ACLMessage newMsg = new ACLMessage(ACLMessage.INFORM);
                    newMsg.addReceiver(agents[agents.length - 1].getName());
                    newMsg.setContent("hohaha");
                    send(newMsg);

                 } catch (FIPAException e) {
                    e.printStackTrace();
                }

                ACLMessage receive = receive();
                if (receive != null && receive.getPerformative() == ACLMessage.INFORM){

                    System.out.println(receive.getContent());
                }
               // doDelete();// kill agent
            }
        });

    }
}
