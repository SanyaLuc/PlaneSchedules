package edu.san.luc.pschedules;

import edu.san.luc.pschedules.message.PlaneMessage;

import java.io.File;
import java.util.List;

/**
 * Created by sanya on 07.10.14.
 */
public interface PlaneMessageReader {
    List<PlaneMessage> read(File file);
}
