package com.example.kthimi.Controller;

import com.example.kthimi.Model.AdministratorModel;
import com.example.kthimi.Model.LibrarianModel;
import com.example.kthimi.Model.ManagerModel;

import java.util.ArrayList;

public class AdminFuncController extends AdministratorModel {

    static ArrayList<ManagerModel> managers = AdministratorModel.getManagers();
    static String[] usernames = AdministratorModel.getUsernames();
    static String[] passwords = AdministratorModel.getPasswords();


    public static void deleteManager(ManagerModel mag, ArrayList<ManagerModel>managers) {
        for (int i=0;i<managers.size();i++) {
            System.out.println(managers);
            if (managers.get(i).getUsername().equals(mag.getUsername()) ) {
                System.out.println(mag.getUsername());
                managers.remove(i);
                System.out.println(managers);
                return;
            }
        }

    }

    public static void updateManagers(ManagerModel mag,ArrayList<ManagerModel>managers) {

        for (int i=0;i<managers.size();i++){
            if (managers.get(i).getUsername().equals(mag.getUsername())) {
                managers.get(i).setEmail( mag.getEmail() );
                managers.get(i).setPhone( mag.getPhone() );
                managers.get(i).setSalary( mag.getSalary() );
                managers.get(i).setPassword( mag.getPassword() );
                managers.get(i).setUsername( mag.getUsername() );
                return;
            }

        }

    }

    public static double getSalaries() {

        double ans = 0;

        for (int i=0;i<ManagerModel.getLibrarians().size();i++) {
            ans += ManagerModel.getLibrarians().get(i).getSalary();
        }

        for (int i=0;i<AdministratorModel.getManagers().size();i++) {
            ans += AdministratorModel.getManagers().get(i).getSalary();
        }

        for (int i=0;i<AdministratorModel.getAdmins().size();i++) {
            ans += AdministratorModel.getAdmins().get(i).getSalary();
        }
        return ans;
    }


    public static boolean ManagerChecker(ManagerModel mag) {
        for (int i=0;i<managers.size();i++) {
            if (managers.get(i).getUsername().equals(mag.getUsername()) && managers.get(i).getPassword().equals(mag.getPassword()))
                return true;
        }
        return false;


    }


    public static boolean checker(String username,String password) {

        for (int i=0;i<usernames.length;i++) {
            if (usernames[i].equals(username))
                if (passwords[i].equals(password))
                    return true;
        }

        return false;
    }


    public static ManagerModel getBackManager(ManagerModel mag,ArrayList<ManagerModel>managers) {

//    	for (int i=0;i<managers.size();i++){
//    		if (managers.get(i).getUsername().equals(mag.getUsername()))
//    			return managers.get(i);
//    	}
//    	return null;
        for (int i=0;i<managers.size();i++){
            if (managers.get(i).getUsername().equals(mag.getUsername()))
                return managers.get(i);
        }

        return null;
    }
}
