package example;

import org.example.model.Staff;
import org.example.service.StaffManager;
import org.junit.jupiter.api.*;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Staff Manager Tests")
public class StaffManagerTest {

    private StaffManager staffManager;

    @BeforeEach
    void setUp() {
        staffManager = new StaffManager();
    }

    @Test
    @DisplayName("Should authenticate staff correctly")
    void testAuthenticatoin() throws IOException {
        try {
            // Create StaffManager and load staff from CSV
            StaffManager staffManager = new StaffManager();
            staffManager.loadStaff("./staffs.csv");

            // Test successful authentication
            Staff authenticatedStaff = staffManager.authenticate("m2", "m2");
            assertNotNull(authenticatedStaff, "Fail to authenticate valid credentials (username:m2, password:m2)");

            // Test failed authentication
            Staff failedAuth = staffManager.authenticate("s1", "asd");
            assertNull(failedAuth, "Fail to detect invalid credentials (username:s1, password:asd)");

        } catch (Exception e) {
            System.err.println("Error loading staff: " + e.getMessage());
            e.printStackTrace();
        }
    }
}