package br.ufjf.dcc.dcc025.dcc025_ecommerce.persistence;

import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.Cupom;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.CupomQuantidadeLimitada;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.CupomValorMinimo;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

/**
 * Serializes a Cupom object into a JSON element.
 * This serializer supports different types of coupons such as CupomQuantidadeLimitada and CupomValorMinimo.
 * @Author: Vitória Isabela de Oliveira - 202065097C
 */
public class CupomSerializer implements JsonSerializer<Cupom> {

    /**
     * Serializes a Cupom object into a JSON element.
     *
     * @param src the Cupom object to serialize
     * @param typeOfSrc the type of the Cupom object being serialized
     * @param context the JSON serialization context
     * @return the JSON representation of the Cupom object
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    @Override
    public JsonElement serialize(Cupom src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();

        if (src instanceof CupomQuantidadeLimitada) {
            jsonObject.addProperty("type", "CupomQuantidadeLimitada");
        } else if (src instanceof CupomValorMinimo) {
            jsonObject.addProperty("type", "CupomValorMinimo");
        }

        jsonObject.addProperty("codigo", src.getCodigo());
        jsonObject.addProperty("percentualDesconto", src.getPercentualDesconto());
        jsonObject.addProperty("ativo", src.isAtivo());

        if (src instanceof CupomQuantidadeLimitada) {
            CupomQuantidadeLimitada cupomQL = (CupomQuantidadeLimitada) src;
            jsonObject.addProperty("maximoUtilizacoes", cupomQL.getMaximoUtilizacoes());
        } else if (src instanceof CupomValorMinimo) {
            CupomValorMinimo cupomVM = (CupomValorMinimo) src;
            jsonObject.addProperty("valorMinimo", cupomVM.getValorMinimo());
        }

        return jsonObject;
    }
}