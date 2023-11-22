package logger;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import java.io.File;

public class DBLogger implements ServletContextListener {
    private static final Logger logger = LogManager.getLogger(DBLogger.class);
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Specify the path to the log file
        String logFilePath = sce.getServletContext().getRealPath(File.separator) + File.separator +"logs" + File.separator + "dbConnectionLogs.txt";
        System.out.println(logFilePath);

        // Configure Log4j to output to a file
        Configurator.initialize(null, "log4j2.xml"); // Use your configuration file name

        // You can log an initial message if needed
        logger.info("Log4j initialized. Log file path: " + logFilePath);
    }
}
