package br.ufjf.dcc.dcc025.dcc025_ecommerce.persistence;

import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.*;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

/**
 * Serializes a Produto object into a JSON element.
 * This serializer supports different types of products such as Eletronico, Roupa, and Alimento.
 * @Author: Vitória Isabela de Oliveira - 202065097C
 */
public class ProdutoSerializer implements JsonSerializer<Produto> {

    /**
     * Serializes a Produto object into a JSON element.
     *
     * @param src the Produto object to serialize
     * @param typeOfSrc the type of the source object
     * @param context the JSON serialization context
     * @return the JSON representation of the Produto object
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    @Override
    public JsonElement serialize(Produto src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();

        if (src instanceof Eletronico) {
            jsonObject.addProperty("type", "Eletrônico");
            Eletronico eletronico = (Eletronico) src;
            jsonObject.addProperty("codigo", eletronico.getCodigo());
            jsonObject.addProperty("nome", eletronico.getNome());
            jsonObject.addProperty("preco", eletronico.getPreco());
            jsonObject.addProperty("garantiaMeses", eletronico.getGarantiaMeses());
            jsonObject.addProperty("marca", eletronico.getMarca());
            jsonObject.addProperty("especificacoesTecnicas", eletronico.getEspecificacoesTecnicas());

        } else if (src instanceof Roupa) {
            jsonObject.addProperty("type", "Roupa");
            Roupa roupa = (Roupa) src;
            jsonObject.addProperty("codigo", roupa.getCodigo());
            jsonObject.addProperty("nome", roupa.getNome());
            jsonObject.addProperty("preco", roupa.getPreco());
            jsonObject.addProperty("tamanho", roupa.getTamanho());
            jsonObject.addProperty("cor", roupa.getCor());
            jsonObject.addProperty("material", roupa.getMaterial());
            jsonObject.addProperty("lote", roupa.getLote());

        } else if (src instanceof Alimento) {
            jsonObject.addProperty("type", "Alimento");
            Alimento alimento = (Alimento) src;
            jsonObject.addProperty("codigo", alimento.getCodigo());
            jsonObject.addProperty("nome", alimento.getNome());
            jsonObject.addProperty("preco", alimento.getPreco());
            jsonObject.add("dataValidade", context.serialize(alimento.getDataValidade()));
            jsonObject.add("dataFabricacao", context.serialize(alimento.getDataFabricacao()));
            jsonObject.addProperty("lote", alimento.getLote());
            jsonObject.addProperty("organico", alimento.isOrganico());
        } else {
            throw new IllegalArgumentException("Tipo de produto não suportado para serialização: " + src.getClass().getName());
        }
        return jsonObject;
    }
}