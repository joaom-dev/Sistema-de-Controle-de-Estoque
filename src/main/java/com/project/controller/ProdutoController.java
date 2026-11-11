package com.project.controller;

import java.util.ArrayList;

import com.project.model.Produto;

public class ProdutoController {

    private ArrayList<Produto> produtos = new ArrayList<>();

    public void cadastrar(String codigo, String descricao, String categoria,
                          double preco, int quantidade, int estoqueMinimo) {

        validarCampos(codigo, descricao, categoria, preco, quantidade, estoqueMinimo);

        if (buscarPorCodigo(codigo) != null) {
            throw new IllegalArgumentException("Já existe um produto com o código " + codigo + ".");
        }

        produtos.add(new Produto(codigo.trim(), descricao.trim(), categoria,
                preco, quantidade, estoqueMinimo));
    }

    public ArrayList<Produto> listar() {
        return produtos;
    }

    public Produto buscarPorCodigo(String codigo) {
        if (codigo == null) {
            return null;
        }
        for (Produto p : produtos) {
            if (p.getCodigo().equalsIgnoreCase(codigo.trim())) {
                return p;
            }
        }
        return null;
    }

    public ArrayList<Produto> pesquisar(String texto) {
        ArrayList<Produto> resultado = new ArrayList<>();

        if (texto == null || texto.trim().isEmpty()) {
            resultado.addAll(produtos);
            return resultado;
        }

        String busca = texto.trim().toLowerCase();
        for (Produto p : produtos) {
            if (p.getCodigo().toLowerCase().contains(busca)
                    || p.getDescricao().toLowerCase().contains(busca)) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    public void editar(String codigo, String descricao, String categoria,
                       double preco, int quantidade, int estoqueMinimo) {

        validarCampos(codigo, descricao, categoria, preco, quantidade, estoqueMinimo);

        Produto p = buscarPorCodigo(codigo);
        if (p == null) {
            throw new IllegalArgumentException("Produto não encontrado.");
        }

        p.setDescricao(descricao.trim());
        p.setCategoria(categoria);
        p.setPrecoUnitario(preco);
        p.setQuantidade(quantidade);
        p.setEstoqueMinimo(estoqueMinimo);
    }

    public void excluir(String codigo) {
        Produto p = buscarPorCodigo(codigo);
        if (p == null) {
            throw new IllegalArgumentException("Produto não encontrado.");
        }
        produtos.remove(p);
    }

    private void validarCampos(String codigo, String descricao, String categoria,
                               double preco, int quantidade, int estoqueMinimo) {

        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("Informe o código do produto.");
        }
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("Informe a descrição do produto.");
        }
        if (categoria == null || categoria.trim().isEmpty()) {
            throw new IllegalArgumentException("Escolha uma categoria.");
        }
        if (preco < 0) {
            throw new IllegalArgumentException("O preço não pode ser negativo.");
        }
        if (quantidade < 0) {
            throw new IllegalArgumentException("A quantidade não pode ser negativa.");
        }
        if (estoqueMinimo < 0) {
            throw new IllegalArgumentException("O estoque mínimo não pode ser negativo.");
        }
    }
}
