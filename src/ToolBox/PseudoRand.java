package ToolBox;

import java.util.ArrayList;

public class PseudoRand {

    /**
     * Attribute
     */
    final public static int A = 5; // ?forgote
    final public static int C = 1; // ?forgote

    /**
     * Methodes
     */
    // Pseudo-random equation [Recursif]
    public static int p_rand_equation(int x0, int m){
        int i = 1;
        int mm = (int) Math.pow((double) 2, (double) i) ;

        while (mm< m){
            mm = (int) Math.pow((double) 2, (double) i);
            i++;
        }
        x0 = (PseudoRand.A * x0 + PseudoRand.C)% mm;

        if (x0 < m )                                                                                                     //&& x0!=0) // added not null
            return x0;
        else
            return p_rand_equation(x0, m);
    }

    // Generate pseudo-random data
    public static ArrayList<String> p_rand_data(int x0, int m, int row){
        // Create array of arrays
        ArrayList<String> list = new ArrayList();

        // Generate data by pseudo-random
        for (int i = 0, x; (i <m && i< row) || (list.size()< row); i++) { // Condition Details
            x = p_rand_equation(x0, m);                             // 1) (i <this.m && i< this.limi_data) ==> i in module range AND i < limit_row [Deny repetition]
            list.add(x+"");                                             //  2) (list.size()< this.limi_data) ==> rows generated < rows limit [Allow repetition]
            x0 = x;
        }


        return list;
    }
}
