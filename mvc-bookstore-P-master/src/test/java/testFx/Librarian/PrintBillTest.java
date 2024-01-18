package testFx.Librarian;

import com.example.kthimi.Controller.BookController;
import com.example.kthimi.Controller.LibrarianFuncController;
import com.example.kthimi.Controller.MainController;
import com.example.kthimi.Controller.Mockers.FileBasedStockBookRepository;
import com.example.kthimi.Controller.Mockers.MockAuthenticationModel;
import com.example.kthimi.Model.AuthenticationModel;
import com.example.kthimi.Model.BookModel;
import com.example.kthimi.Model.LibrarianModel;
import com.example.kthimi.View.Librarian.LibrarianView;
import com.example.kthimi.View.MainView;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
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
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.testfx.util.WaitForAsyncUtils.waitForFxEvents;



public class PrintBillTest extends ApplicationTest {

    private MainController mainController;
    private MainView mainView;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws IOException, InterruptedException, TimeoutException {
        //kjo eshte view kryesore dhe eshte njesoj si tek starti i BookstoreApplication class
        this.primaryStage = primaryStage;
        mainView = new MainView();
        mainController = new MainController(mainView);

        mainView.setPrimaryStage(primaryStage);

        primaryStage.setScene(new Scene(mainView.mainPage(), 800, 600));
        primaryStage.setTitle("Bookstore Application");
        primaryStage.show();

    }

    @Test
    public void runSystemTest() throws IOException, InterruptedException, TimeoutException {
        //krijojm nje temp file testBooks dhe fusim nje test Book brenda ne menyre qe kjo klasa e testit
        //te mos perdor dhe mos e modifikoj(te mos ja uli quantityn) librit real
        File tempFile = createTestFile();
        //i ndryshojme pathet te gjitha klasave qe perdorin Books.bin ne menyre qe kur klasa e testit te behet run
        //pathet te ndryshohen ne testBooks.bin
        BookController.STOCK_FILE_PATH = tempFile.getAbsolutePath();
        FileBasedStockBookRepository.STOCK_FILE_PATH = tempFile.getAbsolutePath();
        LibrarianFuncController.STOCK_FILE_PATH = tempFile.getAbsolutePath();
        LibrarianModel.STOCK_FILE_PATH = tempFile.getAbsolutePath();

        //nje wait per programin qe te presi derisa te klikohet butoni submit
        WaitForAsyncUtils.waitFor(5, TimeUnit.SECONDS, () -> !lookup("#Submit").queryAll().isEmpty());
        //keto jane te credencialet per tu loguar tek view e librarian qe kompjuteri do te shkruaj ne hapesirat boshe
        String validLibrarianUsername = "1";
        String validLibrarianPassword = "11";

        //mockModeli permban kredencialet e mockuara qe useri te logohet tek librarian
        //psh nese programi i ka kredencialet u:1 pass:11 atehere ne mock ne mund te perdorim kredenciale sipas deshires tone
        //per ta lehtesuar punen i kam vendosur kredencialet e librarian tek mocku njesoj sic jane ne te vertet
        AuthenticationModel mockModel = new MockAuthenticationModel();
        mainController.setAuthenticationModel(mockModel);

        clickOn(mainView.getUsername()).write(validLibrarianUsername);
        Thread.sleep(1000);
        clickOn(mainView.getPassword()).write(validLibrarianPassword);
        Thread.sleep(1000);
        clickOn("#Submit");

        waitForFxEvents();

        //assertNotNull(mainView.getMainLoginWarning());
       // assert(mainView.getMainLoginWarning().getText().isEmpty());




        //pasi eshte hapur view e librarian kompjuteri do te selektoj librin Test nga comboboxi dhe
        //do te shkruaj quantityn 5 tek textfieldi dhe do klikoj butonin add dhe me pas bill
        LibrarianView librarianView = new LibrarianView("1", mainView);
        assertNotNull(librarianView);

        ComboBox<String> comboBox = lookup("#comboBoxLibrarian").query();
        TextField quantityField = lookup("#quantity").query();
        FxRobot robot = new FxRobot();

       // robot.interact(() -> comboBox.getSelectionModel().select("Test Book"));
        robot.interact(() -> {
            // Get the items from the ComboBox
            ObservableList<String> items = comboBox.getItems();

            // Check if there are items in the ComboBox
            if (!items.isEmpty()) {
                // Generate a random index
                int randomIndex = new Random().nextInt(items.size());

                // Select the item at the random index
                comboBox.getSelectionModel().select(randomIndex);
            }
        });
        robot.clickOn(quantityField).write("5");
        robot.clickOn((Button) lookup("#bttAdd").query());

        assertEquals("Added", ((TextField) lookup("#warningsLibrarian").query()).getText());

//        robot.clickOn((Button) lookup("#bttBill").query());
//
//        assertEquals("Bill File Created!", ((TextField) lookup("#warningsLibrarian").query()).getText());


        tempFile.delete();
    }

