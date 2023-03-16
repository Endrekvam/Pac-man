module com.example.pacmanoblig {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.pacmanoblig to javafx.fxml;
    exports com.example.pacmanoblig;
}