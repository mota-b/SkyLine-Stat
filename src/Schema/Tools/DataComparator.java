package Schema.Tools;

import Schema.Data;
import java.util.Comparator;

public class DataComparator implements Comparator<Data> {

    /**
     * Attribute
     */
    private int critere;

    /**
     * Constructor
     */
    public DataComparator(int critere) {
        this.critere = critere;
    }


    /**
     * Methodes
     */

    // Getter & Setter
    public void setCritere(int critere) {
        this.critere = critere;
    }
    public int getCritere() {
        return critere;
    }

    // Comparing methode
    @Override
    public int compare(Data dataNC, Data t1) {
        int cmp = 0;

        if (Double.valueOf(dataNC.getAttributes().get(critere)) <= Double.valueOf(t1.getAttributes().get(critere)))
            cmp= 1;
        else
            cmp= -1;

        return cmp;
    }
}
