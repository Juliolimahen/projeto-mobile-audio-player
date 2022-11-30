package com.example.cadastroaluno;

public class Aluno {
    private String RA;
    private String Nome;
    private String Endereco;

    public Aluno(String RA, String nome, String endereco) {
        this.RA = RA;
        Nome = nome;
        Endereco = endereco;
    }

    public String getRA() {
        return RA;
    }

    public void setRA(String RA) {
        this.RA = RA;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getEndereco() {
        return Endereco;
    }

    public void setEndereco(String endereco) {
        Endereco = endereco;
    }

    @Override
    public String toString() {
        return  RA
                +" - "+Nome
                +" - "+Endereco;
    }

    public Aluno() {
    }
}
