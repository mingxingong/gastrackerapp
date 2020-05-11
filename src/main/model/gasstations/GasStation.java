package model.gasstations;


import model.creditcards.CreditCard;
import model.exceptions.NegativeValueException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GasStation implements Serializable {
    private String name;
    private double latitude;
    private double longitude;
    private double price;
    private double priceWithReward;
    private int gasGrade;
    private String address;
    private List<CreditCard> creditCards = new ArrayList<CreditCard>();

    //Constructors
    //EFFECTS: produces new GasStation with given name and prices all set to 0.
    public GasStation(String name) {
        this.name = name;
        this.price = 0;
    }

    public GasStation(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.price = 0;
    }

    //EFFECTS: produces GasStation with given name and Price, midrange price, premium price all set accordingly
    public GasStation(String name, double newPrice) {
        this.name = name;
        this.price = newPrice;
    }


    //Getters
    //EFFECTS: returns name of this GasStation
    public String getName() {
        return name;
    }

    //EFFECTS: returns regular price at this GasStation
    public double getPrice() {
        return price;
    }

    //EFFECTS: returns latitude of this GasStation
    public double getLatitude() {
        return this.latitude;
    }

    //EFFECTS: returns the address of the GasStation
    public String getAddress() {
        return address;
    }

    //EFFECTS: returns longitude of this GasStation
    public double getLongitude() {
        return this.longitude;
    }

    public double getPriceWithReward() {
        return this.priceWithReward;
    }

    public void setPriceWithReward(double newPrice) throws NegativeValueException {
        if (newPrice < 0) {
            throw new NegativeValueException();
        }
        this.priceWithReward = newPrice;
    }

    //Setters
    //MODIFIES: this
    //EFFECTS: sets the name of this GasStation to newName
    public void setName(String newName) {
        this.name = newName;
    }

    //MODIFIES: this
    //EFFECTS: sets the regular price at this GasStation to newPrice
    public void setPrice(double newPrice) throws NegativeValueException {
        if (newPrice < 0) {
            throw new NegativeValueException();
        }
        this.price = newPrice;
    }

    //MODIFIES: this
    //EFFECTS: sets the address at this Gas Station to newAddress
    public void setAddress(String newAddress) {
        this.address = newAddress;
    }

    //MODIFIES: this
    //EFFECTS: sets latitude of this gas station to latitude
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    //MODIFIES: this
    //EFFECTS: sets longitude of this gas station to longitude
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    //MODIFIES: this
    //EFFECTS: sets the latitude and longitude of this gas station to latitude and longitude
    public void setLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    //EFFECTS: produces the name of this GasStation or gs, whichever is cheaper.
    // gs will be produced if prices are equal
    public String findLowestOfTwo(GasStation gs) {
        if (this.getPrice() < gs.getPrice()) {
            return this.getName();
        } else {
            return gs.getName();
        }
    }

    //MODIFIES: this, c
    //EFFECTS: adds a new credit card to creditCards and adds this gasStation to the creditCard's gasStations
    // if not already added.
    public void addCreditCard(CreditCard c) {
        if (!creditCards.contains(c)) {
            creditCards.add(c);
            c.addGasStation(this);
        }
    }

    public CreditCard findBestCard(List<CreditCard> cards) {
        if (cards.size() == 0) {
            return null;
        }
        CreditCard bestCard = cards.get(0);
        for (CreditCard c: cards) {
            if (c.calculateRewards(this) > bestCard.calculateRewards(this)) {
                bestCard = c;
            }
        }
        return bestCard;
    }

    public List<CreditCard> getCreditCards() {
        return this.creditCards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GasStation that = (GasStation) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