    @Test
    public void runSystemEmptyQuantityTest() throws IOException, InterruptedException, TimeoutException {
        //krijojm nje temp file testBooks dhe fusim nje test Book brenda ne menyre qe kjo klasa e testit
        //te mos perdor dhe mos e modifikoj(te mos ja uli quantityn) librit real
        File tempFile = createTestFile();
        //i ndryshojme pathet te gjitha klasave qe perdorin Books.bin ne menyre qe kur klasa e testit te behet run
        //pathet te ndryshohen ne testBooks.bin
        BookController.STOCK_FILE_PATH = tempFile.getAbsolutePath();
        FileBasedStockBookRepository.STOCK_FILE_PATH = tempFile.getAbsolutePath();
        LibrarianFuncController.STOCK_FILE_PATH = tempFile.getAbsolutePath();
        LibrarianModel.STOCK_FILE_PATH = tempFile.getAbsolutePath();

        //nje wait per programin qe te presi derisa te klikohet butoni submit
        WaitForAsyncUtils.waitFor(5, TimeUnit.SECONDS, () -> !lookup("#Submit").queryAll().isEmpty());
        //keto jane te credencialet per tu loguar tek view e librarian qe kompjuteri do te shkruaj ne hapesirat boshe
        String validLibrarianUsername = "1";
        String validLibrarianPassword = "11";

        //mockModeli permban kredencialet e mockuara qe useri te logohet tek librarian
        //psh nese programi i ka kredencialet u:1 pass:11 atehere ne mock ne mund te perdorim kredenciale sipas deshires tone
        //per ta lehtesuar punen i kam vendosur kredencialet e librarian tek mocku njesoj sic jane ne te vertet
        AuthenticationModel mockModel = new MockAuthenticationModel();
        mainController.setAuthenticationModel(mockModel);

        clickOn(mainView.getUsername()).write(validLibrarianUsername);
        Thread.sleep(1000);
        clickOn(mainView.getPassword()).write(validLibrarianPassword);
        Thread.sleep(1000);
        clickOn("#Submit");

        waitForFxEvents();

        //assertNotNull(mainView.getMainLoginWarning());
        // assert(mainView.getMainLoginWarning().getText().isEmpty());




        //pasi eshte hapur view e librarian kompjuteri do te selektoj librin Test nga comboboxi dhe
        //do te shkruaj quantityn 5 tek textfieldi dhe do klikoj butonin add dhe me pas bill
        LibrarianView librarianView = new LibrarianView("1", mainView);
        assertNotNull(librarianView);

        ComboBox<String> comboBox = lookup("#comboBoxLibrarian").query();
        TextField quantityField = lookup("#quantity").query();
        FxRobot robot = new FxRobot();

        robot.interact(() -> comboBox.getSelectionModel().select("Test Book"));
        //robot.clickOn(quantityField).write("5");
        robot.clickOn((Button) lookup("#bttAdd").query());

        // assertEquals("Added", ((TextField) lookup("#warningsLibrarian").query()).getText());

        robot.clickOn((Button) lookup("#bttBill").query());

        assertEquals("Failed, Empty Quantity", ((TextField) lookup("#warningsLibrarian").query()).getText());


        tempFile.delete();
    }

