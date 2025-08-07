package ap.project.model;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.util.*;

public class MapPointAdapter<V> implements JsonSerializer<Map<Point, V>>, JsonDeserializer<Map<Point, V>> {

    private final Class<V> valueClass;

    public MapPointAdapter(Class<V> valueClass) {
        this.valueClass = valueClass;
    }

    @Override
    public JsonElement serialize(Map<Point, V> map, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        for (Map.Entry<Point, V> entry : map.entrySet()) {
            String key = entry.getKey().x + "," + entry.getKey().y;
            jsonObject.add(key, context.serialize(entry.getValue()));
        }
        return jsonObject;
    }

    @Override
    public Map<Point, V> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Map<Point, V> map = new HashMap<>();
        JsonObject jsonObject = json.getAsJsonObject();
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            String[] coords = entry.getKey().split(",");
            Point point = new Point(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
            V value = context.deserialize(entry.getValue(), valueClass);
            map.put(point, value);
        }
        return map;
    }
}
