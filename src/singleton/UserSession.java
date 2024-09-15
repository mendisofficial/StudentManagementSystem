/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package singleton;

/**
 *
 * @author chathushamendis
 */
public class UserSession {
    private static UserSession instance;
    private String username;
    private String role;

    // Private constructor to restrict instantiation
    private UserSession() {
        
    }

    // Singleton pattern to ensure only one instance of UserSession exists
    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    // Getters and Setters for user information
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Method to clear the session when user logs out
    public void clearSession() {
        username = null;
        role = null;
        instance = null;
    }
}
