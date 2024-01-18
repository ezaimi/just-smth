package com.example.kthimi.View.Admin;

import com.example.kthimi.Model.AdministratorModel;
import com.example.kthimi.Model.ManagerModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

import java.util.ArrayList;

public class ManageManagersView {

    AdminView adminView;
    Button bttBack = new Button("Back");
    ManageManAddNew manageManAddNew;
    EditManagerPageView editManagerPage;

    public ManageManagersView(AdminView adminView){
        this.adminView = adminView;

        this.manageManAddNew = new ManageManAddNew(this);
        this.editManagerPage = new EditManagerPageView(this);
    }


    public BorderPane administratorManagerPage() {

        BorderPane border = new BorderPane();

        Text text = new Text("Select Manager");
        StackPane stack = new StackPane();
        text.setFont(new Font(30));
        stack.getChildren().add(text);
        stack.setPadding(new Insets(20));
        border.setTop(stack);

        StackPane stackBackButton = new StackPane();
        stackBackButton.getChildren().add(bttBack);

        bttBack.setOnAction(event -> {
            if(event.getSource()==bttBack) {
                bttBack.getScene().setRoot( adminView.createAdminMainPage() );
            }
        });

        stackBackButton.setPadding(new Insets(0, 0, 40, 0));
        border.setBottom(stackBackButton);

        Button bttAddNew = new Button("Add New");
        ArrayList<ManagerModel> managers = AdministratorModel.getManagers();

        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setAlignment(Pos.CENTER);
        int k=0;
        int j=0;

        for (int i=0;i<managers.size();i++) {
            if (i%5==0) {
                k=0;
                j++;
            }


            Button button = createButton5(managers.get(i));

            grid.add(button,k++,j);

        }
        grid.add(bttAddNew, k, j);
        border.setCenter(grid);

        bttAddNew.setOnAction(event-> {
            bttAddNew.getScene().setRoot(manageManAddNew.addManager());
        });

        bttAddNew.setId("bttAddNew");

        return border;

    }


    //----------------------------------------------------------------------


    private Button createButton5(ManagerModel mag) {
        Button button;


        button = new Button(mag.getName());


        button.setOnAction(new ButtonHandler5(mag));
        button.setId("button");
        return button ;
    }

    class ButtonHandler5 implements EventHandler<ActionEvent> {

        private final ManagerModel mag;

        ButtonHandler5(ManagerModel mag) {
            this.mag = mag;
        }

        @Override
        public void handle(ActionEvent event) {
            Stage stage = new Stage();
            Scene scene = new Scene( editManagerPage.editManagerPage(mag) );
            stage.setWidth(800);
            stage.setHeight(600);
            stage.setScene(scene);
            stage.show();

        }

    }

}
