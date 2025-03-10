package br.ufjf.dcc.dcc025.dcc025_ecommerce.persistence;

import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.ItemVenda;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.Venda;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;

/**
 * Deserializes a JSON element into a Venda object.
 * This deserializer handles the deserialization of the Venda's ID, data, items, and payment method.
 * @Author: Vitória Isabela de Oliveira - 202065097C
 */
public class VendaDeserializer implements JsonDeserializer<Venda> {

    /**
     * Deserializes a JSON element into a Venda object.
     *
     * @param json the Json element to deserialize
     * @param typeOfT the type of the Object to deserialize to
     * @param context the Json deserialization context
     * @return the deserialized Venda object
     * @throws JsonParseException if the JSON structure is invalid or missing required fields
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    @Override
    public Venda deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        Venda venda = new Venda();
        try {
            String id = jsonObject.get("id").getAsString();
            java.lang.reflect.Field idField = Venda.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(venda, id);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new JsonParseException("Could not set id on Venda object: " + e.getMessage());
        }

        LocalDate data = context.deserialize(jsonObject.get("data"), LocalDate.class);
        venda.setData(data);

        JsonArray itensArray = jsonObject.getAsJsonArray("itens");
        for (JsonElement itemElement : itensArray) {
            ItemVenda item = context.deserialize(itemElement, ItemVenda.class);
            venda.adicionarItem(item.getProduto(), item.getQuantidade());
        }

        String formaPagamento = jsonObject.get("formaPagamento").getAsString();
        venda.setFormaPagamento(formaPagamento);

        return venda;
    }
}