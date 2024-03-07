module sio.tp5 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    opens sio.tp5.Tools;
    opens sio.tp5 to javafx.fxml;
    exports sio.tp5;
}