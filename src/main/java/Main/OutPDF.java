package Main;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.List;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class OutPDF {
    public static void saveToFile(ObservableList<Student> students, LocalDate from, LocalDate to){
        Document document = new Document();
        try {
            OutputStream outputStream = new FileOutputStream(new File("Students.pdf"));
            PdfWriter.getInstance(document, outputStream);
            document.open();

            PdfPTable table = new PdfPTable(1);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(105);

            for (Student st: students){
                Paragraph paragraph = new Paragraph();
                paragraph.add(st.getName() + " " + st.getSurname() + " " + st.getGroup() + ": \n");

                List dateList = new List();
                for(LocalDate temp = from; !temp.isAfter(to); temp = temp.plusDays(1)) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    dateList.add(formatter.format(temp) + ": " + st.getAttendanceOnDay(temp).get() + " ");
                }

                paragraph.add(dateList);

                PdfPCell cell = new PdfPCell(paragraph);
                cell.setBorder(3);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);
            }
            document.add(table);

            document.close();
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