    @Test
    public void runSystemEmptyISBNTest() throws IOException, InterruptedException, TimeoutException {
        //krijojm nje temp file testBooks dhe fusim nje test Book brenda ne menyre qe kjo klasa e testit
        //te mos perdor dhe mos e modifikoj(te mos ja uli quantityn) librit real
        File tempFile = createTestFile();
        //i ndryshojme pathet te gjitha klasave qe perdorin Books.bin ne menyre qe kur klasa e testit te behet run
        //pathet te ndryshohen ne testBooks.bin
        BookController.STOCK_FILE_PATH = tempFile.getAbsolutePath();
        FileBasedStockBookRepository.STOCK_FILE_PATH = tempFile.getAbsolutePath();
        LibrarianFuncController.STOCK_FILE_PATH = tempFile.getAbsolutePath();
        LibrarianModel.STOCK_FILE_PATH = tempFile.getAbsolutePath();

        //nje wait per programin qe te presi derisa te klikohet butoni submit
        WaitForAsyncUtils.waitFor(5, TimeUnit.SECONDS, () -> !lookup("#Submit").queryAll().isEmpty());
        //keto jane te credencialet per tu loguar tek view e librarian qe kompjuteri do te shkruaj ne hapesirat boshe
        String validLibrarianUsername = "1";
        String validLibrarianPassword = "11";

        //mockModeli permban kredencialet e mockuara qe useri te logohet tek librarian
        //psh nese programi i ka kredencialet u:1 pass:11 atehere ne mock ne mund te perdorim kredenciale sipas deshires tone
        //per ta lehtesuar punen i kam vendosur kredencialet e librarian tek mocku njesoj sic jane ne te vertet
        AuthenticationModel mockModel = new MockAuthenticationModel();
        mainController.setAuthenticationModel(mockModel);

        clickOn(mainView.getUsername()).write(validLibrarianUsername);
        Thread.sleep(1000);
        clickOn(mainView.getPassword()).write(validLibrarianPassword);
        Thread.sleep(1000);
        clickOn("#Submit");

        waitForFxEvents();

        //assertNotNull(mainView.getMainLoginWarning());
        // assert(mainView.getMainLoginWarning().getText().isEmpty());




        //pasi eshte hapur view e librarian kompjuteri do te selektoj librin Test nga comboboxi dhe
        //do te shkruaj quantityn 5 tek textfieldi dhe do klikoj butonin add dhe me pas bill
        LibrarianView librarianView = new LibrarianView("1", mainView);
        assertNotNull(librarianView);

        ComboBox<String> comboBox = lookup("#comboBoxLibrarian").query();
        TextField quantityField = lookup("#quantity").query();
        FxRobot robot = new FxRobot();

        //robot.interact(() -> comboBox.getSelectionModel().select("Test Book"));
        robot.clickOn(quantityField).write("5");
        robot.clickOn((Button) lookup("#bttAdd").query());

        // assertEquals("Added", ((TextField) lookup("#warningsLibrarian").query()).getText());

        robot.clickOn((Button) lookup("#bttBill").query());

        assertEquals("Failed to add,Empty ISBN", ((TextField) lookup("#warningsLibrarian").query()).getText());


        tempFile.delete();
    }

