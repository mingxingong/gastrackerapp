package ui.fileio;

import model.gasstations.GasStation;

import java.util.List;

public class MapUrlGenerator {

    public static final String KEY = "N9RqjwYTItGZJdcd20FSNDN3fZobq5y6";

    public static String getUrl(List<GasStation> gasStationList) {
        String url;
        url = "https://www.mapquestapi.com/staticmap/v5/map?locations="
                + gasStationAddressMaker(gasStationList)
                + "&size=400,400@2x&key=" + KEY;

        return url;

    }

    private static String gasStationAddressMaker(List<GasStation> allStations) {
        String urlElement = "";
        for (GasStation gs: allStations) {
            String address = gs.getAddress();
            address = address.replaceAll(" ","+");
            address = address.replaceAll("\n","%0A");
            urlElement = urlElement + address + "%7C%7C";
        }
        urlElement = urlElement.substring(0, urlElement.length() - 6);
        return urlElement;
    }
}
