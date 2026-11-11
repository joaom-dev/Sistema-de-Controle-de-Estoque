//FUNÇÕES PRINCIPAIS
//1-LE OS DADOS E PREPARA AS INFORMAÇÕES QUE A TELA RELATORIO VAI MOSTRAR
// -VALOR FINANCEIRO
// -LISTA DE PRODUTOS ABAIXO DO MINIMO

package com.project.controller;

import com.project.model.Produto;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RelatorioController {

    public static final String[] COLUNAS =
            {"Código", "Descrição", "Quantidade", "Mínimo", "Faltam"};

    private static final NumberFormat MOEDA =
            NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR"));

    private final ProdutoController produtoController;

    public RelatorioController(ProdutoController produtoController) {
        this.produtoController = produtoController;
    }

    //METODO QUE PEDE O VALOR TOTAL E CALCULA O PREÇO * QUANTIDADE
    public double calcularValorTotal() {
        double total = 0;
        for (Produto p : produtoController.listar()) {
            total += p.getValorTotal();
        }
        return total;
    }

    //METODO QUE FORMATA O VALOR EM TEXTO DE MOEDA (EX: R$ 1.250,50)
    public String getValorTotalFormatado() {
        return MOEDA.format(calcularValorTotal());
    }

    //METODO QUE RETORNA A QUANTIDADE DE PRODUTOS CADASTRADOS
    public int getTotalProdutos() {
        return produtoController.listar().size();
    }

    //METODO QUE RETORNAR OS PRODUTOS QUE ESTAO AVAIXO DO ESTOQUE MINIMO
    public List<Produto> getProdutosAbaixoDoMinimo() {
        List<Produto> criticos = new ArrayList<>();
        for (Produto p : produtoController.listar()) {
            if (p.isAbaixoDoMinimo()) {
                criticos.add(p);
            }
        }
        return criticos;
    }

    //METODO QUE REAPROIVEITA O METODO ANTERIOR E CONTA QUANTOS SÃO
    public int getTotalAbaixoDoMinimo() {
        return getProdutosAbaixoDoMinimo().size();
    }

    //METODO QUE SERVE PARA CONVERTER A LISTA DE PRODUTOS EM UMA MATRIZ
    public Object[][] getLinhasTabela() {
        List<Produto> criticos = getProdutosAbaixoDoMinimo();
        Object[][] linhas = new Object[criticos.size()][];
        for (int i = 0; i < criticos.size(); i++) {
            Produto p = criticos.get(i);
            linhas[i] = new Object[]{p.getCodigo(), p.getDescricao(),
                    p.getQuantidade(), p.getEstoqueMinimo(),
                    p.getEstoqueMinimo() - p.getQuantidade()};
        }
        return linhas;
    }

    //MONTA O RELATORIO INTEIRO EM TEXTO
    public String gerarTextoRelatorio() {
        StringBuilder sb = new StringBuilder();
        sb.append("RELATÓRIO DE ESTOQUE\n");
        sb.append("Produtos cadastrados: ").append(getTotalProdutos()).append('\n');
        sb.append("Valor total do estoque: ").append(getValorTotalFormatado()).append("\n\n");
        sb.append("Produtos abaixo do mínimo (").append(getTotalAbaixoDoMinimo()).append("):\n");
        for (Produto p : getProdutosAbaixoDoMinimo())
            sb.append(String.format("- %s | %s | qtd %d | mín %d | faltam %d%n",
                    p.getCodigo(), p.getDescricao(), p.getQuantidade(),
                    p.getEstoqueMinimo(), p.getEstoqueMinimo() - p.getQuantidade()));
        return sb.toString();
    }
}