package com.example.kthimi.Controller;

import com.example.kthimi.Model.LibrarianModel;

import java.util.ArrayList;

//fut metodat qe ka te klasa Manager qe lidhen me librarians
public class FixLibrariansController {

    static ArrayList<LibrarianModel> librarians = new ArrayList<>();


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


    public static boolean LibrarianChecker(LibrarianModel lib) {
        for (int i=0;i<librarians.size();i++) {
            if (librarians.get(i).getUsername().equals(lib.getUsername()) && librarians.get(i).getPassword().equals(lib.getPassword()))
                return true;
        }
        return false;


    }

    public static LibrarianModel getBackLibrarian(LibrarianModel lib) {

        for (int i=0;i<librarians.size();i++){
            if (librarians.get(i).getUsername().equals(lib.getUsername()))
                return librarians.get(i);
        }
        return null;
    }

    public static void deleteLibrarian(LibrarianModel lib) {
        for (int i=0;i<librarians.size();i++) {
            if (librarians.get(i).getUsername().equals(lib.getUsername())) {
                librarians.remove(i);
                return;
            }
        }
    }

}
