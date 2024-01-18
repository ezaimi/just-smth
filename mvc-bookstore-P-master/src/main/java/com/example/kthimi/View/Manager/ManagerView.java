package com.example.kthimi.View.Manager;

import com.example.kthimi.Controller.ManagerFuncController;
import com.example.kthimi.Controller.SupplyManagerController;
import com.example.kthimi.Model.BookModel;
import com.example.kthimi.Model.ManagerModel;
import com.example.kthimi.View.MainView;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ManagerView {
    ManagerFuncController managerFuncController = new ManagerFuncController();
    SupplyManagerController managerController;
    private BorderPane managerPage;
    String usernamePage;
    MainView mainView;
    Text textSystem = new Text("System");
    Button bttSupply = new Button("Supply");
    Button bttBack = new Button("Back");
    Button bttCheckStock = new Button("Check Stock");

    Button bttCheckLibrarians = new Button("Check Librarians");
    CheckStockLibrarianView checkStockLibrarianView;
    CheckLibrariansManagerView checkLibrariansManagerView;
    StatisticsManagerView statisticsManagerView;
    public Button getBttSupply() {
        return bttSupply;
    }

    public Button getBttCheckLibrarians() {
        return bttCheckLibrarians;
    }


    public ManagerView(String usernamePage,MainView mainView) {
        this.mainView = mainView;
        this.usernamePage = usernamePage;
        this.managerController = new SupplyManagerController(this);

        this.checkStockLibrarianView = new CheckStockLibrarianView(this);
        this.checkLibrariansManagerView = new CheckLibrariansManagerView(this);
        this.statisticsManagerView = new StatisticsManagerView(this);

        managerPage = createManagerMainPage();


    }

    public BorderPane createManagerMainPage() {
        BorderPane borderPane = new BorderPane();

//        Text librarianText = new Text("Welcome " + usernamePage + "!");
//        librarianText.setFont(new Font(20));
//
//
//        Button bttBack = new Button("Back");
//        bttBack.setOnAction(event -> {
//            mainView.getPrimaryStage().getScene().setRoot(mainView.mainPage());
//        });
//        borderPane.setBottom(bttBack);
//        borderPane.setCenter(librarianText);


        Text textHeaderManager = new Text("Welcome "+usernamePage);
        StackPane stackHeader = new StackPane();
        textHeaderManager.setFont(new Font(30));
        stackHeader.getChildren().add(textHeaderManager);
        stackHeader.setPadding(new Insets(20));
        borderPane.setTop(stackHeader);

        Button bttBookStatistics = new Button("Book Statistics");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.add(bttSupply,0,0);
        grid.add(bttCheckLibrarians, 1, 0);
        grid.add(bttBack, 4, 0);
        grid.add(bttCheckStock, 2, 0);
        grid.add(bttBookStatistics, 3, 0);
        grid.setPadding(new Insets(30));
        borderPane.setCenter(grid);

        //--------------------
        StackPane pane = new StackPane();
        TableView table = new TableView();
        TableColumn<BookModel,String> column1 = new TableColumn<>("ISBN");
        TableColumn<BookModel,String> column2 = new TableColumn<>("Title");
        TableColumn<BookModel,String> column3 = new TableColumn<>("Category");
        TableColumn<BookModel,String> column4 = new TableColumn<>("Author");
        TableColumn<BookModel,Double> column5 = new TableColumn<>("Original Price");
        TableColumn<BookModel,Double> column6 = new TableColumn<>("Selling Price");
        TableColumn<BookModel,String> column7 = new TableColumn<>("Supplier");
        TableColumn<BookModel,Integer> column8 = new TableColumn<>("Stock");

        column1.setCellValueFactory(new PropertyValueFactory<BookModel,String>("ISBN"));
        column2.setCellValueFactory(new PropertyValueFactory<BookModel,String>("title"));
        column3.setCellValueFactory(new PropertyValueFactory<BookModel,String>("category"));
        column4.setCellValueFactory(new PropertyValueFactory<BookModel,String>("author"));
        column5.setCellValueFactory(new PropertyValueFactory<BookModel,Double>("originalPrice"));
        column6.setCellValueFactory(new PropertyValueFactory<BookModel,Double>("sellingPrice"));
        column7.setCellValueFactory(new PropertyValueFactory<BookModel,String>("supplier"));
        column8.setCellValueFactory(new PropertyValueFactory<BookModel,Integer>("stock"));

        table.getColumns().add(column1);
        table.getColumns().add(column2);
        table.getColumns().add(column3);
        table.getColumns().add(column4);
        table.getColumns().add(column5);
        table.getColumns().add(column6);
        table.getColumns().add(column7);
        table.getColumns().add(column8);

        table.setItems(FXCollections.observableArrayList(managerFuncController.getLowStock()));
        table.setMaxHeight(150);
        pane.getChildren().add(table);

        borderPane.setBottom(pane);



        //---------------------



       // bttSupply.setOnAction(this);
        //setSupplyAction();
        bttSupply.setOnAction(event->  managerController.handleSupply());

        bttCheckStock.setOnAction(event -> {
            setCheckStockScene((Stage) bttCheckStock.getScene().getWindow());
        });

        //check librarians
        bttCheckLibrarians.setOnAction(event ->{
            setCheckLibrariansAction((Stage) bttCheckLibrarians.getScene().getWindow());
        });

        //check statistics
        bttBookStatistics.setOnAction(event ->{
            setStatisticsScene((Stage) bttBookStatistics.getScene().getWindow());
        });

        bttBack.setOnAction(event -> {
            if(event.getSource()==bttBack) {
                bttBack.getScene().setRoot(mainView.mainPage());
            }
        });

        bttSupply.setId("bttSupply");
        bttCheckLibrarians.setId("bttCheckLibrarians");
        bttCheckStock.setId("bttCheckStock");
        bttBookStatistics.setId("bttBookStatistics");

        return borderPane;
    }



    //butoni 1 - supply
//    public void setSupplyAction(){
//        bttSupply.setOnAction(event->  managerController.handleSupply());
////        bttSupply.setOnAction(new EventHandler<ActionEvent>() {
////            @Override
////            public void handle(ActionEvent actionEvent) {
////               // managerController.handleSupply();
////                if (managerController != null) {
////                    managerController.handleSupply();
////                } else {
////                    // Handle the scenario where managerController is null
////                    System.out.println("ManagerController is null!");
////                }
////            }
////        });
//    }


    //butoni 2 - check librarians
    public void setCheckLibrariansAction(Stage primaryStage){
        CheckLibrariansManagerView checkLibrariansManagerView = new CheckLibrariansManagerView(this);
        BorderPane checkStockPane = checkLibrariansManagerView.librariansAllPage();
        Scene scene = new Scene(checkStockPane, 800, 600);
        primaryStage.setScene(scene);

    }

    //butoni 3 - check stock
    public void setCheckStockScene(Stage primaryStage) {
        CheckStockLibrarianView stockLibrarianView = new CheckStockLibrarianView(this);
        BorderPane checkStockPane = stockLibrarianView.handleCheckStock();
        Scene scene = new Scene(checkStockPane, 800, 600);
        primaryStage.setScene(scene);
    }

    //butoni 4 - book statistics
    public void setStatisticsScene(Stage primaryStage){
        StatisticsManagerView statisticsManagerView = new StatisticsManagerView(this);
        BorderPane checkStockPane = statisticsManagerView.managerStatisticsPage();
        Scene scene = new Scene(checkStockPane, 800, 600);
        primaryStage.setScene(scene);
    }
    //butoni back eshte brenda createLibrarianMainPage


    public BorderPane getManagerMainPage() {
        // Return the BorderPane containing librarian view components
        return managerPage;
    }
}
