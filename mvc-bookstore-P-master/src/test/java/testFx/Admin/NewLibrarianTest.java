package testFx.Admin;



import com.example.kthimi.Controller.BookController;
import com.example.kthimi.Controller.LibrarianFuncController;
import com.example.kthimi.Controller.MainController;
import com.example.kthimi.Controller.Mockers.FileBasedStockBookRepository;
import com.example.kthimi.Controller.Mockers.MockAuthenticationModel;
import com.example.kthimi.Model.AuthenticationModel;
import com.example.kthimi.Model.BookModel;
import com.example.kthimi.Model.LibrarianModel;
import com.example.kthimi.Model.ManagerModel;
import com.example.kthimi.View.Admin.AdminView;
import com.example.kthimi.View.MainView;
import com.example.kthimi.View.Manager.ManagerView;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NewLibrarianTest extends ApplicationTest {

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
    public void newLibrarianTry() throws TimeoutException, InterruptedException, IOException {

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

        File tempFile = createTestFile();
        BookController.STOCK_FILE_PATH = tempFile.getAbsolutePath();
        FileBasedStockBookRepository.STOCK_FILE_PATH = tempFile.getAbsolutePath();
        LibrarianFuncController.STOCK_FILE_PATH = tempFile.getAbsolutePath();
        LibrarianModel.STOCK_FILE_PATH = tempFile.getAbsolutePath();


        AdminView managerView = new AdminView("3",mainView);

        FxRobot robot = new FxRobot();

        robot.clickOn((Button) lookup("#bttManageLibrarians").query());
        Thread.sleep(1000);
        robot.clickOn((Button) lookup("#bttAddNew").query());
        Thread.sleep(1000);

        TextField name = lookup("#name").query();
        TextField password = lookup("#password").query();
        TextField username = lookup("#username").query();
        TextField salary = lookup("#salary").query();
        TextField phone = lookup("#phone").query();
        TextField email = lookup("#email").query();


        robot.clickOn(name).write("Drake");
        robot.clickOn(password).write("shqipe");
        robot.clickOn(username).write("Ali");
        robot.clickOn(salary).write("500000");
        robot.clickOn(phone).write("+355698654312");
        robot.clickOn(email).write("drake@gmail.com");

        robot.clickOn((Button) lookup("#bttAdd").query());

        assertEquals("Invalid password",((TextField) lookup("#libWarningNew").query()).getText());


    }


    public File createTestFile() throws IOException {
        File tempFile = File.createTempFile("testBooks", ".bin");
        String testFilePath = tempFile.getAbsolutePath();

        ArrayList<BookModel> testData = new ArrayList<>();
        testData.add(new BookModel("1234567898765", "LOT", "War", "Supplier", 20.0, 15.0, "Author", 50));

        try (ObjectOutputStream objout = new ObjectOutputStream(new FileOutputStream(testFilePath))) {
            objout.writeObject(testData);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }

        return tempFile;
    }

}
