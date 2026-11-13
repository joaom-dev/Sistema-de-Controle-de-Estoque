package com.project.view;

import com.project.model.Produto;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ProdutoTableModel extends AbstractTableModel {

    private static final String[] COLUNAS = {
            "Código", "Descrição", "Categoria", "Preço unit.", "Quantidade", "Estoque mín.", "Valor total"
    };

    private List<Produto> produtos = new ArrayList<>();

    public ProdutoTableModel() {
    }

    public ProdutoTableModel(List<Produto> produtos) {
        setProdutos(produtos);
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = (produtos != null) ? new ArrayList<>(produtos) : new ArrayList<>();
        fireTableDataChanged();
    }

    public Produto getProduto(int linha) {
        if (linha < 0 || linha >= produtos.size()) {
            return null;
        }
        return produtos.get(linha);
    }

    @Override
    public int getRowCount() {
        return produtos.size();
    }

    @Override
    public int getColumnCount() {
        return COLUNAS.length;
    }

    @Override
    public String getColumnName(int coluna) {
        return COLUNAS[coluna];
    }

    @Override
    public Class<?> getColumnClass(int coluna) {
        switch (coluna) {
            case 3:
            case 6:
                return Double.class;
            case 4:
            case 5:
                return Integer.class;
            default:
                return String.class;
        }
    }

    @Override
    public boolean isCellEditable(int linha, int coluna) {
        return false;
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        Produto p = produtos.get(linha);
        switch (coluna) {
            case 0:
                return p.getCodigo();
            case 1:
                return p.getDescricao();
            case 2:
                return p.getCategoria();
            case 3:
                return p.getPrecoUnitario();
            case 4:
                return p.getQuantidade();
            case 5:
                return p.getEstoqueMinimo();
            case 6:
                return p.getValorTotal();
            default:
                return null;
        }
    }
}
