package online.omnia.autostop;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by lollipop on 27.09.2017.
 */
public class JsonAdsetDeserializer implements JsonDeserializer<List<JsonAdset>>{
    @Override
    public List<JsonAdset> deserialize(JsonElement jsonElement,
                                 Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();
        String status = object.get("status").getAsString();
        String message = object.get("message").getAsString();

        System.out.println(status + " " + message);
        List<String> titles = new ArrayList<>();

        JsonElement data = object.get("data");
        if (data.isJsonArray()) {
            System.out.println("Empty array");
            System.out.println("Returned: " + jsonElement);
            return null;
        }

        JsonArray titleArray = data.getAsJsonObject().get("title").getAsJsonArray();
        for (JsonElement element : titleArray) {
            titles.add(element.getAsString());
        }
        List<JsonAdset> entities = new ArrayList<>();
        JsonArray array = data.getAsJsonObject().get("data").getAsJsonArray();
        JsonAdset adsetEntity;
        JsonArray arrayElement;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
        for (JsonElement element : array) {
            arrayElement = element.getAsJsonArray();
            adsetEntity = new JsonAdset();

            if (titles.contains("revenue")) {
                adsetEntity.setRevenue(arrayElement.get(titles.indexOf("revenue")).getAsString());
            }
            if (titles.contains("adset")) {
                adsetEntity.setAdset(arrayElement.get(titles.indexOf("adset")).getAsString());

            }
            entities.add(adsetEntity);
        }
        return entities;
    }
}
