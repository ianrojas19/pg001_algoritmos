package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import model.Recursion;
import model.RecursionEngine;
import model.TreePainter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class MainController implements Initializable {

    @javafx.fxml.FXML
    private Canvas canvasTree;
    @javafx.fxml.FXML
    private Slider sliderFactN;
    @javafx.fxml.FXML
    private Label lblFactN;
    @javafx.fxml.FXML
    private Button btnFactReset;
    @javafx.fxml.FXML
    private Button btnFactCalc;
    @javafx.fxml.FXML
    private Label lblComplexity;
    @javafx.fxml.FXML
    private Label lblFactCalls;
    @javafx.fxml.FXML
    private Label lblFactResult;
    @javafx.fxml.FXML
    private ListView<String> listSteps; //lista de pasos recursivos
    @javafx.fxml.FXML
    private BarChart<String, Number> chartTime;
    @javafx.fxml.FXML
    private BarChart<String, Number> chartCalls;

    //atributos internos de la clase controller
    private final RecursionEngine engine = new RecursionEngine();
    private final TreePainter painter = new TreePainter();
    private RecursionEngine.CallNode lastRoot;
    private List<RecursionEngine.CallNode> factBFS;
    private final int[] testValues = {5, 10, 12, 15, 20};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupFactTab();
        setupBenchmarkCharts();
    }

    private void setupFactTab() {
        sliderFactN.setMin(1); sliderFactN.setMax(12); sliderFactN.setValue(5);
        sliderFactN.setMajorTickUnit(1); sliderFactN.setSnapToTicks(true);
        sliderFactN.valueProperty().addListener((observable, oldValue, newValue) -> {
            lblFactN.setText(String.valueOf(newValue.intValue()));
        });
        btnFactCalc.setOnAction(event -> runFactorial());
        btnFactReset.setOnAction(e -> resetFactTab());
    }

    private void resetFactTab() {
        lblFactResult.setText("-");
        lblFactCalls.setText("-");
        lblComplexity.setText("-");
        listSteps.getItems().clear();
    }

    private void runFactorial() {
        int n = (int) sliderFactN.getValue();
//        AtomicInteger counter = new AtomicInteger(0);
//        long result = Recursion.factorial(n, counter);
//        lblFactResult.setText(util.Utility.format(result));
//        lblFactCalls.setText(String.valueOf(counter));

        engine.computeFactorial(n);
        lastRoot = engine.getTreeRoot();
        factBFS = TreePainter.collectBFS(lastRoot);

        //llenamos la lista de pasos
        ObservableList<String> items = FXCollections.observableArrayList();
        for (int i = 0; i < engine.getSteps().size(); i++) {
            RecursionEngine.Step step = engine.getSteps().get(i);
            items.add(String.format("[%02d] %s", i+1, step.description));
        }
        listSteps.setItems(items); //setteamos la lista de pasos recursivos
        lblFactResult.setText(util.Utility.format(engine.getTreeRoot().result));
        lblFactCalls.setText(String.valueOf(engine.getCallCount()));
        lblComplexity.setText("O(n) = O(" + n + ") llamadas");

        //dibujamos el árbol de llamadas en el canva
        painter.paint(canvasTree, lastRoot, factBFS.size(), factBFS);
    }
    private void setupBenchmarkCharts() {

        if (chartTime == null || chartCalls == null)
            return;

        chartTime.getData().clear();
        chartCalls.getData().clear();

        XYChart.Series<String, Number> seriesArrayTime = new XYChart.Series<>();
        XYChart.Series<String, Number> seriesMapTime = new XYChart.Series<>();
        XYChart.Series<String, Number> seriesRecTime = new XYChart.Series<>();

        seriesArrayTime.setName("Fib Memo Array");
        seriesMapTime.setName("Fib Memo HashMap");
        seriesRecTime.setName("Fib Recursivo");

        XYChart.Series<String, Number> seriesArrayCalls = new XYChart.Series<>();
        XYChart.Series<String, Number> seriesMapCalls = new XYChart.Series<>();
        XYChart.Series<String, Number> seriesRecCalls = new XYChart.Series<>();

        seriesArrayCalls.setName("Fib Memo Array");
        seriesMapCalls.setName("Fib Memo HashMap");
        seriesRecCalls.setName("Fib Recursivo");

        for (int n : testValues) {

            // memo array
            AtomicInteger cArray = new AtomicInteger();
            long[] memo = new long[n + 1];
            java.util.Arrays.fill(memo, -1);

            long t0 = System.nanoTime();
            Recursion.fibMemoArray(n, memo, cArray);
            long t1 = System.nanoTime();

            double tArray = (t1 - t0) / 1_000_000.0;


            // memo hashmap
            AtomicInteger cMap = new AtomicInteger();
            java.util.Map<Integer, Long> map = new java.util.HashMap<>();

            long t2 = System.nanoTime();
            Recursion.fibMemo(n, map, cMap);
            long t3 = System.nanoTime();

            double tMap = (t3 - t2) / 1_000_000.0;


            // recursividad simple
            AtomicInteger cRec = new AtomicInteger();

            long t4 = System.nanoTime();
            Recursion.fibonacci(n, cRec);
            long t5 = System.nanoTime();

            double tRec = (t5 - t4) / 1_000_000.0;


            // agregar valores al grafico
            seriesArrayTime.getData().add(new XYChart.Data<>(String.valueOf(n), tArray));
            seriesMapTime.getData().add(new XYChart.Data<>(String.valueOf(n), tMap));
            seriesRecTime.getData().add(new XYChart.Data<>(String.valueOf(n), tRec));

            seriesArrayCalls.getData().add(new XYChart.Data<>(String.valueOf(n), cArray.get()));
            seriesMapCalls.getData().add(new XYChart.Data<>(String.valueOf(n), cMap.get()));
            seriesRecCalls.getData().add(new XYChart.Data<>(String.valueOf(n), cRec.get()));
        }

        // mostrar en el grafico
        chartTime.getData().addAll(seriesArrayTime, seriesMapTime, seriesRecTime);
        chartCalls.getData().addAll(seriesArrayCalls, seriesMapCalls, seriesRecCalls);
    }
}
