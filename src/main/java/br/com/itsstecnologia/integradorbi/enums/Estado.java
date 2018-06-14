package br.com.itsstecnologia.integradorbi.enums;

public enum Estado {

    CONCLUIDO("Concluído"), EM_ATENDIMENTO("Em Atendimento"), AG_INFORMACAO("Ag. Informação"), AG_AVALIACAO("Ag. Avaliação"), OUTROS("Outros");

    public String valor;

    Estado(String valor) {
        switch (valor) {
            case "Concluido \n":
                this.valor = valor;
            case "Ag. Avaliação Cliente\n":
                this.valor = "Ag. Avaliação";
            case "Em Atendimento N1\n":
                this.valor = "Em Atendimento";
            case "Em atendimento N2\n":
                this.valor = "Em Atendimento";
            case "Ag. Atendimento\n":
                this.valor = "Em Atendimento";
            case "Ag. Informação Cliente\n":
                this.valor = "Ag. Informação";
            default:
                this.valor = "Outros";
        }
    }

    public String getValor() {
        return this.valor;
    }


}
