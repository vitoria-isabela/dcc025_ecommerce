package br.ufjf.dcc.dcc025.dcc025_ecommerce.persistence;

import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.ItemVenda;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.Produto;
import com.google.gson.*;
import java.lang.reflect.Type;

/**
 * Deserializes a JSON element into an ItemVenda object.
 * This deserializer ensures that the 'produto' field is present and correctly deserialized.
 * @Author: Vitória Isabela de Oliveira - 202065097C
 */
public class ItemVendaDeserializer implements JsonDeserializer<ItemVenda> {

    /**
     * Deserializes a JSON element into an ItemVenda object.
     *
     * @param json the Json element to deserialize
     * @param typeOfT the type of the Object to deserialize to
     * @param context the Json deserialization context
     * @return the deserialized ItemVenda object
     * @throws JsonParseException if the JSON structure is invalid or missing required fields
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    @Override
    public ItemVenda deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonElement produtoElement = jsonObject.get("produto");

        if (produtoElement == null) {
            throw new JsonParseException("O campo 'produto' é obrigatório para deserializar ItemVenda.");
        }

        Produto produto = context.deserialize(produtoElement, Produto.class);
        int quantidade = jsonObject.get("quantidade").getAsInt();

        return new ItemVenda(produto, quantidade);
    }
}