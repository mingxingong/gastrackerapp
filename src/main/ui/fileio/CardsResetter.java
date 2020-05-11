package ui.fileio;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.creditcards.CreditCard;
import model.gasstations.GasStation;

import java.io.IOException;
import java.util.ArrayList;


public class CardsResetter extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/ui/Scene.fxml"));

        Scene scene = new Scene(root);

        stage.setTitle("JavaFX Geocode Example");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ArrayList<CreditCard> cards = new ArrayList();
        CreditCard c1 = new CreditCard("American Express Centurion",2, "Amex Points");
        CreditCard c2 = new CreditCard("TD Everyday CashBack Visa",1, "CashBack");
        CreditCard c3 = new CreditCard("BMO SPC CashBack Mastercard", 1,"CashBack");
        CreditCard c4 = new CreditCard("BMO SPC AirMiles Mastercard", 1,"Air Miles");
        CreditCard c5 = new CreditCard("PC World Elite Mastercard",5,"PC Points");
        CreditCard c6 = new CreditCard("American Express Cobalt", 2, "Amex Points");
        CreditCard c7 = new CreditCard("Scotiabank Momentum Visa Infinite", 2, "CashBack");
        CreditCard c8 = new CreditCard("TD Aeroplan Visa Infinite",1.5,"Aeroplan Miles");
        addGasStations(c5);
        addThreeCards(cards, c1, c2, c3);
        addThreeCards(cards, c4, c5, c6);
        cards.add(c7);
        cards.add(c8);

        CreditCardsWriter.write(cards);
    }

    private static void addThreeCards(ArrayList<CreditCard> cards, CreditCard c1, CreditCard c2, CreditCard c3) {
        cards.add(c1);
        cards.add(c2);
        cards.add(c3);
    }

    private static void addGasStations(CreditCard c5) {
        c5.addGasStation(new GasStation("Superstore"));
        c5.addGasStation(new GasStation("Esso"));
    }

}