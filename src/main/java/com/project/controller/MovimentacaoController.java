//FUNÇAO PRINCIPAL
//1- RECEBE O CODIGO E A QUANTIDADE, VALIDA, ATUALIZA O ESTOQUE DO PRODUTO E APLICA A REGRA DE QUE NENHUMA SAIDA PODE DEIXAR O ESTOQUE NEGATIVO

package com.project.controller;

import com.project.model.Movimentacao;
import com.project.model.Produto;
import com.project.model.TipoMovimentacao;

import java.util.ArrayList;
import java.util.List;

public class MovimentacaoController {

    private final ProdutoController produtoController;
    //ARRAY LIST PARA SALVAR O HISTORICO
    private final ArrayList<Movimentacao> historico = new ArrayList<>();

    public MovimentacaoController(ProdutoController produtoController) {
        this.produtoController = produtoController;
    }

    //Entrada de mercadorias (reposição e atualização do estoque)
    public void registrarEntrada(String codigo, String quantidadeStr) {
        Produto produto = localizarProduto(codigo);
        int quantidade = converterQuantidade(quantidadeStr);

        produto.setQuantidade(produto.getQuantidade() + quantidade);
        historico.add(new Movimentacao(produto.getCodigo(), TipoMovimentacao.ENTRADA, quantidade));
    }

    //Saída de mercadorias (retirada e bloqueio de estoque negativo)
    public void registrarSaida(String codigo, String quantidadeStr) {
        Produto produto = localizarProduto(codigo);
        int quantidade = converterQuantidade(quantidadeStr);

        if (quantidade > produto.getQuantidade()) {
            throw new IllegalArgumentException("Estoque insuficiente. Disponível: "
                    + produto.getQuantidade() + " unidade(s).");
        }

        produto.setQuantidade(produto.getQuantidade() - quantidade);
        historico.add(new Movimentacao(produto.getCodigo(), TipoMovimentacao.SAIDA, quantidade));
    }

    // Auxiliar para listar os produtos na tela de movimentação (JTable)
    public List<Produto> listarProdutosDisponiveis() {
        return produtoController.listar();
    }

    // Auxiliar para buscar dados de um produto específico antes de movimentar
    public Produto buscarProdutoPorCodigo(String codigo) {
        return produtoController.buscarPorCodigo(codigo);
    }

    // Histórico de todas as entradas e saídas registradas
    public List<Movimentacao> listarHistorico() {
        return new ArrayList<>(historico);
    }

    private Produto localizarProduto(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("Informe o código do produto.");
        }
        Produto produto = produtoController.buscarPorCodigo(codigo);
        if (produto == null) {
            throw new IllegalArgumentException("Produto não encontrado.");
        }
        return produto;
    }

    private int converterQuantidade(String quantidadeStr) {
        if (quantidadeStr == null || quantidadeStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Informe a quantidade.");
        }
        int quantidade;
        try {
            quantidade = Integer.parseInt(quantidadeStr.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("A quantidade deve ser um número inteiro válido.");
        }
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }
        return quantidade;
    }
}