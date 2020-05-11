package tests;

import model.creditcards.CreditCard;
import model.exceptions.NegativeValueException;
import model.gasstations.GasStation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class GasStationTest {
    private GasStation gs;

    @BeforeEach
    public void beforeEachTest(){
        gs = new GasStation("Shell", 130);
    }

    @Test
    public void testSetName() {
        assertEquals(gs.getName(), "Shell");
        gs.setName("Chevron");
        assertEquals("Chevron", gs.getName());
    }

    @Test
    public void testSetPrice() {
        try {
            assertEquals(gs.getPrice(), 130, 0);
            gs.setPrice(100);
            assertEquals(gs.getPrice(), 100, 0);
        } catch (NegativeValueException e) {
            fail();
        }
        try {
            assertEquals(gs.getPrice(), 100, 0);
            gs.setPrice(-100);
            fail();
            assertEquals(gs.getPrice(), 100, 0);
        } catch (NegativeValueException e) {
            // expected
        }
    }

    @Test
    public void testGetName(){
        assertEquals(gs.getName(),"Shell");
    }

    @Test
    public void testGetPrice(){
        assertEquals(gs.getPrice(),130,0);
    }

    @Test
    public void testLocation() {
        GasStation gs1 = new GasStation("Chevron");
        gs1.setLatitude(10);
        gs1.setLongitude(20);
        assertEquals(10,gs1.getLatitude());
        assertEquals(20, gs1.getLongitude());
        gs1.setLocation(100,200);
        assertEquals(100,gs1.getLatitude());
        assertEquals(200,gs1.getLongitude());
        GasStation gs2 = new GasStation("Shell", 100,200);
        assertEquals(100,gs2.getLatitude());
        assertEquals(200,gs2.getLongitude());
    }

    @Test
    public void testFindLowestOfTwoHigher(){
        GasStation chevron1 = new GasStation("Chevron1",129);
        assertEquals(gs.findLowestOfTwo(chevron1),"Chevron1");
    }

    @Test
    public void testFindLowestOfTwoLower(){
        GasStation chevron1 = new GasStation("Chevron1",131);
        assertEquals(gs.findLowestOfTwo(chevron1),"Shell");
    }

    @Test
    public void testFindLowestOfTwo(){
        GasStation chevron1 = new GasStation("Chevron1",130);
        assertEquals(gs.findLowestOfTwo(chevron1),"Chevron1");
    }

    @Test
    public void testFindLowestOfTwoOthersDiff(){
        GasStation chevron1 = new GasStation("Chevron1",131);
        assertEquals(gs.findLowestOfTwo(chevron1),"Shell");
    }

    @Test
    public void testAddCreditCard() {
        assertEquals(0,gs.getCreditCards().size());
        CreditCard c1 = new CreditCard("Amex Platinum",3);
        CreditCard c2 = new CreditCard("Scotiabank Momentum",4);
        gs.addCreditCard(c1);
        assertEquals(c1,gs.getCreditCards().get(0));
        assertEquals(1,gs.getCreditCards().size());
        gs.addCreditCard(c2);
        assertEquals(2,gs.getCreditCards().size());
        assertEquals(c1,gs.getCreditCards().get(0));
        assertEquals(c2,gs.getCreditCards().get(1));
        gs.addCreditCard(c2);
        assertEquals(2,gs.getCreditCards().size());
        assertEquals(c1,gs.getCreditCards().get(0));
        assertEquals(c2,gs.getCreditCards().get(1));
    }

    @Test
    public void testEquals() {
        GasStation gs2 = gs;
        assertTrue(gs.equals(gs2));
        GasStation gs3 = null;
        assertFalse(gs.equals(gs3));
        CreditCard c4 = new CreditCard("Amex Platinum", 3);
        assertFalse(gs.equals(c4));
        GasStation gs4 = new GasStation("Shell");
        assertTrue(gs.equals(gs4));
        GasStation gs5 = new GasStation("Chevron");
        assertFalse(gs.equals(gs5));
    }

    @Test
    public void testHashCode() {
        gs.hashCode();
    }

    @Test
    public void testGetSetAddress() {
        gs.setAddress("100 Infinite Loop");
        assertEquals("100 Infinite Loop", gs.getAddress());
    }

    @Test
    public void testGetSetPriceWithRewards() {
        try {
            gs.setPriceWithReward(100);
        } catch (NegativeValueException e) {
            fail();
        }
        assertEquals(100,gs.getPriceWithReward());
        try {
            gs.setPriceWithReward(0);
        } catch (NegativeValueException e) {
            fail();
        }
        assertEquals(0,gs.getPriceWithReward());
        try {
            gs.setPriceWithReward(-1);
            fail();
        } catch (NegativeValueException e) {
            //expected
        }
        assertEquals(0,gs.getPriceWithReward());
        try {
            gs.setPriceWithReward(-100);
            fail();
        } catch (NegativeValueException e) {
            //expected
        }
        assertEquals(0,gs.getPriceWithReward());
    }

    @Test
    public void testFindBestCard() {
        ArrayList<CreditCard> cards = new ArrayList<>();
        try {
            gs.setPrice(100);
        } catch (NegativeValueException e) {
            e.printStackTrace();
        }
        assertEquals(null,gs.findBestCard(cards));
        CreditCard c02 = new CreditCard("TD Visa",2);
        cards.add(c02);
        assertEquals(c02,gs.findBestCard(cards));
        CreditCard c2= new CreditCard("Scotiabank Visa Momentum",2);
        cards.add(c2);
        assertEquals(c02,gs.findBestCard(cards));
        CreditCard c3 = new CreditCard("American Express",3);
        cards.add(c3);
        assertEquals(c3,gs.findBestCard(cards));
        CreditCard c1 = new CreditCard("TD Visa",1);
        assertEquals(c3,gs.findBestCard(cards));
        GasStation superStore = new GasStation("SuperStore");
        try {
            superStore.setPrice(100);
        } catch (NegativeValueException e) {
            e.printStackTrace();
        }
        CreditCard pc = new CreditCard("PC World Mastercard", 5);
        pc.addGasStation(superStore);
        cards.add(pc);
        assertEquals(c3,gs.findBestCard(cards));
        assertEquals(pc,superStore.findBestCard(cards));
    }
}
