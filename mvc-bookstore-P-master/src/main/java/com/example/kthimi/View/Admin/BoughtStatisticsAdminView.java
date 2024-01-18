package com.example.kthimi.View.Admin;

import com.example.kthimi.Controller.BookController;
import com.example.kthimi.Controller.Mockers.FileBasedStockBookRepository;
import com.example.kthimi.Controller.Mockers.StockBookRepository;
import com.example.kthimi.Controller.StatisticsFuncController;
import com.example.kthimi.Model.BookModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class BoughtStatisticsAdminView {
    StockBookRepository stockBookRepository = new FileBasedStockBookRepository();
    BookController bookController = new BookController(stockBookRepository);

    StatisticsFuncController statisticsFuncController = new StatisticsFuncController();
    StatisticsAdminView statisticsAdminView;
    Button bttBack = new Button("Back");
    ArrayList<String> titlesBought = new ArrayList<>();
    ArrayList<Integer> quantitiesBought = new ArrayList<>();


    public BoughtStatisticsAdminView(StatisticsAdminView statisticsAdminView){
        this.statisticsAdminView = statisticsAdminView;
    }

    public BorderPane administratorBoughtPage() {


        BorderPane border = new BorderPane();

        ArrayList<BookModel> stockBooks = bookController.getStockBooks();
        for(int i=0;i<stockBooks.size();i++) {
            if (stockBooks.get(i).getQuantitiesPurchased()>0) {
                titlesBought.add(stockBooks.get(i).getTitle());
                quantitiesBought.add(stockBooks.get(i).getQuantitiesPurchased());
            }
        }


        PieChart pieChart = new PieChart();
        statisticsFuncController.removeDuplicatesSoldTitles(titlesBought,quantitiesBought);

        for (int i=0;i<titlesBought.size();i++) {
            PieChart.Data test = new PieChart.Data(titlesBought.get(i), quantitiesBought.get(i));
            pieChart.getData().add(test);
        }

        Text text = new Text("Bought books throughout day/month/year/total");
        StackPane stack = new StackPane();
        text.setFont(new Font(30));
        stack.getChildren().add(text);
        stack.setPadding(new Insets(20));
        border.setTop(stack);

        Text text1 = new Text(statisticsFuncController.getBooksBoughtDay());
        Text text2 = new Text(statisticsFuncController.getBooksBoughtMonth());
        Text text3 = new Text(statisticsFuncController.getBooksBoughtYear());
        //Text text4 = new Text(BillNumber.getBooksBoughtTotal());

        GridPane grid = new GridPane();
        grid.add(text1, 0, 0);
        grid.add(text2, 1, 0);
        grid.add(text3, 2, 0);
        grid.add(pieChart, 1, 1);
        grid.setHgap(30);
        grid.setVgap(30);
        grid.setAlignment(Pos.CENTER);
        border.setCenter(grid);


        StackPane stackBackButton = new StackPane();
        stackBackButton.getChildren().add(bttBack);
        bttBack.setOnAction(event -> {
            bttBack.getScene().setRoot(statisticsAdminView.administratorStatPage() );
        });
        stackBackButton.setPadding(new Insets(0, 0, 40, 0));
        border.setBottom(stackBackButton);

        bttBack.setId("bttBack");


        return border;


    }



}
