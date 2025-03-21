module com.example.test {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires spring.context;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires jakarta.persistence;
    requires static lombok;
    requires spring.data.jpa;

    opens com.example.test to javafx.fxml;
    exports com.example.test;
}