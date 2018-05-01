package Sites;


import Schema.Data;

import java.util.ArrayList;

public class Peer {

    /**
     * Attributes
     */

    private static int nb_obj= 1;

    private int peerId;
    private ArrayList<Data> data;
    private boolean isUpeer; // *False ==> peer has the Id of his Upeer
                            //  *True  ==> peer is a Upeer and has peers
    // if Simple Peer
    private Data pmaxSys;
    private int upeer_id;

    // if Upeer
    private Data upmaxSys;
    private ArrayList<Peer> peers ;


    /**
     * Constructors
     */
    public Peer() {
        this.peerId = this.nb_obj++;
        this.isUpeer = false;
        this.data = new ArrayList<>();
    }


    /**
     * Get-Set
     */

    public int getPeerId() {
        return peerId;
    }
    public void setPeerId(int peerId) {
        this.peerId = peerId;
    }

    public ArrayList<Data> getData() {
        return this.data;
    }
    public void setData(ArrayList<Data> data) {
        this.data = data;
    }
    public void addData(Data d) {
        this.data.add(d);
    }
    public void delData(Data d) {
        this.data.remove(d);
    }

    public boolean isUpeer() {
        return isUpeer;
    }
    public void setIsUpeer(boolean isUpeer) {
        this.isUpeer = isUpeer;
        if (this.isUpeer){
            this.peers = new ArrayList<>();
            this.peers.add(this);
        }
        else{
            this.peers = null;
            this.upmaxSys = null;
        }
    }

    public int getUpeer_id() {
        return upeer_id;
    }

    public ArrayList<Peer> getPeers() {
        return peers;
    }
    public void setPeers(ArrayList<Peer> peers) {
        this.peers = peers;
    }
    public void addPeer(Peer p) {
        this.peers.add(p);
    }
    public void delPeer(Peer p) {
        this.peers.remove(p);
    }

    public Data getPmaxSys() {
        return pmaxSys;
    }
    public void setPmaxSys(Data maxSys) {
        this.pmaxSys = maxSys;
    }

    public Data getUpmaxSys() {
        return upmaxSys;
    }
    public void setUpmaxSys(Data maxSys) {
        this.upmaxSys = maxSys;
    }


    /**
     * Methodes
     */
    @Override
    public String toString() {
        return "peerId: "+this.peerId
                + " | isUpeer: "+this.isUpeer;
    }

}
