package ClassAbsensi;


public class Session {
    
    private static Session instance;
    private User currentUser;
    
    // Private constructor untuk Singleton
    private Session() {}
    
    // Method untuk mendapatkan instance
    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    // Set user yang sedang login
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
    
    // Get user yang sedang login
    public User getCurrentUser() {
        return this.currentUser;
    }
    
    // Method untuk cek apakah ada user yang login
    public boolean isLoggedIn() {
        return this.currentUser != null;
    }
    
    // Method untuk logout (clear session)
    public void clearSession() {
        this.currentUser = null;
    }
    
    // Method helper untuk mendapatkan username
    public String getUsername() {
        return currentUser != null ? currentUser.getUsername() : "";
    }
    
    // Method helper untuk mendapatkan nama
    public String getNama() {
        return currentUser != null ? currentUser.getNama() : "";
    }
    
     // Method helper untuk mendapatkan role
    public String getRole(){
        return currentUser != null ? currentUser.getRole() : "";
    }
}
