package mocking;

import com.example.kthimi.Controller.BookController;
import com.example.kthimi.Controller.Mockers.MockStockBookRepository;
import com.example.kthimi.Model.BookModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookControllerTest {


    @Test
    public void testAddBookToStock() throws IOException, ClassNotFoundException {
        // Create a temporary file for testing
        File tempFile = File.createTempFile("testBooks", ".bin");

        // Create a mock repository
        MockStockBookRepository mockRepository = new MockStockBookRepository();
        ArrayList<BookModel> testData = new ArrayList<>();
        mockRepository.setStockBooks(testData);

        // Create a BookController instance with the mock repository
        BookController bookController = new BookController(mockRepository);
        BookController.STOCK_FILE_PATH = tempFile.getAbsolutePath(); // Set the temporary file path

        // Prepare a book to add
        BookModel bookToAdd = new BookModel("newISBN", "saLot", "New Category", "New Supplier", 15.0, 10.0, "New Author", 20);

        // Call the method to add the book to the mock repository
        bookController.addBookToStock(bookToAdd);

        // Get the updated books from the temporary file
        ArrayList<BookModel> updatedBooks;
        try (ObjectInputStream objis = new ObjectInputStream(new FileInputStream(tempFile))) {
            Object obj = objis.readObject();
            if (obj instanceof ArrayList) {
                updatedBooks = (ArrayList<BookModel>) obj;
            } else {
                updatedBooks = new ArrayList<>();
            }
        }

        // Assertions to verify the behavior
        assertEquals(1, updatedBooks.size()); // Only one book should be added
        assertEquals(bookToAdd, updatedBooks.get(0)); // The added book should match the bookToAdd

        // Clean up the temporary file
        tempFile.delete();
    }


//
//    @Test
//    public void testAddBookToStock_ExceptionHandling() {
//        // Create a BookController instance
//        BookController bookController = new BookController();
//
//        // Set an invalid file path to force an IOException
//        BookController.STOCK_FILE_PATH = "InvalidPath"; // Replace with an invalid path
//
//        // Create a book to add
//        BookModel bookToAdd = new BookModel("ISBN123", "Test Book", "Test Category", "Test Supplier", 20.0, 15.0, "Test Author", 50);
//
//        // Test exception handling using assertThrows for IOException
//        assertThrows(IOException.class, () -> bookController.addBookToStock(bookToAdd),
//                "Exception should be thrown for invalid file path");
//    }


    @Test
    public void testUpdateBooks() throws IOException, ClassNotFoundException {
        // Create a temporary file for testing
        File tempFile = File.createTempFile("testBooks", ".bin");

        // Create a mock repository
        MockStockBookRepository mockRepository = new MockStockBookRepository();
        ArrayList<BookModel> testData = new ArrayList<>();
        mockRepository.setStockBooks(testData);

        // Create a BookController instance with the mock repository
        BookController bookController = new BookController(mockRepository);
        BookController.STOCK_FILE_PATH = tempFile.getAbsolutePath(); // Set the temporary file path

        // Prepare test data (existing books in the file)
        ArrayList<BookModel> existingBooks = new ArrayList<>();
        existingBooks.add(new BookModel("existingISBN", "Title", "Category", "Supplier", 20.0, 15.0, "Author", 50));

        // Write the existing books to the temporary file
        try (ObjectOutputStream objout = new ObjectOutputStream(new FileOutputStream(tempFile))) {
            objout.writeObject(existingBooks);
        }

        // Prepare new books to update
        ArrayList<BookModel> newBooks = new ArrayList<>();
        newBooks.add(new BookModel("existingISBN", "Title", "Category", "Supplier", 20.0, 15.0, "Author", 50));
        newBooks.add(new BookModel("newISBN", "New Title", "New Category", "New Supplier", 15.0, 10.0, "New Author", 20));

        // Invoke the updateBooks method
        bookController.updateBooks(newBooks);

        // Get the updated books from the temporary file
        ArrayList<BookModel> updatedBooks;
        try (ObjectInputStream objis = new ObjectInputStream(new FileInputStream(tempFile))) {
            Object obj = objis.readObject();
            if (obj instanceof ArrayList) {
                updatedBooks = (ArrayList<BookModel>) obj;
            } else {
                updatedBooks = new ArrayList<>();
            }
        }

        // Assertions to verify the behavior
        assertEquals(2, updatedBooks.size()); // Two books should be updated
        // Add more assertions based on your requirements
        //...

        // Clean up the temporary file
        tempFile.delete();
    }

    @Test
    public void testUpdateBooks_ExistingBookNotAdded() throws IOException, ClassNotFoundException {
        // Create a temporary file for testing
        File tempFile = File.createTempFile("testBooks", ".bin");

        // Create a mock repository
        MockStockBookRepository mockRepository = new MockStockBookRepository();
        ArrayList<BookModel> testData = new ArrayList<>();
        testData.add(new BookModel("existingISBN", "Existing Book", "Existing Category", "Existing Supplier", 20.0, 15.0, "Existing Author", 50));
        mockRepository.setStockBooks(testData);

        // Create a BookController instance with the mock repository and temporary file
        BookController bookController = new BookController(mockRepository);
        BookController.STOCK_FILE_PATH = tempFile.getAbsolutePath(); // Set the temporary file path

        // Prepare a book to update
        BookModel bookToUpdate = new BookModel("existingISBN", "Existing Book", "Existing Category", "Existing Supplier", 20.0, 15.0, "Existing Author", 50);

        // Call the method to update the books
        bookController.updateBooks(new ArrayList<>(List.of(bookToUpdate))); // Use an existing book

        // Get the updated books from the temporary file
        ArrayList<BookModel> updatedBooks;
        try (ObjectInputStream objis = new ObjectInputStream(new FileInputStream(tempFile))) {
            Object obj = objis.readObject();
            if (obj instanceof ArrayList) {
                updatedBooks = (ArrayList<BookModel>) obj;
            } else {
                updatedBooks = new ArrayList<>();
            }
        }

        // Assertions to verify that existing book is not added again
        assertEquals(1, updatedBooks.size()); // Only one book should exist in the file
        assertEquals(testData.get(0), updatedBooks.get(0)); // The existing book should not be added again

        // Clean up the temporary file
        tempFile.delete();
    }

    @Test
    public void testGetISBNName() {
        // Create a mock repository
        MockStockBookRepository mockRepository = new MockStockBookRepository();
        ArrayList<BookModel> testData = new ArrayList<>();
        testData.add(new BookModel("1", "mos1", "New Category", "New Supplier", 15.0, 10.0, "New Author", 20));
        testData.add(new BookModel("2", "mos2", "New Category", "New Supplier", 15.0, 10.0, "New Author", 20));
        mockRepository.setStockBooks(testData);

        // Create a BookController instance with the mock repository
        BookController bookController = new BookController(mockRepository);

        // Call the method to test
        ArrayList<String> result = bookController.getISBNName();

        // Verify the output
        ArrayList<String> expected = new ArrayList<>();
        expected.add("1 - mos1");
        expected.add("2 - mos2");

        assertEquals(expected, result);
    }


    @Test
    public void testGetCategories() {
        // Create a mock repository
        MockStockBookRepository mockRepository = new MockStockBookRepository();
        ArrayList<BookModel> testData = new ArrayList<>();
        // Create some books for the test
        testData.add(new BookModel("ISBN1", "Book 1", "Category A", "Supplier 1", 20.0, 15.0, "Author 1", 50));
        testData.add(new BookModel("ISBN2", "Book 2", "Category B", "Supplier 2", 20.0, 15.0, "Author 2", 50));
        testData.add(new BookModel("ISBN3", "Book 3", "Category A", "Supplier 3", 20.0, 15.0, "Author 3", 50));
        mockRepository.setStockBooks(testData);

        // Create a BookController instance with the mock repository
        BookController bookController = new BookController(mockRepository);

        // Invoke the getCategories method
        ArrayList<String> categories = bookController.getCategories();

        // Assertions to verify the behavior
        assertEquals(2, categories.size()); // Expecting two unique categories
        // Add more assertions based on your requirements
        //...
    }


    @Test
    public void testGetBookFromCategory() {
        // Create a mock repository
        MockStockBookRepository mockRepository = new MockStockBookRepository();
        ArrayList<BookModel> testData = new ArrayList<>();
        // Create some books for the test
        testData.add(new BookModel("ISBN1", "Book 1", "Category A", "Supplier 1", 20.0, 15.0, "Author 1", 50));
        testData.add(new BookModel("ISBN2", "Book 2", "Category B", "Supplier 2", 20.0, 15.0, "Author 2", 50));
        testData.add(new BookModel("ISBN3", "Book 3", "Category A", "Supplier 3", 20.0, 15.0, "Author 3", 50));
        mockRepository.setStockBooks(testData);

        // Create a BookController instance with the mock repository
        BookController bookController = new BookController(mockRepository);

        // Invoke the getBookFromCategory method for a specific category
        ArrayList<BookModel> booksInCategoryA = bookController.getBookFromCategory("Category A");

        // Assertions to verify the behavior
        assertEquals(2, booksInCategoryA.size()); // Expecting two books in Category A
        // Add more assertions based on your requirements
        //...
    }



    @Test
    public void testIsPartOfBooks() {
        // Create a mock repository
        MockStockBookRepository mockRepository = new MockStockBookRepository();
        // Prepare test data with a book
        ArrayList<BookModel> testData = new ArrayList<>();
        testData.add( new BookModel("ISBN123", "Test Book", "Test Category", "Test Supplier", 20.0, 15.0, "Test Author", 50));
        mockRepository.setStockBooks(testData);

        // Create a BookController instance with the mock repository
        BookController bookController = new BookController(mockRepository);

        // Test the isPartOfBooks method with an existing ISBN
        boolean existingISBNResult = bookController.isPartOfBooks("ISBN123");
        // Test the isPartOfBooks method with a non-existing ISBN
        boolean nonExistingISBNResult = bookController.isPartOfBooks("NonExistingISBN");

        // Assertions to verify the behavior
        assertTrue(existingISBNResult); // ISBN exists in the stock
        assertFalse(nonExistingISBNResult); // ISBN does not exist in the stock
    }



}