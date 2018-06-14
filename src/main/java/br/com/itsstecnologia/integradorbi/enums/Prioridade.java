package br.com.itsstecnologia.integradorbi.enums;

public enum Prioridade {

    URGENTE("Urgente"), ALTA("Alta"), MEDIA("Média"), BAIXA("Baixa"), NC("NC-");

    private String valor;

    Prioridade(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}
