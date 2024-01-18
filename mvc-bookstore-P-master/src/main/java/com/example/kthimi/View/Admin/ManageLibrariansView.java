package com.example.kthimi.View.Admin;

import com.example.kthimi.Model.LibrarianModel;
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

public class ManageLibrariansView {

    AdminView adminView;
    Button bttBack = new Button("Back");
    ManageLibAddNew manageLibAddNew;
    EditLibrarianPageView editLibrarianPage;

    public ManageLibrariansView(AdminView adminView){
        this.adminView = adminView;

        this.manageLibAddNew = new ManageLibAddNew(this);
        this.editLibrarianPage = new EditLibrarianPageView(this);
    }


    public BorderPane administratorManageLibrariansPage(){

        BorderPane border = new BorderPane();

        Text textHeaderManager = new Text("Select Librarians");
        StackPane stackHeader = new StackPane();
        textHeaderManager.setFont(new Font(30));
        stackHeader.getChildren().add(textHeaderManager);
        stackHeader.setPadding(new Insets(20));
        border.setTop(stackHeader);

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
        ArrayList<LibrarianModel> librarians = ManagerModel.getLibrarians();
        System.out.println("Librarians to select: "+ librarians);
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setAlignment(Pos.CENTER);
        int k=0;
        int j=0;

        for (int i=0;i<librarians.size();i++) {
            if (i%5==0) {
                k=0;
                j++;
            }


            Button button = createButton4(librarians.get(i));

            grid.add(button,k++,j);

        }
        grid.add(bttAddNew, k, j);
        border.setCenter(grid);

        bttAddNew.setOnAction(event-> {
            bttAddNew.getScene().setRoot(manageLibAddNew.addLibrarian());
        });

        bttAddNew.setId("bttAddNew");

        return border;
    }

    //-----------------------------------------------

    private Button createButton4(LibrarianModel lib) {
        Button button;

        if (lib.getName()==null)
            button = new Button(lib.getUsername());

        else
            button = new Button(lib.getName());

        button.setId("button");
        button.setOnAction(new ButtonHandler4(lib));
        return button ;
    }

    class ButtonHandler4 implements EventHandler<ActionEvent> {

        private final LibrarianModel lib;

        ButtonHandler4(LibrarianModel lib) {
            this.lib = lib;
        }

        @Override
        public void handle(ActionEvent event) {
            Stage stage = new Stage();
            Scene scene = new Scene(editLibrarianPage.editLibrarianPage(lib));
            stage.setWidth(800);
            stage.setHeight(600);
            stage.setScene(scene);
            stage.show();

        }

    }
}
