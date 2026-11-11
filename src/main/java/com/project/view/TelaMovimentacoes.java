package com.project.view;

import com.project.controller.MovimentacaoController;
import com.project.controller.ProdutoController;
import com.project.model.Produto;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;


public class TelaMovimentacoes extends JFrame {

    private final ProdutoController produtos;
    private final MovimentacaoController movimentacoes;
    private final Runnable aoVoltar;

    private final JTextField codigo = new JTextField();
    private final JTextField quantidade = new JTextField();

    private final JLabel saldo = new JLabel("Estoque atual: -");

    private final JComboBox<String> tipo =
            new JComboBox<>(new String[]{"ENTRADA", "SAÍDA"});

    public TelaMovimentacoes(
            ProdutoController produtos,
            MovimentacaoController movimentacoes,
            Runnable aoVoltar) {

        this.produtos = produtos;
        this.movimentacoes = movimentacoes;
        this.aoVoltar = aoVoltar;

        setTitle("Movimentações de Estoque");
        setSize(500, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Título da tela
        JLabel titulo = new JLabel(
                "ENTRADA E SAÍDA DE MERCADORIAS",
                JLabel.CENTER
        );

        titulo.setBorder(
                BorderFactory.createEmptyBorder(20, 10, 10, 10)
        );

        add(titulo, BorderLayout.NORTH);

        // Painel do formulário
        JPanel formulario = new JPanel(new GridLayout(4, 2, 10, 10));

        formulario.setBorder(
                BorderFactory.createEmptyBorder(15, 25, 15, 25)
        );

        formulario.add(new JLabel("Código do produto:"));
        formulario.add(codigo);

        formulario.add(new JLabel("Tipo de movimentação:"));
        formulario.add(tipo);

        formulario.add(new JLabel("Quantidade:"));
        formulario.add(quantidade);

        formulario.add(new JLabel("Situação do estoque:"));
        formulario.add(saldo);

        add(formulario, BorderLayout.CENTER);

        // Botões
        JButton consultar = new JButton("Consultar produto");
        JButton registrar = new JButton("Registrar movimentação");
        JButton limpar = new JButton("Limpar");
        JButton voltar = new JButton("Voltar");

        JPanel botoes = new JPanel(new FlowLayout());

        botoes.add(consultar);
        botoes.add(registrar);
        botoes.add(limpar);
        botoes.add(voltar);

        add(botoes, BorderLayout.SOUTH);

        consultar.addActionListener(e -> consultarProduto());

        registrar.addActionListener(e -> registrar());

        limpar.addActionListener(e -> limparCampos());

        voltar.addActionListener(e -> {
            dispose();
            aoVoltar.run();
        });
    }

    private void consultarProduto() {

        String codigoProduto = codigo.getText().trim();

        if (codigoProduto.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Informe o código do produto.",
                    "Atenção",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Produto produto = produtos.buscar(codigoProduto);

        if (produto == null) {

            saldo.setText("Produto não encontrado");

            JOptionPane.showMessageDialog(
                    this,
                    "Não existe produto cadastrado com esse código.",
                    "Produto não encontrado",
                    JOptionPane.WARNING_MESSAGE
            );

        } else {

            saldo.setText(
                    "Estoque atual: " + produto.getQuantidade()
                            + " unidade(s)"
            );
        }
    }

    private void registrar() {

        String codigoProduto = codigo.getText().trim();
        String quantidadeTexto = quantidade.getText().trim();

        if (codigoProduto.isEmpty() || quantidadeTexto.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Preencha o código e a quantidade.",
                    "Campos obrigatórios",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            if (tipo.getSelectedItem().equals("ENTRADA")) {

                movimentacoes.registrarEntrada(
                        codigoProduto,
                        quantidadeTexto
                );

            } else {

                movimentacoes.registrarSaida(
                        codigoProduto,
                        quantidadeTexto
                );
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Movimentação registrada com sucesso!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE
            );

            quantidade.setText("");

            consultarProduto();

        } catch (IllegalArgumentException erro) {

            JOptionPane.showMessageDialog(
                    this,
                    erro.getMessage(),
                    "Erro na movimentação",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void limparCampos() {

        codigo.setText("");
        quantidade.setText("");
        saldo.setText("Estoque atual: -");

        tipo.setSelectedIndex(0);

        codigo.requestFocus();
    }
}