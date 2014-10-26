package edu.san.luc.pschedules;

import java.io.File;
import java.util.List;

/**
 * Created by sanya on 07.10.14.
 */
public interface PlaneMessageWriter {
    public <T> boolean append(List<T> list, File file);

    public <T> boolean write(List<T> list, File file);
}
