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
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class BookAttributesTest {

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
    public void testGetISBNName_WithBooks() {
        BookModel testBook1 = createTestBook();
        BookModel testBook2 = new BookModel("9876543210987", "Another Book", "Category2", "Another Publisher", 15.00, 18.00, "Another Author", 5);

        ArrayList<BookModel> booksToSave = new ArrayList<>();
        booksToSave.add(testBook1);
        booksToSave.add(testBook2);

        saveBooksToTemporaryFile(booksToSave);

        ArrayList<String> expectedISBNName = new ArrayList<>();
        expectedISBNName.add(testBook1.getISBN() + " - " + testBook1.getTitle());
        expectedISBNName.add(testBook2.getISBN() + " - " + testBook2.getTitle());

        ArrayList<String> actualISBNName = bookController.getISBNName();
        assertEquals(expectedISBNName, actualISBNName);
    }


    @Test
    void testIsPartOfBooks() {

        String sampleISBN = "1234567890123";

        ArrayList<BookModel> stockBooks = new ArrayList<>();
        stockBooks.add(new BookModel("1234567890123", "Test Book1", "Category1", "Test Publisher", 20.00, 25.00, "Test Author", 1));
        stockBooks.add(new BookModel("1234567890123", "Test Book2", "Category1", "Test Publisher", 20.00, 25.00, "Test Author", 1));

        try {
            bookController.updateBooks(stockBooks);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        boolean result = bookController.isPartOfBooks(sampleISBN);

        assertTrue(result, "The ISBN should be part of the books");

        String nonExistingISBN = "9999999999";
        boolean resultNonExisting = bookController.isPartOfBooks(nonExistingISBN);

        assertFalse(resultNonExisting, "The ISBN should not be part of the books");
    }




    @Test
    public void testGetBookFromCategory() {
        ArrayList<BookModel> stockBooks = new ArrayList<>();
        stockBooks.add(createTestBook());

        try {
            bookController.updateBooks(stockBooks);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ArrayList<BookModel> result = bookController.getBookFromCategory("Category1");
        System.out.println("fa");
        System.out.println(result);
        ArrayList<BookModel> expectedResult = new ArrayList<>();
        expectedResult.add(createTestBook());

        System.out.println("Expected Result: " + expectedResult);
        System.out.println("Actual Result: " + result);

        assertEquals(expectedResult, result);
    }


    @Test
    public void testValidCategoryWithNoBooks() {
        ArrayList<BookModel> result = bookController.getBookFromCategory("CategoryWithNoBooks");
        assertTrue(result.isEmpty());
    }

    @Test
    public void testInvalidCategory() {
        ArrayList<BookModel> result = bookController.getBookFromCategory("InvalidCategory");
        assertTrue(result.isEmpty());
    }

    @Test
    public void testNullCategoryy() {
        ArrayList<BookModel> result = bookController.getBookFromCategory(null);
        assertTrue(result.isEmpty());
    }


    @Test
    public void testEmptyCategoriesStock() {
        assertFalse(bookController.partOfCateogriesChecker(new ArrayList<>(), "SomeCategory"));
    }

    @Test
    public void testNullCategory() {
        ArrayList<String> categoriesStock = new ArrayList<>();
        categoriesStock.add("SomeCategory");
        assertFalse(bookController.partOfCateogriesChecker(categoriesStock, null));
    }

    @Test
    public void testCategoryFound() {
        ArrayList<String> categoriesStock = new ArrayList<>();
        categoriesStock.add("SomeCategory");
        assertTrue(bookController.partOfCateogriesChecker(categoriesStock, "SomeCategory"));
    }

    @Test
    public void testCategoryNotFound() {
        ArrayList<String> categoriesStock = new ArrayList<>();
        categoriesStock.add("SomeCategory");
        assertFalse(bookController.partOfCateogriesChecker(categoriesStock, "AnotherCategory"));
    }


    @Test
    public void testGetCategories() {
        BookModel testBook1 = createTestBook();
        testBook1.setCategory("Fiction");

        BookModel testBook2 = createTestBook();
        testBook2.setCategory("Non-fiction");

        BookModel testBook3 = createTestBook();
        testBook3.setCategory("Fiction");

        ArrayList<BookModel> booksToSave = new ArrayList<>(Arrays.asList(testBook1, testBook2, testBook3));

        saveBooksToTemporaryFile(booksToSave);

        ArrayList<String> expectedCategories = new ArrayList<>(Arrays.asList("Fiction", "Non-fiction"));
        ArrayList<String> actualCategories = bookController.getCategories();

        assertEquals(expectedCategories.size(), actualCategories.size());
        for (String category : expectedCategories) {
            assertTrue(actualCategories.contains(category));
        }
    }
}
