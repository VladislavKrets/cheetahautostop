package online.omnia.autostop;

import java.util.List;

/**
 * Created by lollipop on 26.09.2017.
 */
public class Buyer {
    private String buyerName;
    private List<Adset> adsets;

    public Buyer(String buyerName, List<Adset> adsets) {
        this.adsets = adsets;
        this.buyerName = buyerName;
    }

    public List<Adset> getAdsets() {
        return adsets;
    }

    public void setAdsets(List<Adset> adsets) {
        this.adsets = adsets;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    @Override
    public String toString() {
        return "Buyer{" +
                "buyerName='" + buyerName + '\'' +
                ", adsets=" + adsets +
                '}';
    }
}
