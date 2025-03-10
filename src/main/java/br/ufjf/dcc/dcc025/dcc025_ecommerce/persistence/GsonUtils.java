package br.ufjf.dcc.dcc025.dcc025_ecommerce.persistence;

import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.LocalDate;

/**
 * Utility class to provide a configured Gson instance.
 * This class configures Gson with custom adapters for LocalDate, User, Cupom, Produto, Venda, and ItemVenda.
 * @Author: Vitória Isabela de Oliveira - 202065097C
 */
public class GsonUtils {

    private static Gson gson;

    /**
     * Retrieves a configured Gson instance.
     * If an instance does not exist, it creates a new one with registered type adapters.
     *
     * @return a configured Gson instance
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    public static Gson getGson() {
        if (gson == null) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
            gsonBuilder.registerTypeAdapter(User.class, new UserDeserializer());
            gsonBuilder.registerTypeAdapter(Cupom.class, new CupomDeserializer());
            gsonBuilder.registerTypeAdapter(Cupom.class, new CupomSerializer()); 
            gsonBuilder.registerTypeAdapter(Produto.class, new ProdutoDeserializer());
            gsonBuilder.registerTypeAdapter(Produto.class, new ProdutoSerializer());
            gsonBuilder.registerTypeAdapter(Venda.class, new VendaDeserializer());
            gsonBuilder.registerTypeAdapter(ItemVenda.class, new ItemVendaDeserializer());
            gson = gsonBuilder.create();
        }
        return gson;
    }
}