package com.example.kthimi.Model;

import com.example.kthimi.Controller.BookController;
import com.example.kthimi.Controller.Mockers.FileBasedStockBookRepository;
import com.example.kthimi.Controller.Mockers.StockBookRepository;

import java.util.ArrayList;

public class ManagerModel extends LibrarianModel{
    StockBookRepository stockBookRepository = new FileBasedStockBookRepository();
    BookController bookController = new BookController(stockBookRepository);
    public static ArrayList<LibrarianModel> librarians = new ArrayList<>();

    public ManagerModel(){

    }

    public ManagerModel(String username, String password) {
        super(username, password);

    }

    public ManagerModel(String username, String password, String name, double salary, String phone, String email){
        super(username,password,name,salary,phone,email);
    }

    public static void InstantiateLibrarians() {

        LibrarianModel lib = new LibrarianModel("Alfie123","SSU6umwt","Alfie",500,"(912) 921-2728","aflie@librarian.com") ;
        librarians.add(lib);

        lib = new LibrarianModel("@Leo","TyFzN8we","Leo",500,"(912) 152-7493","leo@librarian.com");
        librarians.add(lib);

        lib = new LibrarianModel("Julie?!","NDt8f6xL","Julie",500,"(912) 742-7832","julie@librarian.com");
        librarians.add(lib);

        lib = new LibrarianModel("MargiE","vGtM6beC","Margie",500,"(912) 253-6939","margie@librarian.com");
        librarians.add(lib);

        lib = new LibrarianModel("1","1","TestLibrarian",500,"(912) 632-6353","TestEmail@librarian.com");
        librarians.add(lib);

    }

    public static void AddLibrarian(LibrarianModel lib) {
        librarians.add(lib);
    }

    public static ArrayList<LibrarianModel> getLibrarians() {
        return librarians;
    }


    //KETE METODEN FUTE TE NJE KONTROLLER I VECANT BASHK ME GETALLCATEGORIES
    //QE ESHTE TEK MANAGER.JAVA SEPSE KANE LIDHJE ME VEPRIMET E LIBRAVE TEK MANAGERVIEW
   ////\\\\\\ u be


    public static ArrayList<String> getAllCategories(){

        ArrayList<String> ans = new ArrayList<>();
        ans.add("Modernist");
        ans.add("Fiction");
        ans.add("Novel");
        ans.add("Magic Realism");
        ans.add("Tragedy");
        ans.add("Adventure Fiction");
        ans.add("Historical Novel");
        ans.add("Epic");
        ans.add("War");
        ans.add("Autobiography and memoir");
        ans.add("Biography");
        ans.add("Non-fiction novel");
        ans.add("Self-help");
        ans.add("Short stories");
        ans.add("Horror");
        ans.add("Mystery");
        ans.add("Romance");
        ans.add("Thriller");
        return ans;
    }



}
