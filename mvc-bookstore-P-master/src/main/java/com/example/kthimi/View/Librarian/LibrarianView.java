package com.example.kthimi.View.Librarian;

import com.example.kthimi.Controller.*;
import com.example.kthimi.Controller.Mockers.FileBasedStockBookRepository;
import com.example.kthimi.Controller.Mockers.StockBookRepository;
import com.example.kthimi.Model.BookModel;
import com.example.kthimi.Model.LibrarianModel;
import com.example.kthimi.Model.SharedDataManager;
import com.example.kthimi.View.Manager.AddStockManagerView;
import com.example.kthimi.View.MainView;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class LibrarianView {
    StockBookRepository stockBookRepository = new FileBasedStockBookRepository();
    BookController bookController = new BookController(stockBookRepository);
    Date date;
    ArrayList<Integer> bookQuantities = new ArrayList<>();
    ArrayList<String> booksSoldTitles = new ArrayList<>();
    TextField bookISBN = new TextField();
    public TextField quantity = new TextField();
    public TextField warningsLibrarian = new TextField();


    AddStockManagerView addStockManagerView;
    ArrayList<BookModel> books;
    String category;
    LibrarianView librarianView;


    ///////////////////

    AddLibrarianController addLibrarianController;
    BillLibrarianController billLibrarianController;

    //////////////////

    public ComboBox<String> comboBoxLibrarian;
    public Button bttBack = new Button("Back");
    public Button bttAdd = new Button("Add");
    public Button bttBill =  new Button("Bill");
    public Text textQuantity = new Text("Quantity");
    public BorderPane librarianPage;
    MainView mainView;
    String usernamePage;

    Stage primaryStage;

    Text textSystem = new Text("System");

    AddStockManagerView.ButtonHandler buttonHandler;
    LibrarianFuncController librarianFuncController = new LibrarianFuncController();

    public Button getBttBack() {
        return bttBack;
    }

    public LibrarianView(){

    }

    public LibrarianView(String usernamePage, MainView mainView) {
        this.usernamePage = usernamePage;
        this.mainView = mainView;
        librarianPage = createLibrarianMainPage();

//        this.addStockManagerView = new AddStockManagerView();
        this.books = SharedDataManager.getInstance().getBooks();

        //per butonin Add tek Librarian
       // initializeAddController();
        this.addLibrarianController = new AddLibrarianController(this);
        this.billLibrarianController = new BillLibrarianController(this,addLibrarianController,books);
        //bttAdd.setOnAction(new AddButtonHandler());

        setAddAction();
        setBillAction();

    }

    public void setBookController(BookController bookController) {
        this.bookController = bookController;
        // Set the comboBox or other components with test data from the injected bookController
        // e.g., comboBoxLibrarian.setItems(bookController.getISBNName());
    }




//    public LibrarianView(AddStockManagerView addStockManagerView) {
//        this.addStockManagerView = addStockManagerView;
//    }

    public void setAddAction(){
        //Button bttSubmit = getBttSubmit();
        //bttSubmit.setOnAction(event -> controller.handleLogin());
        bttAdd.setOnAction(event-> addLibrarianController.handleAdd());
//        bttAdd.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                System.out.println("addi po funksionon");
//                addLibrarianController.handleAdd();
//            }
//        });
    }

    public void setBillAction(){
        bttBill.setOnAction(event-> billLibrarianController.handleBill());
//        bttBill.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                System.out.println("billi u printua");
//                billLibrarianController.handleBill();
//            }
//        });
    }

    public BorderPane createLibrarianMainPage() {

        BorderPane borderPane = new BorderPane();

        //comboBoxLibrarian = new ComboBox(FXCollections.observableArrayList(BookController.getISBNName()));


//        Text librarianText = new Text("Welcome " + usernamePage + "!");
//        librarianText.setFont(new Font(20));


        bttBack.setOnAction(event -> {
            mainView.getPrimaryStage().getScene().setRoot(mainView.mainPage());
        });
//        borderPane.setBottom(bttBack);
//        borderPane.setCenter(comboBoxLibrarian);
//        borderPane.setCenter(librarianText);

        comboBoxLibrarian = new ComboBox(FXCollections.observableArrayList(bookController.getISBNName()));

        Text textHeaderLibrarian = new Text("Welcome "+usernamePage);
        StackPane stackHeader = new StackPane();
        textHeaderLibrarian.setFont(new Font(30));
        stackHeader.getChildren().add(textHeaderLibrarian);
        stackHeader.setPadding(new Insets(20));
        borderPane.setTop(stackHeader);

        GridPane gridLibrarianMain = new GridPane();
        gridLibrarianMain.setAlignment(Pos.CENTER);
        gridLibrarianMain.setHgap(5);
        gridLibrarianMain.setVgap(5);
        gridLibrarianMain.add(comboBoxLibrarian,1,0);
        gridLibrarianMain.add(textQuantity, 0, 2);
        gridLibrarianMain.add(quantity, 1, 2);
        gridLibrarianMain.add(textSystem, 0, 5);
        gridLibrarianMain.add(warningsLibrarian, 1, 5);
        warningsLibrarian.setEditable(false);
        borderPane.setCenter(gridLibrarianMain);

        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(bttBack,bttAdd,bttBill);
        hbox.setPadding(new Insets(40));
        hbox.setSpacing(30);
        borderPane.setBottom(hbox);
        //borderPane.setBottom(bttBack);

        //borderPane.setCenter(librarianText);

        //bttAdd.setOnAction();
        //setAddAction();

        //bttBill.setOnAction(this);
        //setBillAction();

        comboBoxLibrarian.setId("comboBoxLibrarian");
        quantity.setId("quantity");
        bttAdd.setId("bttAdd");
        bttBill.setId("bttBill");
        warningsLibrarian.setId("warningsLibrarian");

        return borderPane;
    }

    public BorderPane getLibrarianMainPage() {
        // Return the BorderPane containing librarian view components
        return librarianPage;
    }


    public Button getBttAdd() {
        return bttAdd;
    }
    public Button getBttBill() {
        return bttBill;
    }



}
