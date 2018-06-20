package br.com.itsstecnologia.integradorbi.entity;


import br.com.itsstecnologia.integradorbi.enums.Estado;
import br.com.itsstecnologia.integradorbi.enums.Prioridade;
import br.com.itsstecnologia.integradorbi.util.CalculaSLA;
import br.com.itsstecnologia.integradorbi.util.Config;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "demandas")
public class Demanda {

    @Id
    @Column(unique = true)
    private Integer id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String empresa;

    @Column(nullable = false)
    private Estado estado;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String destino;

    @Column(nullable = false)
    private Prioridade prioridade;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Calendar dataAlteracao;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dataFinal;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dataCriacao;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dataEntradaNoEstado;

    @Column(nullable = false)
    private String responsavel;

    public Demanda() {
        super();
    }

    public Demanda(Integer id, String titulo, String empresa, Estado estado, String status, String destino, Prioridade prioridade,
                   Calendar dataAlteracao, Calendar dataCriacao, Calendar dataEntradaNoEstado, String responsavel) {
        super();
        this.id = id;
        this.titulo = titulo;
        this.empresa = empresa;
        this.estado = estado;
        this.status = status;
        this.destino = destino;
        this.prioridade = prioridade;
        this.dataAlteracao = dataAlteracao;
        this.dataCriacao = dataCriacao;
        this.dataEntradaNoEstado = dataEntradaNoEstado;
        this.responsavel = responsavel;
        this.dataFinal =  Calendar.getInstance();
        atualiza();
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
        atualiza();
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
        atualiza();
    }

    public void setStatus(String status) {
        this.status = status;
        atualiza();
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
        atualiza();
    }

    public void setDataCriacao(Calendar dataCriacao) {
        this.dataCriacao = dataCriacao;
        atualiza();
    }

    public void setDataEntradaNoEstado(Calendar dataEntradaNoEstado) {
        this.dataEntradaNoEstado = dataEntradaNoEstado;
        atualiza();
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public void setDataAlteracao(Calendar dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public void setDataFinal(Calendar dataFinal) {
        this.dataFinal = dataFinal;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public Integer getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getEmpresa() {
        return empresa;
    }

    public Estado getEstado() {
        return estado;
    }

    public String getStatus() {
        return status;
    }

    public String getDestino() {
        return destino;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public Calendar getDataAlteracao() {
        return dataAlteracao;
    }

    public Calendar getDataFinal() {
        return dataFinal;
    }

    public Calendar getDataCriacao() {
        return dataCriacao;
    }

    public Calendar getDataEntradaNoEstado() {
        return dataEntradaNoEstado;
    }

    public String getResponsavel() {
        return responsavel;
    }

    private void atualiza() {
        if (this.empresa != null
                && this.prioridade != null
                && this.dataEntradaNoEstado != null
                && this.dataCriacao != null
                && this.status != null
                && this.estado != null
//                && this.estado != Estado.CONCLUIDO
                )
            this.dataFinal = CalculaSLA.calcular(this);
    }

    @Override
    public String toString() {

        return "Demanda{" +
                "ID: " + id +
                ", Titulo: " + titulo +
                ", Empresa: " + empresa +
                ", Estado: " + estado +
                ", Status: " + status +
                ", Destino: " + destino +
                ", Prioridade: " + prioridade +
                ", Data Alteração: " + Config.getParametrosGlobal().getDateTime().format(dataAlteracao.getTime()) +
                ", Data de Entrada no Estado: " + Config.getParametrosGlobal().getDateTime().format(dataEntradaNoEstado.getTime()) +
                ", Data Final: " + Config.getParametrosGlobal().getDateTime().format(dataFinal.getTime()) +
                ", Data de Criacao :" + Config.getParametrosGlobal().getDateTime().format(dataCriacao.getTime()) +
                ", responsavel: " + responsavel +
                '}';
    }

}
