package edu.san.luc.pschedules.csv;

import edu.san.luc.pschedules.PlaneMessageWriter;
import org.jsefa.Serializer;
import org.jsefa.csv.CsvIOFactory;
import org.jsefa.csv.config.CsvConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

/**
 * Created by sanya on 19.07.14.
 */
public class GenericCsvWriter{
    public static final char DEFAULT_DELIMITER = ',';

    private static final Logger logger = LoggerFactory.getLogger(GenericCsvWriter.class);
    private final CsvConfiguration csvConfiguration;

    public GenericCsvWriter() {
        csvConfiguration = new CsvConfiguration();
        csvConfiguration.setFieldDelimiter(DEFAULT_DELIMITER);
    }

    public <T> boolean append(List<T> list, File file) {
        return write(list, file, true);
    }

    public <T> boolean write(List<T> list, File file) {
        return write(list, file, false);
    }

    private <T> boolean write(List<T> list, File file, boolean append) {
        boolean result = false;

        if(list != null && !list.isEmpty()){
            Serializer serializer = null;
            try {
                serializer = CsvIOFactory.createFactory(csvConfiguration, list.get(0).getClass()).createSerializer();
                FileWriter writer = new FileWriter(file, append);
                serializer.open(writer);

                for (T t : list) {
                    serializer.write(t);
                }

                result = true;
            } catch (Exception e) {
                logger.error("Can not write csv file: " + file.getAbsolutePath(), e);
            } finally {
                if(serializer != null) {
                    serializer.flush();
                    serializer.close(true);
                }
            }
        }

        return result;
    }
}
