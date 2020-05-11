package model.gasstations;


import java.util.ArrayList;

public class AllGasStations {
    public ArrayList<GasStation> allGasStations;

    //Constructor
    //MODIFIES: this
    //EFFECTS: creates a new AllGasStations with an empty list of gas stations
    public AllGasStations() {
        this.allGasStations = new ArrayList<>();
    }

    //REQUIRES: gs not null
    //MODIFIES: this
    //EFFECTS: adds Gas Station gs to the list of all gas stations
    public void addGasStation(GasStation gs) {
        this.allGasStations.add(gs);
    }

    //EFFECTS: returns number of gas stations in this AllGasStations
    public int size() {
        return allGasStations.size();
    }

    //EFFECTS: returns the GasStation with the correct name in  AllGasStations
    public GasStation get(int i) {
        return allGasStations.get(i);
    }

    //REQUIRES: gsl is a non-empty ArrayList
    //EFFECTS: produces name of the GasStation in the arraylist with the cheapest regular gas.
    public GasStation findLowest() {
        GasStation select = null;
        for (GasStation gs: allGasStations) {
            if (select == null) {
                select = gs;
            } else {
                if (gs.getPrice() < select.getPrice()) {
                    select = gs;
                }
            }
        }
        return select;
    }


}
