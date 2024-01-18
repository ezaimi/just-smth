package com.example.kthimi.View.Admin;

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

public class StatisticsAdminView {

    AdminView adminView;
    Button bttBack = new Button("Back");
    SoldStatisticsAdminView soldStatisticsAdminView;
    BoughtStatisticsAdminView boughtStatisticsAdminView;
    IncomeView  incomeView;
    CostView costView;
    public StatisticsAdminView(AdminView adminView){
        this.adminView = adminView;

        this.soldStatisticsAdminView = new SoldStatisticsAdminView(this);
        this.boughtStatisticsAdminView = new BoughtStatisticsAdminView(this);
        this.incomeView = new IncomeView(this);
        this.costView = new CostView(this);
    }

    public BorderPane administratorStatPage() {

        BorderPane border = new BorderPane();

        Text text = new Text("Book Statistics");
        StackPane stack = new StackPane();
        text.setFont(new Font(30));
        stack.getChildren().add(text);
        stack.setPadding(new Insets(20));
        border.setTop(stack);

        Button bttSold = new Button("Sold");
        Button bttBought = new Button("Bought");
        Button bttIncome = new Button("Income");
        Button bttCost = new Button("Cost");


        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.add(bttSold, 0, 0);
        grid.add(bttBought, 1, 0);
        grid.add(bttIncome, 2, 0);
        grid.add(bttCost, 3, 0);
        border.setCenter(grid);


        bttSold.setOnAction(event ->{
            setSoldStatisticsScene((Stage) bttSold.getScene().getWindow());
        });

        bttBought.setOnAction(event -> {
            setBoughtStatisticsScene((Stage) bttBought.getScene().getWindow());
        });

        bttIncome.setOnAction(event -> {
            setIncomeScene((Stage) bttIncome.getScene().getWindow());
        });

        bttCost.setOnAction(event -> {
            setCostScene((Stage) bttCost.getScene().getWindow());
        });


        StackPane stackBackButton = new StackPane();

        stackBackButton.getChildren().add(bttBack);
        bttBack.setOnAction(event -> {
            bttBack.getScene().setRoot( adminView.createAdminMainPage() );
        });

        stackBackButton.setPadding(new Insets(0, 0, 40, 0));
        border.setBottom(stackBackButton);

        bttBought.setId("bttBought");
        bttCost.setId("bttCost");
        bttIncome.setId("bttIncome");
        bttSold.setId("bttSold");
        bttBack.setId("bttBack");

        return border;
    }

    //butoni sold
    public void setSoldStatisticsScene(Stage primaryStage) {
        SoldStatisticsAdminView soldStatisticsAdminView = new SoldStatisticsAdminView(this);
        BorderPane checkStockPane = soldStatisticsAdminView.administratorSoldPage();
        Scene scene = new Scene(checkStockPane, 800, 600);
        primaryStage.setScene(scene);
    }

    //butoni bought
    public void setBoughtStatisticsScene(Stage primaryStage) {
        BoughtStatisticsAdminView boughtStatisticsAdminView = new BoughtStatisticsAdminView(this);
        BorderPane checkStockPane = boughtStatisticsAdminView.administratorBoughtPage();
        Scene scene = new Scene(checkStockPane, 800, 600);
        primaryStage.setScene(scene);
    }

    //butoni income
    public void setIncomeScene(Stage primaryStage){
        IncomeView incomeView = new IncomeView(this);
        BorderPane checkStockPane = incomeView.administratorIncomePage();
        Scene scene = new Scene(checkStockPane, 800, 600);
        primaryStage.setScene(scene);
    }

    //butoni cost
    public void setCostScene(Stage primaryStage){
        CostView costView = new CostView(this);
        BorderPane checkStockPane = costView.administratorCostPage();
        Scene scene = new Scene(checkStockPane, 800, 600);
        primaryStage.setScene(scene);
    }



}
