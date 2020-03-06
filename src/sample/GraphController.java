package sample;

import com.jfoenix.controls.JFXListView;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.cloudbus.cloudsim.Vm;
import sample.Helpers.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class GraphController  extends Base{


    @FXML
    private JFXListView<VBox> graphListView;
    public void initialize() {
        checkMaximize();
        navigationTollTips();

        loadListView();

    }

    int calUpperBound(ArrayList<Integer> yList) {
        int upperBoud = 0;
            /*Calculate upper bound*/
        for (Integer i : yList) {
            if (i > upperBoud)upperBoud = i;
        }
        System.out.println("Greates Value "+upperBoud);
        //count number of digits
        int count=0;
        for (int i=upperBoud;i>0;i/=10) {
            count++;
        }
        double c =  0.1*upperBoud;
        upperBoud+=c;
        System.out.printf("Increased Upper Bound " + upperBoud);
        return upperBoud;
    }

    private void loadListView() {


        List<Techniques> techniquesList = Bus.getInstance().getTechniquesList();

        for (Techniques t : techniquesList) {

            Label techLabel = getSegoiLabel(t.getTechName());

            ArrayList<String> xList = new ArrayList<>();
            xList.addAll(t.getCloudLetMapping().keySet());

            ArrayList<Integer> yList= new ArrayList<>();
            yList.addAll(t.getCloudLetMapping().values());



            ArrayList<String> xListVmLoad = new ArrayList<>();
            xListVmLoad.addAll(t.getVmLoadMap().keySet());
            ArrayList<Integer> yListVmLoad = new ArrayList<>();
            yListVmLoad.addAll(t.getVmLoadMap().values());


            /*Vm cofig table*/
            // vm[i] = new Vm(idShift + i, userId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());
            List<String> tableColumn = new ArrayList<>();
            /*tableColumn.add("VM ID");
            tableColumn.add("MIPS");
            tableColumn.add("P.NUM");
            tableColumn.add("RAM");
            tableColumn.add("BW");
            tableColumn.add("SIZE");
            tableColumn.add("VMM");*/

            tableColumn = VmDetails.getColumnNames();
            List<List<String>> row = new ArrayList<>();
            for (VmDetails vm: t.getVmDetails()) {
                row.add(vm.getDetailsInListFormat());
            }

            /*for (Vm vm: Bus.getInstance().getVmListGlobal()) {
                List<String> data = new ArrayList<>();
                data.add(String.valueOf(vm.getId()));
                data.add(String.valueOf(vm.getMips()));
                data.add(String.valueOf(vm.getNumberOfPes()));
                data.add(String.valueOf(vm.getRam()));
                data.add(String.valueOf(vm.getBw()));
                data.add(String.valueOf(vm.getSize()));
                data.add(String.valueOf(vm.getVmm()));

                row.add(data);

            }*/

            TableView<ObservableList<String>> tableView = new TableView<>();
            TableHelper.insertDataInColumn(tableView, tableColumn);
            TableHelper.insertDataInRow(tableView, row);

            tableView.setPrefHeight(300);
            tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            List<String> tempx = t.getOverUnderBalanceString();
            List<Integer> tempy = t.getOverUnderBalancedValue();
            ArrayList<String> overUnderBalancedXList = new ArrayList<>();
            overUnderBalancedXList.addAll(tempx);
            ArrayList<Integer> overUnderBalancedYList = new ArrayList<>();
            overUnderBalancedYList.addAll(tempy);

            BarChart<String, Integer> barChart = ChartHelper.getIntegerBarChart(xList, yList, "Virtual Machines", "Tasks", "Cloud", 0.0, calUpperBound(yList), 50);

            BarChart<String, Integer> vmLoadBarChart = ChartHelper.getIntegerBarChart(xListVmLoad, yListVmLoad, "Virtual Machines", "LOAD", "Cloud", 0.0, calUpperBound(yListVmLoad), 50);

            //BarChart<String, Integer> overUnderBalancedBarChart = ChartHelper.getIntegerBarChart(overUnderBalancedXList, overUnderBalancedYList, "Result", "Number Of Machine", "Cloud", 0.0, calUpperBound(overUnderBalancedYList), 50);
            PieChart overUnderBalancedPieChart = ChartHelper.getPieChart(overUnderBalancedXList, overUnderBalancedYList, "Balancing Result");
            //barChart.setPrefHeight(5000);

            barChart.setPrefHeight(500);
            vmLoadBarChart.setPrefHeight(500);
            VBox vbox = new VBox(techLabel, barChart, vmLoadBarChart, overUnderBalancedPieChart, tableView);
            vbox.setSpacing(10);
            //vbox.setPrefHeight(300);
            vbox.setPadding(new Insets(10));
            graphListView.getItems().add(vbox);
        }
        graphListView.setDepth(3);
        graphListView.setExpanded(true);

    }



}
