package Main;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.time.LocalDate;

public class Student {

    private String name;
    private String surname;
    private int group;

    private final ObservableMap<LocalDate, Boolean> attendance = FXCollections.observableHashMap();

    public Student(String name, String surname, int group){
        this.name = name;
        this.surname = surname;
        this.group = group;
    }

    public void updateValues(String name, String surname, int group){
        this.name = name;
        this.surname = surname;
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public String getSurname(){
        return surname;
    }

    public int getGroup(){
        return group;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname){
        this.surname = surname;
    }

    public void setGroup(int group){
        this.group = group;
    }

    public ObservableMap<LocalDate, Boolean> getAttendance() {
        return attendance;
    }

    public void setAttendance(boolean attended, LocalDate localDate) {
        attendance.put(localDate, attended);
    }

    public StringProperty getAttendanceOnDay(LocalDate date) {
        if (this.attendance.get(date) == null) {
            return new SimpleStringProperty("-");
        } else if (!this.attendance.get(date)) {
            return new SimpleStringProperty("Absent");
        } else if (this.attendance.get(date)){
            return new SimpleStringProperty("Present");
        }

        return null;
    }
}
