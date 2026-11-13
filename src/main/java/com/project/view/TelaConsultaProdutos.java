package com.project.view;

import com.project.controller.ProdutoController;
import com.project.model.Produto;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;

public class TelaConsultaProdutos extends JFrame {

    private final ProdutoController controller;
    private final Runnable aoVoltar;

    private final ProdutoTableModel modelo = new ProdutoTableModel();
    private final JTable tabela = new JTable(modelo);
    private final JTextField busca = new JTextField(22);

    public TelaConsultaProdutos(ProdutoController controller, Runnable aoVoltar) {
        this.controller = controller;
        this.aoVoltar = aoVoltar;

        setTitle("Consulta de Produtos");
        setSize(850, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JButton btnPesquisar = new JButton("Pesquisar");
        JButton btnMostrarTodos = new JButton("Mostrar todos");

        btnPesquisar.addActionListener(e -> pesquisar());
        busca.addActionListener(e -> pesquisar());
        btnMostrarTodos.addActionListener(e -> {
            busca.setText("");
            carregar();
        });

        JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topo.add(new JLabel("Código ou descrição:"));
        topo.add(busca);
        topo.add(btnPesquisar);
        topo.add(btnMostrarTodos);
        add(topo, BorderLayout.NORTH);

        configurarTabela();
        JScrollPane rolagem = new JScrollPane(tabela);
        rolagem.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        add(rolagem, BorderLayout.CENTER);

        JButton btnNovo = new JButton("Novo");
        JButton btnEditar = new JButton("Editar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnVoltar = new JButton("Voltar");

        btnNovo.addActionListener(e -> abrirCadastro(null));
        btnEditar.addActionListener(e -> editar());
        btnExcluir.addActionListener(e -> excluir());
        btnVoltar.addActionListener(e -> voltar());

        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        rodape.add(btnNovo);
        rodape.add(btnEditar);
        rodape.add(btnExcluir);
        rodape.add(btnVoltar);
        add(rodape, BorderLayout.SOUTH);

        carregar();
    }

    private void configurarTabela() {
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setRowHeight(26);
        tabela.setAutoCreateRowSorter(true);
        tabela.getTableHeader().setReorderingAllowed(false);

        DefaultTableCellRenderer renderizador = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object valor,
                                                           boolean selecionada, boolean foco,
                                                           int linha, int coluna) {

                Component c = super.getTableCellRendererComponent(t, valor, selecionada, foco, linha, coluna);

                if (valor instanceof Double) {
                    setText(String.format("R$ %.2f", (Double) valor));
                }

                if (valor instanceof Number) {
                    setHorizontalAlignment(RIGHT);
                } else {
                    setHorizontalAlignment(LEFT);
                }

                Produto p = modelo.getProduto(t.convertRowIndexToModel(linha));
                if (!selecionada) {
                    if (p != null && p.isAbaixoDoMinimo()) {
                        c.setForeground(new Color(180, 0, 0));
                    } else {
                        c.setForeground(Color.BLACK);
                    }
                }
                return c;
            }
        };

        tabela.setDefaultRenderer(Object.class, renderizador);
        tabela.setDefaultRenderer(String.class, renderizador);
        tabela.setDefaultRenderer(Double.class, renderizador);
        tabela.setDefaultRenderer(Integer.class, renderizador);
    }

    private void carregar() {
        modelo.setProdutos(controller.listar());
    }

    private void pesquisar() {
        modelo.setProdutos(controller.pesquisar(busca.getText()));
        if (modelo.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Nenhum produto encontrado.");
        }
    }

    private Produto selecionado() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um produto na tabela.");
            return null;
        }
        return modelo.getProduto(tabela.convertRowIndexToModel(linha));
    }

    private void abrirCadastro(Produto produto) {
        TelaCadastroProduto tela = new TelaCadastroProduto(this, controller, produto, this::pesquisarOuCarregar);
        tela.setVisible(true);
    }

    private void pesquisarOuCarregar() {
        modelo.setProdutos(controller.pesquisar(busca.getText()));
    }

    private void editar() {
        Produto p = selecionado();
        if (p != null) {
            abrirCadastro(p);
        }
    }

    private void excluir() {
        Produto p = selecionado();
        if (p == null) {
            return;
        }

        int opcao = JOptionPane.showConfirmDialog(this,
                "Excluir o produto " + p.getCodigo() + " - " + p.getDescricao() + "?",
                "Confirmar exclusão", JOptionPane.YES_NO_OPTION);

        if (opcao == JOptionPane.YES_OPTION) {
            try {
                controller.excluir(p.getCodigo());
                pesquisarOuCarregar();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "Atenção", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void voltar() {
        dispose();
        if (aoVoltar != null) {
            aoVoltar.run();
        }
    }
}
