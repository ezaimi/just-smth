package com.example.kthimi.View.Admin;

import com.example.kthimi.Controller.LibrarianFuncController;
import com.example.kthimi.Model.AdministratorModel;
import com.example.kthimi.Model.ManagerModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ManageManAddNew {

    ManageManagersView manageManagersView;
    Button bttBack = new Button("Back");
    Text textSystem = new Text("System");
    ManagerModel manager;

    public ManageManAddNew(ManageManagersView manageManagersView){
        this.manageManagersView = manageManagersView;

    }


    public BorderPane addManager() {


        BorderPane border = new BorderPane();

        Text text = new Text("Add new Manager");
        StackPane stack = new StackPane();
        text.setFont(new Font(30));
        stack.getChildren().add(text);
        stack.setPadding(new Insets(20));
        border.setTop(stack);

        TextField name = new TextField();
        Text textName = new Text("Name");
        TextField salary = new TextField();
        Text textSalary = new Text("Salary");
        TextField phone = new TextField();
        Text textPhone = new Text("Phone");
        TextField email = new TextField();
        Text textEmail = new Text("Email");
        TextField username = new TextField();
        Text textUsername = new Text("Username");
        TextField password = new TextField();
        Text textPassword = new Text("Password");
        Button bttAdd = new Button("Submit");
        Button bttBack = new Button("Back");
        TextField magWarningNew = new TextField();

        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setAlignment(Pos.CENTER);
        grid.add(textName,0,0);
        grid.add(name,1,0);
        grid.add(textUsername,0,1);
        grid.add(username,1,1);
        grid.add(textPassword,0,2);
        grid.add(password, 1, 2);
        grid.add(textSalary, 0, 3);
        grid.add(salary, 1, 3);
        grid.add(textPhone, 0, 4);
        grid.add(phone, 1, 4);
        grid.add(textEmail, 0, 5);
        grid.add(email, 1, 5);
        grid.add(textSystem, 0, 8);
        grid.add(magWarningNew, 1, 8);
        grid.add(bttAdd, 3, 9);
        grid.add(bttBack, 0, 9);
        border.setCenter(grid);

        bttBack.setOnAction(event -> {
            bttBack.getScene().setRoot( manageManagersView.administratorManagerPage());
        });

        bttAdd.setOnAction(event -> {

            if (password.getCharacters().isEmpty() || username.getCharacters().isEmpty() || salary.getCharacters().isEmpty() || phone.getCharacters().isEmpty() || email.getCharacters().isEmpty() || name.getCharacters().isEmpty()) {
                magWarningNew.setText("Failed, Empty Fields!");
                return;
            }

            if (!(LibrarianFuncController.checkName(name.getCharacters().toString()))) {
                magWarningNew.setText("Invalid Name");
                name.clear();
                return;
            }

            if (!(LibrarianFuncController.checkEmail(email.getCharacters().toString()))) {
                magWarningNew.setText("Invalid email");
                email.clear();
                return;
            }

            if (!(LibrarianFuncController.checkPassword(password.getCharacters().toString()))) {
                magWarningNew.setText("Invalid password");
                password.clear();
                return;
            }

            if (!(LibrarianFuncController.checkSalary(salary.getCharacters().toString()))) {
                magWarningNew.setText("Invalid salary");
                salary.clear();
                return;
            }
            if (!(LibrarianFuncController.checkPhone(phone.getCharacters().toString()))) {
                magWarningNew.setText("Invalid phone");
                phone.clear();
                return;
            }


            manager = new ManagerModel( username.getCharacters().toString(), password.getCharacters().toString(), name.getCharacters().toString(), Double.parseDouble(salary.getCharacters().toString()), phone.getCharacters().toString(),
                    email.getCharacters().toString());

            AdministratorModel.AddManager(manager);
            magWarningNew.setText("Succes!");

            username.clear();
            name.clear();
            password.clear();
            salary.clear();
            phone.clear();
            email.clear();

        });

        name.setId("name");
        password.setId("password");
        username.setId("username");
        salary.setId("salary");
        phone.setId("phone");
        email.setId("email");

        magWarningNew.setId("magWarningNew");
        bttAdd.setId("bttAdd");

        return border;



    }


}
