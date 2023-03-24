module com.example.lab3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;

    requires opencsv;
    requires itextpdf;

    opens Main to javafx.fxml;
    exports Main;
}