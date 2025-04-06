package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import main.najah.code.UserService;

@DisplayName("User Service Tests")
public class UserServiceSimpleTest {

    UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    @Test
    @DisplayName("Valid email returns true")
    void testValidEmail() {
        assertTrue(userService.isValidEmail("user@example.com"));
    }

    @Test
    @DisplayName("Invalid email returns false")
    void testInvalidEmail() {
        assertFalse(userService.isValidEmail("no-at-symbol"));
    }

    @Test
    @DisplayName("Authentication success")
    void testAuthSuccess() {
        assertTrue(userService.authenticate("admin", "1234"));
    }

    @Test
    @DisplayName("Authentication failure")
    void testAuthFail() {
        assertFalse(userService.authenticate("user", "wrong"));
    }
}
