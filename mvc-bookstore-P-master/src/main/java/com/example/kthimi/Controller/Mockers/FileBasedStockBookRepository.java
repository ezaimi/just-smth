package com.example.kthimi.Controller.Mockers;

import com.example.kthimi.Model.BookModel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;


public class FileBasedStockBookRepository implements StockBookRepository{

    public static String STOCK_FILE_PATH = "Books.bin";


    public ArrayList<BookModel> getStockBooks(){
        System.out.println("lol22");
        ArrayList<BookModel> stockBooks = new ArrayList<>();
        System.out.println("pm");
        try (ObjectInputStream objis = new ObjectInputStream(new FileInputStream(STOCK_FILE_PATH))) {
            System.out.println("read pra");
            Object obj = objis.readObject();
            if (obj instanceof ArrayList) {
                System.out.println("readddddddd");
                stockBooks = (ArrayList<BookModel>) obj;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("TekStockmeth" + stockBooks);

        return stockBooks;


    }

}
