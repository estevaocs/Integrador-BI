package br.com.itsstecnologia.integradorbi.util;


import br.com.itsstecnologia.integradorbi.entity.Feriado;

import java.util.Calendar;

public class Calendario {

    public static Calendar getHora(Calendar cal) {
        Calendar calen = Calendar.getInstance();
        calen.setTimeInMillis(0);
        calen.add(Calendar.DATE, 1);

        calen.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY));
        calen.set(Calendar.MINUTE, cal.get(Calendar.MINUTE));
        return calen;
    }

    public static Calendar getData(Calendar cal) {
        Calendar calen = Calendar.getInstance();
        calen.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
        return calen;
    }

    public static boolean ehDiaUtil(Calendar cal) {
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
                || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) return false;
        else {
            for (Feriado feriado : Config.getParametrosGlobal().getFeriados()) {
                if (getData(cal).compareTo(getData(feriado.getData())) == 0) return false;
            }
            return true;
        }
    }

    public static int diffMinutos(Calendar cal1, Calendar cal2) {
        cal1 = getHora(cal1);
        cal2 = getHora(cal2);
        int minutos = (int) (cal1.getTimeInMillis() - cal2.getTimeInMillis()) / 60000;
        return Math.abs(minutos);
    }

    public static int getMinutes(Calendar cal) {
        return (int) cal.getTime().getTime() / 60000;
    }

    public static int getHoras(Calendar cal) {
        return (int) getMinutes(cal) / 60;
    }

    public static long getTime(String minuto) {
        String array[] = minuto.split(":");
        int horas = Integer.parseInt(array[0]);
        int minutos = Integer.parseInt(array[1]);
        return (long) ((horas * 3600000) + (minutos * 60000));
    }
}
