package controllerTest;

import com.example.kthimi.Controller.MainController;
import com.example.kthimi.Controller.Mockers.MockAuthenticationModel;
import com.example.kthimi.Model.AuthenticationModel;
import com.example.kthimi.View.MainView;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;

public class MainControllerTest extends ApplicationTest {

    private MainController mainController ;
    private MainView mainView;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {

//        mainView = new MainView();
//        System.out.println(mainView + "te testi");
//        mainController = new MainController(mainView);
//
//        // mainController.setView(mainView);
//        if(mainView == null){
//            System.out.println("sa nice");
//        } else{
//            System.out.println("lol");
//        }
//
//        primaryStage.setScene(new Scene(mainView.mainPage(), 600, 400));
//        primaryStage.show();

        this.primaryStage = primaryStage;
        mainView = new MainView();
        mainController = new MainController(mainView);

        mainView.setPrimaryStage(primaryStage);

        primaryStage.setScene(new Scene(mainView.mainPage(), 600, 400));
        primaryStage.show();
    }

    @Test
    public void testEmptyFieldsScenario() throws TimeoutException {
        assertNotNull(mainView);

        WaitForAsyncUtils.waitFor(4, TimeUnit.SECONDS, () -> !lookup("#Submit").queryAll().isEmpty());

        clickOn(mainView.getUsername()).eraseText(10);
        clickOn(mainView.getPassword()).eraseText(10);

        clickOn("#Submit");

        WaitForAsyncUtils.waitForFxEvents();

        WaitForAsyncUtils.waitFor(4, TimeUnit.SECONDS, () -> "Empty Fields".equals(mainView.getMainLoginWarning().getText()));

        assertEquals("Empty Fields", mainView.getMainLoginWarning().getText());
    }

    @Test
    public void testEmptyUsername() throws TimeoutException {
        assertNotNull(mainView);

        WaitForAsyncUtils.waitFor(4, TimeUnit.SECONDS, () -> !lookup("#Submit").queryAll().isEmpty());

        clickOn(mainView.getUsername()).eraseText(10);
        clickOn(mainView.getPassword()).write("some_password");

        clickOn("#Submit");

        WaitForAsyncUtils.waitForFxEvents();

        WaitForAsyncUtils.waitFor(4, TimeUnit.SECONDS, () -> "Empty Fields".equals(mainView.getMainLoginWarning().getText()));

        assertEquals("Empty Fields", mainView.getMainLoginWarning().getText());
    }

    @Test
    public void testEmptyPassword() throws TimeoutException {
        assertNotNull(mainView);

        WaitForAsyncUtils.waitFor(4, TimeUnit.SECONDS, () -> !lookup("#Submit").queryAll().isEmpty());

        clickOn(mainView.getUsername()).write("some_username");
        clickOn(mainView.getPassword()).eraseText(10);

        clickOn("#Submit");

        WaitForAsyncUtils.waitForFxEvents();

        WaitForAsyncUtils.waitFor(4, TimeUnit.SECONDS, () -> "Empty Fields".equals(mainView.getMainLoginWarning().getText()));

        assertEquals("Empty Fields", mainView.getMainLoginWarning().getText());
    }


    @Test
    public void testLibrarianAuthentication() throws TimeoutException, InterruptedException {

        assertNotNull(mainView);

        WaitForAsyncUtils.waitFor(4, TimeUnit.SECONDS, () -> !lookup("#Submit").queryAll().isEmpty());

        String validLibrarianUsername = "1";
        String validLibrarianPassword = "11";

        AuthenticationModel mockModel = new MockAuthenticationModel();
        mainController.setAuthenticationModel(mockModel);

        clickOn(mainView.getUsername()).write(validLibrarianUsername);
        Thread.sleep(1000);
        clickOn(mainView.getPassword()).write(validLibrarianPassword);
        Thread.sleep(1000);
        clickOn("#Submit");

        WaitForAsyncUtils.waitForFxEvents();
        WaitForAsyncUtils.waitFor(4, TimeUnit.SECONDS, () -> mainView.getMainLoginWarning().getText().isEmpty());

        assertTrue(mainView.getMainLoginWarning().getText().isEmpty());
    }


    @Test
    public void testManagerAuthentication() throws TimeoutException, InterruptedException {

        assertNotNull(mainView);

        WaitForAsyncUtils.waitFor(4, TimeUnit.SECONDS, () -> !lookup("#Submit").queryAll().isEmpty());

        String validManagerUsername = "2";
        String validManagerPassword = "22";

        AuthenticationModel mockModel = new MockAuthenticationModel();
        mainController.setAuthenticationModel(mockModel);



        clickOn(mainView.getUsername()).write(validManagerUsername);
        Thread.sleep(1000);
        clickOn(mainView.getPassword()).write(validManagerPassword);
        Thread.sleep(1000);
        clickOn("#Submit");

        // Allow some time for any potential asynchronous processes
        WaitForAsyncUtils.waitForFxEvents();
        WaitForAsyncUtils.waitFor(4, TimeUnit.SECONDS, () -> mainView.getMainLoginWarning().getText().isEmpty());

        assertTrue(mainView.getMainLoginWarning().getText().isEmpty());
    }


    @Test
    public void testAdminAuthentication() throws TimeoutException, InterruptedException {

        assertNotNull(mainView);

        WaitForAsyncUtils.waitFor(4, TimeUnit.SECONDS, () -> !lookup("#Submit").queryAll().isEmpty());

        String validAdminUsername = "3";
        String validAdminPassword = "33";

        AuthenticationModel mockModel = new MockAuthenticationModel();
        mainController.setAuthenticationModel(mockModel);

        clickOn(mainView.getUsername()).write(validAdminUsername);
        Thread.sleep(1000);
        clickOn(mainView.getPassword()).write(validAdminPassword);
        Thread.sleep(1000);
        clickOn("#Submit");

        WaitForAsyncUtils.waitForFxEvents();
        WaitForAsyncUtils.waitFor(4, TimeUnit.SECONDS, () -> mainView.getMainLoginWarning().getText().isEmpty());

        assertTrue(mainView.getMainLoginWarning().getText().isEmpty());
    }

    @Test
    public void testWrongInformation() throws TimeoutException, InterruptedException {
        AuthenticationModel mockModel = new MockAuthenticationModel();
        mainController.setAuthenticationModel(mockModel);

        String invalidUsername = "invalid_username";
        String invalidPassword = "invalid_password";

        clickOn(mainView.getUsername()).write(invalidUsername);
        clickOn(mainView.getPassword()).write(invalidPassword);
        clickOn("#Submit");

        // Allow time for asynchronous processes
//        Thread.sleep(1000); // Adjust this timing as needed
//        WaitForAsyncUtils.waitForFxEvents();
        WaitForAsyncUtils.waitFor(4, TimeUnit.SECONDS, () -> "Wrong Information".equals(mainView.getMainLoginWarning().getText()));

        assertEquals("Wrong Information", mainView.getMainLoginWarning().getText());

    }



}


