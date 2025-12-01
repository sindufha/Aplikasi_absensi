package ClassAbsensi;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class StatusCellRenderer extends DefaultTableCellRenderer {
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                    boolean isSelected, boolean hasFocus,
                                                    int row, int column) {
        
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        setHorizontalAlignment(JLabel.CENTER);
        
        if (value != null && !isSelected) {
            String status = value.toString();
            
            switch (status) {
                case "Hadir":
                    c.setBackground(new Color(220, 252, 231)); // #DCFCE7
                    c.setForeground(new Color(22, 163, 74));   // #16A34A
                    setFont(getFont().deriveFont(Font.BOLD));
                    break;
                    
                case "Sakit":
                    c.setBackground(new Color(254, 243, 199)); // #FEF3C7
                    c.setForeground(new Color(202, 138, 4));   // #CA8A04
                    setFont(getFont().deriveFont(Font.BOLD));
                    break;
                    
                case "Izin":
                    c.setBackground(new Color(219, 234, 254)); // #DBEAFE
                    c.setForeground(new Color(37, 99, 235));   // #2563EB
                    setFont(getFont().deriveFont(Font.BOLD));
                    break;
                    
                case "Alfa":
                    c.setBackground(new Color(254, 226, 226)); // #FEE2E2
                    c.setForeground(new Color(220, 38, 38));   // #DC2626
                    setFont(getFont().deriveFont(Font.BOLD));
                    break;
                    
                case "Telat":
                    c.setBackground(new Color(255, 237, 213)); // Orange muda
                    c.setForeground(new Color(234, 88, 12));   // Orange
                    setFont(getFont().deriveFont(Font.BOLD));
                    break;
                    
                default:
                    c.setBackground(Color.WHITE);
                    c.setForeground(Color.BLACK);
                    setFont(getFont().deriveFont(Font.PLAIN));
            }
        }
        
        if (isSelected) {
            c.setBackground(table.getSelectionBackground());
            c.setForeground(table.getSelectionForeground());
        }
        
        return c;
    }
}