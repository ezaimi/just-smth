package com.example.kthimi.Controller;

import com.example.kthimi.Controller.Mockers.FileBasedStockBookRepository;
import com.example.kthimi.Controller.Mockers.StockBookRepository;
import com.example.kthimi.Model.BookModel;
import com.example.kthimi.Model.LibrarianModel;
import com.example.kthimi.View.Librarian.LibrarianView;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

//permban funksionet e billit tek librarian view
//mund te emertohet dhe BillController til now
public class LibrarianFuncController {
    StockBookRepository stockBookRepository = new FileBasedStockBookRepository();
    BookController bookController = new BookController(stockBookRepository);
    BillLibrarianController billLibrarianController;
    LibrarianModel librarianModel;
    public static int totalBooksSold=0;
    public double  totalIncome=0;
    private double moneyMade=0;
    public ArrayList<Date> datesSold;
    public ArrayList<Double> moneyMadeDates;
    private int numberOfBills;
    private int booksSold=0;
    public static String STOCK_FILE_PATH = "Books.bin";
    private static final String BILL_COUNTER_FILE_PATH = "bill_counter.txt";


    public LibrarianFuncController(){
        datesSold = new ArrayList<>();
        moneyMadeDates = new ArrayList<>();
    }

    public LibrarianFuncController(BillLibrarianController billLibrarianController) {
        this.billLibrarianController = billLibrarianController;
        System.out.println("kajjhfdakshfddkshfkdkjdshldsjhhhjhfdshf");
        datesSold = new ArrayList<>();
        moneyMadeDates = new ArrayList<>();

        this.numberOfBills = loadNumberOfBills();
    }

