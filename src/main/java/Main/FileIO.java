package Main;

import javafx.scene.control.TableView;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileIO {
    void saveToFile(TableView tableView) throws IOException;
    void loadFile(TableView tableView) throws FileNotFoundException;
}
