package br.ufjf.dcc.dcc025.dcc025_ecommerce;

import br.ufjf.dcc.dcc025.dcc025_ecommerce.gui.LoginFrame;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.repository.*;
import javax.swing.SwingUtilities;

/**
 * Main class to launch the e-commerce application.
 * This class initializes and displays the login frame after loading data from
 * JSON files.
 * 
 * @Author: Vitória Isabela de Oliveira - 202065097C
 */
public class Main {

    /**
     * Main method to start the e-commerce application.
     * Loads data from JSON files in a specific order (Clients first, then Users,
     * etc.) and initializes the LoginFrame.
     * 
     * @param args the command line arguments
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ClienteRepository.getInstance().carregarDeArquivo("clientes.json");
            UserRepository.getInstance().carregarDeArquivo("users.json");
            ProdutoRepository.getInstance().carregarDeArquivo("produtos.json");
            CupomRepository.getInstance().carregarDeArquivo("cupons.json");
            CupomRepository.getInstance().salvarEmArquivo("cupons.json");
            VendaRepository.getInstance().carregarDeArquivo("vendas.json");

            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }
}