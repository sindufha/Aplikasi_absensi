package absensiapp;
import javax.swing.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Class untuk manage styling sidebar buttons dengan OOP
 */
public class SidebarColor {
    
    // Style Constants
    private final Color DEFAULT_BG = new Color(51, 51, 51);      // Abu gelap
    private final Color ACTIVE_BG = new Color(0, 123, 255);      // Biru
    private final Color DEFAULT_FG = Color.WHITE;
    private final Color ACTIVE_FG = Color.WHITE;
    
    // List untuk menyimpan semua button yang dikelola
    private List<JButton> buttons;
    private JButton currentActiveButton;
    
    public SidebarColor() {
        this.buttons = new ArrayList<>();
    }
    
    /**
     * Tambahkan button ke manager
     */
    public void addButton(JButton button) {
        buttons.add(button);
        applyDefaultStyle(button);
    }
    
    /**
     * Tambahkan multiple buttons sekaligus
     */
    public void addButtons(JButton... buttons) {
        for (JButton btn : buttons) {
            addButton(btn);
        }
    }
    
    /**
     * Apply style default ke button
     */
    private void applyDefaultStyle(JButton button) {
        button.setBackground(DEFAULT_BG);
        button.setForeground(DEFAULT_FG);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(true);
    }
    
    /**
     * Apply style active ke button
     */
    private void applyActiveStyle(JButton button) {
        button.setBackground(ACTIVE_BG);
        button.setForeground(ACTIVE_FG);
    }
    
    /**
     * Set button sebagai active, dan reset button lainnya ke default
     */
    public void setActive(JButton button) {
        // Reset semua button ke default
        resetAll();
        
        // Set button yang dipilih jadi active
        applyActiveStyle(button);
        currentActiveButton = button;
    }
    
    /**
     * Reset semua button ke style default
     */
    public void resetAll() {
        for (JButton btn : buttons) {
            applyDefaultStyle(btn);
        }
    }
    
    /**
     * Get button yang sedang active
     */
    public JButton getCurrentActive() {
        return currentActiveButton;
    }
    
    // Setter untuk custom colors (optional)
    public void setDefaultColor(Color bg, Color fg) {
        // Implementasi jika mau custom
    }
    
    public void setActiveColor(Color bg, Color fg) {
        // Implementasi jika mau custom
    }
}