import Schema.Data;
import Schema.Tools.DataManager;
import Sites.Peer;
import Sites.Tools.PeersManager;
import Sites.Tools.UpeersManager;
import ToolBox.*;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.markers.SeriesMarkers;

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


        ArrayList<Data> sky;
        ArrayList<String> plots;
        ArrayList<PlotManager> pms;



        /**
         * Core part
         */
        // 1) Get Data
        dataSet = FileManager.getDataSet(
                    "/home/zexes/Univercity/M2/System Distribue Grande Echelle/home/data/",
                    "c250.txt"//"e5200.txt"
                    );
        nbData = dataSet.size();



        // 2) Generate Peers
        nbp = 426;
        peers =  PeersManager.generatePeers(nbp);



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



        // 7) Get Stat && Set participant
        PeersManager.sky_participant(nbp, nbUp, maxSys, uPeers);
        ArrayList<Data> data_sky= new ArrayList<>();
        for (Peer p: peers
             ) {
            if (p.isPparticipant())
                for (Data d:p.getData()
                     ) {
                    data_sky.add(d);
                }
        }



        // 8) SkyLine
        sky = DataManager.skyLine(data_sky);




        /**
         * Display part
         */

        // 4) Display Console SkyLine
        System.out.println("\n\t SkyLine\n");
        System.out.println(sky.size()+"/"+data_sky.size());
        DataManager.display_data_console(sky);

        // 5) Display Plots SkyLine
        plots = PlotManager.create_plots(Data.NB_ATTRIBUTES);
        pms = PlotManager.create_pms(plots, data_sky);
        for (int i = 0; i < pms.size(); i++) {
            pms.get(i).addSeries("S"+plots.get(i)+" sky", SeriesMarkers.DIAMOND, XYSeries.XYSeriesRenderStyle.Line, DataManager.get_c_s(sky,Integer.valueOf(plots.get(i).split("/")[0])), DataManager.get_c_s(sky, Integer.valueOf(plots.get(i).split("/")[1])));
            pms.get(i).display_plot("plot"+plots.get(i));
        }




        // 9) Alternative skyLine by applying skyline on Uppeers firs then on peers then on data
        DataManager.alterSkyLine(uPeers);

        // Display
        //UpeersManager.display_distribution(peers, uPeers);


    }

}
