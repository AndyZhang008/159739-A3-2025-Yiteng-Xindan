package example;

import org.example.model.Manager;
import org.example.model.Staff;
import org.example.model.TicketSeller;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Staff Base Class Tests")
public class StaffTest {

    @Test
    @DisplayName("Should create TicketSeller with correct attributes")
    void testTicketSellerCreation() {
        TicketSeller seller = new TicketSeller("TS001", "john_seller", "pass123", "John Doe");

        assertEquals("TS001", seller.getStaffId());
        assertEquals("john_seller", seller.getUsername());
        assertEquals("pass123", seller.getPassword());
        assertEquals("John Doe", seller.getName());
        assertEquals("Ticket Seller", seller.getRole());
    }

    @Test
    @DisplayName("Should create Manager with correct attributes")
    void testManagerCreation() {
        Manager manager = new Manager("M001", "jane_manager", "admin456", "Jane Smith");

        assertEquals("M001", manager.getStaffId());
        assertEquals("jane_manager", manager.getUsername());
        assertEquals("admin456", manager.getPassword());
        assertEquals("Jane Smith", manager.getName());
        assertEquals("Manager", manager.getRole());
    }

    @Test
    @DisplayName("Should successfully login with correct credentials")
    void testSuccessfulLogin() {
        Staff seller = new TicketSeller("TS001", "john_seller", "pass123", "John Doe");

        assertTrue(seller.login("john_seller", "pass123"));
    }

    @Test
    @DisplayName("Should fail login with incorrect username")
    void testFailedLoginWrongUsername() {
        Staff seller = new TicketSeller("TS001", "john_seller", "pass123", "John Doe");

        assertFalse(seller.login("wrong_user", "pass123"));
    }

    @Test
    @DisplayName("Should fail login with incorrect password")
    void testFailedLoginWrongPassword() {
        Staff seller = new TicketSeller("TS001", "john_seller", "pass123", "John Doe");

        assertFalse(seller.login("john_seller", "wrong_pass"));
    }

    @Test
    @DisplayName("Should update staff name")
    void testUpdateName() {
        Staff seller = new TicketSeller("TS001", "john_seller", "pass123", "John Doe");
        seller.setName("John Smith");

        assertEquals("John Smith", seller.getName());
    }

    @Test
    @DisplayName("Should update staff password")
    void testUpdatePassword() {
        Staff seller = new TicketSeller("TS001", "john_seller", "pass123", "John Doe");
        seller.setPassword("newpass456");

        assertTrue(seller.login("john_seller", "newpass456"));
        assertFalse(seller.login("john_seller", "pass123"));
    }

    @Test
    @DisplayName("Should return correct toString format")
    void testToString() {
        Staff seller = new TicketSeller("TS001", "john_seller", "pass123", "John Doe");

        assertEquals("TS001, john_seller, John Doe, Ticket Seller", seller.toString());
    }
}