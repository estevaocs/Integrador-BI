package br.com.itsstecnologia.integradorbi.enums;

public enum TempoAtualizacao {

    A_CADA_15_MINUTOS(15 * 60 * 1000), A_CADA_MEIA_HORA(30 * 60 * 1000), A_CADA_HORA(60* 60 * 1000);

    private int tempo;

    TempoAtualizacao(int tempo) {
        this.tempo = tempo;
    }

    public int getTempo() {
        return tempo;
    }
}
