package com.example.kthimi.View.Manager;

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

public class SoldStatisticsManagerView {
    StatisticsFuncController statisticsFuncController = new StatisticsFuncController();
    StockBookRepository stockBookRepository = new FileBasedStockBookRepository();
    BookController bookController = new BookController(stockBookRepository);

    BorderPane borderPane;
    StatisticsManagerView statisticsManagerView;
    ManagerView managerView;
    Button bttBack = new Button("Back");
    ArrayList<String> titlesSold = new ArrayList<>();
    ArrayList<Integer> quantitiesSold = new ArrayList<>();


    public SoldStatisticsManagerView(StatisticsManagerView statisticsManagerView){
        this.statisticsManagerView = statisticsManagerView;
        borderPane = managerSoldPage();
    }


    public BorderPane managerSoldPage() {

        BorderPane border = new BorderPane();

        PieChart pieChart = new PieChart();
        ArrayList<BookModel> stockBooks = bookController.getStockBooks();
        for(int i=0;i<stockBooks.size();i++) {
            if (stockBooks.get(i).getPurchasedAmount()>0) {
                titlesSold.add(stockBooks.get(i).getTitle());
                quantitiesSold.add(stockBooks.get(i).getPurchasedAmount());
            }
        }

        statisticsFuncController.removeDuplicatesSoldTitles(titlesSold,quantitiesSold);

        for (int i=0;i<titlesSold.size();i++) {
            PieChart.Data test = new PieChart.Data(titlesSold.get(i), quantitiesSold.get(i));
            pieChart.getData().add(test);
        }

        Text text = new Text("Sold books throughout day/month/year/total");
        StackPane stack = new StackPane();
        text.setFont(new Font(30));
        stack.getChildren().add(text);
        stack.setPadding(new Insets(20));
        border.setTop(stack);

        Text text1 = new Text(statisticsFuncController.getBooksSoldDay());
        Text text2 = new Text(statisticsFuncController.getBooksSoldMonth());
        Text text3 = new Text(statisticsFuncController.getBooksSoldYear());
        //Text text4 = new Text( BillNumber.getBooksSoldTotal());

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
            if(event.getSource()==bttBack) {
                bttBack.getScene().setRoot( statisticsManagerView.managerStatisticsPage() );
            }
        });
        stackBackButton.setPadding(new Insets(0, 0, 40, 0));
        border.setBottom(stackBackButton);

        bttBack.setId("bttBack");

        return border;

    }

}
