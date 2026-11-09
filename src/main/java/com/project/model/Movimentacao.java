package com.project.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Movimentacao {

    private String codigoProduto;
    private TipoMovimentacao tipo;
    private int quantidade;
    private LocalDateTime data;

    public Movimentacao(String codigoProduto, TipoMovimentacao tipo, int quantidade) {
        this.codigoProduto = codigoProduto;
        this.tipo = tipo;
        this.quantidade = quantidade;
        this.data = LocalDateTime.now();
    }

    public String getCodigoProduto() {
        return codigoProduto;
    }

    public TipoMovimentacao getTipo() {
        return tipo;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public LocalDateTime getData() {
        return data;
    }

    public String getDataFormatada() {
        return data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    @Override
    public String toString() {
        return getDataFormatada() + " | " + tipo + " | Produto: " + codigoProduto + " | Qtd: " + quantidade;
    }
}