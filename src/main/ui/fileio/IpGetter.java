package ui.fileio;

import java.net.*;
import java.io.*;

public class IpGetter {

    public static String getIP() {
        URL whatismyip = null;
        try {
            whatismyip = new URL("http://checkip.amazonaws.com");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        BufferedReader in = null;
        String ip = null; //you get the IP as a String
        try {
            in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));

            ip = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ip;
    }


}
