package integrationTest;


import com.example.kthimi.Model.AuthenticationModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AuthenticationModelTest {
    private static final String VALID_LIBRARIAN_USERNAME = "1";
    private static final String VALID_LIBRARIAN_PASSWORD = "11";
    private static final String VALID_MANAGER_USERNAME = "2";
    private static final String VALID_MANAGER_PASSWORD = "22";
    private static final String VALID_ADMIN_USERNAME = "3";
    private static final String VALID_ADMIN_PASSWORD = "33";
    private static final String INVALID_USERNAME = "invalidUsername";
    private static final String INVALID_PASSWORD = "invalidPassword";

    @Test
    public void testAuthenticateLibrarianWithValidCredentials() {
        AuthenticationModel authenticationModel = new AuthenticationModel();

        assertTrue(authenticationModel.authenticateLibrarian(VALID_LIBRARIAN_USERNAME, VALID_LIBRARIAN_PASSWORD));
    }

    @Test
    public void testAuthenticateLibrarianWithInvalidCredentials() {
        AuthenticationModel authenticationModel = new AuthenticationModel();

        assertFalse(authenticationModel.authenticateLibrarian(INVALID_USERNAME, INVALID_PASSWORD));
    }

    @Test
    public void testAuthenticateManagerWithValidCredentials() {
        AuthenticationModel authenticationModel = new AuthenticationModel();

        assertTrue(authenticationModel.authenticateManager(VALID_MANAGER_USERNAME, VALID_MANAGER_PASSWORD));
    }

    @Test
    public void testAuthenticateManagerWithInvalidCredentials() {
        AuthenticationModel authenticationModel = new AuthenticationModel();

        assertFalse(authenticationModel.authenticateManager(INVALID_USERNAME, INVALID_PASSWORD));
    }

    @Test
    public void testAuthenticateAdminWithValidCredentials() {
        AuthenticationModel authenticationModel = new AuthenticationModel();

        assertTrue(authenticationModel.authenticateAdmin(VALID_ADMIN_USERNAME, VALID_ADMIN_PASSWORD));
    }

    @Test
    public void testAuthenticateAdminWithInvalidCredentials() {
        AuthenticationModel authenticationModel = new AuthenticationModel();

        assertFalse(authenticationModel.authenticateAdmin(INVALID_USERNAME, INVALID_PASSWORD));
    }


}