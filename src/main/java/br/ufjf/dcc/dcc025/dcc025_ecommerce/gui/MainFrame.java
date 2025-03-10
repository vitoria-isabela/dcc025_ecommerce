package br.ufjf.dcc.dcc025.dcc025_ecommerce.gui;

import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.User;

import javax.swing.*;
import java.awt.*;

/**
 * Main JFrame representing the main window of the e-commerce application.
 * Houses other panels for different functionalities in a tabbed interface.
 * 
 * @Author: Vitoria Isabela de Oliveira - 202065097C
 */
public class MainFrame extends JFrame {
    private User loggedInUser;

    /**
     * Constructs the MainFrame, initializing the UI components and configuring the
     * tabs.
     * 
     * @param user The logged-in user.
     */
    public MainFrame(User user) {
        this.loggedInUser = user;
        initComponents();
    }

    /**
     * Initializes and organizes the components within the MainFrame.
     */
    private void initComponents() {
        setTitle("Sistema de E-commerce - Vitória Isabela de Oliveira - Logado como: " + loggedInUser.getName() + " ("
                + loggedInUser.getRole() + ")");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> logout());
        topPanel.add(logoutButton);

        JTabbedPane abas = new JTabbedPane();

        if (loggedInUser.getRole().equals("manager")) {
            abas.addTab("Produtos", new ProdutoPanel(loggedInUser));
            abas.addTab("Vendas", new VendaPanel());
            abas.addTab("Cupons", new CupomPanel());
            abas.addTab("Clientes", new ClientePanel());
            abas.addTab("Vendedores", new SellerPanel());

        } else if (loggedInUser.getRole().equals("seller")) {
            abas.addTab("Vendas", new VendaPanel());
            abas.addTab("Clientes", new ClientePanel());
            abas.addTab("Produtos", new ProdutoPanel(loggedInUser));

        } else if (loggedInUser.getRole().equals("client")) {
            abas.addTab("Carrinho de Compras", new ClienteVendaPanel(loggedInUser));
        }
        abas.addTab("Relatórios", new RelatorioPanel(loggedInUser));

        add(topPanel, BorderLayout.NORTH);
        add(abas, BorderLayout.CENTER);
        setLocationRelativeTo(null);
    }

    /**
     * Logs the user out, closing the main window and returning to the login screen.
     */
    private void logout() {
        this.dispose();
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.setVisible(true);
    }
}