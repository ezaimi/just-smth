package mocking;

import com.example.kthimi.Controller.BookController;
import com.example.kthimi.Controller.Mockers.MockStockBookRepository;
import com.example.kthimi.Controller.StatisticsFuncController;
import com.example.kthimi.Model.BookModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class StatisticsTest {

    @Test
    public void testGetBooksSoldDay() {
        // Create a mock repository
        MockStockBookRepository mockRepository = new MockStockBookRepository();

        // Create a BookController instance with the mock repository
        BookController mockBookController = new BookController(mockRepository);

        // Create a StatisticsFuncController instance with the mock repository
        StatisticsFuncController statisticsFuncController = new StatisticsFuncController(mockBookController);

        // Prepare test data
        ArrayList<BookModel> testBooks = new ArrayList<>();
        testBooks.add(new BookModel("ISBN1", "Book1", "Category1", "Supplier1", 20.0, 15.0, "Author1", 50));
        testBooks.add(new BookModel("ISBN2", "Book2", "Category2", "Supplier2", 30.0, 25.0, "Author2", 30));

        // Set the test data in the mock repository
        mockRepository.setStockBooks(testBooks);

        // Call the method to test
        String result = statisticsFuncController.getBooksSoldDay();

        // Assertions
        assertNotNull(result);
        assertTrue(result.contains("For Books Sold Today We Have:")); // Check if the output contains the expected string
    }



    @Test
    public void testGetBooksSoldMonth() {
        // Create a mock repository
        MockStockBookRepository mockRepository = new MockStockBookRepository();

        // Create a BookController instance with the mock repository
        BookController mockBookController = new BookController(mockRepository);

        // Create a StatisticsFuncController instance with the mock repository
        StatisticsFuncController statisticsFuncController = new StatisticsFuncController(mockBookController);

        // Prepare test data
        ArrayList<BookModel> testBooks = new ArrayList<>();
        testBooks.add(new BookModel("ISBN1", "Book1", "Category1", "Supplier1", 20.0, 15.0, "Author1", 50));
        testBooks.add(new BookModel("ISBN2", "Book2", "Category2", "Supplier2", 30.0, 25.0, "Author2", 30));

        // Set the test data in the mock repository
        mockRepository.setStockBooks(testBooks);

        // Call the method to test
        String result = statisticsFuncController.getBooksSoldMonth();

        // Assertions
        assertNotNull(result);
        assertTrue(result.contains("For Books Sold In A Month We Have")); // Check if the output contains the expected string
    }


    @Test
    public void testGetBooksSoldYear() {
        // Create a mock repository
        MockStockBookRepository mockRepository = new MockStockBookRepository();

        // Create a BookController instance with the mock repository
        BookController mockBookController = new BookController(mockRepository);

        // Create a StatisticsFuncController instance with the mock repository
        StatisticsFuncController statisticsFuncController = new StatisticsFuncController(mockBookController);

        // Prepare test data
        ArrayList<BookModel> testBooks = new ArrayList<>();
        testBooks.add(new BookModel("ISBN1", "Book1", "Category1", "Supplier1", 20.0, 15.0, "Author1", 50));
        testBooks.add(new BookModel("ISBN2", "Book2", "Category2", "Supplier2", 30.0, 25.0, "Author2", 30));

        // Set the test data in the mock repository
        mockRepository.setStockBooks(testBooks);

        // Call the method to test
        String result = statisticsFuncController.getBooksSoldYear();

        // Assertions
        assertNotNull(result);
        assertTrue(result.contains("For Books Sold In A Year We Have")); // Check if the output contains the expected string
    }


    @Test
    public void testGetBooksBoughtDay() {
        // Create a mock repository
        MockStockBookRepository mockRepository = new MockStockBookRepository();

        // Create a BookController instance with the mock repository
        BookController mockBookController = new BookController(mockRepository);

        // Create a StatisticsFuncController instance with the mock repository
        StatisticsFuncController statisticsFuncController = new StatisticsFuncController(mockBookController);

        // Prepare test data
        ArrayList<BookModel> testBooks = new ArrayList<>();
        testBooks.add(new BookModel("ISBN1", "Book1", "Category1", "Supplier1", 20.0, 15.0, "Author1", 50));
        testBooks.add(new BookModel("ISBN2", "Book2", "Category2", "Supplier2", 30.0, 25.0, "Author2", 30));

        // Set the test data in the mock repository
        mockRepository.setStockBooks(testBooks);

        // Call the method to test
        String result = statisticsFuncController.getBooksBoughtDay();

        // Assertions
        assertNotNull(result);
        assertTrue(result.contains("For Books Bought Today We Have")); // Check if the output contains the expected string
    }



    @Test
    public void testGetBooksBoughtMonth() {
        // Create a mock repository
        MockStockBookRepository mockRepository = new MockStockBookRepository();

        // Create a BookController instance with the mock repository
        BookController mockBookController = new BookController(mockRepository);

        // Create a StatisticsFuncController instance with the mock repository
        StatisticsFuncController statisticsFuncController = new StatisticsFuncController(mockBookController);

        // Prepare test data
        ArrayList<BookModel> testBooks = new ArrayList<>();
        testBooks.add(new BookModel("ISBN1", "Book1", "Category1", "Supplier1", 20.0, 15.0, "Author1", 50));
        testBooks.add(new BookModel("ISBN2", "Book2", "Category2", "Supplier2", 30.0, 25.0, "Author2", 30));

        // Set the test data in the mock repository
        mockRepository.setStockBooks(testBooks);

        // Call the method to test
        String result = statisticsFuncController.getBooksBoughtMonth();

        // Assertions
        assertNotNull(result);
        assertTrue(result.contains("For Books Bought In A Month We Have")); // Check if the output contains the expected string
    }

    @Test
    public void testGetBooksBoughtYear() {
        // Create a mock repository
        MockStockBookRepository mockRepository = new MockStockBookRepository();

        // Create a BookController instance with the mock repository
        BookController mockBookController = new BookController(mockRepository);

        // Create a StatisticsFuncController instance with the mock repository
        StatisticsFuncController statisticsFuncController = new StatisticsFuncController(mockBookController);

        // Prepare test data
        ArrayList<BookModel> testBooks = new ArrayList<>();
        testBooks.add(new BookModel("ISBN1", "Book1", "Category1", "Supplier1", 20.0, 15.0, "Author1", 50));
        testBooks.add(new BookModel("ISBN2", "Book2", "Category2", "Supplier2", 30.0, 25.0, "Author2", 30));

        // Set the test data in the mock repository
        mockRepository.setStockBooks(testBooks);

        // Call the method to test
        String result = statisticsFuncController.getBooksBoughtYear();

        // Assertions
        assertNotNull(result);
        assertTrue(result.contains("For Books Bought In A Year We Have")); // Check if the output contains the expected string
    }


    @Test
    public void testGetIntBooksSoldDay() {
        // Create a mock repository
        MockStockBookRepository mockRepository = new MockStockBookRepository();

        // Create a BookController instance with the mock repository
        BookController mockBookController = new BookController(mockRepository);

        // Create a StatisticsFuncController instance with the mock repository
        StatisticsFuncController statisticsFuncController = new StatisticsFuncController(mockBookController);

        // Prepare test data
        ArrayList<BookModel> testBooks = new ArrayList<>();
        testBooks.add(new BookModel("ISBN1", "Book1", "Category1", "Supplier1", 20.0, 15.0, "Author1", 50));
        testBooks.add(new BookModel("ISBN2", "Book2", "Category2", "Supplier2", 30.0, 25.0, "Author2", 30));

        // Set the test data in the mock repository
        mockRepository.setStockBooks(testBooks);

        // Call the method to test
        int result = statisticsFuncController.getIntBooksSoldDay();

        // Assertions
        assertEquals(0, result); // Since no books are sold, the result should be 0
    }

    @Test
    public void testGetIntBooksSoldMonth() {
        // Create a mock repository
        MockStockBookRepository mockRepository = new MockStockBookRepository();

        // Create a BookController instance with the mock repository
        BookController mockBookController = new BookController(mockRepository);

        // Create a StatisticsFuncController instance with the mock repository
        StatisticsFuncController statisticsFuncController = new StatisticsFuncController(mockBookController);

        // Prepare test data
        ArrayList<BookModel> testBooks = new ArrayList<>();
        testBooks.add(new BookModel("ISBN1", "Book1", "Category1", "Supplier1", 20.0, 15.0, "Author1", 50));
        testBooks.add(new BookModel("ISBN2", "Book2", "Category2", "Supplier2", 30.0, 25.0, "Author2", 30));

        // Set the test data in the mock repository
        mockRepository.setStockBooks(testBooks);

        // Call the method to test
        int result = statisticsFuncController.getIntBooksSoldMonth();

        // Assertions
        assertEquals(0, result);
    }

    @Test
    public void testGetIntBooksSoldYear() {
        // Create a mock repository
        MockStockBookRepository mockRepository = new MockStockBookRepository();

        // Create a BookController instance with the mock repository
        BookController mockBookController = new BookController(mockRepository);

        // Create a StatisticsFuncController instance with the mock repository
        StatisticsFuncController statisticsFuncController = new StatisticsFuncController(mockBookController);

        // Prepare test data
        ArrayList<BookModel> testBooks = new ArrayList<>();
        testBooks.add(new BookModel("ISBN1", "Book1", "Category1", "Supplier1", 20.0, 15.0, "Author1", 50));
        testBooks.add(new BookModel("ISBN2", "Book2", "Category2", "Supplier2", 30.0, 25.0, "Author2", 30));

        // Set the test data in the mock repository
        mockRepository.setStockBooks(testBooks);

        // Call the method to test
        int result = statisticsFuncController.getIntBooksSoldYear();

        // Assertions
        assertEquals(0, result);
    }

    @Test
    public void testGetIncomeDay() {
        // Create a mock repository
        MockStockBookRepository mockRepository = new MockStockBookRepository();

        // Create a BookController instance with the mock repository
        BookController mockBookController = new BookController(mockRepository);

        // Create a StatisticsFuncController instance with the mock BookController
        StatisticsFuncController statisticsFuncController = new StatisticsFuncController(mockBookController);

        // Prepare test data
        ArrayList<BookModel> testBooks = new ArrayList<>();
        testBooks.add(new BookModel("ISBN1", "Book1", "Category1", "Supplier1", 20.0, 15.0, "Author1", 50));
        testBooks.add(new BookModel("ISBN2", "Book2", "Category2", "Supplier2", 30.0, 25.0, "Author2", 30));

        // Set the test data in the mock repository
        mockRepository.setStockBooks(testBooks);

        // Call the method to test
        double result = statisticsFuncController.getIncomeDay();

        // Calculate expected income
        double expectedIncome = 0;
        for (BookModel book : testBooks) {
            expectedIncome += book.getTotalBooksSoldDay() * book.getSellingPrice();
        }

        // Assertions
        assertEquals(expectedIncome, result, 0.01); // Adjust delta value according to precision needs
    }

    @Test
    public void testGetIncomeMonth() {
        // Create a mock repository
        MockStockBookRepository mockRepository = new MockStockBookRepository();

        // Create a BookController instance with the mock repository
        BookController mockBookController = new BookController(mockRepository);

        // Create a StatisticsFuncController instance with the mock BookController
        StatisticsFuncController statisticsFuncController = new StatisticsFuncController(mockBookController);

        // Prepare test data
        ArrayList<BookModel> testBooks = new ArrayList<>();
        testBooks.add(new BookModel("ISBN1", "Book1", "Category1", "Supplier1", 20.0, 15.0, "Author1", 50));
        testBooks.add(new BookModel("ISBN2", "Book2", "Category2", "Supplier2", 30.0, 25.0, "Author2", 30));

        // Set the test data in the mock repository
        mockRepository.setStockBooks(testBooks);

        // Call the method to test
        double result = statisticsFuncController.getIncomeMonth();

        // Calculate expected income
        double expectedIncome = 0;
        for (BookModel book : testBooks) {
            expectedIncome += book.getTotalBooksSoldMonth() * book.getSellingPrice();
        }

        // Assertions
        assertEquals(expectedIncome, result, 0.01);
    }


    @Test
    public void testGetIncomeYear() {
        // Create a mock repository
        MockStockBookRepository mockRepository = new MockStockBookRepository();

        // Create a BookController instance with the mock repository
        BookController mockBookController = new BookController(mockRepository);

        // Create a StatisticsFuncController instance with the mock BookController
        StatisticsFuncController statisticsFuncController = new StatisticsFuncController(mockBookController);

        // Prepare test data
        ArrayList<BookModel> testBooks = new ArrayList<>();
        testBooks.add(new BookModel("ISBN1", "Book1", "Category1", "Supplier1", 20.0, 15.0, "Author1", 50));
        testBooks.add(new BookModel("ISBN2", "Book2", "Category2", "Supplier2", 30.0, 25.0, "Author2", 30));

        // Set the test data in the mock repository
        mockRepository.setStockBooks(testBooks);

        // Call the method to test
        double result = statisticsFuncController.getIncomeYear();

        // Calculate expected income
        double expectedIncome = 0;
        for (BookModel book : testBooks) {
            expectedIncome += book.getTotalBooksSoldYear() * book.getSellingPrice();
        }

        // Assertions
        assertEquals(expectedIncome, result, 0.01); // Adjust delta value according to precision needs
    }


    @Test
    public void testGetTotalBoughtBooksDay() {
        // Create a mock repository
        MockStockBookRepository mockRepository = new MockStockBookRepository();

        // Create a BookController instance with the mock repository
        BookController mockBookController = new BookController(mockRepository);

        // Create a StatisticsFuncController instance with the mock BookController
        StatisticsFuncController statisticsFuncController = new StatisticsFuncController(mockBookController);

        // Prepare test data
        ArrayList<BookModel> testBooks = new ArrayList<>();
        testBooks.add(new BookModel("ISBN1", "Book1", "Category1", "Supplier1", 20.0, 15.0, "Author1", 50));
        testBooks.add(new BookModel("ISBN2", "Book2", "Category2", "Supplier2", 30.0, 25.0, "Author2", 30));

        // Set the test data in the mock repository
        mockRepository.setStockBooks(testBooks);

        // Call the method to test
        int result = statisticsFuncController.getTotalBoughtBooksDay();

        // Calculate expected total bought books
        int expectedTotal = 0;
        for (BookModel book : testBooks) {
            expectedTotal += book.getTotalBooksBoughtDay();
        }

        // Assertions
        assertEquals(expectedTotal, result);
    }

    @Test
    public void testGetTotalBoughtBooksMonth() {
        // Create a mock repository
        MockStockBookRepository mockRepository = new MockStockBookRepository();

        // Create a BookController instance with the mock repository
        BookController mockBookController = new BookController(mockRepository);

        // Create a StatisticsFuncController instance with the mock BookController
        StatisticsFuncController statisticsFuncController = new StatisticsFuncController(mockBookController);

        // Prepare test data
        ArrayList<BookModel> testBooks = new ArrayList<>();
        testBooks.add(new BookModel("ISBN1", "Book1", "Category1", "Supplier1", 20.0, 15.0, "Author1", 50));
        testBooks.add(new BookModel("ISBN2", "Book2", "Category2", "Supplier2", 30.0, 25.0, "Author2", 30));

        // Set the test data in the mock repository
        mockRepository.setStockBooks(testBooks);

        // Call the method to test
        int result = statisticsFuncController.getTotalBoughtBooksMonth();

        // Calculate expected total bought books
        int expectedTotal = 0;
        for (BookModel book : testBooks) {
            expectedTotal += book.getTotalBooksBoughtMonth();
        }

        // Assertions
        assertEquals(expectedTotal, result);
    }

    @Test
    public void testGetTotalBoughtBooksYear() {
        // Create a mock repository
        MockStockBookRepository mockRepository = new MockStockBookRepository();

        // Create a BookController instance with the mock repository
        BookController mockBookController = new BookController(mockRepository);

        // Create a StatisticsFuncController instance with the mock BookController
        StatisticsFuncController statisticsFuncController = new StatisticsFuncController(mockBookController);

        // Prepare test data
        ArrayList<BookModel> testBooks = new ArrayList<>();
        testBooks.add(new BookModel("ISBN1", "Book1", "Category1", "Supplier1", 20.0, 15.0, "Author1", 50));
        testBooks.add(new BookModel("ISBN2", "Book2", "Category2", "Supplier2", 30.0, 25.0, "Author2", 30));

        // Set the test data in the mock repository
        mockRepository.setStockBooks(testBooks);

        // Call the method to test
        int result = statisticsFuncController.getTotalBoughtBooksYear();

        // Calculate expected total bought books
        int expectedTotal = 0;
        for (BookModel book : testBooks) {
            expectedTotal += book.getTotalBooksBoughtYear();
        }

        // Assertions
        assertEquals(expectedTotal, result);
    }


    @Test
    public void testGetCostDay() {
        // Create a mock repository
        MockStockBookRepository mockRepository = new MockStockBookRepository();

        // Create a BookController instance with the mock repository
        BookController mockBookController = new BookController(mockRepository);

        // Create a StatisticsFuncController instance with the mock BookController
        StatisticsFuncController statisticsFuncController = new StatisticsFuncController(mockBookController);

        // Prepare test data
        ArrayList<BookModel> testBooks = new ArrayList<>();
        testBooks.add(new BookModel("ISBN1", "Book1", "Category1", "Supplier1", 20.0, 15.0, "Author1", 50));
        testBooks.add(new BookModel("ISBN2", "Book2", "Category2", "Supplier2", 30.0, 25.0, "Author2", 30));

        // Set the test data in the mock repository
        mockRepository.setStockBooks(testBooks);

        // Call the method to test
        double result = statisticsFuncController.getCostDay();

        // Calculate expected total cost
        double expectedCost = 0;
        for (BookModel book : testBooks) {
            expectedCost += book.getTotalBooksBoughtDay() * book.getOriginalPrice();
        }

        // Assertions
        assertEquals(expectedCost, result, 0.01); // Adjust the delta based on your expected precision
    }


    @Test
    public void testGetCostMonth() {
        // Create a mock repository
        MockStockBookRepository mockRepository = new MockStockBookRepository();

        // Create a BookController instance with the mock repository
        BookController mockBookController = new BookController(mockRepository);

        // Create a StatisticsFuncController instance with the mock BookController
        StatisticsFuncController statisticsFuncController = new StatisticsFuncController(mockBookController);

        // Prepare test data
        ArrayList<BookModel> testBooks = new ArrayList<>();
        testBooks.add(new BookModel("ISBN1", "Book1", "Category1", "Supplier1", 20.0, 15.0, "Author1", 50));
        testBooks.add(new BookModel("ISBN2", "Book2", "Category2", "Supplier2", 30.0, 25.0, "Author2", 30));

        // Set the test data in the mock repository
        mockRepository.setStockBooks(testBooks);

        // Call the method to test
        double result = statisticsFuncController.getCostMonth();

        // Calculate expected total cost
        double expectedCost = 0;
        for (BookModel book : testBooks) {
            expectedCost += book.getTotalBooksBoughtMonth() * book.getOriginalPrice();
        }

        // Assertions
        assertEquals(expectedCost, result, 0.01); // Adjust the delta based on your expected precision
    }

    @Test
    public void testGetCostYear() {
        // Create a mock repository
        MockStockBookRepository mockRepository = new MockStockBookRepository();

        // Create a BookController instance with the mock repository
        BookController mockBookController = new BookController(mockRepository);

        // Create a StatisticsFuncController instance with the mock BookController
        StatisticsFuncController statisticsFuncController = new StatisticsFuncController(mockBookController);

        // Prepare test data
        ArrayList<BookModel> testBooks = new ArrayList<>();
        testBooks.add(new BookModel("ISBN1", "Book1", "Category1", "Supplier1", 20.0, 15.0, "Author1", 50));
        testBooks.add(new BookModel("ISBN2", "Book2", "Category2", "Supplier2", 30.0, 25.0, "Author2", 30));

        // Set the test data in the mock repository
        mockRepository.setStockBooks(testBooks);

        // Call the method to test
        double result = statisticsFuncController.getCostYear();

        // Calculate expected total cost
        double expectedCost = 0;
        for (BookModel book : testBooks) {
            expectedCost += book.getTotalBooksBoughtYear() * book.getOriginalPrice();
        }

        // Assertions
        assertEquals(expectedCost, result, 0.01); // Adjust the delta based on your expected precision
    }


}
