package br.com.itsstecnologia.integradorbi.service.read;

import br.com.itsstecnologia.integradorbi.Log.LogService;
import br.com.itsstecnologia.integradorbi.entity.Demanda;
import br.com.itsstecnologia.integradorbi.enums.Estado;
import br.com.itsstecnologia.integradorbi.enums.Prioridade;
import br.com.itsstecnologia.integradorbi.repository.DemandaRepository;
import br.com.itsstecnologia.integradorbi.util.Config;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;

import static java.util.Calendar.getInstance;

@Controller
public class ReadExcel extends Thread {

    private final String EXCEL_FILE_LOCATION = "C:\\Users\\ITSS\\Desktop\\Demandas.xls";
    private final int INDEX_TITLES_COLUMS = 1;
    private Map<String, Integer> map;
    private Workbook workbook;
    private Sheet sheet;

    private final DemandaRepository repository;

    @Autowired
    public ReadExcel(DemandaRepository repository) {
        this.repository = repository;
        try {
            WorkbookSettings workbookSettings = new WorkbookSettings();
            workbookSettings.setEncoding("ISO-8859-1");
            workbook = Workbook.getWorkbook(new File(EXCEL_FILE_LOCATION), workbookSettings);
            int SHEET_INDEX = 0;
            sheet = workbook.getSheet(SHEET_INDEX);
            map = new HashMap<>();
            map.put("Id", null);
            map.put("Título", null);
            map.put("Empresa", null);
            map.put("Estado", null);
            map.put("Destino", null);
            map.put("Prioridade", null);
            map.put("Data da Alteração", null);
            map.put("Responsável Atendimento", null);
            map.put("Data/Hora de Criação", null);
            map.put("Data/Hora de Entrada no Estado", null);
        } catch (IOException | BiffException e) {
            e.printStackTrace();
            new LogService(e, Level.SEVERE);
        }
    }

    @Override
    public void run() {
        Integer id;
        String titulo;
        String empresa;
        String responsavel;
        String status;
        String destino;
        Estado estado;
        Prioridade prioridade;
        Calendar dataAlteracao;
        Calendar dataCriacao;
        Calendar dataEntradaNoEstado;
        Calendar calendar = getInstance();
        calendar.add(Calendar.MONTH, -2);
        try {
            while (Config.getParametrosGlobal().isRodandoAtualizacao()) {
                instaciaMap();
                for (int indx = INDEX_TITLES_COLUMS + 1; indx < sheet.getRows(); indx++) {
                    id = Integer.parseInt(sheet.getCell(map.get("Id"), indx).getContents());
                    titulo = sheet.getCell(map.get("Título"), indx).getContents();
                    empresa = sheet.getCell(map.get("Empresa"), indx).getContents();
                    estado = classificaEstado(sheet.getCell(map.get("Estado"), indx).getContents());
                    status = sheet.getCell(map.get("Estado"), indx).getContents();
                    destino = sheet.getCell(map.get("Destino"), indx).getContents();
                    prioridade = classificaPrioridade(sheet.getCell(map.get("Prioridade"), indx).getContents());
                    dataAlteracao = setData(sheet.getCell(map.get("Data da Alteração"), indx).getContents(), Config.getParametrosGlobal().getDate());
                    responsavel = sheet.getCell(map.get("Responsável Atendimento"), indx).getContents();
                    dataCriacao = setData(sheet.getCell(map.get("Data/Hora de Criação"), indx).getContents(), Config.getParametrosGlobal().getDateTime());
                    dataEntradaNoEstado = setData(sheet.getCell(map.get("Data/Hora de Entrada no Estado"), indx).getContents(), Config.getParametrosGlobal().getDateTime());
                    Demanda demanda = new Demanda(id, titulo, empresa, estado, status, destino, prioridade, dataAlteracao,
                            dataCriacao, dataEntradaNoEstado, responsavel);
                    if ((demanda.getEstado() != Estado.CONCLUIDO)
                            || (demanda.getDataAlteracao().compareTo(calendar) >= 0)) {
                        repository.save(demanda);
                    }
                }
                Thread.sleep(Config.getParametrosGlobal().getTempo().getTempo());
            }
        } catch (Exception e) {
            LogService logService = new LogService(e, Level.SEVERE);
            logService.start();
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }

    }

    private Calendar setData(String dataDaAlteracao, SimpleDateFormat smpdtf) throws ParseException {
        Calendar data = getInstance();
        data.setTime(smpdtf.parse(dataDaAlteracao));
        return data;
    }


    private void instaciaMap() throws Exception {

        for (int i = 1; i < sheet.getColumns(); i++) {
            Cell cell = sheet.getCell(i, INDEX_TITLES_COLUMS);
            map.entrySet().forEach(entry -> {
                if (cell.getContents().equals(entry.getKey())) {
                    entry.setValue(cell.getColumn());
                    System.out.println(cell.getContents());
                }
            });
        }
        verificaTabela();
    }

    private void verificaTabela() throws Exception {
        List<String> execptions = new ArrayList<>();
        map.forEach((key, value) -> {
            if (value == null) {
                execptions.add(key);
            }
        });
        if (!execptions.isEmpty()) {
            String msg = criaMsgDeExecao(execptions);
            throw new Exception(msg);
        }
    }

    private String criaMsgDeExecao(List<String> execptions) {
        StringBuilder msg;
        msg = new StringBuilder("Está faltando os seguintes campos '");
        for (String s : execptions) {
            msg
                    .append(s).append("', ");
        }
        msg.append("da Tabela '").append(sheet.getName()).append("' ").append("do arquivo ").append(EXCEL_FILE_LOCATION);

        return msg.toString();
    }


    private Estado classificaEstado(String valor) {
        switch (valor) {
            case "Concluido ":
                return Estado.CONCLUIDO;
            case "Ag. Avaliação Cliente":
                return Estado.AG_AVALIACAO;
            case "Em Atendimento N1":
                return Estado.EM_ATENDIMENTO;
            case "Em atendimento N2":
                return Estado.EM_ATENDIMENTO;
            case "Ag. Atendimento":
                return Estado.EM_ATENDIMENTO;
            case "Ag. Informação Cliente":
                return Estado.AG_INFORMACAO;
            default:
                return Estado.OUTROS;
        }
    }

    private Prioridade classificaPrioridade(String valor) {
        if (valor.contains("1"))
            return Prioridade.URGENTE;
        else if (valor.contains("2"))
            return Prioridade.ALTA;
        else if (valor.contains("3"))
            return Prioridade.MEDIA;
        else if (valor.contains("4"))
            return Prioridade.BAIXA;
        else
            return Prioridade.NC;
    }
}

