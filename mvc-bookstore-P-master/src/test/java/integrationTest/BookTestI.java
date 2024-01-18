package integrationTest;


import com.example.kthimi.Controller.BookController;
import com.example.kthimi.Controller.LibrarianFuncController;
import com.example.kthimi.Controller.Mockers.FileBasedStockBookRepository;
import com.example.kthimi.Model.BookModel;
import com.example.kthimi.Model.LibrarianModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;

class BookTestI {

    //BookController bookController = new BookController();

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
        BookTestI.setStockFilePath(TEMP_STOCK_FILE_PATH);
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
    public void testGetSoldDatesQuantitiesYear_NoPurchases() {
        BookModel testBook = createTestBook(); // Create a test book without any sales
        String expected = testBook.getTitle() + " has had no purchases\n";
        String actual = testBook.getSoldDatesQuantitiesYear();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetSoldDatesQuantitiesYear_WithPurchases() {
        BookModel testBook = createTestBook();

        // Adding a sale to the book with today's date
        Date today = new Date();
        testBook.addSale(today, 5);

        String expected = "For \"" + testBook.getTitle() + "\" We have sold in a year:\n";
        expected += "5 at " + today + "\n";

        String actual = testBook.getSoldDatesQuantitiesYear();
        assertEquals(expected, actual);
    }


    @Test
    public void testGetBoughtDatesQuantitiesDay_NoPurchases() {
        // Create a book without any purchases
        BookModel bookWithoutPurchases = createTestBook();

        // Assert that the method returns the correct message for no purchases
        String expected = "We have made no purchases on \"" + bookWithoutPurchases.getTitle() + "\"\n";
        String actual = bookWithoutPurchases.getBoughtDatesQuantitiesDay();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetBoughtDatesQuantitiesDay_WithPurchases() {
        // Create a book and add a purchase for today
        BookModel bookWithPurchases = createTestBook();
        Date today = new Date();
        bookWithPurchases.addPurchase(today); // Assuming addPurchase adds a purchase on the provided date

        // Assert that the method correctly displays purchases made today
        String expected = "For \"" + bookWithPurchases.getTitle() + "\" We have bought in a day:\n" +
                "1 at " + today + "\n";
        String actual = bookWithPurchases.getBoughtDatesQuantitiesDay();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetBoughtDatesQuantitiesMonth_NoPurchases() {
        // Create a book without any purchases
        BookModel bookWithoutPurchases = createTestBook();

        // Assert that the method returns the correct message for no purchases in the current month
        String expected = "We have made no purchases on \"" + bookWithoutPurchases.getTitle() + "\"\n";
        String actual = bookWithoutPurchases.getBoughtDatesQuantitiesMonth();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetBoughtDatesQuantitiesMonth_WithPurchases() {
        // Create a book and add a purchase for this month
        BookModel bookWithPurchases = createTestBook();
        Date today = new Date();
        bookWithPurchases.addPurchase(today); // Assuming addPurchase adds a purchase on the provided date

        // Assert that the method correctly displays purchases made this month
        String expected = "For \"" + bookWithPurchases.getTitle() + "\" We have bought in a month:\n" +
                "1 at " + today + "\n";
        String actual = bookWithPurchases.getBoughtDatesQuantitiesMonth();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetBoughtDatesQuantitiesYear_NoPurchases() {
        BookModel bookWithoutPurchases = createTestBook();
        String expected = "We have made no purchases on \"" + bookWithoutPurchases.getTitle() + "\"\n";
        String actual = bookWithoutPurchases.getBoughtDatesQuantitiesYear();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetBoughtDatesQuantitiesYear_WithPurchasesThisYear() {
        BookModel bookWithPurchases = createTestBook();
        Date today = new Date();
        bookWithPurchases.addPurchase(today); // Adding a purchase for today

        String expected = "For \"" + bookWithPurchases.getTitle() + "\" We have bought in a year:\n" +
                "1 at " + today + "\n";
        String actual = bookWithPurchases.getBoughtDatesQuantitiesYear();
        assertEquals(expected, actual);
    }



    @Test
    public void testGetTotalBooksSoldDay_NoSales() {
        BookModel bookWithoutSales = createTestBook();
        int expected = 0;
        int actual = bookWithoutSales.getTotalBooksSoldDay();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetTotalBooksSoldDay_WithSalesToday() {
        BookModel bookWithSalesToday = createTestBook();
        Date today = new Date();
        bookWithSalesToday.addSale(today, 5); // Adding 5 sales for today

        int expected = 5; // Assuming 5 sales were added for today
        int actual = bookWithSalesToday.getTotalBooksSoldDay();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetTotalBooksSoldDay_EmptyDate() {
        // Create a test book without any sales (empty dates)
        BookModel testBook = createTestBook(); // Assuming you have a method to create a test book

        // Ensure the book's dates list is empty
        assertTrue(testBook.getDates().isEmpty());

        // Get the total books sold for the day and assert it's zero
        int totalBooksSold = testBook.getTotalBooksSoldDay();
        assertTrue(totalBooksSold == 0);
    }

    //created an empty constructor
    //changed the method in Book.java -> date == null
    @Test
    public void testEmptyDatesListDay() {
        BookModel book = new BookModel();
        book.setDates(null);
        int result = book.getTotalBooksSoldDay();
        assertEquals(0, result);
    }





    @Test
    public void testGetTotalBooksSoldMonth_NoSales() {
        BookModel bookWithoutSales = createTestBook();
        int expected = 0;
        int actual = bookWithoutSales.getTotalBooksSoldMonth();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetTotalBooksSoldMonth_WithSalesToday() {
        BookModel bookWithSalesToday = createTestBook();
        Date today = new Date();
        bookWithSalesToday.addSale(today, 5); // Adding 5 sales for today

        int expected = 5; // Assuming 5 sales were added for today
        int actual = bookWithSalesToday.getTotalBooksSoldMonth();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetTotalBooksSoldMonth_EmptyDate() {
        // Create a test book without any sales (empty dates)
        BookModel testBook = createTestBook(); // Assuming you have a method to create a test book

        // Ensure the book's dates list is empty
        assertTrue(testBook.getDates().isEmpty());

        // Get the total books sold for the day and assert it's zero
        int totalBooksSold = testBook.getTotalBooksSoldMonth();
        assertTrue(totalBooksSold == 0);
    }


    //changed the method in Book.java -> date == null
    @Test
    public void testEmptyDatesListMonth() {
        BookModel book = new BookModel();
        book.setDates(null);
        int result = book.getTotalBooksSoldMonth();
        assertEquals(0, result);
    }







    @Test
    public void testGetTotalBooksSoldYear_NoSales() {
        BookModel bookWithoutSales = createTestBook();
        int expected = 0;
        int actual = bookWithoutSales.getTotalBooksSoldYear();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetTotalBooksSoldYear_WithSalesToday() {
        BookModel bookWithSalesToday = createTestBook();
        Date today = new Date();
        bookWithSalesToday.addSale(today, 5); // Adding 5 sales for today

        int expected = 5; // Assuming 5 sales were added for today
        int actual = bookWithSalesToday.getTotalBooksSoldYear();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetTotalBooksSoldYear_EmptyDate() {
        // Create a test book without any sales (empty dates)
        BookModel testBook = createTestBook(); // Assuming you have a method to create a test book

        // Ensure the book's dates list is empty
        assertTrue(testBook.getDates().isEmpty());

        // Get the total books sold for the day and assert it's zero
        int totalBooksSold = testBook.getTotalBooksSoldYear();
        assertTrue(totalBooksSold == 0);
    }


    //changed the method in Book.java -> date == null
    @Test
    public void testEmptyDatesListYear() {
        BookModel book = new BookModel();
        book.setDates(null);
        int result = book.getTotalBooksSoldYear();
        assertEquals(0, result);
    }


    @Test
    public void testAddSaleWhenDatesAreNull() {
        // Create a Book object
        BookModel book = new BookModel();

        // Ensure dates ArrayList is null
        assertNull(book.getDates());

        // Create a date for the sale
        Date saleDate = new Date(); // You can set this to any desired date

        // Add a sale for the book
        book.addSale(saleDate, 5); // Example quantity: 5

        // Assert that the sale has been added correctly and dates ArrayList is not null anymore
        assertNotNull(book.getDates());
        assertEquals(1, book.getDates().size());
        assertEquals(saleDate, book.getDates().get(0)); // Check if the sale date matches
    }



//    @Test
//    public void testAddSale() {
//        // Create a Book object
//        Book book = new Book();
//
//        // Create a date for the sale
//        Date saleDate = new Date(); // You can set this to any desired date
//
//        // Add a sale for the book
//        book.addSale(saleDate, 5); // Example quantity: 5
//
//        // Assert that the sale has been added correctly
//        assertEquals(1, book.getDates().size()); // Assuming getDates() returns the dates ArrayList
//        assertEquals(1, book.getPurchasedAmount().size()); // Assuming getPurchasedAmount() returns the purchasedAmount ArrayList
//    }










    /////////Ardisa/////////////



//    @Test
//    public void testGetTotalBooksSoldYearEmptyList() {
//        // Arrange
//        Book book = createTestBook(); // Replace YourClass with the actual class name
//
//        // Act
//        int result = book.getTotalBooksSoldYear();
//
//        // Assert
//        assertEquals(0, result); // The result should be 0 when dates list is empty
//    }
//    @Test
//    public void testGetTotalBooksSoldYearCurrentYear() {
//        // Arrange
//        Book book = createTestBook(); // Replace YourClass with the actual class name
//
//        // Mock data for testing
//        Date today = new Date();
//        book.addDate(today);
//        book.addQuantity(5);
//
//        // Add an entry for the last year (should be ignored)
//        Date lastYear = new Date(today.getYear() - 1, today.getMonth(), today.getDate());
//        book.addDate(lastYear);
//        book.addQuantity(10);
//
//
//        // Act
//        int result = book.getTotalBooksSoldYear();
//
//        // Assert
//        assertEquals(5, result); // Only consider purchases in the current year
//    }



    @Test
    public void testGetTotalBooksBoughtDay() {
        // Arrange
        BookModel book = createTestBook();

        // Mock data for testing
        Date today = new Date();
        book.addPurchasedDate(today);
        book.addQuantitiesPurchased(5);

        // Add an entry for yesterday (should be ignored)
        Calendar yesterdayCalendar = Calendar.getInstance();
        yesterdayCalendar.setTime(today);
        yesterdayCalendar.add(Calendar.DATE, -1);
        Date yesterday = yesterdayCalendar.getTime();
        book.addPurchasedDate(yesterday);
        book.addQuantity(10);

        // Act
        int result = book.getTotalBooksBoughtDay();

        // Assert
        assertEquals(5, result); // Only consider purchases on the specified day
    }

    @Test
    public void testGetTotalBooksBoughtMonth() {
        // Arrange
        BookModel book = createTestBook();

        // Mock data for testing
        Date today = new Date();
        book.addPurchasedDate(today);
        book.addQuantitiesPurchased(5);

        // Add an entry for last month (should be ignored)
        Calendar lastMonthCalendar = Calendar.getInstance();
        lastMonthCalendar.setTime(today);
        lastMonthCalendar.add(Calendar.MONTH, -1);
        Date lastMonth = lastMonthCalendar.getTime();
        book.addPurchasedDate(lastMonth);
        book.addQuantity(10);

        // Act
        int result = book.getTotalBooksBoughtMonth();

        // Assert
        assertEquals(5, result); // Only consider purchases in the current month
    }

    @Test
    public void testGetTotalBooksBoughtYear() {
        // Arrange
        BookModel book = createTestBook();

        // Mock data for testing
        Date today = new Date();
        book.addPurchasedDate(today);
        book.addQuantitiesPurchased(5);

        // Add an entry for last year (should be ignored)
        Calendar lastYearCalendar = Calendar.getInstance();
        lastYearCalendar.setTime(today);
        lastYearCalendar.add(Calendar.YEAR, -1);
        Date lastYear = lastYearCalendar.getTime();
        book.addPurchasedDate(lastYear);
        book.addQuantity(10);

        // Act
        int result = book.getTotalBooksBoughtYear();

        // Assert
        assertEquals(5, result); // Only consider purchases in the current year
    }
    @Test
    public void testGetTotalBooksBoughtYearWhenPurchasedDatesEmpty() {
        // Arrange
        BookModel book = createTestBook();

        // Act
        int result = book.getTotalBooksBoughtYear();

        // Assert
        assertEquals(0, result); // The result should be 0 when purchasedDates list is empty
    }
    @Test
    public void testGetTotalBooksBoughtMonthWhenPurchasedDatesEmpty() {
        // Arrange
        BookModel book = createTestBook();

        // Act
        int result = book.getTotalBooksBoughtMonth();

        // Assert
        assertEquals(0, result); // The result should be 0 when purchasedDates list is empty
    }
    @Test
    public void testGetTotalBooksBoughtDayWhenPurchasedDatesEmpty() {
        // Arrange
        BookModel book = createTestBook();

        // Act
        int result = book.getTotalBooksBoughtDay();

        // Assert
        assertEquals(0, result); // The result should be 0 when purchasedDates list is empty
    }

    ////////////RREGULLOJE NEW////////////////
//    @Test
//    public void testToString() {
//        // Arrange
//        BookModel book = createTestBookss();
//
//        // Set the time zone explicitly for the date
//        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
//        dateFormat.setTimeZone(TimeZone.getTimeZone("CET"));
//
//        // Expected string representation with GMT time zone
//        String expectedString = "BookModel [ISBN=123456789, title=TestBook, category=Fiction, supplier=SupplierA, " +
//                "sellingPrice=19.99, originalPrice=24.99, author=John Doe, stock=90, dates=[" +
//                dateFormat.format(new Date(0)) + "]]";
//
//        // Act
//        String result = book.toString();
//
//        // Assert
//        assertEquals(expectedString, result);
//    }

    // Helper method to create a test instance of Book
    private BookModel createTestBookss() {
        BookModel book = createTestBook();
        book.setISBN("123456789");
        book.setTitle("TestBook");
        book.setCategory("Fiction");
        book.setSupplier("SupplierA");
        book.setSellingPrice(19.99);
        book.setOriginalPrice(24.99);
        book.setAuthor("John Doe");
        book.AddStock(80);

        // Adding a date for testing
        ArrayList<Date> dates = new ArrayList<>();
        dates.add(new Date(0)); // Thu Jan 01 00:00:00 GMT 1970
        book.setDates(dates);

        return book;
    }
//    @Test
//    public void testAddPurchase() {
//        // Arrange
//        Book book=createTestBook();
//        Date purchaseDate = new Date();
//
//        // Act
//        book.addPurchase(purchaseDate);
//
//        // Assert
//        // Check that purchasedDates is initialized and contains the added date
//        assertNotNull(book.getPurchasedDates());
//        assertEquals(1, book.getPurchasedDates().size());
//        assertEquals(purchaseDate, book.getPurchasedDates().get(0));
//
//        // Check that quantitiesPurchased is initialized and contains the correct quantity (1)
//        assertNotNull(book.getQuantitiesPurchased());
//        assertEquals(1, book.getQuantitiesPurchased());
//        assertEquals(1, book.getQuantitiesPurchased());
//    }

    @Test
    public void testAddPurchase() {
        // Arrange
        BookModel book = createTestBook();
        Date purchaseDate = new Date();

        // Act
        book.addPurchase(purchaseDate);

        // Assert
        // Check that purchasedDates is initialized and contains the added date
        assertNotNull(book.getPurchasedDates());
        assertEquals(1, book.getPurchasedDates().size());
        assertEquals(purchaseDate, book.getPurchasedDates().get(0));

        // Check that quantitiesPurchased is initialized and contains the correct quantity (1)
        assertNotNull(book.getQuantitiesPurchased());
        assertEquals(1, book.getQuantitiesPurchased());
        assertEquals(Integer.valueOf(1), book.getQuantitiesPurchased());


        ArrayList<Date> a=book.getPurchasedDates();
        if(a==null){
            book.addPurchasedDate(new Date());
        }
    }

    @Test
    public void testInitializeListsIfNull() {
        // Arrange
        BookModel book = createTestBook();

        // Act (simulate the behavior of your provided code snippet)
        if (book.getPurchasedDates() == null) {
            book.addPurchasedDate(new Date());
        }
        if (book.getQuantitiesPurchased() == 0) {
            book.addQuantity(0);
        }

        if(book.getPurchasedDates()==null){
            ArrayList<Date>dates=new ArrayList<>();

        }

        // Assert
        // Check that purchasedDates is initialized to an empty list
        assertNotNull(book.getPurchasedDates());
        assertEquals(0, book.getPurchasedDates().size());

        // Check that quantitiesPurchased is initialized to an empty list
        assertNotNull(book.getQuantitiesPurchased());
        assertEquals(0, book.getQuantitiesPurchased());

    }
    @Test
    public void testEquals() {
        // Arrange
        BookModel instance1 = createTestBook();
        BookModel instance2 = createTestBook();
        BookModel instance3=new BookModel(null);
        // Act & Assert
        // Test reflexivity: an object should equal itself
        assertTrue(instance1.equals(instance1));

        // Test symmetry: if A equals B, then B should equal A
        assertTrue(instance1.equals(instance2));
        assertTrue(instance2.equals(instance1));

        // Test with null object
        assertFalse(instance3.equals(null));


    }








    ///////Klea/////////


    @Test
    public void testGetPurchasedAmount() {
        // Create a Book object
        BookModel book = new BookModel();

        // Add some purchase amounts
        book.addSale(new Date(), 5); // Adding a sale with a quantity of 5
        book.addSale(new Date(), 3); // Adding another sale with a quantity of 3

        // Get the purchased amount
        int totalPurchased = book.getPurchasedAmount();

        // Calculate the expected total based on the added sale quantities
        int expectedTotal = 5 + 3; // Sum of the two sale quantities

        // Assert that the calculated total matches the expected total
        assertEquals(expectedTotal, totalPurchased);
    }


    @Test
    public void testGetQuantitiesPurchased() {
        // Create a Book object
        BookModel book = new BookModel();

        // Add some quantities purchased
        book.addPurchase(new Date()); // Adding a purchase with a quantity of 1
        book.addPurchase(new Date()); // Adding another purchase with a quantity of 1

        // Get the total quantities purchased
        int totalQuantitiesPurchased = book.getQuantitiesPurchased();

        // Calculate the expected total based on the added purchases
        int expectedTotal = 1 + 1; // Sum of the two purchase quantities

        // Assert that the calculated total matches the expected total
        assertEquals(expectedTotal, totalQuantitiesPurchased);
    }


    @Test
    public void testGetSoldDatesQuantitiesDay_WithPurchases() {
        // Create a Book object
        BookModel book = new BookModel();
        book.setTitle("Test Book");

        // Add a sale for today with a quantity of 3
        Date saleDate = new Date();
        book.addSale(saleDate, 3);

        // Get the sold dates and quantities for today
        String soldDatesQuantitiesToday = book.getSoldDatesQuantitiesDay();

        // Get today's date
        LocalDate today = LocalDate.now();

        // Calculate the expected result based on the added sale for today
        StringBuilder expected = new StringBuilder("For \"Test Book\" We have sold in a day:\n");
        expected.append("3 at ").append(saleDate).append("\n");

        // Assert that the calculated result matches the expected result
        assertEquals(expected.toString(), soldDatesQuantitiesToday);
    }

    @Test
    public void testGetSoldDatesQuantitiesDay_NoPurchases() {
        BookModel testBook = createTestBook();

        // Set dates to null to simulate the condition
        testBook.setDates(null);
        testBook.setPurchasedAmount(null);

        String expected = "Test Book has had no purchases\n";
        String actual = testBook.getSoldDatesQuantitiesDay();

        assertEquals(expected, actual);
    }



    @Test
    public void testGetSoldDatesQuantitiesMonth_WithPurchases() {
        // Create a Book object
        BookModel book = new BookModel();
        book.setTitle("Test Book");

        // Add a sale for today with a quantity of 3
        Date saleDate = new Date();
        book.addSale(saleDate, 3);

        // Get the sold dates and quantities for today
        String soldDatesQuantitiesToday = book.getSoldDatesQuantitiesMonth();

        // Get today's date
        LocalDate today = LocalDate.now();

        // Calculate the expected result based on the added sale for today
        StringBuilder expected = new StringBuilder("For \"Test Book\" We have sold in a month:\n");
        expected.append("3 at ").append(saleDate).append("\n");

        // Assert that the calculated result matches the expected result
        assertEquals(expected.toString(), soldDatesQuantitiesToday);
    }

    @Test
    public void testGetSoldDatesQuantitiesMonth_NoPurchases() {
        BookModel testBook = createTestBook();

        // Set dates to null to simulate the condition
        testBook.setDates(null);
        testBook.setPurchasedAmount(null);

        String expected = "Test Book has had no purchases\n";
        String actual = testBook.getSoldDatesQuantitiesMonth();

        assertEquals(expected, actual);
    }



    ////////EXTRA////////////

    @Test
    public void testGetISBN() {
        BookModel testBook = createTestBook(); // Assuming createTestBook() generates a Book for testing
        String isbn = "1234567890"; // Replace with the expected ISBN
        testBook.setISBN(isbn);

        String retrievedISBN = testBook.getISBN();
        assertEquals(isbn, retrievedISBN);
    }

    @Test
    public void testGetCategory() {
        BookModel testBook = createTestBook();
        String category = "Fantasy"; // Replace with the expected category
        testBook.setCategory(category);

        String retrievedCategory = testBook.getCategory();
        assertEquals(category, retrievedCategory);
    }

    @Test
    public void testGetSupplier() {
        BookModel testBook = createTestBook();
        String supplier = "Supplier X"; // Replace with the expected supplier
        testBook.setSupplier(supplier);

        String retrievedSupplier = testBook.getSupplier();
        assertEquals(supplier, retrievedSupplier);
    }

    @Test
    public void testGetSellingPrice() {
        // Create a test book
        BookModel testBook = createTestBook();

        // Set the selling price for the test book
        double expectedSellingPrice = 25.99; // Replace with your expected selling price
        testBook.setSellingPrice(expectedSellingPrice);

        // Retrieve the selling price using the method and compare with the expected value
        double retrievedSellingPrice = testBook.getSellingPrice();
        assertEquals(expectedSellingPrice, retrievedSellingPrice, 0.001); // Change delta according to precision needed
    }

    @Test
    public void testGetOriginalPrice() {
        // Create a test book
        BookModel testBook = createTestBook();

        // Set the original price for the test book
        double expectedOriginalPrice = 19.99; // Replace with your expected original price
        testBook.setOriginalPrice(expectedOriginalPrice);

        // Retrieve the original price using the method and compare with the expected value
        double retrievedOriginalPrice = testBook.getOriginalPrice();
        assertEquals(expectedOriginalPrice, retrievedOriginalPrice, 0.001); // Change delta according to precision needed
    }

    @Test
    public void testGetStock() {
        // Create a test book
        BookModel testBook = createTestBook();

        // Set the stock for the test book
        int expectedStock = 10; // Replace with your expected stock
        testBook.setStock(expectedStock);

        // Retrieve the stock using the method and compare with the expected value
        int retrievedStock = testBook.getStock();
        assertEquals(expectedStock, retrievedStock);
    }


    @Test
    public void testRemoveStock() {
        BookModel book = createTestBook(); // Assuming you have a createTestBook() method

        int initialStock = book.getStock();
        int quantityToRemove = 3;
        book.RemoveStock(quantityToRemove);

        int expectedStock = initialStock - quantityToRemove;
        int actualStock = book.getStock();

        assertEquals(expectedStock, actualStock, "Stock count should reduce by the specified quantity");
    }

//    @Test
//    public void testInsufficientStock() {
//        // Redirect standard output to capture printed content
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(outputStream));
//
//        Book book = createTestBook(); // Assuming you have a createTestBook() method
//
//        int initialStock = book.getStock();
//        int quantityToRemove = initialStock + 1; // Attempting to remove more than the available stock
//        book.RemoveStock(quantityToRemove);
//
//        // Reset standard output
//        System.setOut(System.out);
//
//        String expectedOutput = "Insufficient stock!\n";
//        String actualOutput = outputStream.toString();
//
//        assertEquals(expectedOutput, actualOutput, "Should print 'Insufficient stock!' message");
//    }

    @Test
    public void testAddDate() {
        BookModel book = createTestBook(); // Assuming you have a createTestBook() method

        ArrayList<Date> initialDates = new ArrayList<>(book.getDates());
        Date newDate = new Date();
        book.addDate(newDate);

        ArrayList<Date> updatedDates = book.getDates();
        boolean dateAdded = updatedDates.contains(newDate);

        assertEquals(initialDates.size() + 1, updatedDates.size(), "A new date should be added");
        assertEquals(true, dateAdded, "The newly added date should exist in the dates list");
    }




}
