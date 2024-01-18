package com.example.kthimi.View.Admin;

import com.example.kthimi.Controller.AdminFuncController;
import com.example.kthimi.Controller.LibrarianFuncController;
import com.example.kthimi.Model.LibrarianModel;
import com.example.kthimi.Model.ManagerModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class EditManagerPageView {

    ManageManagersView manageManagersView;
    Button bttBack = new Button("Back");
    TextField magLoginWarning = new TextField();
    Text textSystem = new Text("System");
    ManagerModel manager;
    ArrayList<ManagerModel> magList = new ArrayList<>();


    public EditManagerPageView(ManageManagersView manageManagersView){
        this.manageManagersView = manageManagersView;
    }


    public BorderPane editManagerPage(ManagerModel mag) {

        BorderPane border = new BorderPane();

        Text text = new Text("Edit Manager");
        StackPane stack = new StackPane();
        text.setFont(new Font(30));
        stack.getChildren().add(text);
        stack.setPadding(new Insets(20));
        border.setTop(stack);
        magLoginWarning.clear();

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
        Button bttSubmit = new Button("Submit");
        Button bttDelete = new Button("Delete");

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
        grid.add(magLoginWarning, 1, 8);

        HBox hbox = new HBox();
        hbox.getChildren().addAll(bttSubmit,bttDelete);
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(5);
        hbox.setPadding(new Insets(0,0,20,0));
        border.setBottom(hbox);


        magLoginWarning.setEditable(false);
        name.setEditable(false);

        name.setText(mag.getName());
        username.setText(mag.getUsername());
        password.setText(mag.getPassword());
        salary.setText(Double.toString(mag.getSalary()));
        email.setText(mag.getEmail());
        phone.setText(mag.getPhone());

        bttDelete.setOnAction(event ->{

            if (password.getCharacters().isEmpty() || username.getCharacters().isEmpty() || salary.getCharacters().isEmpty() || phone.getCharacters().isEmpty() || email.getCharacters().isEmpty()) {
                magLoginWarning.setText("Empty Fields");
                return;
            }


            if (!(LibrarianFuncController.checkEmail(email.getCharacters().toString()))) {
                email.clear();
                magLoginWarning.setText("Invalid Email");
                return;
            }

            if (!(LibrarianFuncController.checkPassword(password.getCharacters().toString()))) {
                password.clear();
                magLoginWarning.setText("Invalid Password");
                return;
            }

            if (!(LibrarianFuncController.checkPhone(phone.getCharacters().toString()))) {
                phone.clear();
                magLoginWarning.setText("Invalid Phone Number");
                return;
            }

            if (!(LibrarianFuncController.checkSalary(salary.getCharacters().toString()))) {
                salary.clear();
                magLoginWarning.setText("Invalid Salary");
                return;
            }

            manager = new ManagerModel( username.getCharacters().toString(), password.getCharacters().toString(), mag.getName(), Double.parseDouble(salary.getCharacters().toString()), phone.getCharacters().toString(),
                    email.getCharacters().toString());

            AdminFuncController.deleteManager(manager,magList);
            username.clear();
            name.clear();
            password.clear();
            salary.clear();
            phone.clear();
            email.clear();
            magLoginWarning.setText("Deleted Succesfully!");
        });

        bttSubmit.setOnAction(event ->{

            if (password.getCharacters().isEmpty() || username.getCharacters().isEmpty() || salary.getCharacters().isEmpty() || phone.getCharacters().isEmpty() || email.getCharacters().isEmpty()) {
                magLoginWarning.setText("Empty Fields");
                return;
            }


            if (!(LibrarianFuncController.checkEmail(email.getCharacters().toString()))) {
                email.clear();
                magLoginWarning.setText("Invalid Email");
                return;
            }

            if (!(LibrarianFuncController.checkPassword(password.getCharacters().toString()))) {
                password.clear();
                magLoginWarning.setText("Invalid Password");
                return;
            }

            if (!(LibrarianFuncController.checkPhone(phone.getCharacters().toString()))) {
                phone.clear();
                magLoginWarning.setText("Invalid Phone Number");
                return;
            }

            if (!(LibrarianFuncController.checkSalary(salary.getCharacters().toString()))) {
                salary.clear();
                magLoginWarning.setText("Invalid Salary");
                return;
            }

            manager = new ManagerModel( username.getCharacters().toString(), password.getCharacters().toString(), mag.getName(), Double.parseDouble(salary.getCharacters().toString()), phone.getCharacters().toString(),
                    email.getCharacters().toString());


            AdminFuncController.updateManagers(manager,magList);
            magLoginWarning.setText("Success!");

        });

        border.setCenter(grid);
        name.setId("name");
        password.setId("password");
        username.setId("username");
        salary.setId("salary");
        phone.setId("phone");
        email.setId("email");

        bttSubmit.setId("bttSubmit");

        magLoginWarning.setId("magLoginWarning");


        return border;


    }


}
