package br.ufjf.dcc.dcc025.dcc025_ecommerce.persistence;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Gson adapter for serializing and deserializing LocalDate objects.
 * This adapter uses the ISO_LOCAL_DATE format (yyyy-MM-dd).
 * @Author: Vitória Isabela de Oliveira - 202065097C
 */
public class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    /**
     * Serializes a LocalDate object to JSON using the ISO_LOCAL_DATE format.
     *
     * @param localDate the LocalDate object to serialize
     * @param srcType the type of the source object
     * @param context the JSON serialization context
     * @return the JSON representation of the LocalDate object
     */
    @Override
    public JsonElement serialize(LocalDate localDate, Type srcType, JsonSerializationContext context) {
        return new JsonPrimitive(formatter.format(localDate));
    }

    /**
     * Deserializes a JSON element to a LocalDate object using the ISO_LOCAL_DATE format.
     *
     * @param json the JSON element to deserialize
     * @param typeOfT the type of the target object
     * @param context the JSON deserialization context
     * @return the LocalDate object deserialized from the JSON element
     * @throws JsonParseException if the JSON element cannot be parsed as a LocalDate
     */
    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return LocalDate.parse(json.getAsString(), formatter);
    }
}