package com.example.kthimi.Controller;

import com.example.kthimi.Model.ManagerModel;
import com.example.kthimi.View.Manager.ManagerView;
import com.example.kthimi.View.Manager.SupplyManagerView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SupplyManagerController {

    ManagerModel managerModel;
    ManagerView managerView;
    SupplyManagerView supplyView;
    SupplyManagerController managerController;
    AddStockManagerController addStockManagerController;

    public SupplyManagerController(ManagerView managerView){
        this.managerView = managerView;
        this.managerModel = new ManagerModel();
        this.supplyView = new SupplyManagerView(managerView);
        this.managerController = this;
        //this.addStockManagerController = new AddStockManagerController();
        initSupplyButtonAction();
    }

    private void initSupplyButtonAction() {
//        managerView.getBttSupply().setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                handleSupply();
//            }
//        });
        managerView.getBttSupply().setOnAction(event->handleSupply());
    }

    public void handleSupply(){
        BorderPane supplyPage = supplyView.createSupplyPage(); // Create the supply view content

        Stage currentStage = (Stage) managerView.getBttSupply().getScene().getWindow();
        currentStage.setScene(new Scene(supplyPage, 800, 600));
        currentStage.show();

    }


}
