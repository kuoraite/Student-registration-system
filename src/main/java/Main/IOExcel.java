package Main;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class IOExcel implements FileIO {

    @Override
    public void saveToFile(TableView tableView) throws IOException{
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("students.xlsx");
        Map<Integer, Object[]> data = new TreeMap<Integer, Object[]>();

        ObservableList<Student> students = tableView.getItems();

        int i = 0;
        for (Student person : students) {
            data.put(i, new Object[]{person.getName(), person.getSurname(),
                    person.getGroup()});
            i++;
        }

        Set<Integer> keyset = data.keySet();
        int rownum = 0;
        for (Integer key : keyset) {

            Row row = sheet.createRow(rownum++);
            Object[] objArr = data.get(key);
            int cellnum = 0;

            for (Object obj : objArr) {
                Cell cell = row.createCell(cellnum++);

                if (obj instanceof String)
                    cell.setCellValue((String) obj);

                else if (obj instanceof Integer)
                    cell.setCellValue((Integer) obj);
            }
        }

        try {
            FileOutputStream out = new FileOutputStream(new File("students.xlsx"));
            workbook.write(out);
            out.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadFile(TableView tableView)throws FileNotFoundException {
        FileInputStream file = null;
        file = new FileInputStream("C:\\Users\\kuora\\Desktop\\lalalla\\Lab3\\src\\main\\resources\\students.xlsx");
        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        XSSFSheet sheet = workbook.getSheetAt(0);

        Iterator<Row> itr = sheet.iterator();
        while (itr.hasNext()) {
            Row row = itr.next();
            Iterator<Cell> cellIterator = row.cellIterator();

            Cell cell = cellIterator.next();
            String name = cell.getStringCellValue();

            cell = cellIterator.next();
            String surname = cell.getStringCellValue();

            cell = cellIterator.next();
            int group = (int) cell.getNumericCellValue();

            Student student = new Student(name, surname, group);

            student.updateValues(name, surname, group);

            Main.students = tableView.getItems();
            Main.students.add(student);
            tableView.setItems(Main.students);
        }
    }
}
