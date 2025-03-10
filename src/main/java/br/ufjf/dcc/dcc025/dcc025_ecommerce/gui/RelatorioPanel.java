package br.ufjf.dcc.dcc025.dcc025_ecommerce.gui;

import br.ufjf.dcc.dcc025.dcc025_ecommerce.GestorVendas;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.ItemVenda;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.Produto;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.Venda;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.User;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * JPanel representing the sales report section of the e-commerce system GUI.
 * Allows generating and viewing sales reports for a specific date, and exporting them to PDF.
 * @Author: Vitória Isabela de Oliveira
 */
public class RelatorioPanel extends JPanel {
    private GestorVendas gestorVendas = GestorVendas.getInstance();
    private final JDatePickerImpl dataPicker;
    private final JTable tabelaRelatorio;
    private final DefaultTableModel tableModel;
    private User loggedInUser;

    /**
     * Constructs a RelatorioPanel, initializing UI components and configuring the layout.
     * Includes a date selector, a table to display the report, and buttons to generate and export the report.
     * @param user The logged-in user
     */
    public RelatorioPanel(User user) {
        this.loggedInUser = user;
        setLayout(new BorderLayout());

        dataPicker = createDatePicker();

        JPanel painelData = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelData.add(new JLabel("Selecione a data:"));
        painelData.add(dataPicker);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Cliente");
        tableModel.addColumn("CPF");
        tableModel.addColumn("Itens Comprados");
        tableModel.addColumn("Total sem Desconto");
        tableModel.addColumn("Total com Desconto");
        tableModel.addColumn("Desconto");

        tabelaRelatorio = new JTable(tableModel);
        tabelaRelatorio.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tabelaRelatorio.setFillsViewportHeight(true);

        JButton btnGerarRelatorio = new JButton("Gerar Relatório");
        btnGerarRelatorio.addActionListener(e -> gerarRelatorioVendas());

        JButton btnExportarPDF = new JButton("Exportar para PDF");
        btnExportarPDF.setEnabled(false);

        btnGerarRelatorio.addActionListener(e -> {
            gerarRelatorioVendas();
            btnExportarPDF.setEnabled(true);
        });

        btnExportarPDF.addActionListener(e -> exportarParaPDF());

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.add(btnGerarRelatorio);
        painelBotoes.add(btnExportarPDF);

        add(painelData, BorderLayout.NORTH);
        add(new JScrollPane(tabelaRelatorio), BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);
    }

    /**
     * Creates and configures a JDatePickerImpl for date selection.
     * @return the configured JDatePickerImpl
     */
    private JDatePickerImpl createDatePicker() {
        UtilDateModel model = new UtilDateModel();
        model.setSelected(true);

        Properties properties = new Properties();
        properties.put("text.today", "Hoje");
        properties.put("text.month", "Mês");
        properties.put("text.year", "Ano");

        JDatePanelImpl datePanel = new JDatePanelImpl(model, properties);
        return new JDatePickerImpl(datePanel, new DateLabelFormatter());
    }

    /**
     * Generates the sales report for the selected date.
     */
    public void gerarRelatorioVendas() {
        tableModel.setRowCount(0);

        String dataSelecionada = dataPicker.getJFormattedTextField().getText();
        DateTimeFormatter formatterEntrada = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter formatterSaida = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate data = LocalDate.parse(dataSelecionada, formatterEntrada);
        String dataFormatada = data.format(formatterSaida);

        List<Venda> vendas;

        if (loggedInUser.getRole().equals("client")) {
            vendas = gestorVendas.listarVendasPorData(dataFormatada).stream()
                    .filter(venda -> venda.getCliente() != null && venda.getCliente().getEmail().equals(loggedInUser.getEmail()))
                    .collect(Collectors.toList());
        } else {
            vendas = gestorVendas.listarVendasPorData(dataFormatada);
        }

        for (Venda venda : vendas) {
            var cliente = venda.getCliente();
            String nomeCliente = cliente != null ? cliente.getNome() : "N/A";
            String cpfCliente = cliente != null ? cliente.getCpf() : "N/A";

            StringBuilder itensComprados = new StringBuilder();
            for (ItemVenda item : venda.getItens()) {
                Produto produto = item.getProduto();
                itensComprados.append(produto.getNome())
                        .append(" x").append(item.getQuantidade())
                        .append(" (R$").append(produto.getPreco()).append(")\n");
            }

            double totalSemDesconto = venda.getTotalSemDesconto();
            double totalComDesconto = venda.getTotal();
            double desconto = totalSemDesconto - totalComDesconto;

            tableModel.addRow(new Object[]{
                    nomeCliente,
                    cpfCliente,
                    itensComprados.toString(),
                    String.format("R$%.2f", totalSemDesconto),
                    String.format("R$%.2f", totalComDesconto),
                    String.format("R$%.2f", desconto)
            });
        }
    }

