package DistAI3Task2;


import jade.content.ContentElement;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.basic.Action;
import jade.content.onto.basic.Result;
import jade.core.*;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import jade.domain.JADEAgentManagement.QueryPlatformLocationsAction;
import jade.domain.mobility.MobilityOntology;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.util.leap.HashMap;
import jade.util.leap.Map;
import jade.wrapper.*;


/**
 * Created by araxi on 2016-11-27.
 */
public class Auctioneer extends Agent {
    private Map locations = new HashMap();
    private Location[] locationsArray;

    @Override
    protected void setup() {

        getContentManager().registerLanguage(new SLCodec());
        getContentManager().registerOntology(MobilityOntology.getInstance());
//Get the JADE runtime interface (singleton)
        jade.core.Runtime runtime = jade.core.Runtime.instance();
//Create a Profile, where the launch arguments are stored
        Profile profile1 = new ProfileImpl();
        profile1.setParameter(Profile.CONTAINER_NAME, "TestContainer1");
        profile1.setParameter(Profile.MAIN_HOST, "localhost");
        Profile profile2 = new ProfileImpl();
        profile2.setParameter(Profile.CONTAINER_NAME, "TestContainer2");
        profile2.setParameter(Profile.MAIN_HOST, "localhost");
//create a non-main agent container
        ContainerController container1 = runtime.createAgentContainer(profile1);
        ContainerController container2 = runtime.createAgentContainer(profile2);


        //AgentController ag1 = container1.createNewAgent("agentnick" + "", "DistAI3Task2.Auctioneer", new Object[]{});//arguments
        //AgentController ag2 = container2.createNewAgent("agentnick1" + "", "DistAI3Task2.Auctioneer", new Object[]{});//arguments

// Get available locations with AMS
        sendRequest(new Action(getAMS(), new QueryPlatformLocationsAction()));
        try {
            MessageTemplate mt = MessageTemplate.and(
                    MessageTemplate.MatchSender(getAMS()),
                    MessageTemplate.MatchPerformative(ACLMessage.INFORM));
            ACLMessage resp = blockingReceive(mt);
            ContentElement ce = getContentManager().extractContent(resp);
            Result result = (Result) ce;
            jade.util.leap.Iterator it = result.getItems().iterator();

            while (it.hasNext()) {
                Location loc = (Location) it.next();
                locations.put(loc.getName(), loc);
                System.out.println("location är: " + loc + " location.getname är " + loc.getName());
            }



        }catch(Exception e){
        }


    }




    void sendRequest(Action action) {

        ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
        request.setLanguage(new SLCodec().getName());
        request.setOntology(MobilityOntology.getInstance().getName());
        try {
            getContentManager().fillContent(request, action);
            request.addReceiver(action.getActor());
            send(request);
        }
        catch (Exception ex) { ex.printStackTrace(); }
    }

}









