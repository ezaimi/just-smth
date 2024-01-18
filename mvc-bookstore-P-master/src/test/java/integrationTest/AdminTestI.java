package integrationTest;

import com.example.kthimi.Controller.AdminFuncController;
import com.example.kthimi.Controller.BookController;
import com.example.kthimi.Controller.FixLibrariansController;
import com.example.kthimi.Controller.LibrarianFuncController;
import com.example.kthimi.Controller.Mockers.FileBasedStockBookRepository;
import com.example.kthimi.Model.AdministratorModel;
import com.example.kthimi.Model.BookModel;
import com.example.kthimi.Model.LibrarianModel;
import com.example.kthimi.Model.ManagerModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AdminTestI {

    private static String TEMP_STOCK_FILE_PATH = "tempStockFile.bin";
    private static final String TEST_ISBN = "1234567890123";
    private static final String TEST_TITLE = "Test Book";

    ArrayList<ManagerModel> magList = new ArrayList<>();


    public static void setStockFilePath(String newPath) {
        try {
            // Change the STOCK_FILE_PATH field in BookController class
            changeField(BookController.class, "STOCK_FILE_PATH", newPath);
            changeField(LibrarianModel.class, "STOCK_FILE_PATH", newPath);
            changeField(LibrarianFuncController.class, "STOCK_FILE_PATH", newPath);
            changeField(FileBasedStockBookRepository.class, "STOCK_FILE_PATH", newPath);
            // Add more classes and fields to change here as necessary
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void changeField(Class<?> targetClass, String fieldName, String newValue) throws NoSuchFieldException, IllegalAccessException {
        Field field = targetClass.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(null, newValue);
    }

    private BookModel createTestBook() {
        return new BookModel(TEST_ISBN, TEST_TITLE, "Category1", "Test Publisher", 20.00, 25.00, "Test Author", 10);
    }

    @BeforeEach
    public void setUp() {
        AdminTestI.setStockFilePath(TEMP_STOCK_FILE_PATH);
        // Create a temporary file for testing
        createTemporaryFile();
    }

    private void createTemporaryFile() {
        try (ObjectOutputStream objout = new ObjectOutputStream(new FileOutputStream(TEMP_STOCK_FILE_PATH))) {
            // Write initial data to the temporary file if needed for setup
            // For instance:
            BookModel book = createTestBook();
            objout.writeObject(book);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<BookModel> saveBooksToTemporaryFile(ArrayList<BookModel> books) {
        try (ObjectOutputStream objout = new ObjectOutputStream(new FileOutputStream(TEMP_STOCK_FILE_PATH))) {
            objout.writeObject(books);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return books;
    }

    ///////////Klea//////////////

//    @Test
//    public void testGetSalaries() {
//        FixLibrariansController.InstantiateLibrarians();
//        AdminFuncController.InstantiateManagers();
//        //AdminFuncController.InstantiateAdmins();
//
//        double expected = 0;
//
//        for (LibrarianModel librarian : ManagerModel.getLibrarians()) {
//            expected += librarian.getSalary();
//        }
//
//        for (ManagerModel manager : AdministratorModel.getManagers()) {
//            expected += manager.getSalary();
//        }
//
////        for (Administrator admin : Administrator.getAdmins()) {
////            expected += admin.getSalary();
////        }
//
//        double actual = AdminFuncController.getSalaries();
//        assertEquals(expected, actual);
//    }

    @Test
    public void testSalary(){

        LibrarianModel librarian1 = new LibrarianModel("librarian1", "password1", "John Doe", 50000.0, "123456789", "john@example.com");
        LibrarianModel librarian2 = new LibrarianModel("librarian2", "password2", "Jane Doe", 50000.0, "987654321", "jane@example.com");
        ManagerModel.getLibrarians().add(librarian1);
        ManagerModel.getLibrarians().add(librarian2);

        double salary=0;
        int size=ManagerModel.getLibrarians().size();
        System.out.println(size);

        for (int i=0;i<ManagerModel.getLibrarians().size();i++) {
            salary+=ManagerModel.getLibrarians().get(i).getSalary();
            System.out.println(salary);
        }

//
        ManagerModel mag =new ManagerModel("Alfie123","SSU6umwt","Alfie",500,"(912) 921-2728","aflie@librarian.com");
        AdministratorModel.getManagers().add(mag);
        for (int i=0;i<AdministratorModel.getManagers().size();i++) {
            salary += AdministratorModel.getManagers().get(i).getSalary();
        }
        System.out.println("total"+salary);


        AdministratorModel adm =new AdministratorModel("Alfie123","SSU6umwt","Alfie",500,"(912) 921-2728","aflie@librarian.com");
        AdministratorModel.getAdmins().add(adm);
        for (int i=0;i<AdministratorModel.getAdmins().size();i++) {
            salary += AdministratorModel.getAdmins().get(i).getSalary();
        }

        assertEquals(salary,AdminFuncController.getSalaries());
    }

    @Test
    public void testInstantiateManagers() {
        AdministratorModel.getManagers().clear();
        AdministratorModel.InstantiateManagers();
        ArrayList<ManagerModel> managers = AdministratorModel.getManagers();


        assertNotNull(managers);
        assertEquals(3, managers.size());


        ManagerModel firstManager = managers.get(0);
        assertEquals("Calv1n", firstManager.getUsername());
        assertEquals("PQ532Ayba", firstManager.getPassword());
        assertEquals("Calvin", firstManager.getName());
        assertEquals(900, firstManager.getSalary());
        assertEquals("(912) 561-2628", firstManager.getPhone());
        assertEquals("calvl@manager.com", firstManager.getEmail());

    }


    /////////////Era/////////////

    @Test
    public void testAddManager() {
        // Create a new Manager object
        ManagerModel newManager = new ManagerModel("NewManager", "NewPassword", "NewName", 1000, "(123) 456-7890", "new@example.com");

        // Instantiate Managers (Mock data)
        AdministratorModel.InstantiateManagers();

        // Get the size of the managers list before adding the new manager
        int initialSize = AdministratorModel.getManagers().size();

        // Add the new manager
        AdministratorModel.AddManager(newManager);

        // Get the updated size of the managers list
        int updatedSize = AdministratorModel.getManagers().size();

        // Check if the size has increased by one after adding the manager
        assertEquals(initialSize + 1, updatedSize);

        // Check if the new manager is present in the managers list
        assertTrue(AdministratorModel.getManagers().contains(newManager));

    }

    @Test
    public void testManagerChecker() {
        // Create an instance of Administrator
        AdministratorModel administrator = new AdministratorModel("adminUsername", "adminPassword");

        // Create some test data
        AdministratorModel.InstantiateManagers();

        // Verify valid manager credentials
        assertTrue(AdminFuncController.ManagerChecker(new ManagerModel("Calv1n", "PQ532Ayba")));
        assertTrue(AdminFuncController.ManagerChecker(new ManagerModel("Lui54", "y@.3FYrn")));

        // Verify invalid manager credentials
        assertFalse(AdminFuncController.ManagerChecker(new ManagerModel("invalidUser", "invalidPassword")));
        assertFalse(AdminFuncController.ManagerChecker(new ManagerModel("Calv1n", "invalidPassword")));
        assertFalse(AdminFuncController.ManagerChecker(new ManagerModel("invalidUser", "PQ532Ayba")));
    }

    @Test
    void testManagerCheckerInvalid() {
        // Create a Manager object with invalid credentials
        ManagerModel invalidManager = new ManagerModel("invalid", "password");

        // Check if the ManagerChecker method returns false for invalid credentials
        assertFalse(AdminFuncController.ManagerChecker(invalidManager));
    }

    @Test
    public void testChecker() {
        // Create some test data
        AdminFuncController.InstantiateManagers();

        // Verify valid credentials
        assertTrue(AdminFuncController.checker("J0sh", "&zsX6QVZ"));
        assertTrue(AdminFuncController.checker("1", "3"));

        // Verify invalid credentials
        assertFalse(AdminFuncController.checker("invalidUser", "invalidPassword"));
        assertFalse(AdminFuncController.checker("J0sh", "invalidPassword"));
        assertFalse(AdminFuncController.checker("invalidUser", "&zsX6QVZ"));
    }


    //////////Ardisa///////////

    @Test
    public void testGetBackManager() {
        ArrayList<ManagerModel> managerList = new ArrayList<>();
        ArrayList<ManagerModel>emptyList=new ArrayList<>();

        ManagerModel mag1 = new ManagerModel("Calv1n", "PQ532Ayba", "Calvin", 900, "(912) 561-2628", "calvl@manager.com");
        managerList.add(mag1);
        ManagerModel mag2 = new ManagerModel("Lui54", "y@.3FYrn", "Lui", 900, "(912) 218-2594", "lu@manager.com");
        managerList.add(mag2);
        ManagerModel mag3 = new ManagerModel("1", "2", "TestManager", 900, "(912) 623-5353", "TestEmail@librarian.com");
        managerList.add(mag3);
        assertEquals(managerList.size(),3);

        AdministratorModel administrator = new AdministratorModel("1", "3");

        ManagerModel foundManager = AdminFuncController.getBackManager(mag2,managerList);


        assertEquals("Lui54", foundManager.getUsername());
        assertEquals("y@.3FYrn", foundManager.getPassword());
        assertNull(AdminFuncController.getBackManager(mag1,emptyList));
        // Add more assertions for other properties as needed
    }
    @Test
    public void testGetManagers() {

        AdministratorModel administrator = new AdministratorModel("1", "3");

        ArrayList<ManagerModel> returnedManagers = AdministratorModel.getManagers();
    }
    @Test
    public void testDeleteManager() {

        ArrayList<ManagerModel> managerList = new ArrayList<>();
        ManagerModel mag1 = new ManagerModel("Calv1n", "PQ532Ayba", "Calvin", 900, "(912) 561-2628", "calvl@manager.com");
        managerList.add(mag1);
        ManagerModel mag2 = new ManagerModel("Lui54", "y@.3FYrn", "Lui", 900, "(912) 218-2594", "lu@manager.com");
        managerList.add(mag2);
        ManagerModel mag3 = new ManagerModel("1", "2", "TestManager", 900, "(912) 623-5353", "TestEmail@librarian.com");
        managerList.add(mag3);

        AdministratorModel administrator = new AdministratorModel();

        AdminFuncController.deleteManager(mag2,managerList);
        //System.out.println(managerList);

        assertEquals(2,managerList.size());
        assertFalse(AdministratorModel.getManagers().contains(mag2));
    }
    @Test
    public void testUpdateManagers() {
        // Arrange
        ArrayList<ManagerModel> managerList = new ArrayList<>();
        ManagerModel mag1 = new ManagerModel("Calv1n", "PQ532Ayba", "Calvin", 900, "(912) 561-2628", "calvl@manager.com");
        managerList.add(mag1);
        ManagerModel mag2 = new ManagerModel("Lui54", "y@.3FYrn", "Lui", 900, "(912) 218-2594", "lu@manager.com");
        managerList.add(mag2);
        ManagerModel mag3 = new ManagerModel("1", "2", "TestManager", 900, "(912) 623-5353", "TestEmail@librarian.com");
        managerList.add(mag3);

        ManagerModel updatedManager = new ManagerModel("Lui54", "updatedPassword", "UpdatedLui", 1000, "(912) 999-9999", "updatedlu@manager.com");

        AdminFuncController.updateManagers(updatedManager, managerList);

        assertEquals("updatedPassword", mag2.getPassword());
        assertEquals(1000, mag2.getSalary());
        assertEquals("(912) 999-9999", mag2.getPhone());
        assertEquals("updatedlu@manager.com", mag2.getEmail());
    }



}