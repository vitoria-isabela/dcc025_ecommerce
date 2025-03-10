package br.ufjf.dcc.dcc025.dcc025_ecommerce.persistence;

import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Utility class for data persistence operations such as loading from and saving to files.
 * It uses Gson for serialization and deserialization of data objects.
 * @Author: Vitória Isabela de Oliveira - 202065097C
 */
public class Persistencia {
    private static final Logger LOGGER = Logger.getLogger(Persistencia.class.getName());
    private static Gson gson = GsonUtils.getGson();

    /**
     * Loads a list of objects of a specified type from a JSON file.
     *
     * @param arquivo the name of the file to load from
     * @param tipo the Type object representing the class of the objects to load
     * @return a list of objects of the specified type, or null if an error occurred
     * @param <T> the type of the objects to load
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    public static <T> List<T> carregarDeArquivo(String arquivo, Type tipo) {
        try (FileReader reader = new FileReader(arquivo)) {
            return gson.fromJson(reader, tipo);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erro ao carregar dados do arquivo: " + arquivo, e);
            return null;
        }
    }

    /**
     * Saves data to a JSON file.
     * If the data is a list of Cupom objects, it ensures that the "type" property is included in the JSON.
     *
     * @param arquivo the name of the file to save to
     * @param dados the data to save
     * @param <T> the type of the data to save
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    public static <T> void salvarEmArquivo(String arquivo, T dados) {
        if ("cupons.json".equals(arquivo) && dados instanceof List) {
            List<?> lista = (List<?>) dados;

            List<JsonObject> listaComTipos = lista.stream().map(item -> {
                if (item instanceof Cupom) {
                    return adicionarTipoCupom((Cupom) item);
                }
                return null;
            }).collect(Collectors.toList());

            try (FileWriter writer = new FileWriter(arquivo, false)) {
                gson.toJson(listaComTipos, writer);
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Erro ao salvar dados no arquivo: " + arquivo, e);
            }
        } else {
            try (FileWriter writer = new FileWriter(arquivo, false)) {
                gson.toJson(dados, writer);
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Erro ao salvar dados no arquivo: " + arquivo, e);
            }
        }
    }

    /**
     * Adds the "type" property to a Cupom object for correct deserialization.
     *
     * @param cupom the Cupom object to add the "type" property to
     * @return the JsonObject with the added "type" property
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    private static JsonObject adicionarTipoCupom(Cupom cupom) {
        JsonObject jsonObject = new JsonObject();

        if (cupom instanceof CupomQuantidadeLimitada) {
            jsonObject.addProperty("type", "CupomQuantidadeLimitada");
        } else if (cupom instanceof CupomValorMinimo) {
            jsonObject.addProperty("type", "CupomValorMinimo");
        }

        jsonObject.addProperty("codigo", cupom.getCodigo());
        jsonObject.addProperty("percentualDesconto", cupom.getPercentualDesconto());
        jsonObject.addProperty("ativo", cupom.isAtivo());

        if (cupom instanceof CupomQuantidadeLimitada) {
            CupomQuantidadeLimitada cupomQL = (CupomQuantidadeLimitada) cupom;
            jsonObject.addProperty("maximoUtilizacoes", cupomQL.getMaximoUtilizacoes());
        } else if (cupom instanceof CupomValorMinimo) {
            CupomValorMinimo cupomVM = (CupomValorMinimo) cupom;
            jsonObject.addProperty("valorMinimo", cupomVM.getValorMinimo());
        }

        return jsonObject;
    }
}