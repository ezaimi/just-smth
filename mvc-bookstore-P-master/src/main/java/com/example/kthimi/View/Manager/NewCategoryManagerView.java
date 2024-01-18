package com.example.kthimi.View.Manager;

import com.example.kthimi.Controller.AddStockManagerController;
import com.example.kthimi.Controller.BookController;
import com.example.kthimi.Controller.Mockers.FileBasedStockBookRepository;
import com.example.kthimi.Controller.Mockers.StockBookRepository;
import com.example.kthimi.Model.BookModel;
import com.example.kthimi.Model.ManagerModel;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class NewCategoryManagerView {
    StockBookRepository stockBookRepository = new FileBasedStockBookRepository();
    BookController bookController = new BookController(stockBookRepository);
    BorderPane borderPane;
    Button bttBack = new Button("Back");
    Text textCategory = new Text("Category");
    Text textSystem = new Text("System");
    TextField category = new TextField();
    ArrayList<String> categ;
    SupplyManagerView supplyManagerView;
    AddStockManagerView addStockManagerView;

    AddStockManagerController addStockManagerController;


    public NewCategoryManagerView(SupplyManagerView supplyManagerView,ArrayList<String> categ){
        this.supplyManagerView = supplyManagerView;
        //this.addStockManagerView = addStockManagerView;
        borderPane = createNewCategory();

        this.categ = categ;
    }

    public NewCategoryManagerView(ArrayList<String> categ){
        this.categ = categ;
    }
//    public void updateCategInAddStockManagerView(String category) {
//        addStockManagerController.updateCateg(category);
//    }


    public BorderPane createNewCategory(){
        System.out.println("Para selektimit kateg");
        ChoiceBox menuNewCategory = new ChoiceBox(FXCollections.observableArrayList(ManagerModel.getAllCategories()));
        ArrayList<BookModel> stockbooks = bookController.getStockBooks();

        BorderPane border = new BorderPane();

        Text text = new Text("Add new Book Category");
        StackPane stack = new StackPane();
        text.setFont(new Font(30));
        stack.getChildren().add(text);
        stack.setPadding(new Insets(20));
        border.setTop(stack);

        TextField textAddCategoryWarning = new TextField();
        textAddCategoryWarning.setEditable(false);

        Button bttAddCategory = new Button("Add");
        bttBack = new Button("Back");

        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setAlignment(Pos.CENTER);
        grid.add(textCategory, 0, 0);
        grid.add(menuNewCategory, 1, 0);
        grid.add(bttBack, 0, 5);
        grid.add(textSystem, 0, 4);
        grid.add(textAddCategoryWarning, 1, 4);
        grid.add(bttAddCategory, 2, 5);
        border.setCenter(grid);

        bttBack.setOnAction(event -> {
            if(event.getSource()==bttBack) {
                bttBack.getScene().setRoot( supplyManagerView.createSupplyPage() );
            }
        });

        bttAddCategory.setOnAction(event -> {


            if (event.getSource()==bttAddCategory) {



                if (menuNewCategory.getSelectionModel().getSelectedItem().toString().isEmpty()) {
                    textAddCategoryWarning.setText("Failed, Empty field");
                    return;
                }

                if (category.getCharacters().toString().matches("\\d{1,}")) {
                    textAddCategoryWarning.setText("Failed, Invalid Category");
                    return;
                }
                if (bookController.partOfCateogriesChecker(categ,menuNewCategory.getSelectionModel().getSelectedItem().toString())) {
                    textAddCategoryWarning.setText("Failed, Not New");
                    return;
                }



                String bcateg = menuNewCategory.getSelectionModel().getSelectedItem().toString();
               // stockbooks.add(bcateg);
                categ.add(bcateg);
                textAddCategoryWarning.setText("Added!");
                System.out.println("Pas selektimit kateg");
                System.out.println(categ);

                ///

                //addStockManagerView.updateCategories(categ);


            }



        });


        bttAddCategory.setId("bttAddCategory");
        menuNewCategory.setId("menuNewCategory");
        textAddCategoryWarning.setId("textAddCategoryWarning");
        return border;



    }



    public ArrayList<String> getCategories() {
        return categ;
    }

}

