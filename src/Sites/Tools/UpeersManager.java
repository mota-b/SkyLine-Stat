package Sites.Tools;

import Schema.Data;
import Sites.Peer;
import ToolBox.PseudoRand;

import java.util.ArrayList;

public class UpeersManager {

    /**
     * Methodes
     */

    // Election pseudo-rand of Upeers 15% from Peers
    public static void upeersElection(ArrayList<Peer> peers){
        int nb_Upeer = (int) (0.15*peers.size()); // Rule: Upeers are 15% from Peers
        ArrayList<String> upeersIndex= PseudoRand.p_rand_data(1, peers.size(), nb_Upeer);

        for (int i = 0; i < upeersIndex.size() ; i++) {
            int index = Integer.valueOf(upeersIndex.get(i) );
            peers.get(  index  ).setIsUpeer(true);
        }
    }

    // Return list of Upeers
    public static ArrayList<Peer> get_Upeers(ArrayList<Peer> peers){
        ArrayList<Peer> upeers = new ArrayList();

        for (Peer p: peers
             ) {
            if (p.isUpeer())
                upeers.add(p);
        }
        return upeers;
    }

    // Distribute peers on Upeers
    public static void distrubute_rand_peers(ArrayList<Peer> uPeers, ArrayList<Peer> peers, int pseudo_rand_start){
        int x0 = pseudo_rand_start;
        int m = peers.size();
        int n = uPeers.size();

        // Generate data by pseudo-random
        for (int i = 0, x, y; (i <m) ; i++) {
            y = PseudoRand.p_rand_equation(x0, n); // y : index of a pseudo-random Upeer
            x = PseudoRand.p_rand_equation(x0, m); // x : index of a pseudo-random Peer

            if (!peers.get(x).isUpeer())
                uPeers.get(y).addPeer(peers.get(x)); // affect the pseudo-random peer to pseudo-random uPeer
            x0 = x;
        }

    }

    // Display peers distribution on Upeers
    public static void display_distribution(ArrayList<Peer> uPers){

        for (Peer up: uPers
                ) {
            System.out.println("\nUPEER\t"+up+" maxSysUP ==> "+up.getUpmaxSys()+"\n ||\n ||_");
            for (Peer p: up.getPeers()
                    ) {

                System.out.println("\t PEER\t"+p+" maxSysP ==> "+p.getPmaxSys()+"\n");
                for (Data d: p.getData()
                     ) {
                    System.out.println("\t   |_\n\t\t  DATA\t"+d+"\n");
                }
            }
        }
    }
}


