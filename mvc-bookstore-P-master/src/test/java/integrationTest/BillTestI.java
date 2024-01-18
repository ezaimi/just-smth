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


//    @Test
//    public void testAddBookToStock() {
//        // Create a test book
//        Book testBook = new Book("1234567890123", "Test Book", "Test Category", "Test Publisher", 20.00, 25.00, "Test Author", 10);
//
//        // Mock the behavior of getStockBooks() to return a specific ArrayList<Book>
//        ArrayList<Book> mockedStockBooks = new ArrayList<>();
//        when(BillNumber.getStockBooks()).thenReturn(mockedStockBooks);
//
//        // Call the method you want to test
//        BillNumber.addBookToStock(testBook);
//
//        // Verify that the test book was added to the mocked stock books
//        verify(mockedStockBooks).add(testBook);
//    }

    ////////ARDISA//////////


    @Test
    public void testGetTotalBoughtBooksDay_WithBooks() {
        // Creating test books with purchases for the specific day
        BookModel testBook1 = createTestBook();
        testBook1.addPurchase(new Date()); // Adding a purchase for today

        BookModel testBook2 = new BookModel("9876543210987", "Another Book", "Category2", "Another Publisher", 15.00, 18.00, "Another Author", 5);
        testBook2.addPurchase(new Date()); // Adding a purchase for today

        ArrayList<BookModel> booksToSave = new ArrayList<>();
        booksToSave.add(testBook1);
        booksToSave.add(testBook2);

        // Saving the test books to the temporary file
        saveBooksToTemporaryFile(booksToSave);

        // Getting the total bought books for the day and asserting the result
        int expectedTotal = testBook1.getTotalBooksBoughtDay() + testBook2.getTotalBooksBoughtDay();
        int actualTotal = statisticsFuncController.getTotalBoughtBooksDay();
        assertEquals(expectedTotal, actualTotal);
    }

    @Test
    public void testGetTotalBoughtBooksMonth_WithBooks() {
        ArrayList<BookModel> booksWithPurchases = new ArrayList<>();
        BookModel bookWithPurchases = createTestBook();

        // Create a Date object for the purchase date (e.g., today's date)
        Date purchaseDate = new Date();

        // Add a purchase to the book for today
        bookWithPurchases.addPurchase(purchaseDate);

        booksWithPurchases.add(bookWithPurchases);

        // Saving the test books to the temporary file
        saveBooksToTemporaryFile(booksWithPurchases);

        // Getting the total bought books for the month and asserting the result
        int expectedTotalBoughtBooks = bookWithPurchases.getTotalBooksBoughtMonth();
        int actualTotalBoughtBooks = statisticsFuncController.getTotalBoughtBooksMonth();
        assertEquals(expectedTotalBoughtBooks, actualTotalBoughtBooks);

    }

    @Test
    public void testGetTotalBoughtBooksYear_WithBooks() {
        // Creating test books with purchases for the specific day
        BookModel testBook1 = createTestBook();
        testBook1.addPurchase(new Date()); // Adding a purchase for today

        BookModel testBook2 = new BookModel("9876543210987", "Another Book", "Category2", "Another Publisher", 15.00, 18.00, "Another Author", 5);
        testBook2.addPurchase(new Date()); // Adding a purchase for today

        ArrayList<BookModel> booksToSave = new ArrayList<>();
        booksToSave.add(testBook1);
        booksToSave.add(testBook2);

        // Saving the test books to the temporary file
        saveBooksToTemporaryFile(booksToSave);

        // Getting the total bought books for the day and asserting the result
        int expectedTotal = testBook1.getTotalBooksBoughtYear() + testBook2.getTotalBooksBoughtYear();
        int actualTotal = statisticsFuncController.getTotalBoughtBooksYear();
        assertEquals(expectedTotal, actualTotal);
    }

    @Test
    public void testGetISBNName_WithBooks() {
        // Creating test books
        BookModel testBook1 = createTestBook();
        BookModel testBook2 = new BookModel("9876543210987", "Another Book", "Category2", "Another Publisher", 15.00, 18.00, "Another Author", 5);

        ArrayList<BookModel> booksToSave = new ArrayList<>();
        booksToSave.add(testBook1);
        booksToSave.add(testBook2);

        // Saving the test books to the temporary file
        saveBooksToTemporaryFile(booksToSave);

        // Getting ISBNs and names and asserting the result
        ArrayList<String> expectedISBNName = new ArrayList<>();
        expectedISBNName.add(testBook1.getISBN() + " - " + testBook1.getTitle());
        expectedISBNName.add(testBook2.getISBN() + " - " + testBook2.getTitle());

        ArrayList<String> actualISBNName = bookController.getISBNName();
        assertEquals(expectedISBNName, actualISBNName);
    }


    @Test
    void testIsPartOfBooks() {
        // Create an instance of your class (replace YourClassName with the actual class name)

        // Create a sample ISBN
        String sampleISBN = "1234567890123";

        // Create an ArrayList of books for testing
        ArrayList<BookModel> stockBooks = new ArrayList<>();
        stockBooks.add(new BookModel("1234567890123", "Test Book1", "Category1", "Test Publisher", 20.00, 25.00, "Test Author", 1));
        stockBooks.add(new BookModel("1234567890123", "Test Book2", "Category1", "Test Publisher", 20.00, 25.00, "Test Author", 1));

        // Set the stock books in the BillNumber class (replace BillNumber with the actual class name)
        try {
            bookController.updateBooks(stockBooks);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Call the method to check if the ISBN is part of the books
        boolean result = bookController.isPartOfBooks(sampleISBN);

        // Validate the result
        assertTrue(result, "The ISBN should be part of the books");

        // Test with an ISBN that is not part of the books
        String nonExistingISBN = "9999999999";
        boolean resultNonExisting = bookController.isPartOfBooks(nonExistingISBN);

        // Validate the result for non-existing ISBN
        assertFalse(resultNonExisting, "The ISBN should not be part of the books");
    }

    @Test
    void testRemoveDuplicatesSoldTitles() {
        ArrayList<String> titles = new ArrayList<>();
        ArrayList<Integer> quantities = new ArrayList<>();

        // Add test data with duplicates
        titles.add("Title1");
        titles.add("Title2");
        titles.add("Title1");
        titles.add("Title3");
        quantities.add(10);
        quantities.add(5);
        quantities.add(8);
        quantities.add(3);

        // Call the method to remove duplicates
        statisticsFuncController.removeDuplicatesSoldTitles(titles, quantities);
        System.out.println(titles);
        System.out.println(titles.size());

        System.out.println(quantities);
        // Validate the results after removing duplicates
        assertEquals(1, titles.size());
//        assertEquals(2, quantities.size());


    }


    @Test
    public void test_getBooksBoughtDayWithPurchases() {
        // Creating a test book with a purchase date
        BookModel testBook = createTestBook();

        // Simulating a purchase date for today
        testBook.addPurchase(new Date()); // Adding a single purchase for today

        ArrayList<BookModel> booksToSave = new ArrayList<>();
        booksToSave.add(testBook);

        // Saving the test books to the temporary file
        saveBooksToTemporaryFile(booksToSave);

        String booksBoughtDay = statisticsFuncController.getBooksBoughtDay();
        int occurrences = countOccurrences(booksBoughtDay, testBook.getTitle());

        assertTrue(occurrences > 0);

    }

    // Method to count occurrences of a substring within a string
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
        // Creating a test book with a purchase date
        BookModel testBook = createTestBook();

        // Simulating a purchase date for today
        testBook.addPurchase(new Date()); // Adding a single purchase for today

        ArrayList<BookModel> booksToSave = new ArrayList<>();
        booksToSave.add(testBook);

        // Saving the test books to the temporary file
        saveBooksToTemporaryFile(booksToSave);

        String expectedBoughtDay = "For Books Bought In A Month We Have\n\n" +
                "For \"" + testBook.getTitle() + "\" We have bought in a month:\n" +
                "1 at " + new Date().toString() + "\n";


        String actualBoughtMonth = statisticsFuncController.getBooksBoughtMonth();
        assertEquals(expectedBoughtDay, actualBoughtMonth);

    }

    @Test
    public void test_getBooksBoughtYearWithPurchases() {
        // Creating a test book with a purchase date
        BookModel testBook = createTestBook();

        // Simulating a purchase date for today
        testBook.addPurchase(new Date()); // Adding a single purchase for today

        ArrayList<BookModel> booksToSave = new ArrayList<>();
        booksToSave.add(testBook);

        // Saving the test books to the temporary file
        saveBooksToTemporaryFile(booksToSave);

        String expectedBoughtDay = "For Books Bought In A Year We Have\n\n" +
                "For \"" + testBook.getTitle() + "\" We have bought in a year:\n" +
                "1 at " + new Date().toString() + "\n";


        String actualBoughtYear = statisticsFuncController.getBooksBoughtYear();
        assertEquals(expectedBoughtDay, actualBoughtYear);

    }



    @Test
    public void testGetBookFromCategory() {
        // Create sample data for testing
        ArrayList<BookModel> stockBooks = new ArrayList<>();
        stockBooks.add(createTestBook());

        // Update the stock books directly without using setStockBooks method
        try {
            bookController.updateBooks(stockBooks);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Call the method you want to test
        ArrayList<BookModel> result = bookController.getBookFromCategory("Category1");
        System.out.println("fa");
        System.out.println(result);
        // Define the expected result
        ArrayList<BookModel> expectedResult = new ArrayList<>();
        expectedResult.add(createTestBook());

        // Print debug information
        System.out.println("Expected Result: " + expectedResult);
        System.out.println("Actual Result: " + result);

        // Compare the actual result with the expected result
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
        // Creating test books with different categories
        BookModel testBook1 = createTestBook();
        testBook1.setCategory("Fiction");

        BookModel testBook2 = createTestBook();
        testBook2.setCategory("Non-fiction");

        BookModel testBook3 = createTestBook();
        testBook3.setCategory("Fiction");

        ArrayList<BookModel> booksToSave = new ArrayList<>(Arrays.asList(testBook1, testBook2, testBook3));

        // Saving the test books to the temporary file
        saveBooksToTemporaryFile(booksToSave);

        // Getting categories and removing duplicates
        ArrayList<String> expectedCategories = new ArrayList<>(Arrays.asList("Fiction", "Non-fiction"));
        ArrayList<String> actualCategories = bookController.getCategories();

        assertEquals(expectedCategories.size(), actualCategories.size());
        for (String category : expectedCategories) {
            assertTrue(actualCategories.contains(category));
        }
    }


    /////Klea/////


    @Test
    public void testGetIntBooksSoldDay() {
        // Creating test books with sales
        BookModel testBook1 = createTestBook();
        testBook1.addSale(new Date(123, 11, 10), 5); // Sale on a specific date

        BookModel testBook2 = createTestBook();
        testBook2.addSale(new Date(123, 11, 10), 3); // Sale on the same date

        ArrayList<BookModel> booksToSave = new ArrayList<>();
        booksToSave.add(testBook1);
        booksToSave.add(testBook2);

        // Saving the test books to the temporary file
        saveBooksToTemporaryFile(booksToSave);

        // Getting the total number of books sold in a day and asserting the result
        int expectedBooksSold = testBook1.getTotalBooksSoldDay() + testBook2.getTotalBooksSoldDay();
        int actualBooksSold = statisticsFuncController.getIntBooksSoldDay();
        assertEquals(expectedBooksSold, actualBooksSold);
    }

    @Test
    public void testGetIntBooksSoldMonth() {
        // Creating test books with sales
        BookModel testBook1 = createTestBook();
        testBook1.addSale(new Date(123, 11, 10), 5); // Sale on a specific date

        BookModel testBook2 = createTestBook();
        testBook2.addSale(new Date(123, 11, 10), 3); // Sale on the same date

        ArrayList<BookModel> booksToSave = new ArrayList<>();
        booksToSave.add(testBook1);
        booksToSave.add(testBook2);

        // Saving the test books to the temporary file
        saveBooksToTemporaryFile(booksToSave);

        // Getting the total number of books sold in a day and asserting the result
        int expectedBooksSold = testBook1.getTotalBooksSoldMonth() + testBook2.getTotalBooksSoldMonth();
        int actualBooksSold = statisticsFuncController.getIntBooksSoldMonth();
        assertEquals(expectedBooksSold, actualBooksSold);
    }

    @Test
    public void testGetIntBooksSoldYear() {
        // Creating test books with sales
        BookModel testBook1 = createTestBook();
        testBook1.addSale(new Date(123, 11, 10), 5); // Sale on a specific date

        BookModel testBook2 = createTestBook();
        testBook2.addSale(new Date(123, 11, 10), 3); // Sale on the same date

        ArrayList<BookModel> booksToSave = new ArrayList<>();
        booksToSave.add(testBook1);
        booksToSave.add(testBook2);

        // Saving the test books to the temporary file
        saveBooksToTemporaryFile(booksToSave);

        // Getting the total number of books sold in a day and asserting the result
        int expectedBooksSold = testBook1.getTotalBooksSoldYear() + testBook2.getTotalBooksSoldYear();
        int actualBooksSold = statisticsFuncController.getIntBooksSoldYear();
        assertEquals(expectedBooksSold, actualBooksSold);
    }




    //////////ERA///////////////////


    @Test
    public void testShowStock() {
        // Create a sample book
        BookModel book = new BookModel("1234567890", "Test Book", "Test Category", "Test Publisher", 10.0, 15.0, "Test Author", 20);

        // Save the book to the temporary file
        ArrayList<BookModel> books = new ArrayList<>();
        books.add(book);
        saveBooksToTemporaryFile(books);

        // Redirect System.out to capture printed output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Call the method that prints the stock
        bookController.showStock();

        // Reset System.out to the default PrintStream
        System.setOut(System.out);

        // Check if the printed output contains the book details
        String printedOutput = outContent.toString();
        assertTrue(printedOutput.contains(book.toString()));
    }

    /////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testGetIncomeDay() {
        // Sale date
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.DECEMBER, 10); // Year, Month (0-based index), Day
        Date saleDate = calendar.getTime();

        // Creating a test book with sales for the specified date
        BookModel testBook = createTestBook();
        testBook.addSale(saleDate, 5);

        ArrayList<BookModel> booksToSave = new ArrayList<>();
        booksToSave.add(testBook);

        // Saving the test books to the temporary file
        saveBooksToTemporaryFile(booksToSave);

        // Getting the income for the day and asserting the result
        double expectedIncome = testBook.getTotalBooksSoldDay() * testBook.getSellingPrice();
        double actualIncome = statisticsFuncController.getIncomeDay();
        assertEquals(expectedIncome, actualIncome);
    }

    @Test
    public void testGetIncomeMonth() {
        // Sale date
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.DECEMBER, 10); // Year, Month (0-based index), Day
        Date saleDate = calendar.getTime();

        // Creating a test book with sales for the specified date
        BookModel testBook = createTestBook();
        testBook.addSale(saleDate, 5);

        ArrayList<BookModel> booksToSave = new ArrayList<>();
        booksToSave.add(testBook);

        // Saving the test books to the temporary file
        saveBooksToTemporaryFile(booksToSave);

        // Getting the income for the day and asserting the result
        double expectedIncome = testBook.getTotalBooksSoldMonth() * testBook.getSellingPrice();
        double actualIncome = statisticsFuncController.getIncomeMonth();
        assertEquals(expectedIncome, actualIncome);
    }

    @Test
    public void testGetIncomeYear() {
        // Sale date
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.DECEMBER, 10); // Year, Month (0-based index), Day
        Date saleDate = calendar.getTime();

        // Creating a test book with sales for the specified date
        BookModel testBook = createTestBook();
        testBook.addSale(saleDate, 5);

        ArrayList<BookModel> booksToSave = new ArrayList<>();
        booksToSave.add(testBook);

        // Saving the test books to the temporary file
        saveBooksToTemporaryFile(booksToSave);

        // Getting the income for the day and asserting the result
        double expectedIncome = testBook.getTotalBooksSoldYear() * testBook.getSellingPrice();
        double actualIncome = statisticsFuncController.getIncomeYear();
        assertEquals(expectedIncome, actualIncome);
    }

    @Test
    public void testGetCostDay() {
        // Purchase date
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.DECEMBER, 10); // Year, Month (0-based index), Day
        Date purchaseDate = calendar.getTime();

        // Creating a test book with purchases for the specified date
        BookModel testBook = createTestBook();
        testBook.addPurchase(purchaseDate);

        ArrayList<BookModel> booksToSave = new ArrayList<>();
        booksToSave.add(testBook);

        // Saving the test books to the temporary file
        saveBooksToTemporaryFile(booksToSave);

        // Getting the cost for the day and asserting the result
        double expectedCost = testBook.getTotalBooksBoughtDay() * testBook.getOriginalPrice();
        double actualCost = statisticsFuncController.getCostDay();
        assertEquals(expectedCost, actualCost);
    }





    //////////////////////




    @Test
    public void testGetCostMonth() {
        // Purchase date
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.DECEMBER, 10); // Year, Month (0-based index), Day
        Date purchaseDate = calendar.getTime();

        // Creating a test book with purchases for the specified date
        BookModel testBook = createTestBook();
        testBook.addPurchase(purchaseDate);

        ArrayList<BookModel> booksToSave = new ArrayList<>();
        booksToSave.add(testBook);

        // Saving the test books to the temporary file
        saveBooksToTemporaryFile(booksToSave);

        // Getting the cost for the day and asserting the result
        double expectedCost = testBook.getTotalBooksBoughtMonth() * testBook.getOriginalPrice();
        double actualCost = statisticsFuncController.getCostMonth();
        assertEquals(expectedCost, actualCost);
    }

    @Test
    public void testGetCostYear() {
        // Purchase date
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.DECEMBER, 10); // Year, Month (0-based index), Day
        Date purchaseDate = calendar.getTime();

        // Creating a test book with purchases for the specified date
        BookModel testBook = createTestBook();
        testBook.addPurchase(purchaseDate);

        ArrayList<BookModel> booksToSave = new ArrayList<>();
        booksToSave.add(testBook);

        // Saving the test books to the temporary file
        saveBooksToTemporaryFile(booksToSave);

        // Getting the cost for the day and asserting the result
        double expectedCost = testBook.getTotalBooksBoughtYear() * testBook.getOriginalPrice();
        double actualCost = statisticsFuncController.getCostYear();
        assertEquals(expectedCost, actualCost);
    }


    private BookModel createTestBookWithSale() {
        BookModel testBook = new BookModel(TEST_ISBN, TEST_TITLE, "Category1", "Test Publisher", 20.00, 25.00, "Test Author", 10);

        // Create a Date object for the sale date (e.g., today's date)
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.DECEMBER, 10); // Year, Month (0-based index), Day
        Date saleDate = calendar.getTime();

        int quantitySold = 3;

        // Add the sale to the test book
        testBook.addSale(saleDate, quantitySold);

        return testBook;
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

        // Create a Date object for the sale date (e.g., today's date)
        Date saleDate = new Date(); // Creating a Date object with the current date and time

        bookWithSales.addSale(saleDate, 3); // Adding sales for a specific date
        booksWithSales.add(bookWithSales);

        // Generating expected output based on the sale made this month
        String expected = "For Books Sold In A Month We Have\n\n" +
                "For \"" + bookWithSales.getTitle() + "\" We have sold in a month:\n" +
                "3 at " + saleDate.toString() + "\n";

        // Save the books with sales to the temporary file
        saveBooksToTemporaryFile(booksWithSales);

        // Test the method getBooksSoldMonth()
        assertEquals(expected, statisticsFuncController.getBooksSoldMonth());
    }

    @Test
    public void testGetBooksSoldYear_WithSales() {
        ArrayList<BookModel> booksWithSales = new ArrayList<>();
        BookModel bookWithSales = createTestBook();

        // Create a Date object for the sale date (e.g., today's date)
        Date saleDate = new Date(); // Creating a Date object with the current date and time

        bookWithSales.addSale(saleDate, 3); // Adding sales for a specific date
        booksWithSales.add(bookWithSales);

        // Generating expected output based on the sale made this year
        String expected = "For Books Sold In A Year We Have\n\n" +
                "For \"" + bookWithSales.getTitle() + "\" We have sold in a year:\n" +
                "3 at " + saleDate.toString() + "\n";

        // Save the books with sales to the temporary file
        saveBooksToTemporaryFile(booksWithSales);

        // Test the method getBooksSoldYear()
        assertEquals(expected, statisticsFuncController.getBooksSoldYear());
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
        // Test Case 1: Empty List of Books
        ArrayList<BookModel> emptyList = new ArrayList<>();
        //making sure it doesn't throw any exception
        assertDoesNotThrow(() -> statisticsFuncController.printBookDates(emptyList)); // Verify no output or error

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
        // Create a book and add a purchase for today
        BookModel book = createTestBook();
        Date today = new Date();
        book.addDate(today);
        book.addQuantity(5);

        // Call the method being tested
        String result = book.getSoldDatesQuantitiesDay();

        // Define the expected output based on the purchase added
        String expected = "For \"" + book.getTitle() + "\" We have sold in a day:\n5 at " + today + "\n";

        // Assert that the method behaves as expected
        assertEquals(expected, result);
    }







}