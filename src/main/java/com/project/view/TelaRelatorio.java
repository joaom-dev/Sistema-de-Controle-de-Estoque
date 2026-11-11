package com.project.view;

import com.project.controller.RelatorioController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaRelatorio extends JDialog {

    private final RelatorioController controller;
    private final JLabel lblTotal = new JLabel();
    private final JLabel lblResumo = new JLabel();

    private final DefaultTableModel modelo = new DefaultTableModel(RelatorioController.COLUNAS, 0) {
        @Override public boolean isCellEditable(int r, int c) {
            return false;
        }
    };

    public TelaRelatorio(Window pai, Estoque estoque) {
        super(pai, "Relatório de estoque", ModalityType.APPLICATION_MODAL);
        this.controller = new RelatorioController(estoque);

        lblTotal.setFont(lblTotal.getFont().deriveFont(Font.BOLD, 16f));
        JPanel topo = new JPanel(new GridLayout(0, 1, 4, 4));
        topo.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        topo.add(lblTotal);
        topo.add(lblResumo);
        topo.add(new JLabel("Produtos abaixo do estoque mínimo:"));

        JButton btAtualizar = new JButton("Atualizar");
        btAtualizar.addActionListener(e -> atualizar());
        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rodape.add(btAtualizar);

        add(topo, BorderLayout.NORTH);
        add(new JScrollPane(new JTable(modelo)), BorderLayout.CENTER);
        add(rodape, BorderLayout.SOUTH);

        atualizar();
        setSize(650, 420);
        setLocationRelativeTo(pai);
    }

    private void atualizar() {
        lblTotal.setText("Valor total do estoque: " + controller.getValorTotalFormatado());
        lblResumo.setText("Produtos cadastrados: " + controller.getTotalProdutos()
                + "  |  Abaixo do mínimo: " + controller.getTotalAbaixoDoMinimo());
        modelo.setRowCount(0);
        for (Object[] linha : controller.getLinhasTabela())
            modelo.addRow(linha);
    }
}

