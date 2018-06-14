package br.com.itsstecnologia.integradorbi.service.read;

import br.com.itsstecnologia.integradorbi.entity.Demanda;
import br.com.itsstecnologia.integradorbi.enums.Estado;
import br.com.itsstecnologia.integradorbi.enums.Prioridade;
import br.com.itsstecnologia.integradorbi.util.Config;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.Calendar;

public class testaCaulaSLA {

    private Demanda demanda;
    private Calendar dataCriacao;
    private Calendar dataAlteracao;
    private Calendar dataFinal;
    private String empresa;
    private String status;
    private Prioridade prioridade;
    private Calendar esperado;


    @Before
    public void setUp() throws Exception {
    demanda = new Demanda();
        dataFinal =  Calendar.getInstance();
        dataAlteracao =  Calendar.getInstance();
        dataCriacao =  Calendar.getInstance();
        esperado =  Calendar.getInstance();
    }

    @Test
    public void teste1() {
        try {
            dataCriacao.setTime(Config.getParametros().getDateTime().parse("14/06/2018 14:00"));
            dataAlteracao.setTime(Config.getParametros().getDateTime().parse("14/06/2018 14:00"));
            esperado.setTime(Config.getParametros().getDateTime().parse("14/06/2018 18:00"));
            empresa = "empresa";
            status = "Em Atendimento N1";
            prioridade = Prioridade.URGENTE;
            demanda.setDataCriacao(dataCriacao);
            demanda.setDataEntradaNoEstado(dataAlteracao);
            demanda.setEmpresa(empresa);
            demanda.setStatus(status);
            demanda.setPrioridade(prioridade);
            dataFinal = demanda.getDataFinal();
            Assert.assertEquals(esperado, dataFinal);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