    /**
     * Exports the generated sales report to a PDF file.
     * Allows the user to choose the save location and file name.
     * Uses the iText library to create the PDF document with the formatted report data.
     */
    private void exportarParaPDF() {
        String dataSelecionada = dataPicker.getJFormattedTextField().getText();
        DateTimeFormatter formatterEntrada = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter formatterSaida = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate data = LocalDate.parse(dataSelecionada, formatterEntrada);
        String dataFormatada = data.format(formatterSaida);

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salvar Relatório em PDF");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setSelectedFile(new File("relatorio_vendas_" + dataSelecionada + ".pdf"));

        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try {
                Document document = new Document(PageSize.A4, 50, 50, 50, 50);
                PdfWriter.getInstance(document, new FileOutputStream(fileToSave));
                document.open();

                var headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.DARK_GRAY);
                Paragraph empresa = new Paragraph("Empresa Vitória Isabela de Oliveira", headerFont);
                empresa.setAlignment(Element.ALIGN_CENTER);
                document.add(empresa);

                var subHeaderFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.GRAY);
                Paragraph cnpj = new Paragraph("CNPJ: 00.000.000/0000-00", subHeaderFont);
                cnpj.setAlignment(Element.ALIGN_CENTER);
                document.add(cnpj);

                LineSeparator line = new LineSeparator();
                document.add(new Chunk(line));

                var titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK);
                Paragraph title = new Paragraph("Relatório de Vendas do Dia: " + dataSelecionada, titleFont);
                title.setAlignment(Element.ALIGN_CENTER);
                title.setSpacingAfter(20f);
                document.add(title);

                PdfPTable table = new PdfPTable(6);
                table.setWidthPercentage(100);
                table.setSpacingBefore(10f);
                table.setSpacingAfter(10f);

                var tableHeaderFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);
                PdfPCell headerCell;

                headerCell = new PdfPCell(new Phrase("Cliente", tableHeaderFont));
                headerCell.setBackgroundColor(BaseColor.DARK_GRAY);
                headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(headerCell);

                headerCell = new PdfPCell(new Phrase("CPF", tableHeaderFont));
                headerCell.setBackgroundColor(BaseColor.DARK_GRAY);
                headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(headerCell);

                headerCell = new PdfPCell(new Phrase("Itens Comprados", tableHeaderFont));
                headerCell.setBackgroundColor(BaseColor.DARK_GRAY);
                headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(headerCell);

                headerCell = new PdfPCell(new Phrase("Total sem Desconto", tableHeaderFont));
                headerCell.setBackgroundColor(BaseColor.DARK_GRAY);
                headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(headerCell);

                headerCell = new PdfPCell(new Phrase("Total com Desconto", tableHeaderFont));
                headerCell.setBackgroundColor(BaseColor.DARK_GRAY);
                headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(headerCell);

                headerCell = new PdfPCell(new Phrase("Desconto", tableHeaderFont));
                headerCell.setBackgroundColor(BaseColor.DARK_GRAY);
                headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(headerCell);

                List<Venda> vendas;

                if (loggedInUser.getRole().equals("client")) {
                    vendas = gestorVendas.listarVendasPorData(dataFormatada).stream()
                            .filter(venda -> venda.getCliente() != null && venda.getCliente().getEmail().equals(loggedInUser.getEmail()))
                            .collect(Collectors.toList());
                } else {
                    vendas = gestorVendas.listarVendasPorData(dataFormatada);
                }

                for (Venda venda : vendas) {
                    var cliente = venda.getCliente();
                    table.addCell(cliente != null ? cliente.getNome() : "N/A");
                    table.addCell(cliente != null ? cliente.getCpf() : "N/A");

                    StringBuilder itensComprados = new StringBuilder();
                    for (ItemVenda item : venda.getItens()) {
                        Produto produto = item.getProduto();
                        itensComprados.append(produto.getNome())
                                .append(" x").append(item.getQuantidade())
                                .append(" (R$").append(produto.getPreco()).append(")\n");
                    }
                    table.addCell(itensComprados.toString());

                    double totalSemDesconto = venda.getTotalSemDesconto();
                    double totalComDesconto = venda.getTotal();
                    double desconto = totalSemDesconto - totalComDesconto;

                    table.addCell(String.format("R$%.2f", totalSemDesconto));
                    table.addCell(String.format("R$%.2f", totalComDesconto));
                    table.addCell(String.format("R$%.2f", desconto));
                }

                document.add(table);

                double totalGeral = vendas.stream().mapToDouble(Venda::getTotal).sum();
                var totalFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.BLACK);
                Paragraph totalGeralParagraph = new Paragraph("Total Geral de Vendas: R$" + String.format("%.2f", totalGeral), totalFont);
                totalGeralParagraph.setAlignment(Element.ALIGN_RIGHT);
                totalGeralParagraph.setSpacingBefore(20f);
                document.add(totalGeralParagraph);

                document.close();
                JOptionPane.showMessageDialog(this, "Relatório exportado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (DocumentException | IOException e) {
                JOptionPane.showMessageDialog(this, "Erro ao exportar relatório: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Custom formatter to handle the display and parsing of dates in the JDatePicker.
     */
    public class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
        private final String datePattern = "dd-MM-yyyy";
        private final SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        /**
         * Parses the input text into an object representing the date.
         * @param text a string to parse
         * @return the parsed date object
         * @throws ParseException if parsing fails
         */
        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        /**
         * Formats the provided value (a date) into a string representation.
         * @param value the date object to format
         * @return the formatted date string
         * @throws ParseException if formatting fails
         */
        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }
            return "";
        }
    }

    /**
     * Setters for testing purposes
     */
    public void setGestorVendas(GestorVendas gestorVendas) {
        this.gestorVendas = gestorVendas;
    }

    public JDatePickerImpl getDataPicker() {
        return dataPicker;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JTable getTabelaRelatorio() {
        return tabelaRelatorio;
    }
}