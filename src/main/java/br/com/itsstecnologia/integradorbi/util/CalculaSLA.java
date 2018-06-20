package br.com.itsstecnologia.integradorbi.util;


import br.com.itsstecnologia.integradorbi.entity.Demanda;
import br.com.itsstecnologia.integradorbi.entity.SLA;

import java.util.Calendar;
import java.util.Objects;

public class CalculaSLA {

    private static Calendar dataFinal = Calendar.getInstance();
    private static int qntTempoMin;
    private static int qntTempoHoras;

    public static Calendar calcular(Demanda demanda) throws NullPointerException {
        long tempo = demanda.getDataEntradaNoEstado().getTimeInMillis();
        dataFinal.setTimeInMillis(tempo);
        getHoras(demanda);
        ajustaHorario(true);
        calcula();
        return dataFinal;
    }

    private static void getHoras(Demanda demanda) {
        SLA sla = getSLA(demanda);
        if (demanda.getStatus().
                equals("Ag. Atendimento")) {
            assert sla != null;
            qntTempoHoras = sla.getTempoMaxInicoAtendimentoHoras();
            qntTempoMin = sla.getTempoMaxInicoAtendimentoMinutos();
        } else {
            assert sla != null;
            qntTempoHoras = sla.getTempoMaxResolucaoHoras();
            qntTempoMin = sla.getTempoMaxResolucaoMinutos();
        }

    }

    private static void calcula() {
        int i;
        for (i = 0; i < qntTempoMin; i++) {
            dataFinal.add(Calendar.MINUTE, 1);
            ajustaHorario(false);
        }
        for (i = 0; i < qntTempoHoras; i++) {
            dataFinal.add(Calendar.HOUR, 1);
            ajustaHorario(false);
        }
        ajustaHorario(false);
    }


    private static void ajustaHorario(boolean bool) {
        if (bool) {
            if (!Calendario.ehDiaUtil(dataFinal)) {
                descontaNaoUtil();
                setHora(Config.getParametrosGlobal().getHoraDeEntrada());
                ajustaHorario(true);
            } else if (Calendario.getHora(dataFinal).compareTo(Calendario.getHora(Config.getParametrosGlobal().getHoraDeEntrada())) < 0) {
                dataFinal.add(Calendar.MINUTE,
                        Calendario.diffMinutos(Calendario.getHora(dataFinal), Config.getParametrosGlobal().getHoraDeEntrada()));
            } else if (Calendario.getHora(dataFinal).compareTo(Calendario.getHora(Config.getParametrosGlobal().getHorarioDeAlmoco())) > 0
                    && Calendario.getHora(dataFinal).compareTo(Calendario.getHora(Config.getParametrosGlobal().getHorarioDeRetorno())) < 0) {
                setHora(Config.getParametrosGlobal().getHorarioDeRetorno());
            } else if (Calendario.getHora(dataFinal).compareTo(Calendario.getHora(Config.getParametrosGlobal().getHoraDeSaida())) >= 0) {
                dataFinal.add(Calendar.DATE, 1);
                setHora(Config.getParametrosGlobal().getHoraDeEntrada());
            }
        } else {
            if (!Calendario.ehDiaUtil(dataFinal)) {
                descontaNaoUtil();
                ajustaHorario(false);
            } else if (Calendario.getHora(dataFinal).compareTo(Calendario.getHora(Config.getParametrosGlobal().getHoraDeEntrada())) < 0) {
                dataFinal.add(Calendar.MINUTE,
                        Calendario.diffMinutos(Calendario.getHora(dataFinal), Config.getParametrosGlobal().getHoraDeEntrada()));
            } else if (Calendario.getHora(dataFinal).compareTo(Calendario.getHora(Config.getParametrosGlobal().getHorarioDeAlmoco())) > 0
                    && Calendario.getHora(dataFinal).compareTo(Calendario.getHora(Config.getParametrosGlobal().getHorarioDeRetorno())) < 0) {
                dataFinal.add(Calendar.MILLISECOND, (int) Config.getParametrosGlobal().getTempoDeAlmoco());
            } else if (Calendario.getHora(dataFinal).compareTo(Calendario.getHora(Config.getParametrosGlobal().getHoraDeSaida())) > 0) {
                dataFinal.add(Calendar.MILLISECOND, (int) Config.getParametrosGlobal().getHorasUteis());
            }
        }
    }

    private static void descontaNaoUtil() {
        while (!Calendario.ehDiaUtil(dataFinal)) {
            dataFinal.add(Calendar.DATE, 1);
        }
    }

    private static void setHora(Calendar horario) {
        dataFinal.set(Calendar.HOUR_OF_DAY, horario.get(Calendar.HOUR_OF_DAY));
        dataFinal.set(Calendar.MINUTE, horario.get(Calendar.MINUTE));
    }


    private static SLA getSLA(Demanda demanda) {
        for (SLA sla : Config.getParametrosGlobal().getSLAs())
            if (Objects.equals(sla.getEmpresa(), demanda.getEmpresa()) && sla.getPrioridade() == demanda.getPrioridade())
                return sla;
        for (SLA sla : Config.getParametrosGlobal().getSLAs()) {
            if (sla.getPrioridade() == demanda.getPrioridade()) return sla;
        }

        return null;
    }
}
