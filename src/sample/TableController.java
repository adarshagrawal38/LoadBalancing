package sample;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import sample.Helpers.Bus;
import sample.Helpers.TableHelper;

import java.util.ArrayList;
import java.util.List;

public class TableController extends Base {


    @FXML
    private JFXComboBox<String> tech_cmb;

    @FXML
    private TableView<ObservableList<String>> tableView;

    public void initialize() {
        checkMaximize();
        navigationTollTips();


        System.out.printf("Hello "+ Bus.getInstance().getTechniquesList().size());
        initTechComboBox();

        List<String> columnData = new ArrayList<>();
        columnData.add("Technique Name");
        columnData.add("OverLoaded");
        columnData.add("UnderLoaded");
        columnData.add("Balanced");

        List<List<String>> rows = new ArrayList<>();
        /*Display techniue name overloaded unnderloaded balanced*/
        List<Techniques> techniquesList = Bus.getInstance().getTechniquesList();
        for (Techniques techniques: techniquesList) {
            List<String> row = new ArrayList<>();
            row.add(techniques.getTechName());
            for (Integer s: techniques.getOverUnderBalancedValue())
                row.add(String.valueOf(s));

            rows.add(row);

        }
        TableHelper.insertDataInColumn(tableView, columnData);
        TableHelper.insertDataInRow(tableView, rows);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tech_cmb.setVisible(false);

    }
    protected void initTechComboBox() {

        for (Techniques techniques: Bus.getInstance().getTechniquesList()) {
            tech_cmb.getItems().add(techniques.getTechName());
        }
        tech_cmb.getSelectionModel().select(0);

    }
    @FXML
    void getLogData(ActionEvent event) {

        int index = tech_cmb.getSelectionModel().getSelectedIndex();
        Techniques techniques = Bus.getInstance().getTechniquesList().get(index);
        TableHelper.insertDataInColumn(tableView, techniques.getCloudHeaders());
        TableHelper.insertDataInRow(tableView, techniques.getCloudLogs());
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

}
