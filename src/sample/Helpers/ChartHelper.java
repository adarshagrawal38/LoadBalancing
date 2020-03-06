package sample.Helpers;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.*;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ChartHelper {

    static int index = 0, index2 = 0;


    public static BarChart getIntegerBarChart(ArrayList<String> xArrayList, ArrayList<Integer> yArrayList, String xLabel, String yLabel, String seriesLabel, double lowerBound, double upperBound, double unitTick) {

        CategoryAxis x = new CategoryAxis();
        x.setCategories(FXCollections.<String>observableArrayList(xArrayList));
        x.setLabel(xLabel);

        NumberAxis y = new NumberAxis(lowerBound, upperBound, unitTick);
        y.setLabel(yLabel);

        BarChart<String, Number> barChart = new BarChart<>(x, y);

        // Series for response
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName(seriesLabel);
        for (int i = 0; i < yArrayList.size(); i++) {
            final XYChart.Data<String, Number> data = new XYChart.Data(xArrayList.get(i), yArrayList.get(i));
            data.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override
                public void changed(ObservableValue<? extends Node> observable, Node oldValue, Node newValue) {
                    if (newValue != null) {
                        displayLabelForData(data);
                    }
                }
            });
            series1.getData().add(data);
        }

        barChart.getData().addAll(series1);
        double prefWidth = (yArrayList.size() * 100.0) * (1.0 / 2.0);
        barChart.setPrefHeight(Constants.INTEGER_BAR_CHART_HEIGHT);
        barChart.setPrefWidth(prefWidth);
        //barChart.setStyle("-fx-background-color: #3498db");

        return barChart;
    }

    public static PieChart getPieChart(ArrayList<String> itemNameList, ArrayList<Integer> itemValueList, String title) {

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (int i=0;i<itemNameList.size();i++) {
            if (itemValueList.get(i)==0)
                continue;
            pieChartData.add(new PieChart.Data(itemNameList.get(i),  itemValueList.get(i)));
        }
        PieChart chart = new PieChart(pieChartData);
        chart.setPrefHeight(500);
        chart.setPrefHeight(500);
        chart.setTitle(title);

        return chart;
    }



    private static void displayLabelForData(XYChart.Data<String, Number> data) {
        final Node node = data.getNode();

        final Text dataText = new Text(data.getYValue() + " ");

        node.parentProperty().addListener(new ChangeListener<Parent>() {
            @Override
            public void changed(ObservableValue<? extends Parent> observable, Parent oldValue, Parent newValue) {

                Group parentGroup = (Group) newValue;
                parentGroup.getChildren().add(dataText);
            }
        });

        node.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
                dataText.setLayoutX(Math.round(newValue.getMinX() + newValue.getWidth() / 2 - dataText.prefWidth(-1) / 2));
                long y = Math.round(newValue.getMinY() - dataText.prefHeight(-1) * 0.5);
                if (dataText.getText().charAt(0) == '-') {
                    y = Math.round(newValue.getMaxY() + dataText.prefHeight(-1));
                }
                dataText.setLayoutY(y);
            }
        });

    }

    private static void displayLabelForScalledChartData(XYChart.Data<String, Number> data, String text) {
        final Node node = data.getNode();

        final Text dataText = new Text(text);

        node.parentProperty().addListener(new ChangeListener<Parent>() {
            @Override
            public void changed(ObservableValue<? extends Parent> observable, Parent oldValue, Parent newValue) {

                Group parentGroup = (Group) newValue;
                parentGroup.getChildren().add(dataText);
            }
        });

        node.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
                dataText.setLayoutX(Math.round(newValue.getMinX() + newValue.getWidth() / 2 - dataText.prefWidth(-1) / 2));
                long y = Math.round(newValue.getMinY());
                if (text.charAt(0) == '-') {
                    // getting y coordinates in case of minus sign
                    y = Math.round(newValue.getMaxY() + (newValue.getWidth() - 40) / 2);
                }
                dataText.setLayoutY(y);
            }
        });


    }

    private static ArrayList<Double> scalingBar(ArrayList<Integer> response) {

        int max = Collections.max(response);
        max += 1;

        ArrayList<Double> list = new ArrayList<>();

        for (int i = 0; i < response.size(); i++) {

            double d = (double) response.get(i) / max;
            list.add(d);

        }

        return list;
    }
    public static double calculateRelativePositionInChart(double upperBoundInAxis, double lengthOfAxis, double value) {
        //Remove space required by number labels Axis
        lengthOfAxis -= Constants.LABEL_SPACE_IN_AXIS;
        double calPos = (value * lengthOfAxis) / upperBoundInAxis;
        return calPos;
    }



}
