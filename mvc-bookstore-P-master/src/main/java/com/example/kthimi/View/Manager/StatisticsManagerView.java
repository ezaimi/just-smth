package com.example.kthimi.View.Manager;

import com.example.kthimi.View.Manager.BoughtStatisticsManagerView;
import com.example.kthimi.View.Manager.ManagerView;
import com.example.kthimi.View.Manager.SoldStatisticsManagerView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StatisticsManagerView {

    BorderPane borderPane;
    Button bttBack = new Button("Back");
    SoldStatisticsManagerView soldStatisticsManagerView;
    BoughtStatisticsManagerView boughtStatisticsManagerView;
    ManagerView managerView;
    public StatisticsManagerView(ManagerView managerView){
        this.managerView = managerView;
        borderPane = managerStatisticsPage();

        this.soldStatisticsManagerView = new SoldStatisticsManagerView(this);
        this.boughtStatisticsManagerView = new BoughtStatisticsManagerView(this);
    }

    public BorderPane managerStatisticsPage() {

        BorderPane border = new BorderPane();


        Text text = new Text("Book Statistics");
        StackPane stack = new StackPane();
        text.setFont(new Font(30));
        stack.getChildren().add(text);
        stack.setPadding(new Insets(20));
        border.setTop(stack);

        Button bttSold = new Button("Sold");
        Button bttBought = new Button("Bought");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.add(bttSold, 0, 0);
        grid.add(bttBought, 1, 0);
        border.setCenter(grid);


        bttSold.setOnAction(event ->{
            setSoldStatisticsScene((Stage) bttSold.getScene().getWindow());
        });

        bttBought.setOnAction(event -> {
            setBoughtStatisticsScene((Stage) bttBought.getScene().getWindow());
        });


          StackPane stackBackButton = new StackPane();
          stackBackButton.getChildren().add(bttBack);

        bttBack.setOnAction(event -> {
            if(event.getSource()==bttBack) {
                bttBack.getScene().setRoot( managerView.createManagerMainPage() );
            }
        });
        stackBackButton.setPadding(new Insets(0, 0, 40, 0));
        border.setBottom(stackBackButton);

        bttBought.setId("bttBought");
        bttSold.setId("bttSold");

        return border;

    }


    //butoni sold
    public void setSoldStatisticsScene(Stage primaryStage) {
        SoldStatisticsManagerView soldStatisticsManagerView = new SoldStatisticsManagerView(this);
        BorderPane checkStockPane = soldStatisticsManagerView.managerSoldPage();
        Scene scene = new Scene(checkStockPane, 800, 600);
        primaryStage.setScene(scene);
    }

    //butoni bought
    public void setBoughtStatisticsScene(Stage primaryStage){
        BoughtStatisticsManagerView boughtStatisticsManagerView = new BoughtStatisticsManagerView(this);
        BorderPane checkStockPane = boughtStatisticsManagerView.managerBoughtPage();
        Scene scene = new Scene(checkStockPane, 800, 600);
        primaryStage.setScene(scene);
    }
}
