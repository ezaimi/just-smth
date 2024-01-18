package com.example.kthimi.View.Manager;

import com.example.kthimi.Controller.AddStockManagerController;
import com.example.kthimi.Controller.BookController;
import com.example.kthimi.Controller.Mockers.FileBasedStockBookRepository;
import com.example.kthimi.Controller.Mockers.StockBookRepository;
import com.example.kthimi.Controller.NewCategoryManagerController;
import com.example.kthimi.Controller.SupplyManagerController;
import com.example.kthimi.View.Manager.AddStockManagerView;
import com.example.kthimi.View.Manager.ManagerView;
import com.example.kthimi.View.Manager.NewCategoryManagerView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class SupplyManagerView {

    StockBookRepository stockBookRepository = new FileBasedStockBookRepository();
    BookController bookController = new BookController(stockBookRepository);
    private BorderPane borderPane;

    Button bttAddStock = new Button("Add Stock");
    Button bttNewCategory = new Button("New Category");
    Button bttBack = new Button("Back");

    ManagerView managerView;
    SupplyManagerController supplyManagerController;
    AddStockManagerController addStockManagerController;
    NewCategoryManagerController newCategoryManagerController;

    NewCategoryManagerView newCategoryManagerView;

    ArrayList<String> categ = bookController.getCategories();

    private AddStockManagerView addStockManagerView;



    public SupplyManagerView(ManagerView managerView) {
        this.managerView = managerView;

        this.newCategoryManagerView = new NewCategoryManagerView(this,categ);

        this.addStockManagerController = new AddStockManagerController(this,supplyManagerController,newCategoryManagerView);
        this.newCategoryManagerController = new NewCategoryManagerController(this, supplyManagerController);
        borderPane = createSupplyPage();
    }

    public BorderPane createSupplyPage() {

        BorderPane border = new BorderPane();

        Text text = new Text("Add Stock or New Books");
        StackPane stack = new StackPane();
        text.setFont(new Font(30));
        stack.getChildren().add(text);
        stack.setPadding(new Insets(20));
        border.setTop(stack);

        GridPane supplyPageGrid = new GridPane();
        supplyPageGrid.setHgap(5);
        supplyPageGrid.setVgap(5);
        supplyPageGrid.add(bttAddStock, 0, 0);
        supplyPageGrid.add(bttNewCategory, 1, 0);
        supplyPageGrid.add(bttBack, 2, 0);

//        bttAddStock.setOnAction(this);
            setAddStockAction();
//        bttNewCategory.setOnAction(this);
          setNewCategoryAction();



        bttBack.setOnAction(event -> {
            if(event.getSource()==bttBack) {
                bttBack.getScene().setRoot(managerView.createManagerMainPage());
            }
        });

        supplyPageGrid.setAlignment(Pos.CENTER);
        border.setCenter(supplyPageGrid);

        bttAddStock.setId("bttAddStock");
        bttNewCategory.setId("bttNewCategory");

        return border;
    }


    public void setAddStockAction(){
        bttAddStock.setOnAction(event -> addStockManagerController.handleAddStock());
    }

    public Button getBttAddStock() {
        return bttAddStock;
    }



    public void setNewCategoryAction(){
        bttNewCategory.setOnAction(event-> newCategoryManagerController.handleNewCategory());
    }

    public Button getBttNewCategory() {
        return bttNewCategory;
    }


    public ArrayList<String> getCategories() {
        return categ;
    }

}
