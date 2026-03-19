module ucr.lab.pg001 {
    requires javafx.controls;
    requires javafx.fxml;


    opens ucr.lab.pg001 to javafx.fxml;
    exports ucr.lab.pg001;
    exports controller;
    opens controller to javafx.fxml;
}