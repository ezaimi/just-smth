package com.example.kthimi.Model;

import java.util.ArrayList;

public class SharedDataManager {
    private static SharedDataManager instance;
    private ArrayList<BookModel> books;

    public SharedDataManager() {
        books = new ArrayList<>();
    }

    public static SharedDataManager getInstance() {
        if (instance == null) {
            instance = new SharedDataManager();
        }
        return instance;
    }

    public void setBooks(ArrayList<BookModel> books) {
        this.books = books;
    }

    public ArrayList<BookModel> getBooks() {
        return books;
    }
}
