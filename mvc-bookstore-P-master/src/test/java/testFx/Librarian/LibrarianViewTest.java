//package testFx;
//
//import com.example.kthimi.Controller.BookController;
//import com.example.kthimi.Controller.MainController;
//import com.example.kthimi.Controller.Mockers.MockLibrarianView;
//import com.example.kthimi.Controller.Mockers.MockStockBookRepository;
//import com.example.kthimi.Model.BookModel;
//import com.example.kthimi.View.Librarian.LibrarianView;
//import com.example.kthimi.View.MainView;
//import javafx.application.Platform;
//import javafx.scene.Scene;
//import javafx.scene.control.ComboBox;
//import javafx.scene.control.TextField;
//import javafx.stage.Stage;
//import org.junit.jupiter.api.Test;
//import org.testfx.framework.junit5.ApplicationTest;
//import org.testfx.util.WaitForAsyncUtils;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//public class LibrarianViewTest extends ApplicationTest {
//
//    MockLibrarianView mockLibrarianView;
//   // LibrarianView librarianView;
//    MainController mainController;
//    File tempFile;
//
//    @Override
//    public void start(Stage stage) {
//        mainController = new MainController(new MainView());
//
//        mockLibrarianView = new MockLibrarianView();
//
//        assertNotNull(mockLibrarianView);
//
//        Scene scene = new Scene(mockLibrarianView.getLibrarianMainPage());
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    @Test
//    public void testHandleAddWithMockedData() {
//        assertNotNull(mockLibrarianView);
//
//        Platform.runLater(() -> {
////            Stage stage = new Stage();
////            Scene scene = new Scene(mockLibrarianView.getLibrarianMainPage());
////            stage.setScene(scene);
////            stage.show();
//
//            // Wait for the stage to be shown
//            WaitForAsyncUtils.waitForFxEvents();
//
//            ComboBox<String> comboBox = mockLibrarianView.getComboBoxLibrarian();
//            TextField quantityField = mockLibrarianView.getQuantityField();
//
//            clickOn(comboBox).clickOn("Test Book");
//            clickOn(quantityField).write("5");
//            clickOn(mockLibrarianView.getBttAdd());
//
//            assertEquals("Added", mockLibrarianView.getWarningsLibrarian().getText());
//        });
//    }
//
//    private MockLibrarianView getLibrarianView() {
//        try {
//            tempFile = File.createTempFile("testBooks", ".bin");
//
//            MockStockBookRepository mockRepository = new MockStockBookRepository();
//            ArrayList<BookModel> testData = new ArrayList<>();
//            testData.add(new BookModel("123TestISBN", "Title", "Category", "Supplier", 20.0, 15.0, "Author", 50));
//
//            BookController.STOCK_FILE_PATH = tempFile.getAbsolutePath();
//            // Save test data to the temporary file
//            mockRepository.setStockBooks(testData);
//
//            BookController bookController = new BookController(mockRepository);
//            LibrarianView librarianView = new LibrarianView(/* any necessary arguments */);
//
//            librarianView.setBookController(bookController);
//
//            return mockLibrarianView;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @Override
//    public void stop() {
//        if (tempFile != null) {
//            tempFile.delete();
//        }
//    }
//}


package testFx.Librarian;

import com.example.kthimi.Controller.BookController;
import com.example.kthimi.Controller.MainController;
import com.example.kthimi.Controller.Mockers.FileBasedStockBookRepository;
import com.example.kthimi.Controller.Mockers.MockStockBookRepository;
import com.example.kthimi.Model.BookModel;
import com.example.kthimi.View.Librarian.LibrarianView;
import com.example.kthimi.View.MainView;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LibrarianViewTest extends ApplicationTest {

    private LibrarianView librarianView;
    private MainController mainController;
    private MainView mainView;
    private File testFile;

//    @BeforeEach
//    public void setUp() {
//        try {
//            testFile = createTestFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    @AfterEach
//    public void tearDown() {
//        if (testFile != null) {
//            testFile.delete();
//        }
//    }

    @Override
    public void start(Stage stage) throws IOException {

        testFile = createTestFile();
        BookController.STOCK_FILE_PATH = testFile.getAbsolutePath();
        FileBasedStockBookRepository.STOCK_FILE_PATH = testFile.getAbsolutePath();

        //createTestFile();
        MainView mainView = new MainView();
        mainController = new MainController(mainView);
        librarianView = new LibrarianView("TestUser", mainView);
        //librarianView = getLibrarianViewWithTestBooks(mainView);

        assertNotNull(librarianView);

        stage.setScene(new Scene(librarianView.getLibrarianMainPage(), 800, 600));
        stage.show();
    }

    private File createTestFile() throws IOException {
        File tempFile = File.createTempFile("testBooks", ".bin");
        String testFilePath = tempFile.getAbsolutePath();

        ArrayList<BookModel> testData = new ArrayList<>();
        testData.add(new BookModel("1234567899876", "TestBook", "Category", "Supplier", 20.0, 15.0, "Author", 50));

        try (ObjectOutputStream objout = new ObjectOutputStream(new FileOutputStream(testFilePath))) {
            objout.writeObject(testData);
        } catch (IOException e) {
            e.printStackTrace();
            throw e; // Propagate the exception for test failure indication
        }

        return tempFile;
    }


    @Test
    public void testHandleAdd() {
        assertNotNull(librarianView);

        ComboBox<String> comboBox = lookup("#comboBoxLibrarian").query();
        TextField quantityField = lookup("#quantity").query();
        FxRobot robot = new FxRobot();

        robot.interact(() -> comboBox.getSelectionModel().select("1234567899876"));
        robot.clickOn(quantityField).write("5");
        robot.clickOn((Button) lookup("#bttAdd").query());

        assertEquals("Added", ((TextField) lookup("#warningsLibrarian").query()).getText());
    }


}














//    @Test
//    public void testHandleAdd() {
//        assertNotNull(librarianView);
//        //LibrarianView librarianView = getLibrarianViewWithTestBooks();
//
//        ComboBox<String> comboBox = lookup("#comboBoxLibrarian").query();
//        TextField quantityField = lookup("#quantity").query();
//        FxRobot robot = new FxRobot();
//
//        robot.clickOn(comboBox).clickOn("Test Book");
//        robot.clickOn(quantityField).write("5");
//        robot.clickOn((Node)lookup("#bttAdd").query());
//
//        assertEquals("Added", ((TextField) lookup("#warningsLibrarian").query()).getText());
//    }
//
//    private LibrarianView getLibrarianViewWithTestBooks(MainView mainView) {
//        try {
//            tempFile = File.createTempFile("testBooks", ".bin");
//            BookController.STOCK_FILE_PATH = tempFile.getAbsolutePath();
//
//            MockStockBookRepository mockRepository = new MockStockBookRepository();
//            ArrayList<BookModel> testData = new ArrayList<>();
//            testData.add(new BookModel("TestISBN", "Test Book", "Category", "Supplier", 20.0, 15.0, "Author", 50));
//            mockRepository.setStockBooks(testData);
//
//            FileBasedStockBookRepository.STOCK_FILE_PATH = tempFile.getAbsolutePath();
//
//            BookController bookController = new BookController(mockRepository);
//
//            LibrarianView librarianView = new LibrarianView("TestUser", mainView);
//
//            librarianView.setBookController(bookController);
//
//            return librarianView;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @Override
//    public void stop() {
//        if (tempFile != null) {
//            tempFile.delete();
//        }
//    }

