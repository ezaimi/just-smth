package testFx.Admin;


import com.example.kthimi.Controller.MainController;
import com.example.kthimi.Controller.Mockers.MockAuthenticationModel;
import com.example.kthimi.Model.AuthenticationModel;
import com.example.kthimi.View.MainView;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatsTest extends ApplicationTest {

    private MainController mainController;
    private MainView mainView;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws IOException, InterruptedException, TimeoutException {
        this.primaryStage = primaryStage;
        mainView = new MainView();
        mainController = new MainController(mainView);

        mainView.setPrimaryStage(primaryStage);

        primaryStage.setScene(new Scene(mainView.mainPage(), 800, 600));
        primaryStage.setTitle("Bookstore Application");
        primaryStage.show();

    }

    @Test
    public void addStockTry() throws TimeoutException, InterruptedException, IOException {

        WaitForAsyncUtils.waitFor(4, TimeUnit.SECONDS, () -> !lookup("#Submit").queryAll().isEmpty());

        String validManagerUsername = "3";
        String validManagerPassword = "33";

        AuthenticationModel mockModel = new MockAuthenticationModel();
        mainController.setAuthenticationModel(mockModel);


        clickOn(mainView.getUsername()).write(validManagerUsername);
        Thread.sleep(1000);
        clickOn(mainView.getPassword()).write(validManagerPassword);
        Thread.sleep(1000);
        clickOn("#Submit");

        WaitForAsyncUtils.waitForFxEvents();

        Thread.sleep(1000);

        FxRobot robot = new FxRobot();


        robot.clickOn((Button) lookup("#bttStats").query());
        Thread.sleep(1000);

        robot.clickOn((Button) lookup("#bttSold").query());
        Thread.sleep(1000);

        robot.clickOn((Button) lookup("#bttBack").query());
        Thread.sleep(1000);

        robot.clickOn((Button) lookup("#bttBought").query());
        Thread.sleep(1000);

        robot.clickOn((Button) lookup("#bttBack").query());
        Thread.sleep(1000);

        robot.clickOn((Button) lookup("#bttIncome").query());
        Thread.sleep(1000);

        robot.clickOn((Button) lookup("#bttBack").query());
        Thread.sleep(1000);

        robot.clickOn((Button) lookup("#bttCost").query());
        Thread.sleep(1000);

        robot.clickOn((Button) lookup("#bttBack").query());
        Thread.sleep(1000);




    }



}
