package br.com.itsstecnologia.integradorbi.entity;

import br.com.itsstecnologia.integradorbi.enums.Prioridade;

import java.util.Calendar;

public class SLA {
    private String empresa = null;
    private Prioridade prioridade;
    private int tempoMaxInicoAtendimentoHoras;
    private int tempoMaxInicoAtendimentoMinutos;

    private int tempoMaxResolucaoHoras;
    private int tempoMaxResolucaoMinutos;

    public SLA(String empresa, Prioridade prioridade, Calendar tempoMaxInicoAtendimento, Calendar tempoMaxResolucao) {
        this.empresa = empresa;
        this.prioridade = prioridade;
        this.tempoMaxInicoAtendimentoHoras = tempoMaxInicoAtendimento.get(Calendar.HOUR_OF_DAY);
        this.tempoMaxInicoAtendimentoMinutos = tempoMaxInicoAtendimento.get(Calendar.MINUTE);
        this.tempoMaxResolucaoHoras = tempoMaxResolucao.get(Calendar.HOUR_OF_DAY);
        this.tempoMaxResolucaoMinutos = tempoMaxResolucao.get(Calendar.MINUTE);
    }

    public SLA(Prioridade prioridade) {
        this.prioridade = prioridade;
        switch (prioridade.getValor()) {
            case "Urgente":
                this.tempoMaxInicoAtendimentoHoras = 0;
                this.tempoMaxInicoAtendimentoMinutos = 30;
                this.tempoMaxResolucaoHoras = 4;
                this.tempoMaxResolucaoMinutos = 0;
                break;
            case "Alta":
                this.tempoMaxInicoAtendimentoHoras = 2;
                this.tempoMaxInicoAtendimentoMinutos = 0;
                this.tempoMaxResolucaoHoras = 8;
                this.tempoMaxResolucaoMinutos = 0;
                break;
            case "Média":
                this.tempoMaxInicoAtendimentoHoras = 4;
                this.tempoMaxInicoAtendimentoMinutos = 0;
                this.tempoMaxResolucaoHoras = 24;
                this.tempoMaxResolucaoMinutos = 0;
                break;
            case "Baixa":
            case "NC-":
                this.tempoMaxInicoAtendimentoHoras = 8;
                this.tempoMaxInicoAtendimentoMinutos = 0;
                this.tempoMaxResolucaoHoras = 40;
                this.tempoMaxResolucaoMinutos = 0;
                break;
        }
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public int getTempoMaxInicoAtendimentoHoras() {
        return tempoMaxInicoAtendimentoHoras;
    }

    public int getTempoMaxInicoAtendimentoMinutos() {
        return tempoMaxInicoAtendimentoMinutos;
    }

    public int getTempoMaxResolucaoHoras() {
        return tempoMaxResolucaoHoras;
    }

    public int getTempoMaxResolucaoMinutos() {
        return tempoMaxResolucaoMinutos;
    }
}
