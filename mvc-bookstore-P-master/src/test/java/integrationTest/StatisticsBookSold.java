package integrationTest;

import com.example.kthimi.Controller.BookController;
import com.example.kthimi.Controller.LibrarianFuncController;
import com.example.kthimi.Controller.Mockers.FileBasedStockBookRepository;
import com.example.kthimi.Controller.StatisticsFuncController;
import com.example.kthimi.Model.BookModel;
import com.example.kthimi.Model.LibrarianModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatisticsBookSold {

    StatisticsFuncController statisticsFuncController = new StatisticsFuncController();
    BookController bookController = new BookController();
    private static String TEMP_STOCK_FILE_PATH = "tempStockFile.bin";
    private static final String TEST_ISBN = "1234567890123";
    private static final String TEST_TITLE = "Test Book";

    public static void setStockFilePath(String newPath) {
        try {
            // Change the STOCK_FILE_PATH field in BookController class
            changeField(BookController.class, "STOCK_FILE_PATH", newPath);
            changeField(LibrarianModel.class, "STOCK_FILE_PATH", newPath);
            changeField(LibrarianFuncController.class, "STOCK_FILE_PATH", newPath);
            changeField(FileBasedStockBookRepository.class, "STOCK_FILE_PATH", newPath);
            // Add more classes and fields to change here as necessary
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void changeField(Class<?> targetClass, String fieldName, String newValue) throws NoSuchFieldException, IllegalAccessException {
        Field field = targetClass.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(null, newValue);
    }

    private BookModel createTestBook() {
        return new BookModel(TEST_ISBN, TEST_TITLE, "Category1", "Test Publisher", 20.00, 25.00, "Test Author", 10);
    }

    @BeforeEach
    public void setUp() {
        BillTestI.setStockFilePath(TEMP_STOCK_FILE_PATH);
        // Create a temporary file for testing
        createTemporaryFile();
    }

    private void createTemporaryFile() {
        try (ObjectOutputStream objout = new ObjectOutputStream(new FileOutputStream(TEMP_STOCK_FILE_PATH))) {
            // Write initial data to the temporary file if needed for setup
            // For instance:
            BookModel book = createTestBook();
            objout.writeObject(book);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<BookModel> saveBooksToTemporaryFile(ArrayList<BookModel> books) {
        try (ObjectOutputStream objout = new ObjectOutputStream(new FileOutputStream(TEMP_STOCK_FILE_PATH))) {
            objout.writeObject(books);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return books;
    }


    @Test
    public void testGetIntBooksSoldDay() {
        BookModel testBook1 = createTestBook();
        testBook1.addSale(new Date(123, 11, 10), 5);

        BookModel testBook2 = createTestBook();
        testBook2.addSale(new Date(123, 11, 10), 3);

        ArrayList<BookModel> booksToSave = new ArrayList<>();
        booksToSave.add(testBook1);
        booksToSave.add(testBook2);

        saveBooksToTemporaryFile(booksToSave);

        int expectedBooksSold = testBook1.getTotalBooksSoldDay() + testBook2.getTotalBooksSoldDay();
        int actualBooksSold = statisticsFuncController.getIntBooksSoldDay();
        assertEquals(expectedBooksSold, actualBooksSold);
    }

    @Test
    public void testGetIntBooksSoldMonth() {
        BookModel testBook1 = createTestBook();
        testBook1.addSale(new Date(123, 11, 10), 5);

        BookModel testBook2 = createTestBook();
        testBook2.addSale(new Date(123, 11, 10), 3);

        ArrayList<BookModel> booksToSave = new ArrayList<>();
        booksToSave.add(testBook1);
        booksToSave.add(testBook2);

        saveBooksToTemporaryFile(booksToSave);

        int expectedBooksSold = testBook1.getTotalBooksSoldMonth() + testBook2.getTotalBooksSoldMonth();
        int actualBooksSold = statisticsFuncController.getIntBooksSoldMonth();
        assertEquals(expectedBooksSold, actualBooksSold);
    }

    @Test
    public void testGetIntBooksSoldYear() {
        BookModel testBook1 = createTestBook();
        testBook1.addSale(new Date(123, 11, 10), 5); // Sale on a specific date

        BookModel testBook2 = createTestBook();
        testBook2.addSale(new Date(123, 11, 10), 3); // Sale on the same date

        ArrayList<BookModel> booksToSave = new ArrayList<>();
        booksToSave.add(testBook1);
        booksToSave.add(testBook2);

        saveBooksToTemporaryFile(booksToSave);

        int expectedBooksSold = testBook1.getTotalBooksSoldYear() + testBook2.getTotalBooksSoldYear();
        int actualBooksSold = statisticsFuncController.getIntBooksSoldYear();
        assertEquals(expectedBooksSold, actualBooksSold);
    }



    @Test
    public void testGetBooksSoldDay_WithSales() {
        ArrayList<BookModel> booksWithSales = new ArrayList<>();
        BookModel bookWithSales = createTestBook();

        // Create a Date object for the sale date (e.g., today's date)
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(2023, Calendar.DECEMBER, 10); // Year, Month (0-based index), Day
//        Date saleDate = calendar.getTime();

        Date saleDate = new Date();

        bookWithSales.addSale(saleDate, 3); // Adding sales for a specific dat
        //bookWithSales.addQuantity(1);
        booksWithSales.add(bookWithSales);

//        ArrayList<Book> books2 = saveBooksToTemporaryFile(booksWithSales);
//        Book book3 = books2.get(0);
//        book3.addDate(new Date());
//        book3.addQuantity(3);
//        System.out.println("BOOOK");
//        System.out.println(book3);

        saveBooksToTemporaryFile(booksWithSales);
        //BillNumber.updateBooks(bookWithSales);

        String expected = "For Books Sold Today We Have:\n\n" +
                "For \"" + bookWithSales.getTitle() + "\" We have sold in a day:\n" +
                "3 at " + saleDate.toString() + "\n";

        System.out.println("Excpected"+"\n"+expected);
        //System.out.println(BillNumber.getBooksSoldDay());
        System.out.println(statisticsFuncController.getBooksSoldDay());

        assertEquals(expected, statisticsFuncController.getBooksSoldDay());
    }


    @Test
    public void testGetBooksSoldMonth_WithSales() {
        ArrayList<BookModel> booksWithSales = new ArrayList<>();
        BookModel bookWithSales = createTestBook();

        Date saleDate = new Date();

        bookWithSales.addSale(saleDate, 3);
        booksWithSales.add(bookWithSales);

        String expected = "For Books Sold In A Month We Have\n\n" +
                "For \"" + bookWithSales.getTitle() + "\" We have sold in a month:\n" +
                "3 at " + saleDate.toString() + "\n";

        saveBooksToTemporaryFile(booksWithSales);

        assertEquals(expected, statisticsFuncController.getBooksSoldMonth());
    }

    @Test
    public void testGetBooksSoldYear_WithSales() {
        ArrayList<BookModel> booksWithSales = new ArrayList<>();
        BookModel bookWithSales = createTestBook();

        Date saleDate = new Date();

        bookWithSales.addSale(saleDate, 3);
        booksWithSales.add(bookWithSales);

        String expected = "For Books Sold In A Year We Have\n\n" +
                "For \"" + bookWithSales.getTitle() + "\" We have sold in a year:\n" +
                "3 at " + saleDate.toString() + "\n";

        saveBooksToTemporaryFile(booksWithSales);

        assertEquals(expected, statisticsFuncController.getBooksSoldYear());
    }




}
