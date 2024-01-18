package testFx.Manager;


import com.example.kthimi.Controller.BookController;
import com.example.kthimi.Controller.LibrarianFuncController;
import com.example.kthimi.Controller.MainController;
import com.example.kthimi.Controller.Mockers.FileBasedStockBookRepository;
import com.example.kthimi.Controller.Mockers.MockAuthenticationModel;
import com.example.kthimi.Model.AuthenticationModel;
import com.example.kthimi.Model.BookModel;
import com.example.kthimi.Model.LibrarianModel;
import com.example.kthimi.Model.ManagerModel;
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

public class AddStockTest extends ApplicationTest {

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

        String validManagerUsername = "2";
        String validManagerPassword = "22";

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


        ManagerView managerView = new ManagerView("2",mainView);

        FxRobot robot = new FxRobot();

        robot.clickOn((Button) lookup("#bttSupply").query());
        Thread.sleep(1000);
        robot.clickOn((Button) lookup("#bttAddStock").query());
        Thread.sleep(1000);
        robot.clickOn((Button) lookup("#button").query());
        Thread.sleep(1000);
        robot.clickOn((Button) lookup("#bttNewBook").query());

        TextField bookISBNField = lookup("#bookISBN").query();
        TextField titleField = lookup("#title").query();
        GridPane gridSupplier = lookup("#gridSupplier").query();
        TextField originalPrice = lookup("#originalPrice").query();
        TextField sellingPrice = lookup("#sellingPrice").query();
        TextField authorField = lookup("#author").query();
        TextField quantityField = lookup("#quantity").query();



        //bookISBN
        robot.clickOn(bookISBNField).write("1234567893628");
        //title
        robot.clickOn(titleField).write("hello");
        //supplier
        robot.clickOn(gridSupplier.getChildren().get(1));
        //originalPrice
        robot.clickOn(originalPrice).write("12");
        //sellingPrice
        robot.clickOn(sellingPrice).write("20");
        //author
        robot.clickOn(authorField).write("Someone");
        //quantity
        robot.clickOn(quantityField).write("10");

        //stockCategoryAddBook
        robot.clickOn((Button) lookup("#stockCategoryAddBook").query());


        assertEquals("Added",((TextField) lookup("#addedOrNotStockCategory").query()).getText());


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
