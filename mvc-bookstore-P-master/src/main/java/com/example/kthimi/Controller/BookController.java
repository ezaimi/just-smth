package com.example.kthimi.Controller;

import com.example.kthimi.Controller.Mockers.FileBasedStockBookRepository;
import com.example.kthimi.Controller.Mockers.StockBookRepository;
import com.example.kthimi.Model.BookModel;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

public class BookController {

    BookModel bookmodel;

    //public static int billNumber=0;
    private String stockFilePath;

    public BookController(String stockFilePath) {
        this.stockFilePath = stockFilePath;
    }


    public BookController(){
        this.bookmodel = new BookModel();
    }

    public static String STOCK_FILE_PATH = "Books.bin";



    private StockBookRepository stockBookRepository = new FileBasedStockBookRepository();

    public BookController(StockBookRepository stockBookRepository) {
        this.stockBookRepository = stockBookRepository;
    }

    public ArrayList<BookModel> getStockBooks() {
        return stockBookRepository.getStockBooks();
    }



    public void updateBooks(ArrayList<BookModel> arr) throws IOException {

        // Read existing books from the file
        ArrayList<BookModel> existingBooks = getStockBooks();

        // Check for existing books and update if needed
        for (BookModel newBook : arr) {
            if (!bookExists(existingBooks, newBook)) {
                existingBooks.add(newBook);
            }
        }

        try (ObjectOutputStream objout = new ObjectOutputStream(new FileOutputStream(STOCK_FILE_PATH))) {
            objout.writeObject(existingBooks);
        }

    }
//    public ArrayList<BookModel> getStockBooks(){
//        System.out.println("lol22");
//        ArrayList<BookModel> stockBooks = new ArrayList<>();
//        System.out.println("pm");
//        try (ObjectInputStream objis = new ObjectInputStream(new FileInputStream(STOCK_FILE_PATH))) {
//            System.out.println("read pra");
//            Object obj = objis.readObject();
//            if (obj instanceof ArrayList) {
//                System.out.println("readddddddd");
//                stockBooks = (ArrayList<BookModel>) obj;
//            }
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        System.out.println("TekStockmeth" + stockBooks);
//        System.out.println("SDUHET ME U PRINTUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU");
//
//        return stockBooks;
//
//
//    }

    //it saves the books to a file when the user adds a book
    public void addBookToStock(BookModel book) {
        ArrayList<BookModel> stockBooks = getStockBooks();
        stockBooks.add(book);
        try (ObjectOutputStream objout = new ObjectOutputStream(new FileOutputStream(STOCK_FILE_PATH))) {
            objout.writeObject(stockBooks);
        } catch (IOException e) {
            System.out.println("error");
            e.printStackTrace();
        }
    }

    private boolean bookExists(ArrayList<BookModel> existingBooks, BookModel newBook) {
        for (BookModel book : existingBooks) {
            if (book.getISBN().equals(newBook.getISBN())) {
                // Book with the same ISBN already exists
                // You may want to check other attributes for equality if needed
                return true;
            }
        }
        return false;
    }

//    public void saveBooksToFile(ArrayList<BookModel> books) {
//        try (ObjectOutputStream objout = new ObjectOutputStream(new FileOutputStream(STOCK_FILE_PATH))) {
//            objout.writeObject(books);
//        } catch (IOException e) {
//            e.printStackTrace();
//            // Handle the exception accordingly
//        }
//    }

    public ArrayList<String> getISBNName(){

        ArrayList<BookModel> array = getStockBooks();
        System.out.println("TekISBN" + array);
        ArrayList<String> ans = new ArrayList<>();

        for (int i=0;i<array.size();i++) {
            ans.add( array.get(i).getISBN()+" - "+array.get(i).getTitle() );
        }
        //System.out.println(ans);
        return ans;
    }



//    public void removeStock(int quantity) {
//        if (bookmodel.getStock() >= quantity) {
//            bookmodel.setStock(bookmodel.getStock() - quantity);
//        } else {
//            System.out.println("Insufficient stock!");
//        }
//    }



//    public BookModel findBookInStock(ArrayList<BookModel> stockBooks, String ISBN) {
//        for (BookModel stockBook : stockBooks) {
//            if (ISBN.equals(stockBook.getISBN())) {
//                return stockBook;
//            }
//        }
//        return null;
//    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return true;
    }


    //////////////Keto 5 te fundit mire do ishte te futen ne nje controller tjt

    public <T> ArrayList<T> removeDuplicates(ArrayList<T> list)
    {

        ArrayList<T> newList = new ArrayList<T>();

        for (T element : list) {

            if (!newList.contains(element)) {

                newList.add(element);
            }
        }

        return newList;
    }

    public ArrayList<String> getCategories() {

        ArrayList<String> ans = new ArrayList<>();

        ArrayList<BookModel> books = getStockBooks();

        for (int i=0;i<books.size();i++) {
            ans.add( books.get(i).getCategory() );
        }


        return removeDuplicates(ans);

    }

    public ArrayList<BookModel> getBookFromCategory(String category){

        ArrayList<BookModel> ans = new ArrayList<>();
        ArrayList<BookModel> stockbooks = getStockBooks();

        for (int i=0; i<stockbooks.size(); i++) {
            if (stockbooks.get(i).getCategory().equals(category)) {

                ans.add(stockbooks.get(i));
            }

        }

        return ans;

    }


    public boolean partOfCateogriesChecker(ArrayList<String> categoriesStock,String category) {


        for (int i=0;i<categoriesStock.size();i++) {
            if (categoriesStock.get(i).equals(category))
                return true;
        }
        return false;
    }

    public boolean isPartOfBooks(String ISBN) {

        ArrayList<BookModel> array = getStockBooks();

        for (int i=0;i<array.size();i++) {
            if (array.get(i).getISBN().equals(ISBN))
                return true;
        }
        return false;
    }


    public void showStock() {

        try {
            FileInputStream fis = new FileInputStream(STOCK_FILE_PATH);
            ObjectInputStream objis = new ObjectInputStream(fis);

            while (true) {
                try {
                    Object obj = objis.readObject();
                    if (obj instanceof BookModel) {
                        System.out.println((BookModel) obj);
                    } else if (obj instanceof ArrayList) {
                        ArrayList<BookModel> books = (ArrayList<BookModel>) obj;
                        for (BookModel book : books) {
                            System.out.println(book);
                        }
                    }
                } catch (EOFException e) {
                    // End of file reached, exit the loop
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



}
