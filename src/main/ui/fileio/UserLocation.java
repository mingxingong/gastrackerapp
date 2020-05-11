package ui.fileio;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;

import java.io.File;
import java.net.InetAddress;

public class UserLocation {
    public static CityResponse getResponse() {
        CityResponse response;
        try {
            File database = new File("GeoLite2-City.mmdb");
            DatabaseReader dbReader = new DatabaseReader.Builder(database).build();

            InetAddress ipAddress = InetAddress.getByName(IpGetter.getIP());
            response = dbReader.city(ipAddress);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getLatitude() {
        return getResponse().getLocation().getLatitude().toString();
    }

    public static String getLongitude() {
        return getResponse().getLocation().getLongitude().toString();
    }

}
