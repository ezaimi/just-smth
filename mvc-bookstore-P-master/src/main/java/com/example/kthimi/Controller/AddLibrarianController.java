package com.example.kthimi.Controller;

import com.example.kthimi.Controller.Mockers.FileBasedStockBookRepository;
import com.example.kthimi.Controller.Mockers.StockBookRepository;
import com.example.kthimi.Model.BookModel;
import com.example.kthimi.Model.LibrarianModel;
import com.example.kthimi.Model.SharedDataManager;
import com.example.kthimi.View.Manager.AddStockManagerView;
import com.example.kthimi.View.Librarian.LibrarianView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class AddLibrarianController {
    StockBookRepository stockBookRepository = new FileBasedStockBookRepository();
    BookController bookController = new BookController(stockBookRepository);
    LibrarianFuncController librarianFuncController = new LibrarianFuncController();
    Date date;
    ArrayList<Integer> bookQuantities = new ArrayList<>();
    ArrayList<String> booksSoldTitles = new ArrayList<>();
    TextField bookISBN = new TextField();
    TextField quantity = new TextField();
    TextField warningsLibrarian = new TextField();

    AddStockManagerView addStockManagerView;
    ArrayList<BookModel> books;;
    String category;
    LibrarianView librarianView;
    SharedDataManager sharedDataManager;
    BillLibrarianController billLibrarianController;
    public AddLibrarianController(){

    }
    public AddLibrarianController(LibrarianView librarianView) {
        this.librarianView = librarianView;
       // this.category = category;

        this.addStockManagerView = new AddStockManagerView();
        this.books = SharedDataManager.getInstance().getBooks();
        this.billLibrarianController = new BillLibrarianController(librarianView,this,books);

        initializeAddBttLibrarian();
    }

    private void initializeAddBttLibrarian() {
        librarianView.getBttAdd().setOnAction(event -> handleAdd());
    }


        public void handleAdd() {



                if (librarianView.comboBoxLibrarian.getValue() == null && librarianView.quantity.getCharacters().toString().isEmpty()) {
                    librarianView.warningsLibrarian.setText("Failed, Empty fields");
                    return;
                }
                else if (librarianView.comboBoxLibrarian.getValue() == null) {
                    librarianView.warningsLibrarian.setText("Failed to add,Empty ISBN");
                    return;
                }
                else if (librarianView.quantity.getCharacters().toString().isEmpty()) {
                    librarianView.warningsLibrarian.setText("Failed, Empty Quantity");
                    return;
                }

                else if(!librarianView.quantity.getCharacters().toString().matches("\\d{1,}") || Integer.parseInt(librarianView.quantity.getCharacters().toString()) == 0) {
                    librarianView.warningsLibrarian.setText("Failed, Invalid Quantity");
                    return;
                }


                if (!librarianFuncController.EnoughStock(librarianView.comboBoxLibrarian.getValue(), Integer.parseInt(librarianView.quantity.getCharacters().toString())) ) {
                    librarianView.warningsLibrarian.setText("Failed,not enough stock");
                    return;
                }

                String isbn = librarianView.comboBoxLibrarian.getValue().toString();
                if (isbn.length() >= 13) {
                    isbn = isbn.substring(0, 13);
                }
                int quan = Integer.parseInt(librarianView.quantity.getCharacters().toString());

                //String Title = librarianView.comboBoxLibrarian.getValue().toString().substring(16);
            String Title = librarianView.comboBoxLibrarian.getValue().toString();
            if (Title.length() > 16) {
                Title = Title.substring(16);
            }

            date = new Date();

                ArrayList<BookModel> stockbooks = bookController.getStockBooks();
                for (int i=0;i<stockbooks.size();i++) {
                    if (stockbooks.get(i).getISBN().equals(isbn)) {

                        stockbooks.get(i).RemoveStock(quan);
                    }
                }
                try {
                    bookController.updateBooks(stockbooks);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

            BookModel book = new BookModel(isbn);
            System.out.println("books array me gjera" + books);
                books.add(book);
            System.out.println(books);
                bookQuantities.add(quan);
                booksSoldTitles.add(Title);
//                bookISBN.clear();
//                quantity.clear();
                librarianView.warningsLibrarian.setText("Added");

            }

    public ArrayList<BookModel> getBooksPlus() {
        System.out.println("TEK GETI Plus" + books);
        return books;
    }

    public ArrayList<Integer> getQuantityPlus() {
        System.out.println("TEK GETI Plus" + books);
        return bookQuantities;
    }


}








