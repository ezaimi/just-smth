package com.example.kthimi.Model;

import java.util.ArrayList;

public class AdministratorModel extends ManagerModel{

    private static ArrayList<ManagerModel> managers = new ArrayList<>();
    private static ArrayList<AdministratorModel> admins = new ArrayList<>();

    private static String[] usernames = {
            "J0sh",
            "1"
    };

    private static String[] passwords = {
            "&zsX6QVZ",
            "3"
    };

    private String username;
    private String password;

    public AdministratorModel(){

    }

    public AdministratorModel(String username,String password){
        super(username,password);
        this.username=username;
        this.password=password;
    }

    public AdministratorModel(String username,String password,String name,double salary,String phone,String email){
        super(username,password,name,salary,phone,email);
    }

    public static ArrayList<AdministratorModel> getAdmins(){
        return admins;
    }



    public static void InstantiateManagers() {

        ManagerModel mag = new ManagerModel("Calv1n","PQ532Ayba","Calvin",900,"(912) 561-2628","calvl@manager.com") ;
        managers.add(mag);

        mag = new ManagerModel("Lui54","y@.3FYrn","Lui",900,"(912) 218-2594","lu@manager.com") ;
        managers.add(mag);

        mag = new ManagerModel("1","2","TestManager",900,"(912) 623-5353","TestEmail@librarian.com");
        managers.add(mag);
    }


    public static ArrayList<ManagerModel> getManagers(){
        return managers;
    }
    public static String[] getUsernames(){
        return usernames;
    }
    public static String[] getPasswords(){
        return passwords;
    }

    public static void AddManager(ManagerModel mag) {
        managers.add(mag);
    }

}
