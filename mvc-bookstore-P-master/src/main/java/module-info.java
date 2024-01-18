module com.example.kthimi {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.kthimi to javafx.fxml;
    exports com.example.kthimi;

    opens com.example.kthimi.Model to javafx.base;
}

