package ClassAbsensi;
import javax.swing.*;
import java.awt.Color;
import java.util.HashMap;

public class SidebarButtonManager {
    
    private JButton[] buttons;
    private HashMap<JButton, String[]> iconPaths; // [0]=white, [1]=blue
    
    public SidebarButtonManager(JButton... buttons) {
        this.buttons = buttons;
        this.iconPaths = new HashMap<>();
        
        // Auto styling semua button
        for (JButton btn : buttons) {
            btn.setBackground(new Color(8, 86, 210));
            btn.setForeground(Color.white);
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setContentAreaFilled(true);
        }
    }
    
    // Method untuk register ikon button
    public void setButtonIcons(JButton button, String whiteIconPath, String blueIconPath) {
        iconPaths.put(button, new String[]{whiteIconPath, blueIconPath});
        button.setIcon(new ImageIcon(whiteIconPath)); // Set default white
    }
    
    public void setActive(JButton activeButton) {
        for (JButton btn : buttons) {
            if (btn == activeButton) {
                // Active: putih background, blue text
                btn.setBackground(Color.WHITE);
                btn.setForeground(Color.BLUE);
                
                // Ganti ke ikon blue
                if (iconPaths.containsKey(btn)) {
                    btn.setIcon(new ImageIcon(iconPaths.get(btn)[1]));
                }
            } else {
                // Default: biru background, white text
                btn.setBackground(new Color(8, 86, 210));
                btn.setForeground(Color.WHITE);
                
                // Ganti ke ikon white
                if (iconPaths.containsKey(btn)) {
                    btn.setIcon(new ImageIcon(iconPaths.get(btn)[0]));
                }
            }
        }
    }
}