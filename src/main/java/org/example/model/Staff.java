package org.example.model;

/**
 * Abstract base class representing staff members in the movie ticket system
 */
public abstract class Staff {
    protected String staffId;
    protected String username;
    protected String password;
    protected String name;
    
    public Staff(String staffId, String username, String password, String name) {
        this.staffId = staffId;
        this.username = username;
        this.password = password;
        this.name = name;
    }
    
    // Getters
    public String getStaffId() {
        return staffId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public String getName() {
        return name;
    }
    
    // Setters
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Verify login credentials
     */
    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }
    
    /**
     * Get the role/type of the staff member
     */
    public abstract String getRole();
    
    @Override
    public String toString() {
        return staffId + ", " + username + ", " + name + ", " + getRole();
    }
}
