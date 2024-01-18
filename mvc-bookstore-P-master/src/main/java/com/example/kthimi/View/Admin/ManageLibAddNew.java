package com.example.kthimi.View.Admin;

import com.example.kthimi.Controller.LibrarianFuncController;
import com.example.kthimi.Model.LibrarianModel;
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

public class ManageLibAddNew {

    ManageLibrariansView manageLibrariansView;
    Button bttBack = new Button("Back");
    LibrarianModel librarian;
    Text textSystem = new Text("System");

    public ManageLibAddNew(ManageLibrariansView manageLibrariansView){
        this.manageLibrariansView = manageLibrariansView;


    }


    public BorderPane addLibrarian() {

        BorderPane border = new BorderPane();

        Text text = new Text("Add new Librarian");
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
        TextField libWarningNew = new TextField();

        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setAlignment(Pos.CENTER);
        grid.add(textName,0,0);
        grid.add(name,1,0);
        grid.add(textPassword,0,1);
        grid.add(password,1,1);
        grid.add(textUsername,0,2);
        grid.add(username, 1, 2);
        grid.add(textSalary, 0, 3);
        grid.add(salary, 1, 3);
        grid.add(textPhone, 0, 4);
        grid.add(phone, 1, 4);
        grid.add(textEmail, 0, 5);
        grid.add(email, 1, 5);
        grid.add(textSystem, 0, 8);
        grid.add(libWarningNew, 1, 8);
        grid.add(bttAdd, 3, 9);
        grid.add(bttBack, 0, 9);
        border.setCenter(grid);

        bttBack.setOnAction(event -> {
            bttBack.getScene().setRoot(manageLibrariansView.administratorManageLibrariansPage());
        });

        bttAdd.setOnAction(event -> {

            if (password.getCharacters().isEmpty() || username.getCharacters().isEmpty() || salary.getCharacters().isEmpty() || phone.getCharacters().isEmpty() || email.getCharacters().isEmpty() || name.getCharacters().isEmpty()) {
                libWarningNew.setText("Failed, Empty Fields!");
                return;
            }

            if (!(LibrarianFuncController.checkName(name.getCharacters().toString()))) {
                libWarningNew.setText("Invalid Name");
                name.clear();
                return;
            }

            if (!(LibrarianFuncController.checkEmail(email.getCharacters().toString()))) {
                libWarningNew.setText("Invalid email");
                email.clear();
                return;
            }

            if (!(LibrarianFuncController.checkPassword(password.getCharacters().toString()))) {
                libWarningNew.setText("Invalid password");
                password.clear();
                return;
            }

            if (!(LibrarianFuncController.checkSalary(salary.getCharacters().toString()))) {
                libWarningNew.setText("Invalid salary");
                salary.clear();
                return;
            }
            if (!(LibrarianFuncController.checkPhone(phone.getCharacters().toString()))) {
                libWarningNew.setText("Invalid phone");
                phone.clear();
                return;
            }


            librarian = new LibrarianModel( username.getCharacters().toString(), password.getCharacters().toString(), name.getCharacters().toString(), Double.parseDouble(salary.getCharacters().toString()), phone.getCharacters().toString(),
                    email.getCharacters().toString());

            ManagerModel.AddLibrarian(librarian);
            libWarningNew.setText("Succes!");


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

        libWarningNew.setId("libWarningNew");
        bttAdd.setId("bttAdd");


        return border;



    }
}
