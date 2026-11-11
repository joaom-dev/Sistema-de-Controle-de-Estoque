//FUNÇÕES PRINCIPAIS
//1- GUARDAR A LISTA DE PRODUTOS EM MEMÓRIA (ARRAY LIST)
//2- CRUD
//3- BARRAGEM DE INFORMAÇÕES ERRADAS/ERROS DO USER


package com.project.controller;

import java.util.ArrayList;

import com.project.model.Produto;

public class ProdutoController {

    //BANCO DE DADOS LOCAL QUE SO FUNCIONA ENQUANTO O SISTEMA ESTIVER RODANDO
    private ArrayList<Produto> produtos = new ArrayList<>();

    //METODO PARA CADASTRAR PRODUTO(VALIDA SE JÁ EXISTE CODIGO IGUAL, DEPOIS CRIA CADASTRA O PRODUTO)
    public void cadastrar(String codigo, String descricao, String categoria,
                          double preco, int quantidade, int estoqueMinimo) {

        validarCampos(codigo, descricao, categoria, preco, quantidade, estoqueMinimo);

        if (buscarPorCodigo(codigo) != null) {
            throw new IllegalArgumentException("Já existe um produto com o código " + codigo + ".");
        }

        produtos.add(new Produto(codigo.trim(), descricao.trim(), categoria.trim(),
                preco, quantidade, estoqueMinimo));
    }

    //METODO PARA LISTAR TODOS OS PRODUTOS
    public ArrayList<Produto> listar() {
        return new ArrayList<>(produtos);
    }

    //METODO QUE PERCORRE O ARRAY COM UM FOR PARA COMPARAR OS CODIGOS DUPLICADOS
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

    //SISTEMA DE CAMPO DE PESQUISA(SE FICAR VAZIO MOSTRA TODOS OS PRODUTOS, PODE ENCONTRAR OS PRODUTOS SO ESCREVENDO UMA PARTE)
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

    //METODO PARA EDITAR OS PRODUTOS(VALIDA OS CAMPOS, BUSCA OS PRODUTOS PELO CODIGO E DEPOIS ATUALIZA OS DADOS)
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

    //METODO PARA EXCLUIR PRODUTO(VALIDA O CODIGO E SO DPS EXCLUI)
    public void excluir(String codigo) {
        Produto p = buscarPorCodigo(codigo);
        if (p == null) {
            throw new IllegalArgumentException("Produto não encontrado.");
        }
        produtos.remove(p);
    }

    //METODO DE USO INTERNO(QUANDO ALGO ESTÁ NA ERRADO NAS INFO FICAM SEPARADOS PARA CADASTRAR E EDITAR
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