    @Test
    public void runSystemEmptyFieldsTest() throws IOException, InterruptedException, TimeoutException {
        //krijojm nje temp file testBooks dhe fusim nje test Book brenda ne menyre qe kjo klasa e testit
        //te mos perdor dhe mos e modifikoj(te mos ja uli quantityn) librit real
        File tempFile = createTestFile();
        //i ndryshojme pathet te gjitha klasave qe perdorin Books.bin ne menyre qe kur klasa e testit te behet run
        //pathet te ndryshohen ne testBooks.bin
        BookController.STOCK_FILE_PATH = tempFile.getAbsolutePath();
        FileBasedStockBookRepository.STOCK_FILE_PATH = tempFile.getAbsolutePath();
        LibrarianFuncController.STOCK_FILE_PATH = tempFile.getAbsolutePath();
        LibrarianModel.STOCK_FILE_PATH = tempFile.getAbsolutePath();

        //nje wait per programin qe te presi derisa te klikohet butoni submit
        WaitForAsyncUtils.waitFor(5, TimeUnit.SECONDS, () -> !lookup("#Submit").queryAll().isEmpty());
        //keto jane te credencialet per tu loguar tek view e librarian qe kompjuteri do te shkruaj ne hapesirat boshe
        String validLibrarianUsername = "1";
        String validLibrarianPassword = "11";

        //mockModeli permban kredencialet e mockuara qe useri te logohet tek librarian
        //psh nese programi i ka kredencialet u:1 pass:11 atehere ne mock ne mund te perdorim kredenciale sipas deshires tone
        //per ta lehtesuar punen i kam vendosur kredencialet e librarian tek mocku njesoj sic jane ne te vertet
        AuthenticationModel mockModel = new MockAuthenticationModel();
        mainController.setAuthenticationModel(mockModel);

        clickOn(mainView.getUsername()).write(validLibrarianUsername);
        Thread.sleep(1000);
        clickOn(mainView.getPassword()).write(validLibrarianPassword);
        Thread.sleep(1000);
        clickOn("#Submit");

        waitForFxEvents();

        //assertNotNull(mainView.getMainLoginWarning());
        // assert(mainView.getMainLoginWarning().getText().isEmpty());




        //pasi eshte hapur view e librarian kompjuteri do te selektoj librin Test nga comboboxi dhe
        //do te shkruaj quantityn 5 tek textfieldi dhe do klikoj butonin add dhe me pas bill
        LibrarianView librarianView = new LibrarianView("1", mainView);
        assertNotNull(librarianView);

        ComboBox<String> comboBox = lookup("#comboBoxLibrarian").query();
        TextField quantityField = lookup("#quantity").query();
        FxRobot robot = new FxRobot();

        //robot.interact(() -> comboBox.getSelectionModel().select("Test Book"));
        //robot.clickOn(quantityField).write("5");
        robot.clickOn((Button) lookup("#bttAdd").query());

        // assertEquals("Added", ((TextField) lookup("#warningsLibrarian").query()).getText());

        robot.clickOn((Button) lookup("#bttBill").query());

        assertEquals("Failed, Empty fields", ((TextField) lookup("#warningsLibrarian").query()).getText());


        tempFile.delete();
    }

    @Test
    public void runSystemInvalidQuantityTest() throws IOException, InterruptedException, TimeoutException {
        //krijojm nje temp file testBooks dhe fusim nje test Book brenda ne menyre qe kjo klasa e testit
        //te mos perdor dhe mos e modifikoj(te mos ja uli quantityn) librit real
        File tempFile = createTestFile();
        //i ndryshojme pathet te gjitha klasave qe perdorin Books.bin ne menyre qe kur klasa e testit te behet run
        //pathet te ndryshohen ne testBooks.bin
        BookController.STOCK_FILE_PATH = tempFile.getAbsolutePath();
        FileBasedStockBookRepository.STOCK_FILE_PATH = tempFile.getAbsolutePath();
        LibrarianFuncController.STOCK_FILE_PATH = tempFile.getAbsolutePath();
        LibrarianModel.STOCK_FILE_PATH = tempFile.getAbsolutePath();

        //nje wait per programin qe te presi derisa te klikohet butoni submit
        WaitForAsyncUtils.waitFor(5, TimeUnit.SECONDS, () -> !lookup("#Submit").queryAll().isEmpty());
        //keto jane te credencialet per tu loguar tek view e librarian qe kompjuteri do te shkruaj ne hapesirat boshe
        String validLibrarianUsername = "1";
        String validLibrarianPassword = "11";

        //mockModeli permban kredencialet e mockuara qe useri te logohet tek librarian
        //psh nese programi i ka kredencialet u:1 pass:11 atehere ne mock ne mund te perdorim kredenciale sipas deshires tone
        //per ta lehtesuar punen i kam vendosur kredencialet e librarian tek mocku njesoj sic jane ne te vertet
        AuthenticationModel mockModel = new MockAuthenticationModel();
        mainController.setAuthenticationModel(mockModel);

        clickOn(mainView.getUsername()).write(validLibrarianUsername);
        Thread.sleep(1000);
        clickOn(mainView.getPassword()).write(validLibrarianPassword);
        Thread.sleep(1000);
        clickOn("#Submit");

        waitForFxEvents();

        //assertNotNull(mainView.getMainLoginWarning());
        // assert(mainView.getMainLoginWarning().getText().isEmpty());




        //pasi eshte hapur view e librarian kompjuteri do te selektoj librin Test nga comboboxi dhe
        //do te shkruaj quantityn 5 tek textfieldi dhe do klikoj butonin add dhe me pas bill
        LibrarianView librarianView = new LibrarianView("1", mainView);
        assertNotNull(librarianView);

        ComboBox<String> comboBox = lookup("#comboBoxLibrarian").query();
        TextField quantityField = lookup("#quantity").query();
        FxRobot robot = new FxRobot();

        robot.interact(() -> comboBox.getSelectionModel().select("Test Book"));
        robot.clickOn(quantityField).write("-1");
        robot.clickOn((Button) lookup("#bttAdd").query());

        // assertEquals("Added", ((TextField) lookup("#warningsLibrarian").query()).getText());

        robot.clickOn((Button) lookup("#bttBill").query());

        assertEquals("Failed, Invalid Quantity", ((TextField) lookup("#warningsLibrarian").query()).getText());


        tempFile.delete();
    }

