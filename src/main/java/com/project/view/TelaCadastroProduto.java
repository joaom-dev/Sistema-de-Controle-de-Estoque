package com.project.view;

import com.project.controller.ProdutoController;
import com.project.model.Produto;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Window;

public class TelaCadastroProduto extends JDialog {

    private static final String[] CATEGORIAS = {
            "", "Alimentos", "Bebidas", "Limpeza", "Higiene", "Eletrônicos", "Papelaria", "Outros"
    };

    private final ProdutoController controller;
    private final Produto produtoEmEdicao;
    private final Runnable aoSalvar;

    private final JTextField codigo = new JTextField();
    private final JTextField descricao = new JTextField();
    private final JComboBox<String> categoria = new JComboBox<>(CATEGORIAS);
    private final JTextField preco = new JTextField();
    private final JTextField quantidade = new JTextField();
    private final JTextField estoqueMinimo = new JTextField();

    public TelaCadastroProduto(Window pai, ProdutoController controller,
                               Produto produto, Runnable aoSalvar) {

        super(pai, produto == null ? "Cadastrar produto" : "Editar produto",
                ModalityType.APPLICATION_MODAL);

        this.controller = controller;
        this.produtoEmEdicao = produto;
        this.aoSalvar = aoSalvar;

        setSize(480, 400);
        setLocationRelativeTo(pai);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel titulo = new JLabel(
                produto == null ? "CADASTRO DE PRODUTO" : "EDIÇÃO DE PRODUTO",
                JLabel.CENTER
        );
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        add(titulo, BorderLayout.NORTH);

        JPanel formulario = new JPanel(new GridLayout(6, 2, 10, 10));
        formulario.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));

        formulario.add(new JLabel("Código:"));
        formulario.add(codigo);

        formulario.add(new JLabel("Descrição:"));
        formulario.add(descricao);

        formulario.add(new JLabel("Categoria:"));
        formulario.add(categoria);

        formulario.add(new JLabel("Preço unitário (R$):"));
        formulario.add(preco);

        formulario.add(new JLabel("Quantidade:"));
        formulario.add(quantidade);

        formulario.add(new JLabel("Estoque mínimo:"));
        formulario.add(estoqueMinimo);

        add(formulario, BorderLayout.CENTER);

        JButton btnSalvar = new JButton("Salvar");
        JButton btnLimpar = new JButton("Limpar");
        JButton btnCancelar = new JButton("Cancelar");

        btnSalvar.addActionListener(e -> salvar());
        btnLimpar.addActionListener(e -> limpar());
        btnCancelar.addActionListener(e -> dispose());

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        botoes.add(btnSalvar);
        botoes.add(btnLimpar);
        botoes.add(btnCancelar);

        add(botoes, BorderLayout.SOUTH);
        getRootPane().setDefaultButton(btnSalvar);

        if (produto != null) {
            preencher(produto);
        }
    }

    private void preencher(Produto p) {
        codigo.setText(p.getCodigo());
        codigo.setEditable(false);
        descricao.setText(p.getDescricao());

        boolean existe = false;
        for (int i = 0; i < categoria.getItemCount(); i++) {
            if (categoria.getItemAt(i).equalsIgnoreCase(p.getCategoria())) {
                categoria.setSelectedIndex(i);
                existe = true;
                break;
            }
        }
        if (!existe) {
            categoria.addItem(p.getCategoria());
            categoria.setSelectedItem(p.getCategoria());
        }

        preco.setText(String.valueOf(p.getPrecoUnitario()));
        quantidade.setText(String.valueOf(p.getQuantidade()));
        estoqueMinimo.setText(String.valueOf(p.getEstoqueMinimo()));
    }

    private void limpar() {
        if (produtoEmEdicao == null) {
            codigo.setText("");
        }
        descricao.setText("");
        categoria.setSelectedIndex(0);
        preco.setText("");
        quantidade.setText("");
        estoqueMinimo.setText("");
    }

    private void salvar() {
        try {
            String cat = (String) categoria.getSelectedItem();
            double valorPreco = lerDecimal(preco.getText(), "o preço unitário");
            int valorQuantidade = lerInteiro(quantidade.getText(), "a quantidade");
            int valorMinimo = lerInteiro(estoqueMinimo.getText(), "o estoque mínimo");

            if (produtoEmEdicao == null) {
                controller.cadastrar(codigo.getText(), descricao.getText(), cat,
                        valorPreco, valorQuantidade, valorMinimo);
                JOptionPane.showMessageDialog(this, "Produto cadastrado com sucesso!");
            } else {
                controller.editar(codigo.getText(), descricao.getText(), cat,
                        valorPreco, valorQuantidade, valorMinimo);
                JOptionPane.showMessageDialog(this, "Produto atualizado com sucesso!");
            }

            if (aoSalvar != null) {
                aoSalvar.run();
            }
            dispose();

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Atenção", JOptionPane.WARNING_MESSAGE);
        }
    }

    private double lerDecimal(String texto, String campo) {
        try {
            return Double.parseDouble(texto.trim().replace(",", "."));
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Informe " + campo + " com um número válido (ex: 12.50).");
        }
    }

    private int lerInteiro(String texto, String campo) {
        try {
            return Integer.parseInt(texto.trim());
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Informe " + campo + " com um número inteiro válido.");
        }
    }
}
