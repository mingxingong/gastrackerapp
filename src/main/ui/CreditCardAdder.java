package ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.creditcards.CreditCard;
import model.gasstations.GasStation;
import ui.fileio.CreditCardsReader;
import ui.fileio.CreditCardsWriter;

import java.util.List;

public class CreditCardAdder extends Application {

    TextField nameField;
    TextField percentageField;
    TextField rewardTypeField;
    ComboBox gasStationBox;

    Text cardName = new Text("Credit Card Name");
    Text rewardPercentage = new Text("Reward Percentage");
    Text rewardType = new Text("Reward Type");
    Text isSpecificGasStation = new Text("Gas Station");

    @Override
    public void start(Stage primaryStage) throws Exception {
        Button done = new Button("Done");
        done.setOnAction(e -> doneButtonClicked());

        nameField = new TextField();
        percentageField = new TextField();
        rewardTypeField = new TextField();
        gasStationBox = new ComboBox();
        gasStationBox.getItems().addAll("Shell", "Chevron", "Mobil","Husky", "Petro-Canada");

        GridPane gridPane = setupGridPane(cardName, rewardPercentage, rewardType, isSpecificGasStation, done);

        Scene scene = new Scene(gridPane);

        primaryStage.setTitle("Add Credit Cards");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private GridPane setupGridPane(Text cardName, Text rp, Text rewardType, Text isSpecificGasStation, Button done) {
        GridPane gridPane = new GridPane();

        gridPane.setMinSize(400,300);
        gridPane.setPadding(new Insets(10,10,10,10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.CENTER);

        gridPane.add(cardName,0,0);
        gridPane.add(nameField,1,0);
        gridPane.add(rp,0,1);
        gridPane.add(percentageField,1,1);
        gridPane.add(rewardType,0,2);
        gridPane.add(rewardTypeField,1,2);
        gridPane.add(isSpecificGasStation,0,3);
        gridPane.add(gasStationBox,1,3);
        gridPane.add(done,0,4);
        return gridPane;
    }

    public void doneButtonClicked() {
        CreditCard card = new CreditCard("",0);
        card.setName(nameField.getText());
        try {
            card.setRewardPercentage(Double.parseDouble(percentageField.getText()));
            if (gasStationBox != null) {
                card.addGasStation(new GasStation((String)gasStationBox.getValue()));
            }
            card.setRewardType(rewardTypeField.getText());
            List<CreditCard> cards;
            cards = CreditCardsReader.main();
            cards.add(card);
            CreditCardsWriter.write(cards);
        } catch (Exception e) {
            e.printStackTrace();
        }
        clearFields();
    }

    private void clearFields() {
        nameField.clear();
        rewardTypeField.clear();
        percentageField.clear();
    }

}
