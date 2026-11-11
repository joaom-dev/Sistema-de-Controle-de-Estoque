package com.project.controller;

import com.project.model.Produto;
import jdk.jshell.execution.Util;

import java.math.BigDecimal;
import java.util.List;

public class RelatorioController {

    public static final String[] COLUNAS =
            {"Código", "Descrição", "Quantidade", "Mínimo", "Faltam"};

    private final Estoque estoque;

    public RelatorioController(Estoque estoque) {
        this.estoque = estoque;
    }

    public BigDecimal calcularValorTotal() {
        return estoque.valorTotal();
    }

    public String getValorTotalFormatado() {
        return Util.MOEDA.format(calcularValorTotal());
    }

    public int getTotalProdutos() {
        return estoque.listar().size();
    }

    public List<Produto> getProdutosAbaixoDoMinimo() {
        return estoque.abaixoDoMinimo();
    }

    public int getTotalAbaixoDoMinimo() {
        return getProdutosAbaixoDoMinimo().size();
    }

    public Object[][] getLinhasTabela() {
        List<Produto> criticos = getProdutosAbaixoDoMinimo();
        Object[][] linhas = new Object[criticos.size()][];
        for (int i = 0; i < criticos.size(); i++) {
            Produto p = criticos.get(i);
            linhas[i] = new Object[]{p.getCodigo(), p.getDescricao(),
                    p.getQuantidade(), p.getMinimo(), p.getMinimo() - p.getQuantidade()};
        }
        return linhas;
    }

    public String gerarTextoRelatorio() {
        StringBuilder sb = new StringBuilder();
        sb.append("RELATÓRIO DE ESTOQUE\n");
        sb.append("Produtos cadastrados: ").append(getTotalProdutos()).append('\n');
        sb.append("Valor total do estoque: ").append(getValorTotalFormatado()).append("\n\n");
        sb.append("Produtos abaixo do mínimo (").append(getTotalAbaixoDoMinimo()).append("):\n");
        for (Produto p : getProdutosAbaixoDoMinimo())
            sb.append(String.format("- %s | %s | qtd %d | mín %d | faltam %d%n",
                    p.getCodigo(), p.getDescricao(), p.getQuantidade(),
                    p.getMinimo(), p.getMinimo() - p.getQuantidade()));
        return sb.toString();
    }
}

