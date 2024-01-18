package com.example.kthimi.View.Admin;

import com.example.kthimi.Controller.LibrarianFuncController;
import com.example.kthimi.Controller.ManagerFuncController;
import com.example.kthimi.Controller.StatisticsFuncController;
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

public class EditLibrarianPageView {

    LibrarianFuncController librarianFuncController = new LibrarianFuncController();
    StatisticsFuncController statisticsFuncController = new StatisticsFuncController();
    ManageLibrariansView manageLibrariansView;
    Button bttBack = new Button("Back");
    Text textTotalNumberBillsLibrarian = new Text("Total Number of Bills");
    TextField totalNumberBillsLibrarian = new TextField();
    TextField booksSold = new TextField();
    Text textBooksSold = new Text("Books Sold");
    TextField totalAmountOfMoneyMadeInDay = new TextField();
    Text textTotalMoneyDay = new Text("Money made today");
    TextField totalAmountOfMoneyMadeInMonth = new TextField();
    Text textTotalMoneyMonth = new Text("Money made in a month");
    TextField totalAmountOfMoneyMadeInYear = new TextField();
    Text textTotalMoneyYear = new Text("Money made in a year");
    Text textSystem = new Text("System");
    LibrarianModel librarian;
    public EditLibrarianPageView(ManageLibrariansView manageLibrariansView){
        this.manageLibrariansView = manageLibrariansView;

    }

    public BorderPane editLibrarianPage(LibrarianModel lib) {

        BorderPane border = new BorderPane();

        Text text = new Text("Edit Librarian");
        StackPane stack = new StackPane();
        text.setFont(new Font(30));
        stack.getChildren().add(text);
        stack.setPadding(new Insets(20));
        border.setTop(stack);
        TextField libLoginWarning = new TextField();

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
        grid.add(textTotalNumberBillsLibrarian, 2, 0);
        grid.add(totalNumberBillsLibrarian, 3, 0);
        grid.add(textBooksSold, 2, 1);
        grid.add(booksSold, 3, 1);
        grid.add(textTotalMoneyDay, 2, 2);
        grid.add(totalAmountOfMoneyMadeInDay, 3, 2);
        grid.add(textTotalMoneyMonth, 2, 3);
        grid.add(totalAmountOfMoneyMadeInMonth, 3, 3);
        grid.add(textTotalMoneyYear, 2, 4);
        grid.add(totalAmountOfMoneyMadeInYear, 3, 4);
        grid.add(textSystem, 0, 8);
        grid.add(libLoginWarning, 1, 8);

        HBox hbox = new HBox();
        hbox.getChildren().addAll(bttSubmit,bttDelete);
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(5);
        hbox.setPadding(new Insets(0,0,20,0));
        border.setBottom(hbox);

        totalNumberBillsLibrarian.setEditable(false);
        booksSold.setEditable(false);
        totalAmountOfMoneyMadeInDay.setEditable(false);
        totalAmountOfMoneyMadeInMonth.setEditable(false);
        totalAmountOfMoneyMadeInYear.setEditable(false);
        libLoginWarning.setEditable(false);

        totalNumberBillsLibrarian.setText(Integer.toString(librarianFuncController.getNumberOfBills()));
        booksSold.setText(Integer.toString(lib.getBooksSold()));
        totalAmountOfMoneyMadeInDay.setText( Double.toString(statisticsFuncController.moneyMadeInDay()) );
        totalAmountOfMoneyMadeInMonth.setText( Double.toString(statisticsFuncController.moneyMadeInMonth()) );
        totalAmountOfMoneyMadeInYear.setText( Double.toString(statisticsFuncController.moneyMadeInYear()) );

        name.setEditable(false);
        name.setText(lib.getName());
        username.setText(lib.getUsername());
        password.setText(lib.getPassword());
        salary.setText(Double.toString(lib.getSalary()));
        email.setText(lib.getEmail());
        phone.setText(lib.getPhone());

        bttDelete.setOnAction(event ->{

            if (password.getCharacters().isEmpty() || username.getCharacters().isEmpty() || salary.getCharacters().isEmpty() || phone.getCharacters().isEmpty() || email.getCharacters().isEmpty()) {
                libLoginWarning.setText("Empty Fields");
                return;
            }


            if (!(LibrarianFuncController.checkEmail(email.getCharacters().toString()))) {
                email.clear();
                libLoginWarning.setText("Invalid Email");
                return;
            }

            if (!(LibrarianFuncController.checkPassword(password.getCharacters().toString()))) {
                password.clear();
                libLoginWarning.setText("Invalid Password");
                return;
            }

            if (!(LibrarianFuncController.checkPhone(phone.getCharacters().toString()))) {
                phone.clear();
                libLoginWarning.setText("Invalid Phone Number");
                return;
            }

            if (!(LibrarianFuncController.checkSalary(salary.getCharacters().toString()))) {
                salary.clear();
                libLoginWarning.setText("Invalid Salary");
                return;
            }

            librarian = new LibrarianModel( username.getCharacters().toString(), password.getCharacters().toString(), lib.getName(), Double.parseDouble(salary.getCharacters().toString()), phone.getCharacters().toString(),
                    email.getCharacters().toString());

            ManagerFuncController.deleteLibrarian(librarian);
            username.clear();
            name.clear();
            password.clear();
            salary.clear();
            phone.clear();
            email.clear();
            totalNumberBillsLibrarian.clear();
            booksSold.clear();
            totalAmountOfMoneyMadeInDay.clear();
            totalAmountOfMoneyMadeInMonth.clear();
            totalAmountOfMoneyMadeInYear.clear();
            libLoginWarning.setText("Deleted Succesfully!");



        });

        bttSubmit.setOnAction(event ->{

            if (password.getCharacters().isEmpty() || username.getCharacters().isEmpty() || salary.getCharacters().isEmpty() || phone.getCharacters().isEmpty() || email.getCharacters().isEmpty()) {
                libLoginWarning.setText("Empty Fields");
                return;
            }


            if (!(LibrarianFuncController.checkEmail(email.getCharacters().toString()))) {
                email.clear();
                libLoginWarning.setText("Invalid Email");
                return;
            }

            if (!(LibrarianFuncController.checkPassword(password.getCharacters().toString()))) {
                password.clear();
                libLoginWarning.setText("Invalid Password");
                return;
            }

            if (!(LibrarianFuncController.checkPhone(phone.getCharacters().toString()))) {
                phone.clear();
                libLoginWarning.setText("Invalid Phone Number");
                return;
            }

            if (!(LibrarianFuncController.checkSalary(salary.getCharacters().toString()))) {
                salary.clear();
                libLoginWarning.setText("Invalid Salary");
                return;
            }

            librarian = new LibrarianModel( username.getCharacters().toString(), password.getCharacters().toString(), lib.getName(), Double.parseDouble(salary.getCharacters().toString()), phone.getCharacters().toString(),
                    email.getCharacters().toString());


            ManagerFuncController.updateLibrarians(librarian);
            libLoginWarning.setText("Success!");

        });


        name.setId("name");
        password.setId("password");
        username.setId("username");
        salary.setId("salary");
        phone.setId("phone");
        email.setId("email");

        libLoginWarning.setId("libLoginWarning");
        bttSubmit.setId("bttSubmit");

        border.setCenter(grid);

        return border;
    }

}
