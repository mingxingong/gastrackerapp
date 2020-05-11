package ui;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.creditcards.CreditCard;
import model.gasstations.GasStation;
import ui.fileio.CreditCardWriter2;
import ui.fileio.CreditCardsReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreditCardSelector extends Application {
    Text choose = new Text("Choose Credit Card");

    List<CreditCard> cards;

    {
        try {
            cards = CreditCardsReader.main();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    ;
    List<CreditCard> selected = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception {

        VBox content = new VBox();
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);

        GridPane gridPane = new GridPane();
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.CENTER);
        content.getChildren().add(gridPane);

        initializeTop(gridPane);
        initializeCards(gridPane);


        GridPane buttons = new GridPane();
        buttons.setHgap(20);
        makeButtons(gridPane, buttons);
        content.getChildren().add(buttons);

        Scene scene = new Scene(scrollPane, 650, 400);

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    private void makeButtons(GridPane gridPane, GridPane buttons) {
        Button addCard = new Button("Add new credit card");
        setActionForAddCard(gridPane, addCard);

        Button refresh = new Button("Refresh");
        setActionForRefresh(gridPane, refresh);

        Button saveButton = new Button("Save Cards");
        setActionForSave(gridPane, saveButton);

        buttons.add(addCard,0,0);
        buttons.add(refresh,1,0);
        buttons.add(saveButton,2,0);
        buttons.setAlignment(Pos.CENTER);
    }

    private void setActionForSave(GridPane gridPane, Button saveButton) {
        saveButton.setOnAction(e -> {
            try {
                saveCards(gridPane.getChildren());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void setActionForRefresh(GridPane gridPane, Button refresh) {
        refresh.setOnAction(e -> {
            try {
                cards = CreditCardsReader.main();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
            initializeCards(gridPane);
        });
    }

    private void setActionForAddCard(GridPane gridPane, Button addCard) {
        addCard.setOnAction(e -> {
            try {
                new CreditCardAdder().start(new Stage());
                cards = CreditCardsReader.main();
                initializeCards(gridPane);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private void initializeCards(GridPane gridPane) {
        for (int i = 1; i < cards.size() + 1; i++) {
            addNewRow(gridPane,i);
        }
    }

    private void saveCards(ObservableList<Node> children) throws IOException {
        for (int i = 0; i < children.size(); i++) {
            CheckBox cb = new CheckBox();
            if (children.get(i).getClass() == cb.getClass()) {
                CheckBox checkbox = (CheckBox) children.get(i);
                boolean isSelected = checkbox.isSelected();
                if (isSelected) {
                    CreditCard c = cards.get((i - 4) / 5);
                    selected.add(c);
                }
            }
        }
        CreditCardWriter2.write(selected);
    }

    private void initializeTop(GridPane gridPane) {
        Text name = new Text("Name");
        Text rewardPercentage = new Text("Reward Percentage");
        Text rewardType = new Text("Reward Type");
        Text gasStation = new Text("Accepted at:");
        gridPane.add(name,0,0);
        gridPane.add(rewardPercentage,1,0);
        gridPane.add(rewardType,2,0);
        gridPane.add(gasStation,3,0);
    }

    private void addNewRow(GridPane gridPane, int i) {
        CreditCard card = cards.get(i - 1);
        Text name = new Text(card.getName());
        Text rewardPercentage = new Text(Double.toString(card.getRewardPercentage()));
        Text rewardType = new Text(card.getRewardType());
        Text gasStations = new Text(gasStationFormatter(card));
        CheckBox checkbox = new CheckBox("Select");
        gridPane.add(name,0,i);
        gridPane.add(rewardPercentage,1,i);
        gridPane.add(rewardType,2,i);
        gridPane.add(gasStations,3,i);
        gridPane.add(checkbox,4,i);
    }

    private String gasStationFormatter(CreditCard c) {
        List<GasStation> stations = c.getGasStations();
        String result = "";
        if (stations.size() > 0) {
            for (GasStation gs : stations) {
                result = result + gs.getName() + ", ";
            }
            return result.substring(0, result.length() - 2);
        } else {
            return "any";
        }
    }
}
