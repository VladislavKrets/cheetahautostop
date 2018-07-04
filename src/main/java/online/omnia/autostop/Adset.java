package online.omnia.autostop;

import java.util.List;

/**
 * Created by lollipop on 26.09.2017.
 */
public class Adset {
    private int sum;
    private int conversions;
    private String prefix;
    private List<Integer> adsetNumbers;

    public Adset(int sum, int conversions, String prefix, List<Integer> adsetNumbers) {
        this.sum = sum;
        this.conversions = conversions;
        this.prefix = prefix;
        this.adsetNumbers = adsetNumbers;
    }

    public int getConversions() {
        return conversions;
    }

    public void setConversions(int conversions) {
        this.conversions = conversions;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setAdsetNumbers(List<Integer> adsetNumbers) {
        this.adsetNumbers = adsetNumbers;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public List<Integer> getAdsetNumbers() {
        return adsetNumbers;
    }

    public void setAdsetNumber(List<Integer> adsetNumbers) {
        this.adsetNumbers = adsetNumbers;
    }

    @Override
    public String toString() {
        return "Adset{" +
                "sum=" + sum +
                ", adsetNumbers=" + adsetNumbers +
                '}';
    }
}
