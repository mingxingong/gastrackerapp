package ui;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.creditcards.CreditCard;
import model.exceptions.NegativeValueException;
import model.gasstations.GasStation;
import ui.fileio.CreditCardReader2;
import ui.fileio.MapUrlGenerator;
import ui.fileio.Scraper;
import ui.fileio.UserLocation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private Stage primaryStage;
    private AnchorPane rootLayout;

    private String lat = UserLocation.getLatitude();
    private String lng = UserLocation.getLongitude();
    private List<GasStation> stations = Scraper.scrape(Scraper.makeUrl(lat,lng,1));

    private List<CreditCard> currentCards;

    {
        try {
            currentCards = CreditCardReader2.main();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    int cheapestIndex = 0;

    private VBox leftControl = new VBox();
    private GridPane gridPane = new GridPane();
    private VBox rightControl = new VBox(gridPane);
    private ScrollPane scroller = new ScrollPane(rightControl);

    public Main() {
        lat = UserLocation.getLatitude();
    }

    @Override
    public void start(Stage primaryStage) throws IOException, ClassNotFoundException, NegativeValueException {
        SplitPane splitPane = new SplitPane();

        String mapUrl = MapUrlGenerator.getUrl(stations);

        ImageView imageView = getImageView(mapUrl);

        scroller.setFitToWidth(true);

        splitPane.getItems().addAll(leftControl,scroller);
        splitPane.setDividerPositions(0.62);

        HBox gasSelectors = initGasSelectors();
        makeGasStations();
        setCheapest(gridPane.getChildren());
        makeGasStations();
        initButtons();
        leftControl.getChildren().add(imageView);
        leftControl.getChildren().add(gasSelectors);

        Scene scene = new Scene(splitPane, 1280, 827);

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void setCheapest(ObservableList<Node> children) {
        int counter = 2;
        ArrayList<Node> rewardPrices = new ArrayList<>();

        filterPrices(children, counter, rewardPrices);
        ArrayList<Double> numbers = new ArrayList<>();

        convertPrices(rewardPrices, numbers);
        for (int i = 0; i < numbers.size(); i++) {
            if (numbers.get(i) < numbers.get(cheapestIndex)) {
                cheapestIndex = i;
            }
        }
        System.out.println(cheapestIndex);
    }

    private void convertPrices(ArrayList<Node> rewardPrices, ArrayList<Double> numbers) {
        for (Node n: rewardPrices) {
            Text text = (Text) n;
            numbers.add(Double.parseDouble(text.getText().substring(0, text.getText().length() - 1)));
        }
    }

    private void filterPrices(ObservableList<Node> children, int counter, ArrayList<Node> rewardPrices) {
        for (Node n: children) {
            if (counter == 0) {
                rewardPrices.add(n);
                counter = 4;
            } else {
                counter -= 1;
            }
        }
    }

    private HBox initGasSelectors() {
        HBox gasSelectors = new HBox(0);
        Button regularGas = getButton(gasSelectors);
        getGasButton(gasSelectors, "MidGrade", 2);
        getGasButton(gasSelectors, "Premium", 3);
        getGasButton(gasSelectors,"Diesel", 4);
        return gasSelectors;
    }

    private void getGasButton(HBox gasSelectors, String midGrade, int i) {
        Button midgradeGas = new Button(midGrade);
        midgradeGas.setOnAction(e -> {
            stations = Scraper.scrape(Scraper.makeUrl2(lat, lng, i));
            try {
                makeGasStations();
                setCheapest(gridPane.getChildren());
                makeGasStations();
            } catch (NegativeValueException | IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
            initButtons();
        });
        gasSelectors.getChildren().add(midgradeGas);
    }

    private Button getButton(HBox gasSelectors) {
        Button regularGas = new Button("Regular");
        regularGas.setOnAction(e -> {
            stations = Scraper.scrape(Scraper.makeUrl2(lat,lng,1));
            try {
                makeGasStations();
            } catch (NegativeValueException | IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
            initButtons();
        });
        gasSelectors.getChildren().add(regularGas);
        return regularGas;
    }

    private void initButtons() {
        Button chooseCreditCards = new Button("Choose Cards");
        setChooseCreditCardsButton(chooseCreditCards);
        Button refresh = new Button("Refresh");
        setRefreshButton(refresh);
        HBox buttons = new HBox();
        buttons.getChildren().addAll(chooseCreditCards,refresh);
        buttons.setAlignment(Pos.CENTER);
        gridPane.addRow(21,buttons);
    }

    private void setRefreshButton(Button refresh) {
        refresh.setOnAction(e -> {
            try {
                makeGasStations();
                initButtons();
            } catch (NegativeValueException | ClassNotFoundException | IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void makeGasStations() throws NegativeValueException, IOException, ClassNotFoundException {
        currentCards = CreditCardReader2.main();
        gridPane.getChildren().clear();
        for (int i = 0; i < stations.size(); i++) {
            addNewRow(gridPane,i, currentCards);
        }
    }

    private void setChooseCreditCardsButton(Button chooseCreditCards) {
        chooseCreditCards.setOnAction(e -> {
            try {
                new CreditCardSelector().start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private ImageView getImageView(String mapUrl) {
        Image image = new Image(mapUrl);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(800);
        imageView.setPreserveRatio(true);
        Rectangle2D viewport = new Rectangle2D(0,0,800,800);
        imageView.setViewport(viewport);
        return imageView;
    }

    private GridPane addNewRow(GridPane gridPane, int i, List<CreditCard> currentCards) throws NegativeValueException {
        GasStation gs = stations.get(i);
        CreditCard bestCard = gs.findBestCard(currentCards);

        Text name = new Text(gs.getName());
        name.setFont(new Font(24));
        Text address = new Text(gs.getAddress());

        Text realPrice = makeBigPrice(gs, bestCard);

        Text price = setPrice(gs);

        Text rewards = makeRewards(gs, bestCard);

        price.setTextAlignment(TextAlignment.LEFT);

        styleCheapest(i, name, address, realPrice, price, rewards);

        gridPane.add(name,0,2 * i);
        gridPane.add(address,0,2 * i + 1);
        gridPane.add(realPrice,1,2 * i,1,2);
        gridPane.add(price,2,2 * i);
        gridPane.add(rewards,2,2 * i + 1);



        return gridPane;
    }

    private Text makeRewards(GasStation gs, CreditCard bestCard) {
        String rw = Double.toString(bestCard.calculateRewards(gs));
        if (rw.length() >= 6) {
            rw = rw.substring(0,5);
        }
        return new Text(rw + " in " + bestCard.getRewardType());
    }

    private Text setPrice(GasStation gs) {
        if (gs.getPrice() == 0) {
            stations.remove(gs);
        }
        String p = Double.toString(gs.getPrice());
        if (p.length() >= 6) {
            p = p.substring(0,5);
        }
        Text price = new Text(gs.getPrice() + "¢");
        price.setFont(new Font(16));
        return price;
    }

    private void styleCheapest(int i, Text name, Text address, Text realPrice, Text price, Text rewards) {
        if (i == cheapestIndex) {
            name.setFill(Color.web("#5AC8FA"));
            address.setFill(Color.web("#5AC8FA"));
            realPrice.setFill(Color.web("#5AC8FA"));
            price.setFill(Color.web("#5AC8FA"));
            rewards.setFill(Color.web("#5AC8FA"));
        }
    }

    private Text makeBigPrice(GasStation gs, CreditCard bestCard) throws NegativeValueException {
        gs.setPriceWithReward(bestCard.calculateRealPrice(gs));
        String rp = Double.toString(gs.getPriceWithReward());
        if (rp.length() > 6) {
            rp = rp.substring(0,5);
        }
        Text realPrice = new Text(rp + "¢");
        realPrice.setFont(new Font(36));
        realPrice.setTextAlignment(TextAlignment.RIGHT);
        return realPrice;
    }


    public static void main(String[] args) {
        launch(args);
    }



}
