package com.template.model.dto;

public class PokemonDTO {
    private int numero;
    private String nome;
    private String tipo;
    private int geracao;

    public PokemonDTO() {}

    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public int getGeracao() { return geracao; }
    public void setGeracao(int geracao) { this.geracao = geracao; }
}
