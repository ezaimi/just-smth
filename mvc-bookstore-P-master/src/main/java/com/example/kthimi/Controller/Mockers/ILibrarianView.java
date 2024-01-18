package com.example.kthimi.Controller.Mockers;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public interface ILibrarianView {
    ComboBox<String> getComboBoxLibrarian();
    TextField getQuantityField();
    Button getBttAdd();
    TextField getWarningsLibrarian();
    BorderPane getLibrarianMainPage();
    // Other necessary methods to mock
}
