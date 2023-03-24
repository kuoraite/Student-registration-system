package Main;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class IOCsv implements FileIO{

    public void saveToFile(TableView tableView) throws IOException {

        File file = new File("Students.csv");

        try {
            FileWriter outputFile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(outputFile);

            List<String[]> data = new ArrayList<String[]>();
            ObservableList<Student> students = tableView.getItems();
            for (Student st: students){
                int group = st.getGroup();
                data.add(new String[]{st.getName(), st.getSurname(), Integer.toString(group)});
            }

            writer.writeAll(data);
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFile(TableView tableView) throws FileNotFoundException {
        CSVReader reader = null;
        try
        {
            reader = new CSVReader(new FileReader("C:\\Users\\kuora\\Desktop\\lalalla\\Lab3\\src\\main\\resources\\students.csv"), ';');
            String [] nextLine;
            while ((nextLine = reader.readNext()) != null)
            {
                String name = nextLine[0];
                String surname = nextLine[1];
                int group = parseInt(nextLine[2]);
                
                Student student = new Student(name, surname, group);
                Main.students = tableView.getItems();
                Main.students.add(student);
                tableView.setItems(Main.students);

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
