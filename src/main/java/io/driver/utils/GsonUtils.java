package io.driver.utils;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class GsonUtils {
    private static final Gson gson = new GsonBuilder().setLenient().
            registerTypeAdapter(LocalDate.class, new LocalDateAdaptor()).
            registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdaptor()).
            serializeSpecialFloatingPointValues().
            //setPrettyPrinting().
            create();

    public GsonUtils() {

    }

    public static Gson getGson() {
        return gson;
    }

    public static Map<Object, Object> parseToMapObject(String jsonString) {
        return gson.fromJson(jsonString, new TypeToken<Map<Object, Object>>() {
        }.getType());
    }

    public static Object parseToObject(String jsonString, Type typeToken) {
        return gson.fromJson(jsonString, typeToken);
    }

    public static Map<String, Object> parseToStringObject(String jsonString) {
        Map<String, Object> map = new HashMap<>();
        parseToMapObject(jsonString).forEach((key, value) -> map.put((String) key, value));
        return map;
    }

    public static <T> T parseToClass(String jsonString, Class<T> clazz) {
        return gson.fromJson(jsonString, clazz);
    }

    public static <T> T parseToClass(String jsonString, Type type) {
        return gson.fromJson(jsonString, type);
    }

    public static Map<String, Double> parseToStringDouble(String jsonString) {
        Map<String, Double> map = new HashMap<>();
        parseToMapObject(jsonString).forEach((key, value) -> map.put((String) key, (Double) value));
        return map;
    }

    public static Map<String, String> parseToStringString(String jsonString) {
        Map<String, String> map = new HashMap<>();
        parseToMapObject(jsonString).forEach((key, value) -> map.put((String) key, (String) value));
        return map;
    }

    public static Map<String, LocalDate> parseToStringLocalDate(String jsonString) {
        Map<String, LocalDate> map = new HashMap<>();
        parseToMapObject(jsonString).forEach((key, value) -> map.put((String) key, LocalDate.parse((String) value)));
        return map;
    }

    public static Map<String, LocalDateTime> parseToStringLocalDateTime(String jsonString) {
        Map<String, LocalDateTime> map = new HashMap<>();
        parseToMapObject(jsonString).forEach((key, value) -> map.put((String) key, LocalDateTime.parse((String) value)));
        return map;
    }

    public static String toJsonString(Object clazz) {
        return gson.toJson(clazz);
    }

    public static String toPrettyJsonString(String rawJsonString) {
        JsonElement je = JsonParser.parseString(rawJsonString);
        return gson.toJson(je);
    }

    private final static class LocalDateAdaptor implements JsonDeserializer<LocalDate>, JsonSerializer<LocalDate> {
        @Override
        public LocalDate deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
            return LocalDate.parse(jsonElement.getAsJsonPrimitive().getAsString());
        }

        @Override
        public JsonElement serialize(LocalDate localDate, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(localDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
        }
    }

    private final static class LocalDateTimeAdaptor implements JsonDeserializer<LocalDateTime>, JsonSerializer<LocalDateTime> {
        @Override
        public LocalDateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
            return LocalDateTime.parse(jsonElement.getAsJsonPrimitive().getAsString());
        }

        @Override
        public JsonElement serialize(LocalDateTime localDateTime, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        }
    }

}
