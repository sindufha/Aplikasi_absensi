package ClassAbsensi;

public class User {
    // Atribut private (Encapsulation)
    private int userId;
    private String username;
    private String password;
    private String nama;
    private String role;
    
    // Constructor default
    public User() {
    }
    
    // Constructor dengan parameter
    public User(int userId, String username, String nama, String role) {
        this.userId = userId;
        this.username = username;
        this.nama = nama;
        this.role = role;
    }
    
    // Constructor lengkap
    public User(int userId, String username, String password, String nama, String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.nama = nama;
        this.role = role;
    }
    
    // Getter dan Setter (Encapsulation)
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getNama() {
        return nama;
    }
    
    public void setNama(String nama) {
        this.nama = nama;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    // Method untuk cek apakah user adalah Admin
    public boolean isAdmin() {
        return this.role.equals("Admin");
    }
    
    // Method untuk cek apakah user adalah Guru
    public boolean isGuru() {
        return this.role.equals("Guru");
    }
    
    // Method toString untuk debugging
    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", nama='" + nama + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}