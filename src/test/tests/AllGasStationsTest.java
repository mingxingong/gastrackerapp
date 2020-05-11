package tests;

import model.creditcards.CreditCard;
import model.exceptions.NegativeValueException;
import model.gasstations.AllGasStations;
import model.gasstations.GasStation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


public class AllGasStationsTest {
    private AllGasStations log;

    @BeforeEach
    public void beforeEachTest(){
        log = new AllGasStations();
    }

    @Test
    public void testAddOne() {
        assertEquals(log.size(), 0);
        GasStation chevron = new GasStation("Chevron");
        log.addGasStation(chevron);
        assertEquals(log.size(),1);
        assertEquals(chevron,log.get(0));
    }

    @Test
    public void testAddTwo() {
        assertEquals(log.size(), 0);
        GasStation chevron = new GasStation("Chevron");
        log.addGasStation(chevron);
        assertEquals(log.size(),1);
        assertEquals(chevron,log.get(0));
        GasStation superstore = new GasStation("Superstore");
        log.addGasStation(superstore);
        assertEquals(log.size(), 2);
        assertEquals(log.get(1),superstore);
        assertEquals(log.get(0),chevron);
    }

    @Test
    public void testFindLowest() {
        try {
            GasStation chevron;
            chevron = new GasStation("Chevron");
            chevron.setPrice(135.9);

            GasStation shell;
            shell = new GasStation("Shell");
            shell.setPrice(129.9);

            GasStation superStore;
            superStore = new GasStation("SuperStore", 133.9);

            log.addGasStation(chevron);
            log.addGasStation(shell);
            log.addGasStation(superStore);

            assertEquals(log.findLowest(), shell);
        } catch (NegativeValueException e) {
            fail();
        }
    }

    @Test
    public void testFindLowestOthersLower() {
        try {
            GasStation chevron;
            chevron = new GasStation("Chevron");
            chevron.setPrice(135.9);

            GasStation shell;
            shell = new GasStation("Shell");
            shell.setPrice(129.9);

            GasStation superStore;
            superStore = new GasStation("SuperStore", 133.9);

            log.addGasStation(chevron);
            log.addGasStation(shell);
            log.addGasStation(superStore);

            assertEquals(log.findLowest(), shell);
        } catch (NegativeValueException e) {
            fail();
        }
    }

    @Test
    public void testFindLowestDiffPos() {
        try {
            GasStation chevron;
            chevron = new GasStation("Chevron");
            chevron.setPrice(135.9);

            GasStation shell;
            shell = new GasStation("Shell");
            shell.setPrice(129.9);

            GasStation superStore;
            superStore = new GasStation("SuperStore",133.9);

            log.addGasStation(chevron);
            log.addGasStation(superStore);
            log.addGasStation(shell);

            assertEquals(log.findLowest(), shell);
        } catch (NegativeValueException e) {
            fail();
        }
    }


    @Test
    public void testSetPrices() {
        try {
            GasStation chevron;
            chevron = new GasStation("Chevron");
            chevron.setPrice(90);

            GasStation shell;
            shell = new GasStation("Shell");
            shell.setPrice(129.9);

            CreditCard amex = new CreditCard("AmericanExpress", 2);
            log.addGasStation(chevron);
            log.addGasStation(shell);

            assertEquals(log.get(0).getPrice(), 90, 0);
            assertEquals(log.get(1).getPrice(), 129.9, 0);
        } catch (NegativeValueException e) {
            fail();
        }
    }

}
