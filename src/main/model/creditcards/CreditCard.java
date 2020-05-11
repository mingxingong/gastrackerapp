package model.creditcards;

import model.exceptions.NegativeValueException;
import model.gasstations.GasStation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CreditCard implements Serializable {
    private String name;
    private double rewardPercentage;
    private String rewardType;
    private List<GasStation> gasStations = new ArrayList<>();
    private boolean specificGasStation = false;

    public CreditCard(String newName, double newRewardPercentage) {
        this.name = newName;
        this.rewardPercentage = newRewardPercentage;
    }

    public CreditCard() {
        this.name = "";
        this.rewardPercentage = 0;
    }

    public CreditCard(String newName, double rewardPercentage, String rewardType) {
        this.name = newName;
        this.rewardPercentage = rewardPercentage;
        this.rewardType = rewardType;

    }

    //Getters
    //EFFECTS: produces name of CreditCard
    public String getName() {
        return this.name;
    }

    //EFFECTS: returns RewardPercentage of CreditCard
    public double getRewardPercentage() {
        return this.rewardPercentage;
    }

    //EFFECTS: returns isSpecificGasStation of CreditCard
    public Boolean isSpecificGasStation() {
        return (gasStations.size() > 0);
    }

    //EFFECTS: returns the gasStations field of CreditCard
    public List getGasStations() {
        return gasStations;
    }

    //EFFECTS: returns the rewardType of this card
    public String getRewardType() {
        return this.rewardType;
    }

    //Setters
    //MODIFIES: this
    //EFFECTS: changes name of CreditCard to newName
    public void setName(String newName) {
        this.name = newName;
    }

    //MODIFIES: this
    //EFFECTS: changes RewardPercentage of CreditCard to newName
    public void setRewardPercentage(double newRewardPercentage) throws NegativeValueException {
        if (newRewardPercentage < 0) {
            throw new NegativeValueException();
        } else {
            this.rewardPercentage = newRewardPercentage;
        }
    }

    //MODIFIES: this
    //EFFECTS: sets the rewardType of this card to rewardType
    public void setRewardType(String rewardType) {
        this.rewardType = rewardType;
    }

    //MODIFIES: this, gs
    //EFFECTS: adds gs to gasStations and adds this creditCard to the GasStation's list of credit cards
    // if it is not already added.
    public void addGasStation(GasStation gs) {
        if (!gasStations.contains(gs)) {
            gasStations.add(gs);
            this.specificGasStation = true;
            gs.addCreditCard(this);
        }
    }

    //EFFECTS: calculates amount of rewards points earned when originalPrice of gas is purchased
    public double calculateRewards(GasStation gs) {
        if (specificGasStation) {
            if (gasStations.contains(gs)) {
                return returnGasPrice(gs);
            } else {
                return 0;
            }
        } else {
            return returnGasPrice(gs);
        }
    }

    private double returnGasPrice(GasStation gs) {
        return rewardPercentage * gs.getPrice() * 0.01;
    }

    public double calculateRealPrice(GasStation gs) {
        return gs.getPrice() - calculateRewards(gs);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CreditCard that = (CreditCard) o;
        return Double.compare(that.rewardPercentage, rewardPercentage) == 0
                && name.equals(that.name)
                && gasStations.equals(that.gasStations)
                && rewardType.equals(that.rewardType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, rewardPercentage, gasStations, rewardType);
    }


}

