package Schema.Tools;

import Schema.Data;
import Sites.Peer;
import Sites.Tools.PeersManager;
import ToolBox.PlotManager;
import ToolBox.PseudoRand;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.util.ArrayList;

public class DataManager {




    /**
     * Methodes
     */
    // Data distribution
    public static void distribute_data_toPeers(ArrayList<Peer> peers, ArrayList<Data> data, int pseudo_rand_start){
        int x0 = pseudo_rand_start;
        int m = data.size();
        int n = peers.size();


        for (int i = 0, x, y; (i <m) ; i++) {
            y = PseudoRand.p_rand_equation(x0, n); // y : index of a pseudo-random Peer
            x = PseudoRand.p_rand_equation(x0, m); // x : index of a pseudo-random Data

            peers.get(y).addData(data.get(x)); // affect the pseudo-random data to  pseudo-random peer
            x0 = x;
        }
    }

    /**
     * MaxSys
     */
    // Get maxSys From list of Data
    public static Data maxSys_data(ArrayList<Data> data){

        ArrayList<String> critere = new ArrayList<>();
        Data max_temp ;
        if (data.size()==0){ // au cas ou le peer ne contient pas de "Data" en en créé 1 Symbolique : qui n'influera pas sur le traitement
            for (int i = 0, nc = 5; i < nc ; i++) {
                critere.add(""+Double.POSITIVE_INFINITY);
            }
            max_temp = new Data(critere, true);
        }
        else{

            // Create Sorted Liste for each Critere
            ArrayList<ArrayList<Data>> sorted_critere = new ArrayList<>();
            DataComparator cmp = new DataComparator(0);

            // Sort Croissent
            for (int i = 0,nb_c= Data.NB_ATTRIBUTES ;i < nb_c ;i++) {
                ArrayList<Data> sorted_i= new ArrayList<Data>(data); // init

                cmp.setCritere(i); // set sorting critere
                sorted_i.sort(cmp.reversed()); // sort

                sorted_critere.add(sorted_i); // append sorted list
            }



            // Set the maxSys attributes
            double maxC = Double.POSITIVE_INFINITY;
            for (int i = 0,  nc= sorted_critere.size(); i < nc; i++) {

                if(i==0) // If u want X(maxSys) ==> init with next Critere
                    maxC = Double.valueOf(sorted_critere.get(i+1).get(0).getAttributeI(i));
                else // Init with First Critere
                    maxC = Double.valueOf(sorted_critere.get(0).get(0).getAttributeI(i));

                // Search max Curent Critere From First element in each Sorted List
                for (int j = 0; j < nc; j++) {
                    if (j!=i)
                        if(maxC< Double.valueOf(sorted_critere.get(j).get(0).getAttributeI(i)))
                            maxC = Double.valueOf(sorted_critere.get(j).get(0).getAttributeI(i));
                }
                critere.add(String.valueOf(maxC));
                //critere.add(sorted_critere.get(i).get(nd-1).getC(i));
            }

            max_temp = new Data(critere, true);
        }

        return max_temp;
    }

    // Get maxSys from peers
    public static Data maxSys_peers(ArrayList<Peer> peers){

        ArrayList<Data> maxSys_list = new ArrayList<>();
        Data max_temp;
        for (Peer p : peers
                ) {

            max_temp = new Data(maxSys_data(p.getData()).getAttributes(), true);
            p.setPmaxSys(max_temp);

            maxSys_list.add(max_temp);
        }

        return maxSys_data(maxSys_list);
    }

    // Get maxSys from Upeers
    public static Data maxSys_upeers(ArrayList<Peer> upeers) {

        ArrayList<Data> maxSys_upeers = new ArrayList<>();
        Data max_temp;
        for (Peer up : upeers
                ) {

            max_temp = new Data(maxSys_peers(up.getPeers()).getAttributes(), true);
            up.setUpmaxSys(max_temp);

            maxSys_upeers.add(max_temp);
        }

        return maxSys_data(maxSys_upeers);
    }

    // Display nb participant in skyLine
    public static ArrayList<Integer> particip(Data maxSys, ArrayList<Peer> uPeers){
        int nbp_doSky = 0;
        int nbup_doSky = 0;
        boolean up_doSky = true;
        for (Peer up: uPeers
                ) {

            boolean p_doSky = true;
            for (Peer p: up.getPeers()
                    ) {

                p_doSky = DataManager.isDom(maxSys, p.getPmaxSys()); // p do skyline if not dominated by maxSys
                up_doSky = up_doSky & p_doSky; // up do sky if all peers of the up do SkyLine

                if (!p_doSky) {nbp_doSky++; p.setPparticipant(true);}
                else{p.setPparticipant(false);}
                p_doSky = true;
            }

            if (!up_doSky) nbup_doSky++;
            up_doSky = true;
        }


        ArrayList<Integer> nbs = new ArrayList<>();
        nbs.add(nbp_doSky);
        nbs.add(nbup_doSky);

        return nbs;
    }



