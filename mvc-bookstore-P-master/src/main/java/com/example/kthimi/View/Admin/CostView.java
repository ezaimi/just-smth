package com.example.kthimi.View.Admin;

import com.example.kthimi.Controller.AdminFuncController;
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

public class CostView {

    StatisticsFuncController statisticsFuncController = new StatisticsFuncController();
    StatisticsAdminView statisticsAdminView;
    Button bttBack = new Button("Back");

    public CostView(StatisticsAdminView statisticsAdminView){
        this.statisticsAdminView = statisticsAdminView;
    }


    public BorderPane administratorCostPage() {

        BorderPane border = new BorderPane();

        Text text = new Text("Bought books throughout day/month/year");
        StackPane stack = new StackPane();
        text.setFont(new Font(30));
        stack.getChildren().add(text);
        stack.setPadding(new Insets(20));
        border.setTop(stack);


        TextField totalBooksDay = new TextField();
        Text textTotalBooksDay = new Text("Total Books Today");
        TextField totalIncomeDay = new TextField();
        Text textIncomeDay = new Text("Total Cost Today");

        TextField totalBooksMonth = new TextField();
        Text textTotalBooksMonth = new Text("Total Books in a Month");
        TextField totalIncomeMonth = new TextField();
        TextField salaryMonth = new TextField();
        Text textSalaryMonth = new Text("Salary Total This Month");
        Text textIncomeMonth = new Text("Total Cost in a Month");

        TextField totalBooksYearly = new TextField();
        Text textTotalBooksYearly = new Text("Total Books in a Year");
        TextField totalIncomeYearly = new TextField();
        TextField salaryYear = new TextField();
        Text textSalaryYear = new Text("Salary Total In a  Year");
        Text textIncomeYearly = new Text("Total Cost in a Year");


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
        grid.add(textSalaryMonth,2,2);
        grid.add(salaryMonth, 3, 2);

        grid.add(textTotalBooksYearly, 4, 0);
        grid.add(totalBooksYearly, 5, 0);
        grid.add(textIncomeYearly, 4, 1);
        grid.add(totalIncomeYearly, 5, 1);
        grid.add(textSalaryYear, 4, 2);
        grid.add(salaryYear, 5, 2);

        border.setCenter(grid);

        totalBooksDay.setEditable(false);
        totalIncomeDay.setEditable(false);
        totalBooksMonth.setEditable(false);
        totalIncomeMonth.setEditable(false);
        totalBooksYearly.setEditable(false);
        totalIncomeYearly.setEditable(false);
        salaryMonth.setEditable(false);
        salaryYear.setEditable(false);



        totalBooksDay.setText( Integer.toString( statisticsFuncController.getTotalBoughtBooksDay() ) );
        totalIncomeDay.setText( Double.toString( statisticsFuncController.getCostDay()) );

        totalBooksMonth.setText( Integer.toString( statisticsFuncController.getTotalBoughtBooksMonth() )  );
        totalIncomeMonth.setText( Double.toString( statisticsFuncController.getCostMonth())  );
        salaryMonth.setText( Double.toString(AdminFuncController.getSalaries()) );

        totalBooksYearly.setText( Integer.toString( statisticsFuncController.getTotalBoughtBooksYear() ));
        totalIncomeYearly.setText( Double.toString( statisticsFuncController.getCostYear() ));
        salaryYear.setText( Double.toString(AdminFuncController.getSalaries()*12) );

        StackPane stackBackButton = new StackPane();
        stackBackButton.getChildren().add(bttBack);
        bttBack.setOnAction(event -> {
            bttBack.getScene().setRoot( statisticsAdminView.administratorStatPage());

        });
        stackBackButton.setPadding(new Insets(0, 0, 40, 0));
        border.setBottom(stackBackButton);

        bttBack.setId("bttBack");

        return border;

    }


}
