package Sites.Tools;

import Schema.Data;
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


}
