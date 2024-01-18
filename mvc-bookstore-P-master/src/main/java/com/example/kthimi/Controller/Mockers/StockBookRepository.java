package com.example.kthimi.Controller.Mockers;

import com.example.kthimi.Model.BookModel;

import java.util.ArrayList;

public interface StockBookRepository {
    ArrayList<BookModel> getStockBooks();
}