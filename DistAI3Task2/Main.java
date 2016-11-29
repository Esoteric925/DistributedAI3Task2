package DistAI3Task2;

import jade.content.ContentElement;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.basic.Action;
import jade.content.onto.basic.Result;
import jade.core.Agent;
import jade.core.Location;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.domain.JADEAgentManagement.QueryPlatformLocationsAction;
import jade.domain.mobility.MobilityOntology;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by araxi on 2016-11-28.
 */
public class Main extends Agent {
    //private Map locations = new HashMap();
    private HashMap locations = new <String,Location>HashMap();
    AgentController auctioneer;

    @Override
    protected void setup() {

        getContentManager().registerLanguage(new SLCodec());
        getContentManager().registerOntology(MobilityOntology.getInstance());
//Get the JADE runtime interface (singleton)
        jade.core.Runtime runtime = jade.core.Runtime.instance();
//Create a Profile, where the launch arguments are stored
        Profile profile1 = new ProfileImpl();
        profile1.setParameter(Profile.CONTAINER_NAME, "Container1");
        profile1.setParameter(Profile.MAIN_HOST, "localhost");
        Profile profile2 = new ProfileImpl();
        profile2.setParameter(Profile.CONTAINER_NAME, "Container2");
        profile2.setParameter(Profile.MAIN_HOST, "localhost");



//create a non-main agent container
        ContainerController container1 = runtime.createAgentContainer(profile1);
        ContainerController container2 = runtime.createAgentContainer(profile2);

/*
        try {
            auctioneer = container1.createNewAgent("Auctioneer" + "", "DistAI3Task2.Auctioneer", new Object[]{});
            auctioneer.start();
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
*/





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


            //lägger in den i rätt container(som är Main-container) men den startar inte
            while (it.hasNext()) {
                Location loc = (Location) it.next();
                //ContainerID containerID = (ContainerID) it.next();
                locations.put(loc.getName(), loc);

                System.out.println("location är: " + loc + " location.getname är " + loc.getName());
                // System.out.println("containerId är: " + containerID + " containerId getname är " + containerID.getName());
            }


                Iterator itt = locations.entrySet().iterator();
                while (itt.hasNext()) {
                    Map.Entry pair = (Map.Entry)itt.next();
                    System.out.println(pair.getKey() + " = " + pair.getValue());

                }


         /*   while (it.hasNext()) {
                Location loc = (Location) it.next();

                if(loc.getName().equalsIgnoreCase("Main-Container")){
                    this.getContainerController().createNewAgent("Auctioneer" + "", "DistAI3Task2.Auctioneer", new Object[]{});
                }

                if(loc.getName().equalsIgnoreCase("Container1")){
                    //this.getContainerController().createNewAgent("Auctioneer" + "", "DistAI3Task2.Auctioneer", new Object[]{});

                    auctioneer.clone(loc,"AuctioneerClone1");

                }
                if(loc.getName().equalsIgnoreCase("Container2")){
                    //this.getContainerController().createNewAgent("Auctioneer" + "", "DistAI3Task2.Auctioneer", new Object[]{});
                    auctioneer.clone(loc,"AuctioneerClone2");

                }
            }*/


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
