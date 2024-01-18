package com.example.kthimi.Controller;

import com.example.kthimi.Model.BookModel;
import com.example.kthimi.Model.LibrarianModel;
import com.example.kthimi.View.Manager.AddStockManagerView;
import com.example.kthimi.View.Librarian.LibrarianView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;

public class BillLibrarianController {


    LibrarianFuncController librarianFuncController;

    LibrarianView librarianView;
    AddLibrarianController addLibrarianController;
    AddStockManagerView addStockManagerView;

    //////////////////////////////////////////
    ArrayList<BookModel> books;
    ArrayList<Integer> bookQuantities;
    //////////////////////////////////////////
    ArrayList<String> booksSoldTitles = new ArrayList<>();
    TextField bookISBN = new TextField();
    TextField quantity = new TextField();
    TextField warningsLibrarian = new TextField();
    public BillLibrarianController(){

    }
    public BillLibrarianController(LibrarianView librarianView,AddLibrarianController addLibrarianController,ArrayList<BookModel> books) {
        this.librarianView = librarianView;
        this.addStockManagerView = new AddStockManagerView();
        this.addLibrarianController = addLibrarianController;
        initializeBillBttLibrarian();

        //this.books = addLibrarianController.getBooksPlus();

        this.librarianFuncController = new LibrarianFuncController(this);
    }

    private void initializeBillBttLibrarian() {
        System.out.println("its britney bitch");
//        librarianView.getBttBill().setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                handleBill();
//            }
//        });
        librarianView.getBttBill().setOnAction(event->handleBill());
//
//        librarianView.getBttAdd().setOnAction(new AddButtonHandler());
//        // Set actions for other buttons if needed
//        //librarianView.getBttAdd().setOnAction(event -> handleAddButton());
//        books = addStockManagerView.getBooks();

    }

    public void handleBill(){
        books = addLibrarianController.getBooksPlus();
        bookQuantities = addLibrarianController.getQuantityPlus();

        System.out.println("Books array: " + books);
        System.out.println("POSI PRAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        if (books.isEmpty() || bookQuantities.isEmpty()) {
            warningsLibrarian.setText("Failed, No Books to add");
            return;
        }
        System.out.println("seshte bosh");
        try {
            System.out.println("CHRISTMAS");
            librarianFuncController.checkOutBooks(books, bookQuantities);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        librarianView.warningsLibrarian.setText("");
        librarianView.warningsLibrarian.setText("Bill File Created!");
        bookISBN.clear();
        booksSoldTitles.clear();
        quantity.clear();
        books.clear();
        bookQuantities.clear();
        warningsLibrarian.setId("warningsLibrarian");
    }


}
