package com.example.kthimi.View.Manager;

import com.example.kthimi.Controller.BookController;
import com.example.kthimi.Controller.Mockers.FileBasedStockBookRepository;
import com.example.kthimi.Controller.Mockers.StockBookRepository;
import com.example.kthimi.Model.BookModel;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class CheckStockLibrarianView {
    StockBookRepository stockBookRepository = new FileBasedStockBookRepository();
    BookController bookController = new BookController(stockBookRepository);
    ManagerView managerView;
    Button bttBack = new Button("Back");

    CheckStockLibrarianView(ManagerView managerView){
        this.managerView = managerView;
        //initCheckStockButtonAction();
    }

//    private void initCheckStockButtonAction() {
//        managerView.getBttSupply().setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                handleCheckStock();
//            }
//        });
//    }

    public BorderPane handleCheckStock() {

        BorderPane border = new BorderPane();
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

        table.setItems(FXCollections.observableArrayList(bookController.getStockBooks()));

        border.setCenter(table);

        Text text = new Text("Stock");
        StackPane stack = new StackPane();
        text.setFont(new Font(30));
        stack.getChildren().add(text);
        stack.setPadding(new Insets(20));

        StackPane stackBackButton = new StackPane();
        stackBackButton.getChildren().add(bttBack);
        bttBack.setOnAction(event -> {
            if(event.getSource()==bttBack) {
                bttBack.getScene().setRoot( managerView.createManagerMainPage() );
            }
        });
        stackBackButton.setPadding(new Insets(40, 0, 40, 0));
        border.setBottom(stackBackButton);

        border.setTop(stack);

        border.setPrefSize(800, 600);

        bttBack.setId("bttBack");

        return border;

    }

}
