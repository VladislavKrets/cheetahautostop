package online.omnia.autostop;


import java.io.*;
import java.util.*;

/**
 * Created by lollipop on 09.08.2017.
 */
public class Utils {

    public static synchronized Map<String, String> iniFileReader() {
        Map<String, String> properties = new HashMap<>();
        try (BufferedReader iniFileReader = new BufferedReader(new FileReader("configuration.ini"))) {
            String property;
            String[] propertyArray;
            while ((property = iniFileReader.readLine()) != null) {
                propertyArray = property.split("=");
                if (property.contains("=")) {
                    properties.put(propertyArray[0], propertyArray[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public static synchronized List<Buyer> configReader() {
        List<Buyer> buyers = new ArrayList<>();
        List<Adset> adsets = new ArrayList<>();
        List<Integer> ids = new ArrayList<>();
        List<String> lines = new ArrayList<>();
        try (BufferedReader configReader = new BufferedReader(new FileReader("cheetah_autostop.cfg"))) {
            String line = null;
            while ((line = configReader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String buyerName = null;
        Buyer buyer = null;
        int sum = 0;
        int conversions = 0;
        String prefix = "";
        boolean isAdset = false;
        boolean isBuyer = false;
        for (String line : lines) {
            if (line.equals("[adset]")) {
                if (isAdset) {
                    adsets.add(new Adset(sum, conversions, prefix, ids));
                    ids = new ArrayList<>();
                }
                isAdset = true;
            } else if (line.matches("\\[.+\\]")) {
                if (isBuyer) {
                    isAdset = false;
                    buyer = new Buyer(buyerName, adsets);
                    adsets.add(new Adset(sum, conversions, prefix, ids));
                    buyers.add(buyer);
                    adsets = new ArrayList<>();
                }
                buyerName = line.replaceAll("([\\[\\]])", "");
                isBuyer= true;
            } else if (line.contains("sum=")) {
                sum = Integer.parseInt(line.split("=")[1]);
            }
            else if (line.contains("conversions=")) {
                conversions = Integer.parseInt(line.split("=")[1]);
            }
            else if (line.contains("prefix=")) {
                prefix = line.split("=")[1];
            }
            else {
                ids.add(Integer.valueOf(line));
            }
        }
        adsets.add(new Adset(sum, conversions, prefix, ids));
        buyers.add(new Buyer(buyerName, adsets));
        return buyers;
    }

}
