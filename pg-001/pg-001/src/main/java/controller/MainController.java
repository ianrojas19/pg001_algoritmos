package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import model.Recursion;
import model.RecursionEngine;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class MainController implements Initializable {
    @javafx.fxml.FXML
    private Canvas canvasTree;
    @javafx.fxml.FXML
    private Button btnFactReset;
    @javafx.fxml.FXML
    private Button btnFactCalc;
    @javafx.fxml.FXML
    private Label lblComplexity;
    @javafx.fxml.FXML
    private Slider sliderFactN;
    @javafx.fxml.FXML
    private ToggleGroup ops_facfib;
    @javafx.fxml.FXML
    private Label lblFactCalls;
    @javafx.fxml.FXML
    private Label lblFactN;
    @javafx.fxml.FXML
    private ListView<String> listSteps;
    @javafx.fxml.FXML
    private Label lblFactResult;

//    atributos internos de la clase controller
    private final RecursionEngine engine = new RecursionEngine();
    private RecursionEngine.CallNode lastRoot;
    private List<RecursionEngine.CallNode> factBFS;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupFacTab();
    }

    private void setupFacTab() {
        sliderFactN.setMin(1);
        sliderFactN.setMax(12);
        sliderFactN.setValue(5);
        sliderFactN.setMajorTickUnit(1);
        sliderFactN.setSnapToTicks(true);
        sliderFactN.valueProperty().addListener((
                observableValue,
                oldValue,
                newValue) -> {
            lblFactN.setText(String.valueOf(newValue.intValue()));
        });

        btnFactCalc.setOnAction(e -> runFactorial());
        btnFactReset.setOnAction(e -> resetFactTab());
    }

    private void runFactorial() {
        int n = (int) sliderFactN.getValue();
//        AtomicInteger counter = new AtomicInteger(0);
//        long result = Recursion.factorial(n, counter);
//        lblFactResult.setText(util.Utility.format(result));
//        lblFactCalls.setText(String.valueOf(counter));

        engine.computeFactorial(n);
        lastRoot = engine.getTreeRoot();
//                var chart parte graficos,

        ObservableList<String> items = FXCollections.observableArrayList();

        for (int i = 0; i < n; i++) {
            RecursionEngine.Step step = engine.getSteps().get(i);
            items.add(String.format("[%02d] %s", i + 1, step.description));
        }

        listSteps.setItems(items); //setteamos la lista de pasos recursivos
        lblFactResult.setText(util.Utility.format(engine.getTreeRoot().result));
        lblFactCalls.setText(util.Utility.format(engine.getCallCount()));
        lblComplexity.setText("O(n) = O(" + n + ") llamadas");
    }

    private void resetFactTab() {
        lblFactResult.setText("-");
        lblFactCalls.setText("-");
        lblComplexity.setText("-");
        listSteps.getItems().clear();
    }
}