package com.example.kthimi.View.Manager;

import com.example.kthimi.Controller.LibrarianFuncController;
import com.example.kthimi.Controller.StatisticsFuncController;
import com.example.kthimi.Model.LibrarianModel;
import com.example.kthimi.Model.ManagerModel;
import com.example.kthimi.View.Manager.ManagerView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class CheckLibrariansManagerView {

    LibrarianFuncController librarianFuncController = new LibrarianFuncController();
    BorderPane borderPane;
    Button bttBack = new Button("Back");
    ManagerView managerView;
    Text textTotalNumberBillsLibrarian = new Text("Total Number of Bills");
    TextField totalNumberBillsLibrarian = new TextField();
    Text textBooksSold = new Text("Books Sold");
    TextField booksSold = new TextField();//Kujdes
    TextField totalAmountOfMoneyMadeInDay = new TextField();
    Text textTotalMoneyDay = new Text("Money made today");
    TextField totalAmountOfMoneyMadeInMonth = new TextField();
    Text textTotalMoneyMonth = new Text("Money made in a month");
    TextField totalAmountOfMoneyMadeInYear = new TextField();
    Text textTotalMoneyYear = new Text("Money made in a year");
    StatisticsFuncController statisticsFuncController = new StatisticsFuncController();



    public CheckLibrariansManagerView(ManagerView managerView){
        borderPane = librariansAllPage();
        this.managerView = managerView;

        this.statisticsFuncController = new StatisticsFuncController();
    }

    public BorderPane librariansAllPage() {

        BorderPane border = new BorderPane();

        Text textHeaderManager = new Text("Select Librarians");
        StackPane stackHeader = new StackPane();
        textHeaderManager.setFont(new Font(30));
        stackHeader.getChildren().add(textHeaderManager);
        stackHeader.setPadding(new Insets(20));
        border.setTop(stackHeader);

        StackPane stackBackButton = new StackPane();
        stackBackButton.getChildren().add(bttBack);
        bttBack.setOnAction(event -> {
            if(event.getSource()==bttBack) {
                bttBack.getScene().setRoot( managerView.createManagerMainPage() );
            }
        });
        stackBackButton.setPadding(new Insets(0, 0, 40, 0));
        border.setBottom(stackBackButton);


        ArrayList<LibrarianModel> librarians = ManagerModel.getLibrarians();

        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setAlignment(Pos.CENTER);
        int k=0;
        int j=0;

        for (int i=0;i<librarians.size();i++) {
            if (i%5==0) {
                k=0;
                j++;
            }


            Button button = createButton3(librarians.get(i));

            grid.add(button,k++,j);

        }
        border.setCenter(grid);

        bttBack.setId("bttBack");


        return border;


    }

    //------------------------------------------------------------------


    private Button createButton3(LibrarianModel lib) {
        Button button;

        if (lib.getName()==null)
            button = new Button(lib.getUsername());

        else
            button = new Button(lib.getName());


        button.setOnAction(new ButtonHandler3(lib));
        button.setId("button");
        return button ;
    }

    class ButtonHandler3 implements EventHandler<ActionEvent> {

        private final LibrarianModel lib;

        ButtonHandler3(LibrarianModel lib) {
            this.lib = lib;
        }

        @Override
        public void handle(ActionEvent event) {
            Stage stage = new Stage();
            Scene scene = new Scene(selectLibrarianPage(lib));
            //stage.getIcons().add(new Image("bookIcon.png"));
            stage.setWidth(800);
            stage.setHeight(600);
            stage.setScene(scene);
            stage.show();

        }

    }


    //------------------------------------------------------------------


    public BorderPane selectLibrarianPage(LibrarianModel lib) {

        BorderPane border = new BorderPane();
        // Button bttBackIn = new Button("Back");

        Text text;
        if (lib.getName() == null)
            text = new Text("Performance of: "+lib.getUsername());
        else
            text = new Text("Performance of: "+lib.getName());
        StackPane stack = new StackPane();
        text.setFont(new Font(30));
        stack.getChildren().add(text);
        stack.setPadding(new Insets(20));
        border.setTop(stack);

        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setAlignment(Pos.CENTER);
        grid.add(textTotalNumberBillsLibrarian, 0, 0);
        grid.add(totalNumberBillsLibrarian, 1, 0);

        grid.add(textBooksSold, 0, 1);
        grid.add(booksSold, 1, 1);

        grid.add(textTotalMoneyDay, 0, 2);
        grid.add(totalAmountOfMoneyMadeInDay, 1, 2);

        grid.add(textTotalMoneyMonth, 0, 3);
        grid.add(totalAmountOfMoneyMadeInMonth, 1, 3);

        grid.add(textTotalMoneyYear, 0, 4);
        grid.add(totalAmountOfMoneyMadeInYear, 1, 4);

        //grid.add(bttBackIn,1,5);

        totalNumberBillsLibrarian.setEditable(false);
        booksSold.setEditable(false);
        totalAmountOfMoneyMadeInDay.setEditable(false);
        totalAmountOfMoneyMadeInMonth.setEditable(false);
        totalAmountOfMoneyMadeInYear.setEditable(false);

        totalNumberBillsLibrarian.setText(Integer.toString(librarianFuncController.getNumberOfBills()));
        booksSold.setText(Integer.toString(lib.getBooksSold()));
        totalAmountOfMoneyMadeInDay.setText( Double.toString(statisticsFuncController.moneyMadeInDay()) );
        totalAmountOfMoneyMadeInMonth.setText( Double.toString(statisticsFuncController.moneyMadeInMonth()) );
        totalAmountOfMoneyMadeInYear.setText( Double.toString(statisticsFuncController.moneyMadeInYear()) );

        border.setCenter(grid);

        //bttBackIn.setOnAction(event -> librariansAllPage());

       // bttBackIn.setId("bttBackIn");

        return border;
    }

}
