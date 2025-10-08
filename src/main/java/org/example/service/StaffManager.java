
package org.example.service;

import org.example.model.Manager;
import org.example.model.Staff;
import org.example.model.TicketSeller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class to manage staff members and load them from CSV file
 */
public class StaffManager {
    private List<Staff> staffList = new ArrayList<>();

    /**
     * Load staff members from ./staffs.csv
     */
    public void loadStaff(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Skip empty lines and comments
                if (line.trim().isEmpty() || line.trim().startsWith("#")) {
                    continue;
                }

                String[] parts = line.split(",\\s*");
                if (parts.length < 5) {
                    System.err.println("Invalid staff record (insufficient fields): " + line);
                    continue;
                }

                String staffId = parts[0];
                String username = parts[1];
                String password = parts[2];
                String name = parts[3];
                String role = parts[4];

                Staff staff;
                switch (role.toLowerCase()) {
                    case "ticketseller":
                        staff = new TicketSeller(staffId, username, password, name);
                        break;
                    case "manager":
                        staff = new Manager(staffId, username, password, name);
                        break;
                    default:
                        System.err.println("Unknown role: " + role + " in record: " + line);
                        continue;
                }
                staffList.add(staff);
            }
        }
    }

    /**
     * Find staff by username
     */
    public Staff findByUsername(String username) {
        return staffList.stream()
                .filter(s -> s.getUsername().equalsIgnoreCase(username))
                .findFirst()
                .orElse(null);
    }

    /**
     * Authenticate staff member
     */
    public Staff authenticate(String username, String password) {
        Staff staff = findByUsername(username);
        if (staff != null && staff.login(username, password)) {
            return staff;
        }
        return null;
    }
}