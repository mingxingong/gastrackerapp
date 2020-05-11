package ui.fileio;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import model.exceptions.NegativeValueException;
import model.gasstations.GasStation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Scraper {

    public static List<GasStation> scrape(String baseUrl) {
        WebClient client = new WebClient();
        setup(client);

        List<GasStation> gasStations = new ArrayList<>();

        try {
            HtmlPage page = client.getPage(baseUrl);
            List<HtmlElement> items;
            items = page.getByXPath("//div[@class='panel__panel___3Q2zW panel__white___19KTz colors__"
                    + "bgWhite___1stjL panel__bordered___1Xe-S panel__rounded___2etNE GenericStationListItem__"
                    + "station___1O4vF']");

            makeGasStations(gasStations, page);
            List<Object> prices = page.getByXPath("//span[@class='text__left___1iOw3 GenericStationListItem__"
                    + "price___3GpKP']");
            List<Object> addresses = page.getByXPath("//div[@class='GenericStationListItem__address___1VFQ3']");
            setPriceAndLocation(gasStations, prices, addresses);

            return gasStations;

        } catch (IOException e) {
            return gasStations;
        }

    }

    private static void setup(WebClient client) {
        client.getOptions().setJavaScriptEnabled(false);
        client.getOptions().setCssEnabled(false);
        client.getOptions().setUseInsecureSSL(true);
    }

    private static void makeGasStations(List<GasStation> gasStations, HtmlPage page) {
        List<Object> names = page.getByXPath("//h3[@class='header__header3___1b1oq header__header___1zII0 header"
                + "__evergreen___2DD39 header__snug___lRSNK GenericStationListItem__stationNameHeader___3qxdy']");
        for (Object element : names) {
            HtmlElement element1 = (HtmlElement) element;
            GasStation station = new GasStation(element1.asText().substring(0,element1.asText().length() - 1));
            gasStations.add(station);
        }
    }

    private static void setPriceAndLocation(List<GasStation> gasStations, List<Object> prices, List<Object> addresses) {
        int i;
        for (i = 0; i < gasStations.size(); i++) {
            GasStation gs;
            gs = gasStations.get(i);
            HtmlElement price = (HtmlElement) prices.get(i);
            HtmlElement address = (HtmlElement) addresses.get(i);
            if (price.asText().equals("---")) {
                gasStations.remove(i);
            } else {
                try {
                    gs.setPrice(new Double(price.asText().substring(0, price.asText().length() - 1)));
                } catch (NegativeValueException e) {
                    e.printStackTrace();
                }
                gs.setAddress(address.asText());
            }
        }
    }

    //EFFECTS: returns the url for the appropriate gasbuddy webpage
    public static String makeUrl(String latitude, String longitude, int gasGrade) {
        String result;
        result = "https://www.gasbuddy.com/home?lat=" + latitude + "&lng="
                + longitude + "&fuel=" + gasGrade;
        return result;
    }

    public static String makeUrl2(String lat, String lng, int gasGrade) {
        String result;
        result = "https://www.gasbuddy.com/home?fuel=" + gasGrade + "&lat=" + lat + "&lng=" + lng;
        return result;
    }
}
