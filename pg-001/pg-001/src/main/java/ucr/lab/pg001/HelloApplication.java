package ucr.lab.pg001;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
//        start1(stage);
        start2(stage);
    }

    private void start2(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main.fxml"));
        stage.setTitle("PG-01 - IF-3001 Algoritmos y Estructuras de Datos!");
        Scene scene = new Scene(fxmlLoader.load(), 1100, 720);
        scene.getStylesheets().add(HelloApplication.class.getResource("styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void start1(Stage stage) throws IOException {
        TextField name = new TextField("1000");

        Label result = new Label("");

        Button button = new Button("Medir O(n) vs O(n^2)");

        button.setOnAction(event -> {
            int n = Integer.parseInt(name.getText());
            /*
             * Ahora se va a determinar el tiempo de ejecucion de un algoritmo
             */

            long t1 = System.nanoTime();
            long sum = 0;
            for (int i = 0; i < n; i++) {
                sum = +i; //O(n)
            }
            long t2 = System.nanoTime();

            result.setText("O(n): " + util.Utility.format((t2 - t1)) + "ns");
        });

        Scene scene = new Scene(new VBox(20, name, button, result), 800, 600);

    }

    public static void main(String[] args) {
        launch();
    }
}