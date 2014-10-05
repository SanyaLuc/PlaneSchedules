package edu.san.luc.pschedules.message;

import org.jsefa.csv.annotation.CsvDataType;
import org.jsefa.csv.annotation.CsvField;

import java.io.Serializable;

/**
 * Created by sanya on 16.08.14.
 */
@CsvDataType
public class PlaneMessage implements Serializable{
    public static final String MESSAGE_COUNT = "messageCount";

    @CsvField(pos = 5)
    private String id;

    @CsvField(pos = 0)
    private String name;

    @CsvField(pos = 1)
    private String date;

    @CsvField(pos = 2)
    private String time;

    @CsvField(pos = 3)
    private String departureFrom;

    @CsvField(pos = 4)
    private String arrivalTo;

    private Integer messageCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDepartureFrom() {
        return departureFrom;
    }

    public void setDepartureFrom(String departureFrom) {
        this.departureFrom = departureFrom;
    }

    public String getArrivalTo() {
        return arrivalTo;
    }

    public void setArrivalTo(String arrivalTo) {
        this.arrivalTo = arrivalTo;
    }

    public Integer getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(Integer messageCount) {
        this.messageCount = messageCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlaneMessage that = (PlaneMessage) o;

        if (!arrivalTo.equals(that.arrivalTo)) return false;
        if (!date.equals(that.date)) return false;
        if (!departureFrom.equals(that.departureFrom)) return false;
        if (!id.equals(that.id)) return false;
        if (!name.equals(that.name)) return false;
        if (!time.equals(that.time)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        if(id != null){
            int result = id.hashCode();
            result = 31 * result + name.hashCode();
            result = 31 * result + date.hashCode();
            result = 31 * result + time.hashCode();
            result = 31 * result + departureFrom.hashCode();
            result = 31 * result + arrivalTo.hashCode();
            return result;
        } else {
            return -1;
        }
    }

    @Override
    public String toString() {
        return "PlaneMessage{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", departureFrom='" + departureFrom + '\'' +
                ", arrivalTo='" + arrivalTo + '\'' +
                ", messageCount=" + messageCount +
                '}';
    }
}
