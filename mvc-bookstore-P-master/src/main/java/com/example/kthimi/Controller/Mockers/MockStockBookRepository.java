package com.example.kthimi.Controller.Mockers;

import com.example.kthimi.Model.BookModel;

import java.util.ArrayList;

public class MockStockBookRepository implements StockBookRepository {
    private ArrayList<BookModel> stockBooks;

    public void setStockBooks(ArrayList<BookModel> stockBooks) {
        this.stockBooks = stockBooks;
    }

    @Override
    public ArrayList<BookModel> getStockBooks() {
        return stockBooks;
    }

}