    /**
     * SkyLine
     */
    // Display Console Set of Data
    public static void display_data_console(ArrayList<Data> dataSet ){
        for (Data row: dataSet
                ) {
            System.out.println(row);
        }
    }

    // Get Critere Vector AKA(Column)
    public static ArrayList<Double> get_c_s(ArrayList<Data> data, int c_index){
        ArrayList<Double> c1 = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            c1.add(Double.valueOf(data.get(i).getAttributes().get(c_index)));
        }
        return c1;
    }

    // Get SkyLine from data
    public static ArrayList<Data> skyLine(ArrayList<Data> data){


        // Create Sorted Liste for each Critere
        ArrayList<ArrayList<Data>> sorted_critere = new ArrayList<>();
        DataComparator cmp = new DataComparator(0);
        for (int i = 0,nb_c= Data.NB_ATTRIBUTES ;i < nb_c ;i++) {
            ArrayList<Data> sorted_i= new ArrayList<Data>(data); // init

            cmp.setCritere(i); // set sorting critere
            sorted_i.sort(cmp.reversed()); // sort

            sorted_critere.add(sorted_i); // append sorted list
        }

        // Create SkyLine
        ArrayList<Data> sky = new ArrayList<>(); // SkyLine
        ArrayList<Integer> domined = new ArrayList<>(); // IndexList of dominated individu
        for (int i = 0; i < data.size() && domined.indexOf(i)==-1;i++) {

            if (sky.indexOf(sorted_critere.get(0).get(i)) ==-1 )
            {
                sky.add(sorted_critere.get(0).get(i));
                for (int j = i+1; j < data.size()-1; j++) {

                    if (isDominant(sorted_critere, sorted_critere.get(0).get(i),sorted_critere.get(0).get(j))){
                        domined.add(j);
                    }
                    else if (sky.indexOf(sorted_critere.get(0).get(j))==-1) {
                        sky.add(sorted_critere.get(0).get(j));
                        i = sorted_critere.get(0).indexOf(sky.get(sky.size()-1)); // Next individue is non Dominated One !
                    }
                }
            }

        }


        return sky;
    }

    // Individu I domine Individu J ?
    private static boolean isDominant(ArrayList<ArrayList<Data>> dataSorted, Data dataI, Data dataJ) {
        boolean v = true;

        for (int i = 0, nb_c = dataSorted.size(); i < nb_c ; i++) {
            // Must domine for Each Critere
            v = v & Double.valueOf(dataSorted.get(i).indexOf(dataI)) < Double.valueOf(dataSorted.get(i).indexOf(dataJ));
        }

        return v;
    }

    // Data I domine Data J
    public static boolean isDom(Data dataI, Data dataJ) {

        boolean v = true;
        for (int i = 0, nb_c = dataI.NB_ATTRIBUTES; i < nb_c ; i++) {
            // Must domine for Each Critere
            v = v & Double.valueOf(dataI.getAttributeI(i) ) < Double.valueOf(dataJ.getAttributeI(i) ) ;
        }

        return v;
    }

    // Upeers SkyLine
    public static void alterSkyLine(ArrayList<Peer> uPeers){
        // Editable uPeers list
        ArrayList<Peer> ups = new ArrayList<>(uPeers);

        // SkyLine on Upeers by maxSys
        ArrayList<Data> upeers_maxSys = PeersManager.getMaxSysList(uPeers, 1);
        ArrayList<Data> upeer_sky = skyLine(upeers_maxSys);

        for (Peer up: ups
             ) {
            if(upeer_sky.indexOf(up.getUpmaxSys())==-1) // upeer not in the upeerSkyline
                //ups.remove(up); // remove the upeer
                up.setUPparticipant(false);
            else{ // upeer in the upeerSkyLine
                up.setUPparticipant(true);
                // SkyLine on peers by maxSys
                ArrayList<Data> peers_maxSys = PeersManager.getMaxSysList(up.getPeers(), 0);
                ArrayList<Data> peer_sky = skyLine(peers_maxSys);

                for (Peer p: up.getPeers()
                     ) {
                    if(peer_sky.indexOf(p.getPmaxSys())==-1)
                        //up.getPeers().remove(p);
                        p.setPparticipant(false);
                    else
                        p.setPparticipant(true);
                }

            }
        }

        // Sky

        ArrayList<Data> data_sky= new ArrayList<>();
        for (Peer up: ups
                ) {
            if (up.isUPparticipant())
                for (Peer p: up.getPeers()
                        ) {

                    if (p.isPparticipant())
                        for (Data d: p.getData()
                             ) {
                            data_sky.add(d);
                        }
                }
        }


        ArrayList<Data> sky = skyLine(data_sky);
        ArrayList<String> plots;
        ArrayList<PlotManager> pms;

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
    }


}
