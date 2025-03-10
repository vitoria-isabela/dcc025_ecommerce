package br.ufjf.dcc.dcc025.dcc025_ecommerce.gui;

import br.ufjf.dcc.dcc025.dcc025_ecommerce.GestorVendas;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.Cupom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class CupomPanelTest {

    private GestorVendas gestorVendasMock;
    private CupomPanel cupomPanel;

    @BeforeEach
    public void setUp() {
        gestorVendasMock = Mockito.mock(GestorVendas.class);
        cupomPanel = new CupomPanel();
    }

    @Test
    public void givenInvalidPercentual_whenSalvarCupom_thenErrorMessageIsShown() {
        // Arrange
        cupomPanel.getTxtPercentual().setText("abc"); 
        // Act
        cupomPanel.salvarCupom();

        // Assert
        verify(gestorVendasMock, never()).adicionarCupom(any(Cupom.class));
    }

    @Test
    public void givenEmptyCodigo_whenSalvarCupom_thenErrorMessageIsShown() {
        // Arrange
        cupomPanel.getTxtCodigo().setText(""); 

        // Act
        cupomPanel.salvarCupom();

        // Assert
        verify(gestorVendasMock, never()).adicionarCupom(any(Cupom.class));
    }
}