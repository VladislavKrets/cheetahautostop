package online.omnia.autostop;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lollipop on 02.10.2017.
 */
public class JsonEditTimeDeserializer implements JsonDeserializer<Date>{
    @Override
    public Date deserialize(JsonElement jsonElement, Type type,
                              JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();
        JsonElement element = object.get("data").getAsJsonObject();
        if (element != null) {
            String date = element.getAsJsonObject().get("record_time").getAsString();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            try {
                return simpleDateFormat.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
