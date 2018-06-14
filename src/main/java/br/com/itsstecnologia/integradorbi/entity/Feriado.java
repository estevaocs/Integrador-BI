package br.com.itsstecnologia.integradorbi.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Calendar;


@Entity
@Table(name = "feriado")
@AttributeOverride(name = "id", column = @Column(name = "Id"))
public class Feriado extends AbstractPersistable<Integer> {

    @Id
    private int id;

    @Column(name = "Data", nullable = false, unique = false)
    @Temporal(TemporalType.DATE)
    private Calendar data;

    @Column(name = "Descricao", nullable = false, unique = false)
    private String nome;

    public Feriado() {
    }

    public Feriado(int id, Calendar data, String nome) {
        this.id = id;
        this.data = data;
        this.nome = nome;
    }

    public Calendar getData() {
        return data;
    }

    public void setData(Calendar data) {
        this.data = data;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
