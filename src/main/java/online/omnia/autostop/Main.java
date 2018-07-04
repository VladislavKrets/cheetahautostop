package online.omnia.autostop;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by lollipop on 26.09.2017.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        List<Buyer> buyers = Utils.configReader();
        CheetahTokenEntity cheetahTokenEntity;
        AccountEntity accountEntity;
        List<Adset> adsets;
        List<Integer> ids;
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(List.class, new JsonAdsetDeserializer());
        builder.registerTypeAdapter(Date.class, new JsonEditTimeDeserializer());
        Gson gson = builder.create();
        for (Buyer buyer : buyers) {

            accountEntity = MySQLDaoImpl.getInstance().getAccountEntity(buyer.getBuyerName());
            cheetahTokenEntity = MySQLDaoImpl.getInstance().getCheetahTokenEntity(accountEntity.getAccountId());
            System.out.println(cheetahTokenEntity.getAccessToken());
            adsets = buyer.getAdsets();
            for (Adset adset : adsets) {
                ids = adset.getAdsetNumbers();
                for (Integer id : ids) {
                    String answerDate = HttpMethodUtils.getMethod("adset/" + id, cheetahTokenEntity.getAccessToken());
                    Date date = gson.fromJson(answerDate, Date.class);
                    if (date != null) {
                        String answer = HttpMethodUtils.sendJsonRequest(cheetahTokenEntity.getAccessToken(), id, date);
                        List<JsonAdset> jsonAdsets = gson.fromJson(answer, List.class);

                    }
                }
            }
        }

    }
}
