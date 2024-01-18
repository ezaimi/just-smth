package integrationTest;

import com.example.kthimi.Controller.BookController;
import com.example.kthimi.Controller.LibrarianFuncController;
import com.example.kthimi.Controller.Mockers.FileBasedStockBookRepository;
import com.example.kthimi.Controller.StatisticsFuncController;
import com.example.kthimi.Model.BookModel;
import com.example.kthimi.Model.LibrarianModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class LibrarianTestI {

    LibrarianFuncController librarianFuncController = new LibrarianFuncController();
    BookController bookController = new BookController();
    StatisticsFuncController statisticsFuncController = new StatisticsFuncController();
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
        LibrarianTestI.setStockFilePath(TEMP_STOCK_FILE_PATH);
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


//    private Book createTestBookForCheckingBooksOut() {
//        return new Book(TEST_ISBN, TEST_TITLE, "Category1", "Test Publisher", 20.00, 25.00, "Test Author", 10);
//    }

    @Test
    public void testCheckoutBooks() throws IOException, ClassNotFoundException {

        //LibrarianModel librarian = new LibrarianModel("username", "password", "name", 1000.00, "1234567890", "test@example.com");

        // Create a list of stock books
        ArrayList<BookModel> stockBooks = new ArrayList<>();
        stockBooks.add(createTestBook()); // Adding a test book to the stock

        // Set up the initial stock data
        saveBooksToTemporaryFile(stockBooks);

        // Choose a book to check out
        ArrayList<BookModel> chosenBooks = new ArrayList<>();
        chosenBooks.add(createTestBook()); // Choosing the test book to buy

        // Specify the quantity of the chosen book
        ArrayList<Integer> quantities = new ArrayList<>();
        quantities.add(2); // Buying two copies of the test book

        // Execute the checkout process
        librarianFuncController.checkOutBooks(chosenBooks, quantities);
        System.out.println("te testi");
        //System.out.println(stockBooks.toString());

        // Directly query the Librarian instance for the current stockBooks
        ArrayList<BookModel> updatedStock = bookController.getStockBooks();

        // Retrieve the updated book after checkout
        BookModel updatedBook = null;
            for (BookModel book : updatedStock) {
                System.out.println(createTestBook().getTitle() + "hhahhahhahahaha" + book.getTitle());
                if (book.getISBN().equals(createTestBook().getISBN())) {
                updatedBook = book;
                break;
            }
        }

        // Print the book with the reduced quantity
        if (updatedBook != null) {
            System.out.println("Updated Book: " + updatedBook.toString());
        } else {
            System.out.println("Book not found.");
        }

        // Assert the stock has reduced after checkout
        assertNotNull(updatedBook);
        assertEquals(8, updatedBook.getStock(), "Stock should decrease after checkout");

    }

    private ArrayList<BookModel> loadBooksFromTemporaryFile() throws IOException, ClassNotFoundException {
        ArrayList<BookModel> stock;
        try (ObjectInputStream objin = new ObjectInputStream(new FileInputStream(TEMP_STOCK_FILE_PATH))) {
            stock = (ArrayList<BookModel>) objin.readObject();
        }
        return stock;
    }

//
//    @Test
//    public void testValidBookCheckout() throws IOException {
//        ArrayList<Book> stockBooks = new ArrayList<>();
//        Book testBook = createTestBook();
//        testBook.setStock(10); // Set initial stock to 10
//        stockBooks.add(testBook); // Adding a test book to the stock
//
//        Librarian librarian = new Librarian("username", "password", "name", 1000.00, "1234567890", "test@example.com");
//
//        ArrayList<Book> chosenBooks = new ArrayList<>();
//        chosenBooks.add(testBook); // Choosing the test book to buy
//
//        ArrayList<Integer> quantities = new ArrayList<>();
//        quantities.add(2); // Buying two copies of the test book
//
//        librarian.checkOutBooks(chosenBooks, quantities);
//
//        // After checkout, assert that the stock quantity has reduced
//        Book checkedOutBook = Book.findBookInStock(stockBooks, TEST_ISBN);
//        if (checkedOutBook != null) {
//            assertEquals(8, checkedOutBook.getStock()); // Expecting stock quantity to reduce to 8
//        } else {
//            // Handle book not found
//            fail("Book not found in stock");
//        }
//
//        // Add assertions for the lines covered within the condition
//        // For instance, you can check if the totalPrice, totalBooksSold, and writer content are as expected.
//    }




    @Test
    public void testRemoveDuplicatesSoldBooks() {
        // Instantiate your Librarian class or use a proper constructor

        // Create test books with same ISBN and quantities
        BookModel book1 = new BookModel("ISBN1", "Title1","Category1", "Test Publisher", 20.00, 25.00, "Test Author", 10);
        BookModel book2 = new BookModel("ISBN2", "Title2", "Category1", "Test Publisher", 20.00, 25.00, "Test Author", 10); // Add details for book2
        BookModel book3 = new BookModel("ISBN1", "Title3", "Category1", "Test Publisher", 20.00, 25.00, "Test Author", 10); // Another book with the same ISBN as book1
        BookModel book4 = new BookModel("ISBN4", "Title4", "Category1", "Test Publisher", 20.00, 25.00, "Test Author", 10); // Unique book

        ArrayList<BookModel> books = new ArrayList<>(Arrays.asList(book1, book2, book3, book4));
        ArrayList<Integer> quantities = new ArrayList<>(Arrays.asList(1, 2, 3, 4)); // Quantities corresponding to books

        LibrarianModel librarian = new LibrarianModel("1","1");
        LibrarianFuncController.removeDuplicatesSoldBooks(books, quantities);

        for(BookModel book : books){
            System.out.println(book.toString());
        }
        // Assert the expected size of books after merging
        assertEquals(3, books.size());


        System.out.println(quantities.get(0));
        // Assert the expected quantities after merging
        assertEquals(4, quantities.get(0)); // book 1 and 3 quantities are merged ( 1 + 3 = 4)

        assertEquals(2, quantities.get(1)); // Check quantity for book4
    }

/////////////idk per kte./////////////////
//
//    @Test
//    public void testConditionNGreaterThanOrEqualToOne() {
//        // Create a list with at least two books
//        Book book1 = new Book("ISBN1", "Title1", "Category1", "Test Publisher", 20.00, 25.00, "Test Author", 10);
//        Book book2 = new Book("ISBN2", "Title2", "Category1", "Test Publisher", 20.00, 25.00, "Test Author", 10);
//
//        ArrayList<Book> books = new ArrayList<>(Arrays.asList(book1, book2));
//        ArrayList<Integer> quantities = new ArrayList<>(Arrays.asList(1, 2)); // Quantities corresponding to books
//
//        Librarian.removeDuplicatesSoldBooks(books, quantities);
//
//        // Assert that the condition n >= 1 becomes true
//        assertTrue(books.size() >= 1);
//    }



    ////////////Ardisa////////////

    @Test
    public void testMoneymadeinDay1(){
        var stat=new StatisticsFuncController();
        stat.datesSold = null;
        double result =stat.moneyMadeInDay(); // Replace with the actual method you're testing
        assertEquals(0, result);



    }
    @Test
    public void testMoneymadeinDay2(){
        Date today = new Date();

        System.out.println(today);

        var stat=new StatisticsFuncController();
        ArrayList<Date> getDates=new ArrayList<>();
        getDates.add(today);

        // Create an instance of your class

        // Set up datesSold and moneyMadeDates with specific values
        ArrayList<Date> datesSold = new ArrayList<>();
        ArrayList<Double> moneyMadeDates = new ArrayList<>();

        // Add dates and corresponding money made values for today (replace with actual logic)
        datesSold.add(today);
        moneyMadeDates.add(50.0);  // Replace with the actual money made for today

        // Set datesSold and moneyMadeDates in your class
        stat.datesSold = datesSold;
        stat.moneyMadeDates = moneyMadeDates;

        // Test the method
        double result = stat.moneyMadeInDay();  // Replace with the actual method you're testing

        // Assert the result
        // If today's date is present in datesSold, the result should be the corresponding money made value
        assertEquals(50.0, result);


    }

    @Test
    public void testMoneymadeinMonthy1(){
        var stat=new StatisticsFuncController();
        stat.datesSold = null;
        double result =stat.moneyMadeInMonth(); // Replace with the actual method you're testing
        assertEquals(0, result);



    }
    @Test
    public void testMoneymadeinMonth2(){
        Date today = new Date();

        System.out.println(today);

        var stat=new StatisticsFuncController();
        ArrayList<Date> getDates=new ArrayList<>();
        getDates.add(today);

        // Create an instance of your class

        // Set up datesSold and moneyMadeDates with specific values
        ArrayList<Date> datesSold = new ArrayList<>();
        ArrayList<Double> moneyMadeDates = new ArrayList<>();

        // Add dates and corresponding money made values for today (replace with actual logic)
        datesSold.add(today);
        moneyMadeDates.add(50.0);  // Replace with the actual money made for today

        // Set datesSold and moneyMadeDates in your class
        stat.datesSold = datesSold;
        stat.moneyMadeDates = moneyMadeDates;

        // Test the method
        double result = stat.moneyMadeInMonth();  // Replace with the actual method you're testing

        // Assert the result
        // If today's date is present in datesSold, the result should be the corresponding money made value
        assertEquals(50.0, result);


    }

    @Test
    public void testMoneymadeinYear1(){
        var stat=new StatisticsFuncController();
        stat.datesSold = null;
        double result =stat.moneyMadeInYear(); // Replace with the actual method you're testing
        assertEquals(0, result);



    }
    @Test
    public void testMoneymadeinYear2(){
        Date today = new Date();

        System.out.println(today);

        var stat=new StatisticsFuncController();
        ArrayList<Date> getDates=new ArrayList<>();
        getDates.add(today);

        // Create an instance of your class

        // Set up datesSold and moneyMadeDates with specific values
        ArrayList<Date> datesSold = new ArrayList<>();
        ArrayList<Double> moneyMadeDates = new ArrayList<>();

        // Add dates and corresponding money made values for today (replace with actual logic)
        datesSold.add(today);
        moneyMadeDates.add(50.0);  // Replace with the actual money made for today

        // Set datesSold and moneyMadeDates in your class
        stat.datesSold = datesSold;
        stat.moneyMadeDates = moneyMadeDates;

        // Test the method
        double result = stat.moneyMadeInYear();  // Replace with the actual method you're testing

        // Assert the result
        // If today's date is present in datesSold, the result should be the corresponding money made value
        assertEquals(50.0, result);


    }

    @Test
    public void testMoneyMadeInMonthNoSales() {
        LibrarianModel librarian = new LibrarianModel("1", "1"); // Instantiate your Librarian class
        librarianFuncController.datesSold = null; // Set datesSold to null explicitly
        double moneyMade = statisticsFuncController.moneyMadeInMonth();
        assertEquals(0, moneyMade, 0); // Assuming a negligible error    }
    }

    @Test
    public void testMoneyMadeInMonthWithSales() throws IOException {
        LibrarianModel librarian = new LibrarianModel("1","1"); // Instantiate your Librarian class

        // Add some sales to datesSold and moneyMadeDates
        ArrayList<Date> datesSold = new ArrayList<>();
        ArrayList<Double> moneyMadeDates = new ArrayList<>();
        datesSold.add(new Date()); // Add today's date
        moneyMadeDates.add(1.0); // Add some money made for today

        // Set the datesSold and moneyMadeDates in librarian
        BookModel  b1=createTestBook();
        BookModel b2=createTestBook();

        ArrayList<BookModel>arrayList=new ArrayList<>();
        arrayList.add(b1);
        arrayList.add(b2);

        int x=b1.getPurchasedAmount();
        int y=b2.getPurchasedAmount();
        System.out.println("x");
        System.out.println(x);
        ArrayList<Integer>quantities=new ArrayList<>();
        quantities.add(x);
        quantities.add(y);
        librarianFuncController.checkOutBooks(arrayList,quantities);


        double moneyMade = statisticsFuncController.moneyMadeInMonth();
        assertEquals(1.0, moneyMade, 1.0); // Adjust the expected value based on your test data
    }

    @Test
    public void testMoneyMadeInYearNoSales() {
        LibrarianModel librarian = new LibrarianModel("1", "1"); // Instantiate your Librarian class
        librarianFuncController.datesSold = null; // Set datesSold to null explicitly
        double moneyMade = statisticsFuncController.moneyMadeInYear();
        assertEquals(0, moneyMade, 0); // Assuming a negligible error    }
    }

    @Test
    public void testMoneyMadeInYearWithSales() throws IOException {
        LibrarianModel librarian = new LibrarianModel("1","1"); // Instantiate your Librarian class

        // Add some sales to datesSold and moneyMadeDates
        ArrayList<Date> datesSold = new ArrayList<>();
        ArrayList<Double> moneyMadeDates = new ArrayList<>();
        datesSold.add(new Date()); // Add today's date
        moneyMadeDates.add(1.0); // Add some money made for today

        // Set the datesSold and moneyMadeDates in librarian
        BookModel  b1=createTestBook();
        BookModel b2=createTestBook();

        ArrayList<BookModel>arrayList=new ArrayList<>();
        arrayList.add(b1);
        arrayList.add(b2);

        int x=b1.getPurchasedAmount();
        int y=b2.getPurchasedAmount();
        System.out.println("x");
        System.out.println(x);
        ArrayList<Integer>quantities=new ArrayList<>();
        quantities.add(x);
        quantities.add(y);
        librarianFuncController.checkOutBooks(arrayList,quantities);


        double moneyMade = statisticsFuncController.moneyMadeInYear();
        assertEquals(1.0, moneyMade, 1.0); // Adjust the expected value based on your test data
    }

    @Test
    public void testToStringLib() {
        // Create a Librarian object with some sample data
        LibrarianModel librarian = new LibrarianModel("sampleUsername", "samplePassword", "John Doe", 50000.0, "123456789", "john.doe@example.com");

        // Define the expected result based on the sample data
        String expectedToString = "Librarian [username=sampleUsername, password=samplePassword, name=John Doe, salary=50000.0, phone=123456789, email=john.doe@example.com]";

        // Call the toString method and compare the result with the expected string
        assertEquals(expectedToString, librarian.toString());
    }




    ////////////////////Klea///////////////
//
//    @Test
//    public void testEnoughStockWithValidISBNAndEnoughStock() throws IOException {
//        // Set up stock for testing using createTestBook()
//        ArrayList<BookModel> stockBooks = new ArrayList<>();
//        stockBooks.add(createTestBook()); // Assume createTestBook() creates a Book with ISBN "123456789" and quantity 10
//        stockBooks.add(new BookModel("123456789", "Book2", "Category2", "Supplier2", 15.0, 25.0, "Author2", 5));
//        BookController.updateBooks(stockBooks);
//        assertTrue(LibrarianModel.EnoughStock("123456789", 5));
//    }
//
//    @Test
//    public void testEnoughStockWithValidISBNAndInsufficientStock() throws IOException {
//        // Set up stock for testing using createTestBook()
//        ArrayList<BookModel> stockBooks = new ArrayList<>();
//        stockBooks.add(createTestBook()); // Assuming createTestBook() creates a Book with default values
//        stockBooks.add(createTestBook()); // Add another book if needed
//        BillNumber.updateBooks(stockBooks);
//
//        // Assert that there is insufficient stock for the specified ISBN and quantity
//        assertFalse(Librarian.EnoughStock("123456789", 15));
//    }
//
//
//
//    @Test
//    public void testEnoughStockWithInvalidISBN() {
//        assertFalse(Librarian.EnoughStock("999999999", 5));
//    }
//
//    @Test
//    public void testEnoughStockWithValidISBNAndExactStock() throws IOException {
//        // Set up stock for testing using createTestBook()
//        ArrayList<Book> stockBooks = new ArrayList<>();
//        stockBooks.add(createTestBook());
//        BillNumber.updateBooks(stockBooks);
//
//        // Assert that there is enough stock for the specified ISBN and exact quantity
//        assertTrue(Librarian.EnoughStock(TEST_ISBN, 10));
//    }



//
//    @Test
//    public void testCheckPasswordWithValidPassword() {
//        assertTrue(LibrarianModel.checkPassword("securePW1"));
//    }
//
//    @Test
//    public void testCheckPasswordWithInvalidShortPassword() {
//        assertFalse(Librarian.checkPassword("shortPW"));
//    }
//
//
//    @Test
//    public void testCheckPhoneWithValidPhone() {
//        assertTrue(Librarian.checkPhone("(912) 123-4567"));
//    }
//
//    @Test
//    public void testCheckPhoneWithInvalidPhoneFormat() {
//        assertFalse(Librarian.checkPhone("123-456-7890"));
//    }
//
//    @Test
//    public void testCheckEmailWithValidEmail() {
//        assertTrue(Librarian.checkEmail("calvl@manager.com"));
//
//    }
//
//    @Test
//    public void testCheckEmailWithInvalidEmailFormat() {
//        assertFalse(Librarian.checkEmail("invalid-email"));
//    }
//
//    @Test
//    public void testCheckSalaryWithValidSalary() {
//        assertTrue(Librarian.checkSalary("50000.00"));
//    }
//
//    @Test
//    public void testCheckSalaryWithInvalidSalaryFormat() {
//        assertFalse(Librarian.checkSalary("invalid-salary"));
//    }
//
//    @Test
//    public void testCheckNameWithValidName() {
//        assertTrue(Librarian.checkName("John"));
//    }
//
//    @Test
//    public void testCheckNameWithInvalidNameFormat() {
//        assertFalse(LibrarianModel.checkName("123"));
//    }
//
//



    /////////////////////EXTRA//////////////////

    @Test
    public void testGetNumberOfBills() {
        LibrarianModel librarian = new LibrarianModel();

        librarianFuncController.setNumberOfBills(3);
        assertEquals(3,librarianFuncController.getNumberOfBills() , "Number of bills should match");
    }

    @Test
    public void testGetBooksSold(){
        LibrarianModel librarian = new LibrarianModel();

        librarian.setBooksSold(3);
        assertEquals(3,librarian.getBooksSold());


    }

    @Test
    public void testGetUsername(){
        LibrarianModel librarian = new LibrarianModel();

        librarian.setUsername("bob");
        assertEquals("bob",librarian.getUsername());


    }

    @Test
    public void testGetPassword(){
        LibrarianModel librarian = new LibrarianModel();

        librarian.setPassword("lol");
        assertEquals("lol",librarian.getPassword());
    }


    @Test
    public void testGetName(){
        LibrarianModel librarian = new LibrarianModel();

        librarian.setName("dikush");
        assertEquals("dikush",librarian.getName());
    }

    @Test
    public void testGetSalary(){
        LibrarianModel librarian = new LibrarianModel();

        librarian.setSalary(1000);
        assertEquals(1000,librarian.getSalary());
    }

    @Test
    public void testGetPhone(){
        LibrarianModel librarian = new LibrarianModel();

        librarian.setPhone("0799");
        assertEquals("0799",librarian.getPhone());
    }

    @Test
    public void testGetEmail(){
        LibrarianModel librarian = new LibrarianModel();

        librarian.setEmail("e@m");
        assertEquals("e@m",librarian.getEmail());
    }


    ///////////////tek billnumberi///////////////////



    @Test
    public void testGetBoughtDatesQuantitiesDay_NoPurchases() {
        // Create a sample Book with no purchases
        BookModel testBook = new BookModel("1234567890123", "Test Book", "Category1", "Test Publisher", 20.00, 25.00, "Test Author", 1);

        // Invoke the method
        String result = testBook.getBoughtDatesQuantitiesDay();

        // Define the expected result for no purchases
        String expected = "We have made no purchases on \"Test Book\"\n";

        // Assert the equality of the expected and actual results
        assertEquals(expected, result);
    }

    @Test
    public void testGetBoughtDatesQuantitiesDay_WithPurchases() {
        // Create a sample Book with purchases on the current day
        BookModel testBook = new BookModel("1234567890123", "Test Book", "Category1", "Test Publisher", 20.00, 25.00, "Test Author", 1);

        // Set the purchased date for the test book to today
        testBook.addPurchase(new Date());

        // Invoke the method
        String result = testBook.getBoughtDatesQuantitiesDay();

        // Define the expected result for purchases on the current day
        // Customize the expected result based on your test data
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        String currentDateFormatted = dateFormat.format(new Date());

        // Define the expected result for purchases on the current day
        String expected = "For \"Test Book\" We have bought in a day:\n1 at " + currentDateFormatted + "\n";
        // Assert the equality of the expected and actual results
        assertEquals(expected, result);
    }

    @Test
    public void testGetBoughtDatesQuantitiesDay_MultiplePurchases() {
        // Create a sample Book with purchases on different days
        BookModel testBook = new BookModel("1234567890123", "Test Book", "Category1", "Test Publisher", 20.00, 25.00, "Test Author", 1);

        // Set the purchased date for the test book to yesterday and today
        testBook.addPurchase(getYesterday());
        testBook.addPurchase(new Date());

        // Invoke the method
        String result = testBook.getBoughtDatesQuantitiesDay();

        // Define the expected result for purchases on the current day
        // Customize the expected result based on your test data
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        String currentDateFormatted = dateFormat.format(new Date());

        // Define the expected result for purchases on the current day
        String expected = "For \"Test Book\" We have bought in a day:\n1 at " + currentDateFormatted + "\n";
        // Assert the equality of the expected and actual results
        assertEquals(expected, result);
    }

    // Helper method to get yesterday's date
    private Date getYesterday() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }


    @Test
    public void testAddBookToStock() {
        // Arrange
        BookModel testBook = createTestBook();

        // Act
        bookController.addBookToStock(testBook);

        // Assert
        ArrayList<BookModel> stockBooks = bookController.getStockBooks();

        // Ensure the test book is in the stock
        assertTrue(stockBooks.contains(testBook));

        // Clean up: Delete the test file created during the test
        File testFile = new File(TEMP_STOCK_FILE_PATH);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    void testAddBookToStockMultipleBooks() {
        // Arrange
        ArrayList<BookModel> initialStock = bookController.getStockBooks();
        BookModel book1 = createTestBook();
        BookModel book2 = createTestBook();

        // Act
        bookController.addBookToStock(book1);
        bookController.addBookToStock(book2);

        // Assert
        ArrayList<BookModel> updatedStock = bookController.getStockBooks();
        assertTrue(updatedStock.contains(book1));
        assertTrue(updatedStock.contains(book2));
        assertEquals(initialStock.size() + 2, updatedStock.size());
    }

    @Test
    void testAddBookToStockNoBooksAdded() {
        // Arrange
        ArrayList<BookModel> initialStock = bookController.getStockBooks();
        int initialStockSize = initialStock.size();

        // Act: No books added to the stock

        // Assert
        ArrayList<BookModel> updatedStock = bookController.getStockBooks();
        assertEquals(initialStockSize, updatedStock.size());
        assertEquals(initialStock, updatedStock);
    }

    @Test
    public void testEquals() {
        BookModel obj1 = new BookModel("1234567890123", "Test Book", "Category1", "Test Publisher", 20.00, 25.00, "Test Author", 1);
        BookModel obj2 = new BookModel("1234567890123", "Test Book", "Category1", "Test Publisher", 20.00, 25.00, "Test Author", 1);
        BookModel obj3 = new BookModel("9876543210987", "Different Book", "Category2", "Another Publisher", 30.00, 35.00, "Another Author", 2);
        // Arrange
        bookController.equals(obj1);

        // Act & Assert
        // Test equality with itself
        assertTrue(obj1.equals(obj1));

        // Test equality with an identical instance
        assertTrue(obj1.equals(obj2));
        assertTrue(obj2.equals(obj1));

        // Test inequality with a different instance
        assertFalse(obj1.equals(obj3));
        assertFalse(obj3.equals(obj1));

        // Test inequality with null
        assertFalse(obj1.equals(null));

        // Test inequality with an object of a different class
        assertFalse(obj1.equals("Not an instance of BookModel"));

        // Test inequality when ISBN is different
        BookModel objWithDifferentIsbn = new BookModel("9999999999999", "Test Book", "Category1", "Test Publisher", 20.00, 25.00, "Test Author", 1);
        assertFalse(obj1.equals(objWithDifferentIsbn));

        // Test inequality when Title is different
        BookModel objWithDifferentTitle = new BookModel("1234567890123", "Different Title", "Category1", "Test Publisher", 20.00, 25.00, "Test Author", 1);
        assertFalse(obj1.equals(objWithDifferentTitle));

        // Add similar tests for other properties (e.g., Category, Publisher, Price, Author, Quantity)
    }

}