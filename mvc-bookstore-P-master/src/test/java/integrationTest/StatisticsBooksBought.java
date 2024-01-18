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
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StatisticsBooksBought {


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
    public void testGetTotalBoughtBooksDay_WithBooks() {
        BookModel testBook1 = createTestBook();
        testBook1.addPurchase(new Date());

        BookModel testBook2 = new BookModel("9876543210987", "Another Book", "Category2", "Another Publisher", 15.00, 18.00, "Another Author", 5);
        testBook2.addPurchase(new Date());

        ArrayList<BookModel> booksToSave = new ArrayList<>();
        booksToSave.add(testBook1);
        booksToSave.add(testBook2);

        saveBooksToTemporaryFile(booksToSave);

        int expectedTotal = testBook1.getTotalBooksBoughtDay() + testBook2.getTotalBooksBoughtDay();
        int actualTotal = statisticsFuncController.getTotalBoughtBooksDay();
        assertEquals(expectedTotal, actualTotal);
    }

    @Test
    public void testGetTotalBoughtBooksMonth_WithBooks() {
        ArrayList<BookModel> booksWithPurchases = new ArrayList<>();
        BookModel bookWithPurchases = createTestBook();

        Date purchaseDate = new Date();

        bookWithPurchases.addPurchase(purchaseDate);

        booksWithPurchases.add(bookWithPurchases);

        saveBooksToTemporaryFile(booksWithPurchases);

        int expectedTotalBoughtBooks = bookWithPurchases.getTotalBooksBoughtMonth();
        int actualTotalBoughtBooks = statisticsFuncController.getTotalBoughtBooksMonth();
        assertEquals(expectedTotalBoughtBooks, actualTotalBoughtBooks);

    }

    @Test
    public void testGetTotalBoughtBooksYear_WithBooks() {
        BookModel testBook1 = createTestBook();
        testBook1.addPurchase(new Date());

        BookModel testBook2 = new BookModel("9876543210987", "Another Book", "Category2", "Another Publisher", 15.00, 18.00, "Another Author", 5);
        testBook2.addPurchase(new Date());

        ArrayList<BookModel> booksToSave = new ArrayList<>();
        booksToSave.add(testBook1);
        booksToSave.add(testBook2);

        saveBooksToTemporaryFile(booksToSave);

        int expectedTotal = testBook1.getTotalBooksBoughtYear() + testBook2.getTotalBooksBoughtYear();
        int actualTotal = statisticsFuncController.getTotalBoughtBooksYear();
        assertEquals(expectedTotal, actualTotal);
    }


    @Test
    public void test_getBooksBoughtDayWithPurchases() {
        BookModel testBook = createTestBook();

        testBook.addPurchase(new Date());

        ArrayList<BookModel> booksToSave = new ArrayList<>();
        booksToSave.add(testBook);

        saveBooksToTemporaryFile(booksToSave);

        String booksBoughtDay = statisticsFuncController.getBooksBoughtDay();
        int occurrences = countOccurrences(booksBoughtDay, testBook.getTitle());

        assertTrue(occurrences > 0);

    }


    private int countOccurrences(String str, String subStr) {
        int lastIndex = 0;
        int count = 0;

        while (lastIndex != -1) {
            lastIndex = str.indexOf(subStr, lastIndex);
            if (lastIndex != -1) {
                count++;
                lastIndex += subStr.length();
            }
        }
        return count;
    }

    @Test
    public void test_getBooksBoughtMonthWithPurchases() {
        BookModel testBook = createTestBook();

        testBook.addPurchase(new Date());

        ArrayList<BookModel> booksToSave = new ArrayList<>();
        booksToSave.add(testBook);

        saveBooksToTemporaryFile(booksToSave);

        String expectedBoughtDay = "For Books Bought In A Month We Have\n\n" +
                "For \"" + testBook.getTitle() + "\" We have bought in a month:\n" +
                "1 at " + new Date().toString() + "\n";


        String actualBoughtMonth = statisticsFuncController.getBooksBoughtMonth();
        assertEquals(expectedBoughtDay, actualBoughtMonth);

    }

    @Test
    public void test_getBooksBoughtYearWithPurchases() {
        BookModel testBook = createTestBook();

        testBook.addPurchase(new Date());

        ArrayList<BookModel> booksToSave = new ArrayList<>();
        booksToSave.add(testBook);

        saveBooksToTemporaryFile(booksToSave);

        String expectedBoughtDay = "For Books Bought In A Year We Have\n\n" +
                "For \"" + testBook.getTitle() + "\" We have bought in a year:\n" +
                "1 at " + new Date().toString() + "\n";


        String actualBoughtYear = statisticsFuncController.getBooksBoughtYear();
        assertEquals(expectedBoughtDay, actualBoughtYear);

    }



}
