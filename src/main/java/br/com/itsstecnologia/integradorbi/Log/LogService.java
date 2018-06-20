package br.com.itsstecnologia.integradorbi.Log;

import br.com.itsstecnologia.integradorbi.IntegradorbiApplication;

import java.io.FileInputStream;
import java.util.logging.*;

public class LogService extends Thread{

    private static Logger logger = Logger.getLogger(IntegradorbiApplication.class.getName());
    private Exception e;
    private Level level;

    public LogService(Exception exp, Level lvl) {
        this.e = exp;
        this.level = lvl;
        try {
            LogManager.getLogManager().readConfiguration(new FileInputStream("mylogging.properties"));
            logger.setLevel(Level.FINE);
            logger.addHandler(new ConsoleHandler());
            logger.addHandler(new MyHandler());
            //FileHandler file name with max size and number of log files limit
            Handler fileHandler = new FileHandler("C:\\temp\\logger.log", 2000, 5);
            fileHandler.setFormatter(new LogFormatter());
            //setting custom filter for FileHandler
            fileHandler.setFilter(new MyFilter());
            logger.addHandler(fileHandler);
        } catch (Exception e1) {
            e.printStackTrace();
            logger.log(Level.SEVERE, e1.getMessage());
        }
    }

    @Override
    public void run() {
       logger.log(level, e.getMessage());
    }
}
