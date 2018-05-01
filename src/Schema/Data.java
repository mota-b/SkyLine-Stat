package Schema;

import java.util.ArrayList;

public class Data {


    /**
     * Attributes
     */
    public static int NB_ATTRIBUTES;
    private static int obj_count = 0;

    private int id;
    private ArrayList<String> attributes;


    /**
     * Constructor
     */
    public Data(ArrayList<String> attributes, boolean isTemp){
        if (isTemp)
            this.id = -1;
        else
            this.id = this.obj_count++;

        this.attributes = attributes;
    }


    /**
     * Methodes
     */
    // Getter & Setter
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<String> getAttributes() {
        return attributes;
    }
    public void setAttributes(ArrayList<String> attributes) {
        this.attributes = attributes;
    }

    public String getAttributeI(int attribute_index){
        return this.attributes.get(attribute_index);
    }

    // Obj Description
    @Override
    public String toString() {
        String row = "id:" + this.id;
        for (int i = 0; i < this.NB_ATTRIBUTES; i++) {
            row+= " | " + this.attributes.get(i);
        }
        return row;
    }

}
