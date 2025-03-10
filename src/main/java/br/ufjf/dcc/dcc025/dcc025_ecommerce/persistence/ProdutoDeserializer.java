package br.ufjf.dcc.dcc025.dcc025_ecommerce.persistence;

import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.*;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
/**
 * Deserializes a JSON element into a Produto object.
 * This deserializer supports different types of products such as Eletronico, Roupa, and Alimento.
 * @Author: Vitória Isabela de Oliveira - 202065097C
 */
public class ProdutoDeserializer implements JsonDeserializer<Produto> {
    /**
     * Deserializes a JSON element into a specific Produto object based on its type.
     *
     * @param json the Json element to deserialize
     * @param typeOfT the type of the Object to deserialize to
     * @param context the Json deserialization context
     * @return the deserialized Produto object
     * @throws JsonParseException if the JSON structure is invalid or missing required fields
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    @Override
    public Produto deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonElement typeElement = jsonObject.get("type");

        if (typeElement == null) {
            throw new JsonParseException("O campo 'type' é obrigatório para deserializar Produto.");
        }

        String type = typeElement.getAsString();

        switch (type) {
            case "Eletrônico":
                return context.deserialize(jsonObject, Eletronico.class);
            case "Roupa":
                return context.deserialize(jsonObject, Roupa.class);
            case "Alimento":
                return context.deserialize(jsonObject, Alimento.class);
            default:
                throw new JsonParseException("Tipo de produto desconhecido: " + type);
        }
    }
}