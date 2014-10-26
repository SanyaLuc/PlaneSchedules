package edu.san.luc.pschedules.csv;

import org.jsefa.Deserializer;
import org.jsefa.csv.CsvIOFactory;
import org.jsefa.csv.config.CsvConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sanya on 19.07.14.
 */
public class GenericCsvReader {
    public static final char DEFAULT_DELIMITER = ',';

    private static final Logger logger = LoggerFactory.getLogger(GenericCsvReader.class);
    private final CsvConfiguration csvConfiguration;

    public GenericCsvReader() {
        csvConfiguration = new CsvConfiguration();
        csvConfiguration.setFieldDelimiter(DEFAULT_DELIMITER);
    }

    public <T> List<T> read(Class<T> clazz, File file) {
        List<T> list = new ArrayList<T>();

        try {
            Deserializer deserializer = CsvIOFactory.createFactory(csvConfiguration, clazz).createDeserializer();
            FileReader reader = new FileReader(file);
            deserializer.open(reader);

            while (deserializer.hasNext()) {
                T obj = deserializer.next();
                list.add(obj);
            }

            deserializer.close(true);
        } catch (FileNotFoundException e) {
            logger.error("Can not find csv file: " + file.getAbsolutePath(), e);
        }

        return list;
    }
}
