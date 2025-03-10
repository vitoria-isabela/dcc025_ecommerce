package br.ufjf.dcc.dcc025.dcc025_ecommerce.persistence;

import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.*;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.repository.ClienteRepository;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Deserializes a JSON element into a User object, supporting different user roles (manager, seller, client).
 * This deserializer also handles the creation or retrieval of associated Cliente objects for ClientUser instances.
 * @Author: Vitória Isabela de Oliveira - 202065097C
 */
public class UserDeserializer implements JsonDeserializer<User> {

    private static final AtomicInteger cpfCounter = new AtomicInteger(1);

    /**
     * Deserializes a JSON element into a specific User object based on its role.
     * For ClientUser instances, it checks if a corresponding Cliente exists and either retrieves it or creates a new one.
     *
     * @param json the Json element to deserialize
     * @param typeOfT the type of the Object to deserialize to
     * @param context the Json deserialization context
     * @return the deserialized User object
     * @throws JsonParseException if the JSON structure is invalid or missing required fields
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    @Override
    public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonElement roleElement = jsonObject.get("role");

        String role = roleElement.getAsString();
        switch (role) {
            case "manager":
                return context.deserialize(json, Manager.class);

            case "seller":
                return context.deserialize(json, Seller.class);

            case "client":
                ClientUser clientUser = context.deserialize(json, ClientUser.class);

                ClienteRepository clienteRepository = ClienteRepository.getInstance();
                Cliente existingCliente = clienteRepository.buscarPorEmail(clientUser.getEmail());
                if (existingCliente == null) {
                    String uniqueCpf = "CPF_PENDENTE_" + cpfCounter.getAndIncrement();

                    Cliente newCliente = new Cliente(
                            uniqueCpf,
                            clientUser.getName(),
                            clientUser.getEmail(),
                            "Telefone_Pendente",
                            new Endereco("Rua Pendente", "Numero Pendente", "Complemento Pendente", "Bairro Pendente", "Cidade Pendente", "Estado Pendente")
                    );
                    clienteRepository.salvar(newCliente);
                    clientUser.setCliente(newCliente);
                } else {
                    clientUser.setCliente(existingCliente);
                }

                return clientUser;

            default:
                throw new JsonParseException("Tipo de usuário desconhecido: " + role);
        }
    }
}