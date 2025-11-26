package ClassAbsensi;
import javax.swing.*;
import java.awt.Color;

public class pengaturansidebar {
    
    private JButton[] buttons;
    
    public pengaturansidebar(JButton... buttons) {
        this.buttons = buttons;
        
        // Auto styling semua button
        for (JButton btn : buttons) {
            btn.setBackground(new Color(8, 86, 210));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setContentAreaFilled(true);
        }
    }
    
    public void setActive(JButton activeButton) {
        for (JButton btn : buttons) {
            if (btn == activeButton) {
                // Active: putih background, blue text
                btn.setBackground(new Color(227, 242, 253));
                btn.setForeground(new Color(21, 101, 192));
            } else {
                // Default: biru background, white text
                btn.setBackground(Color.WHITE);
                btn.setForeground(new Color(44, 62, 80));
            }
        }
    }
}