    private int loadNumberOfBills() {
        try (BufferedReader reader = new BufferedReader(new FileReader(BILL_COUNTER_FILE_PATH))) {
            String line = reader.readLine();
            if (line != null) {
                return Integer.parseInt(line);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void saveNumberOfBills() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(BILL_COUNTER_FILE_PATH))) {
            writer.println(numberOfBills);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void incrementNumberOfBills() {
        //numberOfBills++;
        saveNumberOfBills();
    }

    public void checkOutBooks(ArrayList<BookModel> books, ArrayList<Integer> quantities) throws  IOException{

        PrintWriter writer = new PrintWriter("Bill"+(numberOfBills++)+".txt");
        ArrayList<BookModel> stockbooks = bookController.getStockBooks();
        double totalPrice = 0;

        System.out.println("TEK LIBRARIAN STOCKU FILLESTAR");
        System.out.println(stockbooks.toString());

        removeDuplicatesSoldBooks(stockbooks,quantities);


        System.out.println("After removing duplicates");
        System.out.println(stockbooks.toString());

        for (int i=0;i<books.size();i++) {
            for (int j=0;j<stockbooks.size();j++) {
                System.out.println("jessssssssssssssssssssssssssssssss" + books);
                System.out.println("jessssssssssssssssssssssssssssssss" + stockbooks);

                System.out.println(books.get(i).getISBN().equals(stockbooks.get(j).getISBN())+ "WOW" );

                if (books.get(i).getISBN().equals(stockbooks.get(j).getISBN()))
                {
                    System.out.println(books.get(i).getISBN().equals(stockbooks.get(j).getISBN())+ "WOW jO");
                    System.out.println("Title: \""+stockbooks.get(j).getTitle());
                    writer.write("Title: \""+stockbooks.get(j).getTitle()+"\", Quantities: "+quantities.get(i)+", OriginalPrice "+
                            stockbooks.get(j).getSellingPrice() +", Price: "+stockbooks.get(j).getSellingPrice()*quantities.get(i)+"\n");

                    // Reduce stock quantity by the checkout amount
                    stockbooks.get(j).removeStock(quantities.get(i));

                    stockbooks.get(j).addDate(new Date());
                    stockbooks.get(j).addQuantity( quantities.get(i) );

                    totalPrice+=stockbooks.get(j).getSellingPrice()*quantities.get(i);

                    totalBooksSold+=quantities.get(i);
                    totalIncome += totalPrice;
                    booksSold += quantities.get(i);

                }

            }
        }


        updateBookQuantity(stockbooks);

        // Update the stock file here if needed

//		try (ObjectOutputStream objout = new ObjectOutputStream(new FileOutputStream(STOCK_FILE_PATH))) {
//			objout.writeObject(stockbooks);
//		}

        System.out.println("AFter update");
        System.out.println(stockbooks.toString());


        moneyMade+=totalPrice;
        //numberOfBills+=1;
        incrementNumberOfBills();
        datesSold.add(new Date());
        moneyMadeDates.add(moneyMade);

        writer.write("\nTotal price: "+totalPrice+" Date: "+ (new Date()));
        writer.close();


    }
//    public ArrayList<Date> getDatesSold() {return datesSold;}
//    public void setDatesSold(ArrayList<Date> datesSold){this.datesSold = datesSold;}

    public int getNumberOfBills() {
        return numberOfBills;
    }

    public void setNumberOfBills(int numberOfBills){ this.numberOfBills = numberOfBills;}




    public static void removeDuplicatesSoldBooks(ArrayList<BookModel> books, ArrayList<Integer> quantities) {
        if (books.isEmpty() || quantities.isEmpty()) {
            return;
        }

        if(books.size() == 1)
            return;

        for (int i = 0; i < books.size(); i++) {
            for (int j = i + 1; j < books.size(); j++) {
                if (books.get(i).getISBN().equals(books.get(j).getISBN())) {
                    quantities.set(i, quantities.get(i) + quantities.get(j));
                    books.remove(j); // Remove the duplicated book
                    quantities.remove(j); // Remove the corresponding quantity
                    j--; // Adjust the index after removal
                }
            }
        }


    }


    public void updateBookQuantity(ArrayList<BookModel> updatedBooks) {
        ArrayList<BookModel> books = bookController.getStockBooks();
        for (BookModel updatedBook : updatedBooks) {
            for (int i = 0; i < books.size(); i++) {
                BookModel currentBook = books.get(i);
                if (currentBook.getISBN().equals(updatedBook.getISBN())) {
                    // Replace the book in the list with the updated book
                    books.set(i, updatedBook);
                    break;
                }
            }
        }
        // Save the updated books back to the file if needed
        saveBooksToFile(books);
    }

    public static void saveBooksToFile(ArrayList<BookModel> books) {
        try (ObjectOutputStream objout = new ObjectOutputStream(new FileOutputStream(STOCK_FILE_PATH))) {
            objout.writeObject(books);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception accordingly
        }
    }



    public static boolean checkPassword(String password) {
        return password.matches(".{8,}");
    }

    public static boolean checkPhone(String phone) {
        return phone.matches("\\(912\\)\\s\\d{3}\\-\\d{4}");
    }

    public static boolean checkEmail(String email) {
        return email.matches("[a-zA-Z]{1,}\\@[a-zA-z]{1,}\\.com");
    }

    public static boolean checkSalary(String salary) {
        return salary.matches("^[0-9]+\\.?[0-9]*$");
    }

    public static boolean checkName(String name) {
        return name.matches("[a-zA-Z]{1,}");
    }



    public boolean EnoughStock(String ISBN, int quantity) {

        ArrayList<BookModel> stockbooks = bookController.getStockBooks();

        if (ISBN.length() >= 13) {
            ISBN = ISBN.substring(0, 13);
        }

        for (int i=0;i<stockbooks.size();i++) {
            System.out.println(stockbooks.get(i).getISBN().equals(ISBN) + "Yeeeeeeeeeeeeeeeeeeeeeeee");
            System.out.println("book1: " + stockbooks.get(i).getISBN());
            System.out.println("book2: " + ISBN);
            if (stockbooks.get(i).getISBN().equals(ISBN))
                //System.out.println(stockbooks.get(i).getISBN().equals(ISBN)+"Yeeeeeeeeeeeeeeeeeeeeeeee");
                if (stockbooks.get(i).getStock() - quantity >= 0) {
                    System.out.println("STOCKUUUUUUUUU" + stockbooks.get(i).getStock());
                    System.out.println("quant" + quantity);

                    return true;
                } else {
                    System.out.println("QUANTITYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY" + quantity);
                    return false;
                }
        }
            return false;

    }



}
