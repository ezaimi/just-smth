package com.example.kthimi.View.Admin;

import com.example.kthimi.View.Admin.ManageLibrariansView;
import com.example.kthimi.View.Admin.ManageManagersView;
import com.example.kthimi.View.Admin.StatisticsAdminView;
import com.example.kthimi.View.MainView;
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

public class AdminView {
    private BorderPane adminPage;
    String usernamePage;
    MainView mainView;

    Text textSystem = new Text("System");
    Button bttBack = new Button("Back");

    ManageLibrariansView manageLibrariansView;
    ManageManagersView manageManagersView;
    StatisticsAdminView statisticsAdminView;

    public AdminView(String usernamePage, MainView mainView) {
        this.mainView = mainView;
        this.usernamePage = usernamePage;
        adminPage = createAdminMainPage();

        this.manageLibrariansView = new ManageLibrariansView(this);
        this.manageManagersView = new ManageManagersView(this);
        this.statisticsAdminView = new StatisticsAdminView(this);
    }

    public BorderPane createAdminMainPage() {

        BorderPane borderPane = new BorderPane();

        Text text = new Text("Welcome " + usernamePage + "!");
        StackPane stack = new StackPane();
        text.setFont(new Font(30));
        stack.getChildren().add(text);
        stack.setPadding(new Insets(20));
        borderPane.setTop(stack);

        Button bttManageManager = new Button("Manage Managers");
        Button bttManageLibrarians = new Button("Manage Librarians");
        Button bttStats = new Button("Stats");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.add(bttManageLibrarians, 0, 0);
        grid.add(bttManageManager, 1, 0);
        grid.add(bttStats, 2, 0);
        grid.add(bttBack, 3, 0);
        borderPane.setCenter(grid);

        //1 - Manage Librarians
        bttManageLibrarians.setOnAction(event -> {
            setManageLibrariansScene((Stage) bttManageLibrarians.getScene().getWindow());
        });

        //2 - Manage Managers
        bttManageManager.setOnAction(event -> {
            setManageManagersScene((Stage) bttManageManager.getScene().getWindow());
        });

        //3 - Admin Stats
        bttStats.setOnAction(event -> {
            setAdminStatsScene((Stage) bttStats.getScene().getWindow());
        });

        bttBack.setOnAction(event-> {
            if (event.getSource() == bttBack) {
                //usernamePage.clear();
                bttBack.getScene().setRoot(mainView.mainPage());
            }
        });

        bttStats.setId("bttStats");
        bttManageLibrarians.setId("bttManageLibrarians");
        bttManageManager.setId("bttManageManagers");
        bttBack.setId("bttBack");

        return borderPane;
    }


    //butoni 1 - Manage Librarians
    public void setManageLibrariansScene (Stage primaryStage){
        ManageLibrariansView manageLibrariansView = new ManageLibrariansView(this);
        BorderPane checkStockPane = manageLibrariansView.administratorManageLibrariansPage();
        Scene scene = new Scene(checkStockPane, 800, 600);
        primaryStage.setScene(scene);

    }

    //butoni 2 - Manage Managers
    public void setManageManagersScene(Stage primaryStage){
        ManageManagersView manageLibrariansView = new ManageManagersView(this);
        BorderPane checkStockPane = manageManagersView.administratorManagerPage();
        Scene scene = new Scene(checkStockPane, 800, 600);
        primaryStage.setScene(scene);

    }

    //butoni 3 - Administrator Statistics
    public void setAdminStatsScene(Stage primaryStage){
        StatisticsAdminView statisticsAdminView = new StatisticsAdminView(this);
        BorderPane checkStockPane = statisticsAdminView.administratorStatPage();
        Scene scene = new Scene(checkStockPane, 800, 600);
        primaryStage.setScene(scene);

    }

    public BorderPane getAdminMainPage() {
        // Return the BorderPane containing librarian view components
        return adminPage;
    }
}
