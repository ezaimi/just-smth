package com.example.kthimi.View;

import com.example.kthimi.Controller.MainController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainView {


    TextField mainLoginWarning = new TextField();
    TextField username = new TextField();
    Text textUsername = new Text("Username");
    PasswordField password = new PasswordField();
    Text textPassword = new Text("Password");
    Text textSystem = new Text("System");
    Button bttSubmit = new Button("Submit");
    private MainController controller;
    BorderPane mainPage;
    private Stage primaryStage;


    public MainView() {
        mainPage = mainPage();
    }


    public void setController(MainController controller) {
        this.controller = controller;
        initSubmitButtonAction();
    }

    private void initSubmitButtonAction() {
        Button bttSubmit = getBttSubmit();
        bttSubmit.setOnAction(event -> controller.handleLogin());
    }




    public Button getBttSubmit() {
        return bttSubmit;
    }

    public TextField getUsername() {
        return username;
    }

    public TextField getPassword() {
        return password;
    }

    public TextField getMainLoginWarning() {
        return mainLoginWarning;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }


    public void clearFields() {
        username.clear();
        password.clear();
        mainLoginWarning.clear();
    }


    public BorderPane mainPage() {
        bttSubmit.setId("Submit");
        BorderPane border = new BorderPane();
        border.setMinSize(500,300);

        StackPane stackText = new StackPane();
        Text text = new Text("Welcome");
        text.setFont(new Font(30));
        stackText.getChildren().add(text);
        stackText.setPadding(new Insets(20));
        border.setTop(stackText);

        mainLoginWarning.setEditable(false);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.add(textUsername,0,1);
        grid.add(username,1,1);
        grid.add(textPassword,0,2);
        grid.add(password,1,2);
        grid.add(textSystem, 0, 5);
        grid.add(mainLoginWarning, 1, 5);
        grid.add(bttSubmit, 6, 6);
        border.setCenter(grid);


        //Funksioni per event handler te butonit submit tek main page
        // setSubmitAction();


        return border;
    }

}