    @Test
    public void runSystemInvalidQuantityZeroTest() throws IOException, InterruptedException, TimeoutException {
        //krijojm nje temp file testBooks dhe fusim nje test Book brenda ne menyre qe kjo klasa e testit
        //te mos perdor dhe mos e modifikoj(te mos ja uli quantityn) librit real
        File tempFile = createTestFile();
        //i ndryshojme pathet te gjitha klasave qe perdorin Books.bin ne menyre qe kur klasa e testit te behet run
        //pathet te ndryshohen ne testBooks.bin
        BookController.STOCK_FILE_PATH = tempFile.getAbsolutePath();
        FileBasedStockBookRepository.STOCK_FILE_PATH = tempFile.getAbsolutePath();
        LibrarianFuncController.STOCK_FILE_PATH = tempFile.getAbsolutePath();
        LibrarianModel.STOCK_FILE_PATH = tempFile.getAbsolutePath();

        //nje wait per programin qe te presi derisa te klikohet butoni submit
        WaitForAsyncUtils.waitFor(5, TimeUnit.SECONDS, () -> !lookup("#Submit").queryAll().isEmpty());
        //keto jane te credencialet per tu loguar tek view e librarian qe kompjuteri do te shkruaj ne hapesirat boshe
        String validLibrarianUsername = "1";
        String validLibrarianPassword = "11";

        //mockModeli permban kredencialet e mockuara qe useri te logohet tek librarian
        //psh nese programi i ka kredencialet u:1 pass:11 atehere ne mock ne mund te perdorim kredenciale sipas deshires tone
        //per ta lehtesuar punen i kam vendosur kredencialet e librarian tek mocku njesoj sic jane ne te vertet
        AuthenticationModel mockModel = new MockAuthenticationModel();
        mainController.setAuthenticationModel(mockModel);

        clickOn(mainView.getUsername()).write(validLibrarianUsername);
        Thread.sleep(1000);
        clickOn(mainView.getPassword()).write(validLibrarianPassword);
        Thread.sleep(1000);
        clickOn("#Submit");

        waitForFxEvents();

        //assertNotNull(mainView.getMainLoginWarning());
        // assert(mainView.getMainLoginWarning().getText().isEmpty());




        //pasi eshte hapur view e librarian kompjuteri do te selektoj librin Test nga comboboxi dhe
        //do te shkruaj quantityn 5 tek textfieldi dhe do klikoj butonin add dhe me pas bill
        LibrarianView librarianView = new LibrarianView("1", mainView);
        assertNotNull(librarianView);

        ComboBox<String> comboBox = lookup("#comboBoxLibrarian").query();
        TextField quantityField = lookup("#quantity").query();
        FxRobot robot = new FxRobot();

        robot.interact(() -> comboBox.getSelectionModel().select("Test Book"));
        robot.clickOn(quantityField).write("0");
        robot.clickOn((Button) lookup("#bttAdd").query());

        // assertEquals("Added", ((TextField) lookup("#warningsLibrarian").query()).getText());

        robot.clickOn((Button) lookup("#bttBill").query());

        assertEquals("Failed, Invalid Quantity", ((TextField) lookup("#warningsLibrarian").query()).getText());


        tempFile.delete();
    }

