package com.example.kthimi.View.Admin;

import com.example.kthimi.Controller.StatisticsFuncController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class IncomeView {
StatisticsFuncController statisticsFuncController = new StatisticsFuncController();
    StatisticsAdminView statisticsAdminView;
    Button bttBack = new Button("Back");

    public IncomeView(StatisticsAdminView statisticsAdminView){
        this.statisticsAdminView = statisticsAdminView;
    }

    public BorderPane administratorIncomePage() {

        BorderPane border = new BorderPane();

        Text text = new Text("Income throughout day/month/year");
        StackPane stack = new StackPane();
        text.setFont(new Font(30));
        stack.getChildren().add(text);
        stack.setPadding(new Insets(20));
        border.setTop(stack);

        TextField totalBooksDay = new TextField();
        Text textTotalBooksDay = new Text("Total Books Today");
        TextField totalIncomeDay = new TextField();
        Text textIncomeDay = new Text("Total Income Today");

        TextField totalBooksMonth = new TextField();
        Text textTotalBooksMonth = new Text("Total Books in a Month");
        TextField totalIncomeMonth = new TextField();
        Text textIncomeMonth = new Text("Total Income in a Month");

        TextField totalBooksYearly = new TextField();
        Text textTotalBooksYearly = new Text("Total Books in a Year");
        TextField totalIncomeYearly = new TextField();
        Text textIncomeYearly = new Text("Total Income in a Year");


        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.add(textTotalBooksDay, 0, 0);
        grid.add(totalBooksDay, 1, 0);
        grid.add(textIncomeDay, 0, 1);
        grid.add(totalIncomeDay,1,1);
        grid.add(textTotalBooksMonth, 2, 0);
        grid.add(totalBooksMonth,3,0);
        grid.add(textIncomeMonth, 2, 1);
        grid.add(totalIncomeMonth, 3, 1);
        grid.add(textTotalBooksYearly, 4, 0);
        grid.add(totalBooksYearly, 5, 0);
        grid.add(textIncomeYearly, 4, 1);
        grid.add(totalIncomeYearly, 5, 1);

        border.setCenter(grid);

        totalBooksDay.setEditable(false);
        totalIncomeDay.setEditable(false);
        totalBooksMonth.setEditable(false);
        totalIncomeMonth.setEditable(false);
        totalBooksYearly.setEditable(false);
        totalIncomeYearly.setEditable(false);

        totalBooksDay.setText( Integer.toString( statisticsFuncController.getIntBooksSoldDay() ) );
        totalIncomeDay.setText( Double.toString( statisticsFuncController.getIncomeDay()) );
        totalBooksMonth.setText( Integer.toString( statisticsFuncController.getIntBooksSoldMonth() )  );
        totalIncomeMonth.setText( Double.toString( statisticsFuncController.getIncomeMonth())  );
        totalBooksYearly.setText( Integer.toString( statisticsFuncController.getIntBooksSoldYear() ));
        totalIncomeYearly.setText( Double.toString( statisticsFuncController.getIncomeYear() ));

        StackPane stackBackButton = new StackPane();
        stackBackButton.getChildren().add(bttBack);
        bttBack.setOnAction(event -> {
            bttBack.getScene().setRoot( statisticsAdminView.administratorStatPage() );
        });
        stackBackButton.setPadding(new Insets(0, 0, 40, 0));
        border.setBottom(stackBackButton);

        bttBack.setId("bttBack");

        return border;

    }
}
