package com.project.view;

import com.project.controller.MovimentacaoController;
import com.project.controller.ProdutoController;
import com.project.controller.RelatorioController;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TelaPrincipal extends JFrame {

    private final ProdutoController produtoController;
    private final MovimentacaoController movimentacaoController;
    private final RelatorioController relatorioController;

    public TelaPrincipal(ProdutoController produtoController,
                         MovimentacaoController movimentacaoController,
                         RelatorioController relatorioController) {

        this.produtoController = produtoController;
        this.movimentacaoController = movimentacaoController;
        this.relatorioController = relatorioController;

        setTitle("Controle de Estoque");
        setSize(420, 380);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Título
        JLabel titulo = new JLabel("CONTROLE DE ESTOQUE", JLabel.CENTER);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 20f));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        add(titulo, BorderLayout.NORTH);

        // Menu de botões
        JPanel menu = new JPanel(new GridLayout(5, 1, 10, 10));
        menu.setBorder(BorderFactory.createEmptyBorder(10, 40, 25, 40));

        JButton cadastro = new JButton("Cadastro de Produtos");
        JButton consulta = new JButton("Consulta de Produtos");
        JButton movimentacoes = new JButton("Movimentações (Entrada e Saída)");
        JButton relatorio = new JButton("Relatório de Estoque");
        JButton sair = new JButton("Sair");

        menu.add(cadastro);
        menu.add(consulta);
        menu.add(movimentacoes);
        menu.add(relatorio);
        menu.add(sair);

        add(menu, BorderLayout.CENTER);

        cadastro.addActionListener(e -> abrirCadastro());
        consulta.addActionListener(e -> abrirConsulta());
        movimentacoes.addActionListener(e -> abrirMovimentacoes());
        relatorio.addActionListener(e -> abrirRelatorio());
        sair.addActionListener(e -> System.exit(0));
    }

    // A tela principal continua aberta, o "voltar" das outras telas só traz ela pra frente.

    private void abrirCadastro() {
        new TelaCadastroProduto(this, produtoController, null, null).setVisible(true);
    }

    private void abrirConsulta() {
        new TelaConsultaProdutos(produtoController, this::toFront).setVisible(true);
    }

    private void abrirMovimentacoes() {
        new TelaMovimentacoes(produtoController, movimentacaoController, this::toFront).setVisible(true);
    }

    private void abrirRelatorio() {
        new TelaRelatorio(this, relatorioController).setVisible(true);
    }
}