    @Test
    public void runSystemNotEnoughStockTest() throws IOException, InterruptedException, TimeoutException {
        //krijojm nje temp file testBooks dhe fusim nje test Book brenda ne menyre qe kjo klasa e testit
        //te mos perdor dhe mos e modifikoj(te mos ja uli quantityn) librit real
        File tempFile = createTestFile();
        //i ndryshojme pathet te gjitha klasave qe perdorin Books.bin ne menyre qe kur klasa e testit te behet run
        //pathet te ndryshohen ne testBooks.bin
        BookController.STOCK_FILE_PATH = tempFile.getAbsolutePath();
        FileBasedStockBookRepository.STOCK_FILE_PATH = tempFile.getAbsolutePath();
        LibrarianFuncController.STOCK_FILE_PATH = tempFile.getAbsolutePath();
        LibrarianModel.STOCK_FILE_PATH = tempFile.getAbsolutePath();

        //nje wait per programin qe te presi derisa te klikohet butoni submit
        WaitForAsyncUtils.waitFor(5, TimeUnit.SECONDS, () -> !lookup("#Submit").queryAll().isEmpty());
        //keto jane te credencialet per tu loguar tek view e librarian qe kompjuteri do te shkruaj ne hapesirat boshe
        String validLibrarianUsername = "1";
        String validLibrarianPassword = "11";

        //mockModeli permban kredencialet e mockuara qe useri te logohet tek librarian
        //psh nese programi i ka kredencialet u:1 pass:11 atehere ne mock ne mund te perdorim kredenciale sipas deshires tone
        //per ta lehtesuar punen i kam vendosur kredencialet e librarian tek mocku njesoj sic jane ne te vertet
        AuthenticationModel mockModel = new MockAuthenticationModel();
        mainController.setAuthenticationModel(mockModel);

        clickOn(mainView.getUsername()).write(validLibrarianUsername);
        Thread.sleep(1000);
        clickOn(mainView.getPassword()).write(validLibrarianPassword);
        Thread.sleep(1000);
        clickOn("#Submit");

        waitForFxEvents();

        //assertNotNull(mainView.getMainLoginWarning());
        // assert(mainView.getMainLoginWarning().getText().isEmpty());




        //pasi eshte hapur view e librarian kompjuteri do te selektoj librin Test nga comboboxi dhe
        //do te shkruaj quantityn 5 tek textfieldi dhe do klikoj butonin add dhe me pas bill
        LibrarianView librarianView = new LibrarianView("1", mainView);
        assertNotNull(librarianView);

        ComboBox<String> comboBox = lookup("#comboBoxLibrarian").query();
        TextField quantityField = lookup("#quantity").query();
        FxRobot robot = new FxRobot();

        robot.interact(() -> comboBox.getSelectionModel().select("Test Book"));
        robot.clickOn(quantityField).write("100");
        robot.clickOn((Button) lookup("#bttAdd").query());

        // assertEquals("Added", ((TextField) lookup("#warningsLibrarian").query()).getText());

        robot.clickOn((Button) lookup("#bttBill").query());

        assertEquals("Failed,not enough stock", ((TextField) lookup("#warningsLibrarian").query()).getText());


        tempFile.delete();
    }


    private File createTestFile() throws IOException {
        File tempFile = File.createTempFile("testBooks", ".bin");
        String testFilePath = tempFile.getAbsolutePath();

        ArrayList<BookModel> testData = new ArrayList<>();
        testData.add(new BookModel("1234567898765", "Test Book", "War", "Supplier", 20.0, 15.0, "Author", 50));

        try (ObjectOutputStream objout = new ObjectOutputStream(new FileOutputStream(testFilePath))) {
            objout.writeObject(testData);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }

        return tempFile;
    }


}
