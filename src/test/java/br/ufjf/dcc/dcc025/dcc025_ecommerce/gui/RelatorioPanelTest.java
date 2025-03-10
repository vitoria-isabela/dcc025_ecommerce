package br.ufjf.dcc.dcc025.dcc025_ecommerce.gui;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.GestorVendas;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.Manager;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.User;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

class RelatorioPanelTest {

    private GestorVendas mockGestorVendas;

    @BeforeEach
    void setUp() {
        mockGestorVendas = Mockito.mock(GestorVendas.class);
        User dummyUser = new Manager("testManager", "password", "Test Manager", "test@example.com"); 
        RelatorioPanel relatorioPanel = new RelatorioPanel(dummyUser);
        relatorioPanel.setGestorVendas(mockGestorVendas);
    }

    
}