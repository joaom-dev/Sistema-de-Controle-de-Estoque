package com.project.model;

public class Produto {

    private String codigo;
    private String descricao;
    private String categoria;
    private double precoUnitario;
    private int quantidade;
    private int estoqueMinimo;

    public Produto(String codigo, String descricao, String categoria,
                   double precoUnitario, int quantidade, int estoqueMinimo) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.categoria = categoria;
        this.precoUnitario = precoUnitario;
        this.quantidade = quantidade;
        this.estoqueMinimo = estoqueMinimo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getEstoqueMinimo() {
        return estoqueMinimo;
    }

    public void setEstoqueMinimo(int estoqueMinimo) {
        this.estoqueMinimo = estoqueMinimo;
    }


    public double getValorTotal() {
        return precoUnitario * quantidade;
    }

    public boolean isAbaixoDoMinimo() {
        return quantidade < estoqueMinimo;
    }

    @Override
    public String toString() {
        return codigo + " - " + descricao + " (" + categoria + ")"
                + " | R$ " + String.format("%.2f", precoUnitario)
                + " | Qtd: " + quantidade
                + " | Mín: " + estoqueMinimo;
    }
}