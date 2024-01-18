package integrationTest;


import com.example.kthimi.Controller.BookController;
import com.example.kthimi.Controller.LibrarianFuncController;
import com.example.kthimi.Controller.Mockers.FileBasedStockBookRepository;
import com.example.kthimi.Controller.Mockers.StockBookRepository;
import com.example.kthimi.Controller.StatisticsFuncController;
import com.example.kthimi.Model.BookModel;
import com.example.kthimi.Model.LibrarianModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BillTestI {

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

//    @AfterEach
//    public void tearDown() {
//        // Clean up the temporary file after each test
//        deleteTemporaryFile();
//    }

    private void deleteTemporaryFile() {
        try {
            Files.deleteIfExists(Path.of(TEMP_STOCK_FILE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    ////////ARDISA//////////


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
    void testRemoveDuplicatesSoldTitles() {
        ArrayList<String> titles = new ArrayList<>();
        ArrayList<Integer> quantities = new ArrayList<>();

        titles.add("Title1");
        titles.add("Title2");
        titles.add("Title1");
        titles.add("Title3");
        quantities.add(10);
        quantities.add(5);
        quantities.add(8);
        quantities.add(3);

        statisticsFuncController.removeDuplicatesSoldTitles(titles, quantities);
        System.out.println(titles);
        System.out.println(titles.size());

        System.out.println(quantities);
        assertEquals(1, titles.size());
//        assertEquals(2, quantities.size());


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


    /////Klea/////





    //////////ERA///////////////////


    @Test
    public void testShowStock() {
        BookModel book = new BookModel("1234567890", "Test Book", "Test Category", "Test Publisher", 10.0, 15.0, "Test Author", 20);

        ArrayList<BookModel> books = new ArrayList<>();
        books.add(book);
        saveBooksToTemporaryFile(books);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        bookController.showStock();

        System.setOut(System.out);

        String printedOutput = outContent.toString();
        assertTrue(printedOutput.contains(book.toString()));
    }

    /////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testGetIncomeDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.DECEMBER, 10);
        Date saleDate = calendar.getTime();

        BookModel testBook = createTestBook();
        testBook.addSale(saleDate, 5);

        ArrayList<BookModel> booksToSave = new ArrayList<>();
        booksToSave.add(testBook);

        saveBooksToTemporaryFile(booksToSave);

        double expectedIncome = testBook.getTotalBooksSoldDay() * testBook.getSellingPrice();
        double actualIncome = statisticsFuncController.getIncomeDay();
        assertEquals(expectedIncome, actualIncome);
    }

    @Test
    public void testGetIncomeMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.DECEMBER, 10);
        Date saleDate = calendar.getTime();

        BookModel testBook = createTestBook();
        testBook.addSale(saleDate, 5);

        ArrayList<BookModel> booksToSave = new ArrayList<>();
        booksToSave.add(testBook);

        saveBooksToTemporaryFile(booksToSave);

        double expectedIncome = testBook.getTotalBooksSoldMonth() * testBook.getSellingPrice();
        double actualIncome = statisticsFuncController.getIncomeMonth();
        assertEquals(expectedIncome, actualIncome);
    }

    @Test
    public void testGetIncomeYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.DECEMBER, 10);
        Date saleDate = calendar.getTime();

        BookModel testBook = createTestBook();
        testBook.addSale(saleDate, 5);

        ArrayList<BookModel> booksToSave = new ArrayList<>();
        booksToSave.add(testBook);

        saveBooksToTemporaryFile(booksToSave);

        double expectedIncome = testBook.getTotalBooksSoldYear() * testBook.getSellingPrice();
        double actualIncome = statisticsFuncController.getIncomeYear();
        assertEquals(expectedIncome, actualIncome);
    }

    @Test
    public void testGetCostDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.DECEMBER, 10);
        Date purchaseDate = calendar.getTime();

        BookModel testBook = createTestBook();
        testBook.addPurchase(purchaseDate);

        ArrayList<BookModel> booksToSave = new ArrayList<>();
        booksToSave.add(testBook);

        saveBooksToTemporaryFile(booksToSave);

        double expectedCost = testBook.getTotalBooksBoughtDay() * testBook.getOriginalPrice();
        double actualCost = statisticsFuncController.getCostDay();
        assertEquals(expectedCost, actualCost);
    }





    //////////////////////




    @Test
    public void testGetCostMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.DECEMBER, 10);
        Date purchaseDate = calendar.getTime();

        BookModel testBook = createTestBook();
        testBook.addPurchase(purchaseDate);

        ArrayList<BookModel> booksToSave = new ArrayList<>();
        booksToSave.add(testBook);

        saveBooksToTemporaryFile(booksToSave);

        double expectedCost = testBook.getTotalBooksBoughtMonth() * testBook.getOriginalPrice();
        double actualCost = statisticsFuncController.getCostMonth();
        assertEquals(expectedCost, actualCost);
    }

    @Test
    public void testGetCostYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.DECEMBER, 10);
        Date purchaseDate = calendar.getTime();

        BookModel testBook = createTestBook();
        testBook.addPurchase(purchaseDate);

        ArrayList<BookModel> booksToSave = new ArrayList<>();
        booksToSave.add(testBook);

        saveBooksToTemporaryFile(booksToSave);

        double expectedCost = testBook.getTotalBooksBoughtYear() * testBook.getOriginalPrice();
        double actualCost = statisticsFuncController.getCostYear();
        assertEquals(expectedCost, actualCost);
    }


    private BookModel createTestBookWithSale() {
        BookModel testBook = new BookModel(TEST_ISBN, TEST_TITLE, "Category1", "Test Publisher", 20.00, 25.00, "Test Author", 10);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.DECEMBER, 10); // Year, Month (0-based index), Day
        Date saleDate = calendar.getTime();

        int quantitySold = 3;

        testBook.addSale(saleDate, quantitySold);

        return testBook;
    }







    @Test
    void testRemoveDuplicates() {
        ArrayList<String> originalList = new ArrayList<>();
        originalList.add("Science");
        originalList.add("Fiction");
        originalList.add("Science");
        originalList.add("Comedy");
        originalList.add("Fiction");

        ArrayList<String> expectedList = new ArrayList<>();
        expectedList.add("Science");
        expectedList.add("Fiction");
        expectedList.add("Comedy");

        ArrayList<String> result = bookController.removeDuplicates(originalList);
        assertEquals(expectedList, result, "Duplicates not removed properly");
    }


    @Test
    public void testPrintBookDates() {
        ArrayList<BookModel> emptyList = new ArrayList<>();
        assertDoesNotThrow(() -> statisticsFuncController.printBookDates(emptyList));

        // Test Case 2: Books without Dates
        BookModel bookWithoutDate = new BookModel("ISBN1", "Book1", "Category1", "Publisher1", 20.00, 25.00, "Author1", 10);
        ArrayList<BookModel> booksWithoutDates = new ArrayList<>();
        booksWithoutDates.add(bookWithoutDate);
        assertDoesNotThrow(() -> statisticsFuncController.printBookDates(booksWithoutDates)); // Expect "empty" to be printed

        // Test Case 3: Books with Single Date
        BookModel bookWithSingleDate = new BookModel("ISBN2", "Book2", "Category2", "Publisher2", 20.00, 25.00, "Author2", 10);
        bookWithSingleDate.addDate(new Date());
        ArrayList<BookModel> booksWithSingleDate = new ArrayList<>();
        booksWithSingleDate.add(bookWithSingleDate);
        assertDoesNotThrow(() -> statisticsFuncController.printBookDates(booksWithSingleDate)); // Expect single date to be printed

        // Test Case 4: Books with Multiple Dates
        BookModel bookWithMultipleDates = new BookModel("ISBN3", "Book3", "Category3", "Publisher3", 20.00, 25.00, "Author3", 10);
        bookWithMultipleDates.addDate(new Date());
        bookWithMultipleDates.addDate(new Date());
        ArrayList<BookModel> booksWithMultipleDates = new ArrayList<>();
        booksWithMultipleDates.add(bookWithMultipleDates);
        assertDoesNotThrow(() -> statisticsFuncController.printBookDates(booksWithMultipleDates)); // Expect multiple dates to be printed

        //3 dates will be printed, one for test case 3 and 2 for test case 4
    }

    @Test
    public void testGetSoldDatesQuantitiesDay() {
        BookModel book = createTestBook();
        Date today = new Date();
        book.addDate(today);
        book.addQuantity(5);

        String result = book.getSoldDatesQuantitiesDay();

        String expected = "For \"" + book.getTitle() + "\" We have sold in a day:\n5 at " + today + "\n";

        assertEquals(expected, result);
    }







}