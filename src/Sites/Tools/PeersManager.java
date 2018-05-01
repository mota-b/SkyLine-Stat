package Sites.Tools;

import Schema.Data;
import Schema.Tools.DataManager;
import Sites.Peer;

import java.util.ArrayList;

public class PeersManager {


    /**
     * Methodes
     */

    // Generate of peers
    public static ArrayList<Peer> generatePeers(int nb_peer){
        ArrayList<Peer> peers = new ArrayList();

        for (int i = 0; i <nb_peer ; i++) {
            peers.add(new Peer());
        }

        return peers;
    }

    // Display list of Peers by id
    public static void display_peers_console(ArrayList<Peer> peers){
        for (Peer p: peers
             ) {
            System.out.println(p);
        }
    }

    // Display data distribution on peers
    public static void display_distribution(ArrayList<Peer> peers){
        for (Peer p: peers
                ) {
            System.out.println("\t PEER\t"+p);
            for (Data d: p.getData()
                    ) {
                System.out.println("\t   |_\n\t\t  DATA\t"+d+"\n");
            }
        }
    }

    // Set && Display participants in SkyLine
    public static void sky_participant(int nbp, int nbUp, Data maxSys, ArrayList<Peer> uPeers){

        ArrayList<Integer> participant = new ArrayList<>(DataManager.particip(maxSys, uPeers));
        double partPsent = ((double)participant.get(0)/(double)nbp)*100;
        double partUPsent = ((double)participant.get(1)/(double)nbUp)*100;

        System.out.println("Existant" +
                "\n\tpeers : "+nbp+
                "\n\tupeers : "+nbUp);
        System.out.println("Stat Participant" +
                "\n\tpeers : "+partPsent+"% ==> "+participant.get(0)+
                "\n\tupeers : "+partUPsent+"% ==> "+participant.get(1));
        System.out.println("Stat NON Participant" +
                "\n\tpeers : "+(100-partPsent)+"% ==> "+(nbp-participant.get(0))+
                "\n\tupeers : "+(100-partUPsent)+"% ==> "+(nbUp-participant.get(1)));
    }

    // Get list of maxSys from Peers
    public static ArrayList<Data> getMaxSysList(ArrayList<Peer> peers, int i){
        // i == 0 for maxSys of peers
        // i == 1 for maxSys of uPeers

        ArrayList<Data> maxSysList = new ArrayList<>();

        if (i ==0)
            for (Peer p: peers
                 ) {
                maxSysList.add(p.getPmaxSys());
            }
        else
            for (Peer p: peers
                    ) {
                maxSysList.add(p.getUpmaxSys());
            }

        return maxSysList;
    }

}
