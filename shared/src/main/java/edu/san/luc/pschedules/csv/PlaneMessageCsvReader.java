package edu.san.luc.pschedules.csv;

import edu.san.luc.pschedules.PlaneMessageReader;
import edu.san.luc.pschedules.message.PlaneMessage;

import java.io.File;
import java.util.List;

/**
 * Created by sanya on 07.10.14.
 */
public class PlaneMessageCsvReader extends GenericCsvReader implements PlaneMessageReader{
    public List<PlaneMessage> read(File file){
        return this.read(PlaneMessage.class, file);
    }
}
