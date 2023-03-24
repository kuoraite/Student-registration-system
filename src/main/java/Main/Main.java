package Main;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

public class Main extends Application {

    private Student selectedStudent;
    public static ObservableList students;
    public String name, surname;
    public int group;

    LocalDate from, to;

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Attendance report");
        stage.setResizable(false);

        //--------------------------------------------------

        TableView<Student> table = new TableView();

        table.setTranslateX(30);
        table.setTranslateY(60);

        TableColumn<Student, String> nameColumn = new TableColumn("Name");
        TableColumn<Student, String> surnameColumn = new TableColumn("Surname");
        TableColumn<Student, String> groupColumn = new TableColumn("Group");

        table.getColumns().addAll(nameColumn, surnameColumn, groupColumn);

        //------------------------------------------------

        Group root = new Group();
        Scene scene = new Scene(root, 750, 500);

        Label label_name = new Label("Student name: ");
        label_name.setTranslateX(340);
        label_name.setTranslateY(140);

        Label label_surname = new Label("Student last name: ");
        label_surname.setTranslateX(340);
        label_surname.setTranslateY(200);

        Label label_group = new Label("Group: ");
        label_group.setTranslateX(340);
        label_group.setTranslateY(260);

        root.getChildren().addAll(label_name, label_surname, label_group, table);

        TextField textField_name = new TextField();
        textField_name.setTranslateX(340);
        textField_name.setTranslateY(160);

        TextField textField_surname = new TextField();
        textField_surname.setTranslateX(340);
        textField_surname.setTranslateY(220);

        TextField textField_group = new TextField();
        textField_group.setTranslateX(340);
        textField_group.setTranslateY(280);

        root.getChildren().addAll(textField_name, textField_surname, textField_group);

        //---------------------------------------------------------------------------

        Button getFromExcel = new Button("Get data from Excel");
        getFromExcel.setTranslateX(30);
        getFromExcel.setTranslateY(20);

        Button getFromCSV = new Button("Get data from CSV");
        getFromCSV.setTranslateX(160);
        getFromCSV.setTranslateY(20);

        Button confirm = new Button("Apply");
        confirm.setTranslateX(340);
        confirm.setTranslateY(60);

        Button add = new Button("Add student");
        add.setTranslateX(520);
        add.setTranslateY(240);

        Button toExcel = new Button("Export data to excel");
        toExcel.setTranslateX(340);
        toExcel.setTranslateY(360);

        Button toCSV = new Button("Export data to CSV");
        toCSV.setTranslateX(480);
        toCSV.setTranslateY(360);

        Button getAttendance = new Button("Get attendance");
        getAttendance.setTranslateX(340);
        getAttendance.setTranslateY(420);

        Button change = new Button("Change student");
        change.setTranslateX(520);
        change.setTranslateY(280);

        DatePicker datePicker = new DatePicker();
        datePicker.setTranslateX(340);
        datePicker.setTranslateY(20);

        ChoiceBox attendance = new ChoiceBox();
        attendance.setTranslateX(530);
        attendance.setTranslateY(20);
        attendance.getItems().addAll("Present", "Absent");
        attendance.getSelectionModel().select(0);

        root.getChildren().addAll(datePicker, getFromExcel, getFromCSV, confirm, add, change, toExcel, toCSV, attendance, getAttendance);

        confirm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(table.getSelectionModel().getSelectedItem() != null){
                    Student selectedStudent = table.getSelectionModel().getSelectedItem();

                    if(attendance.getValue() == "Present"){
                        selectedStudent.setAttendance(true, datePicker.getValue());
                    }
                    else if(attendance.getValue() == "Absent"){
                        selectedStudent.setAttendance(false, datePicker.getValue());
                    }
                }
                else{
                    System.out.println("You didn't select the student");
                }
            }
        });

        getFromExcel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    new IOExcel().loadFile(table);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }

        });

        getFromCSV.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    new IOCsv().loadFile(table);
                } catch (FileNotFoundException e){
                    throw new RuntimeException(e);
                }
            }
        });

        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                name = textField_name.getText();
                surname = textField_surname.getText();
                group = Integer.parseInt(textField_group.getText());

                nameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
                surnameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("surname"));
                groupColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("group"));

                Student student = new Student(name, surname, group);

                students = table.getItems();
                students.add(student);
                table.setItems(students);
            }
        });

        change.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                if(table.getSelectionModel().getSelectedItem() != null){
                    Student student = table.getSelectionModel().getSelectedItem();

                    name = textField_name.getText();
                    surname = textField_surname.getText();
                    group = Integer.parseInt(textField_group.getText());

                    student.updateValues(name, surname, group);
                    table.refresh();
                }
                else{
                    System.out.println("The student wasn't selected");
                }
            }
        });

        toExcel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    new IOExcel().saveToFile(table);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        toCSV.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    new IOCsv().saveToFile(table);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        stage.setScene(scene);
        stage.show();

        getAttendance.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage2 = new Stage();
                Group root = new Group();
                Scene attend = new Scene(root, 250, 150);

                Label label = new Label("Choose the time period: ");

                DatePicker dateFrom = new DatePicker();
                dateFrom.setTranslateY(30);

                DatePicker dateTo = new DatePicker();
                dateTo.setTranslateY(60);

                Button button = new Button("Confirm");
                button.setTranslateY(90);

                root.getChildren().addAll(label, dateFrom, dateTo, button);

                stage2.setScene(attend);
                stage2.show();

                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        if(dateFrom.getValue() != null && dateTo.getValue() != null){
                            from = dateFrom.getValue();
                            to = dateTo.getValue();
                        }
                        OutPDF.saveToFile(students, from, to);
                    }
                });
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}