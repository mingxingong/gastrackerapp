package tests;

import model.creditcards.CreditCard;
import model.exceptions.NegativeValueException;
import model.gasstations.GasStation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class CreditCardTest {
    CreditCard c1;
    CreditCard c2;
    CreditCard c3;

    @BeforeEach
    public void setup() {
        c1 = new CreditCard("PC World Mastercard",7);
        c2 = new CreditCard("American Express Platinum",5,"Amex Points");
        c3 = new CreditCard();
    }

    @Test
    public void testGetSetRewardPercentage() {
        try {
            assertEquals(c1.getRewardPercentage(), 7, 0);
            c1.setRewardPercentage(8);
            assertEquals(c1.getRewardPercentage(), 8, 0);
            assertEquals(c2.getRewardPercentage(), 5, 0);
        } catch (NegativeValueException e) {
            fail();
        }
        try {
            assertEquals(c1.getRewardPercentage(), 8, 0);
            c1.setRewardPercentage(-8);
            fail();
            assertEquals(c1.getRewardPercentage(), 8, 0);
            assertEquals(c2.getRewardPercentage(), 5, 0);
        } catch (NegativeValueException e) {
            // expected
        }
    }

    @Test
    public void testSetGetName() {
        assertEquals("PC World Mastercard", c1.getName());
        c1.setName("Scotiabank Momentum Visa Infinite");
        assertEquals("Scotiabank Momentum Visa Infinite", c1.getName());
    }

    @Test
    public void testGasStations() {
        assertFalse(c1.isSpecificGasStation());
        GasStation gs1 = new GasStation("Chevron");
        assertEquals(0,c1.getGasStations().size());
        c1.addGasStation(gs1);
        assertTrue(c1.isSpecificGasStation());
        assertEquals(1,c1.getGasStations().size());
        assertEquals(gs1,c1.getGasStations().get(0));
        GasStation gs2 = new GasStation("Shell");
        c1.addGasStation(gs2);
        assertEquals(2,c1.getGasStations().size());
        assertEquals(gs1,c1.getGasStations().get(0));
        assertEquals(gs2,c1.getGasStations().get(1));
        c1.addGasStation(gs2);
        assertEquals(2,c1.getGasStations().size());
        assertEquals(gs1,c1.getGasStations().get(0));
        assertEquals(gs2,c1.getGasStations().get(1));
        assertTrue(c1.isSpecificGasStation());
    }

    @Test
    public void testSetGetRewardType() {
        assertEquals("Amex Points", c2.getRewardType());
        c2.setRewardType("American Express Points");
        assertEquals("American Express Points",c2.getRewardType());
    }

    @Test
    public void testCalculateRewards() {
        GasStation gs = new GasStation("Chevron");
        try {
            gs.setPrice(100);
        } catch (NegativeValueException e) {
            e.printStackTrace();
        }
        assertEquals(7,c1.calculateRewards(gs));
        try {
            gs.setPrice(200);
        } catch (NegativeValueException e) {
            e.printStackTrace();
        }
        assertEquals(14,c1.calculateRewards(gs));
        try {
            c1.setRewardPercentage(5);
        } catch (NegativeValueException e) {
            e.printStackTrace();
        }
        assertEquals(10,c1.calculateRewards(gs));
        assertFalse(c1.isSpecificGasStation());
        c1.addGasStation(gs);
        assertTrue(c1.isSpecificGasStation());
        assertEquals(1,c1.getGasStations().size());
        assertTrue(c1.getGasStations().contains(gs));
        assertEquals(10,c1.calculateRewards(gs));
        GasStation gs2 = new GasStation("Shell");
        assertTrue(c1.isSpecificGasStation());
        try {
            gs2.setPrice(100);
        } catch (NegativeValueException e) {
            e.printStackTrace();
        }
        assertFalse(c1.getGasStations().contains(gs2));
        assertEquals(0,c1.calculateRewards(gs2));
        c1.addGasStation(gs2);
        assertEquals(2,c1.getGasStations().size());
        assertEquals(gs,c1.getGasStations().get(0));
        assertEquals(gs2,c1.getGasStations().get(1));
        assertEquals(5,c1.calculateRewards(gs2));
    }

    @Test
    public void testEquals() {
        CreditCard c4 = c1;
        assertTrue(c1.equals(c4));
        CreditCard c5 = null;
        assertFalse(c1.equals(c5));
        GasStation gs1 = new GasStation("Chevron");
        assertFalse(c1.equals(gs1));
        CreditCard c6 = new CreditCard("Scotiabank Momentum",4,"CashBack");
        assertFalse(c1.equals(c6));
        CreditCard c7 = new CreditCard("American Express Platinum",5,"Amex Points");
        assertTrue(c2.equals(c7));
        CreditCard c8 = new CreditCard("American Express",5,"Amex Points");
        assertFalse(c2.equals(c8));
        CreditCard c9 = new CreditCard("American Express Platinum",5,"CashBack");
        assertFalse(c2.equals(c9));
        CreditCard c10 = new CreditCard("American Express Platinum",5,"Amex Points");
        c10.addGasStation(gs1);
        assertNotEquals(c2, c10);
        c2.addGasStation(gs1);
        assertEquals(c2, c10);
    }

    @Test
    public void testHashCode() {
        c2.hashCode();
    }

    @Test
    public void testCalculateRealPrice() {
        GasStation gs = new GasStation("SuperStore");
        try {
            gs.setPrice(100);
        } catch (NegativeValueException e) {
            fail();
        }
        assertEquals(93,c1.calculateRealPrice(gs));
    }

}
