package com.example.kthimi.View.Manager;

import com.example.kthimi.Controller.*;
import com.example.kthimi.Controller.Mockers.FileBasedStockBookRepository;
import com.example.kthimi.Controller.Mockers.StockBookRepository;
import com.example.kthimi.Model.BookModel;
import com.example.kthimi.Model.SharedDataManager;
import com.example.kthimi.View.Librarian.LibrarianView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class AddStockManagerView {
    LibrarianFuncController librarianFuncController = new LibrarianFuncController();
    StockBookRepository stockBookRepository = new FileBasedStockBookRepository();
    BookController bookController = new BookController(stockBookRepository);

    public ArrayList<BookModel> books;
    BorderPane borderPane;
    Button bttBackManager = new Button("Back");
    Button bttNewBook = new Button("New Book");
    TextField bookISBN = new TextField();
    TextField title = new TextField();
    TextField supplier = new TextField();
    TextField originalPrice = new TextField();
    TextField sellingPrice = new TextField();
    TextField author = new TextField();
    TextField quantity = new TextField();
    Text textBookISBN = new Text("Book ISBN");
    Text textQuantity = new Text("Quantity");
    Text textSupplier = new Text("Supplier");
    Text textOriginalPrice = new Text("Original Price");
    Text textSellingPrice = new Text("Selling Price");
    Text textAuthor = new Text("Author");
    Text textTitle = new Text("Title");
    Text textSystem = new Text("System");
    TextField addedOrNotStockCategory = new TextField();
    Button stockCategoryAddBook = new Button("Add");
    Button bttBack = new Button("Back");
    Button bttAddBookToStock = new Button("Add to Stock");
    TextField addedOrNot = new TextField();
    Date date;
    NewCategoryManagerView newCategoryManagerView;
    ArrayList<String> categ;
    SupplyManagerView supplyManagerView;
    AddLibrarianController addLibrarianController;
    LibrarianView librarianView;
    BillLibrarianController billLibrarianController;

    // Example usage in AddStockManagerView or another appropriate place
    //AddLibrarianController librarianController = new AddLibrarianController(librarianViewInstance, categoryText);

    public AddStockManagerView(){

    }
    public AddStockManagerView(SupplyManagerView supplyManagerView, NewCategoryManagerView newCategoryManagerView){
        this.supplyManagerView = supplyManagerView;
        this.newCategoryManagerView = newCategoryManagerView;

        borderPane = createAddToStock();
        //per addin
        this.librarianView = new LibrarianView();
        this.addLibrarianController = new AddLibrarianController();
        this.billLibrarianController = new BillLibrarianController();
        this.books = SharedDataManager.getInstance().getBooks();

    }



    public BorderPane createAddToStock() {

        BorderPane border = new BorderPane();

        Text text = new Text("Choose category to add");
        StackPane stack = new StackPane();
        text.setFont(new Font(30));
        stack.getChildren().add(text);
        stack.setPadding(new Insets(20));
        border.setTop(stack);

        System.out.println("siper backut");
        StackPane stackBackButton = new StackPane();
        stackBackButton.getChildren().add(bttBackManager);
        bttBackManager.setOnAction(event -> {
            if(event.getSource()==bttBackManager) {
                bttBackManager.getScene().setRoot( supplyManagerView.createSupplyPage() );
            }
        });
        System.out.println("poshte backut");
        stackBackButton.setPadding(new Insets(0, 0, 40, 0));
        border.setBottom(stackBackButton);

        initializeCategories();
        if(categ.isEmpty()){
            categ.add("War");
        }
        categ = bookController.removeDuplicates(categ);
        System.out.println(categ + "Pas FUNKSI");

        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        int j=0;
        int k=0;
        System.out.println("para loopit");
        System.out.println(categ);
        System.out.println(categ.size());
        for (int i=0;i<categ.size();i++) {
            System.out.println("rreshti i 1 i loopit");
            if (i%5==0) {
                k=0;
                j++;
            }
            System.out.println("para krijimit");

            Button button = createButton(categ.get(i));

            grid.add(button,k++,j);

        }



        grid.setAlignment(Pos.CENTER);
        border.setCenter(grid);


        return border;
    }

    private Button createButton(String text) {
        System.out.println("Butoni i ri");
        Button button = new Button(text);
        button.setOnAction(new ButtonHandler(button.getText()));
        button.setId("button");
        return button;
    }

    public class ButtonHandler implements EventHandler<ActionEvent> {

        public String text;

        ButtonHandler(String text) {
            this.text = text ;
        }

        @Override
        public void handle(ActionEvent event) {

            SharedDataManager.getInstance().setBooks(books);

            Stage stage = new Stage();
            Scene scene = new Scene(categoryStock(text));
            //stage.getIcons().add(new Image("bookIcon.png"));
            stage.setWidth(800);
            stage.setHeight(600);
            stage.setScene(scene);
            stage.show();

        }

    }



    public BorderPane categoryStock(String category) {

        BorderPane border = new BorderPane();

        Text text = new Text("Choose Book to add");
        StackPane stack = new StackPane();
        text.setFont(new Font(30));
        stack.getChildren().add(text);
        stack.setPadding(new Insets(20));
        border.setTop(stack);


        books = bookController.getBookFromCategory(category);
        System.out.println("Ktu duhen printuar" + books);
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        int j=0;
        int k=0;

        if (books.isEmpty()) {
            System.out.println("Empty");
        }
        else {
            for (int i=0;i<books.size();i++) {
                if (i%5==0) {
                    k=0;
                    j++;
                }
                Button button = createButton2(books.get(i));

                grid.add(button,k++,j);

            }
        }

        grid.add(bttNewBook, k, j);
        grid.setAlignment(Pos.CENTER);
        border.setCenter(grid);

        bttNewBook.setOnAction(event -> {

            if(event.getSource()==bttNewBook) {
                bookISBN.clear();
                title.clear();
                supplier.clear();
                originalPrice.clear();
                sellingPrice.clear();
                author.clear();
                quantity.clear();


                bttNewBook.getScene().setRoot( stockCategoryNewBookPage(category) );
            }


        });

        bttNewBook.setId("bttNewBook");
        return border;


    }

    private Button createButton2(BookModel book) {
        Button button = new Button(book.getTitle());
        button.setOnAction(new ButtonHandler2(book));
        button.setId("newCateg");
        return button ;
    }

    class ButtonHandler2 implements EventHandler<ActionEvent> {

        private final BookModel book;

        ButtonHandler2(BookModel book) {
            this.book = book;
        }

        @Override
        public void handle(ActionEvent event) {
            Stage stage = new Stage();
            Scene scene = new Scene(stockBookPage(book));
            //stage.getIcons().add(new Image("bookIcon.png"));
            stage.setWidth(800);
            stage.setHeight(600);
            stage.setScene(scene);
            stage.show();

        }

    }

    public BorderPane stockBookPage(BookModel book) {

        BorderPane border = new BorderPane();

        Text text = new Text("Add \""+book.getTitle()+"\" To stock");
        StackPane stack = new StackPane();
        text.setFont(new Font(30));
        stack.getChildren().add(text);
        stack.setPadding(new Insets(20));
        border.setTop(stack);



        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setAlignment(Pos.CENTER);
        grid.add(textQuantity, 0, 0);
        grid.add(quantity, 1, 0);
        grid.add(bttAddBookToStock,2, 4);
        grid.add(addedOrNot, 1, 3);
        grid.add(textSystem, 0, 3);
        border.setCenter(grid);


        addedOrNot.setEditable(false);
        bttAddBookToStock.setOnAction(event -> {

            if(event.getSource()==bttAddBookToStock) {

                if(!quantity.getCharacters().toString().matches("\\d{1,}")){
                    addedOrNot.setText("Failed, Invalid Quantity");
                    return;
                }

                if (quantity.getCharacters().toString().isEmpty()) {
                    addedOrNot.setText("Failed, Empty Quantity");
                    return;
                }

                ArrayList<BookModel> stockbooks = bookController.getStockBooks();

                for (int i=0;i<stockbooks.size();i++) {
                    if (stockbooks.get(i).getISBN().equals(book.getISBN())) {

                        stockbooks.get(i).AddStock(Integer.parseInt(quantity.getCharacters().toString()));
                        stockbooks.get(i).addPurchasedDate(new Date());
                        stockbooks.get(i).addQuantitiesPurchased(Integer.parseInt(quantity.getCharacters().toString()));
                    }
                }

                librarianFuncController.updateBookQuantity(stockbooks);

                quantity.clear();
                addedOrNot.setText("Added");
            }

        });

        bttAddBookToStock.setId("bttAddBookToStock");

        return border;


    }



    ////////////

    public BorderPane stockCategoryNewBookPage(String category) {

        BorderPane border = new BorderPane();

        Text text = new Text("Add book to "+category+" category");
        StackPane stack = new StackPane();
        text.setFont(new Font(30));
        stack.getChildren().add(text);
        stack.setPadding(new Insets(20));
        border.setTop(stack);

        GridPane gridSupplier = new GridPane();
        ToggleGroup Supplier = new ToggleGroup();
        RadioButton r1 = new RadioButton("Ingram Content Group, Inc");
        RadioButton r2 = new RadioButton("Baker & Taylor");
        RadioButton r3 = new RadioButton("BCH Fulfillment & Distribution");
        RadioButton r4 = new RadioButton("Cardinal Publishers Group");
        RadioButton r5 = new RadioButton("Bella Distribution");
        RadioButton r6 = new RadioButton("Publishers Group West");
        r1.setToggleGroup(Supplier);
        r2.setToggleGroup(Supplier);
        r3.setToggleGroup(Supplier);
        r4.setToggleGroup(Supplier);
        r5.setToggleGroup(Supplier);
        r6.setToggleGroup(Supplier);
        gridSupplier.add(r1, 0, 0);
        gridSupplier.add(r2, 1, 0);
        gridSupplier.add(r3, 1, 2);
        gridSupplier.add(r4, 0, 1);
        gridSupplier.add(r5, 0, 2);
        gridSupplier.add(r6, 1, 1);
        gridSupplier.setHgap(5);
        gridSupplier.setVgap(5);




        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setAlignment(Pos.CENTER);
        grid.add(textBookISBN, 0, 0);
        grid.add(bookISBN, 1, 0);
        grid.add(textTitle,0, 1);
        grid.add(title, 1, 1);
        grid.add(textSupplier, 0, 2);
        grid.add(gridSupplier, 1, 2);
        grid.add(textOriginalPrice, 0, 3);
        grid.add(originalPrice, 1, 3);
        grid.add(textSellingPrice,0,4);
        grid.add(sellingPrice, 1, 4);
        grid.add(textAuthor, 0, 5);
        grid.add(author, 1, 5);
        grid.add(textQuantity, 0, 6);
        grid.add(quantity, 1, 6);
        grid.add(textSystem, 0, 9);
        grid.add(addedOrNotStockCategory, 1, 9);
        grid.add(stockCategoryAddBook, 2, 10);
        grid.add(bttBack, 0, 10);


        addedOrNotStockCategory.setEditable(false);
        bttBack.setOnAction(event -> {
            if(event.getSource()==bttBack) {
                bttBack.getScene().setRoot( categoryStock(category) );
            }

        });
        stockCategoryAddBook.setOnAction(event -> {
            if(event.getSource()==stockCategoryAddBook) {

                RadioButton chk = (RadioButton)Supplier.getSelectedToggle();

                if (bookISBN.getCharacters().toString().isEmpty() || title.getCharacters().toString().isEmpty() || Supplier.getSelectedToggle() == null
                        || originalPrice.getCharacters().toString().isEmpty() || sellingPrice.getCharacters().toString().isEmpty() || author.getCharacters().toString().isEmpty()
                        || quantity.getCharacters().toString().isEmpty()) {
                    addedOrNotStockCategory.setText("Failed, Empty Fields");
                    return;
                }

                if (!bookISBN.getCharacters().toString().matches("\\d{13}")) {
                    addedOrNotStockCategory.setText("Failed, Invalid ISBN");
                    return;
                }

                if ( !(quantity.getCharacters().toString().matches("\\d{1,}")) || !(originalPrice.getCharacters().toString().matches("\\d{1,}"))
                        || !(sellingPrice.getCharacters().toString().matches("\\d{1,}")) ||  Integer.parseInt(sellingPrice.getCharacters().toString())==0
                        || Integer.parseInt(originalPrice.getCharacters().toString()) == 0 || Integer.parseInt(quantity.getCharacters().toString()) == 0) {

                    addedOrNotStockCategory.setText("Failed, Invalid Numbers");
                    return;
                }

                if (bookController.isPartOfBooks((bookISBN.getCharacters().toString()))){
                    addedOrNotStockCategory.setText("Failed, Already In Stock");
                    return;

                }

                String bIsbn = bookISBN.getCharacters().toString();
                String bTitle = title.getCharacters().toString();
                String bSupplier = chk.getText();
                int bSellingPrice = Integer.parseInt(sellingPrice.getCharacters().toString());
                int bOriginalPrice = Integer.parseInt(originalPrice.getCharacters().toString());
                String bAuthor = author.getCharacters().toString();
                int bStock = Integer.parseInt(quantity.getCharacters().toString());

                BookModel book = new BookModel(bIsbn,bTitle,category,bSupplier,bOriginalPrice,bSellingPrice,bAuthor,bStock);
                book.addQuantitiesPurchased(bStock);
                date = new Date();
                book.addPurchasedDate(date);

                //this adds the books to the stock and saves it to the file
                bookController.addBookToStock(book);

                bookISBN.clear();
                title.clear();
                Supplier.getSelectedToggle().setSelected(false);
                originalPrice.clear();
                sellingPrice.clear();
                author.clear();
                quantity.clear();


                addedOrNotStockCategory.setText("Added");

            }

        });

        border.setCenter(grid);

        bookISBN.setId("bookISBN");
        title.setId("title");
        gridSupplier.setId("gridSupplier");
        originalPrice.setId("originalPrice");
        sellingPrice.setId("sellingPrice");
        author.setId("author");
        quantity.setId("quantity");

        stockCategoryAddBook.setId("stockCategoryAddBook");
        addedOrNotStockCategory.setId("addedOrNotStockCategory");

        return border;
    }


    public ArrayList<String> getCategories() {
        return categ;
    }

    private void initializeCategories() {
        if (newCategoryManagerView != null) {
            categ = newCategoryManagerView.getCategories();
        } else {
            categ = new ArrayList<>();
        }
    }


    public void setNewCategoryManagerView(NewCategoryManagerView newCategoryManagerView) {
        this.newCategoryManagerView = newCategoryManagerView;
        initializeCategories();
    }

    public String getText() {
        return "text";
    }


    public void setBooks(ArrayList<BookModel> books) {
        this.books = books;
    }

    // Getter method to retrieve the books array
    public ArrayList<BookModel> getBooks() {
        System.out.println("TEK GETI"+books);
        return books;
    }

    public ArrayList<BookModel> getBooksFromSharedManager() {
        return SharedDataManager.getInstance().getBooks();
    }
}
