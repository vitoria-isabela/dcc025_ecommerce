package br.ufjf.dcc.dcc025.dcc025_ecommerce.persistence;

import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.Cupom;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.CupomQuantidadeLimitada;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.CupomValorMinimo;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Deserializes a JSON element into a Cupom object.
 * This deserializer supports different types of coupons such as CupomQuantidadeLimitada and CupomValorMinimo.
 * @Author: Vitória Isabela de Oliveira - 202065097C
 */
public class CupomDeserializer implements JsonDeserializer<Cupom> {

    /**
     * Deserializes a JSON element into a specific Cupom object based on its type.
     *
     * @param json the Json element to deserialize
     * @param typeOfT the type of the Object to deserialize to
     * @param context the Json deserialization context
     * @return the deserialized Cupom object
     * @throws JsonParseException if the JSON structure is invalid or missing required fields
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    @Override
    public Cupom deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        JsonElement typeElement = jsonObject.get("type");
        if (typeElement == null || !typeElement.isJsonPrimitive()) {
            throw new JsonParseException("Campo 'type' ausente ou inválido no JSON do Cupom.");
        }
        String type = typeElement.getAsString();

        JsonElement codigoElement = jsonObject.get("codigo");
        if (codigoElement == null || !codigoElement.isJsonPrimitive()) {
            throw new JsonParseException("Campo 'codigo' ausente ou inválido no JSON do Cupom.");
        }
        String codigo = codigoElement.getAsString();

        JsonElement percentualDescontoElement = jsonObject.get("percentualDesconto");
        if (percentualDescontoElement == null || !percentualDescontoElement.isJsonPrimitive()) {
            throw new JsonParseException("Campo 'percentualDesconto' ausente ou inválido no JSON do Cupom.");
        }
        double percentualDesconto = percentualDescontoElement.getAsDouble();

        switch (type) {
            case "CupomQuantidadeLimitada":
                JsonElement maximoUtilizacoesElement = jsonObject.get("maximoUtilizacoes");
                if (maximoUtilizacoesElement == null || !maximoUtilizacoesElement.isJsonPrimitive()) {
                    throw new JsonParseException("Campo 'maximoUtilizacoes' ausente ou inválido para CupomQuantidadeLimitada no JSON.");
                }
                int maximoUtilizacoes = maximoUtilizacoesElement.getAsInt();
                CupomQuantidadeLimitada cupomQuantidadeLimitada = new CupomQuantidadeLimitada(codigo, percentualDesconto, maximoUtilizacoes);
                if (jsonObject.has("ativo")) {
                    cupomQuantidadeLimitada.setAtivo(jsonObject.get("ativo").getAsBoolean());
                }
                return cupomQuantidadeLimitada;

            case "CupomValorMinimo":
                JsonElement valorMinimoElement = jsonObject.get("valorMinimo");
                if (valorMinimoElement == null || !valorMinimoElement.isJsonPrimitive()) {
                    throw new JsonParseException("Campo 'valorMinimo' ausente ou inválido para CupomValorMinimo no JSON.");
                }
                double valorMinimo = valorMinimoElement.getAsDouble();
                CupomValorMinimo cupomValorMinimo = new CupomValorMinimo(codigo, percentualDesconto, valorMinimo);
                if (jsonObject.has("ativo")) {
                    cupomValorMinimo.setAtivo(jsonObject.get("ativo").getAsBoolean());
                }
                return cupomValorMinimo;

            default:
                throw new JsonParseException("Tipo de cupom desconhecido ou inválido: " + type);
        }
    }
}