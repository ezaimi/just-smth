package com.example.kthimi.Model;

import com.example.kthimi.Controller.BookController;

import java.util.ArrayList;
import java.util.Date;

public class LibrarianModel {
    private int booksSold = 0;
    //public ArrayList<Date> datesSold;
    //private ArrayList<Double> moneyMadeDates;
    private String username;
    private String password;
    private String name;
    private double salary;
    private String phone;
    private String email;

    public static String STOCK_FILE_PATH = "Books.bin";

    public LibrarianModel() {

    }

    public LibrarianModel(String username, String password, String name, double salary, String phone, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.salary = salary;
        this.phone = phone;
        this.email = email;
//        datesSold = new ArrayList<>();
//        moneyMadeDates = new ArrayList<>();
    }

    public LibrarianModel(String username, String password) {
        this.username = username;
        this.password = password;
//        datesSold = new ArrayList<>();
//        moneyMadeDates = new ArrayList<>();
    }

       public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name;}

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public void setPassword(String password) {
        this.password = password;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Librarian [username=" + username + ", password=" + password + ", name=" + name + ", salary=" + salary
                + ", phone=" + phone + ", email=" + email + "]";
    }


    ///put it in a controller




    //put it in a billmodel//////////////////////////
//    public int getNumberOfBills() {
//        return numberOfBills;
//    }

    public int getBooksSold() {
        return booksSold;
    }

    public void setBooksSold(int booksSold){

        this.booksSold = booksSold;
    }





}