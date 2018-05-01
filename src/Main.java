import Schema.Data;
import Schema.Tools.DataManager;
import Sites.Peer;
import Sites.Tools.PeersManager;
import Sites.Tools.UpeersManager;
import ToolBox.*;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        /**
         * Programme Attributes
         */
        ArrayList<Data> dataSet;
        int nbData;

        ArrayList<Peer> peers;
        int nbp;

        ArrayList<Peer> uPeers;
        int nbUp;


        //ArrayList<Data> sky;
        //ArrayList<String> plots;
        //ArrayList<PlotManager> pms;


        /**
         * Core part
         */
        // 1) Get Data
        dataSet = FileManager.getDataSet(
                    "/home/zexes/Univercity/M2/System Distribue Grande Echelle/home/data/",
                    "a250.txt"//"e5200.txt"
                    );
        nbData = dataSet.size();



        // 2) Generate Peers
        nbp = 426;
        peers =  PeersManager.generatePeers(nbp); // TODO why max 55



        // 3) Distribute Data
        DataManager.distribute_data_toPeers(peers, dataSet, 4);



        // 4) Upeers Election
        UpeersManager.upeersElection(peers);



        // 5) Distribute Peers on Upeers
        uPeers = UpeersManager.get_Upeers(peers);
        nbUp = uPeers.size();
        UpeersManager.distrubute_rand_peers(uPeers, peers, 4);



        // 6) Get MaxSys
        Data maxSys = DataManager.maxSys_upeers(uPeers);



        // 7) Get Stat
        int nbUpeers = uPeers.size();
        System.out.println("Existant" +
                "\n\tpeers : "+nbp+
                "\n\tupeers : "+nbUp);
        ArrayList<Integer> participant = new ArrayList<>(DataManager.particip(maxSys, uPeers));

        System.out.println("Stat Participant" +
                "\n\tpeers : "+((double)participant.get(0)/(double)nbp)*100+"%"+
                "\n\tupeers : "+((double)participant.get(1)/(double)nbUp)*100+"%");






        // Display
        //UpeersManager.display_distribution(peers, uPeers);


    }





}
